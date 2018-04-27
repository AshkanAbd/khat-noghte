package ir.ashkanabd.client;

public class Node {

    private Integer X, Y, mark;
    private boolean isFree, isBlock, isSpot, isMarked;

    public Node(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public Node(Node node) {
        this.X = node.X;
        this.Y = node.Y;
    }

    Node(String node) {
        String nodeInfo[] = node.split("_");
        this.X = Integer.parseInt(nodeInfo[0]);
        this.Y = Integer.parseInt(nodeInfo[1]);
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    void reset() {
        this.X = 0;
        this.Y = 0;
    }

    public boolean isFree() {
        return isFree;
    }

    void setFree() {
        isFree = true;
    }

    public boolean isBlock() {
        return isBlock;
    }

    void setBlock() {
        isBlock = true;
    }

    public boolean isSpot() {
        return isSpot;
    }

    void setSpot() {
        isSpot = true;
    }

    public boolean isMarked() {
        return isMarked;
    }

    void setMarked() {
        isMarked = true;
    }

    public Integer getMark() {
        return mark;
    }

    void setMark(Integer mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "<" + X + ", " + Y + ">";
    }

    @Override
    public int hashCode() {
        return X.hashCode() * Y.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node node = (Node) o;
            if (node.Y == this.Y && node.X == this.X)
                return true;
        }
        return false;
    }
}
