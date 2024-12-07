package com.flightmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flightmate.libs.builders.FeedbackBuilder;
import com.flightmate.libs.builders.UserBuilder;
import com.flightmate.beans.Feedback;

import javax.servlet.http.HttpServletRequest;

public class FeedbackDao {
	private static FeedbackDao dao;
	private final String FEEDBACK_ID = "feedback_id";
	private final String FEEDBACK_TYPE = "feedback_type";
	private final String FEEDBACK_DATE = "feedback_date";
	private final String FEEDBACK_COMMENT = "feedback_comment";
	private final String FEEDBACK_HAS_READ = "has_read";
	
	private FeedbackDao() {};
	
	public static synchronized FeedbackDao getDao() {
		if (dao == null) dao = new FeedbackDao();
		return dao;
	}
	
	public void createFeedback(HttpServletRequest req, int userId) {
		String feedbackType = req.getParameter(FEEDBACK_TYPE);
		String feedbackComment = req.getParameter(FEEDBACK_COMMENT);
		
		String sql = "INSERT INTO " + ApplicationDao.FEEDBACK_TABLE + "("+FEEDBACK_TYPE+","+FEEDBACK_COMMENT+","+UserDao.USER_ID+") VALUES (?, ?, ?);";
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
		
			stmt.setString(1, feedbackType);
			stmt.setString(2, feedbackComment);
			stmt.setInt(3, userId);
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Feedback getFeedbackById(int feedbackId) {
		Feedback feedback = null;
		String sql = "SELECT * FROM "+ApplicationDao.FEEDBACK_TABLE+" INNER JOIN "+ ApplicationDao.USERS_TABLE +" ON "+ApplicationDao.FEEDBACK_TABLE+"."+UserDao.USER_ID+" = "+ApplicationDao.USERS_TABLE+"."+UserDao.USER_ID+" WHERE "+FEEDBACK_ID+" = ?;";
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
				stmt.setInt(1, feedbackId);
				ResultSet rs = stmt.executeQuery();
				
				if (rs != null && rs.next()) {
					feedback = new FeedbackBuilder()
							.setFeedbackId(rs.getInt(FEEDBACK_ID))
							.setFeedbackType(rs.getString(FEEDBACK_TYPE))
							.setFeedbackComment(rs.getString(FEEDBACK_COMMENT))
							.setHasRead(rs.getBoolean(FEEDBACK_HAS_READ))
							.setUser(new UserBuilder()
									.setUserId(rs.getInt(UserDao.USER_ID))
									.setFirstName(rs.getString(UserDao.FIRST_NAME))
									.setLastName(rs.getString(UserDao.LAST_NAME))
									.setEmail(rs.getString(UserDao.EMAIL_ADDRESS))
									.setRoleId(rs.getInt(UserDao.ROLE_ID))
									.create())
							.setFeedbackDate(rs.getDate(FEEDBACK_DATE).toLocalDate())
							.create();
				}
				
			} catch (SQLException e) {
				DBUtil.processException(e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
		return feedback;
	}
	
	public List<Feedback> getAllFeedback() {
		List<Feedback> feedbackList = new ArrayList<>();
		String sql = "SELECT * FROM "+ApplicationDao.FEEDBACK_TABLE+" INNER JOIN "+ ApplicationDao.USERS_TABLE +" ON "+ApplicationDao.FEEDBACK_TABLE+"."+UserDao.USER_ID+" = "+ApplicationDao.USERS_TABLE+"."+UserDao.USER_ID+" ORDER BY "+ApplicationDao.FEEDBACK_TABLE+"."+FEEDBACK_DATE+" DESC, "+ApplicationDao.FEEDBACK_TABLE+"."+FEEDBACK_HAS_READ+" ASC;";
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
			) {
			while (rs.next()) {
				Feedback feedback = new FeedbackBuilder()
						.setFeedbackId(rs.getInt(FEEDBACK_ID))
						.setFeedbackType(rs.getString(FEEDBACK_TYPE))
						.setFeedbackComment(rs.getString(FEEDBACK_COMMENT))
						.setHasRead(rs.getBoolean(FEEDBACK_HAS_READ))
						.setUser(new UserBuilder()
								.setUserId(rs.getInt(UserDao.USER_ID))
								.setFirstName(rs.getString(UserDao.FIRST_NAME))
								.setLastName(rs.getString(UserDao.LAST_NAME))
								.setEmail(rs.getString(UserDao.EMAIL_ADDRESS))
								.setRoleId(rs.getInt(UserDao.ROLE_ID))
								.create())
						.setFeedbackDate(rs.getDate(FEEDBACK_DATE).toLocalDate())
						.create();
				feedbackList.add(feedback);
			}
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return feedbackList;
	}
	
	public boolean setFeedbackReadStatusById(boolean readStatus, int feedbackId) {
		boolean updated = false;
		String sql = "UPDATE "+ApplicationDao.FEEDBACK_TABLE+" SET "+FEEDBACK_HAS_READ+" = ? WHERE "+FEEDBACK_ID+" = ?;";
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			) {
			stmt.setBoolean(1, readStatus);
			stmt.setInt(2, feedbackId);
			
			updated = stmt.executeUpdate() > 0;
						
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return updated;
	}
}
