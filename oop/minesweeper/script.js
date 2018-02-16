document.addEventListener("DOMContentLoaded", () => {
  const reset = document.getElementById("reset");

  let startGame = (function(){
    let game = null;
    return {
      getGame: function(){
        if(game === null){
          game = new Game(9,9,10);
          return game;
        } else {
          return game;
        }
      }
    }
  })();
  
  reset.addEventListener("click", () => {
    // get the unique game instance 
    let game = startGame.getGame();
    game.resetGame();
  });

  class Player {
    constructor(){
      this.score = 0;
    }
  }

  class Game {
    constructor(width, height, mines){
      this.width = width; 
      this.height = height;
      this.mines = mines;
      this.mineObjects = [];
      let game = document.getElementById('game');
      game.style.height = `${50 * height}px`;
      game.style.width = `${50 * width}px`;
      this.placeBoxes(width, height, game);
      this.setMines(mines, width, height);
    }

    placeBoxes(width, height, game){
      for(let i = 0; i < width * height; i++){
        let box = document.createElement('div');
        box.classList.add('box');
        let mine = new Mine(box, i, this);
        this.mineObjects.push(mine);
        game.appendChild(box);
      }
    }

    setMines(mines, width, height){
      let i = 0;
      while(i < mines){
        let idx = Math.floor(Math.random() * width * height);
        let target = document.getElementsByClassName("box")[idx];
        if(!target.classList.contains('mineFuture')){
          target.classList.add('mineFuture');
          i++;
        }
      }
    }

    checkNeighbors(index){
      const left = index  - 1;
      const right = index + 1;
      const up = index - 9;
      const down = index + 9;
      const upLeft = index - 10;
      const upRight = index - 8;
      const downLeft = index + 8;
      const downRight = index + 10;
      const boxes = document.getElementsByClassName('box');
      let count = 0;
      if(this.checkMines(left) && left % 9 != 8){
        count++;
      }

      if(this.checkMines(right) && right % 9 != 0){
        count++; 
      }

      if(this.checkMines(up)){
        count++;
      }

      if(this.checkMines(down)){
        count++;
      }

      if(this.checkMines(upLeft) && left % 9 != 8){
        count++;
      }

      if(this.checkMines(upRight) && right % 9 != 0){
        count++;
      }

      if(this.checkMines(downLeft) && left % 9 != 8){
        count++;
      }

      if(this.checkMines(downRight) && right % 9 != 0){
        count++;
      }

      if(count > 0){
        boxes[index].innerHTML = count;
        boxes[index].classList.add('number');
        switch(count){
          case 1:
            boxes[index].classList.add('one');
            break;
          case 2:
            boxes[index].classList.add('two');
            break;
          case 3:
            boxes[index].classList.add('three');
            break;
          case 4:
            boxes[index].classList.add('four');
            break;
          case 5:
            boxes[index].classList.add('five');
            break;
          case 6:
            boxes[index].classList.add('six');
            break;
          case 7:
            boxes[index].classList.add('seven');
            break;
          case 8:
            boxes[index].classList.add('eight');
            break;
          default:
            break;
        }
        return true;
      }
      return false;
    }

    explore(index){
      const left = index  - 1;
      const right = index + 1;
      const up = index - 9;
      const down = index + 9;
      const boxes = document.getElementsByClassName('box');

      if(this.checkIndex(left) && left % 9 != 8){
        const target = boxes[left];
        if(!this.clicked(target)){
          target.classList.add('gray');
          let n = this.checkNeighbors(left);
          if(!n){
            this.explore(left);
          }
        }
      }

      if(this.checkIndex(right) && right % 9 != 0){
        const target = boxes[right];
        if(!this.clicked(target)){
         target.classList.add('gray');
         let n = this.checkNeighbors(right);
         if(!n){
          this.explore(right); 
         }
        }
      }

      if(this.checkIndex(up)){
        const target = boxes[up];
        if(!this.clicked(target)){
          target.classList.add('gray');
          let n = this.checkNeighbors(up);
          if(!n){
            this.explore(up);  
          }
          
        }

      }

      if(this.checkIndex(down)){
        const target = boxes[down];
        if(!this.clicked(target)){
          target.classList.add('gray');
          let n = this.checkNeighbors(down);
          if(!n){
            this.explore(down);  
          }
        }
      }
    }

    clicked(target){
      if(!target.classList.contains("mineFuture") && !target.classList.contains("gray") && !target.classList.contains("mine")){
        return false;
      }
      return true;
    }


    checkIndex(index){
      return index >= 0 && index < 81;
    }

    checkMines(index){
      const boxes = document.getElementsByClassName('box');
      return index >= 0 && index < 81 && boxes[index].classList.contains("mineFuture");
    }

    resetGame(){
      const boxes = document.getElementsByClassName("box");
      for(let i = 0; i < boxes.length; i++){
        let target = boxes[i];
        target.innerHTML = "";
        if(target.classList.contains("gray")){
          target.classList.remove("gray");
        }
        if(target.classList.contains("mineFuture")){
          target.classList.remove("mineFuture");
        }

        if(target.classList.contains("mine")){
          target.classList.remove("mine");
        }
      }
      this.setMines(this.mines, this.width, this.height);
    }
  }

  class Mine {
    constructor(domElement, index, board){
      this.clicked = false;
      this.board = board;
      this.mine = false;
      this.height = 50;
      this.width = 50;
      this.x = index % 8;
      this.y = Math.floor(index / 8);
      this.board = board;
      this.index = index;
      this.dom = domElement;
      this.mines = 0;
      var _that = this;
      this.dom.addEventListener('click', function(){
        console.log("hi there " + _that.index);
        if(this.classList.contains('mineFuture')){
          this.classList.add('mine');
        } else {
          this.classList.add('gray');
          let n = _that.board.checkNeighbors(_that.index);
          if(!n){
            _that.board.explore(_that.index);  
          }
          
        }
      });
    }
  }

  let currentGame = startGame.getGame();
});


