package com.example.mypackage;

public class Main {

    public static void main(String[] args) {

        ListNode node = new ListNode(10);
        ListNode node0 = new ListNode(15);
        ListNode node1 = new ListNode(20);
        ListNode node2 = new ListNode(30);
        ListNode node3 = new ListNode(40);
        ListNode node4 = new ListNode(1337);
        ListNode node5 = new ListNode(9001);

        LinkedList list = new LinkedList(node);
        ListNode head = list.head;
        head.addNode(node0);
        head.addNode(node1);
        head.addNode(node2);
        head.addNode(node3);
        head.addNode(node4);
        head.addNode(node5);

        list.printList();

        // pass two integers i and j and reverse the nodes in this area
        // i = 3, j = 5 --> 10 15 40 30 20 1337 9001

        list.reverseSubList(3,5);
        list.printList();


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

    public void reverseSubList(int i, int j){
        ListNode current = this.head, prev = this.head, initialPrev = this.head;
        int counter = 1;

        // get to the right node to manipulate
        while(counter <  i){
            prev = current;
            current = current.getNext();
            counter++;
        }
        initialPrev = prev; // initialPrev.next needs to be node j
        ListNode next = this.head, savedNext = this.head;
        while(counter < j){
            if(counter == i ){
                prev = current;
                current = current.getNext();
                next = current.getNext();
                prev.setNext(null);
                current.setNext(prev);
                savedNext = prev;
                counter++;
            }
             prev = current;
             current = next;
             next = current.getNext();
             current.setNext(prev);

             counter++;
        }
        
        initialPrev.setNext(current);
        savedNext.setNext(next);


    }
}