package com.example.demo.service;

import com.example.demo.data.dto.AuthDTO;
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
public class GameService {

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


    boolean isExisted(String userName) {
        Integer count = userMapper.getUserId(userName);
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



    @Transactional
    public ResponseEntity<Result> register(AuthDTO registerReqDTO) {
        String username = registerReqDTO.getUsername();
        String password = registerReqDTO.getPassword();
        if (isExisted(username)) {
            throw new RuntimeException("用户已存在");
        }

        CheckPwd.ret res = cp.checkPwd(password);
        if (!res.isOk()) {
            return Result.error(400, res.getMsg());
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(BcryptUtils.encrypt(password));
        userMapper.insert(user);
        return Result.success(getToken(user), "注册成功");
    }


}
