package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/editMessage" })
public class EditMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger log = Logger.getLogger("twitter");

	public EditMessageServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();
	}

	// 編集ボタンが押された時（編集画面を表示する処理）
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		// URLパラメータを文字列として受け取る
		String messageIdStr = request.getParameter("message_id");
		int messageId = 0;

		try {
			// 文字列を数値に変換してみる
			messageId = Integer.parseInt(messageIdStr);
		} catch (NumberFormatException e) {
			// 数字以外が入力されて変換エラーになった場合の処理
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add("不正なパラメータが入力されました");

			// エラーメッセージをセッションにセットしてトップ画面へリダイレクト
			request.getSession().setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}
		// DBから最新の情報を取得する
		Message message = new MessageService().select(messageId);

		// データが存在しなかった場合のエラー処理
		if (message == null) {
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add("不正なパラメータが入力されました");

			// エラーメッセージをセッションにセットしてトップ画面へリダイレクト
			request.getSession().setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}
		// 取得したメッセージを画面に渡して表示
		request.setAttribute("message", message);
		request.getRequestDispatcher("editMessage.jsp").forward(request, response);
	}

	// 更新ボタンが押された時（DBを更新する処理）
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		List<String> errorMessages = new ArrayList<String>();

		int messageId = Integer.parseInt(request.getParameter("message_id"));
		String text = request.getParameter("text");

		// DBから元のメッセージを取得し、新しいテキストをセットする
		Message message = new MessageService().select(messageId);
		message.setText(text);

		// バリデーションチェック（空欄や140文字オーバーでないか）
		if (!isValid(text, errorMessages)) {
			request.setAttribute("errorMessages", errorMessages);
			request.setAttribute("message", message);
			request.getRequestDispatcher("editMessage.jsp").forward(request, response);
			return;
		}

		// DBを更新してトップ画面に戻る
		new MessageService().update(message);
		response.sendRedirect("./");
	}

	// バリデーション用メソッド
	private boolean isValid(String text, List<String> errorMessages) {
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		if (StringUtils.isBlank(text)) {
			errorMessages.add("入力してください");
		} else if (140 < text.length()) {
			errorMessages.add("140文字以下で入力してください");
		}

		if (errorMessages.size() != 0) {
			return false;
		}
		return true;
	}
}