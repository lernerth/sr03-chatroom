<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	import="services.DataService,services.impl.DataServiceImpl,models.User"%>
<%
if (session.getAttribute("user") == null) {
	response.sendRedirect("login.jsp");
	return;
}
User u = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>User Profil</title>
</head>
<body>
<%=u.getLogin() %>
</body>
</html>