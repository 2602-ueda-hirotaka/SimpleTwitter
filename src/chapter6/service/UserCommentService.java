package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import chapter6.beans.UserComment;
import chapter6.dao.UserCommentDao;

public class UserCommentService {

	/**
	 * ロガーインスタンスの生成
	 */
	Logger log = Logger.getLogger("twitter");

	public List<UserComment> select() {
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());
		final int LIMIT_NUM = 1000;
		Connection connection = null;
		
		try {
			connection = getConnection();
			// Daoの無条件全件取得を呼び出す
			List<UserComment> comments = new UserCommentDao().select(connection, LIMIT_NUM);

			// ※既存のMessageServiceの実装に合わせるなら、ここで commit(connection); を入れるとより綺麗です。
			commit(connection);

			return comments;
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}
}