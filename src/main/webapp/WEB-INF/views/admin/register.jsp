
<%--
  Created by IntelliJ IDEA.
  User: arek
  Date: 02.06.2022
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post">
    <div><label> User email : <input type="text" name="email"/> </label></div>
    <div><label> First name : <input type="text" name="firstName"/> </label></div>
    <div><label> Last name : <input type="text" name="lastName"/> </label></div>
    <div><label> Nick name : <input type="text" name="nickName"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>