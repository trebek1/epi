package com.example.mypackage;

public class Main {

    public static void main(String[] args) {

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
}

