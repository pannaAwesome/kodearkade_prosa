let grid = [];
let gridElements = 25;
let size = 50;
let cellScore;
let currentCellScore;
let chosenPath = 2;

class Cell {
    constructor(id, row, col, isGoal, isObstacle) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.isGoal = isGoal;
        this.isObstacle = isObstacle;
        this.onPath = false;
    }

    getNeighbours() {
        let neighbours = []
        grid.forEach(row => {
            row.forEach(cell => {
                let distance = Math.abs(cell.row-this.row) + Math.abs(cell.col-this.col);
                if (distance === 1 && !cell.isObstacle) {
                    neighbours.push(cell);
                }
            });
        });
        return neighbours;
    }
}

function changePath(id) {
    let rowIndex = parseInt(id[0]);
    let colIndex = parseInt(id[1]);
    
    if (grid[rowIndex][colIndex].onPath) {
        grid[rowIndex][colIndex].onPath = false;
        document.getElementById(id).className = "cell";
        document.getElementById(id).className += " road";
        chosenPath--;
    } else {
        grid[rowIndex][colIndex].onPath = true;
        document.getElementById(id).className = "cell";
        document.getElementById(id).className += " path";
        chosenPath++;
    }
}

function startGame(gameStatus) {    
    if (gameStatus === "new") {
        let newGameButton = document.getElementById("new-game");
        newGameButton.style.display = "none";
        newGameButton.style.visibility = "visible";
        let noSolutionButton = document.getElementById("no-solution");
        noSolutionButton.style.visibility = "visible";
        let submitPathButton = document.getElementById("submit-path");
        submitPathButton.style.visibility = "visible";
        gridElements = 25;
    }else if (gameStatus === "lost") {
        gridElements = 25;
    } else {
        gridElements = (Math.sqrt(gridElements) + 1)**2;
    }
    chosenPath = 2;
    document.getElementById("overlay").style.display = "none";
    createGrid(Math.sqrt(gridElements));
}

function checkSolution() {
    let pathLength = findPathLength();
    let textField = document.getElementById("game-text");
    if (chosenPath <= pathLength) {
        textField.innerHTML = "You got the egg before the Easter Bunny!";
        document.getElementById("next").style.display = "block";
        document.getElementById("lost").style.display = "none";
    } else {
        textField.innerHTML = "The Easter Bunny found the egg first, and hid it again...";
        document.getElementById("lost").style.display = "block";
        document.getElementById("next").style.display = "none";
    }
    document.getElementById("overlay").style.display = "block";
}

function findPathLength() {
    let startCell = grid[0][0];
    let gridSize = grid.length-1;
    let endCell = grid[gridSize][grid[gridSize].length-1];
    discoveredCells = [startCell];
    cameFrom = new Map([]);
    cellScore[startCell.id] = 0;
    currentCellScore[startCell.id] = heuristicPath(startCell, endCell);

    while (discoveredCells.length !== 0) {
        let currentCell = discoveredCells.shift();

        if (currentCell.isGoal) {
            return findPath(cameFrom, currentCell);
        }

        currentCell.getNeighbours().forEach(neighbour => {
            let relativeCellScore = cellScore.get(currentCell.id);

            if (relativeCellScore < cellScore.get(neighbour.id)) {
                cameFrom.set(neighbour.id, currentCell);
                cellScore.set(neighbour.id, relativeCellScore);
                currentCellScore.set(neighbour.id, relativeCellScore + heuristicPath(neighbour, endCell));

                if (!discoveredCells.includes(neighbour)) {
                    let index = discoveredCells.findIndex((cell) => currentCellScore.get(cell.id) >= currentCellScore.get(neighbour.id));
                    discoveredCells.splice(index === -1 ? 0 : index, 0, neighbour);
                }
            }
        });
    }
    return 2;
}

function heuristicPath(current, end) {
    return (end.row - current.row)**2 + (end.col - current.col)**2
}

function findPath(cameFrom, current) {
    let path = [current];
    while (cameFrom.has(current.id)) {
        current = cameFrom.get(current.id);
        path.push(current);
        if (current.id !== "00" && !current.isGoal) {
            let cell = document.getElementById(current.id);
            cell.className = "cell bunny-path";
        }
    }
    return path.length;
}

function createGrid(elementsPerSide) {
    let gameboard = document.getElementById("gameboard");
    gameboard.innerHTML = "";
    gameboard.style.height = elementsPerSide*size + "px";
    gameboard.style.width = elementsPerSide*size + "px";
    cellScore = new Map([]);
    currentCellScore = new Map([]);
    grid = [];
    for (let rowIndex = 0; rowIndex < elementsPerSide; rowIndex++) {
        let row = newRow();
        grid.push([])
        for (let cellIndex = 0; cellIndex < elementsPerSide; cellIndex++) {
            let cell;
            let cellObject
            if (cellIndex === 0 && rowIndex === 0) {
                cell = createSpecialTile("start", 0);
                cellObject = new Cell("00", 0, 0, false, false);              
                cellScore.set("00", 0);
                currentCellScore.set("00", 0);
            } else if (cellIndex === elementsPerSide-1 && rowIndex === elementsPerSide-1) {
                cell = createSpecialTile("goal", cellIndex*size);
                cellObject = new Cell("" + rowIndex + cellIndex, rowIndex, cellIndex, true, false);              
                cellScore.set(cellObject.id, Infinity);
                currentCellScore.set(cellObject.id, Infinity);
            } else {
                cellObject = new Cell("" + rowIndex + cellIndex, rowIndex, cellIndex, false, false);              
                cell = newCell(cellObject, cellIndex*size);
                cellScore.set(cellObject.id, Infinity);
                currentCellScore.set(cellObject.id, Infinity);
            }
            row.appendChild(cell);
            grid[rowIndex].push(cellObject);
        }
        gameboard.appendChild(row);
    }
}

function createSpecialTile(specialId, left) {
    let cell = document.createElement("div");
    cell.className = "cell";
    cell.id = specialId;
    cell.style.left = left + "px";
    return cell;
}

function newCell(cellObject, left) {
    let cell = document.createElement("div");
    cell.className = "cell";
    cell.style.left = left + "px";
    cell.id = cellObject.id;
    if (Math.random() < 0.8) {
        cell.className += " road";
        cell.onclick = function() { changePath(cellObject.id); };
    } else {
        cell.className += " obstacle";
        cellObject.isObstacle = true;
    }
    return cell;
}

function newRow() {
    let row = document.createElement("div");
    row.className = "row";
    row.style.top = "0px";
    row.style.height = size + "px";
    return row;
}