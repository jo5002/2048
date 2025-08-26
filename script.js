//Variablen
let spielfeld = [
    0, 0, 0, 0,
    0, 0, 0, 0,
    0, 0, 0, 0,
    0, 0, 0, 0
];

const left = [
    [3, 2, 1, 0],
    [7, 6, 5, 4],
    [11, 10, 9, 8],
    [15, 14, 13, 12]
];

const right = [
    [0, 1, 2, 3],
    [4, 5, 6, 7],
    [8, 9, 10, 11],
    [12, 13, 14, 15]
];

const down = [
    [0, 4, 8, 12],
    [1, 5, 9, 13],
    [2, 6, 10, 14],
    [3, 7, 11, 15]
];

const up = [
    [12, 8, 4, 0],
    [13, 9, 5, 1],
    [14, 10, 6, 2],
    [15, 11, 7, 3]
];

let touchStartX = 0;
let touchStartY = 0;

//Funktionen
function getRandomInt(min, max) {
    const minCeiled = Math.ceil(min);
    const maxFloored = Math.floor(max) + 1;
    return Math.floor(Math.random() * (maxFloored - minCeiled) + minCeiled);
}

function getRandomStartNumber() {
    const randomNumber = getRandomInt(1, 10);
    if (randomNumber == 10) {
        return 4;
    }
    else {
        return 2;
    }
}

function getRandomFreeFieldIndex() {
    let fieldIndex = -1;
    do {
        fieldIndex = getRandomInt(0, 15);
    } while (spielfeld[fieldIndex] != 0);
    return fieldIndex;
}

function generate() {
    const fieldIndex = getRandomFreeFieldIndex();
    const startNumber = getRandomStartNumber();
    spielfeld[fieldIndex] = startNumber;
}

function processFields(firstIndex, secoundIndex) {
    if (spielfeld[firstIndex] == spielfeld[secoundIndex] && spielfeld[secoundIndex] > 0) {

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

    if (processFields(firstIndex, secoundIndex)) {
        processFields(thirdIndex, fourthIndex);
    }
    else {
        if (!processFields(secoundIndex, thirdIndex)) {
            processFields(thirdIndex, fourthIndex);
        }
    }
}

function pushInDirection(direction) {
    direction.forEach(line => {
        let freiePositionen = [];
        for (let i = 3; i >= 0; i--) {
            const index = line[i];
            if (spielfeld[index] == 0) {
                freiePositionen.push(index);
            }
            else {
                if (freiePositionen.length > 0) {
                    spielfeld[freiePositionen.shift()] = spielfeld[index];
                    spielfeld[index] = 0;
                }
            }
        }
    });
}

function combineInDirection(direction) {
    direction.forEach(line => collapseRow(line));
}

function isGameBoardFull() {
    return spielfeld.every(feld => feld != 0);
}

function isMovePossible() {

    for (let reihe = 0; reihe < 4; reihe++) {

        //Felder in dieser Reihe
        const erstesFeld = spielfeld[reihe * 4 + 0];
        const zweitesFeld = spielfeld[reihe * 4 + 1];
        const drittesFeld = spielfeld[reihe * 4 + 2];
        const viertesFeld = spielfeld[reihe * 4 + 3];

        if (erstesFeld == zweitesFeld || zweitesFeld == drittesFeld || drittesFeld == viertesFeld) {
            return true;
        }
    }

    for (let spalte = 0; spalte < 4; spalte++) {

        //Felder in dieser Spalte
        const erstesFeld = spielfeld[0 * 4 + spalte];
        const zweitesFeld = spielfeld[1 * 4 + spalte];
        const drittesFeld = spielfeld[2 * 4 + spalte];
        const viertesFeld = spielfeld[3 * 4 + spalte];

        if (erstesFeld == zweitesFeld || zweitesFeld == drittesFeld || drittesFeld == viertesFeld) {
            return true;
        }
    }

    return false;
}

function onMoveInDirection(direction) {
    let gameOver = false;
    pushInDirection(direction);
    combineInDirection(direction);
    pushInDirection(direction);

    if(!isGameBoardFull()) {
        generate();
    }

    if(isGameBoardFull() && !isMovePossible()) {
        gameOver = true;
    }

    updateHTMLVonArray();
    if(gameOver) {
        const gameOverDisplay = document.querySelector('.game-over');
        gameOverDisplay.textContent = 'Game Over!';
    }
}

function updateHTMLVonArray() {
    array = spielfeld;
    for (let i = 0; i < array.length; i++) {

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
            onMoveInDirection(up);
            break;
        case "ArrowDown":
            onMoveInDirection(down);
            break;
        case "ArrowLeft":
            onMoveInDirection(left);
            break;
        case "ArrowRight":
            onMoveInDirection(right);
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
        if (deltaX > 30) onMoveInDirection(right);
        else if (deltaX < -30) onMoveInDirection(left);
    } else {
        // Vertikale Swipe
        if (deltaY > 30) onMoveInDirection(down);
        else if (deltaY < -30) onMoveInDirection(up);
    }
});

//Initialiserung
felder = document.querySelectorAll('.feld');
generate();
generate();
updateHTMLVonArray();