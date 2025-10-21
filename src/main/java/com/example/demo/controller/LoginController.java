package com.example.demo.controller;

import com.example.demo.data.dto.AuthDTO;
import com.example.demo.data.dto.RegAuthDTO;
import com.example.demo.data.vo.Result;
import com.example.demo.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<Result> register(@RequestBody RegAuthDTO registerReqDTO) {
        return loginService.register(registerReqDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<Result> update(@RequestBody RegAuthDTO updateReqDTO) {
        return loginService.update(updateReqDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody AuthDTO loginReqDTO) {
        return loginService.login(loginReqDTO);
    }

    @PostMapping("/deregister")
    public ResponseEntity<Result> deregister(@RequestBody AuthDTO deregisterReqDTO) {
        return loginService.deregister(deregisterReqDTO);
    }


}
