/*
  script.js - attendance logging for the web UI
  - accepts any Student ID and logs timestamp
  - persists entries in localStorage
  - renders entries in the right-hand log table
  - supports CSV download and clearing the log
*/

const STORAGE_KEY = 'attendance_log_v1';
const VALID_ID = '12-3456-789';
const VALID_PASSWORD = '12345678';
let beepSound;

function Gettime() {
    const now = new Date();
    const mm = String(now.getMonth() + 1).padStart(2, '0');
    const dd = String(now.getDate()).padStart(2, '0');
    const yyyy = now.getFullYear();
    const hh = String(now.getHours()).padStart(2, '0');
    const min = String(now.getMinutes()).padStart(2, '0');
    const ss = String(now.getSeconds()).padStart(2, '0');
    return `${mm}/${dd}/${yyyy} ${hh}:${min}:${ss}`;
}

function loadRecords(){
    try{
        const raw = localStorage.getItem(STORAGE_KEY);
        return raw ? JSON.parse(raw) : [];
    }catch(e){ return []; }
}

function saveRecords(records){
    localStorage.setItem(STORAGE_KEY, JSON.stringify(records));
}

function playBeep(){
    if(beepSound){ beepSound.currentTime = 0; beepSound.play().catch(()=>{}); }
}

function renderTable(){
    const tbody = document.querySelector('#logTable tbody');
    tbody.innerHTML = '';
    const records = loadRecords();
    records.forEach(r => {
        const tr = document.createElement('tr');
        const tdTime = document.createElement('td'); tdTime.textContent = r.timestamp;
        const tdId = document.createElement('td'); tdId.textContent = r.id;
        tr.appendChild(tdTime); tr.appendChild(tdId);
        tbody.appendChild(tr);
    });
    const count = records.length;
    const logCount = document.getElementById('logCount');
    if(logCount) logCount.textContent = count ? `${count} entr${count===1? 'y':'ies'}` : 'No entries yet';
    const downloadBtn = document.getElementById('downloadBtn');
    if(downloadBtn) downloadBtn.style.display = count ? 'inline-block' : 'none';
}

function addRecord(id){
    const records = loadRecords();
    const rec = { id: id, timestamp: Gettime() };
    records.unshift(rec);
    saveRecords(records);
    renderTable();
}

function downloadCSV(){
    const records = loadRecords();
    if(!records.length){ alert('No records to download'); return; }
    const lines = ['"Time","Student ID"'];
    records.slice().reverse().forEach(r => {
        const t = `"${r.timestamp.replace(/"/g,'""')}"`;
        const id = `"${String(r.id).replace(/"/g,'""')}"`;
        lines.push(`${t},${id}`);
    });
    const csv = lines.join('\n');
    const blob = new Blob([csv], {type:'text/csv;charset=utf-8;'});
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a'); a.href = url;
    a.download = `attendance_${new Date().toISOString().slice(0,19).replace(/[:T]/g,'-')}.csv`;
    document.body.appendChild(a); a.click(); a.remove(); URL.revokeObjectURL(url);
}

function clearRecords(){
    if(!confirm('Clear all attendance entries?')) return;
    localStorage.removeItem(STORAGE_KEY);
    renderTable();
}

window.addEventListener('load', ()=>{
    // initialize beep (optional)
    beepSound = new Audio('beep.mp3');
    renderTable();

    const submitBtn = document.getElementById('submitBtn');
    const idInput = document.getElementById('id');
    const pwInput = document.getElementById('password');
    const messageDiv = document.getElementById('message');
    const downloadBtn = document.getElementById('downloadBtn');
    const downloadLog = document.getElementById('downloadLog');
    const clearBtn = document.getElementById('clearLog');

    function flash(msg, ok=true){
        if(!messageDiv) return;
        messageDiv.textContent = msg;
        messageDiv.className = ok ? 'success' : 'error';
        messageDiv.style.display = 'block';
        setTimeout(()=>{ messageDiv.style.display = 'none'; }, 2500);
    }

    submitBtn.addEventListener('click', ()=>{
        const idVal = idInput.value.trim();
        const pwVal = pwInput ? pwInput.value.trim() : '';
        if(!idVal){ playBeep(); flash('Please enter Student ID', false); idInput.focus(); return; }

        // Validate credentials before recording
        if(idVal !== VALID_ID || pwVal !== VALID_PASSWORD){
            playBeep();
            flash('Invalid ID or Password', false);
            if(pwInput) pwInput.value = '';
            return; // do not record
        }

        // valid - record attendance
        addRecord(idVal);
        flash('Attendance recorded');
        idInput.value = '';
        if(pwInput) pwInput.value = '';
    });

    idInput.addEventListener('keypress', (e)=>{ if(e.key==='Enter') submitBtn.click(); });
    if(pwInput) pwInput.addEventListener('keypress', (e)=>{ if(e.key==='Enter') submitBtn.click(); });

    if(downloadBtn) downloadBtn.addEventListener('click', downloadCSV);
    if(downloadLog) downloadLog.addEventListener('click', downloadCSV);
    if(clearBtn) clearBtn.addEventListener('click', clearRecords);
});