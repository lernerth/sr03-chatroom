<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	import="services.DataService,services.impl.DataServiceImpl,models.User"%>
<%
if (session.getAttribute("user") == null) {
	response.sendRedirect("login.jsp");
	return;
}
User u = (User) session.getAttribute("user");
String roomName = request.getParameter("roomName");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chat Room</title>
        <script type="text/javascript" src="chatroom.js"></script>
        <style>
            #history {
                display: block;
                width: 500px;
                height: 300px;
            }

            #txtMessage {
                display: inline-block;
                width: 300px;
            }

            #btnSend {
                display: inline-block;
                width: 180px;
            }

            #btnClose {
                display: block;
                width: 500px;
            }
        </style>
    </head>
    <body>
    	<h1 id="roomName"><%=roomName %></h1>
    	<h1 id="userLogin"><%=u.getLogin() %></h1>
        <textarea id="history" readonly></textarea>
        <input id="txtMessage" type="text" />
        <button id="btnSend">Send message</button>
        <button id="btnClose">Close connection</button>
    </body>
</html>