document.addEventListener("DOMContentLoaded", () => {

  class Game {
    constructor(){
      this.squares = document.getElementsByClassName("square"); 
      this.player1 = new Player(1);
      this.player2 = new Player(2);
      this.currentPlayer = this.player1;
      this.activeSquare = null;
      this.activeClass = null;
      const that = this;

      for(let i = 0; i < this.squares.length; i++){
        let target = this.squares[i];

        // add piece to player

        target.addEventListener("click", (e) => {
          if(!e.target.classList.contains('possible')){
            this.removePossible();
          } else {
            this.handleMove(this.activeSquare, i);
            this.removePossible();
            return;
          }
          let x = i % 8;
          let y = Math.floor(i/8) + 1;

          let pieces = that.currentPlayer.pieces;
          let object = null;
          
          for(let k = 0; k < pieces.length; k++){
            let p = pieces[k];
            if(p.equals(x,y)){
              object = p;
              break;
            }
          }


          // clicked on a piece that does not belong to you
          if(object === null){
            return
          } else {
            let moves = object.moves;
            for(let j = 0; j < moves.length; j++){
              let move = moves[j];
              let possibleMoveIndex = move[0] + move[1] * 8;
              this.squares[possibleMoveIndex].classList.add('possible');
            }
          }


          // find the index 
          // look and see if its in current players pieces 
          // show possible moves

          if(this.activeSquare === target){
            that.activeSquare.classList.remove('active');
            this.activeSquare = null;
          }

          if(that.activeSquare != null){
            that.activeSquare.classList.remove('active');
          }
          this.activeSquare = target;
          target.classList.add('active');
          this.setActiveClass(this.activeSquare);

        });
      }
    }

    setActiveClass(target){
      let classList = target.classList;

      for(let i = 0; i < classList.length; i++){
        let target = classList[i];
        if(target != 'square' && target.indexOf('row') === -1 && target != 'active'){
          this.activeClass = target;
        }
      }
    }

    handleMove(activeSquare, target){
      activeSquare.classList.remove(this.activeClass);
      this.squares[target].classList.add(this.activeClass);
      this.activeClass = null;
      return;
    }

    removePossible(){
      let possible = document.getElementsByClassName("possible");
      if(possible.length > 0){
        
        while(possible.length > 0){
          var pos = possible[0];
          pos.classList.remove("possible");
        }            
      }
    }
  }

  class Player {
    constructor(number){
      this.number = number;
      this.color = null;
      if(number === 1){
        this.color = 'white';
        this.pieces = [new Pawn(0,7,'white'),new Pawn(1,7,'white'),new Pawn(2,7,'white'),new Pawn(3,7,'white'),new Pawn(4,7,'white'),new Pawn(5,7,'white'),new Pawn(6,7,'white'), new Pawn(7,7,'white')];
        
      // white
      } else if (number === 2) {
        this.color = 'black';
        this.pieces = [new Pawn(0,1,'black'),new Pawn(1,1,'black'),new Pawn(2,1,'black'),new Pawn(3,1,'black'),new Pawn(4,1,'black'),new Pawn(5,1,'black'),new Pawn(6,1,'black'), new Pawn(7,1,'black')];
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

    equals(x, y){
      if(this.x === x && this.y === y){
        return true;
      }
      return false;
    }
  }

  class Pawn extends Piece {
    constructor(x, y, color){
      super(x,y, color);
      if(this.color === 'black'){
        this.moves = [[x,y + 1], [x, y + 2]];
      } else {
        this.moves = [[x,y - 2], [x, y - 3]];
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
