package com.example.demo.controller;

import com.example.demo.annotation.Auth;
import com.example.demo.data.po.Sleep;
import com.example.demo.data.vo.Result;
import com.example.demo.service.SleepService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/sleep")
public class SleepController {

    @Resource
    private SleepService sleepService;
    @Resource
    private HttpServletRequest request;

    @Auth
    @PostMapping("/start")
    public ResponseEntity<Result> startSleep() {
        String userId = (String) request.getAttribute("userId");
        return sleepService.startSleep(userId);
    }

    @Auth
    @PostMapping("/end")
    public ResponseEntity<Result> endSleep() {
        String userId = (String) request.getAttribute("userId");
        return sleepService.endSleep(userId);
    }

    @Auth
    @GetMapping("/time")
    public ResponseEntity<Result> sleepTime() {
        String userId = (String) request.getAttribute("userId");
        return sleepService.sleepTime(userId);
    }
}
