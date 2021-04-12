<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    import="services.LoginService,services.impl.LoginServiceImpl,models.User"%>
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
</body>

</html>