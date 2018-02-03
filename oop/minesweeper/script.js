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
        let mine = new Mine(box, i);
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

    resetGame(){
      const boxes = document.getElementsByClassName("box");
      for(let i = 0; i < boxes.length; i++){
        let target = boxes[i];
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
    constructor(domElement, index){
      this.clicked = false;
      this.mine = false;
      this.height = 50;
      this.width = 50;
      this.index = index;
      this.dom = domElement;
      var _that = this;
      this.dom.addEventListener('click', function(){
        console.log("hi there " + _that.index);
        if(this.classList.contains('mineFuture')){
          this.classList.add('mine');
        } else {
          this.classList.add('gray');
        }
      });
    }
  }

  let currentGame = startGame.getGame();
});
