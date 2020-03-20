package com.van.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Van
 * @date 2020/3/13 - 14:28
 */
@Data
@TableName("mmall_order_item")
public class Order {
    @TableId
   private Integer id;
   private Long orderNo;
   private Integer userId;
   private Integer shippingId;
   private BigDecimal payment;
   private Integer paymentType;
   private Integer postage;
   private Integer status;
   private LocalDateTime paymentTime;
    private LocalDateTime sendTime;
    private LocalDateTime endTime;
    private LocalDateTime closeTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
