const VALID_USERNAME = "admin";
const VALID_PASSWORD = "password123";

let attendanceRecords = [];
let beepAudio;

// Initialize audio
window.addEventListener('load', () => {
    beepAudio = new Audio('beep.mp3');
});

function playBeep() {
    if (beepAudio) {
        beepAudio.currentTime = 0;
        beepAudio.play().catch(() => {});
    }
}

function getTimestamp() {
    const now = new Date();
    const mm = String(now.getMonth() + 1).padStart(2, '0');
    const dd = String(now.getDate()).padStart(2, '0');
    const yyyy = now.getFullYear();
    const hh = String(now.getHours()).padStart(2, '0');
    const min = String(now.getMinutes()).padStart(2, '0');
    const ss = String(now.getSeconds()).padStart(2, '0');
    
    return `${mm}/${dd}/${yyyy} ${hh}:${min}:${ss}`;
}

function handleLogin() {
    
    
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const messageDiv = document.getElementById('message');
    
    if (username === VALID_USERNAME && password === VALID_PASSWORD) {
        const timestamp = getTimestamp();
        attendanceRecords.push({ username, password, timestamp });
        
        messageDiv.className = 'success';
        messageDiv.textContent = `Welcome, ${username}! Logged in at ${timestamp}`;
        messageDiv.style.display = 'block';
        
        document.getElementById('username').value = '';
        document.getElementById('password').value = '';
        document.getElementById('downloadBtn').style.display = 'block';
    } else {
        playBeep();
        messageDiv.className = 'error';
        messageDiv.textContent = 'Invalid username or password';
        messageDiv.style.display = 'block';
    }
}

function downloadReport() {
    
    if (attendanceRecords.length === 0) {
        alert('No attendance records');
        return;
    }
    
    let content = 'ATTENDANCE SUMMARY\n';
    content += '=' .repeat(40) + '\n\n';
    
    attendanceRecords.forEach((record, i) => {
        content += `${i + 1}. Username: ${record.username}\n`;
        content += `   Timestamp: ${record.timestamp}\n\n`;
    });
    
    const blob = new Blob([content], { type: 'text/plain' });
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = 'attendance_summary.txt';
    link.click();
}

document.getElementById('submitBtn').addEventListener('click', handleLogin);
document.getElementById('username').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') handleLogin();
});
document.getElementById('password').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') handleLogin();
});
document.getElementById('downloadBtn').addEventListener('click', downloadReport);

