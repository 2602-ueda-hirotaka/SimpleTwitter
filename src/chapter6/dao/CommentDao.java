package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import chapter6.beans.UserComment;
import chapter6.exception.SQLRuntimeException;

public class CommentDao {
	// 【登録処理】
	public void insert(Connection connection, int messageId, int userId, String text) {
		PreparedStatement ps = null;
		try {
			String sql = "INSERT INTO comments (message_id, user_id, text, created_date, updated_date) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, messageId);
			ps.setInt(2, userId);
			ps.setString(3, text);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<UserComment> selectAll(Connection connection) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT c.id, c.message_id, c.user_id, u.account, u.name, c.text, c.created_date "
					+ "FROM comments c INNER JOIN users u ON c.user_id = u.id "
					+ "ORDER BY c.created_date ASC";
			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			List<UserComment> comments = new ArrayList<UserComment>();
			while (rs.next()) {
				UserComment comment = new UserComment();
				comment.setId(rs.getInt("id"));
				comment.setMessageId(rs.getInt("message_id"));
				comment.setUserId(rs.getInt("user_id"));
				comment.setAccount(rs.getString("account"));
				comment.setName(rs.getString("name"));
				comment.setText(rs.getString("text"));
				comment.setCreatedDate(rs.getTimestamp("created_date"));
				comments.add(comment);
			}
			return comments;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}