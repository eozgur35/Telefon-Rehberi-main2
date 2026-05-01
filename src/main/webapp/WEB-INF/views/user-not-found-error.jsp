<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <title>Kullanıcı Bulunamadı</title>
</head>
<body style="font-family: Arial; background:#f5f7fb;">

<div style="
    width: 520px;
    margin: 120px auto;
    background: white;
    padding: 40px;
    border-radius: 18px;
    text-align: center;
    box-shadow: 0 10px 30px rgba(0,0,0,0.08);
">
    <h1 style="color:#1f2937;">Kullanıcı Bulunamadı</h1>

    <p style="color:#6b7280; font-size:18px;">
        ${message}
    </p>

    <a href="${pageContext.request.contextPath}/forgot-password"
       style="
        display:inline-block;
        margin-top:25px;
        background:#21477d;
        color:white;
        padding:14px 24px;
        border-radius:8px;
        text-decoration:none;
       ">
        Şifre Sıfırlama Sayfasına Dön
    </a>
</div>

</body>
</html>