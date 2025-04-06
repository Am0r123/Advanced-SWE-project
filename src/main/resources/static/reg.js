window.addEventListener('DOMContentLoaded', (event) => {
    var urlParams = new URLSearchParams(window.location.search);
    
    if (urlParams.has('success')) {
        showNotification(urlParams.get('success'), false);
        removeUrlParam('success'); 
    } else if (urlParams.has('error')) {
        showNotification(urlParams.get('error'), true);
        removeUrlParam('error');
    }
    
    setTimeout(() => {
        const successMessage = document.getElementById('success-message');
        const errorMessage = document.getElementById('error-message');
        
        if (successMessage) {
            successMessage.style.transition = "opacity 1s";
            successMessage.style.opacity = 0;
            setTimeout(() => { successMessage.remove(); }, 1000);
        }
        
        if (errorMessage) {
            errorMessage.style.transition = "opacity 1s";
            errorMessage.style.opacity = 0;
            setTimeout(() => { errorMessage.remove(); }, 1000);
        }
    }, 5000);
});

function removeUrlParam(param) {
    var urlParams = new URLSearchParams(window.location.search);
    urlParams.delete(param);
    window.history.replaceState(null, null, window.location.pathname + '?' + urlParams.toString());
}

function showNotification(message, isError) {
    const notification = document.createElement('div');
    notification.classList.add(isError ? 'alert-danger' : 'alert-success');
    notification.textContent = message;
}
