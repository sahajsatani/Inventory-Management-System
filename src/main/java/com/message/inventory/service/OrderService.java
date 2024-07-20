package com.message.inventory.service;

import com.message.inventory.config.TwilioConfig;
import com.message.inventory.model.*;
import com.message.inventory.model.CustomerResponseDtos.OrderResponse;
import com.message.inventory.model.Email.EmailDetails;
import com.message.inventory.model.Email.EmailService;
import com.message.inventory.model.Embedable.Address;
import com.message.inventory.model.InvoiceDtos.Status;
import com.message.inventory.repositories.*;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class OrderService {

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    TwilioConfig twilioConfig;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    CustomerRepo customerRepo;

    @Transactional
    public ResponseEntity<?> makeProduct(Order order) {
        try {
            Product p = productRepo.findById(order.getProduct().getProductId()).get();
            order.setProduct(p);
            order.setCustomer(customerRepo.findById(order.getCustomer().getCustomerId()).get());
            order.setTotalAmount(((p.getPrice() + (p.getPrice() * p.getGst()) / 100)) * order.getQty());

            if (p.getInventoryStoke()>=order.getQty() && payment(order)) {
                order.setStatus(Status.PAID);

                p.setSold(p.getSold() + order.getQty());
                p.setInventoryStoke(p.getInventoryStoke() - order.getQty());

                Invoice invoice = Invoice.builder()
                        .transactionId("TEMP_Transaction_ID")
                        .order(order)
                        .date(order.getOrderDate())
                        .customerPayment(order.getCustomer())
                        .productId(order.getProduct().getProductId())
                        .qty(order.getQty())
                        .gst(order.getProduct().getGst())
                        .totalAmount(order.getTotalAmount())
                        .build();

                //Send SMS to customer
                OrderResponse orderResponse = sendOrderResponce(order.getCustomer().getName(),p.getProductName(),invoice.getTotalAmount(),order.getCustomer().getPhone());
//                OrderResponse orderResponse = new OrderResponse(com.message.inventory.model.CustomerResponseDtos.Status.DELIVERED, "Message Send Successfully...");

                //Send Email to customer
                sendOrderEmailToCustomer(order);

                if(p.getInventoryStoke()<=100)
                    sendThreshold(p);

                orderRepo.save(order);
                productRepo.save(p);
                invoiceService.createInvoice(invoice);
                return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
            }
            else if(p.getInventoryStoke()<order.getQty()){
                return new ResponseEntity<>("Only "+p.getInventoryStoke()+" left", HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>("Transaction Faild.", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private OrderResponse sendOrderResponce(String name, String product, int price, String phone) {
        OrderResponse orderResponse;
        try {
            Message
                    .creator(
                            new com.twilio.type.PhoneNumber("+91" + phone),
                            new com.twilio.type.PhoneNumber("+14157670885"),
                            "Dear " + name + ", Your order of " + product + " is confirmed for price " + price + " INR. We’ll keep you updated on its status. Your satisfaction is our priority. Let us know if there’s anything we can improve.")
                    .create();
            orderResponse = new OrderResponse(com.message.inventory.model.CustomerResponseDtos.Status.DELIVERED, "Message Send Successfully...");
        } catch (Exception e) {
            orderResponse = new OrderResponse(com.message.inventory.model.CustomerResponseDtos.Status.FAILED, e.getMessage());
        }
        return orderResponse;
    }
    private void sendThreshold(Product p) {
        List<Admin> list = adminRepo.findAll();
        for (Admin admin : list) {
                try {
                    Message message = Message
                            .creator(
                                    new com.twilio.type.PhoneNumber("whatsapp:+91" + admin.getPhone()),
                                    new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                                    "Alert! Threshold Report\n" +
                                            "Stock Report: Product Inventory\n" +
                                            "\n" +
                                            "Product ID: " + p.getProductId() + "\n" +
                                            "Product Name: " + p.getProductName() + "\n" +
                                            "GST: " + p.getGst() + "%\n" +
                                            "Price (INR): " + p.getPrice() + ".00\n" +
                                            "Inventory Stock: " + p.getInventoryStoke() + "\n")
                            .create();
                } catch (Exception e) {
                    Message message = Message
                            .creator(
                                    new com.twilio.type.PhoneNumber("whatsapp:+91" + admin.getPhone()),
                                    new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                                    "Exception while send whatsapp message\n error:" + e.getMessage())
                            .create();
                }
        }
    }
    private void sendOrderEmailToCustomer(Order order) {
        String msg = makeMsg(order);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(order.getCustomer().getEmail())
                .subject("Order Confirmed On Shopify")
                .msgBody(msg)
                .build();
        emailService.sendSimpleMail(emailDetails);
    }
    private String makeMsg(Order order) {
        Address address = order.getAddress();
        String msg = new StringBuilder().append("Order Confirmation\n").append("\n").append("Dear ").append(order.getCustomer().getName()).append(",\n").append("\n").append("Thank you for shopping with us! Your order has been confirmed successfully. Here are the details:\n").append("\n").append("- Order ID: ").append(order.getOrderId()).append("\n\n").append("- Order Date: ").append(order.getOrderDate()).append("\n\n").append("- Product Name: ").append(order.getProduct().getProductName()).append("\n\n").append("- Quantity: ").append(order.getQty()).append("\n\n").append("- Total Amount (INR): ").append(order.getTotalAmount()).append(".00\n\n").append("- Shipping With in : ").append(order.getShippingDate()).append("\n\n").append("- Shipping Address:\n").append("  - Apartment No.: ").append(address.getAppartmentNo()).append("\n").append("  - Society: ").append(address.getSociety()).append("\n").append("  - Area: ").append(address.getArea()).append("\n").append("  - City: ").append(address.getCity()).append("\n").append("  - State: ").append(address.getState()).append("\n").append("  - Pincode: ").append(address.getPincode()).append("\n").append("\n").append("Your order is on its way! We'll notify you once it's shipped.\n").append("\n").append("If you have any questions or need assistance, feel free to reply to this email or contact our customer support team.\n").append("\n").append("Thank you for choosing us!\n").append("\n").append("Best regards,\n").append("Shopify\n").toString();
        return msg;
    }
    public ResponseEntity<?> getOrder(int id) {
        return new ResponseEntity<>(orderRepo.findById(id), HttpStatus.OK);
    }
    private boolean payment(Order order) {
        Random random = new Random();
        return random.nextBoolean();
    }
}
