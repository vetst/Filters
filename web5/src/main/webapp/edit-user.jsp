<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h2>Обновить пользователя</h2><br>
<center><a href="<c:url value='/logout' />">Logout</a></center>
<form action="edit" method="POST">
    <input type="hidden" name="userId" value="${userId}">
    <p><input type="text" value="${name}" name="name" placeholder="${name}" required></p>
    <p><input type="text" value="${surName}" name="surName" placeholder="${surName}" required></p>
    <p><input type="text" value="${password}" name="password" placeholder="${password}" required></p>
    <select name="role">
        <option value="null">Выберите роль</option>
        <option value="admin">admin</option>
        <option value="user">user</option>
    </select><br><br>
    <input type="submit" value="Обновить"/></form>
</body>
</html>