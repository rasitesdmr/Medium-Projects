package com.rasitesdmr.springboottransactionmanagement.mail.controller;

import com.rasitesdmr.springboottransactionmanagement.mail.enums.MailTypeEnum;
import com.rasitesdmr.springboottransactionmanagement.mail.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping(path = "/send")
    public ResponseEntity<String> sendMail(@RequestParam MailTypeEnum mailTypeEnum, @RequestParam Long orderId){
        return new ResponseEntity<>(mailService.sendMail(mailTypeEnum, orderId), HttpStatus.OK);
    }

    @PostMapping(path = "/invoice/send")
    public void sendInvoiceByEmail(){
        mailService.sendInvoiceByEmail("test@gmail.com");
    }
}
