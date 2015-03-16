package com.epweike.service;

import com.epweike.model.Users;
import com.github.abel533.mapper.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxp
 */
@Service
public class UsersService extends BaseService<Users> {

	@Autowired
	private Mapper<Users> usersMapper;
}
