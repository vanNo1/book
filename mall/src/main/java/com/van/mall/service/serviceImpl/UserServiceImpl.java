package com.van.mall.service.serviceImpl;

import com.van.mall.common.Const;
import com.van.mall.common.ServerResponse;
import com.van.mall.common.TokenCache;
import com.van.mall.dao.UserMapper;
import com.van.mall.entity.User;
import com.van.mall.service.IUserService;
import com.van.mall.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Van
 * @date 2020/3/9 - 20:28
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public int checkUser(String userName) {
        Map<String ,Object>map=new HashMap<>();
        map.put("username",userName);
        List<User>users =userMapper.selectByMap(map);
       if (users.isEmpty()){
           return 0;
       }else
           return 1;
    }

    @Override
    public int checkAnswer(String username, String question, String answer) {
        Map<String ,Object>map=new HashMap<>();
        map.put("username",username);
        map.put("question",question);
        map.put("answer",answer);
        List<User>users=userMapper.selectByMap(map);
        return users.size();
    }

    @Override
    public String selectQuestionByUsername(String username) {
        Map<String ,Object>map=new HashMap<>();
        map.put("username",username);
       List<User>users=userMapper.selectByMap(map);
       return users.get(0).getQuestion();
    }

    @Override
    public int checkEmail(String email) {
        Map<String ,Object>map=new HashMap<>();
        map.put("email",email);
        List<User>users =userMapper.selectByMap(map);
        if (users.isEmpty()){
            return 0;
        }else
            return 1;
    }

    @Override
    public User findUser(String username, String password) {
        Map<String ,Object>map=new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        List<User>users=userMapper.selectByMap(map);
        if (users.isEmpty()){
            return null;
        }else{
            User user=users.get(0);
            user.setPassword("");//set password is empty............
            return user;
        }
    }

    @Override
    public ServerResponse login(String username, String password) {
        //if user is not exist
        if (checkUser(username)==0){
            return ServerResponse.error("can't find user!");
        }//if password is wrong..................
        //get MD5 password.....................
        String md5password=MD5Util.getMD5(password);
        if (findUser(username,md5password)==null){
            return ServerResponse.error("password is wrong!");
        }
        //find user...................
        return ServerResponse.success(findUser(username,password));

    }

    @Override
    public ServerResponse register(User user) {
        //if username is exist
        ServerResponse response=checkValid(user.getUsername(),Const.USERNAME);
        if (!response.isSuccess()){
            return response;
        }
        //if email is exist
        response=checkValid(user.getEmail(),Const.EMAIL);
        if (!response.isSuccess()){
            return response;
        }
        //make sure user's password in database can't be seen.......
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        //set role
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //insert user
        int count= userMapper.insert(user);
        //if db insert have error..............
        if (count==0){
            return ServerResponse.error("用户添加错误");
        }
        return ServerResponse.success(user);
    }

    @Override
    public ServerResponse checkValid(String str, String type) {
        if (type==""||type==" "){
            return ServerResponse.error("参数错误");
        }
        if (type.equals(Const.EMAIL)){
            //if email is exist
            if (checkEmail(str)>0)
            {
                return ServerResponse.error("该邮箱已绑定");
            }
        }
        if (type.equals(Const.USERNAME)){
            //if username is exist
            if (checkUser(str)>0){
                return ServerResponse.error("用户已存在");
            }
        }
        return ServerResponse.success(null);
    }

    @Override
    public ServerResponse selectQuestion(String username) {
        ServerResponse response=checkValid(username,Const.USERNAME);
        if (response.isSuccess()){
            return ServerResponse.error("无该用户");
        }
        String question=selectQuestionByUsername(username);
        if (StringUtils.isEmpty(question)){
            return ServerResponse.error("找回密码问题是空的");
        }
        return ServerResponse.success(question);
    }

    @Override
    public int updatePasswordByUsername(String username,String newPassword) {
        String MD5password=MD5Util.getMD5(newPassword);
        Map<String ,Object>map=new HashMap<>();
        map.put("username",username);
        List<User>users=userMapper.selectByMap(map);
        User newUser=users.get(0);
        newUser.setPassword(MD5password);
        int count= userMapper.updateById(newUser);
        return count;
    }

    @Override
    public ServerResponse returnToken(String username, String question, String answer) {
        if (checkAnswer(username,question,answer)>0){
            //user can get token
            String forgetToken= UUID.randomUUID().toString();
            TokenCache.setKey("token_"+username,forgetToken);
            return ServerResponse.success(forgetToken);
        }
        return ServerResponse.error("回答错误");
    }

    @Override
    public ServerResponse forgetResetPassword(String username, String newPassword, String forgetToken) {

        ServerResponse response=checkValid(username,Const.USERNAME);
        if (response.isSuccess()){
            return ServerResponse.error("无该用户");
        }
        String token=TokenCache.getKey(forgetToken);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(token)){
            return ServerResponse.error("token无效或过期");
        }
        if (org.apache.commons.lang3.StringUtils.equals(forgetToken,token)){
            //reset password................
            int count=updatePasswordByUsername(username,newPassword);
            if (count>0){
                return ServerResponse.success("修改密码成功");
            }else {
                return ServerResponse.error("用户更新错误");
            }
        }
        return ServerResponse.error("token不一致");
    }

    @Override
    public ServerResponse resetPassword(String passwordNew, String passwordOld, User user) {
      String passwordOldMD5=MD5Util.getMD5(passwordOld);
        if (!user.getPassword().equals(passwordOldMD5)){
            return ServerResponse.error("旧密码错误");//if user put a wrong password
        }
        user.setPassword(MD5Util.getMD5(passwordNew));
        int count= updatePasswordByUsername(user.getUsername(),passwordNew);//this method can make MD5 inside
        if (count>0){
            return ServerResponse.success("改密成功");
        }
        return ServerResponse.error("修改密码错误");
    }

    @Override
    public int updateUserInfomation(User user) {
       return userMapper.updateById(user);
    }

    @Override
    public ServerResponse getInformatino(int id) {
        User user =userMapper.selectById(id);
        if (user==null){
            return ServerResponse.error("用户不存在");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.success(user);
    }

    //backend
    //if this user is a admin

    @Override
    public ServerResponse checkAdminRole(User user) {
        if (user!=null&&user.getRole()==Const.Role.ROLE_ADMIN){
            return ServerResponse.success(null);
        }
        return ServerResponse.error("不是管理员");
    }
}

