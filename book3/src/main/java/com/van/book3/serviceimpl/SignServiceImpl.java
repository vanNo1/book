package com.van.book3.serviceimpl;

import com.van.book3.dao.SignMapper;
import com.van.book3.entity.Sign;
import com.van.book3.service.SignService;
import com.van.book3.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Van
 * @date 2020/3/7 - 14:15
 */
@Slf4j
@Service
public class SignServiceImpl implements SignService {
    @Resource
    private SignMapper signMapper;
    @Override
    public void insert(Sign sign) {
        try {
            //get create time
            sign.setCreateDt(DateUtil.now());
            signMapper.insert(sign);
        }catch (Exception e){
           e.printStackTrace();
        }
    }
}
