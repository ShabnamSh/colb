package com.niit.collaborative.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaborativebackend.dao.FriendDAO;
import com.niit.collaborativebackend.dao.FriendDAOImpl;
import com.niit.collaborativebackend.dao.UserDAO;
import com.niit.collaborativebackend.model.Friend;
import com.niit.collaborativebackend.model.User;

@RestController
public class FriendController {
	private static final Logger log = LoggerFactory.getLogger(FriendController.class);
	@Autowired
	UserDAO userDao;
	
	@Autowired 
	User user;
	@Autowired
	private FriendDAO frienDao;

	@Autowired
	private Friend friend;

	@GetMapping("/searchAll")
	public ResponseEntity<List<User>> searchUsers() {
		log.debug("starting of search users ...");
		List<User> users = userDao.list();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);

	}

}
