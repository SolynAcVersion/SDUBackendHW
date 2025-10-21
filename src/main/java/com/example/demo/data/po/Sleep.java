package com.example.demo.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sleep {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
