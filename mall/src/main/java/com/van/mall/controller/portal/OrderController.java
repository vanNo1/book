package com.van.mall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.van.mall.common.Const;
import com.van.mall.common.ServerResponse;
import com.van.mall.entity.User;
import com.van.mall.service.serviceImpl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/13 - 14:24
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Resource
    private OrderServiceImpl orderService;
    @RequestMapping("/pay")
    public ServerResponse pay(HttpSession session, HttpServletRequest request,Long orderNo){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.error("用户未登录");
        }
        String path=request.getSession().getServletContext().getRealPath("/upload");
        return orderService.pay(orderNo,user.getId(),path);
    }
    @RequestMapping("alipay_callback.do")
    public Object alipayCallback(HttpServletRequest request){
        Map params=new HashMap();
         Map requestParams=request.getParameterMap();
         String valueStr="";
         for (Iterator iterator=requestParams.keySet().iterator();iterator.hasNext();){
             String key=(String) iterator.next();
             String []values=(String[]) requestParams.get(key);
             for (int i=0;i<values.length;i++){
                 valueStr=(i==values.length-1)?valueStr+values[i]:valueStr+values[i]+",";
             }
             params.put(key,valueStr);
         }
         log.info("支付宝回调sign{}，trade_status{}，参数{}",params.get("sign"),params.get("trade_status"),params.toString());
                 //todo verify the authentic of params to ensure that is from alipay and avoid the repeat of notify from alipay
        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2= AlipaySignature.rsaCheckV2(params, Configs.getPublicKey(),"utf-8",Configs.getSignType());
            if (!alipayRSACheckedV2){
                return ServerResponse.error("非法请求，验证不通过");
                //todo service logic

            }
        } catch (AlipayApiException e) {
            log.error("支付宝回调异常",e);
        }
        //todo verify datas
return null;
    }
    @RequestMapping("/create.do")
    public ServerResponse create(HttpSession session,Integer shippingId){
   //todo
        return null;

    }
}
