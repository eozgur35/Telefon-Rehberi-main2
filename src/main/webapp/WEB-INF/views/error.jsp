<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="error.title" /></title>

    <style>
        body { margin: 0; font-family: "Segoe UI", Arial, sans-serif; background: #f7f9fc; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .card { text-align: center; background: #ffffff; padding: 40px; border-radius: 16px; box-shadow: 0 10px 25px rgba(0,0,0,0.08); width: 520px; max-width: 90%; }
        .icon { font-size: 60px; margin-bottom: 10px; }
        h1 { font-size: 22px; margin: 10px 0; color: #2c3e50; }
        .subtitle { color: #7f8c8d; margin-bottom: 20px; }
        .tag { display: inline-block; background: #eef2f7; padding: 5px 12px; border-radius: 8px; font-size: 12px; margin-bottom: 15px; }
        .info { font-size: 13px; color: #555; margin: 5px 0; }
        ul { text-align: left; margin-top: 10px; }
        li { color: #e74c3c; font-size: 13px; }
        .btn { margin-top: 20px; display: inline-block; padding: 10px 16px; background: #3498db; color: white; text-decoration: none; border-radius: 8px; }
        .btn:hover { background: #2980b9; }
    </style>
</head>

<body>

<div class="card">
    <div class="icon">🛠️</div>

    <h1><spring:message code="error.main" /></h1>

    <div class="subtitle">
        <spring:message code="error.subtitle" />
    </div>

    <div class="tag">${errorCode} - ${errorType}</div>

    <p class="info"><b><spring:message code="error.message" />:</b> ${errorMessage}</p>
    <p class="info"><b><spring:message code="error.page" />:</b> ${path}</p>
    <p class="info"><b><spring:message code="error.timestamp" />:</b> ${timestamp}</p>

    <c:if test="${not empty validationErrors}">
        <h3><spring:message code="error.validation.title" /></h3>
        <ul>
            <c:forEach var="entry" items="${validationErrors}">
                <li>${entry.key} → ${entry.value}</li>
            </c:forEach>
        </ul>
    </c:if>

    <a class="btn" href="/"><spring:message code="error.home" /></a>
</div>

</body>
</html>