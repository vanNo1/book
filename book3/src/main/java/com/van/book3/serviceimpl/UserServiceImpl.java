package com.van.book3.serviceimpl;

import com.van.book3.common.CodeMsg;
import com.van.book3.common.Const;
import com.van.book3.common.ServerResponse;
import com.van.book3.dao.UserMapper;
import com.van.book3.entity.User;
import com.van.book3.exception.GlobalException;
import com.van.book3.service.UserService;
import com.van.book3.utils.RedisPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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

    public boolean isDupilicate(String openId) {
        Map map = new HashMap();
        map.put("open_id", openId);
        List<User> userList = userMapper.selectByMap(map);
        if (userList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public User findUserByOpenId(String openId) {
        Map map = new HashMap();
        map.put("open_id", openId);
        List<User> userList = userMapper.selectByMap(map);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    public ServerResponse register(User user) {

        //if user's is already register
        if (isDupilicate(user.getOpenId())) {
            throw new GlobalException(CodeMsg.DUPLICATE_USER);
        }
        //insert user to DB
        try {
            userMapper.insert(user);
            return ServerResponse.success("用户注册成功");
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.DB_ERROR);
        }

    }

    public User selectUserByOpenId(String openId) {
        Map map = new HashMap();
        map.put("open_id", openId);
        List<User> userList = userMapper.selectByMap(map);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    public ServerResponse getDay(String openId) {
        User user = selectUserByOpenId(openId);
        //if user is not exist
        if (user == null) {
            throw new GlobalException(CodeMsg.NOT_EXIST);
        }
        Duration duration = Duration.between(user.getCreateTime(), LocalDateTime.now());
        long days = duration.toDays();
        Map map = new HashMap();
        map.put("day", days);
        return ServerResponse.success("获取用户信息成功", map);
    }

    public ServerResponse changePassword(String username, String oldPassword, String newPassword, String openId) {

        User user = findUserByOpenId(openId);
        if (user == null) {
            throw new GlobalException(CodeMsg.NOT_EXIST);
        }
        if (!(username.equals(user.getUsername()) && oldPassword.equals(user.getPassword()))) {
            throw new GlobalException(CodeMsg.INVALID);
        }
        //ok
        user.setPassword(newPassword);
        userMapper.updateById(user);
        return ServerResponse.success("密码更新成功");
    }

    public ServerResponse forgetPassword(String openId, String answer) {
        User user = findUserByOpenId(openId);
        //if user is not exist
        if (user == null) {
            throw new GlobalException(CodeMsg.NOT_EXIST);
        }
        //if answer is wrong
        if (!answer.equals(user.getAnswer())) {
            throw new GlobalException(CodeMsg.WRONG_ANSWER);
        }
        //answer is right
        //forget token is 5 minute
        String forgetToken = UUID.randomUUID().toString();
        RedisPoolUtil.setEx(Const.TOKEN_PREFIX + openId, forgetToken, 60 * 5);
        return ServerResponse.success("回答正确");
    }

    public ServerResponse forgetPasswordAndChangePassword(String openId, String forgetToken, String newPassword) {
        User user = findUserByOpenId(openId);
        if (user == null) {
            throw new GlobalException(CodeMsg.NOT_EXIST);
        }
        String value = RedisPoolUtil.get(forgetToken);
        if (value == null) {
            throw new GlobalException(CodeMsg.TOKEN_EXPIRE);
        }
        //if ok
        user.setPassword(newPassword);
        userMapper.updateById(user);
        return ServerResponse.success("找回密码成功");
    }

    public ServerResponse login(String openId, String username, String password) {
        User user = findUserByOpenId(openId);
        if (user == null) {
            throw new GlobalException(CodeMsg.NOT_EXIST);
        }
        if (!(user.getUsername().equals(username) && user.getPassword().equals(password))) {
            throw new GlobalException(CodeMsg.INVALID);
        }
        return ServerResponse.success("登录成功");
    }
}
