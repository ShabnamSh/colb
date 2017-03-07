package com.niit.collaborativebackend.dao;

import java.util.List;

import com.niit.collaborativebackend.model.Friend;

public interface FriendDAO {
	public List<Friend> getMyFriends(String userid);
	public boolean save(Friend friend);
	public boolean update(Friend friend);
	public void delete(String userid, String friendid);
	public List<Friend> getNewFriendRequests(String friendid);
	public List<Friend> getRequestsSendByMe(String userid);
	public Friend get(String userid, String friendid);
	public Friend get(String userid);
	public void setOnline(String friendid);
	public void setOffLine(String friendid);
}
