package com.niit.collaborativebackend.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaborativebackend.model.Friend;
@Repository("friendDao")
public class FriendDAOImpl implements FriendDAO {
	private static final Logger log = LoggerFactory.getLogger(FriendDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	@Transactional
	public List<Friend> getAllFriends() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from Friend").list();
	}

}
