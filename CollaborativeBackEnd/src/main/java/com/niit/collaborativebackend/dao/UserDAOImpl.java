package com.niit.collaborativebackend.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaborativebackend.model.User;
@Repository("userDao")
public class UserDAOImpl implements UserDAO{
	public UserDAOImpl(){
		
	}
	@Autowired
	private SessionFactory sessionFactory;
	public UserDAOImpl(SessionFactory sessionFactory) {
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			//log.error(" Unable to connect to db");
			e.printStackTrace();
		}
	}
@Transactional
	public List<User> list() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return list;
	}
   @Transactional
	public User get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
   @Transactional
	public boolean save(User user) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().save(user);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
   @Transactional
	public boolean update(User user) {
		// TODO Auto-generated method stub
		return false;
	}
   @Transactional
	public boolean delete(User user) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().delete(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			return true;
	}
   @Transactional
	public User isValidUser(String id, String password) {
		// TODO Auto-generated method stub
		return null;
	}
   @Transactional
	public boolean saveOrUpdate(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
