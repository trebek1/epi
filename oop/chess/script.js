document.addEventListener("DOMContentLoaded", () => {

  class Game {
    constructor(){
      this.squares = document.getElementsByClassName("square"); 
      this.player1 = new Player(1, this.squares);
      this.player2 = new Player(2, this.squares);
      this.activeSquare = null;
      const that = this;

      for(let i = 0; i < this.squares.length; i++){
        let target = this.squares[i];
        target.addEventListener("click", (e) => {
          if(this.activeSquare === target){
            that.activeSquare.classList.remove('active');
            this.activeSquare = null;
            return;
          }
          if(that.activeSquare != null){
            that.activeSquare.classList.remove('active');
          }
          this.activeSquare = target;
          target.classList.add('active');
        });
      }
    }
  }

  class Player {
    constructor(number){
      this.number = number;
      this.pieces = [];
      this.color = null;

      if(number === 1){
        this.color = 'white';
        // 48 -> 55 shoud be pawns

      // white
      } else if (number === 2) {
        this.color = 'black';
        // 8 --> 15 should be pawns 
      } else {
        console.log("cant have more than 2 players");
        return;
      }
      console.log("player " + number + " created ");
    }
  }

  class Piece {
    constructor(x, y, color){
      this.x = x; 
      this.y = y;
      this.startingPosition = [x, y];
      this.color = color;
    }
  }

  class Pawn extends Piece {
    constructor(x, y, color){
      super(x,y, color);
      if(this.color === 'black'){
        this.moves = [[x,y + 1], [x, y + 2]];
      } else {
        this.moves = [[x,y - 1], [x, y - 2]];
      }
    
    }
  }

  class Rook extends Piece{
    constructor(x, y, color){
      super(x,y, color);
      this.moves = [];
      
    }
  }

  class Knight extends Piece {
    constructor(x, y, color){
      super(x,y, color);
      this.moves = [];
    }
  }

  class Bishop extends Piece {
    constructor(x, y, color){
      super(x,y, color);
      this.moves = [];
    }
  }

  class Queen extends Piece {
    constructor(x, y, color){
      super(x,y, color);
      this.moves = [];
    }
  }

  class King extends Piece {
    constructor(x, y, color){
      super(x,y, color);
      this.moves = [];
    }
  }

  const createGame = () => {
    let instance = null;

    function getInstance(){
      if(instance === null){
        instance = new Game();
      }
      return instance;
    }

    return {
      getInstance
    }
  };

  const game = createGame();
  game.getInstance();

});

// class Foo {
//    constructor(x, y) {
//       this.x = x;
//       this.y = y;
//    }
// }

// this is the same as a class (below)
// var Foo = (function () {
//     function Foo(x, y) {
//         this.x = x;
//         this.y = y;
//     }
//     return Foo;
// }());
