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
            this.calculateAllMoves();
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
      if(this.squares[target].classList.length > 2){
        this.handleTake(target);  
      }
      this.squares[target].classList.add(this.activeClass);
      this.activeClass = null;
      this.setNextPlayer();
      this.calculateAllMoves();
      return;
    }

    // remove the class from the board
    handleTake(target){
      let player = this.currentPlayer;
      let other = this.currentPlayer === this.player1 ? this.player2 : this.player1;
      let color = player.color;
      let square = this.squares[target];
      for(let i = 0; i < square.classList.length; i++){
        let cls = square.classList[i];
        let letter = cls[0];
        if(letter === 'b' && color === 'white'){
          square.classList.remove(cls);
          other.remove(target);
          return;
        } else if(letter === 'w' && color === 'black'){
          square.classList.remove(cls);
          other.remove(target);
          return;
        }
      }

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
      let target = document.getElementById("activePlayer");
      if(this.currentPlayer === this.player1){
        this.currentPlayer = this.player2;
        target.innerHTML = "Black";
      } else {
        this.currentPlayer = this.player1;
        target.innerHTML = "White";
      }
    }

    calculateBlackPawnNextMoves(obj){
      let index = obj.x + obj.y * 8;
      let settings = {
        relIndex: 8,
        relIndex2: 16,
        beginOffset: 1,
        beginOffset2: 2,
        firstIndex: index + 7 < 64,
        secondIndex: index + 8 < 64,
        thirdIndex: index + 9 < 64 && obj.x != 7,
        next1: index + 7,
        next2: index + 8,
        next3: index + 9,
        colorText: "w"
      };

      Pawn.calculateMoves(obj, settings, this.squares);
    }

    calculateWhitePawnNextMoves(obj){

      let index = obj.x + obj.y * 8;
      let settings = {
        relIndex: -8,
        relIndex2: -16,
        beginOffset: -1,
        beginOffset2: -2,
        firstIndex: index - 7 >= 0,
        secondIndex: index - 8 >= 0,
        thirdIndex: index - 9 >= 0 && obj.x > 0,
        next1: index - 7,
        next2: index - 8,
        next3: index - 9,
        colorText: "b"
      };

      Pawn.calculateMoves(obj, settings, this.squares);
    }

    calculateNextMoves(obj){
      
      if(obj instanceof Pawn){
        if(obj.color === 'white'){
          this.calculateWhitePawnNextMoves(obj);
        } else {
          this.calculateBlackPawnNextMoves(obj);
        }
      } else if(obj instanceof King){
        King.calculateMoves(obj, null, this.squares);
      } else if(obj instanceof Rook){
        Rook.calculateMoves(obj, null, this.squares);
      } else if(obj instanceof Bishop){
        Bishop.calculateMoves(obj, null, this.squares);
      } else if(obj instanceof Queen){
        Queen.calculateMoves(obj, null, this.squares);
      } else if(obj instanceof Knight){
        Knight.calculateMoves(obj, null, this.squares);
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
        this.pieces = [new Rook(0, 7, 'white'),new Knight(1,7,'white'), new Bishop(2,7, 'white'), new Queen(3,7, 'white'), new King(4,7,'white'), new Bishop(5,7,'white'), new Knight(6,7,'white'), new Rook(7,7,'white'),new Pawn(0,6,'white'),new Pawn(1,6,'white'),new Pawn(2,6,'white'),new Pawn(3,6,'white'),new Pawn(4,6,'white'),new Pawn(5,6,'white'),new Pawn(6,6,'white'), new Pawn(7,6,'white')];
        
      // white
      } else if (number === 2) {
        this.color = 'black';
        this.pieces = [new Rook(0, 0, 'black'),new Knight(1,0,'black'), new Bishop(2,0, 'black'), new Queen(3,0, 'black'), new King(4,0,'black'), new Bishop(5,0,'black'), new Knight(6,0,'black'), new Rook(7,0,'black'),new Pawn(0,1,'black'),new Pawn(1,1,'black'),new Pawn(2,1,'black'),new Pawn(3,1,'black'),new Pawn(4,1,'black'),new Pawn(5,1,'black'),new Pawn(6,1,'black'), new Pawn(7,1,'black')];
      } else {
        console.log("cant have more than 2 players");
        return;
      }
    }

    remove(index){
      let x = index % 8;
      let y = Math.floor(index / 8);
      for(let i = 0; i < this.pieces.length; i++){
        let target = this.pieces[i];
        if(target.x === x && target.y == y){
          this.pieces.splice(i, 1);
          return;
        }
      }
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

    static checkTeam(classes, other, current){
      for(let i = 0; i < classes.length; i++){
        let c = classes[i][0];
        if(c === other){
          return false;
        }
        if(c[0] === current){
          return true;
        }
      }
      return true;
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

    static calculateMoves(obj, settings, squares){
      let index = obj.x + obj.y * 8;
      // starting position
      if(obj.x === obj.startingPosition[0] && obj.y === obj.startingPosition[1]){
        let relIndex1 = index + +settings.relIndex;
        if(squares[relIndex1].classList.length == 2){
          obj.moves.push([obj.x,obj.y + +settings.beginOffset]);
        }
        let relIndex2 = index + +settings.relIndex2;
        if(squares[relIndex2].classList.length == 2 && squares[relIndex1].classList.length == 2){
          obj.moves.push([obj.x, obj.y + +settings.beginOffset2]);
        }

        // check left take 
        if(settings.firstIndex == true){
        let relIndex = settings.next1;
        if(squares[relIndex].classList.length > 2 ){
          let classList = squares[relIndex].classList;
          let i = 0;
          while(i < classList.length){
            if(classList[i][0] === settings.colorText){
              let x = relIndex % 8;
              let y = Math.floor(relIndex / 8);
              obj.moves.push([x, y]);
            }
            i++;
          }
        } 
      }

      // check right take
      if(settings.thirdIndex == true){
        let relIndex = settings.next3;
        if(squares[relIndex].classList.length > 2 ){
          let classList = squares[relIndex].classList;
          let i = 0;
          while(i < classList.length){
            if(classList[i][0] === settings.colorText){
              let x = relIndex % 8;
              let y = Math.floor(relIndex / 8);
              obj.moves.push([x, y]);
            }
            i++;
          }
        } 
      }
        return;
      }
      
      // check left take
      if(settings.firstIndex == true){
        let relIndex = settings.next1;
        if(squares[relIndex].classList.length > 2 ){
          let classList = squares[relIndex].classList;
          let i = 0;
          while(i < classList.length){
            if(classList[i][0] === settings.colorText){
              let x = relIndex % 8;
              let y = Math.floor(relIndex / 8);
              obj.moves.push([x, y]);
            }
            i++;
          }
        } 
      }
      
      // check in front of pawn
      if(settings.secondIndex === true){
        let relIndex = settings.next2;
        let x = relIndex % 8;
        let y = Math.floor(relIndex / 8);
        if(squares[relIndex].classList.length === 2 ){
          obj.moves.push([x, y]);  
        }
      }

      // check right take 
      if(settings.thirdIndex == true){
        let relIndex = settings.next3;
        if(squares[relIndex].classList.length > 2 ){
          let classList = squares[relIndex].classList;
          let i = 0;
          while(i < classList.length){
            if(classList[i][0] === settings.colorText){
              let x = relIndex % 8;
              let y = Math.floor(relIndex / 8);
              obj.moves.push([x, y]);
            }
            i++;
          }
        } 
      }
    }
  }

  class Rook extends Piece{
    constructor(x, y, color){
      super(x,y, color);
      this.moves = []; 
    }
    static calculateMoves(obj, settings, squares){
      let other = null;
      if(obj.color === 'white'){
        other = 'b';
      } else {
        other = 'w';
      }
      let index = obj.x + obj.y * 8;
      // check possible indices
      // move up down left right as far as possible and add squares until you run into a wall or piece
      let up = 1; 
      let down = -1; 
      let left = -1; 
      let right = 1;

      // up
      let blocker = false;
      while(true){
        index = obj.x + (obj.y + up) * 8;
        
        const isNoPiece = squares[index] && (squares[index].classList.length === 2 || !Piece.checkTeam(squares[index].classList, other, obj.color[0]));
        if(index < 64 && isNoPiece && obj.y + up < 8 && !blocker){
          obj.moves.push([obj.x, obj.y + up]);
          if(!Piece.checkTeam(squares[index].classList, other, obj.color[0])){
            blocker = true;
          }
          up++;
        } else {
          break;
        }
      }

      // down
      blocker = false;
      while(true){
        index = obj.x + (obj.y + down) * 8;
        const isNoPiece = squares[index] && (squares[index].classList.length === 2 || !Piece.checkTeam(squares[index].classList, other, obj.color[0]));
        if(index >= 0 && isNoPiece && obj.y + down >= 0 && !blocker){
          obj.moves.push([obj.x, obj.y + down]);
          if(!Piece.checkTeam(squares[index].classList, other, obj.color[0])){
            blocker = true;
          }
          down--;
        } else {
          break;
        }
      }

      // left
      blocker = false;
      while(true){
        index = obj.x + left + (obj.y) * 8;
        const isNoPiece = squares[index] && (squares[index].classList.length === 2 || !Piece.checkTeam(squares[index].classList, other, obj.color[0]));
        if(index >= 0 && isNoPiece && obj.x + left >= 0 && !blocker){
          obj.moves.push([obj.x + left, obj.y]);
          if(!Piece.checkTeam(squares[index].classList, other, obj.color[0])){
            blocker = true;
          }
          left--;
        } else {
          break;
        }
      }

      // right
      blocker = false;
      while(true){
        index = obj.x + right + (obj.y) * 8;
        const isNoPiece = squares[index] && (squares[index].classList.length === 2 || !Piece.checkTeam(squares[index].classList, other, obj.color[0]));
        if(index >= 0 && isNoPiece && obj.x + right < 8 && !blocker){
          obj.moves.push([obj.x + right, obj.y]);
          if(!Piece.checkTeam(squares[index].classList, other, obj.color[0])){
            blocker = true;
          }
          right++;
        } else {
          break;
        }
      }
    }
  }

  class Knight extends Piece {
    constructor(x, y, color){
      super(x,y, color);
      this.moves = [];
    }

    static calculateMoves(obj, settings, squares){
      let other = null;
      if(obj.color === 'white'){
        other = 'b';
      } else {
        other = 'w';
      }
      let index = obj.x + obj.y * 8;
      // check possible indices
      const delta = [1,2, -1, -2];
      for(let i = 0; i < delta.length; i++){
        for(let j = 0; j < delta.length; j++){
          let dx = Math.abs(delta[i]);
          let dy = Math.abs(delta[j]);
          if(dx === dy){
            continue;
          }
          let index = (obj.x + delta[i]) + (obj.y + delta[j]) * 8;
          let isNoPiece = (index < 64) && squares[index] != undefined && (squares[index].classList.length === 2 || !Piece.checkTeam(squares[index].classList, other, obj.color[0]));
          if(index < 64 && isNoPiece && obj.x + delta[i] < 8 && obj.x + delta[i] >=0){
            obj.moves.push([obj.x + delta[i], obj.y + delta[j]]);
          }
        }
      }
    }
  }

  class Bishop extends Piece {
    constructor(x, y, color){
      super(x,y, color);
      this.moves = [];
    }

    static calculateMoves(obj, settings, squares){
      let other = null;
      if(obj.color === 'white'){
        other = 'b';
      } else {
        other = 'w';
      }
      let index = obj.x + obj.y * 8;
      // check possible indices
      let delta = [1, -1];

      let counter = 1;
      let blocker = false;
      for(let i = 0; i < delta.length; i++){
        for(let j = 0; j < delta.length; j++){
          while(true){
            if(blocker){
              break;
            }

            let possibleX = obj.x + delta[i] * counter;
            let possibleY = obj.y + delta[j] * counter;

            let index = possibleX + possibleY * 8;

            // if we are not on the board
            if(possibleX < 0 || possibleX > 7 || possibleY < 0 || possibleY > 7){
              break;
            }
            const isNoPiece = squares[index] && (squares[index].classList.length === 2 || !Piece.checkTeam(squares[index].classList, other, obj.color[0]));
            if(index < 64 && isNoPiece){
              if(blocker){
                break;  
              }
              obj.moves.push([possibleX, possibleY]);
              counter++;
              if(!Piece.checkTeam(squares[index].classList, other, obj.color[0])){
                blocker = true;
              }
            } else {
              break;
            }  
          }
          counter = 1;
          blocker = false;
        }
      }
    }
  }

  class Queen extends Piece {
    constructor(x, y, color){
      super(x,y, color);
      this.moves = [];
    }

    static calculateMoves(obj, settings, squares){
      Bishop.calculateMoves(obj, settings, squares);
      Rook.calculateMoves(obj, settings, squares);
    }
  }

  class King extends Piece {
    constructor(x, y, color){
      super(x,y, color);
      this.moves = [];
    }

    static calculateMoves(obj, settings, squares){
      let other = null;
      if(obj.color === 'white'){
        other = 'b';
      } else {
        other = 'w';
      }
      let index = obj.x + obj.y * 8;
      // check possible indices
      const delta = [0, 1, -1];
      for(let i = 0; i < delta.length; i++){
        for(let j = 0; j < delta.length; j++){
          if(i === 0 && j === 0){
            continue;
          }
          let index = (obj.x + delta[i]) + (obj.y + delta[j]) * 8;
          const isNoPiece = squares[index] && (squares[index].classList.length === 2 || !Piece.checkTeam(squares[index].classList, other, obj.color[0]));
          if(index < 64 && isNoPiece){
            obj.moves.push([obj.x + delta[i], obj.y + delta[j]]);
          }
        }
      }
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
