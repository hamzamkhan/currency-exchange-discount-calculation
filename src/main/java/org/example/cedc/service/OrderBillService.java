package org.example.cedc.service;

import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import org.example.cedc.data.ItemRepository;
import org.example.cedc.data.OrderItemRepository;
import org.example.cedc.data.OrderRepository;
import org.example.cedc.data.StoreUserRepository;
import org.example.cedc.exception.ServiceLayerException;
import org.example.cedc.model.dto.OrderItemDTO;
import org.example.cedc.model.dto.request.OrderBillRequestDTO;
import org.example.cedc.model.dto.response.OrderBillResponseDTO;
import org.example.cedc.model.entity.Item;
import org.example.cedc.model.entity.Order;
import org.example.cedc.model.entity.OrderItem;
import org.example.cedc.model.entity.StoreUser;
import org.example.cedc.model.enums.ItemCategory;
import org.example.cedc.model.enums.OrderStatus;
import org.example.cedc.model.enums.StoreUserRole;
import org.example.cedc.util.ExchangeRateFetcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Service
public class OrderBillService {
    private final ItemRepository itemRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final StoreUserRepository storeUserRepository;

    private final ExchangeRateFetcher exchangeRateFetcher;

    private static final String ERROR_ITEM_NOT_FOUND = "Item does not exist";

    private static final String ERROR_USER_NOT_FOUND = "User does not exist";

    public OrderBillService(ItemRepository itemRepository, OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository, StoreUserRepository storeUserRepository,
                            ExchangeRateFetcher exchangeRateFetcher) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.storeUserRepository = storeUserRepository;
        this.exchangeRateFetcher = exchangeRateFetcher;
    }

    public OrderBillResponseDTO calculateOrder(OrderBillRequestDTO request){
        return new OrderBillResponseDTO(calculateFinalTotal(request).setScale(2, RoundingMode.CEILING));
    }

    private BigDecimal calculateFinalTotal(OrderBillRequestDTO request){
        boolean totalAmountPresent = false;
        boolean userEmailPresent = false;
        if(StringUtils.isNotBlank(request.getUserEmail())){
            userEmailPresent = true;
        }
        StoreUser user = null;
        if(userEmailPresent){
            user = storeUserRepository.findByEmail(request.getUserEmail());
            if(Objects.isNull(user)){
                throw new ServiceLayerException(ERROR_USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
        }

        if(Objects.nonNull(request.getTotalAmount()) && request.getTotalAmount().compareTo(BigDecimal.ZERO) > 0){
            totalAmountPresent = true;
        }
        BigDecimal originalTotal = totalAmountPresent ? calculateTotalDiscountedAmountFromRequest(request) : calculateItemTotalPrice(request.getOrderItems(), user);
        originalTotal = calculateHundredDiscount(originalTotal);
        BigDecimal conversionRate = BigDecimal.ONE; //Default

        Order order = createOrder(request, user);


        //Fetching currency exchange rate
        if(!request.getCurrency().equalsIgnoreCase(request.getTargetCurrency())){
            conversionRate = exchangeRateFetcher.fetchPairConversionRate(request.getCurrency(), request.getTargetCurrency());
        }

        BigDecimal payabaleAmount = conversionRate.multiply(originalTotal);
        order.setTotalAmountOriginalCurrency(originalTotal);
        order.setTotalPayableAmountTargetCurrency(payabaleAmount);

        orderRepository.save(order);
        orderItemRepository.saveAll(getOrderItems(request.getOrderItems(), order));

        return payabaleAmount;
    }

    private BigDecimal calculateTotalDiscountedAmountFromRequest(OrderBillRequestDTO request){
        BigDecimal totalPrice = request.getTotalAmount();
        boolean hasGroceries = false;
        for(OrderItemDTO orderItemDTO : request.getOrderItems()){
            if(ItemCategory.GROCERIES.equals(orderItemDTO.getCategory())){
                hasGroceries = true;
                break;
            }

        }
        if(!hasGroceries){
            totalPrice = calculateDiscountPerItem(null, totalPrice, request.getRole(), request.getCustomerTenure());
        }

        return totalPrice;
    }

    private BigDecimal calculateItemTotalPrice(List<OrderItemDTO> orderItemDTOS, StoreUser user){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(OrderItemDTO orderItemDTO : orderItemDTOS){
            if(StringUtil.isNullOrEmpty(orderItemDTO.getItemId())){
                throw new ServiceLayerException(ERROR_ITEM_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
            Optional<Item> optionalItem = itemRepository.findById(Long.valueOf(orderItemDTO.getItemId()));
            if(optionalItem.isEmpty()){
                throw new ServiceLayerException(ERROR_ITEM_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
            Item item = optionalItem.get();
            BigDecimal price = item.getCategory().equals(ItemCategory.GROCERIES) ? item.getPrice() :
                    calculateDiscountPerItem(user, item.getPrice(), user.getRole(), 0);
            totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(orderItemDTO.getQuantity())));
        }

        return totalPrice;
    }

    private BigDecimal calculateDiscountPerItem(StoreUser user, BigDecimal itemPrice, StoreUserRole role, Integer tenure){
        BigDecimal discountedPrice = itemPrice;
        if(StoreUserRole.EMPLOYEE.equals(role)){
            discountedPrice = itemPrice.multiply(new BigDecimal(30)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            discountedPrice = itemPrice.subtract(discountedPrice);
        } else if(StoreUserRole.AFFILIATE.equals(role)){
            discountedPrice = itemPrice.multiply(new BigDecimal(10)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            discountedPrice = itemPrice.subtract(discountedPrice);
        } else if(StoreUserRole.CUSTOMER.equals(role)){
            if(Objects.nonNull(user)){
                LocalDateTime userSince = user.getCreatedAt();
                long yearsBetween = ChronoUnit.YEARS.between(userSince, LocalDateTime.now());
                if(yearsBetween > 2){
                    discountedPrice = itemPrice.multiply(new BigDecimal(5)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                    discountedPrice = itemPrice.subtract(discountedPrice);
                }
            } else {
                if(tenure > 2){
                    discountedPrice = itemPrice.multiply(new BigDecimal(5)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                    discountedPrice = itemPrice.subtract(discountedPrice);
                }
            }

        }
        return discountedPrice;
    }

    private BigDecimal calculateHundredDiscount(BigDecimal totalPrice){
        BigDecimal hundredDiscountAmount = new BigDecimal(5);
        BigDecimal discountedAmount = totalPrice.divide(new BigDecimal(100), 0, RoundingMode.FLOOR).multiply(hundredDiscountAmount);
        return totalPrice.subtract(discountedAmount);
    }

    private Order createOrder(OrderBillRequestDTO requestDTO, StoreUser user){
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setOriginalCurrency(requestDTO.getCurrency());
        order.setTargetCurrency(requestDTO.getTargetCurrency());
        order.setStoreUser(user);

        return orderRepository.save(order);
    }

    private List<OrderItem> getOrderItems(List<OrderItemDTO> orderItemDTOS, Order order){
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemDTO orderItemDTO : orderItemDTOS){
            OrderItem orderItem = new OrderItem();
            Item item = null;
            if(StringUtil.isNullOrEmpty(orderItemDTO.getItemId())){
                item = createItem(orderItemDTO);
            } else {
                item = itemRepository.findById(Long.valueOf(orderItemDTO.getItemId())).orElse(null);
            }
            if(Objects.isNull(item)){
                throw new ServiceLayerException(ERROR_ITEM_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
            orderItem.setItem(item);
            orderItem.setOrder(order);
            orderItem.setUpdatedAt(LocalDateTime.now());
            orderItem.setCreatedAt(LocalDateTime.now());
            orderItem.setQuantity(orderItemDTO.getQuantity());

            orderItems.add(orderItem);
        }

        return orderItems;
    }

    private Item createItem(OrderItemDTO orderItemDTO){
        Item item = new Item();
        item.setCategory(orderItemDTO.getCategory());
        item.setName(orderItemDTO.getName());
        item.setQuantity(1);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        item.setPrice(BigDecimal.ONE);

        return itemRepository.save(item);
    }
}
