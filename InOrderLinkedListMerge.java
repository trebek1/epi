package com.example.mypackage;

public class Main {

    public static void main(String[] args) {
        ListNode node = new ListNode(5);
        ListNode node1 = new ListNode(7);
        ListNode node2 = new ListNode(9);
        ListNode node3 = new ListNode(123);

        LinkedList list = new LinkedList(node);
        list.addNode(node1);
        list.addNode(node2);
        list.addNode(node3);

        ListNode head = list.getHead();
        ListNode nodeTarget = head;
        while(nodeTarget.getNext() != null){
            nodeTarget = nodeTarget.getNext();
        }

        ListNode node4 = new ListNode(10);
        ListNode node5 = new ListNode(20);
        ListNode node6 = new ListNode(50);
        ListNode node7 = new ListNode(100);
        LinkedList list2 = new LinkedList(node4);
        list2.addNode(node5);
        list2.addNode(node6);
        list2.addNode(node7);

        ListNode head2 = list2.getHead();
        ListNode nodeTarget2 = head2;
        while(nodeTarget2.getNext() != null){
            nodeTarget2 = nodeTarget2.getNext();
        }

        ListNode a = new ListNode(10);
        ListNode b = a;
        a.setData(25);

        LinkedList.combine(list,list2);
    }
}

class ListNode{
    private int data;
    private ListNode next;

    public ListNode(int val){
        this.data = val;
        this.next = null;
    }

    public ListNode getNext(){
        return this.next;
    }

    public void setNext(ListNode node){
        this.next = node;
    }

    public void setData(int data){
        this.data = data;
    }

    public int getData(){
        return this.data;
    }
}

class LinkedList{

    public static LinkedList combine(LinkedList l1, LinkedList l2){
        ListNode list1 = l1.getHead();
        ListNode list2 = l2.getHead();

        int size1 = l1.size();
        int size2 = l2.size();
        int count1 = 0;
        int count2 = 0;
        LinkedList soln;

        // Set the head of the LL to the smaller of the two.
        if((list1.getData()) > (list2.getData())){
            soln = new LinkedList(list2);
            list2 = list2.getNext();
            count2++;
        }else{
            soln = new LinkedList(list1);
            list1 = list1.getNext();
            count1++;
        }
        ListNode current = soln.getHead();
        while((list1!= null) && (list2!= null)){
            int one = list1.getData();
            int two = list2.getData();
            if(one > two){
                current.setNext(list2);
                current = current.getNext();
                list2 = list2.getNext();
                count2++;
            }else{
                current.setNext(list1);
                current = current.getNext();
                list1 = list1.getNext();
                count1++;
            }
        }

        if(count1 == size1){
            current.setNext(list2);
        }else{
            current.setNext(list1);
        }
        System.out.println("This is the solution!");
        //5,7,9,10,20,50,100,123
        soln.printList();
        return soln;
    }

    private ListNode head;
    public LinkedList(ListNode node){
        this.head = node;
    }

    public void printList(){
        ListNode currentNode = this.head;
        while(currentNode != null){
            System.out.println((currentNode.getData()));
            currentNode = currentNode.getNext();
        }
    }

    public ListNode getHead(){
        return this.head;
    }

    public int size(){
        ListNode head = this.getHead();

        int size = 1;

        ListNode current = head.getNext();

        while(current != null){
            size++;
            current = current.getNext();
        }

        return size;
    }

    public void addNode(ListNode node){
        ListNode head = this.getHead();
        ListNode next = head.getNext();
        ListNode prev = head;
        ListNode temp;

        while(next != null){
            prev = next;
            next = next.getNext();
        }
        prev.setNext(node);
    }
}




