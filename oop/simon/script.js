document.addEventListener("DOMContentLoaded", () => {
  const start = document.getElementById("start");
  
  start.addEventListener("click", () => {
    const game = new Game();
  });


  class Game {
    constructor(){
      this.moves = [];
      this.player = new Player();
      this.start();
    }

    start(){
      this.gameIteration(4);
    }

    gameIteration(colors){
      for(let i = 0; i < colors; i++){

        let map = {
          0 : "red",
          1 : "blue",
          2 : "green",
          3 : "yellow"
        }

        this.moves.push(map[i]);

        setTimeout(() => {
          let selection = Math.floor(Math.random()*4);
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
        const red = document.getElementById("red");
        const blue = document.getElementById("blue");
        const green = document.getElementById("green");
        const yellow = document.getElementById("yellow");
        red.addEventListener("click", () => {
          this.moves.push("red");
          red.style.opacity = 1;
          setTimeout(() => {
            red.style.opacity = 0.5;
          }, 500);
        });
        blue.addEventListener("click", () => {
          this.moves.push("blue");
          blue.style.opacity = 1;
          setTimeout(() => {
            blue.style.opacity = 0.5;
          }, 500);
        });
        green.addEventListener("click", () => {
          this.moves.push("green");
          green.style.opacity = 1;
          setTimeout(() => {
            green.style.opacity = 0.5;
          }, 500);
        });
        yellow.addEventListener("click", () => {
          this.moves.push("yellow");
          yellow.style.opacity = 1;
          setTimeout(() => {
            yellow.style.opacity = 0.5;
          }, 500);

        });
    }

  }

});