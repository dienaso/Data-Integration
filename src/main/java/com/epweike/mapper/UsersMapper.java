package com.epweike.mapper;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.epweike.model.Users;

public interface UsersMapper extends Mapper<Users> {
	List<Users> selectUsers();
}