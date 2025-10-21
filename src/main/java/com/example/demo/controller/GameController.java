package com.example.demo.controller;

import com.example.demo.data.dto.AuthDTO;
import com.example.demo.data.vo.Result;
import com.example.demo.service.GameService;
import com.example.demo.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/game")
public class GameController {

    @Resource
    private GameService gameService;

    @PostMapping("/update")
    public ResponseEntity<Result> register(@RequestBody AuthDTO registerReqDTO) {
        return gameService.register(registerReqDTO);
    }


}
