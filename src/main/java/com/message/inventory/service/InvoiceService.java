package com.message.inventory.service;

import com.message.inventory.model.Entity.Invoice;
import com.message.inventory.repositories.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepo invoiceRepo;

    public Invoice createInvoice(Invoice invoice) {
        try{
            return invoiceRepo.save(invoice);
        }catch (Exception e){
            return null;
        }
    }
}
