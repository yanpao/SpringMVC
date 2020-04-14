<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>测试@ModelAttribute</title>
</head>
<body>
    <h4>Country List</h4>
    <tr th:each="country: ${countries}">
        <td th:text="${country}" />
        <td><br/></td>
    </tr>
    <h4>States</h4>
    <tr th:each="state: ${states}">
        <td th:text="${state}" />
        <td><br/></td>
    </tr>
</body>
</html>