import { PuzzleTimer } from './puzzletimer.js';

document.addEventListener('DOMContentLoaded', (event) => {
    const timer = new PuzzleTimer();
    timer.startTimeCounter();

    document.addEventListener('puzzleSolved', (e) => {
        timer.stopTimer();
    });
});


