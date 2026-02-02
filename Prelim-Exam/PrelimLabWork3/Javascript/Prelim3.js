// Prelim3.js
// Handles UI logic for Prelim.html

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('calcForm');
  const attendanceEl = document.getElementById('attendance');
  const l1El = document.getElementById('l1');
  const l2El = document.getElementById('l2');
  const l3El = document.getElementById('l3');
  const resultsEl = document.getElementById('results');
  const clearBtn = document.getElementById('clear');

  form.addEventListener('submit', (ev) => {
    ev.preventDefault();
    calculateAndShow();
  });

  [attendanceEl, l1El, l2El, l3El].forEach(el => {
    el.addEventListener('keydown', (e) => {
      if (e.key === 'Enter') {
        e.preventDefault();
        calculateAndShow();
      }
    });
  });

  clearBtn.addEventListener('click', () => {
    attendanceEl.value = '';
    l1El.value = '';
    l2El.value = '';
    l3El.value = '';
    resultsEl.textContent = '--- Results will appear here ---';
    clearErrors();
    attendanceEl.focus();
  });

  function calculateAndShow() {
    clearErrors();
    const attendance = parseFloat(attendanceEl.value);
    const l1 = parseFloat(l1El.value);
    const l2 = parseFloat(l2El.value);
    const l3 = parseFloat(l3El.value);

    if (!isFiniteNumber(attendance)) return showFieldError(attendanceEl, 'Enter a valid attendance value');
    if (!isFiniteNumber(l1)) return showFieldError(l1El, 'Enter Lab Work 1');
    if (!isFiniteNumber(l2)) return showFieldError(l2El, 'Enter Lab Work 2');
    if (!isFiniteNumber(l3)) return showFieldError(l3El, 'Enter Lab Work 3');

    if (!inRange(attendance,0,100)) return showFieldError(attendanceEl, 'Attendance must be 0–100');
    if (!inRange(l1,0,100)) return showFieldError(l1El, 'Lab Work 1 must be 0–100');
    if (!inRange(l2,0,100)) return showFieldError(l2El, 'Lab Work 2 must be 0–100');
    if (!inRange(l3,0,100)) return showFieldError(l3El, 'Lab Work 3 must be 0–100');

    const la = (l1 + l2 + l3) / 3.0;
    const cs = 0.4 * attendance + 0.6 * la;
    const examForPassing = (75.0 - 0.3 * cs) / 0.7;
    const examForExcellent = (100.0 - 0.3 * cs) / 0.7;

    const fmt = n => Number.isFinite(n) ? n.toFixed(2) : 'N/A';

    const out = [];
    out.push('--- Results ---');
    out.push('Attendance: ' + fmt(attendance));
    out.push('Lab Work 1: ' + fmt(l1));
    out.push('Lab Work 2: ' + fmt(l2));
    out.push('Lab Work 3: ' + fmt(l3));
    out.push('Lab Work Average: ' + fmt(la));
    out.push('Class Standing: ' + fmt(cs));
    out.push('');
    out.push('Exam needed for Passing (PG=75): ' + fmt(examForPassing));
    out.push('Exam needed for Excellent (PG=100): ' + fmt(examForExcellent));

    resultsEl.textContent = out.join('\n');
  }

  function isFiniteNumber(v){
    return typeof v === 'number' && Number.isFinite(v);
  }

  function inRange(v,min,max){ return v >= min && v <= max; }

  function showFieldError(el, message){
    el.classList.add('invalid');
    el.focus();
    flashMessage(message);
  }

  function clearErrors(){
    [attendanceEl, l1El, l2El, l3El].forEach(e => e.classList.remove('invalid'));
  }

  function flashMessage(msg){
    // small ephemeral notice in results area
    const prev = resultsEl.textContent;
    resultsEl.textContent = 'Error: ' + msg;
    resultsEl.style.background = '#3b0b0b';
    setTimeout(()=>{ resultsEl.textContent = prev; resultsEl.style.background = ''; }, 1800);
  }
});

