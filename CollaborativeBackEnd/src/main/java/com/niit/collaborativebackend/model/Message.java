package com.niit.collaborativebackend.model;

public class Message {
	private int id;
	private String message;
	 private String userid;
	 public Message() {
		    
	  }
	  public Message(int id, String message,String userid) {
		    this.id = id;
		    this.message = message;
		    this.userid=userid;
		  }
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	  

}
