package com.inditex.zboost.service;

import com.inditex.zboost.entity.Order;
import com.inditex.zboost.entity.OrderDetail;
import com.inditex.zboost.exception.InvalidParameterException;
import com.inditex.zboost.exception.NotFoundException;

import java.util.Date;
import java.util.List;

public interface OrderService {

    List<Order> findOrders(int limit) throws InvalidParameterException;

    List<Order> findOrdersBetweenDates(Date fromDate, Date toDate);

    OrderDetail findOrderDetail(long orderId) throws NotFoundException;

}
