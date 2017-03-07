package com.niit.collaborative.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	private FriendDAO friendDao;

	@Autowired
	private Friend friend;
	@Autowired
	HttpSession httpSession; 
	

	@RequestMapping(value = "/myFriends", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriends() {
		log.debug("->->->->calling method getMyFriends");
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		List<Friend> myFriends = new ArrayList<Friend>();
		if(loggedInUserID == null)
		{
			friend.setErrorcode("404");
			friend.setErrorMessage("Please login to know your friends");
			myFriends.add(friend);
			return new ResponseEntity<List<Friend>>(myFriends, HttpStatus.OK);
			
		}
       
		log.debug("getting friends of : " + loggedInUserID);
		 myFriends = friendDao.getMyFriends(loggedInUserID);

		if (myFriends.isEmpty()) {
			log.debug("Friends does not exsit for the user : " + loggedInUserID);
			friend.setErrorcode("404");
			friend.setErrorMessage("You does not have any friends");
			myFriends.add(friend);
		}
		log.debug("Send the friend list ");
		return new ResponseEntity<List<Friend>>(myFriends, HttpStatus.OK);
	}
	@RequestMapping(value = "/addFriend/{friendid}", method = RequestMethod.GET)
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendid") String friendID) {
		log.debug("->->->->calling method sendFriendRequest");
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		friend.setUserid(loggedInUserID);
		friend.setFriendid(friendID);
		friend.setStatus("N"); // N - New, R->Rejected, A->Accepted
		friend.setIsOnline('N');
		// Is the user already sent the request previous?
		
		//check whether the friend exist in user table or not
		if(isUserExist(friendID)==false)
		{
			friend.setErrorcode("404");
			friend.setErrorMessage("No user exist with the id :" + friendID);
		}
		
		else

		if (friendDao.get(loggedInUserID, friendID) != null) {
			friend.setErrorcode("404");
			friend.setErrorMessage("You already sent the friend request to " + friendID);

		} else {
			friendDao.save(friend);

			friend.setErrorcode("200");
			friend.setErrorMessage("Friend request successfull.." + friendID);
		}

		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}
	
	
	
	private boolean isUserExist(String id)
	{
		if(userDao.get(id)==null)
			return false;
		else
			return true;
	}
	
	
	private boolean isFriendRequestAvailabe(String friendid)
	{
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		
		if(friendDao.get(loggedInUserID,friendid)==null)
			return false;
		else
			return true;
	}
	@RequestMapping(value = "/unFriend/{friendid}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> unFriend(@PathVariable("friendid") String friendID) {
		log.debug("->->->->calling method unFriend");
		updateRequest(friendID, "U");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	@RequestMapping(value = "/rejectFriend/{friendid}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> rejectFriendFriendRequest(@PathVariable("friendid") String friendID) {
		log.debug("->->->->calling method rejectFriendFriendRequest");

		updateRequest(friendID, "R");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	@RequestMapping(value = "/accepttFriend/{friendid}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> acceptFriendFriendRequest(@PathVariable("friendid") String friendID) {
		log.debug("->->->->calling method acceptFriendFriendRequest");
        
		friend = updateRequest(friendID, "A");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}
	private Friend updateRequest(String friendid, String status) {
		log.debug("Starting of the method updateRequest");
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		log.debug("loggedInUserID : " + loggedInUserID);
		
		if(isFriendRequestAvailabe(friendid)==false)
		{
			friend.setErrorcode("404");
			friend.setErrorMessage("The request does not exist.  So you can not update to "+status);
		}
		
		if (status.equals("A") || status.equals("R"))
			friend = friendDao.get(friendid, loggedInUserID);
		else
			friend = friendDao.get(loggedInUserID, friendid);
		friend.setStatus(status); // N - New, R->Rejected, A->Accepted

		friendDao.update(friend);

		friend.setErrorcode("200");
		friend.setErrorMessage(
				"Request from   " + friend.getUserid() + " To " + friend.getFriendid() + " has updated to :" + status);
		log.debug("Ending of the method updateRequest");
		return friend;

	}

	@RequestMapping(value = "/getMyFriendRequests/", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriendRequests() {
		log.debug("->->->->calling method getMyFriendRequests");
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		List<Friend> myFriendRequests = friendDao.getNewFriendRequests(loggedInUserID);
		return new ResponseEntity<List<Friend>>(myFriendRequests, HttpStatus.OK);

	}
	@RequestMapping("/getRequestsSendByMe")
	public ResponseEntity<List<Friend>>  getRequestsSendByMe()
	{
		log.debug("->->->->calling method getRequestsSendByMe");
		
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		List<Friend> requestSendByMe = friendDao.getRequestsSendByMe(loggedInUserID);
		
		log.debug("->->->->Ending method getRequestsSendByMe");
		
		return new ResponseEntity<List<Friend>>(requestSendByMe, HttpStatus.OK);
		
	}

}
