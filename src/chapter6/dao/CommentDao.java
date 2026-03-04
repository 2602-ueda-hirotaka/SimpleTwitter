package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import chapter6.beans.Comment;
import chapter6.exception.SQLRuntimeException;

public class CommentDao {
	// 【登録処理】
	public void insert(Connection connection, Comment comment) {
		PreparedStatement ps = null;
		try {
			// ▼ StringBuilderを使ってSQLを構築
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments ( ");
			sql.append("    message_id, ");
			sql.append("    user_id, ");
			sql.append("    text, ");
			sql.append("    created_date, ");
			sql.append("    updated_date ");
			sql.append(") VALUES ( ");
			sql.append("    ?, ");                  // message_id
			sql.append("    ?, ");                  // user_id
			sql.append("    ?, ");                  // text
			sql.append("    CURRENT_TIMESTAMP, ");  // created_date
			sql.append("    CURRENT_TIMESTAMP ");   // updated_date
			sql.append(")");

			// ▼ sql.toString() で文字列に変換して渡す
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, comment.getMessageId());
			ps.setInt(2, comment.getUserId());
			ps.setString(3, comment.getText());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}