<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    import="services.DataService,services.impl.DataServiceImpl,models.*,java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	if(session.getAttribute("user")==null){
		response.sendRedirect("login.jsp");
		return;
	}
	User u = (User) session.getAttribute("user");
	DataService sd = new DataServiceImpl();

	List<Chat> myownChats = sd.findOwnChat(u.getId());

	pageContext.setAttribute("chats", myownChats);
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="/Devoir2/css/main.css">
    <link rel="stylesheet" href="/Devoir2/css/iconfont.css">
    <title>Welcome Page</title>
</head>

<body>
    <div class="menu_container">
        <div class="avatar"><%=u.getLogin().substring(0, 1).toUpperCase()%></div>
        <div class="menu_items_container">
            <div class="menu_item" id="profil">
                <form>
                    <button type="submit" class="menu_button" formaction="/Devoir2/profil.jsp" formmethod="post">
                        <span class="iconfont icon-user"></span>
                    </button>    
                </form>
                <label class="menu_title">Profil</label>
            </div>
            <div class="menu_item" id="logout">
                <form>
                    <button type="submit" class="menu_button" formaction="UserManager?method=logout" formmethod="post">
                        <span class="iconfont icon-logout"></span>
                    </button>    
                </form>
                <label class="menu_title">Logout</label>
            </div>
        </div>
    </div>
    <%
		String msg = (String) request.getAttribute("msg");
		if (msg != null) {
	%>
		<p class="err_msg" style=""><%=msg%></p>
	<%}%>
    <div class="chatrooms_containre">
    	<form action="/Devoir2/roomCreation.jsp" method="post">	
    		<Button type="submit">Create</Button>
    	</form>
        <div class="own_chats">
            <h1 class="chat_type">Mes Chats</h1>
            <div class="chats_container">
                <form>
                	<c:forEach items="${chats}" var="chat">
                		<a href="/Devoir2/chatroom.jsp?roomName=${chat.getName()}">${chat.getName()}</a> <br/>
                	</c:forEach>
                </form>
            </div>
        </div>

        <div class="invited_chats">
            <h1 class="chat_type">Chats Inivtes</h1>
            <div class="chats_container">
                <form>
                    <Button class="chat_link">
    
                    </Button>
                </form>
            </div>
        </div>
        
        <div class="invitations">
            <h1 class="chat_type">Invitations</h1>
            <div class="chats_container">
                <form>
                    <Button class="invitation_link">
    
                    </Button>
                </form>
            </div>
        </div>
    </div>
</body>

</html>