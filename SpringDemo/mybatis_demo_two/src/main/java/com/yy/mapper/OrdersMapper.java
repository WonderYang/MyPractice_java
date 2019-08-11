package com.yy.mapper;

import com.yy.po.Orders;
import com.yy.po.OrdersEx;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 14:52 2019/8/10
 */
public interface OrdersMapper {
    List<OrdersEx> queryOrdersToUser() throws Exception;
}
