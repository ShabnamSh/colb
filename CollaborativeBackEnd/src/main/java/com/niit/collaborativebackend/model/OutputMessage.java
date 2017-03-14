package com.niit.collaborativebackend.model;

import java.util.Date;

public class OutputMessage  extends Message{
private Date time;
    
    private String userid;
    
    public OutputMessage(Message original, Date time, String userid) {
       super(original.getId(), original.getMessage());
       this.time=time;
       this.userid=userid;
        
    }

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
    

}
