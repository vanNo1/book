package com.van.book3.serviceimpl;

import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.UserMapper;
import com.van.book3.entity.User;
import com.van.book3.service.UserService;
import com.van.book3.utils.DateUtil;
import com.van.book3.utils.RedisPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Van
 * @date 2020/3/7 - 14:31
 */
@Slf4j
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
    public User findUserByOpenId(String openId){
        Map map=new HashMap();
        map.put("open_id",openId);
        List<User>userList=userMapper.selectByMap(map);
        if (userList.size()==0){
            return null;
        }
        return userList.get(0);
    }
    public ServerResponse register(User user){

        //if user's is already register
        if (isDupilicate(user.getOpenId())){
            return ServerResponse.error(Const.DUPLICATE,"用户注册失败,用户已存在(openid is duplicate)");
        }
        //if the param is invalid
        if (user.getOpenId()==null||user.getUsername()==null||user.getPassword()==null||user.getAnswer()==null||user.getQuestion()==null){
            return ServerResponse.error(Const.ISNULL,"必要参数没传");
        }
        try {
           int count = userMapper.insert(user);
            if (count>0){
                return ServerResponse.success("用户注册成功",null);
            }else{
                return ServerResponse.error("用户注册失败");
            }
        } catch (Exception e) {
            log.info("插入异常",e);
            return ServerResponse.error(Const.SYSTEMERROR,"插入异常");
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
    public ServerResponse changePassword(String username,String oldPassword,String newPassword,String openId){

        //param is invalid
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(oldPassword)||StringUtils.isEmpty(newPassword)||StringUtils.isEmpty(openId)){
            return ServerResponse.error(Const.ISNULL,"缺少必要参数");
        }
        User user=findUserByOpenId(openId);
        if (user==null){
            return ServerResponse.error("用户不存在");
        }
        if (!(username.equals(user.getUsername())&&oldPassword.equals(user.getPassword()))){
            return ServerResponse.error("用户名或密码有误");
        }
        //ok
        user.setPassword(newPassword );

        try {
            userMapper.updateById(user);
            return ServerResponse.success("密码更新成功",null);
        } catch (Exception e) {
            log.error("插入异常",e);
            return ServerResponse.error(Const.SYSTEMERROR,"插入异常");
        }

    }
    public ServerResponse forgetPassword(String openId, String answer){
        User user=findUserByOpenId(openId);
        if (user==null){
            return ServerResponse.error("用户不存在");
        }
        if (!answer.equals(user.getAnswer())){
            return ServerResponse.error("问题错误");
        }
     //answer is right
        //forget token is 5 minute
        String forgetToken=UUID.randomUUID().toString();
        RedisPoolUtil.setEx("token_"+openId,forgetToken,60*5);
        return ServerResponse.success("回答正确，返回forgetToken",forgetToken);
    }
    public ServerResponse forgetPasswordAndChangePassword(String openId,String forgetToken,String newPassword){
        User user=findUserByOpenId(openId);
        if (user==null){
            return ServerResponse.error("用户不存在");
        }
        String value= RedisPoolUtil.get(forgetToken);
        if (value==null){
            return ServerResponse.error("forToken无效（超时或不正确）");
        }
        //if ok
        user.setPassword(newPassword);
        userMapper.updateById(user);
        return ServerResponse.success("找回密码成功");
    }
    public ServerResponse login(String openId, String username, String password){
        User user=findUserByOpenId(openId);
        if (user==null){
            return ServerResponse.error("用户不存在");
        }
        if (!(user.getUsername().equals(username)&&user.getPassword().equals(password))){
            return ServerResponse.error("用户名或密码错误");
        }
        return ServerResponse.success("用户名密码正确");
    }
}
