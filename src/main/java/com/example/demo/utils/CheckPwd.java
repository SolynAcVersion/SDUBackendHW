package com.example.demo.utils;

import org.springframework.stereotype.Component;

@Component
public class CheckPwd {
    public ret checkPwd(String pwd) {
        if (pwd == null || pwd.trim().isEmpty()) {
            return new ret(false, "密码不可为空");
        }
        if (pwd.length() < 8) {
            return new ret(false, "密码长度要至少8位");
        }
        if (pwd.length() > 20) {
            return new ret(false, "密码长度要小于等于20位");
        }
        boolean hl = false, hb = false, hc = false;
        for (char c : pwd.toCharArray()) {
            if (Character.isLetter(c)) {
                hl = true;
            } else if (Character.isDigit(c)) {
                hb = true;
            } else {
                hc = true;
            }
        }
        int byd = 0;
        if (hl) byd++;
        if (hb) byd++;
        if (hc) byd++;

        if (byd < 2) {
            return new ret(false, "密码需包含至少字母、数字、特殊字符中的2种");
        }
        return new ret(true, "密码强度符合要求");
    }

    public static class ret {
        private boolean isok;
        private String msg;
        private Integer byd;

        public ret(boolean isok, String msg) {
            this(isok, msg, null);
        }

        public ret(boolean isok, String msg, Integer byd) {
            this.isok = isok;
            this.msg = msg;
            this.byd = byd;
        }

        public boolean isOk() {
            return isok;
        }

        public String getMsg() {
            return msg;
        }
    }
}