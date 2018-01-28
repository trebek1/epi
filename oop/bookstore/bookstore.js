document.addEventListener("DOMContentLoaded", function(){

  // books 
  // reviews for books 
  // books have an author 

  class Book {

    static generateId(){
      console.log(Math.random());
    }

    constructor(title, author, isbn){
      this.title = title;
      this.author = author;
      this.isbn = isbn;
      this.reviews = [];
    }

    getIsbn(){
      return this.isbn;
    }

    setIsbn(isbn){
      this.isbn = isbn;
    }

    getAuthor(){
      return this.author;
    }

    getTitle(){
      return this.title;
    }

    setAuthor(author){
      this.author = author;
    }

    setTitle(title){
      this.title = title;
    }

    addReview(rev){
      this.reviews.push(rev);
    }

    getReviews(){
      return this.reviews;
    }
  }

  class Author {
    constructor(first, last){
      this.first = first;
      this.last = last;
    }
  }

  class Review {
    constructor(message){
      this.message = message;
    }
  }

  class Animal {
    constructor(age){
      this.walk = true;
      this.life = 100;
      this.age = age;
    }
  }

  class Monkey extends Animal {
    constructor(age){
      super(age);
      this.tail = true;
    }

    noise(){
      console.log("he he he");
    }
  }

  var m = new Monkey(11);

  console.log("Age " + m.age);
  console.log("Tail " + m.tail);
  console.log("Walk " + m.walk);
  m.noise();


  // Static Methods in JavaScript
  // console.log(Book.generateId());

});




