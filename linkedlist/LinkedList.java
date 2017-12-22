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

  // figure out if there is a cycle
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

  // return the node that starts the cycle, assumes there is a cycle
  public static Node cycleNode(LinkedList l1){
    Node rabbit = l1.head;
    Node hare = l1.head;

    rabbit = rabbit.next;
    hare = hare.next.next;

    while(rabbit != null && hare != null){
      while(rabbit != hare){
        rabbit = rabbit.next;
        hare = hare.next.next;
      }
      hare = l1.head;
      while(rabbit != hare){

        rabbit = rabbit.next;
        hare = hare.next;
      }
      return rabbit;
    }
    return null;
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

  public static int cycleStart(LinkedList l1){

    int start = 0;

    Node rabbit = l1.head;
    Node hare = l1.head;

    rabbit = rabbit.next;
    hare = hare.next.next;

    while(rabbit != null && hare != null){
      while(rabbit != hare){
        rabbit = rabbit.next;
        hare = hare.next.next;
      }
      hare = l1.head;
      while(rabbit != hare){

        rabbit = rabbit.next;
        hare = hare.next;
        start++;
      }
      return start;
    }

    return 0;
  }

  // This method assumes that there could be cycles 
  public static Node commonNode(LinkedList l1, LinkedList l2, boolean cycles){
    boolean cycle1 = detectCycle(l1);
    boolean cycle2 = detectCycle(l2);

    // if both have cycles
    // intersect before cycle
    if(cycle1 && cycle2){
      int cycle1Start = cycleStart(l1);
      int cycle2Start = cycleStart(l2);
      Node start1 = l1.head;
      Node start2 = l2.head;
      int pos1 = 1;
      int pos2 = 1;
      
      int diff = 0;
      
      if(cycle2Start > cycle1Start){
        diff = cycle2Start - cycle1Start;
        while(diff > 0){
          start2 = start2.next;
          diff--;
          pos2++;
        }
        // now star1 and start2 are same distance from cycle 
      }

      if(cycle1Start > cycle2Start){
        diff = cycle1Start - cycle2Start;
        while(diff > 0){
          start1 = start1.next;
          diff--;
          pos1++;
        }
        // now star1 and start2 are same distance from cycle 
      }

      while(start1 != start2 && pos1 < cycle1Start && pos2 < cycle2Start){
          if(start1 == start2){
            System.out.println("Intersect before cycle " + start1.value);
            return start1;
          } else {
            start1 = start1.next;
            start2 = start2.next;
            pos1++;
            pos2++;
          }
        }

      // interset during cycle
      Node c1 = cycleNode(l1);
      Node c2 = cycleNode(l2);

      System.out.println("Intersect during cycle " + c1.value);

      return c1;

    }

    // if one has a cycle and one does not

    if(cycle1 != cycle2){
      return null;
    }

    // if neither have cycles

    if(cycle1 == false && cycle2 == false){
      return commonNode(l1, l2);
    }


    return null;
  }

  // This method assumes there are no cycles 
  public static Node commonNode(LinkedList l1, LinkedList l2){
    int length1, length2; 
    length1 = length2 = 1;

    Node p1, p2; 
    p1 = l1.head;
    p2 = l2.head;

    while(p2 != null){
      p2 = p2.next;
      length2++;
    }

    while(p1 != null){
      p1 = p1.next;
      length1++;
    }

    int diff = 0;
    boolean longer1 = false; 

    if(length1 >= length2){
      diff = length1 - length2; 
      longer1 = true;
    } else {
      diff = length2 - length1;
      longer1 = false;
    }

    // reset pointers to head of list
    p1 = l1.head;
    p2 = l2.head;

    if(longer1){
      while(diff > 0){
        p1 = p1.next; 
        diff--;
      }
    } else {
      while(diff > 0){
        p2 = p2.next;
        diff--;
      }
    }
    while(p1 != null && p2 != null){
      if(p1 == p2){
        System.out.println("Intersecting Node Value " + p1.value);
        return p1; 
      }
      p1 = p1.next;
      p2 = p2.next;  
    }
    
    return null;
  }

  // Merge two sorted linkedLists 
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

  // assumes pointer to node 
  void deleteNodeWithPointer(Node pointer){
    
    // head
    if(pointer == this.head){
      this.head = this.head.next;
      return;
    }
    // tail 
    if(pointer.next == null){
      System.out.println("Cant be tail");
      return;
    }

    pointer.value = pointer.next.value;
    pointer.next = pointer.next.next;
  }

  void deleteNode(int val){
    Node current = this.head;
    Node previous = this.head;

    while(current != null && current.value != val){
      previous = current;
      current = current.next;
    }

    if(current != null){
      previous.next = current.next;
    }

    return;

  }

  // assume that the list is at least k length
  void deleteKthLastElement(int k){

    Node winner, leader;
    winner = leader = this.head;

    while(k > 0){
      k--;
      leader = leader.next;
    }

    while(leader != null){
      leader = leader.next;
      winner = winner.next;
    }

    winner.value = winner.next.value;
    winner.next = winner.next.next;
  }

  static void removeDuplicatesFromSorted(LinkedList L1){
    Node current = L1.head; 

    while(current != null){
      Node next = current.next;
      while(next != null && current.value == next.value){
        next = next.next;
        if(next == null){
          current.next = null;
          return;
        }
      }

      current.next = next;
      
      current = current.next;
    }
  }
  // assuming k < L1.size()
  static void cyclicRightShift(LinkedList L1, int k){
    Node current = L1.head;
    Node prev = null;

    while(k > 0){
    	prev = current;
    	current = current.next;
    	k--;
    }
    // found where new head should be
    Node newHead = current;

    while(current.next != null){
    	current = current.next;
    }

    // found the tail of the list
    Node tail = current;

    // connect tail to old head
    tail.next = L1.head;

    // make new tail out of new head prev
    prev.next = null;

    L1.head = newHead;

  }

  // start at zero and put even links before odd links 
  static LinkedList evenOddMerge(LinkedList L1){

  	if(L1 == null){
  		return null;
  	}

  	Node head1 = L1.head;
  	Node head2 = L1.head.next;

  	Node p1 = head1;
  	Node p2 = head2;

  	while(p1 != null && p1.next != null && p2.next != null){
  		p1.next = p1.next.next;
  		p1 = p1.next;

  		p2.next = p2.next.next;
  		p2 = p2.next;
  	}

  	if(head2 == null){
  		return L1;
  	}

  	p1.next = head2;

  	return L1;
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

    Node node = new Node(44);
    Node node2 = new Node(88);

    LinkedList list1 = new LinkedList();
    list1.add(10);
    list1.add(25);
    list1.add(55);
    list1.add(72);
    
    Node node3 = new Node(101);
    node3.next = node;
    list1.add(node3);

    LinkedList list2 = new LinkedList();
    list2.add(5);
    // list2.add(5);
    list2.add(10);
    list2.add(15);
    list2.add(77);
    // list2.add(15);
    // list2.add(15);

    

    list2.add(node);
    list2.add(node2);
    list2.add(1337);
    list2.add(2084);
    // list2.add(2084);
    // list2.add(2084);

    // list1.printList();
    list2.printList();

    // list2.deleteNode(44);
    // Node nodeToDelete = list2.head.next.next.next.next.next.next;
    // list2.deleteNodeWithPointer(nodeToDelete);
    // int k = 3; // Should delete 88 
    // list2.deleteKthLastElement(k);

    // list2.removeDuplicatesFromSorted();

    // This creates a cycle, comment out to test other methods
    // Node nodeLoop = new Node(45);
    // nodeLoop.next = list2.head.next.next.next;
    // list2.add(nodeLoop);
    
    // list2.printList();

    // LinkedList.merge(list1, list2);
    // cycleNode(list2);
    // reverse(list2);

    // reverseSublist(list2, 2, 4);

    // System.out.println(LinkedList.detectCycle(list2));

    // commonNode(list1, list2);

    // commonNode(list1, list2, true);

    // removeDuplicatesFromSorted(list2);
    
    // cyclicRightShift(list2, 3);

    evenOddMerge(list2);

    list2.printList();


  }
}