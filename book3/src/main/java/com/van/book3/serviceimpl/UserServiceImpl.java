package com.van.book3.serviceimpl;

import com.van.book3.dao.UserMapper;
import com.van.book3.entity.User;
import com.van.book3.service.UserService;
import com.van.book3.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Van
 * @date 2020/3/7 - 14:31
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public void insert(User user)throws Exception {
        try {
            user.setCreateDt(DateUtil.now());
            user.setUpdateDt(DateUtil.now());
            userMapper.insert(user);
        }catch (Exception e){
            throw e;
        }
    }
}
