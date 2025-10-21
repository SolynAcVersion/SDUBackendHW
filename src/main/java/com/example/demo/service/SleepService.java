package com.example.demo.service;

import com.example.demo.data.po.Sleep;
import com.example.demo.data.vo.Result;
import com.example.demo.mapper.SleepMapper;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SleepService {

    @Resource
    private SleepMapper sleepMapper;

    // 这里的 userId 怎么使用自己想一下
    public ResponseEntity<Result> startSleep(String userId) {
        try {
            Sleep sleep = new Sleep();
            sleep.setStartTime(LocalDateTime.now());
            sleepMapper.insert(sleep);
        } catch (Exception e) {
            return Result.error(500, "睡觉失败");
        }
        return Result.ok();
    }

    public ResponseEntity<Result> endSleep(String userId) {
        try {
            Sleep sleep = new Sleep();
            sleep.setEndTime(LocalDateTime.now());
            sleep.setId(1);
            sleepMapper.updateById(sleep);
        } catch (Exception e) {
            return Result.error(500, "起床失败");
        }
        return Result.ok();
    }

    public ResponseEntity<Result> sleepTime(String userId) {
        try {
            Sleep sleep = sleepMapper.selectById(1);
            Thread.sleep(5000);
            long time = Duration.between(sleep.getStartTime(), sleep.getEndTime()).getSeconds();
            return Result.success(time, "获取成功");
        } catch (Exception e) {
            return Result.error(500, "获取失败");
        }
    }
}
