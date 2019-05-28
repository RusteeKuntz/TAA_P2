package pt.up.fc.dcc.taa;

public class Avl implements Tree<Integer> {

    protected Avl left;
    protected Avl right;
    protected Avl parent;
    protected Integer value;

    public Avl getLeft() {
        return left;
    }

    public void setLeft(Avl left) {
        this.left = left;
    }

    public Avl getRight() {
        return right;
    }

    public void setRight(Avl right) {
        this.right = right;
    }

    public Avl getParent() {
        return parent;
    }

    public void setParent(Avl parent) {
        this.parent = parent;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    protected int height;

    /**
     * Public constructor Constructor used to build a tree
     *
     * @param value The value of the root
     */
    public Avl(Integer value) {
        left = null;
        right = null;
        parent = null;
        this.value = value;
        height = 1;
    }

    /**
     * Private constructor Used in rotations
     */
    private Avl(Avl left, Avl right, Avl parent, Integer value) {
        this.left = left;
        if (this.left != null) {
            this.left.setParent(this);
        }
        this.right = right;
        if (this.right != null) {
            this.right.setParent(this);
        }
        this.parent = parent;
        this.value = value;
        updateHeight();
    }

    /**
     * Private constructor Used when a node is inserted
     *
     * @param value The value of the inserted node
     * @param parent The parent of the inserted node
     */
    private Avl(int value, Avl parent) {
        left = null;
        right = null;
        this.parent = parent;
        this.value = value;
        height = 1;
    }

    //Auxiliary tree functions
    private int leftHeight() {
        return left == null ? 0 : left.getHeight();
    }

    private int rightHeight() {
        return right == null ? 0 : right.getHeight();
    }

    public void updateHeightRecurs() {
        updateHeight();
        if (getParent() != null) {
            getParent().updateHeightRecurs();
        }
    }

    private void updateHeight() {
        setHeight(1 + Math.max(this.rightHeight(), this.leftHeight()));
    }

    //Main public functions
    @Override
    public void insert(Integer a) {
        boolean inserted = false;
        if (a.compareTo(value) < 1) {
            if (left == null) {
                left = new Avl(a, this);
                inserted = true;
            } else {
                left.insert(a);
            }
        } else {
            if (right == null) {
                right = new Avl(a, this);
                inserted = true;
            } else {
                right.insert(a);
            }
        }
        if (inserted) {
            updateHeightRecurs();
            balance();
        }
    }

    @Override
    public Integer search(Integer a) {
        switch (a.compareTo(value)) {
            case 0:
                return value;
            case -1:
                return left == null ? null : left.search(a);
            case 1:
                return right == null ? null : left.search(a);
            default:
                return null;
        }
    }

    public void balance() {
        if (left == null) {
            if (rightHeight() <= 1) {
                if (parent != null) {
                    parent.balance();
                }
            } else {
                if (right.getLeft() == null || right.rightHeight() >= right.leftHeight()) {
                    rotateRightLeft();
                } else {
                    rotateRightRight();
                }
            }
        } else if (right == null) {
            if (leftHeight() <= 1) {
                if (parent != null) {
                    parent.balance();
                }
            } else {
                if (left.getRight() == null || left.leftHeight() >= left.rightHeight()) {
                    rotateLeftRight();
                } else {
                    rotateLeftLeft();
                }
            }
        } else if ((Math.abs(leftHeight() - rightHeight()) <= 1)) {
            if (parent != null) {
                parent.balance();
            }
        } else {
            if (leftHeight() > rightHeight()) {
                if (left.leftHeight() >= left.rightHeight()) {
                    rotateLeftRight();
                } else {
                    rotateLeftLeft();
                }
            } else {
                if (right.rightHeight() >= right.leftHeight()) {
                    rotateRightLeft();
                } else {
                    rotateRightRight();
                }
            }
        }
    }

    //Rotations
    public void rotateLeftRight() {
        Avl x = new Avl(left.getRight(), right, this, value);
        this.value = left.getValue();
        this.left = left.getLeft();
        this.left.setParent(this);
        this.right = x;
        updateHeightRecurs();
    }

    public void rotateRightLeft() {
        Avl x = new Avl(left, right.getLeft(), this, value);
        this.value = right.getValue();
        this.right = right.getRight();
        this.right.setParent(this);
        this.left = x;
        updateHeightRecurs();
    }

    public void rotateLeftLeft() {
        Avl x = new Avl(left.getRight().getRight(), right, this, value);
        Avl y = new Avl(left.getLeft(), left.getRight().getLeft(), this, left.getValue());
        this.value = left.getRight().getValue();
        this.left = y;
        this.right = x;
        updateHeightRecurs();
    }

    public void rotateRightRight() {
        Avl x = new Avl(left, right.getLeft().getLeft(), this, value);
        Avl y = new Avl(right.getLeft().getRight(), right.getRight(), this, right.getValue());
        this.value = right.getLeft().getValue();
        this.left = x;
        this.right = y;
        updateHeightRecurs();
    }

    //After this are the printing functions
    @Override
    public void print() {
        String[] output = new String[height];
        for (int i = 0; i < height; i++) {
            output[i] = new String();
        }
        fill(output, 0);
        for (String line : output) {
            System.out.println(line);
        }
    }

    public void fill(String[] output, int stage) {
        if (left != null) {
            left.fill(output, stage + 1);
        } else if (stage + 1 < output.length) {
            output[stage + 1] += repeatNTimes("   ", (int) Math.pow(2, output.length - stage + 1));
        }
        if (right != null) {
            right.fill(output, stage + 1);
        } else if (stage + 1 < output.length) {
            output[stage + 1] += repeatNTimes("   ", (int) Math.pow(2, output.length - stage + 1));
        }
        output[stage] += repeatNTimes("   ", (int) Math.pow(2, output.length - stage))
                + String.valueOf(value)
                + repeatNTimes("   ", (int) Math.pow(2, output.length - stage));
    }

    public static String repeatNTimes(String s, int n) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append(s);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        Avl test = new Avl(15);
        test.insert(5);
        test.insert(25);
        test.insert(30);
        test.insert(35);
        test.insert(40);
        test.insert(50);
        test.insert(4);
        test.insert(3);
        test.print();
    }

    @Override
    public boolean remove(Integer t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
