<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Telefon Rehberi</title>
    <style>
        /* CSS KODLARININ TAMAMINI BURAYA YAPIŞTIR (Yukarıdaki HTML'de olan CSS'in aynısı) */
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', system-ui, -apple-system, sans-serif; background: #f5f6f8; color: #1a1a2e; min-height: 100vh; display: flex; flex-direction: column; }
        header { background: #1a3a6b; color: #fff; padding: 0 2rem; display: flex; align-items: center; justify-content: space-between; height: 64px; box-shadow: 0 2px 8px rgba(0,0,0,0.18); }
        .header-brand { display: flex; align-items: center; gap: 14px; text-decoration: none; color: inherit; }
        .header-brand .logo-circle { width: 40px; height: 40px; border-radius: 50%; background: rgba(255,255,255,0.15); border: 1.5px solid rgba(255,255,255,0.4); display: flex; align-items: center; justify-content: center; font-weight: 700; font-size: 15px; letter-spacing: -0.5px; }
        .header-brand h1 { font-size: 17px; font-weight: 600; line-height: 1.2; }
        .header-brand span { font-size: 12px; opacity: 0.72; font-weight: 400; }
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
        footer { background: #1a3a6b; color: rgba(255,255,255,0.65); text-align: center; padding: 14px 2rem; font-size: 12px; }
        footer span { color: rgba(255,255,255,0.9); font-weight: 500; }
        @media (max-width: 768px) {
            header { padding: 0 1rem; }
            .filter-bar { padding: 1rem; gap: 1rem; }
            main { padding: 1rem; }
            .filter-group { min-width: 100%; }
            thead th:nth-child(4), thead th:nth-child(5), tbody td:nth-child(4), tbody td:nth-child(5) { display: none; }
        }
    </style>
</head>
<body>

<header>
    <a href="/" class="header-brand">
        <div class="logo-circle">TR</div>
        <div>
            <h1>Telefon Rehberi</h1>
            <span>Kurum İçi İletişim Sistemi</span>
        </div>
    </a>
    <div class="header-actions">
        <a href="/login">Oturum Aç</a>
    </div>
</header>

<div class="filter-bar">
    <div class="filter-group">
        <label>Birim Seç</label>
        <select id="departmentSelect" onchange="filterByDepartment(this)">
            <option value="">— Tüm Birimler —</option>
            <c:forEach var="dept" items="${departments}">
                <option value="${dept.departmentId}" ${selectedDepartmentId == dept.departmentId ? 'selected' : ''}>
                        ${dept.name}
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="filter-group">
        <label>Bölüm Seç</label>
        <select id="subDepartmentSelect" onchange="filterBySubDepartment(this)">
            <option value="">— Tüm Bölümler —</option>
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
                    <span class="lbl">Telefon:</span>
                    <span>${selectedDepartment.phones}</span>
                </div>
            </c:if>
            <c:if test="${not empty selectedDepartment.faxes}">
                <div class="dept-info-item">
                    <span class="lbl">Faks:</span>
                    <span>${selectedDepartment.faxes}</span>
                </div>
            </c:if>
        </div>
    </c:if>

    <div class="filter-group search-group">
        <label>Kişi / Birim Ara</label>
        <div style="position:relative;">
            <svg style="position:absolute;left:10px;top:50%;transform:translateY(-50%);width:16px;height:16px;color:#9ca3af;pointer-events:none;" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
            </svg>
            <input type="text" id="searchInput" placeholder="Ad, Soyad, Birim veya Bölüm (3 harf)..." oninput="liveSearch(this.value)"
                   style="width:100%;padding-left:36px;">
        </div>
    </div>
</div>

<main>
    <div class="table-card">
        <div class="table-header">
            <h2>Personel Listesi</h2>
            <span class="result-count" id="resultCount">
                <span>${fn:length(kisiler)}</span> kişi
            </span>
        </div>

        <table>
            <thead>
            <tr>
                <th>Ad Soyad / Unvan</th>
                <th>Birim</th>
                <th>Bölüm</th>
                <th>Oda</th>
                <th>Dahili No</th>
                <th>E-posta</th>
            </tr>
            </thead>
            <tbody id="personTable">

            <c:choose>
                <c:when test="${empty kisiler}">
                    <tr>
                        <td colspan="6" class="empty-state">
                            <p>Henüz kayıt bulunamadı veya filtreleme yapın.</p>
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
                            <td>
                                <c:choose>
                                    <c:when test="${kisi.subdepartment != null && kisi.subdepartment.department != null}">
                                        ${kisi.subdepartment.department.name}
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${kisi.subdepartment != null}">
                                        ${kisi.subdepartment.name}
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty kisi.roomNumber}">
                                        <span class="badge-room">${kisi.roomNumber}</span>
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty kisi.extensionNumber}">
                                        <span class="badge-ext">${kisi.extensionNumber}</span>
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty kisi.email}">
                                        <a class="email-link" href="mailto:${kisi.email}">${kisi.email}</a>
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

            <tr id="noResultRow" style="display: none;">
                <td colspan="6" class="no-result-msg">Aranan kriterlere uygun personel bulunamadı.</td>
            </tr>
            </tbody>
        </table>
    </div>
</main>

<footer>
    <span>2026</span> — BM470 - Ders Projesi
</footer>

<script>
    // JS KODLARININ TAMAMINI BURAYA YAPIŞTIR (Yukarıdaki HTML'de olan JS'in aynısı)
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

</body>
</html>