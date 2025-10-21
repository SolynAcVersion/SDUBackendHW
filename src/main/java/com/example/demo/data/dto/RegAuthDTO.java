package com.example.demo.data.dto;
import lombok.Data;

import java.security.SecureRandom;

@Data
public class RegAuthDTO{
    private  String username;
    private String password;
    private String email;
    private String profile;
    private String picurl;
}
