<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	import="services.*,services.impl.DataServiceImpl,models.*,java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
if (session.getAttribute("user") == null) {
	response.sendRedirect("login.jsp");
	return;
}
User u = (User) session.getAttribute("user");
String roomName = request.getParameter("roomName");
DataService sd = new DataServiceImpl();
Chat c = (Chat) sd.findChat(roomName);
User owner = (User) sd.findOwner(c);
List<User> otherUsers = sd.findUsersNotInChat(roomName);
List<User> users = sd.findUsersInChat(roomName);
pageContext.setAttribute("otherUsers", otherUsers);
pageContext.setAttribute("users", users);
pageContext.setAttribute("u", u);
%>


<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Chat Room</title>
    <script type="text/javascript" src="chatroom.js"></script>
    <link rel="stylesheet" type="text/css" href="./css/chatroom.css" />
</head>

<body>

    <header>
        <a href="./main.jsp" id="btnBack">&lt;&lt;Back</a>
            <span id="roomName"><%=roomName %></span>
        <span class="room_info">Created <%=c.getDateStr() %>, by <%=owner.getLogin() %></span>
    </header>

    <div class="main">
        <article>
            <textarea id="history" readonly>
            </textarea>

        </article>

        <aside>
            <span class="title desc"><%=c.getDesc()%></span>
            <span class="title">Users on line</span>
            <div class="user ownself">
                <div class="avatar"><%=u.getLogin().substring(0, 1).toUpperCase() %></div>
                <div id="userLogin" class="user_info">
                	<span class="user_login"><%=u.getLogin() %></span>
                	<span><%=u.getFname() %></span>
                	<span><%=u.getLname() %></span>
                </div>
            </div>

            <c:forEach items="${users}" var="user">
            	<c:if test="${user.getId() != u.getId() }">
                	<div class="user">
                    	<div class="avatar">${user.getLogin().substring(0, 1).toUpperCase()}</div>
                    	<div class="user_info">
                    		<span class="user_login">${user.getLogin()}</span>
                    		<span>${user.getFname()}</span>
                    		<span>${user.getLname()}</span>
                    	</div>
                	</div>
            	</c:if>
            </c:forEach>
        </aside>
    </div>

    <div class="actions">
        <div class="msg_send">
            <input id="txtMessage" type="text" />
            <button id="btnSend">Send message</button>
        </div>
        <%
            String msg = (String) request.getAttribute("msg");
            if(msg != null){
        %>
            <p class="err_msg"><%=msg%></p>
        <%	} %>
        <div class="user_invite">
            <form method="post" action="ChatManager?method=invite">
                <input type="hidden" value="" name="roomName" />
                <label class="invite_title">Invite users : </label>
                <select name="invitedUserIds">
	                <c:forEach items="${otherUsers}" var="user">           		
	                	<option value="${user.getId()}">${user.getLogin()}</option>
	                </c:forEach>
                </select>
                <input type="submit" value="Invite" id="btnInvite">
            </form>
        </div>
    </div>
</body>

</html>