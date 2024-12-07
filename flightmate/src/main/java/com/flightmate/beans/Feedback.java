package com.flightmate.beans;

import java.time.LocalDate;

public class Feedback {
	private int feedbackId;
	private User user;
	private String feedbackType, feedbackComment;
	private boolean hasRead;
	
	private LocalDate feedbackDate;
		
	public Feedback(int feedbackId, String feedbackType, String feedbackComment, LocalDate feedbackDate, boolean hasRead,  User user) {
		this.feedbackId = feedbackId;
		this.feedbackType = feedbackType;
		this.feedbackComment = feedbackComment;
		this.feedbackDate = feedbackDate;
		this.hasRead = hasRead;
		this.user = user;
	}

	public int getFeedbackId() {
		return feedbackId;
	}
	
	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getFeedbackType() {
		return feedbackType;
	}
	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}
	public String getFeedbackComment() {
		return feedbackComment;
	}
	public void setFeedbackComment(String feedbackComment) {
		this.feedbackComment = feedbackComment;
	}
	
	public boolean hasRead() {
		return hasRead;
	}

	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}
	
	public LocalDate getFeedbackDate() {
		return feedbackDate;
	}
	public void setFeedbackDate(LocalDate feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	
}
