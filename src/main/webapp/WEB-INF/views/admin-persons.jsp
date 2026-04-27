<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><spring:message code="admin.panel.title" /></title>
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', system-ui, -apple-system, sans-serif; background: #f5f6f8; color: #1a1a2e; min-height: 100vh; display: flex; flex-direction: column; }
        header { background: #1a3a6b; color: #fff; padding: 0 2rem; display: flex; align-items: center; justify-content: space-between; height: 64px; box-shadow: 0 2px 8px rgba(0,0,0,0.18); }
        .header-brand { display: flex; align-items: center; gap: 14px; text-decoration: none; color: inherit; }
        .logo-circle { width: 40px; height: 40px; border-radius: 50%; background: rgba(255,255,255,0.15); border: 1.5px solid rgba(255,255,255,0.4); display: flex; align-items: center; justify-content: center; font-weight: 700; font-size: 15px; }
        .header-brand h1 { font-size: 17px; font-weight: 600; line-height: 1.2; }
        .header-brand span { font-size: 12px; opacity: 0.72; }
        .header-right { display: flex; align-items: center; gap: 1rem; }
        .admin-badge { font-size: 12px; background: rgba(255,255,255,0.15); border: 1px solid rgba(255,255,255,0.3); padding: 4px 12px; border-radius: 20px; }
        .header-right a { font-size: 13px; color: rgba(255,255,255,0.85); text-decoration: none; border: 1px solid rgba(255,255,255,0.35); padding: 6px 16px; border-radius: 6px; transition: background 0.15s; }
        .header-right a:hover { background: rgba(255,255,255,0.12); }
        .filter-bar { background: #fff; border-bottom: 1px solid #e2e4ea; padding: 0.9rem 2rem; display: flex; align-items: flex-end; gap: 1rem; flex-wrap: wrap; }
        .filter-group { display: flex; flex-direction: column; gap: 4px; }
        .filter-group label { font-size: 11px; font-weight: 600; letter-spacing: 0.06em; text-transform: uppercase; color: #6b7280; }
        .filter-group select, .filter-group input { height: 36px; border: 1px solid #d1d5db; border-radius: 7px; padding: 0 12px; font-size: 13.5px; font-family: inherit; color: #1a1a2e; background: #fff; outline: none; transition: border-color 0.15s, box-shadow 0.15s; min-width: 180px; }
        .filter-group select:focus, .filter-group input:focus { border-color: #1a3a6b; box-shadow: 0 0 0 3px rgba(26,58,107,0.1); }
        main { flex: 1; padding: 1.5rem 2rem; }
        .alert { padding: 11px 16px; border-radius: 8px; font-size: 13.5px; margin-bottom: 1.25rem; display: flex; align-items: center; gap: 8px; }
        .alert-success { background: #f0fdf4; border: 1px solid #bbf7d0; color: #15803d; }
        .alert-error { background: #fef2f2; border: 1px solid #fecaca; color: #b91c1c; }
        .section-header { display: flex; align-items: center; justify-content: space-between; margin-bottom: 1.25rem; flex-wrap: wrap; gap: 0.75rem; }
        .section-header h2 { font-size: 18px; font-weight: 600; color: #1a1a2e; }
        .btn { display: inline-flex; align-items: center; gap: 6px; padding: 8px 18px; border-radius: 7px; font-size: 13.5px; font-weight: 500; font-family: inherit; cursor: pointer; border: 1px solid transparent; transition: all 0.15s; text-decoration: none; }
        .btn-primary { background: #1a3a6b; color: #fff; border-color: #1a3a6b; }
        .btn-primary:hover { background: #153260; }
        .btn-outline { background: #fff; color: #374151; border-color: #d1d5db; }
        .btn-outline:hover { background: #f9fafb; }
        .btn-danger { background: #fff; color: #b91c1c; border-color: #fecaca; }
        .btn-danger:hover { background: #fef2f2; }
        .btn-sm { padding: 5px 12px; font-size: 12.5px; }
        .table-card { background: #fff; border-radius: 10px; border: 1px solid #e2e4ea; overflow: hidden; }
        .table-inner-header { padding: 14px 18px; border-bottom: 1px solid #e2e4ea; display: flex; align-items: center; justify-content: space-between; }
        .table-inner-header h3 { font-size: 14px; font-weight: 600; color: #374151; }
        .count-badge { font-size: 12px; background: #eff6ff; color: #1a3a6b; padding: 3px 10px; border-radius: 20px; font-weight: 500; }
        table { width: 100%; border-collapse: collapse; font-size: 13.5px; }
        thead tr { background: #f8f9fb; }
        thead th { padding: 11px 16px; text-align: left; font-size: 11.5px; font-weight: 600; letter-spacing: 0.05em; text-transform: uppercase; color: #6b7280; border-bottom: 1px solid #e2e4ea; white-space: nowrap; }
        tbody tr { border-bottom: 1px solid #f3f4f6; transition: background 0.1s; }
        tbody tr:hover { background: #f5f8ff; }
        tbody td { padding: 11px 16px; color: #374151; }
        .person-name { font-weight: 600; color: #1a1a2e; }
        .person-title { font-size: 12px; color: #6b7280; margin-top: 2px; }
        .td-actions { display: flex; gap: 8px; align-items: center; }
        .modal-overlay { display: none; position: fixed; inset: 0; background: rgba(0,0,0,0.4); z-index: 100; align-items: center; justify-content: center; padding: 1rem; }
        .modal-overlay.open { display: flex; }
        .modal { background: #fff; border-radius: 12px; border: 1px solid #e2e4ea; padding: 2rem; width: 100%; max-width: 540px; }
        .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
        .form-group { display: flex; flex-direction: column; gap: 5px; }
        .form-group label { font-size: 12.5px; font-weight: 600; color: #374151; }
        .form-group input, .form-group select { height: 40px; border: 1px solid #d1d5db; border-radius: 7px; padding: 0 12px; font-size: 14px; }
        .modal-footer { display: flex; justify-content: flex-end; gap: 10px; margin-top: 1.5rem; }
        footer { background: #1a3a6b; color: rgba(255,255,255,0.65); text-align: center; padding: 14px 2rem; font-size: 12px; }
    </style>
</head>
<body>

<header>
    <a href="/" class="header-brand">
        <div class="logo-circle">TR</div>
        <div>
            <h1><spring:message code="app.name" /></h1>
            <span><spring:message code="admin.panel.title" /></span>
        </div>
    </a>
    <div class="header-right">
        <span class="admin-badge">${oturumEmail}</span>
        <a href="/logout"><spring:message code="admin.logout" /></a>
    </div>
</header>

<div class="filter-bar">
    <div class="filter-group">
        <label><spring:message code="filter.department" /></label>
        <select id="deptFilter" onchange="filterTable()">
            <option value=""><spring:message code="filter.allDepartments" /></option>
            <c:forEach var="d" items="${departments}">
                <option value="${d.name}">${d.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="filter-group">
        <label><spring:message code="filter.search" /></label>
        <input type="text" id="searchInput" placeholder="<spring:message code="filter.search.placeholder" />" oninput="filterTable()">
    </div>
</div>

<main>
    <c:if test="${not empty mesaj}"><div class="alert alert-success">${mesaj}</div></c:if>
    <c:if test="${not empty hata}"><div class="alert alert-error">${hata}</div></c:if>

    <div class="section-header">
        <h2><spring:message code="admin.manage.persons" /></h2>
        <button class="btn btn-primary" onclick="openModal('modalEkle')">
            <spring:message code="admin.btn.add" />
        </button>
    </div>

    <div class="table-card">
        <div class="table-inner-header">
            <h3><spring:message code="admin.list.title" /></h3>
            <span class="count-badge" id="resultCount"><span>${fn:length(kisiler)}</span> <spring:message code="table.person.count" /></span>
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
                <th>İşlem</th>
            </tr>
            </thead>
            <tbody id="personTable">
            <c:forEach var="k" items="${kisiler}">
                <tr class="person-row" data-name="${k.firstName} ${k.lastName}" data-person-id="${k.personId}" data-first-name="${k.firstName}" data-last-name="${k.lastName}" data-title="${k.titleName}" data-extension="${k.extensionNumber}" data-room="${k.roomNumber}" data-email="${k.email}" data-sub-id="${not empty k.subDeptId ? k.subDeptId : 0}">
                    <td>
                        <div class="person-name">${k.firstName} ${k.lastName}</div>
                        <div class="person-title">${k.titleName}</div>
                    </td>
                    <td>${not empty k.deptName ? k.deptName : '—'}</td>
                    <td>${not empty k.subDeptName ? k.subDeptName : '—'}</td>
                    <td>${not empty k.roomNumber ? k.roomNumber : '—'}</td>
                    <td>${not empty k.extensionNumber ? k.extensionNumber : '—'}</td>
                    <td>${not empty k.email ? k.email : '—'}</td>
                    <td>
                        <div class="td-actions">
                            <button class="btn btn-outline btn-sm" onclick="openEditFromRow(this)"><spring:message code="admin.action.edit" /></button>
                            <button class="btn btn-danger btn-sm" onclick="openDeleteFromRow(this)"><spring:message code="admin.action.delete" /></button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>

<footer><span>2026</span> — <spring:message code="footer.project" /></footer>

<div class="modal-overlay" id="modalEkle">
    <div class="modal">
        <h3><spring:message code="admin.modal.add.title" /></h3>
        <form action="/admin/persons/create" method="post">
            <div class="form-grid">
                <div class="form-group"><label><spring:message code="admin.field.firstname" /></label><input type="text" name="firstName" required></div>
                <div class="form-group"><label><spring:message code="admin.field.lastname" /></label><input type="text" name="lastName" required></div>
                <div class="form-group full"><label><spring:message code="admin.field.title" /></label><input type="text" name="titleName"></div>
                <div class="form-group"><label><spring:message code="admin.field.ext" /></label><input type="text" name="extensionNumber"></div>
                <div class="form-group"><label><spring:message code="admin.field.room" /></label><input type="text" name="roomNumber"></div>
                <div class="form-group full"><label><spring:message code="admin.field.email" /></label><input type="email" name="email"></div>
                <div class="form-group"><label><spring:message code="admin.select.dept" /></label><select id="ekle-deptSelect" onchange="loadSubDepts('ekle-deptSelect','ekle-subSelect')"><option value="">— <spring:message code="admin.select.first_dept" /> —</option><c:forEach var="d" items="${departments}"><option value="${d.departmentId}">${d.name}</option></c:forEach></select></div>
                <div class="form-group"><label><spring:message code="admin.select.subdept" /></label><select id="ekle-subSelect" name="subDepartmentId"><option value="">— <spring:message code="admin.select.first_dept" /> —</option></select></div>
            </div>
            <div class="modal-footer"><button type="button" class="btn btn-outline" onclick="closeModal('modalEkle')"><spring:message code="admin.btn.cancel" /></button><button type="submit" class="btn btn-primary"><spring:message code="admin.btn.save" /></button></div>
        </form>
    </div>
</div>

<div class="modal-overlay" id="modalDuzenle">
    <div class="modal">
        <h3><spring:message code="admin.modal.edit.title" /></h3>
        <form action="/admin/persons/update" method="post">
            <input type="hidden" name="personId" id="editPersonId">
            <div class="form-grid">
                <div class="form-group"><label><spring:message code="admin.field.firstname" /></label><input type="text" name="firstName" id="editFirstName" required></div>
                <div class="form-group"><label><spring:message code="admin.field.lastname" /></label><input type="text" name="lastName" id="editLastName" required></div>
                <div class="form-group full"><label><spring:message code="admin.field.title" /></label><input type="text" name="titleName" id="editTitleName"></div>
                <div class="form-group"><label><spring:message code="admin.field.ext" /></label><input type="text" name="extensionNumber" id="editExtension"></div>
                <div class="form-group"><label><spring:message code="admin.field.room" /></label><input type="text" name="roomNumber" id="editRoom"></div>
                <div class="form-group full"><label><spring:message code="admin.field.email" /></label><input type="email" name="email" id="editEmail"></div>
                <div class="form-group"><label><spring:message code="admin.select.dept" /></label><select id="edit-deptSelect" onchange="loadSubDepts('edit-deptSelect','edit-subSelect')"><option value="">— <spring:message code="admin.select.first_dept" /> —</option><c:forEach var="d" items="${departments}"><option value="${d.departmentId}">${d.name}</option></c:forEach></select></div>
                <div class="form-group"><label><spring:message code="admin.select.subdept" /></label><select id="edit-subSelect" name="subDepartmentId"><option value="">— <spring:message code="admin.select.first_dept" /> —</option></select></div>
            </div>
            <div class="modal-footer"><button type="button" class="btn btn-outline" onclick="closeModal('modalDuzenle')"><spring:message code="admin.btn.cancel" /></button><button type="submit" class="btn btn-primary"><spring:message code="admin.btn.update" /></button></div>
        </form>
    </div>
</div>

<div class="modal-overlay" id="modalSil">
    <div class="modal">
        <h3><spring:message code="admin.modal.delete.title" /></h3>
        <form action="/admin/persons/delete" method="post">
            <input type="hidden" name="personId" id="deletePersonId">
            <div class="modal-footer"><button type="button" class="btn btn-outline" onclick="closeModal('modalSil')"><spring:message code="admin.btn.cancel" /></button><button type="submit" class="btn btn-danger"><spring:message code="admin.btn.confirm_delete" /></button></div>
        </form>
    </div>
</div>

<script>
    const allSubDepts = ${not empty subDepartmentsJson ? subDepartmentsJson : '[]'};
    function openModal(id) { document.getElementById(id).classList.add('open'); }
    function closeModal(id) { document.getElementById(id).classList.remove('open'); }
    function loadSubDepts(deptSelId, subSelId, selectedSubId) {
        const deptId = parseInt(document.getElementById(deptSelId).value) || 0;
        const subSel = document.getElementById(subSelId);
        subSel.innerHTML = '<option value="">— Bölüm Seç —</option>';
        if (!deptId) return;
        allSubDepts.filter(s => s.departmentId === deptId).forEach(s => {
            const opt = document.createElement('option');
            opt.value = s.subDepartmentId; opt.textContent = s.name;
            if (selectedSubId && s.subDepartmentId === parseInt(selectedSubId)) opt.selected = true;
            subSel.appendChild(opt);
        });
    }
    // ... diğer JS fonksiyonların aynı kalacak
</script>
</body>
</html>