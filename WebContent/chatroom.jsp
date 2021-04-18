<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	import="services.DataService,services.impl.DataServiceImpl,models.User,java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
if (session.getAttribute("user") == null) {
	response.sendRedirect("login.jsp");
	return;
}
User u = (User) session.getAttribute("user");
String roomName = request.getParameter("roomName");
DataService sd = new DataServiceImpl();
List<User> otherUsers = sd.findUsersNotInChat(roomName);
List<User> users = sd.findUsersInChat(roomName);
pageContext.setAttribute("otherUsers", otherUsers);
pageContext.setAttribute("users", users);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chat Room</title>
        <script type="text/javascript" src="chatroom.js"></script>
        <link rel="stylesheet" type="text/css" href="/Devoir2/css/chatroom.css"/>
    </head>
    <body>
    	<%
			String msg = (String) request.getAttribute("msg");
			if(msg != null){
		%>
			<p class="err_msg"><%=msg%></p>
		<%	} %>
		
    	<h1 id="roomName"><%=roomName %></h1>
    	<h1 id="userLogin"><%=u.getLogin() %></h1>
        <textarea id="history" readonly></textarea>
        <input id="txtMessage" type="text" />
        <button id="btnSend">Send message</button>
        <button id="btnClose">Close connection</button>
        <button id="btnInvite" onclick="handleChange()">Invite users</button>
        <div class="invitation_container" id="blockInvitation">
            <form method="post" action="ChatManager?method=invite">
            	<input type="hidden" value="<%=roomName%>" name="roomName"/>
                <label class="invite_title">Invite users</label>
                <select name="invitedUserIds" multiple>
	                <c:forEach items="${otherUsers}" var="user">
	                	<option value="${user.getId()}">${user.getLogin()}</option>
	                </c:forEach>
                </select>
                <input type="submit" value="Invite">
            </form>
        </div>
    </body>
</html>