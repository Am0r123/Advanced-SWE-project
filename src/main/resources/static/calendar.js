document.addEventListener('DOMContentLoaded', function() {
  const monthYearDisplay = document.getElementById('month-year');
  const prevMonthButton = document.getElementById('prev-month');
  const nextMonthButton = document.getElementById('next-month');
  const weekdaysContainer = document.querySelector('.calendar-weekdays');
  const datesContainer = document.querySelector('.calendar-dates');

  const weekdays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
  let currentDate = new Date();
  let notes = {};

  function renderWeekdays() {
      weekdaysContainer.innerHTML = '';
      weekdays.forEach(day => {
          const dayElement = document.createElement('div');
          dayElement.textContent = day;
          weekdaysContainer.appendChild(dayElement);
      });
  }

  function renderCalendar() {
      datesContainer.innerHTML = '';
      const firstDayOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
      const lastDayOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 0);
      const startDay = firstDayOfMonth.getDay();
      const totalDays = lastDayOfMonth.getDate();

      monthYearDisplay.textContent = `${currentDate.toLocaleString('default', { month: 'long' })} ${currentDate.getFullYear()}`;

      for (let i = 0; i < startDay; i++) {
          const emptyCell = document.createElement('div');
          datesContainer.appendChild(emptyCell);
      }

      for (let day = 1; day <= totalDays; day++) {
          const dateCell = document.createElement('div');
          dateCell.textContent = day;
          dateCell.classList.add('date-cell');

          const noteInput = document.createElement('textarea');
          noteInput.classList.add('note-input');
          noteInput.placeholder = "Note...";
          noteInput.value = notes[`${currentDate.getFullYear()}-${currentDate.getMonth()}-${day}`] || "";

          noteInput.addEventListener('input', function() {
              notes[`${currentDate.getFullYear()}-${currentDate.getMonth()}-${day}`] = noteInput.value;
          });

          dateCell.appendChild(noteInput);

          const today = new Date();
          if (
              day === today.getDate() &&
              currentDate.getMonth() === today.getMonth() &&
              currentDate.getFullYear() === today.getFullYear()
          ) {
              dateCell.classList.add('current-date');
          }

          datesContainer.appendChild(dateCell);
      }
  }

  prevMonthButton.addEventListener('click', function() {
      currentDate.setMonth(currentDate.getMonth() - 1);
      renderCalendar();
  });

  nextMonthButton.addEventListener('click', function() {
      currentDate.setMonth(currentDate.getMonth() + 1);
      renderCalendar();
  });

  renderWeekdays();
  renderCalendar();
});
