class PuzzleTimer {
    constructor() {
        this.startTime = Math.floor(Date.now() / 1000);
        this.timeoutId = 0;
        localStorage.setItem("startTime", this.startTime);
    }

    startTimeCounter() {
        const now = Math.floor(Date.now() / 1000);
        const diff = now - this.startTime;
        const h = Math.floor(diff / 3600);
        const m = Math.floor((diff / 60) % 60);
        const s = Math.floor(diff % 60);

        const secondsElement = document.querySelector(".seconds");
        const minutesElement = document.querySelector(".minutes");
        const hoursElement = document.querySelector(".hours");

        if (secondsElement && minutesElement && hoursElement) {
            secondsElement.innerHTML = this.pad(s);
            minutesElement.innerHTML = this.pad(m);
            hoursElement.innerHTML = this.pad(h);
        }

        this.timeoutId = setTimeout(() => this.startTimeCounter(), 500);
    }

    pad(i) {
        return i < 10 ? "0" + i : i;
    }

    stopTimer() {
        clearTimeout(this.timeoutId);
    }
}

document.addEventListener('DOMContentLoaded', (event) => {
    const timer = new PuzzleTimer();
    timer.startTimeCounter();

    document.addEventListener('puzzleSolved', (e) => {
        timer.stopTimer();
    });
});
