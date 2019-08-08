<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <table border="1" align="center" width="100%">
        <tr>
            <th>UserId</th>
            <th>UserName</th>
            <td>UserAddr</td>
        </tr>
        <c:forEach items="${userListKey}" var="user">
            <tr>
                <td>${user.userid}</td>
                <td>${user.username}</td>
                <td>${user.useraddr}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
