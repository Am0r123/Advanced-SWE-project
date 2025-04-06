function toggleFilters() {
    const filterDiv = document.getElementById("filterContainer");
    filterDiv.style.display = (filterDiv.style.display === "none" || filterDiv.style.display === "") ? "block" : "none";
}
document.addEventListener("DOMContentLoaded", function () {
    const ctx = document.getElementById("timelineChart").getContext("2d");
    const tasks = [
        { name: "Task 1", start: "2025-03-01", end: "2025-03-05", status: "done" },
        { name: "Task 2", start: "2025-03-03", end: "2025-03-10", status: "in-progress" },
        { name: "Task 3", start: "2025-03-06", end: "2025-03-12", status: "done" },
        { name: "Task 4", start: "2025-03-08", end: "2025-03-15", status: "in-progress" },
    ];

    const colors = {
        "done": "green",
        "in-progress": "orange"
    };

    let chartInstance;

    function createChart(filteredTasks) {
        if (chartInstance) {
            chartInstance.destroy();
        }
        const chartData = {
            labels: filteredTasks.map(task => task.name),
            datasets: [{
                label: "Tasks",
                data: filteredTasks.map(task => ({
                    x: [new Date(task.start), new Date(task.end)],
                    y: task.name
                })),
                backgroundColor: filteredTasks.map(task => colors[task.status]),
                borderColor: "black",
                borderWidth: 1,
                borderSkipped: false
            }]
        };

        chartInstance = new Chart(ctx, {
            type: "bar",
            data: chartData,
            options: {
                indexAxis: 'y',
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    x: {
                        type: "time",
                        time: { unit: "day" },
                        title: { display: true, text: "Date" },
                        min: "2025-03-01",
                        max: "2025-03-20"
                    },
                    y: { title: { display: true, text: "Tasks" } }
                },
                plugins: {
                    legend: { display: false },
                    tooltip: {
                        callbacks: {
                            label: function (tooltipItem) {
                                let task = filteredTasks[tooltipItem.dataIndex];
                                return `Start: ${task.start} - End: ${task.end}`;
                            }
                        }
                    }
                }
            }
        });
    }

    function applyFilters() {
        const statusFilter = document.getElementById("statusFilter").value;
        const startDate = document.getElementById("startDate").value;
        const endDate = document.getElementById("endDate").value;

        let filteredTasks = tasks.filter(task => {
            const taskStart = new Date(task.start);
            const taskEnd = new Date(task.end);

            let matchesStatus = statusFilter === "all" || task.status === statusFilter;

            let matchesStartDate = true;
            let matchesEndDate = true;

            if (startDate) {
                const start = new Date(startDate);
                matchesStartDate = taskStart >= start;
            }

            if (endDate) {
                const end = new Date(endDate);
                matchesEndDate = taskEnd <= end;
            }

            return matchesStatus && matchesStartDate && matchesEndDate;
        });
        if(filteredTasks.length === 0){
            alert("no tasks found");
            statusFilter = 'all';
            startDate = '';
            endDate = '';
        }
        createChart(filteredTasks);
    }
    document.getElementById("applyButton").addEventListener("click", applyFilters);
    createChart(tasks);
});