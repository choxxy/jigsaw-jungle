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

        document.querySelector(".seconds").innerHTML = this.pad(s);
        document.querySelector(".minutes").innerHTML = this.pad(m);
        document.querySelector(".hours").innerHTML = this.pad(h);

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
