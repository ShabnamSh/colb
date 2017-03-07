package com.niit.collaborativebackend.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaborativebackend.model.Friend;
@Repository("friendDao")
@Transactional
public class FriendDAOImpl implements FriendDAO {
	private static final Logger log = LoggerFactory.getLogger(FriendDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<Friend> getMyFriends(String userid) {
		// TODO Auto-generated method stub
		/*
		 * select user_id from c_friend where user_id='Shabnam' and status ='A'
		 * UNION select friend_id from c_friend where user_id='Shabnam' and
		 * status ='A' MINUS select user_id from c_friend where
		 * user_id='Shabnam';
		 */
		String hql1 = "select friendid  from Friend where userid='" + userid + "' and status = 'A' ";

				/*+ " union  " +*/

		String hql2= "select userID from Friend where friendID='" + userid + "' and status = 'A'";

		log.debug("getMyFriends hql1 : " + hql1);
		log.debug("getMyFriends hql2 : " + hql2);
	
		List<Friend> list1 = sessionFactory.openSession().createQuery(hql1).list();
		List<Friend> list2 = sessionFactory.openSession().createQuery(hql2).list();
		
		
		
		list1.addAll(list2);

		return list1;
	}
	public boolean save(Friend friend) {
		// TODO Auto-generated method stub
		try {
			log.debug("Starting of save friend  ");
			
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean update(Friend friend) {
		// TODO Auto-generated method stub
		try {
			log.debug("Starting of update friend  ");
			
			sessionFactory.getCurrentSession().update(friend);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public void delete(String userid, String friendid) {
		// TODO Auto-generated method stub
		Friend friend = new Friend();
		friend.setFriendid(friendid);
		friend.setUserid(userid);
		sessionFactory.openSession().delete(friend);
		
	}
	public List<Friend> getNewFriendRequests(String friendid) {
		// TODO Auto-generated method stub
		String hql = "select userid from Friend where friendid=" + "'" + friendid + "' and status ='" + "N'";

		log.debug(hql);
		 return  sessionFactory.openSession().createQuery(hql).list();

	}
	public List<Friend> getRequestsSendByMe(String userid) {
		// TODO Auto-generated method stub
		String hql = "select friendid from Friend where userid=" + "'" + userid + "' and status ='" + "N'";

		log.debug(hql);
		return  sessionFactory.openSession().createQuery(hql).list();

		
	}
	public Friend get(String userid, String friendid) {
		// TODO Auto-generated method stub
		String hql = "from Friend where userid=" + "'" + userid + "' and friendid= '" + friendid + "'";

		log.debug("hql: " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);

		return (Friend) query.uniqueResult();
	}
	public Friend get(String userid) {
		// TODO Auto-generated method stub
		String hql = "from Friend where userid=" + "'" + userid + "' and friendID= '" + userid + "'";

		log.debug("hql: " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);

		return (Friend) query.uniqueResult();
	}
	public void setOnline(String friendid) {
		// TODO Auto-generated method stub
		log.debug("Starting of the metnod setOnline");
		String hql = " UPDATE Friend	SET isOnline = 'Y' where friendid='" + friendid + "'";
		log.debug("hql: " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		query.executeUpdate();
		log.debug("Ending of the metnod setOnline");

		
	}
	public void setOffLine(String friendid) {
		// TODO Auto-generated method stub
		log.debug("Starting of the metnod setOffLine");
		String hql = " UPDATE Friend	SET isOnline = 'N' where friendid='" + friendid + "'";
		log.debug("hql: " + hql);
		Query query = sessionFactory.openSession().createQuery(hql);
		query.executeUpdate();
		log.debug("Ending of the metnod setOffLine");

		
	}
	

}
