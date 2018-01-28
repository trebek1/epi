document.addEventListener("DOMContentLoaded", function(){

  const SUITS = ["Heart", "Spade", "Diamond", "Club"]; 
  const numbers = [1,2,3,4,5,6,7,8,9,10,11,12,13]; // 1 for ace, 11 is jack, 12 is Queen, 13 is King

  class Card {
    constructor(num, suit){
      this.number = num;
      this.suit = suit;
    }
  }

  class Deck {
    constructor(index){
      this.deckNumber = index;
      this.cards = [];
      this.suits = ["Heart", "Spade", "Diamond", "Club"];
      this.numbers = [1,2,3,4,5,6,7,8,9,10,11,12,13]; // 1 for ace, 11 is jack, 12 is Queen, 13 is King

      this.createDeck();
      this.shuffleDeck();
    }

    createDeck(){
      for(let i = 0; i < this.suits.length; i++){
        for(let k = 0; k < this.numbers.length; k++){
          this.cards.push(new Card(this.numbers[k], this.suits[i]));
        }
      }
    }

    shuffleDeck(){
      // randomly pick two places in the deck and swap the cards 
      for(let i = 0; i < 10000; i++){
        let idx1 = Math.floor(Math.random() * 52);
        let idx2 = Math.floor(Math.random() * 52);
        
        let card = this.cards[idx1];
        this.cards[idx1] = this.cards[idx2];
        this.cards[idx2] = card;
        
      }
    }

    printDeck(){
      for(let i = 0; i < this.cards.length; i++){
        var cards = document.getElementById("cards");
        var div = document.createElement('div');
        div.className = "card";
        div.innerHTML = `deck: ${this.deckNumber} suit: ${this.cards[i].suit} number: ${this.cards[i].number}`;
        cards.appendChild(div);
      }
    }
  }

  class Game {

  }

  const deck = new Deck(1);
  deck.printDeck();


});