function showSection(sectionId) {
    const sections = document.querySelectorAll('.dynamic-section');
    sections.forEach(section => {
        section.classList.add('hidden');
    });
    const selectedSection = document.getElementById(sectionId);
    if (selectedSection) {
        selectedSection.classList.remove('hidden');
    }
}
function show(id,userData){
  console.log(userData);
  if(userData?.type == 'user'){
    console.log(userData);
    document.getElementById('userPasswordedit').value = userData.password;
    document.getElementById('userworkoutIdedit').value = userData.workout_id;
    document.getElementById('edituserid').value = userData.ID;
    document.getElementById('userNameedit').value = userData.name;
    document.getElementById('userEmailedit').value = userData.email;
  }
  else if(userData?.type == 'admin'){
    console.log(userData);
    document.getElementById('adminPasswordedit').value = userData.password;
    document.getElementById('editadminid').value = userData.ID;
    document.getElementById('adminNameedit').value = userData.name;
    document.getElementById('adminEmailedit').value = userData.email;
  }
  else if(userData?.type == 'coach'){
    console.log(userData);
    document.getElementById('coachPasswordedit').value = userData.password;
    document.getElementById('editcoachid').value = userData.ID;
    document.getElementById('coachNameedit').value = userData.name;
    document.getElementById('coachEmailedit').value = userData.email;
  }
  else if(userData?.payment_name){
        console.log(userData);
        document.getElementById('editpaymentid').value = userData.ID;
        document.getElementById('paymentNameedit').value = userData.payment_name;
        document.getElementById('paymentPriceedit').value = userData.price;
        document.getElementById('paymentDescriptionedit').value = userData.description;
      }
  else if(userData?.price){
    console.log(userData);
    document.getElementById('editproductid').value = userData.ID;
    document.getElementById('productPriceedit').value =  userData.price;
    document.getElementById('productNameedit').value = userData.name;
    document.getElementById('productDescriptionedit').value = userData.description;
  }
  else if(userData?.description){
    console.log(userData);
    document.getElementById('editworkoutid').value = userData.ID;
    document.getElementById('workoutNameedit').value = userData.name;
    document.getElementById('workoutDescriptionedit').value = userData.description;
  }
  console.log(id);
    const sections = document.querySelectorAll('.dynamic-section');
    sections.forEach(section => {
        section.classList.add('hidden');
    });
    const selectedSection = document.getElementById(id);
    if (selectedSection) {
        selectedSection.classList.remove('hidden');
    }
}
function back(id){
  const sections1 = document.querySelectorAll('.dynamic-section');
  sections1.forEach(section => {
      section.classList.add('hidden');
  });
  const sections = document.querySelectorAll('.insights');
  sections.forEach(section => {
      section.classList.remove('hidden');
  });
  const selectedSection = document.getElementById(id);
    if (selectedSection) {
        selectedSection.classList.remove('hidden');
    }
}
let users = [];
let admins = [];
let products = [];
let workouts = [];
function addUser() {
const name = document.getElementById('userName').value;
const email = document.getElementById('userEmail').value;

if (name && email) {
    users.push({ name, email });
    updateUserTable();
    document.getElementById('addUserForm').reset();
}
}

function updateUserTable() {
const tableBody = document.getElementById('userTable').querySelector('tbody');
tableBody.innerHTML = '';

users.forEach((user, index) => {
    const row = `
    <tr>
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td>
        <button onclick="deleteUser(${index})" class="btn-danger">Delete</button>
        </td>
    </tr>`;
    tableBody.innerHTML += row;
});
}

function deleteUser(index) {
users.splice(index, 1);
updateUserTable();
}

function addAdmin() {
const name = document.getElementById('adminName').value;
const email = document.getElementById('adminEmail').value;

if (name && email) {
    admins.push({ name, email });
    updateAdminTable();
    document.getElementById('addAdminForm').reset();
}
}

function updateAdminTable() {
const tableBody = document.getElementById('adminTable').querySelector('tbody');
tableBody.innerHTML = '';

admins.forEach((admin, index) => {
    const row = `
    <tr>
        <td>${admin.name}</td>
        <td>${admin.email}</td>
        <td>
        <button onclick="deleteAdmin(${index})" class="btn-danger">Delete</button>
        </td>
    </tr>`;
    tableBody.innerHTML += row;
});
}

function deleteAdmin(index) {
admins.splice(index, 1);
updateAdminTable();
}

function showPopupMessage(message, type) {
  const popup = document.getElementById('popupMessage');
  popup.textContent = message;

  popup.className = type === 'success' ? 'success' : 'error';

  popup.style.opacity = '1';
  popup.style.transform = 'translateY(0)';
  popup.style.display = 'block';

  setTimeout(() => {
      popup.style.opacity = '0';
      popup.style.transform = 'translateY(-10px)';
      setTimeout(() => {
          popup.style.display = 'none';
          popup.className = 'hidden';
      }, 300);
  }, 3000);
}

function showNotification(message, isError) {
    var notification = document.getElementById("notification");
    notification.textContent = message;
    notification.classList.remove("error");
    if (isError) {
        notification.classList.add("error");
    }
    notification.style.display = "block";
    setTimeout(function() {
        notification.style.display = "none";
    }, 5000);
}
function reveal(form, table) {
    document.getElementById(form).classList.remove('hidden');
    document.querySelectorAll('.reveal_btn').forEach(function(button) {
        button.classList.add('hidden');
    });
    document.getElementById(table).classList.add('hidden');
    document.querySelectorAll('.addusertitle').forEach(function(button) {
        button.classList.remove('hidden');
    });
    document.querySelectorAll('.addadmintitle').forEach(function(button) {
        button.classList.remove('hidden');
    });
    document.getElementById('userlisttitle').classList.add('hidden');
    document.getElementById('adminlisttitle').classList.add('hidden');
}
function goback(form, table) {
    document.getElementById(form).classList.add('hidden');
    document.querySelectorAll('.reveal_btn').forEach(function(button) {
        button.classList.remove('hidden');
    });
    document.getElementById(table).classList.remove('hidden');
    document.querySelectorAll('.addusertitle').forEach(function(button) {
        button.classList.add('hidden');
    });
    document.querySelectorAll('.addadmintitle').forEach(function(button) {
        button.classList.add('hidden');
    });
    document.getElementById('userlisttitle').classList.remove('hidden');
    document.getElementById('adminlisttitle').classList.remove('hidden');
}

window.onload = function() {
    var urlParams = new URLSearchParams(window.location.search);
    
    if (urlParams.has('success')) {
        showNotification(urlParams.get('success'), false);
        removeUrlParam('success');
    } else if (urlParams.has('error')) {
        showNotification(urlParams.get('error'), true);
        removeUrlParam('error');
    }
};

function removeUrlParam(param) {
    var urlParams = new URLSearchParams(window.location.search);
    urlParams.delete(param);
    window.history.replaceState(null, null, window.location.pathname + '?' + urlParams.toString());
}