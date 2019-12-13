package objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Stack;

public abstract class BinarySearchTree<T extends Comparable<T>> {
    private TreeNode<T> root;

    public boolean isRoot(T item){
        return root.getData().compareTo(item) == 0;
    }

    public void add(T item){
        if (root == null) {
            root = new TreeNode<>(item, null);
            return;
        }

        TreeNode<T> potentialParent = potentialParentOf(item);
        TreeNode<T> newNode = new TreeNode<>(item, potentialParent);


        if (potentialParent.compareTo(newNode) > 0){
            potentialParent.setLeft(newNode);
        }
        else{
            potentialParent.setRight(newNode);
        }
    }

    public ArrayList<TreeNode<T>> addAndGetPath(T item){
        add(item);
        return pathTo(item);
    }

    @SafeVarargs
    public final void add(final T... items){
        multiAddHelper(0, items);
    }

    public ArrayList<TreeNode<T>> pathTo(T item){
        TreeNode<T> searchNode = new TreeNode<>(item, null);
        return pathFinder(root, searchNode);
    }


    public ArrayList<T> getSorted(){
        ArrayList<T> sortBag = localSort(root);
        return sortBag;
    }

    public ArrayList<T> localSort(TreeNode<T> origin){
        ArrayList<T> sortBag = new ArrayList<>();
        Stack<TreeNode<T>> travelledNodes = new Stack<>();
        leftSortTraveller(sortBag, origin, travelledNodes);
        return sortBag;
    }






    private TreeNode<T> potentialParentOf(T item){
        return pathTo(item).get(0);
    }

    public TreeNode<T> closestMatch(T item){
        return pathTo(item).get(0);
    }

    private ArrayList<TreeNode<T>> pathFinder(TreeNode<T> current, TreeNode<T> searchNode){
        if (current == null){
            return new ArrayList<>();
        }
        else {
            ArrayList<TreeNode<T>> path;
            if (current.compareTo(searchNode) == 0) {
                    path = new ArrayList<>();
            }
            else if (current.compareTo(searchNode) > 0) {
                path = pathFinder(current.getLeft(), searchNode);

            }
            else {
                path = pathFinder(current.getRight(), searchNode);
            }

            path.add(current);
            return path;
        }
    }




    private void leftSortTraveller(Collection<T> collector, TreeNode<T> traveller,
                                   Stack<TreeNode<T>> travelled) {
        travelled.push(traveller);
        if (travelled.isEmpty()) return;

        else if (traveller.hasLeft()) {
            leftSortTraveller(collector, traveller.getLeft(), travelled);
        }


        TreeNode<T> climber = travelled.peek();
        collector.add(climber.getData());
        if (climber.hasRight()) leftSortTraveller(collector, climber.getRight(), travelled);
        travelled.pop();

    }



    @SafeVarargs
    private final void multiAddHelper(int index, final T... items){
        if (index == items.length) return;
        add(items[index]);
        multiAddHelper(++index, items);
    }


    public abstract TreeNode<T> remove(T item) throws NoSuchElementException;


}
