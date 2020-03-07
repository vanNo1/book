package com.van.book3.controller;

import com.google.gson.Gson;
import com.van.book3.entity.Openid;
import com.van.book3.entity.Sign;
import com.van.book3.serviceimpl.SignServiceImpl;
import com.van.book3.utils.OpenIdVOUtil;
import com.van.book3.vo.OpenIdVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/6 - 12:44
 */
@Slf4j
@RestController
@RequestMapping("/openId")
public class loginController {
    @Resource
    SignServiceImpl signService;
    @RequestMapping("/get")
    public OpenIdVO<Openid> login(@RequestParam String code,@RequestParam String appId, @RequestParam String  secret){

        Map<String ,String > params=new HashMap<>();
        params.put("appId",appId);
        params.put("secret",secret);
        params.put("code",code);
        String url="https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={secret}&js_code={code}&grant_type=authorization_code";
        RestTemplate restTemplate=new RestTemplate();
        String openidString= restTemplate.getForObject(url,String.class,params);
        //have already get openid.....................
        Gson gson=new Gson();
        Openid openid= gson.fromJson(openidString, Openid.class);
        //translate to entity...........................
        //1.if openid is not null
        if (openid.getOpenid()!=null){
            OpenIdVO openIdVO= OpenIdVOUtil.success(openid);
            log.info(openIdVO.toString());
            //build Sign entity
            Sign sign=new Sign();
            sign.setOpenId(openid.getOpenid());
            signService.insert(sign);
            //.............................insert to database
            return openIdVO;

        }
         //2. if openid is null
        OpenIdVO openIdVO= OpenIdVOUtil.error();
        return openIdVO;

    }

}
