let startTime = Math.floor(Date.now() / 1000); //Get the starting time (right now) in seconds
localStorage.setItem("startTime", startTime); // Store it if I want to restart the timer on the next page

function startTimeCounter() {
    let now = Math.floor(Date.now() / 1000); // get the time now
    let diff = now - startTime; // diff in seconds between now and start
    let m = Math.floor((diff / 60) % 60); // get minutes value (quotient of diff)
    let s = Math.floor(diff % 60); // get seconds value (remainder of diff)
    let h = Math.floor(diff / 3600); // get seconds value (remainder of diff)
    document.querySelector(".seconds").innerHTML = pad(s);
    document.querySelector(".minutes").innerHTML = pad(m);
    document.querySelector(".hours").innerHTML = pad(h);
    let t = setTimeout(startTimeCounter, 500); // set a timeout to update the timer
}

function pad(i) {
    if (i < 10) {i = "0" + i;}  // add zero in front of numbers < 10
    return i;
}

startTimeCounter();
