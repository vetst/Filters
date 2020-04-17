<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h2>Вход</h2><br>
<center><a href="<c:url value='/logout' />">Logout</a></center>
<form action="login" method="POST">
    <p><input type="text"  name="name" placeholder="Name" required></p>
    <p><input type="password"  name="password" placeholder="Password" required></p>
    <input type="submit" value="Submit"/></form>
</body>
</html>