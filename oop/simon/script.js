document.addEventListener("DOMContentLoaded", () => {
  const start = document.getElementById("start");
  start.addEventListener("click", newGame);
  
  let game = (function(){
    var instance;
    function createGame(){
      let inst = new Game();
      return inst;
    }
    return {
      getInstance: function(){
        if(!instance){
          instance = createGame();
        }
        return instance;
      }
    }
  })();

  function newGame(){
    let g = game.getInstance();
    g.start();
  }

  class Game {
    constructor(){
      this.moves = [];
      this.player = new Player();
      this.iteration = 4;

      let red = document.getElementById("red");
      let blue = document.getElementById("blue");
      let green = document.getElementById("green");
      let yellow = document.getElementById("yellow");

      this.createEventListener(red);
      this.createEventListener(blue);
      this.createEventListener(green);
      this.createEventListener(yellow);
    }

    evaluateMove(){
      for(let i = 0; i < this.player.moves.length; i++){
        let playerMove = this.player.moves[i];
        let gameMove = this.moves[i];

        if(playerMove != gameMove){
          alert("game over!");
          this.player.moves = [];
          this.moves = [];
          return;
        }
        if(i === this.player.moves.length - 1 && this.player.moves.length === this.moves.length){
          this.player.moves = [];
          this.gameIteration(1);
        }
      }
    }

    createEventListener(el){
      el.addEventListener("click", () =>{
        el.style.opacity = 1;
        this.player.moves.push(el.id);
        setTimeout(() => {
          el.style.opacity = 0.5;
          this.evaluateMove();
        }, 500);
      });
    }

    start(){
      this.gameIteration(4);
    }

    gameIteration(colors){
      let previousColors = this.moves;
      let lenP = previousColors.length;
      let x = 0;
      if(previousColors.length > 0){
        for(x = 0; x < lenP; x++){
          (function(x){
            setTimeout(() => {
            var el = document.getElementById(previousColors[x]);
            el.style.opacity = 1;
            setTimeout(() => {
              el.style.opacity = 0.5;
            }, 500);
          }, x * 1000);
          })(x)
        }
      }

      for(let i = 0 + x; i < colors + x; i++){
        let map = {
          0 : "red",
          1 : "blue",
          2 : "green",
          3 : "yellow"
        }
        setTimeout(() => {
          let selection = Math.floor(Math.random()*4);
          this.moves.push(map[selection]);
          var el = document.getElementById(map[selection]);
          el.style.opacity = 1;
          setTimeout(() => {
            el.style.opacity = 0.5;
          }, 500);
        }, i * 1000);
      }
    }
  }
  
  class Player {
    constructor(){
      this.moves = [];
    }
  }
});



