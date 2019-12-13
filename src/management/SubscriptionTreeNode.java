package management;

import management.account_types.Producer;

public class SubscriptionTreeNode implements Comparable<SubscriptionTreeNode> {
    private String name;
    private Producer producer;

    public SubscriptionTreeNode(String name, Producer producer){
        this.name = name;
        this.producer = producer;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public int compareTo(SubscriptionTreeNode o) {
        return this.name.compareTo(o.name);
    }
}
