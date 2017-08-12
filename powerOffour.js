
(function(){

	function isPowerOfFour(num){
		// if its zero or not an integer return false; 
		if(num === 0 || num != Math.floor(num)){
			console.log("false");
			return false;
		}
		// when number is 4 then return true (4/4 is 1); 
		if(num === 1 || num === 4){
			console.log("true");
			return true; 
		}

		// no remainder when subtracting 4s
		if(num % 4 === 0){
			return isPowerOfFour(num/4); 
			
		}else{
			// if it has a remainder when subtracting fours return false; 
			console.log("false"); 
			return false; 
		}
	}

	isPowerOfFour(0); // false 
	isPowerOfFour(7); // false 
	isPowerOfFour(8); // false 
	isPowerOfFour(16); // true 
	isPowerOfFour(32); // should be false 
	isPowerOfFour(64); // true 

})(); 