package com.flightmate.libs.builders;

import java.time.LocalDate;

import com.flightmate.beans.Feedback;
import com.flightmate.beans.User;

public class FeedbackBuilder {
	private int feedbackId;
	private User user;
	private String feedbackType, feedbackComment;
	private LocalDate feedbackDate;
	
	public FeedbackBuilder setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
		return this;
	}
	
	public FeedbackBuilder setUser(User user) {
		this.user = user;
		return this;
	}
	
	public FeedbackBuilder setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
		return this;
	}
	
	public FeedbackBuilder setFeedbackComment(String feedbackComment) {
		this.feedbackComment = feedbackComment;
		return this;
	}
	
	public FeedbackBuilder setFeedbackDate(LocalDate feedbackDate) {
		this.feedbackDate = feedbackDate;
		return this;
	}
	
	public Feedback create() {
		return new Feedback(feedbackId, feedbackType, feedbackComment, feedbackDate, user);
	}
}
