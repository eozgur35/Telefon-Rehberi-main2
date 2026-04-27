<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><spring:message code="app.name" /></title>
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', system-ui, -apple-system, sans-serif; background: #f5f6f8; color: #1a1a2e; min-height: 100vh; display: flex; flex-direction: column; }
        header { background: #1a3a6b; color: #fff; padding: 0 2rem; display: flex; align-items: center; justify-content: space-between; height: 64px; box-shadow: 0 2px 8px rgba(0,0,0,0.18); }
        .header-brand { display: flex; align-items: center; gap: 14px; text-decoration: none; color: inherit; }
        .header-brand .logo-circle { width: 40px; height: 40px; border-radius: 50%; background: rgba(255,255,255,0.15); border: 1.5px solid rgba(255,255,255,0.4); display: flex; align-items: center; justify-content: center; font-weight: 700; font-size: 15px; letter-spacing: -0.5px; }
        .header-brand h1 { font-size: 17px; font-weight: 600; line-height: 1.2; }
        .header-brand span { font-size: 12px; opacity: 0.72; font-weight: 400; }
        .header-actions { display: flex; align-items: center; gap: 15px; }
        .header-actions a { font-size: 13px; color: rgba(255,255,255,0.85); text-decoration: none; border: 1px solid rgba(255,255,255,0.35); padding: 6px 16px; border-radius: 6px; transition: background 0.15s; }
        .header-actions a:hover { background: rgba(255,255,255,0.12); }
        .filter-bar { background: #fff; border-bottom: 1px solid #e2e4ea; padding: 1rem 2rem; display: flex; align-items: flex-start; gap: 1.5rem; flex-wrap: wrap; }
        .filter-group { display: flex; flex-direction: column; gap: 5px; min-width: 220px; }
        .filter-group label { font-size: 11px; font-weight: 600; letter-spacing: 0.06em; text-transform: uppercase; color: #6b7280; }
        .filter-group select, .filter-group input { height: 38px; border: 1px solid #d1d5db; border-radius: 7px; padding: 0 12px; font-size: 14px; font-family: inherit; color: #1a1a2e; background: #fff; outline: none; transition: border-color 0.15s, box-shadow 0.15s; cursor: pointer; }
        .filter-group select:focus, .filter-group input:focus { border-color: #1a3a6b; box-shadow: 0 0 0 3px rgba(26,58,107,0.1); }
        .dept-info { display: flex; align-items: center; gap: 1.5rem; padding: 0.5rem 0; flex-wrap: wrap; }
        .dept-info-item { display: flex; align-items: center; gap: 6px; font-size: 13px; color: #374151; }
        .dept-info-item .lbl { font-weight: 600; color: #1a3a6b; }
        .search-group { flex: 1; min-width: 240px; position: relative; }
        main { flex: 1; padding: 1.5rem 2rem; }
        .table-card { background: #fff; border-radius: 10px; border: 1px solid #e2e4ea; overflow: hidden; }
        .table-header { padding: 14px 18px; border-bottom: 1px solid #e2e4ea; display: flex; align-items: center; justify-content: space-between; }
        .table-header h2 { font-size: 14px; font-weight: 600; color: #374151; }
        .result-count { font-size: 12px; background: #eff6ff; color: #1a3a6b; padding: 3px 10px; border-radius: 20px; font-weight: 500; }
        table { width: 100%; border-collapse: collapse; font-size: 13.5px; }
        thead tr { background: #f8f9fb; }
        thead th { padding: 11px 16px; text-align: left; font-size: 11.5px; font-weight: 600; letter-spacing: 0.05em; text-transform: uppercase; color: #6b7280; border-bottom: 1px solid #e2e4ea; white-space: nowrap; }
        tbody tr { border-bottom: 1px solid #f3f4f6; transition: background 0.1s; }
        tbody tr:last-child { border-bottom: none; }
        tbody tr:hover { background: #f5f8ff; }
        tbody td { padding: 12px 16px; color: #374151; vertical-align: middle; }
        .person-name { font-weight: 600; color: #1a1a2e; }
        .person-title { font-size: 12px; color: #6b7280; margin-top: 2px; }
        .badge-ext { display: inline-block; background: #eff6ff; color: #1a3a6b; font-weight: 600; font-size: 12px; padding: 3px 10px; border-radius: 5px; letter-spacing: 0.03em; }
        .badge-room { display: inline-block; background: #f0fdf4; color: #166534; font-size: 12px; padding: 3px 9px; border-radius: 5px; }
        .email-link { color: #1a3a6b; text-decoration: none; font-size: 13px; }
        .email-link:hover { text-decoration: underline; }
        .empty-state { padding: 3rem; text-align: center; color: #9ca3af; }
        .no-result-msg { padding: 2rem; text-align: center; color: #6b7280; font-weight: 500; }
        footer { background: #1a3a6b; color: rgba(255,255,255,0.65); text-align: center; padding: 14px 2rem; font-size: 12px; margin-top: auto; }
        footer span { color: rgba(255,255,255,0.9); font-weight: 500; }
        @media (max-width: 768px) {
            header { padding: 0 1rem; }
            .filter-bar { padding: 1rem; gap: 1rem; }
            main { padding: 1rem; }
            .filter-group { min-width: 100%; }
        }
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
    <div class="header-actions">
        <a href="?lang=tr" style="text-decoration:none; color:white;">TR</a> |
        <a href="?lang=en" style="text-decoration:none; color:white;">EN</a>
        <a href="/login"><spring:message code="login.link" /></a>
    </div>
</header>

<div class="filter-bar">
    <div class="filter-group">
        <label><spring:message code="filter.department" /></label>
        <select id="departmentSelect" onchange="filterByDepartment(this)">
            <option value=""><spring:message code="filter.allDepartments" /></option>
            <c:forEach var="dept" items="${departments}">
                <option value="${dept.departmentId}" ${selectedDepartmentId == dept.departmentId ? 'selected' : ''}>
                        ${dept.name}
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="filter-group">
        <label><spring:message code="filter.subdepartment" /></label>
        <select id="subDepartmentSelect" onchange="filterBySubDepartment(this)">
            <option value=""><spring:message code="filter.allSubDepartments" /></option>
            <c:forEach var="sub" items="${subDepartments}">
                <option value="${sub.subDepartmentId}" ${selectedSubId == sub.subDepartmentId ? 'selected' : ''}>
                        ${sub.name}
                </option>
            </c:forEach>
        </select>
    </div>

    <c:if test="${not empty selectedDepartment}">
        <div class="dept-info" id="deptInfo">
            <c:if test="${not empty selectedDepartment.phones}">
                <div class="dept-info-item">
                    <span class="lbl"><spring:message code="department.phone" />:</span>
                    <span>${selectedDepartment.phones}</span>
                </div>
            </c:if>
            <c:if test="${not empty selectedDepartment.faxes}">
                <div class="dept-info-item">
                    <span class="lbl"><spring:message code="department.fax" />:</span>
                    <span>${selectedDepartment.faxes}</span>
                </div>
            </c:if>
        </div>
    </c:if>

    <div class="filter-group search-group">
        <label><spring:message code="filter.search" /></label>
        <div style="position:relative;">
            <input type="text" id="searchInput" placeholder="<spring:message code="filter.search.placeholder" />" oninput="liveSearch(this.value)"
                   style="width:100%;padding-left:10px;">
        </div>
    </div>
</div>

<main>
    <div class="table-card">
        <div class="table-header">
            <h2><spring:message code="table.title" /></h2>
            <span class="result-count" id="resultCount">
                <span>${fn:length(kisiler)}</span> <spring:message code="table.person.count" />
            </span>
        </div>

        <table>
            <thead>
            <tr>
                <th><spring:message code="table.name" /></th>
                <th><spring:message code="table.department" /></th>
                <th><spring:message code="table.subdepartment" /></th>
                <th><spring:message code="table.room" /></th>
                <th><spring:message code="table.extension" /></th>
                <th><spring:message code="table.email" /></th>
            </tr>
            </thead>
            <tbody id="personTable">
            <c:choose>
                <c:when test="${empty kisiler}">
                    <tr>
                        <td colspan="6" class="empty-state">
                            <p><spring:message code="table.empty" /></p>
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="kisi" items="${kisiler}">
                        <tr class="person-row">
                            <td>
                                <div class="person-name">${kisi.firstName} ${kisi.lastName}</div>
                                <div class="person-title">${kisi.titleName}</div>
                            </td>
                            <td>${not empty kisi.deptName ? kisi.deptName : '—'}</td>
                            <td>${not empty kisi.subDeptName ? kisi.subDeptName : '—'}</td>
                            <td>${not empty kisi.roomNumber ? kisi.roomNumber : '—'}</td>
                            <td>${not empty kisi.extensionNumber ? kisi.extensionNumber : '—'}</td>
                            <td>${not empty kisi.email ? kisi.email : '—'}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <tr id="noResultRow" style="display: none;">
                <td colspan="6" class="no-result-msg"><spring:message code="table.empty" /></td>
            </tr>
            </tbody>
        </table>
    </div>
</main>

<footer>
    <span>2026</span> — <spring:message code="footer.project" />
</footer>

<script>
    function liveSearch(val) {
        const q = val.toLowerCase().trim();
        const rows = document.querySelectorAll('#personTable .person-row');
        const noResultRow = document.getElementById('noResultRow');
        let visibleCount = 0;
        if (q.length > 0 && q.length < 3) {
            rows.forEach(row => row.style.display = '');
            noResultRow.style.display = 'none';
            document.getElementById('resultCount').querySelector('span').textContent = rows.length;
            return;
        }
        rows.forEach(row => {
            const content = row.innerText.toLowerCase();
            const isMatch = !q || content.includes(q);
            row.style.display = isMatch ? '' : 'none';
            if (isMatch) visibleCount++;
        });
        noResultRow.style.display = (visibleCount === 0 && q.length >= 3) ? '' : 'none';
        document.getElementById('resultCount').querySelector('span').textContent = visibleCount;
    }
    function filterByDepartment(sel) {
        const id = sel.value;
        window.location.href = id ? '/?departmentId=' + id : '/';
    }
    function filterBySubDepartment(sel) {
        const id = sel.value;
        const deptId = document.getElementById('departmentSelect').value;
        let url = '/';
        const params = [];
        if (deptId) params.push('departmentId=' + deptId);
        if (id)     params.push('subDepartmentId=' + id);
        if (params.length) url += '?' + params.join('&');
        window.location.href = url;
    }
</script>

<style>
    #ai-fab { position:fixed; bottom:24px; right:24px; width:52px; height:52px; border-radius:50%; background:#1a3a6b; color:#fff; border:none; font-size:22px; cursor:pointer; box-shadow:0 4px 16px rgba(0,0,0,0.25); z-index:1000; display:flex; align-items:center; justify-content:center; }
    #ai-panel { position:fixed; bottom:88px; right:24px; width:340px; max-height:480px; background:#fff; border-radius:12px; box-shadow:0 8px 32px rgba(0,0,0,0.18); display:none; flex-direction:column; z-index:1000; overflow:hidden; border:1px solid #e2e4ea; }
    #ai-panel-header { background:#1a3a6b; color:#fff; padding:12px 16px; font-weight:600; font-size:14px; display:flex; justify-content:space-between; align-items:center; }
    #ai-panel-header button { background:none; border:none; color:#fff; font-size:18px; cursor:pointer; line-height:1; }
    #ai-messages { flex:1; overflow-y:auto; padding:12px; display:flex; flex-direction:column; gap:8px; min-height:200px; max-height:330px; }
    .ai-msg { max-width:85%; padding:8px 12px; border-radius:8px; font-size:13px; line-height:1.5; white-space:pre-wrap; }
    .ai-msg.user { background:#eff6ff; color:#1a3a6b; align-self:flex-end; border-bottom-right-radius:2px; }
    .ai-msg.bot  { background:#f3f4f6; color:#374151; align-self:flex-start; border-bottom-left-radius:2px; }
    #ai-input-row { display:flex; gap:8px; padding:10px 12px; border-top:1px solid #e2e4ea; }
    #ai-input { flex:1; border:1px solid #d1d5db; border-radius:7px; padding:7px 10px; font-size:13px; font-family:inherit; outline:none; resize:none; }
    #ai-input:focus { border-color:#1a3a6b; }
    #ai-send { background:#1a3a6b; color:#fff; border:none; border-radius:7px; padding:7px 14px; font-size:13px; cursor:pointer; white-space:nowrap; }
    #ai-send:disabled { opacity:0.5; cursor:not-allowed; }
</style>

<button id="ai-fab" onclick="toggleAiPanel()" title="AI Asistan">💬</button>

<div id="ai-panel">
    <div id="ai-panel-header">
        <span>🤖 Rehber Asistanı</span>
        <button onclick="toggleAiPanel()">×</button>
    </div>
    <div id="ai-messages">
        <div class="ai-msg bot">Merhaba! Yapay zeka asistanınızım. Soru sorabilirsiniz.</div>
    </div>
    <div id="ai-input-row">
        <textarea id="ai-input" rows="1" placeholder="Sorunuzu yazın..." onkeydown="aiEnter(event)"></textarea>
        <button id="ai-send" onclick="sendAiMessage()">Gönder</button>
    </div>
</div>

<script>
    function toggleAiPanel() { const p = document.getElementById('ai-panel'); p.style.display = p.style.display === 'flex' ? 'none' : 'flex'; }
    function aiEnter(e) { if (e.key === 'Enter' && !e.shiftKey) { e.preventDefault(); sendAiMessage(); } }
    async function sendAiMessage() {
        const input = document.getElementById('ai-input');
        const msg = input.value.trim();
        if (!msg) return;
        addMsg(msg, 'user');
        input.value = '';
        const btn = document.getElementById('ai-send');
        btn.disabled = true;
        addMsg('...', 'bot', 'loading-msg');
        try {
            const res = await fetch('/api/chat', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ message: msg }) });
            const data = await res.json();
            document.getElementById('loading-msg')?.remove();
            addMsg(data.reply || 'Yanıt alınamadı.', 'bot');
        } catch {
            document.getElementById('loading-msg')?.remove();
            addMsg('Bağlantı hatası.', 'bot');
        }
        btn.disabled = false;
    }
    function addMsg(text, role, id) {
        const box = document.getElementById('ai-messages');
        const d = document.createElement('div');
        d.className = 'ai-msg ' + role;
        d.textContent = text;
        if (id) d.id = id;
        box.appendChild(d);
        box.scrollTop = box.scrollHeight;
    }
</script>

</body>
</html>