package LinkedList;

class Node {

    private Node left, right;
    private int data;

    public Node(int data) {
        this.data = data;
    }
    public Node (int data, Node left, Node right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public void delete(){
        if (right != null) {
            this.setLeft(left);
        }
        if (left != null) {
            this.setRight(right);
        }

        this.left = null;
        this.right = null;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getData(){
        return this.data;
    }

    public Node getLeft(){
        return this.left;
    }

    public Node getRight(){
        return this.right;
    }
}
