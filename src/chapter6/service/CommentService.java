package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import chapter6.beans.UserComment;
import chapter6.dao.CommentDao;
import chapter6.logging.InitApplication;

public class CommentService {

	/**
	 * ロガーインスタンスの生成
	 */
	Logger log = Logger.getLogger("twitter");

	/**
	 * デフォルトコンストラクタ
	 * アプリケーションの初期化を実施する。
	 */
	public CommentService() {
		InitApplication application = InitApplication.getInstance();
		application.init();
	}

	// 返信を登録するメソッド
	public void insert(int messageId, int userId, String text) {
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		Connection connection = null;
		try {
			// 1. DBを利用できる状態にする（接続情報の用意）
			connection = getConnection();

			// 2. Daoを呼び出して実際にDBへ登録する
			new CommentDao().insert(connection, messageId, userId, text);

			// 3. 問題がなければコミット（確定）する
			commit(connection);
		} catch (RuntimeException e) {
			// エラーが起きたらロールバック（取り消し）する
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
	public List<UserComment> selectAll() {
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		Connection connection = null;
		try {
			connection = getConnection();
			// Daoの無条件全件取得を呼び出す
			List<UserComment> comments = new CommentDao().selectAll(connection);
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