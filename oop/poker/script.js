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
        
        // swap the cards 
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
    constructor(players, decks, hands){
      this.players = [];
      this.decks = [];
      this.currentCards = [];
      this.usedCards = [];
      this.hands = hands;
      this.tableCards = [];

      this.royalFlush = 0;
      this.straightFlush = 0;
      this.fourOfAKind = 0;
      this.fullHouse = 0;
      this.flush = 0;
      this.straight = 0;
      this.threeOfAKind = 0;
      this.twoPair = 0;
      this.onePair = 0; 
      this.highCard = 0;

      for(let i = 0; i < players; i++){
        this.players.push(new Player(0));
      }

      for(let i = 0; i < decks; i++){
        this.decks.push(new Deck(i));
      }

      this.startGame();
    }

    getCurrentCards(){
      for(let i = 0; i < this.decks.length; i++){
        this.currentCards = this.currentCards.concat(this.decks[i].cards);
      }
    }
    // This is the Fischer Yeats Shuffle 
    shuffle() {
      let array = this.currentCards;
      var currentIndex = array.length, temporaryValue, randomIndex;

      // While there remain elements to shuffle...
      while (0 !== currentIndex) {

        // Pick a remaining element...
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;

        // And swap it with the current element.
        temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
      }
      this.currentCards = array;
    }

    shuffleCurrentCards(){
      // randomly pick two places in the deck and swap the cards 
      for(let i = 0; i < 100000; i++){
        let idx1 = Math.floor(Math.random() * this.currentCards.length);
        let idx2 = Math.floor(Math.random() * this.currentCards.length);

        // swap the cards 
        let card = this.currentCards[idx1];
        this.currentCards[idx1] = this.currentCards[idx2];
        this.currentCards[idx2] = card;
      }
    }

    determineBestHand(){
      for(let i = 0; i < this.players.length; i++){
        let current = this.players[i];
        if(current.hasRoyalFlush(this.tableCards)){
          current.bestCombination = "Royal Flush";
          current.value = 10;
          this.royalFlush++;
        } else if(current.hasStraightFlush(this.tableCards)){
          current.bestCombination = "Straight Flush";
          current.value = 9;
          this.straightFlush++;
        } else if(current.has4k(this.tableCards)){
          current.bestCombination = "Four of a Kind";
          current.value = 8;
          this.fourOfAKind++;
        } else if(current.hasFullHouse(this.tableCards)){
          current.bestCombination = "Full House";
          current.value = 7;
          this.fullHouse++;
        } else if(current.hasFlush(this.tableCards)){
          current.bestCombination = "Flush";
          current.value = 6;
          this.flush++;
        } else if(current.hasStraight(this.tableCards)){
          current.bestCombination = "Straight";
          current.value = 5;
          this.straight++;
        } else if(current.has3k(this.tableCards)){
          current.bestCombination = "Three of a Kind";
          current.value = 4;
          this.threeOfAKind++;
        } else if(current.has2P(this.tableCards)){
          current.bestCombination = "Two Pair";
          current.value = 3;
          this.twoPair++;
        } else if(current.hasP(this.tableCards)){
          current.bestCombination = "Pair";
          current.value = 2;
          this.onePair++;
        } else {
          current.highCardFn(this.tableCards);
          current.bestCombination = "High Card";
          current.value = 1;
          this.highCard++;
        }
      }
    }

    determineWinner(){
      this.determineBestHand();
      // this.evaluateResults();
    }

    playHand(){
      this.dealCards();
      this.determineWinner();
      this.clearCards();
    }

    clearCards(){
      for(let i = 0; i < this.players.length; i++){
        this.usedCards.push(this.players[i].hand.pop());
        this.usedCards.push(this.players[i].hand.pop());
      }

      for(let i = 0; i < 5; i++){
        this.usedCards.push(this.tableCards.pop());
      }
    }

    resetCards(){
      this.currentCards = this.currentCards.concat(this.usedCards);
      this.usedCards = [];
      this.shuffleCurrentCards();

    }

    dealCards(){
      let numberOfCards = this.currentCards.length;
      let neededCards = (this.players.length) * 2 + 5;
      if(neededCards > numberOfCards){
        this.resetCards();
      }
      for(let i = 0; i < this.players.length; i++){
        let target = this.players[i];
        target.hand.push(this.currentCards.pop());
        target.hand.push(this.currentCards.pop());
      }

      for(let i = 0; i < 5; i++){
        this.tableCards.push(this.currentCards.pop());  
      }
    }

    startGame(){
      // put current cards into an array
      this.getCurrentCards();

      // shuffle the cards 
      this.shuffleCurrentCards();

      // combine the decks 
      // keep track of cards left
      // if cards left < num players * 2 + 5, shuffle cards 

      // after determining winner move cards from current cards to used cards;

      for(let i = 0; i < this.hands; i++){
        this.playHand();
      }
      console.log("royal flushes: " + this.royalFlush);
      console.log("straight flushes: " + this.straightFlush);
      console.log("four of a kind: " + this.fourOfAKind);
      console.log("full house: " + this.fullHouse);
      console.log("flush: " + this.flush);
      console.log("straight: " + this.straight);
      console.log("three of a kind: " + this.threeOfAKind);
      console.log("two pair: " + this.twoPair);
      console.log("one pair: " + this.onePair);
      console.log("high card: " + this.highCard);
    }
  }

  class Player {
    constructor(money){
      this.money = money;
      this.hand = [];
      this.bestCombination = null;
      this.handsWon = 0;
      this.handsLost = 0; 
      this.handsTied = 0;
      this.highCard = null;
      this.value = 0;
    }

    // straights cant wrap around 
    hasRoyalFlush(tableCards){
      // 10 11 12 13 1 
      let totalCards = [...tableCards, ...this.hand];
      let map = {
        "Spade": [],
        "Heart": [],
        "Diamond": [],
        "Club": []
      };

      // map the cards to suits
      for(let i = 0; i < totalCards.length; i++){
        let card = totalCards[i];
        map[card.suit].push(card);
      }

      // This will sort so items are increasing left to right
      var compare = function(a, b){
        return a.number - b.number;
      };

      // sort the arrays 
      for(let key in map){
        let arr = map[key];
        arr.sort(compare);
      }

      // go through each suit and look for a flush
      for(let key in map){
        let suit = map[key];

        // need 5 cards at least 
        if(suit.length >= 5){
          let start = suit[0].number;
          let consecutiveCards = 1;

          // look at consecutive cards 
          for(let i = 1; i < suit.length; i++){
            let prevCardNum = suit[i - 1].number;
            let currCardNum = suit[i].number;

            if(currCardNum === prevCardNum + 1){
              consecutiveCards++;
              // if you hit a king and are in a consecutive card pattern and first card in suit is a 1 then RF 
              if(currCardNum === 13 && (consecutiveCards === 4 && start == 10
                  || consecutiveCards === 5 && start == 9
                  || consecutiveCards === 6 && start === 8)
                && suit[0].number === 1){
                console.log(suit);
                return true;
              }

            } else {
                consecutiveCards = 1;
                start = suit[i].number;
            }
          } 
        }
      }

      return false;
    }

    // straights cant wrap around 
    hasStraightFlush(tableCards){
      let totalCards = [...tableCards, ...this.hand];
      let map = {
        "Spade": [],
        "Heart": [],
        "Diamond": [],
        "Club": []
      };

      // map the cards to suits
      for(let i = 0; i < totalCards.length; i++){
        let card = totalCards[i];
        map[card.suit].push(card);
      }

      // This will sort so items are increasing left to right
      var compare = function(a, b){
        return a.number - b.number;
      };

      // sort the arrays 
      for(let key in map){
        let arr = map[key];
        arr.sort(compare);
      }

      // go through each suit and look for a flush
      for(let key in map){
        let suit = map[key];

        // need 5 cards at least 
        if(suit.length >= 5){
          let consecutiveCards = 1;

          // look at consecutive cards 
          for(let i = 1; i < suit.length; i++){
            let prevCardNum = suit[i - 1].number;
            let currCardNum = suit[i].number;

            if(currCardNum === prevCardNum + 1){
              consecutiveCards++;
              if(consecutiveCards === 5){
                return true;
              }
            } else {
              consecutiveCards = 1;
            }
          } 
        }
      }

      return false;
    }

    has4k(tableCards){
      let totalCards = [...tableCards, ...this.hand];
      let map = {};

      for(let i = 0; i < totalCards.length; i++){
        let card = totalCards[i];
        if(!map[card.number]){
          map[card.number] = 1;
        } else {
          map[card.number]++;
        }
      }

      for(var key in map){
        if(map[key] === 4){
          return true;
        }
      }
      
      return false;
    }

    hasFullHouse(tableCards){
      let totalCards = [...tableCards, ...this.hand];
      let map = {};
      let threeOfAKind = false;
      let pair = false;

      for(let i = 0; i < totalCards.length; i++){
        let card = totalCards[i];
        if(!map[card.number]){
          map[card.number] = 1;
        } else {
          map[card.number]++;
        }
      }

      for(var key in map){
        if(map[key] === 3){
          threeOfAKind = true;
        }
        if(map[key] === 2){
          pair = true;
        }
      }

      return pair && threeOfAKind;
    }

    hasFlush(tableCards){
      let totalCards = [...tableCards, ...this.hand];
      let map = {};

      for(let i = 0; i < totalCards.length; i++){
        let card = totalCards[i];
        if(!map[card.suit]){
          map[card.suit] = 1;
        } else {
          map[card.suit]++;
        }
      }

      for(var key in map){
        if(map[key] === 5){
          return true;
        }
      }
      
      return false;
    }

    // straights cant wrap around 
    hasStraight(tableCards){
      let totalCards = [...tableCards, ...this.hand];

      // This will sort so items are increasing left to right
      var compare = function(a, b){
        return a.number - b.number;
      };

      // sort the arrays 
      totalCards.sort(compare);

      let suit = totalCards;
      let consecutiveCards = 1;

      // look at consecutive cards
      for(let i = 1; i < suit.length; i++){
        let prevCardNum = suit[i - 1].number;
        let currCardNum = suit[i].number;

        if(currCardNum === prevCardNum + 1){
          consecutiveCards++;
          if(consecutiveCards === 5){
            return true;
          }
        } else {
          consecutiveCards = 1;
        }
      }  
      return false;
    }

    has3k(tableCards){
      let totalCards = [...tableCards, ...this.hand];
      let map = {};

      for(let i = 0; i < totalCards.length; i++){
        let card = totalCards[i];
        if(!map[card.number]){
          map[card.number] = 1;
        } else {
          map[card.number]++;
        }
      }

      for(var key in map){
        if(map[key] === 3){
          return true;
        }
      }

      return false;
    }

    has2P(tableCards){
      let pairs = 0;
      let totalCards = [...tableCards, ...this.hand];
      let map = {};

      for(let i = 0; i < totalCards.length; i++){
        let card = totalCards[i];
        if(!map[card.number]){
          map[card.number] = 1;
        } else {
          map[card.number]++;
          if(map[card.number] === 2){
            pairs++;
          }
        }
      }
      if(pairs === 2){
        return true;
      }

      return false;
    }

    hasP(tableCards){
      let totalCards = [...tableCards, ...this.hand];
      let map = {};

      for(let i = 0; i < totalCards.length; i++){
        let card = totalCards[i];
        if(!map[card.number]){
          map[card.number] = 1;
        } else {
          map[card.number]++;
        }
      }

      for(var key in map){
        if(map[key] === 2){
          return true;
        }
      }

      return false;
    }

    highCardFn(tableCards){
      let totalCards = [...tableCards, ...this.hand];
      let max = 0; 
      let card = null;

      for(var i = 0; i < totalCards.length; i++){
        let target = totalCards[i];
        if(target.number > max){
          max = target.number;
          card = target;
        }
      }
    }
  }

  const game = new Game(3, 1, 2200);

  // Total hands will be number of players * number of hands

});



