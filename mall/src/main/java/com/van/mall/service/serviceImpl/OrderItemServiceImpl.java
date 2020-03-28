package com.van.mall.service.serviceImpl;

import com.van.mall.dao.OrderItemMapper;
import com.van.mall.entity.OrderItem;
import com.van.mall.service.IOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/13 - 15:17
 */
@Service
public class OrderItemServiceImpl implements IOrderService {
    @Resource
    private OrderItemMapper orderItemMapper;

    public List<OrderItem> findOrderItemByUserIdAndOrderId(Integer userId, Long orderNo) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("orderNo", orderNo);
        List<OrderItem> orderItemList = orderItemMapper.selectByMap(map);
        return orderItemList;
    }

    public boolean batchInsert(List<OrderItem> orderItemList) {
        int num = 0;
        for (OrderItem orderItem : orderItemList) {
            num += orderItemMapper.insert(orderItem);
        }
        if (num == orderItemList.size()) {
            return true;
        } else {
            return false;
        }
    }
}
