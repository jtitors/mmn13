package LinkedList;

public class List {
    private Node head;
    private Node tail;
    private Node min;
    private int size;
    private static boolean sorted;

    public List(){}

    public List(String[] input){
        if (input == null || input.length == 0) return;

        if(!sorted) this.createdUnsorted(input);
        else this.createSorted(input);
    }

    private void createSorted(String[] input){
        for (String s : input) {
            try {
                  this.insertSorted(Integer.parseInt(s.trim()));
            } catch (Exception e) {
                invalidNumberError(s);
                continue;
            }
        }
    }

    private void createdUnsorted(String[] input) {
        Node current = this.head;
        for (String s : input) {
            try {
                this.insert(new Node(Integer.parseInt(s.trim())),head, null);
            } catch (NumberFormatException e) {
                invalidNumberError(s);
            }
        }
    }

    private static void invalidNumberError(String s){
        System.out.println("Error: \""+s+"\" is not a valid number. The node has not been inserted.");
    }

    public static void makeHeap(List heap, String[] input)
    {
        if (input == null || input.length == 0) {
            heap.size = 0;
            heap.head = null;
            heap.tail = null;
            heap.min = null;
        }
        else {
            List newHeap = new List(input);
            heap.head = newHeap.head;
            heap.tail = newHeap.tail;
            heap.min = newHeap.min;
            heap.size = newHeap.size;
        }

    }

    public static void setSort(boolean sort){
        sorted = sort;
    }
//    If sorted - add all items from B to A - O(n) while n=sizeA+sizeB

    public static void union(List listA, List listB) {
        if (listA == null || listB == null || listA.size == 0 || listB.size == 0) {
            System.out.println("Union failed - check that the lists contain elements.");
            return;
        }

        if (!sorted){
            listA.tail.setRight(listB.head);
            listA.tail.getRight().setLeft(listA.tail);
            listA.tail = listB.tail;
            listB.head = null;
            listB.tail = null;
            listB.size = 0;
            return;
        }

        while (listB.size > 0){
            listA.insertSorted(listB.extractMin().getData());
        }
    }

    private void insertSorted(int data){
        if (this.head == null) {
            this.head = new Node(data);
            this.tail = this.head;
            this.min = this.head;
            this.size++;
            return;
        }
        if (this.Minimum() > data) {
            this.head.setLeft(new Node(data, null, this.head));
            this.head = this.head.getLeft();
            this.min = this.head;
            this.size++;
            return;
        }
        Node current = this.head;

        while (current.getRight() != null){
            if (data < current.getData()) {
                current.setLeft(new Node(data, current.getLeft(), current));
                current.getLeft().getLeft().setRight(current.getLeft());
                this.size++;
                return;
            }
            current = current.getRight();
        }
        if (data < current.getData()) {
            current.setLeft(new Node(data, current.getLeft(), current));
            current.getLeft().getLeft().setRight(current.getLeft());
            this.size++;
            return;
        }
        current.setRight(new Node(data, current, null));
        this.tail = current.getRight();
        this.size++;
    }

    private void insertSorted(Node newNode){
        if (this.head == null) {
            this.head = newNode;
            this.tail = this.head;
            this.min = this.head;
            this.size++;
            return;
        }

        if (this.Minimum() > newNode.getData()) {
            this.head.setLeft(newNode);
            newNode.setRight(this.head);
            this.head = newNode;
            this.min = this.head;
            this.size++;
            return;
        }

        Node current = this.head;
        while (current.getRight() != null){
            if (newNode.getData() < current.getData()) {
                current.setLeft(newNode);
                newNode.setLeft(current.getLeft());
                newNode.setRight(current);
                newNode.getLeft().setRight(newNode);
                this.size++;
                return;
            }
            current = current.getRight();
        }
        if (newNode.getData() < current.getData()) {
            newNode.setLeft(current.getLeft());
            newNode.setRight(current);
            current.setLeft(newNode);
            newNode.getLeft().setRight(newNode);
            this.size++;
            return;
        }
        current.setRight(newNode);
        newNode.setLeft(current);
        this.tail = newNode;
        this.size++;
    }

    private void insert(Node newNode, Node leftNode, Node rightNode) {
        if (this.head == null){
            this.head = newNode;
            this.tail = newNode;
            this.min = newNode;
            this.size++;
        } else if (leftNode == null) {
            newNode.setRight(this.head);
            this.head.setLeft(newNode);
            this.head = newNode;
            this.size++;
            if (newNode.getData() < this.Minimum()) this.min = newNode;
        } else if (rightNode == null) {
        tail.setRight(newNode);
            newNode.setLeft(tail);
            tail = newNode;
            if (newNode.getData() < this.Minimum()) min = newNode;
            this.size++;
        }

    }

//    Minimum is always set - O(1)
    public Integer Minimum(){
        if (this.min == null) return null;
        return this.min.getData();
    }
    
    public Node extractMin() {
        if (size < 1) {
            System.out.println("Heap underflow");
            return null;
        }

        Node minimum = min;
        Node currentNode = this.head;

//        For sorted list - the next node after minimum is the new minimum - O(1)
       if (sorted) {
           if (this.min.getRight() != null) {
               this.min = this.min.getRight();
               this.min.getLeft().delete();
               this.head = this.min;
           } else {
               this.min.delete();
               this.head = null;
           }

           this.size--;
           if (this.size == 0) this.min = null;
           return minimum;
       }

//       For unsorted list - iterate through the list and find the new minimum - O(n)
       int newMin = Integer.MAX_VALUE;
       while (currentNode != null) {
           if (currentNode.getData() < newMin){
               newMin = currentNode.getData();
               min = currentNode;
           }
           currentNode = currentNode.getRight();
       }
       return minimum;
    }

    @Override
    public String toString(){
        if (this.head == null) return "null";
        Node current = this.head;
        String result = "";
        while (current.getRight() != null) {
            result += current.getData() + " -> ";
            current = current.getRight();
        }
        result += current.getData();
        return result;
    }
}
