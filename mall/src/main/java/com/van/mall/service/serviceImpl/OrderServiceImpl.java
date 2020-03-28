package com.van.mall.service.serviceImpl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.google.common.collect.Lists;
import com.van.mall.common.Const;
import com.van.mall.common.ServerResponse;
import com.van.mall.dao.OrderMapper;
import com.van.mall.dao.ProductMapper;
import com.van.mall.entity.Cart;
import com.van.mall.entity.Order;
import com.van.mall.entity.OrderItem;
import com.van.mall.entity.Product;
import com.van.mall.service.IOrderService;
import com.van.mall.util.BigDecimalUtil;
import com.van.mall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/13 - 14:28
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {
    private static AlipayTradeService tradeService;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemServiceImpl orderItemService;
    @Resource
    private CartServiceImpl cartService;

    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    public Order selectByOrderNo(Long orderNo) {
        Map map = new HashMap();
        map.put("orderNo", orderNo);
        List<Order> orderList = orderMapper.selectByMap(map);
        if (orderList.size() > 0) {
            return orderList.get(0);
        } else {
            return null;
        }
    }

    public Order findOrderByOrderNoAndUserId(Integer userId, Long orderNo) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("orderNo", orderNo);
        List<Order> orderList = orderMapper.selectByMap(map);
        if (orderList.size() > 0) {
            return orderList.get(0);
        } else {
            return null;
        }
    }

    public ServerResponse pay(Long orderNo, Integer userId, String path) {
        Map<String, String> resultMap = new HashMap<>();
        Order order = findOrderByOrderNoAndUserId(userId, orderNo);
        if (order == null) {
            return ServerResponse.error("用户没有该订单");
        }
        resultMap.put("orderNo", String.valueOf(order.getOrderNo()));


        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "happymmall 扫码支付，订单号：" + outTradeNo;

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "订单：" + outTradeNo + "购买商品共" + totalAmount + "元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        List<OrderItem> orderItemList = orderItemService.findOrderItemByUserIdAndOrderId(userId, orderNo);
        for (OrderItem item : orderItemList) {
            GoodsDetail good = GoodsDetail.newInstance(item.getProductId().toString(), item.getProductName(), BigDecimalUtil.mul(item.getCurrentUnitPrice().doubleValue(), new Double(100).doubleValue()).longValue(), item.getQuantity());
            goodsDetailList.add(good);
        }


//        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
//        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
//        // 创建好一个商品后添加至商品明细列表
//        goodsDetailList.add(goods1);
//
//        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
//        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
//        goodsDetailList.add(goods2);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
//                .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);


        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();


        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
                //封装二维码。。。。。。。。。。。。。。。。
                File upload = new File(path);
                if (!upload.exists()) {
                    upload.setWritable(true);
                    upload.mkdirs();
                }
                String qrName = String.format("ar-%s.png", response.getOutTradeNo());
                String qrPath = String.format(upload + "/" + qrName);
                // 需要修改为运行机器上的路径
                log.info("filePath:" + qrPath);
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);//get QRCode
                File targetFile = new File(path, qrName);
                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));//sent QRCode to FTPServer
                } catch (IOException e) {
                    log.error("二维码上传异常");
                }
                return ServerResponse.success(resultMap);


            case FAILED:
                log.error("支付宝预下单失败!!!");
                return ServerResponse.error("支付宝预下单失败!!!");

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                return ServerResponse.error("系统异常，预下单状态未知!!!");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.error("不支持的交易状态，交易返回异常!!!");
        }
    }

    public ServerResponse aliCallback(Map<String, String> params) {
        Long orderNo = Long.parseLong(params.get("out_trade_no"));// in pay method i fill orderNo to out_trade_no
        String tradeNo = params.get("trade_status");//alipay's tradeNo
        String tradeStatus = params.get("trade_status");
        Order order = selectByOrderNo(orderNo);
        if (order == null) {
            return ServerResponse.error("该订单不是快乐商场的订单，请务必忽略");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            //the user is already paid
            return ServerResponse.success("支付宝重复调用");
        }
        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeNo)) {
            order.setStatus(Const.OrderStatusEnum.ORDER_SUCCESS.getCode());
            orderMapper.updateById(order);
        }
        //todo
        return null;
    }

    public ServerResponse createOrder(Integer userId, Integer shippingId) {
        //get all checked chart from a user
        List<Cart> cartList = cartService.selectUserCartWhichIsChecked(userId);
        List<OrderItem> orderItemList = (List<OrderItem>) getCartOrderItem(userId, cartList);
        //get order total price
        BigDecimal totalPrice = getTotalPrice(orderItemList);

        //generate the order
        Order order = assembleOrder(userId, shippingId, totalPrice);
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        //batch insert orderItem
        orderItemService.batchInsert(orderItemList);
        //clean the cart
        cleanCart(cartList);
        //todo
        return null;

    }

    private void cleanCart(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartService.deleteById(cart.getId());
        }
    }

    private void reduceProductStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectById(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            productMapper.updateById(product);
        }
    }

    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setStatus(10);//10-no pay
        order.setPostage(0);
        order.setPaymentType(1);//1-pay online
        order.setUserId(userId);
        order.setShippingId(shippingId);
        int count = orderMapper.insert(order);
        if (count > 0) {
            return order;
        } else {
            return null;
        }


    }

    private long generateOrderNo() {
        long currentTime = System.currentTimeMillis();
        return currentTime + currentTime % 10;//%10 can generate a 0-9 number
    }

    private BigDecimal getTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal totalPrice = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            totalPrice = BigDecimalUtil.add(totalPrice.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return totalPrice;
    }

    //make sure two things
    // 1.the product in the cart is must on sale
    //2. the product's stock is must enough
    private ServerResponse getCartOrderItem(Integer userId, List<Cart> cartList) {
        if (CollectionUtils.isEmpty(cartList)) {
            return ServerResponse.error("购物车为空");
        }
        List orderItemList = new ArrayList();
        for (Cart cart : cartList) {
            Product product = productMapper.selectById(cart.getProductId());
            //verify this product is no sale
            if (product.getStatus() != 1) {
                return ServerResponse.error("商品" + product.getName() + "不是在售状态");
            }
            //make sure that stock of product is enough
            if (product.getStock() < cart.getQuantity()) {
                return ServerResponse.error("产品" + product.getName() + "库存不足");
            }
            //user cart and product to wrap orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setUserid(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(cart.getQuantity().doubleValue(), product.getPrice().doubleValue()));
            orderItemList.add(orderItem);
        }
        return ServerResponse.success(orderItemList);
    }


}
