package com.van.book3.serviceimpl;

import com.google.gson.Gson;
import com.van.book3.common.CodeMsg;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.SignMapper;
import com.van.book3.entity.Sign;
import com.van.book3.exception.GlobalException;
import com.van.book3.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/7 - 14:15
 */
@Slf4j
@Service
public class SignServiceImpl implements SignService {
    @Resource
    private SignMapper signMapper;

    public ServerResponse getOpenId(String code, String appId, String secret) {
        Map<String, String> params = new HashMap<>();
        params.put("appId", appId);
        params.put("secret", secret);
        params.put("code", code);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={secret}&js_code={code}&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String openidString = restTemplate.getForObject(url, String.class, params);
        log.info("have already get openid: ", openidString);
        //have already get openid.....................
        Gson gson = new Gson();
        Sign openid = gson.fromJson(openidString, Sign.class);
        //translate to entity...........................
        //1.if openid is not null
        if (openid.getOpenId() != null) {
            //1.if openid is not duplicate
            if (!isDuplicate(openid.getOpenId())) {
                //build Sign entity
                signMapper.insert(openid);
                //.............................insert to database

            }

            return ServerResponse.success("获取openId成功", openid);

        }
        //2. if openid is null
        throw new GlobalException(CodeMsg.OPENID_FAIL);

    }

    public boolean isDuplicate(String openid) {
        Map map = new HashMap();
        map.put("open_id", openid);
        List<Sign> signList = signMapper.selectByMap(map);
        if (signList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
