package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.User;
// ※DB接続やトランザクション処理を行うCommentServiceを作成しておく必要があります（MessageServiceを参考に作成してください）
import chapter6.service.CommentService;

// 要件: 呼び出す際のURLは「/comment」とすること
@WebServlet(urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        List<String> errorMessages = new ArrayList<String>();

        String text = request.getParameter("text");
        // 要件: 紐づいているつぶやきのIDを受け取る
        int messageId = Integer.parseInt(request.getParameter("message_id"));

        if (!isValid(text, errorMessages)) {
            session.setAttribute("errorMessages", errorMessages);
            response.sendRedirect("./");
            return;
        }

        User user = (User) session.getAttribute("loginUser");

        // 返信を登録するServiceを呼び出す
        new CommentService().insert(messageId, user.getId(), text);

        // 要件: 返信を登録してトップ画面を再表示する
        response.sendRedirect("./");
    }

    private boolean isValid(String text, List<String> errorMessages) {
        if (StringUtils.isBlank(text)) {
            errorMessages.add("返信を入力してください");
        } else if (140 < text.length()) {
            errorMessages.add("140文字以下で入力してください");
        }
        if (errorMessages.size() != 0) {
            return false;
        }
        return true;
    }
}
