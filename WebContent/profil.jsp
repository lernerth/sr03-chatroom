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
    <link href="./css/profil.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div class="profil_container">
        <h1>User Profil</h1>
        <div class="input_container">
            <label class="big_lable">Login</label>
            <input type="text" name="Login" value=<%=u.getLogin()%> disabled />
        </div>
        <div class="input_container">
            <label class="big_lable">First Name</label>
            <input type="text" name="firstName" value=<%=u.getFname()%> disabled />
        </div>
        <div class="input_container">
            <label class="big_lable">Last Name</label>
            <input type="text" name="lastName" value=<%=u.getLname()%> disabled />
        </div>
        <div class="input_container">
            <label class="big_lable">Email</label>
            <input type="text" name="email" value=<%=u.getEmail()%> disabled />
        </div>
    </div>
    <div class="actions">
        <button onclick="window.location.href='/Devoir2/main.jsp'">Back</button>
    </div>
</body>

</html>