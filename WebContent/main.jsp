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
	List<Chat> invitedChats = sd.findInvitedChat(u.getId());
	List<Chat> myownChats = sd.findOwnChat(u.getId());

	pageContext.setAttribute("ownChats", myownChats);
	pageContext.setAttribute("invitedChats", invitedChats);
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
			<div class="menu_item" id="create">
                <form>
                    <button type="submit" class="menu_button" formaction="/Devoir2/roomCreation.jsp" formmethod="post">
                        <span class="iconfont icon-create"></span>
                    </button>    
                </form>
                <label class="menu_title">Create</label>
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
        <div class="own_chats">
            <h1 class="chat_type">My Chats</h1>
            <div class="chats_container">
               	<c:forEach items="${ownChats}" var="chat">
                       <div class="chat_item">
                            <a href="/Devoir2/chatroom.jsp?roomName=${chat.getName()}" title="join">${chat.getName()}</a>
                            <form action="/Devoir2/ChatManager?method=delete" method="post">
                                <Button 
                                    class="delete_button"
                                    type="submit"
                                    name="roomName" 
                                    value="${chat.getName()}"
                                    title="delete"
                                >
                                    <span class="iconfont icon-delete"></span>
                                </Button>
                            </form>
                       </div>
               	</c:forEach>
                
            </div>
        </div>

        <div class="invited_chats">
            <h1 class="chat_type">Invited Chats</h1>
            <div class="chats_container">
                <form>
                    <c:forEach items="${invitedChats}" var="chat">
						<div class="chat_item">
                            <a href="/Devoir2/chatroom.jsp?roomName=${chat.getName()}" title="join">${chat.getName()}</a>
                            <form action="/Devoir2/ChatManager?method=delete" method="post">
                                <Button 
                                    class="delete_button"
                                    type="submit"
                                    name="roomName" 
                                    value=${chat.getName()}
                                    title="delete"
                                >
                                    <span class="iconfont icon-delete"></span>
                                </Button>
                            </form>
                       </div>
                	</c:forEach>
                </form>
            </div>
        </div>

    </div>
</body>

</html>