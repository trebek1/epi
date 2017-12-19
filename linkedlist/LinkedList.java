

class LinkedList {
	Node head;
	Integer len;
	LinkedList(Node node){
		head = node;
		len = 0;
	}

	LinkedList(){
		head = null;
		len = 0;
	}

	void add(Node node){
		Node current, prev;
		current = prev = this.head;

		while(current != null){
			prev = current;
			current = current.next;
		}
		if(prev == null){
			this.head = node;
		} else {
			prev.next = node;	
		}
		this.len++;
	}

	public static boolean detectCycle(LinkedList l1){

		Node rabbit = l1.head;
		Node hare = l1.head;

		while(rabbit != null && hare != null){
			if(hare.next != null && hare.next.next != null){
				hare = hare.next.next;
			} else {
				return false;
			}
			rabbit = rabbit.next;

			if(rabbit == hare){
				return true;
			}
		}
		return false;
	}

	void add(int val){
		Node node = new Node(val);
		Node current, prev;
		current = prev = this.head;

		while(current != null){
			prev = current;
			current = current.next;
		}
		if(prev == null){
			this.head = node;
		} else {
			prev.next = node;	
		}
		this.len++;
	}
	// removes the head 
	void remove(){
		if(this.len == 0){
			System.out.println("No elements to remove");
		}

		Node current = this.head;
		this.head = current.next;

	}
	// removes a specific index 
	void remove(int i){
		if(this.len == 0){
			System.out.println("No elements to remove");
		}
		Node current, prev;
		current = prev = this.head;
		int idx = 0;
		while(idx < i){
			current = current.next;
			if(idx != 0){
				prev = prev.next;
			}
			idx++;
			if(current == null){
				System.out.println("index does not exist in list");
				return;
			}	
		}
		prev.next = current.next;
	}

	void printList(){
		Node current = this.head;

		while(current != null){
			System.out.print(current.value + " ---> " );
			current = current.next;
		}
			System.out.print(" null " + '\n');
	}

	public static LinkedList reverseSublist(LinkedList l1, int first, int end){
		int count = 0;
		Node current = l1.head;
		Node temp = null;
		Node start = null;
		Node prev;

		while(count < first){
			temp = current;
			current = current.next;
			count++;
		}

		// save prev as temp
		prev = temp;

		// save the initial position
		start = current;

		Node reverseTemp = null;
		while(count < end){
			reverseTemp = current.next;
			current.next = prev;
			prev = current;
			current = reverseTemp;
			count++;
		}
		// set the start next first since youll lose it 
		start.next = current.next;

		// then set current next to finish off loop
		current.next = prev;
		
		// now just point the start at your current node 
		temp.next = current;

		l1.printList();
		return l1;

	}

	// O(1) for creating new dummy head variable 
	// you cant "add" to new linkedlist bc you will be adding the whole list each time 
	// can also do this recursively 
	public static LinkedList reverse(LinkedList l1){
		Node current = l1.head;
		Node prev = null;
		Node temp = null;
		if(current == null){
			return null;
		}

		while(current != null){
			// save the next one 
			temp = current.next;

			// set the working next
			current.next = prev;
			
			// update prev 
			prev = current;

			// update current;
			current = temp;
		}
		l1.head = prev; 

		l1.printList();
		return l1;
		
	}

	public static LinkedList merge(LinkedList l1, LinkedList l2){

		LinkedList l3 = new LinkedList();
		Node current = l3.head;

		Node h1 = l1.head;
		Node h2 = l2.head;
		int index1 = 0;
		int index2 = 0;

		// take care of either being null
		if(h1 == null){
			if(h2 == null){
				return null;
			}
			return l1;
		}

		if(h2 == null){
			if(h1 == null){
				return null;
			}
			return l2;
		}

		while(h1 != null && h2 != null){
			if(h1.value < h2.value){
				if(current == null){
					l3.head = h1;
					current = h1;
					h1 = h1.next;
					continue;
				}
				current.next = h1;
				current = current.next;
				h1 = h1.next;
			} else if(h2.value < h1.value){
				if(current == null){
					l3.head = h2;
					current = h2;
					h2 = h2.next;
					continue;
				}
				current.next = h2;
				current = current.next;
				h2 = h2.next;
			} else {
				return null;
			}
		}
		System.out.println("Out of while loop");
		if(h2 == null){
			l3.add(h1);
		} else if(h1 == null){
			l3.add(h2);
		}

		System.out.println(l3.len);
		l3.printList();
		return l3;

	}

	public static void main(String[] args){
		LinkedList LL = new LinkedList();

		Node n1 = new Node(25);
		Node n2 = new Node(30);
		Node n3 = new Node(35);
		Node n4 = new Node(40);
		Node n5 = new Node(45);
		Node n6 = new Node(50);
		Node n7 = new Node(55);
		Node n8 = new Node(60);
		Node n9 = new Node(65);
		Node n10 = new Node(70);

		LL.add(n1);
		LL.add(n2);
		LL.add(n3);
		LL.add(n4);
		LL.add(n5);
		LL.add(n6);
		LL.add(n7);
		LL.add(n8);
		LL.add(n9);
		LL.add(n10);


		// LL.printList();


		LinkedList list1 = new LinkedList();
		list1.add(10);
		list1.add(25);
		list1.add(55);
		list1.add(72);
		list1.add(101);

		LinkedList list2 = new LinkedList();
		list2.add(5);
		list2.add(10);
		list2.add(15);
		list2.add(44);
		list2.add(88);
		list2.add(1337);
		list2.add(2084);

		// This creates a cycle, comment out to test other methods
		Node node = new Node(45);
		node.next = list2.head.next.next.next;
		list2.add(node);
		

		// list2.printList();

		// LinkedList.merge(list1, list2);

		// reverse(list2);

		// reverseSublist(list2, 2, 4);


		System.out.println(LinkedList.detectCycle(list2));






	}
}




