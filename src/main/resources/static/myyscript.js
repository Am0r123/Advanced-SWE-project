const passwordField = document.getElementById('password');
const confirmPasswordField = document.getElementById('confirmPassword');

function checkPasswordsMatch() {
    if (passwordField!== confirmPasswordField) {
        alert("Passwords don't match!"); 
        return false;
    }
    return true; 
}
