package com.example.mypackage;

public class Main {

    public static void main(String[] args) {

        ListNode node  = new ListNode(1);
        ListNode node0 = new ListNode(2);
        ListNode node1 = new ListNode(3);
        ListNode node2 = new ListNode(4);
        ListNode node3 = new ListNode(5);
        ListNode node4 = new ListNode(6);
        ListNode node5 = new ListNode(7);

        LinkedList list = new LinkedList(node);
        ListNode head = list.head;
        head.addNode(node0);
        head.addNode(node1);
        head.addNode(node2);
        head.addNode(node3);
        head.addNode(node4);
        head.addNode(node5);

        list.printList();

        //list.addCycle(3);

        list.isThereACycle();

    }
}

class ListNode{
    private int val;
    private ListNode next = null;

    public ListNode(int val){
        this.val = val;
        this.next = null;
    }

    public int getVal(){
        return this.val;
    }

    public ListNode getNext(){
        return this.next;
    }

    public int setVal(int val){
        return this.val = val;
    }

    public void setNext(ListNode node){
        this.next = node;
    }
    public void addNode(ListNode node){
        ListNode current = this;
        while(current.next != null){
            current = current.next;
        }
        current.next = node;
    }
}

class LinkedList{
    public ListNode head;
    public LinkedList(ListNode node){
        this.head = node;
    }
    public void printList(){
        ListNode current = this.head;
        while(current != null){
            System.out.println(current.getVal());
            current = current.getNext();
        }
    }
    public void addCycle(int n){
        // n would be position where cycle starts
        int i = 1;
        ListNode node = this.head;
        while(i < n){
            node = node.getNext();
            i++;
        }
        ListNode startCycle = node;

        while(node.getNext() != null){
            node = node.getNext();
        }
        node.setNext(startCycle);
    }

    public boolean isThereACycle(){
        ListNode fast = this.head;
        ListNode slow = this.head;

        while(fast.getNext()!= null && fast.getNext().getNext() != null){
            slow = slow.getNext();
            fast = fast.getNext().getNext();

            if(slow.getVal() == fast.getVal()){
                System.out.println("true");
                return true;
            }

        }
        System.out.println("false");
        return false;
    }
}

// Now to get the start of the cycle reset position of tortoise to head and make both speeds 1. Go till collision and 
// that point will be the start of the cycle 

//why?  slow moves x + y; 
// fast moves x + y + z + y to make collision; 
// so 2(x+y) = x + y + z + y; 
// so x = z; Thus collision will be where cycle is 








 









