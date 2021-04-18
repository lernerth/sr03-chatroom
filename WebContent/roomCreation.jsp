<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
     import="services.DataService,services.impl.DataServiceImpl,models.*,java.util.*"%>
    
<%
	if(session.getAttribute("user")==null){
		response.sendRedirect("login.jsp");
		return;
	}
	User u = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Create a room</title>
</head>
<body>
	<h1>Create a chat room</h1>
	<%
		String msg = (String) request.getAttribute("msg");
		if(msg!=null){
	%>
		<p class="err_msg"><%=msg%></p>
	<%	} %>
    <form class="form_container" id='create' method="post" action="ChatManager?method=create">
        <div class="input_container">
            <label class="big_lable">Room Name</label>
            <input type="text" name="roomName" required />
        </div>
        <div class="input_container">
            <label class="big_lable">Owner</label>
            <input type="text" name="Owner" value="<%=u.getLogin()%>" disabled/>
        </div>
        
        <div class="actions">
            <button onclick="window.location.href='/Devoir2/main.jsp'">Cancel</button>
            <button type="submit" form="create">Confirm</button>
        </div>
    </form>
</body>
</html>