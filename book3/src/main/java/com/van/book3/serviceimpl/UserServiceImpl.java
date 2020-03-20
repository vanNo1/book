package com.van.book3.serviceimpl;

import com.van.book3.common.ServerResponse;
import com.van.book3.dao.UserMapper;
import com.van.book3.entity.User;
import com.van.book3.service.UserService;
import com.van.book3.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Van
 * @date 2020/3/7 - 14:31
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    public boolean isDupilicate(String openId){
        Map map=new HashMap();
        map.put("open_id",openId);
        List<User>userList=userMapper.selectByMap(map);
        if (userList.size()>0){
            return true;
        }else {
            return false;
        }
    }
    public ServerResponse register(User user){
        if (user.getOpenId()==null){
            return ServerResponse.error("未传openId");
        }
        //if user's is already register
        if (isDupilicate(user.getOpenId())){
            return ServerResponse.error("用户注册失败,用户已存在");
        }
      int count= userMapper.insert(user);
        if (count>0){
            return ServerResponse.success("用户注册成功",null);
        }else{
            return ServerResponse.error("用户注册失败");
        }

    }
    public User selectUserByOpenId(String openId){
        Map map=new HashMap();
        map.put("open_id",openId);
        List<User>userList=userMapper.selectByMap(map);
        if (CollectionUtils.isEmpty(userList)){
            return null;
        }else {
            return userList.get(0);
        }
    }
    public ServerResponse getDay(String openId){
        User user=selectUserByOpenId(openId);
        //if user is not exist
        if (user==null){
            return ServerResponse.error("找不到该用户");
        }
        Duration duration=Duration.between(user.getCreateTime(), LocalDateTime.now());
        long days=duration.toDays();
        Map map=new HashMap();
        map.put("day",days);
        return ServerResponse.success("用户注册成功",map);
    }

}
