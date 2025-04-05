package com.rasitesdmr.springboottransactionmanagement.mail.service;

import com.rasitesdmr.springboottransactionmanagement.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springboottransactionmanagement.mail.enums.MailTypeEnum;
import com.rasitesdmr.springboottransactionmanagement.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private final OrderService orderService;

    public MailServiceImpl(@Lazy OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String sendMail(MailTypeEnum mailTypeEnum, Long orderId) {
        switch (mailTypeEnum) {
            case MailTypeEnum.ORDER -> {
                log.info("Your order has been created and mailed. Order ID:{}", orderId);
                //throw new RuntimeException("Send Mail Method Error");
                return "Your order has been created.";
            }
            case MailTypeEnum.CARGO -> {
                log.info("Your order has been shipped and mailed. Order ID:{}", orderId);
                return "Your order has been shipped.";
            }
            default -> {
                return "default message";
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendInvoiceByEmail(String email) {
        // Şiparişten sonra kesilen faturayı müşteriye e-posta gönderir.
        orderService.logDailyOrderCount();
    }

}
