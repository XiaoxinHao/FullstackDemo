package com.newidor.learn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newidor.learn.dao.UserMapper;
import com.newidor.learn.model.User;

@Service("userService")
public class UserServiceImpl implements UserServiceI {

	private UserMapper userMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public User getUserById(String id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public List<User> getAll() {
		return userMapper.getAll();
	}

	public List<User> getAll2() {
		return userMapper.getAll2();
	}

	public List<User> getAll3() {
		return userMapper.getAll3();
	}

}
