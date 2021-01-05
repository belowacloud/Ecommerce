<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    </head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="menu.jsp"></jsp:include>

<h3>Login Page</h3>
<p style="color: #ff0000;">${errorString}</p>


<form method="POST" action="${pageContext.request.contextPath}/login">
<table border="0">
<tr>
<td>User Name</td>
 <td><input type="text" name="userName" value= "${user.userName}" /> </td>
 </tr>
 <tr>
 <td>Password</td>
 <td><input type="text" name="password" value= "${user.password}" /> </td>
 </tr>
 <tr>
 <td>Remember me</td>
 </tr>
 <tr>
 <td colspan ="2">
 <input type="submit" value= "Submit" />
 <a href="${pageContext.request.contextPath}/">Cancel</a>
 </td>
 </tr>
</table>
</form>


<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>