package com.rasitesdmr.springboottransactionmanagement.order.service;

import com.rasitesdmr.springboottransactionmanagement.cart.Cart;
import com.rasitesdmr.springboottransactionmanagement.cart.service.CartService;
import com.rasitesdmr.springboottransactionmanagement.cartitem.CartItem;
import com.rasitesdmr.springboottransactionmanagement.cartitem.service.CartItemService;
import com.rasitesdmr.springboottransactionmanagement.customer.Customer;
import com.rasitesdmr.springboottransactionmanagement.customer.service.CustomerService;
import com.rasitesdmr.springboottransactionmanagement.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springboottransactionmanagement.mail.enums.MailTypeEnum;
import com.rasitesdmr.springboottransactionmanagement.mail.service.MailService;
import com.rasitesdmr.springboottransactionmanagement.order.Order;
import com.rasitesdmr.springboottransactionmanagement.order.repository.OrderRepository;
import com.rasitesdmr.springboottransactionmanagement.orderitem.service.OrderItemService;
import com.rasitesdmr.springboottransactionmanagement.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final StockService stockService;
    private final CustomerService customerService;
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final ApplicationContext applicationContext;
    private final MailService mailService;

    public OrderServiceImpl(OrderRepository orderRepository, StockService stockService, CustomerService customerService, CartItemService cartItemService, CartService cartService, OrderItemService orderItemService, ApplicationContext applicationContext, MailService mailService) {
        this.orderRepository = orderRepository;
        this.stockService = stockService;
        this.customerService = customerService;
        this.cartItemService = cartItemService;
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.applicationContext = applicationContext;
        this.mailService = mailService;
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String approveOrder(Long authCustomerId) {
        Customer customer = customerService.getById(authCustomerId);
        Cart cart = cartService.getByCustomerId(customer.getId());
        List<CartItem> cartItems = cartItemService.getAllByCartId(cart.getId());
        if (!cartItems.isEmpty()) {
            cartItems.forEach(cartItem -> stockService.checkStockQuantity(cartItem.getProduct().getId(), cartItem.getQuantity()));
            Order order = createOrder(customer);
            saveOrder(order);
            cartItems.forEach(cartItem -> orderItemService.createOrderItem(cartItem, order));
            cartItems.forEach(cartItem -> stockService.deductStockForOrder(cartItem.getProduct().getId(), cartItem.getQuantity()));
            cartItems.forEach(cartItem -> cartItemService.deleteById(cartItem.getId()));
            mailService.sendMail(MailTypeEnum.ORDER, order.getId());
        }
        return "Cart Successfully Approved";
    }

    @Override
    public Order createOrder(Customer customer) {
        return Order.builder().customer(customer).createdDate(new Date()).build();
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotAvailableException("Order not found"));
    }

    @Override
    public List<Order> getAllByCreatedDateBetween() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        return orderRepository.findAllByCreatedDateBetween(Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay));
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
   // @Scheduled(cron = "0 * * * * *")
    public void logDailyOrderCount() {
        List<Order> todayOrders = getAllByCreatedDateBetween();
        int orderCount = todayOrders.size();
        LocalDateTime todayOrder = LocalDateTime.now().toLocalDate().atStartOfDay();
        log.info("Date : {} , Daily Number Of Orders : {} ", todayOrder, orderCount);
    }

    private OrderService getProxyOrderService() {
        return applicationContext.getBean(OrderService.class);
    }

}
