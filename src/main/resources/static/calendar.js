 <script th:inline="javascript">// hya5od el value mn controller w y7otha fe java script
    let backendEvents = /*[[${events}]]*/ []; //bta5od el events mn controller w y7otha fe el database 
    console.log("Backend Events:", backendEvents);
    const weekdays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    const calendarDates = document.getElementById("calendar-dates");
    const monthYear = document.getElementById("month-year");
    const weekdaysDiv = document.getElementById("weekdays");
    const selectedDateTitle = document.getElementById("selected-date-title");
    const eventList = document.getElementById("event-list");
    const addEventBtn = document.getElementById("add-event");

    let currentDate = new Date();
    let selectedDate = null;

    weekdaysDiv.innerHTML = weekdays.map(day => `<div>${day}</div>`).join("");

    let eventsMap = {};

    backendEvents.forEach(ev => {
      let evDate = ev.eventDate;
      if (!eventsMap[evDate]) {
        eventsMap[evDate] = [];
      }
      eventsMap[evDate].push(ev.title);
    });

    function renderCalendar() {
      calendarDates.innerHTML = "";
      const year = currentDate.getFullYear();
      const month = currentDate.getMonth();

      monthYear.textContent = `${currentDate.toLocaleString('default', { month: 'long' })} ${year}`;
      const firstDay = new Date(year, month, 1).getDay();
      const totalDays = new Date(year, month + 1, 0).getDate();

      for (let i = 0; i < firstDay; i++) {
        calendarDates.innerHTML += `<div class="empty"></div>`;
      }

      for (let day = 1; day <= totalDays; day++) {
        const dateDiv = document.createElement("div");
        dateDiv.textContent = day;

        const dateStr = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;

        if (eventsMap[dateStr]) {
          dateDiv.classList.add("has-note");
        }

        dateDiv.addEventListener("click", () => {
          selectedDate = dateStr;
          selectDate(dateDiv, dateStr);
        });

        calendarDates.appendChild(dateDiv);
      }
    }

    function loadEvents(dateStr) {
      eventList.innerHTML = "";
      const events = eventsMap[dateStr] || [];

      if (events.length === 0) {
        eventList.innerHTML = "<li>No events for this date.</li>";
      } else {
        events.forEach(event => {
          const li = document.createElement("li");
          li.textContent = `${event}`;
          eventList.appendChild(li);
        });
      }
    }

    function selectDate(div, dateStr) {
      document.querySelectorAll(".dates div").forEach(d => d.classList.remove("selected"));
      div.classList.add("selected");

      const [year, month, day] = dateStr.split("-");
      selectedDateTitle.textContent = `Events for ${day}/${month}/${year}`;
      loadEvents(dateStr);

      addEventBtn.onclick = () => {
        window.location.href = `/issues/create?selectedDate=${dateStr}&type=event`;
      };
    }

    document.getElementById("prev-month").addEventListener("click", () => {
      currentDate.setMonth(currentDate.getMonth() - 1);
      renderCalendar();
    });

    document.getElementById("next-month").addEventListener("click", () => {
      currentDate.setMonth(currentDate.getMonth() + 1);
      renderCalendar();
    });

    renderCalendar();
