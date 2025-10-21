package com.example.demo.service;

import com.example.demo.data.dto.AuthDTO;
import com.example.demo.data.dto.RegAuthDTO;
import com.example.demo.data.po.User;
import com.example.demo.data.vo.Result;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.BcryptUtils;
import com.example.demo.utils.CheckPwd;
import com.example.demo.utils.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JWTUtil jwtUtil;

    @Resource
    private CheckPwd cp;

    /**
     * 获取token
     *
     * @param user 用户
     * @return Map<String, String>
     */
    private Map<String, String> getToken(User user) {
        String token;
        String refreshToken;
        String id = user.getId().toString();
        refreshToken = jwtUtil.getToken(id, JWTUtil.REFRESH_EXPIRE_TIME, JWTUtil.REFRESH_SECRET_KEY);

        token = jwtUtil.getToken(id, JWTUtil.EXPIRE_TIME, JWTUtil.SECRET_KEY);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", token);
        tokenMap.put("refreshToken", refreshToken);
        tokenMap.put("username", user.getUsername());
        return tokenMap;
    }

    private User getDetail(String userName) {
        return userMapper.getUserById(userName);
    }

    public ResponseEntity<Result> login(AuthDTO loginReqDTO) {
        String username = loginReqDTO.getUsername();
        String password = loginReqDTO.getPassword();
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!BcryptUtils.verifyPasswd(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        return Result.success(getToken(user), "登录成功");
    }


    boolean isExisted(String s) {
        Integer count = userMapper.getUserId(s);
        return count != null && count > 0;
    }
    boolean isEExisted(String s) {
        Integer count = userMapper.getEmailId(s);
        return count != null && count > 0;
    }


    public ResponseEntity<Result> deregister(AuthDTO deregisterReqDTO) {
        String username = deregisterReqDTO.getUsername();
        String password = deregisterReqDTO.getPassword();
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!BcryptUtils.verifyPasswd(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        userMapper.deleteById(user.getId());
        return Result.success(getToken(user), "注销成功");
    }

    public ResponseEntity<Result> update(RegAuthDTO updateReqDTO) {
        String username = updateReqDTO.getUsername();
        String password = updateReqDTO.getPassword();
        User user = userMapper.getUserByUsername(username);
        if (user == null || !BcryptUtils.verifyPasswd(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        User upduser = new User();
        upduser.setId(user.getId());
        upduser.setUsername(username);
        upduser.setPassword(password);
        upduser.setProfile(updateReqDTO.getProfile());
        upduser.setEmail(updateReqDTO.getEmail());
        upduser.setPicture(updateReqDTO.getPicurl());
        upduser.setProfile(updateReqDTO.getProfile());
        if(isEExisted(updateReqDTO.getEmail())){
            throw new RuntimeException("邮箱已被占用");
        }
        userMapper.deleteById(user.getId());
        userMapper.insert(upduser);
        return Result.success(getToken(user), "更改已提交，等待审核");
    }

    @Transactional
    public ResponseEntity<Result> register(RegAuthDTO registerReqDTO) {
        String username = registerReqDTO.getUsername();
        String password = registerReqDTO.getPassword();
        String email = registerReqDTO.getEmail();
        if (isExisted(username)) {
            throw new RuntimeException("用户已存在");
        }
        if(isEExisted(email)) {
            throw new RuntimeException("邮箱已注册");
        }

        CheckPwd.ret res = cp.checkPwd(password);
        if (!res.isOk()) {
            return Result.error(400, res.getMsg());
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(BcryptUtils.encrypt(password));
        user.setEmail(email);
        user.setProfile(registerReqDTO.getProfile());
        user.setPicture(registerReqDTO.getPicurl());
        userMapper.insert(user);
        return Result.success(getToken(user), "注册成功，等待审核中···");
    }


}
