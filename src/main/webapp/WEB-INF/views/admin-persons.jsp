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
        tbody td { padding: 11px 16px; color: #374151; vertical-align: middle; }
        .person-info-wrapper { display: flex; align-items: center; gap: 12px; }
        .profile-img { width: 36px; height: 36px; border-radius: 50%; object-fit: cover; border: 1px solid #e2e4ea; }
        .default-avatar { width: 36px; height: 36px; border-radius: 50%; background: #e0e7ff; color: #1a3a6b; display: flex; align-items: center; justify-content: center; font-weight: 600; font-size: 12px; border: 1px solid #c7d2fe; letter-spacing: 1px; }
        .person-name { font-weight: 600; color: #1a1a2e; }
        .person-title { font-size: 12px; color: #6b7280; margin-top: 2px; }
        .td-actions { display: flex; gap: 8px; align-items: center; }
        .modal-overlay { display: none; position: fixed; inset: 0; background: rgba(0,0,0,0.4); z-index: 100; align-items: center; justify-content: center; padding: 1rem; }
        .modal-overlay.open { display: flex; }
        .modal { background: #fff; border-radius: 12px; border: 1px solid #e2e4ea; padding: 2rem; width: 100%; max-width: 540px; }
        .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
        .form-group { display: flex; flex-direction: column; gap: 5px; }
        .form-group.full { grid-column: 1 / -1; }
        .form-group label { font-size: 12.5px; font-weight: 600; color: #374151; }
        .form-group input, .form-group select { height: 40px; border: 1px solid #d1d5db; border-radius: 7px; padding: 0 12px; font-size: 14px; }
        .modal-footer { display: flex; justify-content: flex-end; gap: 10px; margin-top: 1.5rem; }
        .tabs { display: flex; gap: 4px; background: #fff; border-bottom: 1px solid #e2e4ea; padding: 0 2rem; }
        .tab-btn { padding: 12px 20px; font-size: 13.5px; font-weight: 500; font-family: inherit; background: none; border: none; border-bottom: 2px solid transparent; color: #6b7280; cursor: pointer; transition: all 0.15s; }
        .tab-btn.active { color: #1a3a6b; border-bottom-color: #1a3a6b; font-weight: 600; }
        .tab-panel { display: none; }
        .tab-panel.active { display: block; }
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

<div class="tabs">
    <button class="tab-btn active" onclick="switchTab(event, 'persons')">Kişiler</button>
    <button class="tab-btn" onclick="switchTab(event, 'departments')">Birimler</button>
    <button class="tab-btn" onclick="switchTab(event, 'subdepartments')">Bölümler</button>
</div>

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

    <%-- ===== KİŞİLER PANELİ ===== --%>
    <div id="tab-persons" class="tab-panel active">
        <div class="section-header">
            <h2>Kişi Yönetimi</h2>
            <button class="btn btn-primary" onclick="openModal('modalEkle')">+ Kişi Ekle</button>
        </div>
        <div class="table-card">
            <div class="table-inner-header">
                <h3>Kişi Listesi</h3>
                <span class="count-badge" id="resultCount"><span>${fn:length(kisiler)}</span> kişi</span>
            </div>
            <table>
                <thead><tr>
                    <th>Ad Soyad</th><th>Birim</th><th>Bölüm</th>
                    <th>Oda</th><th>Dahili</th><th>E-posta</th><th>İşlem</th>
                </tr></thead>
                <tbody id="personTable">
                <c:forEach var="k" items="${kisiler}">
                    <tr class="person-row"
                        data-name="${k.firstName} ${k.lastName}"
                        data-person-id="${k.personId}"
                        data-first-name="${k.firstName}"
                        data-last-name="${k.lastName}"
                        data-title="${k.titleName}"
                        data-extension="${k.extensionNumber}"
                        data-room="${k.roomNumber}"
                        data-email="${k.email}"
                        data-sub-id="${not empty k.subDeptId ? k.subDeptId : 0}">
                        <td>
                            <div class="person-info-wrapper">
                                <c:choose>
                                    <c:when test="${not empty k.photo}">
                                        <img src="data:image/jpeg;base64,${k.photo}" class="profile-img" alt="${k.firstName}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="default-avatar">${fn:substring(k.firstName,0,1)}${fn:substring(k.lastName,0,1)}</div>
                                    </c:otherwise>
                                </c:choose>
                                <div>
                                    <div class="person-name">${k.firstName} ${k.lastName}</div>
                                    <div class="person-title">${k.titleName}</div>
                                </div>
                            </div>
                        </td>
                        <td>${not empty k.deptName    ? k.deptName    : '—'}</td>
                        <td>${not empty k.subDeptName ? k.subDeptName : '—'}</td>
                        <td>${not empty k.roomNumber  ? k.roomNumber  : '—'}</td>
                        <td>${not empty k.extensionNumber ? k.extensionNumber : '—'}</td>
                        <td>${not empty k.email       ? k.email       : '—'}</td>
                        <td>
                            <div class="td-actions">
                                <button class="btn btn-outline btn-sm" onclick="openEditFromRow(this)">Düzenle</button>
                                <button class="btn btn-danger btn-sm"  onclick="openDeleteFromRow(this)">Sil</button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <%-- ===== BİRİMLER PANELİ ===== --%>
    <div id="tab-departments" class="tab-panel">
        <div class="section-header">
            <h2>Birim Yönetimi</h2>
            <button class="btn btn-primary" onclick="openModal('modalDeptEkle')">+ Birim Ekle</button>
        </div>
        <div class="table-card">
            <div class="table-inner-header">
                <h3>Birim Listesi</h3>
                <span class="count-badge">${fn:length(departments)} birim</span>
            </div>
            <table>
                <thead><tr>
                    <th>Birim Adı</th><th>Telefon(lar)</th><th>Faks(lar)</th><th>İşlem</th>
                </tr></thead>
                <tbody>
                <c:forEach var="d" items="${departments}">
                    <tr data-dept-id="${d.departmentId}"
                        data-dept-name="${d.name}"
                        data-dept-phones="${not empty d.phones ? d.phones : ''}"
                        data-dept-faxes="${not empty d.faxes  ? d.faxes  : ''}">
                        <td><strong>${d.name}</strong></td>
                        <td>${not empty d.phones ? d.phones : '—'}</td>
                        <td>${not empty d.faxes  ? d.faxes  : '—'}</td>
                        <td>
                            <div class="td-actions">
                                <button class="btn btn-outline btn-sm" onclick="openEditDept(this)">Düzenle</button>
                                <button class="btn btn-danger btn-sm"  onclick="openDeleteDept(this)">Sil</button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <%-- ===== BÖLÜMLER PANELİ ===== --%>
    <div id="tab-subdepartments" class="tab-panel">
        <div class="section-header">
            <h2>Bölüm Yönetimi</h2>
            <button class="btn btn-primary" onclick="openModal('modalSubEkle')">+ Bölüm Ekle</button>
        </div>
        <div class="table-card">
            <div class="table-inner-header">
                <h3>Bölüm Listesi</h3>
                <span class="count-badge">${fn:length(allSubDepts)} bölüm</span>
            </div>
            <table>
                <thead><tr>
                    <th>Bölüm Adı</th><th>Bağlı Birim</th><th>İşlem</th>
                </tr></thead>
                <tbody>
                <c:forEach var="s" items="${allSubDepts}">
                    <tr data-sub-id="${s.subDepartmentId}"
                        data-sub-name="${s.name}"
                        data-sub-dept-id="${s.departmentId}">
                        <td>${s.name}</td>
                        <td>
                            <c:forEach var="d" items="${departments}">
                                <c:if test="${d.departmentId == s.departmentId}">${d.name}</c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <div class="td-actions">
                                <button class="btn btn-outline btn-sm" onclick="openEditSub(this)">Düzenle</button>
                                <button class="btn btn-danger btn-sm"  onclick="openDeleteSub(this)">Sil</button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>

<footer><span>2026</span> — <spring:message code="footer.project" /></footer>

<div class="modal-overlay" id="modalEkle">
    <div class="modal">
        <h3><spring:message code="admin.modal.add.title" /></h3>
        <form action="/admin/persons/create" method="post" enctype="multipart/form-data">
            <div class="form-grid">
                <div class="form-group"><label><spring:message code="admin.field.firstname" /></label><input type="text" name="firstName" required></div>
                <div class="form-group"><label><spring:message code="admin.field.lastname" /></label><input type="text" name="lastName" required></div>
                <div class="form-group full"><label><spring:message code="admin.field.title" /></label><input type="text" name="titleName"></div>
                <div class="form-group"><label><spring:message code="admin.field.ext" /></label><input type="text" name="extensionNumber"></div>
                <div class="form-group"><label><spring:message code="admin.field.room" /></label><input type="text" name="roomNumber"></div>
                <div class="form-group full"><label><spring:message code="admin.field.email" /></label><input type="email" name="email"></div>

                <div class="form-group">
                    <label><spring:message code="admin.select.dept" /></label>
                    <select id="ekle-deptSelect" onchange="loadSubDepts('ekle-deptSelect','ekle-subSelect')" required>
                        <option value="">— <spring:message code="admin.select.first_dept" /> —</option>
                        <c:forEach var="d" items="${departments}"><option value="${d.departmentId}">${d.name}</option></c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label><spring:message code="admin.select.subdept" /></label>
                    <select id="ekle-subSelect" name="subDepartmentId" required>
                        <option value="">— Bölüm Seçin —</option>
                    </select>
                </div>

                <div class="form-group full">
                    <label>Profil Resmi</label>
                    <input type="file" name="file" accept="image/*" style="padding-top: 6px;">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalEkle')"><spring:message code="admin.btn.cancel" /></button>
                <button type="submit" class="btn btn-primary"><spring:message code="admin.btn.save" /></button>
            </div>
        </form>
    </div>
</div>

<div class="modal-overlay" id="modalDuzenle">
    <div class="modal">
        <h3><spring:message code="admin.modal.edit.title" /></h3>
        <form action="/admin/persons/update" method="post" enctype="multipart/form-data">
            <input type="hidden" name="personId" id="editPersonId">
            <div class="form-grid">
                <div class="form-group"><label><spring:message code="admin.field.firstname" /></label><input type="text" name="firstName" id="editFirstName" required></div>
                <div class="form-group"><label><spring:message code="admin.field.lastname" /></label><input type="text" name="lastName" id="editLastName" required></div>
                <div class="form-group full"><label><spring:message code="admin.field.title" /></label><input type="text" name="titleName" id="editTitleName"></div>
                <div class="form-group"><label><spring:message code="admin.field.ext" /></label><input type="text" name="extensionNumber" id="editExtension"></div>
                <div class="form-group"><label><spring:message code="admin.field.room" /></label><input type="text" name="roomNumber" id="editRoom"></div>
                <div class="form-group full"><label><spring:message code="admin.field.email" /></label><input type="email" name="email" id="editEmail"></div>

                <div class="form-group">
                    <label><spring:message code="admin.select.dept" /></label>
                    <select id="edit-deptSelect" onchange="loadSubDepts('edit-deptSelect','edit-subSelect')" required>
                        <option value="">— <spring:message code="admin.select.first_dept" /> —</option>
                        <c:forEach var="d" items="${departments}"><option value="${d.departmentId}">${d.name}</option></c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label><spring:message code="admin.select.subdept" /></label>
                    <select id="edit-subSelect" name="subDepartmentId" required>
                        <option value="">— Bölüm Seçin —</option>
                    </select>
                </div>

                <div class="form-group full">
                    <label>Profil Resmi (Değiştirmek istemiyorsanız boş bırakın)</label>
                    <input type="file" name="file" accept="image/*" style="padding-top: 6px;">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalDuzenle')"><spring:message code="admin.btn.cancel" /></button>
                <button type="submit" class="btn btn-primary"><spring:message code="admin.btn.update" /></button>
            </div>
        </form>
    </div>
</div>

<div class="modal-overlay" id="modalSil">
    <div class="modal">
        <h3><spring:message code="admin.modal.delete.title" /></h3>
        <form action="/admin/persons/delete" method="post">
            <input type="hidden" name="personId" id="deletePersonId">
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalSil')"><spring:message code="admin.btn.cancel" /></button>
                <button type="submit" class="btn btn-danger"><spring:message code="admin.btn.confirm_delete" /></button>
            </div>
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
        subSel.innerHTML = '<option value="">— Bölüm Seçin —</option>';
        if (!deptId) return;
        allSubDepts.filter(s => s.departmentId === deptId).forEach(s => {
            const opt = document.createElement('option');
            opt.value = s.subDepartmentId;
            opt.textContent = s.name;
            if (selectedSubId && s.subDepartmentId === parseInt(selectedSubId)) opt.selected = true;
            subSel.appendChild(opt);
        });
    }

    function openEditFromRow(btn) {
        const tr = btn.closest('tr');
        document.getElementById('editPersonId').value = tr.dataset.personId;
        document.getElementById('editFirstName').value = tr.dataset.firstName;
        document.getElementById('editLastName').value = tr.dataset.lastName;
        document.getElementById('editTitleName').value = tr.dataset.title;
        document.getElementById('editExtension').value = tr.dataset.extension;
        document.getElementById('editRoom').value = tr.dataset.room;
        document.getElementById('editEmail').value = tr.dataset.email;


        const subId = parseInt(tr.dataset.subId);
        let deptId = "";
        if(subId) {
            const subDeptObj = allSubDepts.find(s => s.subDepartmentId === subId);
            if(subDeptObj) deptId = subDeptObj.departmentId;
        }


        document.getElementById('edit-deptSelect').value = deptId;
        loadSubDepts('edit-deptSelect', 'edit-subSelect', subId);

        openModal('modalDuzenle');
    }


    function openDeleteFromRow(btn) {
        const tr = btn.closest('tr');
        document.getElementById('deletePersonId').value = tr.dataset.personId;
        openModal('modalSil');
    }


    function filterTable() {
        const deptFilter = document.getElementById('deptFilter').value.toLowerCase();
        const searchInput = document.getElementById('searchInput').value.toLowerCase();
        const rows = document.querySelectorAll('.person-row');
        let visibleCount = 0;

        rows.forEach(row => {
            const deptText = row.children[1].textContent.toLowerCase();
            const nameText = row.dataset.name.toLowerCase();
            const matchDept = deptFilter === "" || deptText.includes(deptFilter);
            const matchName = searchInput === "" || nameText.includes(searchInput);

            if (matchDept && matchName) {
                row.style.display = "";
                visibleCount++;
            } else {
                row.style.display = "none";
            }
        });

        document.getElementById('resultCount').querySelector('span').textContent = visibleCount;
    }
    function switchTab(e, name) {
        document.querySelectorAll('.tab-panel').forEach(p => p.classList.remove('active'));
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        document.getElementById('tab-' + name).classList.add('active');
        e.currentTarget.classList.add('active');
    }

    // Birim modal fonksiyonları
    function openEditDept(btn) {
        const tr = btn.closest('tr');
        document.getElementById('editDeptId').value    = tr.dataset.deptId;
        document.getElementById('editDeptName').value  = tr.dataset.deptName;
        document.getElementById('editDeptPhones').value= tr.dataset.deptPhones;
        document.getElementById('editDeptFaxes').value = tr.dataset.deptFaxes;
        openModal('modalDeptDuzenle');
    }
    function openDeleteDept(btn) {
        document.getElementById('deleteDeptId').value = btn.closest('tr').dataset.deptId;
        openModal('modalDeptSil');
    }

    // Bölüm modal fonksiyonları
    function openEditSub(btn) {
        const tr = btn.closest('tr');
        document.getElementById('editSubId').value     = tr.dataset.subId;
        document.getElementById('editSubName').value   = tr.dataset.subName;
        document.getElementById('editSubDeptId').value = tr.dataset.subDeptId;
        openModal('modalSubDuzenle');
    }
    function openDeleteSub(btn) {
        document.getElementById('deleteSubId').value = btn.closest('tr').dataset.subId;
        openModal('modalSubSil');
    }
</script>
<%-- Birim Ekle --%>
<div class="modal-overlay" id="modalDeptEkle">
    <div class="modal">
        <h3>Yeni Birim Ekle</h3>
        <form action="/admin/persons/departments/create" method="post">
            <div class="form-grid">
                <div class="form-group full"><label>Birim Adı *</label><input type="text" name="name" required></div>
                <div class="form-group full"><label>Telefon(lar)</label><input type="text" name="phones" placeholder="0380 000 00 00, ..."></div>
                <div class="form-group full"><label>Faks(lar)</label><input type="text" name="faxes" placeholder="0380 000 00 01, ..."></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalDeptEkle')">İptal</button>
                <button type="submit" class="btn btn-primary">Kaydet</button>
            </div>
        </form>
    </div>
</div>

<%-- Birim Düzenle --%>
<div class="modal-overlay" id="modalDeptDuzenle">
    <div class="modal">
        <h3>Birimi Düzenle</h3>
        <form action="/admin/persons/departments/update" method="post">
            <input type="hidden" name="departmentId" id="editDeptId">
            <div class="form-grid">
                <div class="form-group full"><label>Birim Adı *</label><input type="text" name="name" id="editDeptName" required></div>
                <div class="form-group full"><label>Telefon(lar)</label><input type="text" name="phones" id="editDeptPhones"></div>
                <div class="form-group full"><label>Faks(lar)</label><input type="text" name="faxes" id="editDeptFaxes"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalDeptDuzenle')">İptal</button>
                <button type="submit" class="btn btn-primary">Güncelle</button>
            </div>
        </form>
    </div>
</div>

<%-- Birim Sil --%>
<div class="modal-overlay" id="modalDeptSil">
    <div class="modal">
        <h3>Birimi Sil</h3>
        <p style="margin:1rem 0;color:#374151;font-size:14px;">Bu birimi silmek istediğinizden emin misiniz? Bağlı tüm bölümler ve kişiler de silinecektir.</p>
        <form action="/admin/persons/departments/delete" method="post">
            <input type="hidden" name="departmentId" id="deleteDeptId">
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalDeptSil')">İptal</button>
                <button type="submit" class="btn btn-danger">Evet, Sil</button>
            </div>
        </form>
    </div>
</div>

<%-- Bölüm Ekle --%>
<div class="modal-overlay" id="modalSubEkle">
    <div class="modal">
        <h3>Yeni Bölüm Ekle</h3>
        <form action="/admin/persons/subdepartments/create" method="post">
            <div class="form-grid">
                <div class="form-group full"><label>Bölüm Adı *</label><input type="text" name="name" required></div>
                <div class="form-group full">
                    <label>Bağlı Birim *</label>
                    <select name="departmentId" required>
                        <option value="">— Birim Seçin —</option>
                        <c:forEach var="d" items="${departments}">
                            <option value="${d.departmentId}">${d.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalSubEkle')">İptal</button>
                <button type="submit" class="btn btn-primary">Kaydet</button>
            </div>
        </form>
    </div>
</div>

<%-- Bölüm Düzenle --%>
<div class="modal-overlay" id="modalSubDuzenle">
    <div class="modal">
        <h3>Bölümü Düzenle</h3>
        <form action="/admin/persons/subdepartments/update" method="post">
            <input type="hidden" name="subDepartmentId" id="editSubId">
            <div class="form-grid">
                <div class="form-group full"><label>Bölüm Adı *</label><input type="text" name="name" id="editSubName" required></div>
                <div class="form-group full">
                    <label>Bağlı Birim *</label>
                    <select name="departmentId" id="editSubDeptId" required>
                        <option value="">— Birim Seçin —</option>
                        <c:forEach var="d" items="${departments}">
                            <option value="${d.departmentId}">${d.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalSubDuzenle')">İptal</button>
                <button type="submit" class="btn btn-primary">Güncelle</button>
            </div>
        </form>
    </div>
</div>

<%-- Bölüm Sil --%>
<div class="modal-overlay" id="modalSubSil">
    <div class="modal">
        <h3>Bölümü Sil</h3>
        <p style="margin:1rem 0;color:#374151;font-size:14px;">Bu bölümü silmek istediğinizden emin misiniz?</p>
        <form action="/admin/persons/subdepartments/delete" method="post">
            <input type="hidden" name="subDepartmentId" id="deleteSubId">
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalSubSil')">İptal</button>
                <button type="submit" class="btn btn-danger">Evet, Sil</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>