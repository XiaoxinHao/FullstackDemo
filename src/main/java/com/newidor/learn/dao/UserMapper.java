package com.newidor.learn.dao;

import java.util.List;

import com.newidor.learn.model.User;

public interface UserMapper {
	int deleteByPrimaryKey(String id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	List<User> getAll();

	List<User> getAll2();

	List<User> getAll3();
}