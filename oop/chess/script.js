document.addEventListener("DOMContentLoaded", () => {

  class Game {
    constructor(){
      this.squares = document.getElementsByClassName("square"); 
      this.player1 = new Player(1);
      this.player2 = new Player(2);
      this.currentPlayer = this.player1;
      this.activeSquare = null;
      this.activeClass = null;
      this.activeObject = null;
      const that = this;

      for(let i = 0; i < this.squares.length; i++){
        let target = this.squares[i];

        // add piece to player

        target.addEventListener("click", (e) => {
          if(!e.target.classList.contains('possible')){
            this.removePossible();
          } else {
            this.removePossible();
            this.handleMove(this.activeSquare, i);
            
            return;
          }
          let x = i % 8;
          let y = Math.floor(i/8);

          let pieces = that.currentPlayer.pieces;
          let object = null;
          
          for(let k = 0; k < pieces.length; k++){
            let p = pieces[k];
            if(p.equals(x,y)){
              object = p;
              this.activeObject = object;
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
              let possibleSquare = this.squares[possibleMoveIndex];

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
      this.activeObject.moves = [];
      let x = target % 8;
      let y = Math.floor(target/8);
      this.activeObject.x = x
      this.activeObject.y = y
      this.squares[target].classList.add(this.activeClass);
      this.activeClass = null;
      this.setNextPlayer();
      this.calculateAllMoves();
      return;
    }

    calculateAllMoves(){
      let player1Pieces = this.player1.pieces;
      let player2Pieces = this.player2.pieces;
      let i = 0;
      let target = null;

      for(i = 0; i < player1Pieces.length; i++){
        target = player1Pieces[i];
        target.moves = [];
        this.calculateNextMoves(target);
      }

      for(i = 0; i < player2Pieces.length; i++){
       target = player2Pieces[i]; 
       target.moves = [];
       this.calculateNextMoves(target);
      }
      this.removePossible();
      this.removeActive();
    }

    setNextPlayer(){
      if(this.currentPlayer === this.player1){
        this.currentPlayer = this.player2;
      } else {
        this.currentPlayer = this.player1;
      }
    }

    calculateNextMoves(obj){
      
      if(obj instanceof Pawn){
        let index = obj.x + obj.y * 8;
        if(obj.color === 'white'){
          if(obj.x === obj.startingPosition[0] && obj.y === obj.startingPosition[1]){
            let relIndex = index - 8;
            if(this.squares[relIndex].classList.length == 2){
              obj.moves.push([obj.x,obj.y - 1]);
            }
            relIndex = index - 16;
            if(this.squares[relIndex].classList.length == 2){
              obj.moves.push([obj.x, obj.y - 2]);
            }
            return;
          }
          
          if(index - 9 >= 0){
            let relIndex = index - 9;
            if(this.squares[relIndex].classList.length > 2 ){
              let classList = this.squares[relIndex].classList;
              let i = 0;
              while(i < classList.length){
                if(classList[i][0] === "b"){
                  let x = relIndex % 8;
                  let y = Math.floor(relIndex / 8);
                  obj.moves.push([x, y]);
                  break;
                }
                i++;
              }
            } 
          }
          if(index - 8 >= 0){
            let relIndex = index - 8;
            let x = relIndex % 8;
            let y = Math.floor(relIndex / 8);
            relIndex = index - 8;
            if(this.squares[relIndex].classList.length === 2 ){
              obj.moves.push([x, y]);  
            }
          }

          if(index - 7 >= 0){
            let relIndex = index - 7;
            if(this.squares[relIndex].classList.length > 2 ){
              let classList = this.squares[relIndex].classList;
              let i = 0;
              while(i < classList.length){
                if(classList[i][0] === "b"){
                  let x = relIndex % 8;
                  let y = Math.floor(relIndex / 8);
                  obj.moves.push([x, y]);
                }
                i++;
              }
            } 
          }
        } else {
          let index = obj.x + obj.y * 8;
          if(obj.x === obj.startingPosition[0] && obj.y === obj.startingPosition[1]){
            let relIndex = index + 8;
            if(this.squares[relIndex].classList.length == 2){
              obj.moves.push([obj.x,obj.y + 1]);
            }
            relIndex = index + 16;
            if(this.squares[relIndex].classList.length == 2){
              obj.moves.push([obj.x, obj.y + 2]);
            }
            return;
          }
          obj.x + obj.y * 8;
          if(index + 7 < 64){
            let relIndex = index + 7;
            if(this.squares[relIndex].classList.length > 2 ){
              let classList = this.squares[relIndex].classList;
              let i = 0;
              while(i < classList.length){
                if(classList[i][0] === "w"){
                  let x = relIndex % 8;
                  let y = Math.floor(relIndex / 8);
                  obj.moves.push([x, y]);
                }
                i++;
              }
            } 
          }
          if(index + 8 < 64){
            let relIndex = index + 8;
            let x = relIndex % 8;
            let y = Math.floor(relIndex / 8);
            if(this.squares[relIndex].classList.length === 2 ){
              obj.moves.push([x, y]);  
            }
          }

          if(index + 9 < 64){
            let relIndex = index + 9;
            if(this.squares[relIndex].classList.length > 2 ){
              let classList = this.squares[relIndex].classList;
              let i = 0;
              while(i < classList.length){
                if(classList[i][0] === "w"){
                  let x = relIndex % 8;
                  let y = Math.floor(relIndex / 8);
                  obj.moves.push([x, y]);
                }
                i++;
              }
            } 
          }
        }

      } else if(obj instanceof Rook){

      }
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

    removeActive(){
      let active = document.getElementsByClassName("active");
      if(active.length > 0){
        
        while(active.length > 0){
          var act = active[0];
          act.classList.remove("active");
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
        this.pieces = [new Pawn(0,6,'white'),new Pawn(1,6,'white'),new Pawn(2,6,'white'),new Pawn(3,6,'white'),new Pawn(4,6,'white'),new Pawn(5,6,'white'),new Pawn(6,6,'white'), new Pawn(7,6,'white')];
        
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
