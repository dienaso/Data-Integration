package com.epweike.service;

import java.util.List;

import com.epweike.mapper.UsersMapper;
import com.epweike.model.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxp
 */
@Service
public class UsersService extends BaseService<Users> {

	@Autowired
	private UsersMapper mapper;
	
	public List<Users> select() {
		return mapper.selectUsers();
	}
}
