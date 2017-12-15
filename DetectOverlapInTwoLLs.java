package com.example.mypackage;

public class Main {

    public static void main(String[] args) {

        ListNode node01 = new ListNode(101);
        ListNode node02 = new ListNode(102);
        ListNode node03 = new ListNode(103);

        ListNode node = new ListNode(10);
        ListNode node1 = new ListNode(20);
        ListNode node2 = new ListNode(30);
        ListNode node3 = new ListNode(40);
        ListNode node4 = new ListNode(1337);
        ListNode node5 = new ListNode(9001);

        LinkedList list = new LinkedList(node);
        ListNode head = list.head;
        head.addNode(node1);
        head.addNode(node2);
        head.addNode(node3);
        head.addNode(node4);
        head.addNode(node5);

        LinkedList list2 = new LinkedList(node01);
        ListNode head2 = list2.head;
        head2.addNode(node02);
        head2.addNode(node03);
        head2.addNode(node2);

        System.out.println("LIST 1");
        list.printList();
        System.out.println("LIST 2");
        list2.printList();

        LinkedList.overlap(list,list2);

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
    public static boolean overlap(LinkedList l1, LinkedList l2){
        // how can you tell if two LLs overlap? Same tail.
        ListNode current1 = l1.head;
        ListNode current2 = l2.head;

        while(current1.getNext() != null){
            current1 = current1.getNext();
        }

        while(current2.getNext() != null){
            current2 = current2.getNext();
        }

        if(current1.getVal() == current2.getVal()){
            System.out.println("true");
            return true;
        }
        System.out.println("false");
        return false;
    }
}

// once detected, the best way to figure out what the overlapping node is, iterate through both lists
// and check the difference in lengths until you find a node value that overlaps smaller + diff



 



