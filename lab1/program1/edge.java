package AI.program1;

public class edge {
    node left;
    node right;
    boolean covered;
    public boolean getCovered(){
        return left.getInList()||right.getInList();
    }
    public edge(node left, node right) {
        this.left = left;
        this.right = right;
    }

    public int getValue() {
        if (left.getInList() || right.getInList()) return 0;
        else return Math.min(left.getValue(), right.getValue());
    }

    public void update() {
        covered = left.getInList() && right.getInList();
    }

}
