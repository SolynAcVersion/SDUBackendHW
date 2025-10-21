package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.data.po.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserById(String id);

    @Select("SELECT * FROM user WHERE username = #{userName}")
    User getUserByUsername(String username);

    @Select("SELECT id FROM user WHERE username = #{userName}")
    Integer getUserId(String userName);

    @Select("SELECT password FROM user WHERE username = #{userName}")
    String getPassword(String userName);

    @Select("SELECT id FROM user WHERE email = #{emailn}")
    Integer getEmailId(String emailn);

}
