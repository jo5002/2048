//Variablen
let spielfeld = [
  0, 0, 0, 0,
  0, 0, 0, 0,
  0, 0, 0, 0,
  0, 0, 0, 0
];

const left = [
    [ 3, 2, 1, 0],
    [ 7, 6, 5, 4],
    [11,10, 9, 8],
    [15,14,13,12]
];

const right = [
    [ 0, 1, 2, 3],
    [ 4, 5, 6, 7],
    [ 8, 9,10,11],
    [12,13,14,15]
];

const down = [
    [ 0, 4, 8,12],
    [ 1, 5, 9,13],
    [ 2, 6,10,14],
    [ 3, 7,11,15]
];

const up = [
    [12, 8, 4, 0],
    [13, 9, 5, 1],
    [14,10, 6, 2],
    [15,11, 7, 3]
];

let touchStartX = 0;
let touchStartY = 0;

//Funktionen
function getRandomInt(min, max) {
    const minCeiled = Math.ceil(min);
    const maxFloored = Math.floor(max);
    return Math.floor(Math.random() * (maxFloored - minCeiled) + minCeiled);
}

function processFields(firstIndex, secoundIndex) {
    if(spielfeld[firstIndex] == spielfeld[secoundIndex] && spielfeld[secoundIndex] > 0) {

        spielfeld[firstIndex] = spielfeld[firstIndex] * 2;
        spielfeld[secoundIndex] = 0;

        return true;
    }
    else {
        return false;
    }
}

function collapseRow(rowIndexes) {
    const fourthIndex = rowIndexes[0];
    const thirdIndex = rowIndexes[1];
    const secoundIndex = rowIndexes[2];
    const firstIndex = rowIndexes[3];

    if(processFields(firstIndex, secoundIndex)) {
        processFields(thirdIndex, fourthIndex);
    }
    else {
        if(!processFields(secoundIndex, thirdIndex)) {
            processFields(thirdIndex, fourthIndex);
        }
    }

    let freiePositionen = [];
    for(let i = 3; i >= 0; i--) {
        const index = rowIndexes[i];
        if(spielfeld[index] == 0) {
            freiePositionen.push(index);
        }
        else {
            if(freiePositionen.length >= 0) {
                spielfeld[freiePositionen.shift()] = spielfeld[index];
                spielfeld[index] = 0;
            }
        }
    }
}

function verarbeiteSpielfeld(indexRichtungArray) {
    indexRichtungArray.forEach(zeile => collapseRow(zeile));
    updateHTMLVonArray(spielfeld);
}

const moveLeft = () => verarbeiteSpielfeld(left);
const moveRight = () => verarbeiteSpielfeld(right);
const moveDown = () => verarbeiteSpielfeld(down);
const moveUp = () => verarbeiteSpielfeld(up);

function updateHTMLVonArray(array) {
    for(let i = 0; i < array.length; i++) {

        const feld = felder[i];
        const wert = array[i];

        feld.textContent = wert > 0 ? wert : "";

        feld.className = "feld feld-" + wert;
    }
}

//Event Listener
document.addEventListener("keydown", (event) => {
    switch (event.key) {
        case "ArrowUp":
            moveUp();
            break;
        case "ArrowDown":
            moveDown();
            break;
        case "ArrowLeft":
            moveLeft();
            break;
        case "ArrowRight":
            moveRight();
            break;
    }
});

document.addEventListener("touchstart", (e) => {
    const touch = e.touches[0];
    touchStartX = touch.clientX;
    touchStartY = touch.clientY;
});

document.addEventListener("touchend", (e) => {
    const touch = e.changedTouches[0];
    const deltaX = touch.clientX - touchStartX;
    const deltaY = touch.clientY - touchStartY;

    if (Math.abs(deltaX) > Math.abs(deltaY)) {
        // Horizontal Swipe
        if (deltaX > 30) moveRight();
        else if (deltaX < -30) moveLeft();
    } else {
        // Vertikale Swipe
        if (deltaY > 30) moveDown();
        else if (deltaY < -30) moveUp();
    }
});

//Initialiserung
felder = document.querySelectorAll('.feld');
let cellIndex1 = getRandomInt(0, 15);
let cellIndex2 = 0;
do {
    cellIndex2 = getRandomInt(0, 15);
} while (cellIndex1 == cellIndex2);

spielfeld[cellIndex1] = 2;
spielfeld[cellIndex2] = 2;
updateHTMLVonArray(spielfeld);