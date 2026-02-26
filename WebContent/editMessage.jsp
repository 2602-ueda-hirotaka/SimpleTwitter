<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>つぶやき編集</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="main-contents">
		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="errorMessage">
						<li><c:out value="${errorMessage}" /></li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<form action="editMessage" method="post">
			<!-- どのつぶやきを更新するかをServletに伝えるための隠し項目 -->
			<input name="message_id" value="${message.id}" type="hidden" /> <label
				for="text">いま、どうしてる？</label><br />
			<!-- 現在のつぶやき内容を初期値として表示 -->
			<textarea name="text" cols="100" rows="5" class="tweet-box" id="text"><c:out
					value="${message.text}" /></textarea>
			<br /> <input type="submit" value="更新" /> <a href="./">戻る</a>
		</form>
		<div class="copyright">Copyright(c)Your Name</div>
	</div>
</body>
</html>