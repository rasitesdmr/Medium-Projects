package com.rasitesdmr.springboottransactionmanagement.mail.service;

import com.rasitesdmr.springboottransactionmanagement.mail.enums.MailTypeEnum;

public interface MailService {

    String sendMail(MailTypeEnum mailTypeEnum, Long orderId);
    void sendInvoiceByEmail(String email);
}
