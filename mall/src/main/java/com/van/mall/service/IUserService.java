package com.van.mall.service;

import com.van.mall.common.ServerResponse;
import com.van.mall.entity.User;

/**
 * @author Van
 * @date 2020/3/9 - 20:26
 */
public interface IUserService {
    int checkUser(String userName);
    int updateUserInfomation(User user);
    int checkEmail(String email);
    int checkAnswer(String username,String question,String answer);
    int updatePasswordByUsername(String username,String newPassword);
    String selectQuestionByUsername(String username);
    User findUser(String username,String password);
    ServerResponse login(String username,String password);
    ServerResponse register(User user);
    ServerResponse checkValid(String str,String type);
    ServerResponse selectQuestion(String username);
    ServerResponse returnToken(String username,String question,String answer);
    ServerResponse forgetResetPassword(String username,String newPassword,String forgetToken);
    ServerResponse resetPassword(String passwordNew,String passwordOld,User user);
    ServerResponse getInformatino(int id);
    ServerResponse checkAdminRole(User user);

}
