<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kişi Yönetimi – Yönetici Paneli</title>
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
        .alert-error   { background: #fef2f2; border: 1px solid #fecaca; color: #b91c1c; }

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
        tbody tr:last-child { border-bottom: none; }
        tbody tr:hover { background: #f5f8ff; }
        tbody td { padding: 11px 16px; color: #374151; vertical-align: middle; }

        .person-name { font-weight: 600; color: #1a1a2e; }
        .person-title { font-size: 12px; color: #6b7280; margin-top: 2px; }
        .badge-ext { display: inline-block; background: #eff6ff; color: #1a3a6b; font-weight: 600; font-size: 12px; padding: 3px 10px; border-radius: 5px; }
        .badge-room { display: inline-block; background: #f0fdf4; color: #166534; font-size: 12px; padding: 3px 9px; border-radius: 5px; }
        .td-actions { display: flex; gap: 8px; align-items: center; }

        .modal-overlay { display: none; position: fixed; inset: 0; background: rgba(0,0,0,0.4); z-index: 100; align-items: center; justify-content: center; padding: 1rem; overflow-y: auto; }
        .modal-overlay.open { display: flex; }
        .modal { background: #fff; border-radius: 12px; border: 1px solid #e2e4ea; padding: 2rem; width: 100%; max-width: 540px; box-shadow: 0 8px 40px rgba(0,0,0,0.15); margin: auto; }
        .modal h3 { font-size: 17px; font-weight: 600; margin-bottom: 4px; color: #1a1a2e; }
        .modal > p { font-size: 13px; color: #6b7280; margin-bottom: 1.5rem; }
        .modal-footer { display: flex; justify-content: flex-end; gap: 10px; margin-top: 1.5rem; padding-top: 1rem; border-top: 1px solid #f3f4f6; }

        .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
        .form-grid .full { grid-column: 1 / -1; }
        .form-group { display: flex; flex-direction: column; gap: 5px; }
        .form-group label { font-size: 12.5px; font-weight: 600; color: #374151; }
        .form-group input, .form-group select { height: 40px; border: 1px solid #d1d5db; border-radius: 7px; padding: 0 12px; font-size: 14px; font-family: inherit; color: #1a1a2e; background: #fff; outline: none; transition: border-color 0.15s, box-shadow 0.15s; }
        .form-group input:focus, .form-group select:focus { border-color: #1a3a6b; box-shadow: 0 0 0 3px rgba(26,58,107,0.1); }

        .delete-warning { background: #fef2f2; border: 1px solid #fecaca; border-radius: 8px; padding: 12px 16px; font-size: 13.5px; color: #b91c1c; margin-bottom: 1rem; }

        footer { background: #1a3a6b; color: rgba(255,255,255,0.65); text-align: center; padding: 14px 2rem; font-size: 12px; }
        footer span { color: rgba(255,255,255,0.9); font-weight: 500; }

        @media (max-width: 768px) {
            main { padding: 1rem; }
            .filter-bar { padding: 0.9rem 1rem; }
            .form-grid { grid-template-columns: 1fr; }
            .form-grid .full { grid-column: 1; }
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
            <span>Yönetici Paneli</span>
        </div>
    </a>
    <div class="header-right">
        <span class="admin-badge">${oturumEmail}</span>
        <a href="/logout">Çıkış Yap</a>
    </div>
</header>

<div class="filter-bar">
    <div class="filter-group">
        <label>Birim</label>
        <select id="deptFilter" onchange="filterTable()">
            <option value="">Tüm Birimler</option>
            <c:forEach var="d" items="${departments}">
                <option value="${d.name}">${d.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="filter-group">
        <label>Kişi Ara</label>
        <input type="text" id="searchInput" placeholder="Ad, Soyad, Birim, Bölüm..." oninput="filterTable()">
    </div>
</div>

<main>
    <c:if test="${not empty mesaj}">
        <div class="alert alert-success">
            <svg width="16" height="16" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>
            <span>${mesaj}</span>
        </div>
    </c:if>
    <c:if test="${not empty hata}">
        <div class="alert alert-error">
            <svg width="16" height="16" fill="none" stroke="currentColor" viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
            <span>${hata}</span>
        </div>
    </c:if>

    <div class="section-header">
        <h2>Kişi Yönetimi</h2>
        <button class="btn btn-primary" onclick="openModal('modalEkle')">
            <svg width="14" height="14" fill="none" stroke="currentColor" viewBox="0 0 24 24"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
            Yeni Kişi Ekle
        </button>
    </div>

    <div class="table-card">
        <div class="table-inner-header">
            <h3>Kayıtlı Kişiler</h3>
            <span class="count-badge" id="resultCount">
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
                <th>Dahili</th>
                <th>E-posta</th>
                <th>İşlem</th>
            </tr>
            </thead>
            <tbody id="personTable">

            <c:choose>
                <c:when test="${empty kisiler}">
                    <tr>
                        <td colspan="7" style="padding:2.5rem;text-align:center;color:#9ca3af;font-size:14px;">Henüz kayıtlı kişi bulunmuyor.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="k" items="${kisiler}">
                        <tr class="person-row"
                            data-name="${k.firstName} ${k.lastName}"
                            data-ext="${not empty k.extensionNumber ? k.extensionNumber : ''}"
                            data-dept="${not empty k.deptName ? k.deptName : ''}"
                            data-person-id="${k.personId}"
                            data-first-name="${k.firstName}"
                            data-last-name="${k.lastName}"
                            data-title="${not empty k.titleName ? k.titleName : ''}"
                            data-extension="${not empty k.extensionNumber ? k.extensionNumber : ''}"
                            data-room="${not empty k.roomNumber ? k.roomNumber : ''}"
                            data-email="${not empty k.email ? k.email : ''}"
                            data-sub-id="${not empty k.subDeptId ? k.subDeptId : 0}">

                            <td>
                                <div class="person-name">${k.firstName} ${k.lastName}</div>
                                <div class="person-title">${k.titleName}</div>
                            </td>
                            <td>${not empty k.deptName ? k.deptName : '—'}</td>
                            <td>${not empty k.subDeptName ? k.subDeptName : '—'}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty k.roomNumber}">
                                        <span class="badge-room">${k.roomNumber}</span>
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty k.extensionNumber}">
                                        <span class="badge-ext">${k.extensionNumber}</span>
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty k.email}">
                                        <a style="color:#1a3a6b;font-size:13px;text-decoration:none;" href="mailto:${k.email}">${k.email}</a>
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div class="td-actions">
                                    <button class="btn btn-outline btn-sm" onclick="openEditFromRow(this)">Düzenle</button>
                                    <button class="btn btn-danger btn-sm"  onclick="openDeleteFromRow(this)">Sil</button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</main>

<footer><span>2026</span> — BM470 - Ders Projesi</footer>

<div class="modal-overlay" id="modalEkle">
    <div class="modal">
        <h3>Yeni Kişi Ekle</h3>
        <p>Kişinin bilgilerini doldurun.</p>
        <form action="/admin/persons/create" method="post">
            <div class="form-grid">
                <div class="form-group">
                    <label>Ad</label>
                    <input type="text" name="firstName" placeholder="Ahmet" required>
                </div>
                <div class="form-group">
                    <label>Soyad</label>
                    <input type="text" name="lastName" placeholder="Yılmaz" required>
                </div>
                <div class="form-group full">
                    <label>Unvan</label>
                    <input type="text" name="titleName" placeholder="Doç. Dr., Öğr. Gör., ...">
                </div>
                <div class="form-group">
                    <label>Dahili No</label>
                    <input type="text" name="extensionNumber" placeholder="1234">
                </div>
                <div class="form-group">
                    <label>Oda No</label>
                    <input type="text" name="roomNumber" placeholder="A-201">
                </div>
                <div class="form-group full">
                    <label>E-posta</label>
                    <input type="email" name="email" placeholder="ahmet@kurum.edu.tr">
                </div>
                <div class="form-group">
                    <label>Birim</label>
                    <select id="ekle-deptSelect" onchange="loadSubDepts('ekle-deptSelect','ekle-subSelect')">
                        <option value="">— Birim Seç —</option>
                        <c:forEach var="d" items="${departments}">
                            <option value="${d.departmentId}">${d.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Bölüm</label>
                    <select id="ekle-subSelect" name="subDepartmentId">
                        <option value="">— Önce Birim Seçin —</option>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalEkle')">İptal</button>
                <button type="submit" class="btn btn-primary">Kaydet</button>
            </div>
        </form>
    </div>
</div>

<div class="modal-overlay" id="modalDuzenle">
    <div class="modal">
        <h3>Kişiyi Düzenle</h3>
        <p>Güncellemek istediğiniz alanları değiştirin.</p>
        <form action="/admin/persons/update" method="post">
            <input type="hidden" name="personId" id="editPersonId">
            <div class="form-grid">
                <div class="form-group">
                    <label>Ad</label>
                    <input type="text" name="firstName" id="editFirstName" required>
                </div>
                <div class="form-group">
                    <label>Soyad</label>
                    <input type="text" name="lastName" id="editLastName" required>
                </div>
                <div class="form-group full">
                    <label>Unvan</label>
                    <input type="text" name="titleName" id="editTitleName">
                </div>
                <div class="form-group">
                    <label>Dahili No</label>
                    <input type="text" name="extensionNumber" id="editExtension">
                </div>
                <div class="form-group">
                    <label>Oda No</label>
                    <input type="text" name="roomNumber" id="editRoom">
                </div>
                <div class="form-group full">
                    <label>E-posta</label>
                    <input type="email" name="email" id="editEmail">
                </div>
                <div class="form-group">
                    <label>Birim</label>
                    <select id="edit-deptSelect" onchange="loadSubDepts('edit-deptSelect','edit-subSelect')">
                        <option value="">— Birim Seç —</option>
                        <c:forEach var="d" items="${departments}">
                            <option value="${d.departmentId}">${d.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Bölüm</label>
                    <select id="edit-subSelect" name="subDepartmentId">
                        <option value="">— Önce Birim Seçin —</option>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalDuzenle')">İptal</button>
                <button type="submit" class="btn btn-primary">Güncelle</button>
            </div>
        </form>
    </div>
</div>

<div class="modal-overlay" id="modalSil">
    <div class="modal">
        <h3>Kişiyi Sil</h3>
        <div class="delete-warning">
            <strong id="deleteName"></strong> adlı kişi kalıcı olarak silinecek. Bu işlem geri alınamaz.
        </div>
        <form action="/admin/persons/delete" method="post">
            <input type="hidden" name="personId" id="deletePersonId">
            <div class="modal-footer">
                <button type="button" class="btn btn-outline" onclick="closeModal('modalSil')">Vazgeç</button>
                <button type="submit" class="btn btn-danger" style="background:#b91c1c;color:#fff;border-color:#b91c1c;">Evet, Sil</button>
            </div>
        </form>
    </div>
</div>

<script>
    // Controller'dan gönderilen JSON verisini JavaScript değişkenine alıyoruz
    // (Eğer Controller'da ObjectMapper silmediysen subDepartmentsJson olarak gelir)
    const allSubDepts = ${not empty subDepartmentsJson ? subDepartmentsJson : '[]'};

    function openModal(id)  { document.getElementById(id).classList.add('open'); }
    function closeModal(id) { document.getElementById(id).classList.remove('open'); }

    document.querySelectorAll('.modal-overlay').forEach(o => {
        o.addEventListener('click', e => { if (e.target === o) o.classList.remove('open'); });
    });

    function loadSubDepts(deptSelId, subSelId, selectedSubId) {
        const deptId = parseInt(document.getElementById(deptSelId).value) || 0;
        const subSel = document.getElementById(subSelId);
        subSel.innerHTML = '<option value="">— Bölüm Seç —</option>';
        if (!deptId) return;
        allSubDepts
            .filter(s => s.departmentId === deptId)
            .forEach(s => {
                const opt = document.createElement('option');
                opt.value = s.subDepartmentId;
                opt.textContent = s.name;
                if (selectedSubId && s.subDepartmentId === parseInt(selectedSubId)) opt.selected = true;
                subSel.appendChild(opt);
            });
    }

    function openEditFromRow(btn) {
        const row = btn.closest('tr');
        const d   = row.dataset;
        document.getElementById('editPersonId').value  = d.personId;
        document.getElementById('editFirstName').value = d.firstName;
        document.getElementById('editLastName').value  = d.lastName;
        document.getElementById('editTitleName').value = d.title     || '';
        document.getElementById('editExtension').value = d.extension || '';
        document.getElementById('editRoom').value      = d.room      || '';
        document.getElementById('editEmail').value     = d.email     || '';

        const subId = d.subId || '0';
        const sub   = allSubDepts.find(s => s.subDepartmentId === parseInt(subId));
        if (sub) {
            document.getElementById('edit-deptSelect').value = sub.departmentId;
            loadSubDepts('edit-deptSelect', 'edit-subSelect', subId);
        } else {
            document.getElementById('edit-deptSelect').value = '';
            document.getElementById('edit-subSelect').innerHTML = '<option value="">— Önce Birim Seçin —</option>';
        }
        openModal('modalDuzenle');
    }

    function openDeleteFromRow(btn) {
        const row = btn.closest('tr');
        document.getElementById('deletePersonId').value   = row.dataset.personId;
        document.getElementById('deleteName').textContent = row.dataset.name;
        openModal('modalSil');
    }

    function filterTable() {
        const q    = document.getElementById('searchInput').value.toLowerCase().trim();
        const dept = document.getElementById('deptFilter').value.toLowerCase().trim();
        const rows = document.querySelectorAll('#personTable .person-row');
        let visible = 0;

        rows.forEach(row => {
            const rowText = row.innerText.toLowerCase();
            const matchSearch = !q || rowText.includes(q);
            const matchDept = !dept || (row.dataset.dept || '').toLowerCase() === dept;

            if (matchSearch && matchDept) {
                row.style.display = '';
                visible++;
            } else {
                row.style.display = 'none';
            }
        });

        document.getElementById('resultCount').querySelector('span').textContent = visible;
    }
</script>

</body>
</html>