
document.addEventListener("DOMContentLoaded", function () {
    const notificationList = document.getElementById("notificationList");
    const readAllButton = document.getElementById("readAllButton");
    const notifications = JSON.parse(localStorage.getItem("notifications")) || [
        { message: "New task added to your timeline", time: "Just now", new: true },
        { message: "Reminder: Meeting at 3 PM", time: "10 min ago", new: false },
        { message: "Your event has been updated", time: "1 hour ago", new: true },
        { message: "You have 3 new messages", time: "Yesterday", new: false },
    ];
    localStorage.setItem("notifications", JSON.stringify(notifications));

    function displayNotifications() {
        notificationList.innerHTML = "";

        notifications.forEach((notification, index) => {
            const notifElement = document.createElement("div");
            notifElement.classList.add("notification");
            if (notification.new) {
                notifElement.classList.add("new");
            }

            notifElement.innerHTML = `
                <span class="icon">🔔</span>
                <span class="text">${notification.message}</span>
                <span class="time">${notification.time}</span>
            `;

            notifElement.addEventListener("click", () => {
                notifications[index].new = false;
                localStorage.setItem("notifications", JSON.stringify(notifications));
                displayNotifications();
            });

            notificationList.appendChild(notifElement);
        });
    }

    readAllButton.addEventListener("click", () => {
        notifications.forEach(notification => {
            notification.new = false;
        });
        localStorage.setItem("notifications", JSON.stringify(notifications));
        displayNotifications();
    });

    displayNotifications();
});