document.addEventListener("DOMContentLoaded", function(){
  console.log("loading");

  var Board = function(){
    this.winner = false;
    this.moves = [
      0,0,0,
      0,0,0,
      0,0,0
    ];
    
  }

  Board.prototype.checkWinner = function(){
    var v1 = this.moves[0] === this.moves[3] && this.moves[3] === this.moves[6] && this.moves[0] != 0;
    var v2 = this.moves[1] === this.moves[4] && this.moves[4] === this.moves[7] && this.moves[1] != 0;
    var v3 = this.moves[2] === this.moves[5] && this.moves[5] === this.moves[7] && this.moves[2] != 0;
    var h1 = this.moves[0] === this.moves[1] && this.moves[1] === this.moves[2] && this.moves[0] != 0;
    var h2 = this.moves[3] === this.moves[4] && this.moves[4] === this.moves[5] && this.moves[3] != 0;
    var h3 = this.moves[6] === this.moves[7] && this.moves[7] === this.moves[7] && this.moves[6] != 0;
    var d1 = this.moves[0] === this.moves[4] && this.moves[4] === this.moves[8] && this.moves[0] != 0;
    var d2 = this.moves[2] === this.moves[4] && this.moves[4] === this.moves[6] && this.moves[2] != 0;
    
    if(v1 || v2 || v3 || h1 || h2 || h3 || d1 || d2){
      return true;
    }
    return false;
  }

  Board.prototype.init = function(){
    var that = this;
    var player1 = new Player('X', 'player1');
    var player2 = new Player('Y', 'player2');

    this.currentPlayer = player1;

    var boxes = document.getElementsByClassName("box");
    for(var i = 0; i < boxes.length; i++){
      var target = boxes[i];
      (function(i){
        target.addEventListener("click", function(){
          this.id = i;
          var p1Class = this.classList.contains("player1");
          var p2Class = this.classList.contains("player2");
          if(p1Class || p2Class){
            return;
          } else {
            this.innerHTML = that.currentPlayer.sym;
            this.classList.add(that.currentPlayer.selector);
            that.currentPlayer = (that.currentPlayer === player1) ? player2 : player1;
            that.moves[this.id] = that.currentPlayer.sym;
            var winner = that.checkWinner();
            if(winner && that.winner === false){
              that.winner = true;
              alert("Winner is " + that.currentPlayer.selector);
            }
          }
        });
      })(i);      
    }
  }

  var Player = function(sym, s){
      this.sym = sym;
      this.selector = s;
  }

  var board = new Board();
  board.init();

});