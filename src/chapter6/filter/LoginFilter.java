package chapter6.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.User;

// @WebFilterでこのフィルターを適用するURL（ユーザー編集画面とつぶやき編集画面）を指定
@WebFilter(urlPatterns = { "/setting", "/edit" })
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		// セッションを扱うために、HttpServletRequestなどに変換（キャスト）します
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();

		// 1. セッションからログインユーザーの情報を取得して判定の準備をする
		User loginUser = (User) session.getAttribute("loginUser");

		// 2. ログインしているか判定し、遷移する画面を分岐する
		if (loginUser != null) {
			// 【ログインしている場合】
			// chain.doFilter で リクエストのあった画面（/setting や /edit）にそのまま遷移
			chain.doFilter(request, response);

		} else {
			// 【ログインしていない場合】
			// エラーメッセージを用意してセッションに格納
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add("ログインしてください");
			session.setAttribute("errorMessages", errorMessages);

			// response.sendRedirect で ログイン画面に遷移
			httpResponse.sendRedirect("login");
		}
	}

	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void destroy() {
	}
}