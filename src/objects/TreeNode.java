package objects;

public class TreeNode<T extends Comparable<T>> implements Comparable<TreeNode<T>>{
    private T data;
    private TreeNode<T> left;
    private TreeNode<T> right;
    private TreeNode<T> parent;

    public boolean hasLeft(){
        return left != null;
    }

    public  boolean hasRight(){
        return right != null;
    }

    public boolean hasChildren(){
        return hasLeft() ||  hasRight();
    }

    public TreeNode(T item, TreeNode<T> parent){
        this.data = item;
        this.parent = parent;
        this.left = null;
        this.right = null;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }


    @Override
    public int compareTo(TreeNode<T> rhs) {
        return this.data.compareTo(rhs.getData());
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode<T> getParent() {
        return parent;
    }
}
