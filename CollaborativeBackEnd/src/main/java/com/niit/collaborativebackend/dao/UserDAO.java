package com.niit.collaborativebackend.dao;

import java.util.List;

import com.niit.collaborativebackend.model.User;

public interface UserDAO {
	public List<User> list();

	public User get(String id);
	
	public boolean save(User user);
	public boolean update(User user);

	public boolean delete(User user);
	
	public User isValidUser(String id, String password);
	public boolean saveOrUpdate(User user);

}
