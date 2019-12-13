package management;

import management.account_types.Producer;
import objects.BinarySearchTree;
import objects.TreeNode;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class SubscriptionTree {
    private BinarySearchTree<SubscriptionTreeNode> tree;

    public SubscriptionTree(){
        tree = new BinarySearchTree<SubscriptionTreeNode>() {
            @Override
            public TreeNode<SubscriptionTreeNode> remove(SubscriptionTreeNode item) throws NoSuchElementException {
                return null;
            }
        };
    }

    public void add(Producer p){
        tree.add(new SubscriptionTreeNode(p.getName(), p));
    }

    public Producer get(String s){
        SubscriptionTreeNode fakeNode = new SubscriptionTreeNode(s, null);
        TreeNode<SubscriptionTreeNode> closestMatch = tree.closestMatch(fakeNode);
        System.out.println(closestMatch.getData().getName());
        return closestMatch.getData().getProducer();
    }


}
