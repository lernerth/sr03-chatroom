<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	import="services.DataService,services.impl.DataServiceImpl,models.User"%>

<%
//consulter cookie
Cookie[] cookies = request.getCookies();
if (cookies != null) {
	String uid = "";
	for (Cookie c : cookies) {
		if ("uid".equals(c.getName())) {
			uid = c.getValue();
		}
	}
	if (!"".equals(uid)) {
		DataService ds = new DataServiceImpl();
		User u = ds.checkUserId(uid);
		if (u != null) {
			//accéder à la page d'accueil
			session.setMaxInactiveInterval(15 * 60);
			session.setAttribute("user", u);
			response.sendRedirect("main.jsp");
			return;
		}
	}
}
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="/Devoir2/css/login.css">
<link rel="stylesheet" href="/Devoir2/css/iconfont.css">
</head>

<body>

	<h2 class="title">Chatroom Login</h2>
	<%
		String msg = (String) request.getAttribute("msg");
		Object defaultLoginObj = request.getAttribute("defaultLogin");
		String defaultLogin = defaultLoginObj == null ? "" : (String) defaultLoginObj;
		if (msg != null) {
	%>
		<p class="err_msg" style=""><%=msg%></p>
	<%}%>
	<form class="form_container" method="post"
		action="UserManager?method=login">
		<div class="input_container">
			<label class="big_lable">Login</label>
			<div class="input_icon">
				<span class="iconfont icon-user"></span> <input type="text"
					name="login" value="<%=defaultLogin%>" required />
			</div>
		</div>
		<div class="input_container">
			<label class="big_lable">Password</label>
			<div class="input_icon">
				<span class="iconfont icon-lock"></span> <input type="password"
					name="pwd" required />
			</div>
		</div>
		<div class="actions">
			<input type="checkbox" name="rememberMe" value="rememberMe" /> <label
				class="small_label">Remember Me</label>
			<button type="submit">Login</button>
		</div>
		<div class="creation_navi_container">
			<label class="small_label">New user?</label>&nbsp; <a
				href="/Devoir2/regist.jsp" class="small_label">Click here to
				register</a>
		</div>
	</form>

</body>

</html>