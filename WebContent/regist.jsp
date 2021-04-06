<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="css/register.css">
    <script>
    	function validate(){
    		var pwd1 = document.getElementById("pwd1").value;
    		var pwd2 = document.getElementById("pwd2").value;
    		var submitButton = document.getElementById("submit");
    		var pwdTooltip = document.getElementById("tooltip");
    		if(pwd1===pwd2){
    			submitButton.style.visibility = "visible";
    			pwdTooltip.style.display = "none";
    		}else{
    			submitButton.style.visibility = "hidden";
    			pwdTooltip.style.display = "inline";
    		}
    	}
    </script>
</head>

<body>
    <h2 class="title">Create your account</h2>
    <%
		String msg = (String) request.getAttribute("msg");
		if(msg!=null){
	%>
		<p class="err_msg" style=""><%=msg%></p>
	<%	} %>
    <form class="form_container" id='create' method="post" action="UserManager?method=regist">
        <div class="input_container">
            <label>Male</label>
            <input type="radio" name="gender" value="male" checked />
            <label>Female</label>
            <input type="radio" name="gender" value="female" />
        </div>
        <div class="input_container">
            <label class="big_lable">Login</label>
            <input type="text" name="login" required />
        </div>
        <div class="input_container">
            <label class="big_lable">Password</label>
            <input type="password" name="pwd" id="pwd1" onkeyup="validate()" required />
        </div>
		<div class="input_container">
            <label class="big_lable">Confirm your password</label>
            <input id="pwd2" type="password" onkeyup="validate()" required />
        </div>
        <span id="tooltip">Please check your password.</span>
        <div class="input_container">
            <label class="big_lable">First Name</label>
            <input type="text" name="first name" required />
        </div>
        <div class="input_container">
            <label class="big_lable">Last Name</label>
            <input type="text" name="last name" required />
        </div>
        <div class="input_container">
            <label class="big_lable">Email</label>
            <input type="email" name="email" required />
        </div>
        <div class="actions">
            <button onclick="window.location.href='/Devoir2/login.jsp'">Cancel</button>
            <button type="submit" id="submit" form="create">Confirm</button>
        </div>
    </form>
</body>

</html>