<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Şifre Sıfırlama | <spring:message code="app.name" /></title>
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', system-ui, -apple-system, sans-serif; background: #f5f6f8; min-height: 100vh; display: flex; flex-direction: column; }
        header { background: #1a3a6b; color: #fff; padding: 0 2rem; display: flex; align-items: center; height: 64px; box-shadow: 0 2px 8px rgba(0,0,0,0.18); }
        .header-brand { display: flex; align-items: center; gap: 14px; text-decoration: none; color: inherit; }
        .logo-circle { width: 40px; height: 40px; border-radius: 50%; background: rgba(255,255,255,0.15); border: 1.5px solid rgba(255,255,255,0.4); display: flex; align-items: center; justify-content: center; font-weight: 700; font-size: 15px; }
        .header-brand h1 { font-size: 17px; font-weight: 600; line-height: 1.2; }
        .header-brand span { font-size: 12px; opacity: 0.72; font-weight: 400; }
        main { flex: 1; display: flex; align-items: center; justify-content: center; padding: 2rem 1rem; }
        .login-card { background: #fff; border: 1px solid #e2e4ea; border-radius: 12px; padding: 2.5rem 2rem; width: 100%; max-width: 400px; box-shadow: 0 4px 24px rgba(26,58,107,0.07); }
        .login-card h2 { font-size: 20px; font-weight: 600; color: #1a1a2e; margin-bottom: 6px; }
        .login-card p { font-size: 13.5px; color: #6b7280; margin-bottom: 2rem; }
        .form-group { display: flex; flex-direction: column; gap: 6px; margin-bottom: 1.5rem; }
        .form-group label { font-size: 13px; font-weight: 600; color: #374151; }
        .form-group input { height: 42px; border: 1px solid #d1d5db; border-radius: 8px; padding: 0 14px; font-size: 14px; font-family: inherit; color: #1a1a2e; outline: none; transition: border-color 0.15s, box-shadow 0.15s; }
        .form-group input:focus { border-color: #1a3a6b; box-shadow: 0 0 0 3px rgba(26,58,107,0.1); }
        .btn-login { width: 100%; height: 44px; background: #1a3a6b; color: #fff; border: none; border-radius: 8px; font-size: 15px; font-weight: 600; cursor: pointer; margin-top: 0.5rem; transition: background 0.15s; }
        .btn-login:hover { background: #142d54; }
        .back-link { display: block; text-align: center; margin-top: 1.25rem; font-size: 13px; color: #1a3a6b; text-decoration: none; }
        .back-link:hover { text-decoration: underline; }
        footer { background: #1a3a6b; color: rgba(255,255,255,0.65); text-align: center; padding: 14px 2rem; font-size: 12px; }
        footer span { color: rgba(255,255,255,0.9); font-weight: 500; }
    </style>
</head>
<body>

<header>
    <a href="/" class="header-brand">
        <div class="logo-circle">TR</div>
        <div>
            <h1><spring:message code="app.name" /></h1>
            <span><spring:message code="app.subtitle" /></span>
        </div>
    </a>
</header>

<main>
    <div class="login-card">
        <h2>Şifre Sıfırlama</h2>
        <p>E-posta adresinizi girin, size bir doğrulama kodu (OTP) gönderelim.</p>

        <form action="/forgotten-password-send-otp" method="post">
            <div class="form-group">
                <label for="email">E-posta</label>
                <input type="email" id="email" name="email" placeholder="ornek@kurum.edu.tr" required>
            </div>

            <button type="submit" class="btn-login">OTP Gönder</button>
        </form>

        <a href="/login" class="back-link">
            ← Giriş Sayfasına Dön
        </a>
    </div>
</main>

<footer>
    <span>2026</span> — <spring:message code="footer.project" />
</footer>

</body>
</html>