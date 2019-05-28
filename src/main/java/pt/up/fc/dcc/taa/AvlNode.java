package pt.up.fc.dcc.taa;

public class AvlNode implements Tree<Integer> {

    private AvlNode left = null;
    private AvlNode right = null;
    private AvlNode parent = null;
    private Integer value;
    private int height = 1;

    public AvlNode getLeft() {
        return left;
    }

    public void setLeft(AvlNode left) {
        this.left = left;
    }

    public AvlNode getRight() {
        return right;
    }

    public void setRight(AvlNode right) {
        this.right = right;
    }

    public AvlNode getParent() {
        return parent;
    }

    public void setParent(AvlNode parent) {
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

    /**
     * Public constructor Constructor used to build a tree
     *
     * @param value The value of the root
     */
    public AvlNode(Integer value) {
        this.value = value;
    }

    /**
     * Private constructor Used in rotations
     */
    private AvlNode(AvlNode left, AvlNode right, AvlNode parent, Integer value) {
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
    private AvlNode(Integer value, AvlNode parent) {
        this.parent = parent;
        this.value = value;
        height = 1;
    }

    //Auxiliary tree functions
    private int leftHeight() {
        return getLeft() == null ? 0 : getLeft().getHeight();
    }

    private int rightHeight() {
        return getRight() == null ? 0 : getRight().getHeight();
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
        if (a.compareTo(getValue()) < 1) {
            if (getLeft() == null) {
                setLeft(new AvlNode(a, this));
                inserted = true;
            } else {
                getLeft().insert(a);
            }
        } else {
            if (getRight() == null) {
                setRight(new AvlNode(a, this));
                inserted = true;
            } else {
                getRight().insert(a);
            }
        }
        if (inserted) {
            updateHeightRecurs();
            balance();
        }
    }

    @Override
    public Integer search(Integer a) {
        switch (a.compareTo(getValue())) {
            case 0:
                return getValue();
            case -1:
                return getLeft() == null ? null : getLeft().search(a);
            case 1:
                return getRight() == null ? null : getLeft().search(a);
            default:
                return null;
        }
    }

    public void balance() {
        if (getLeft() == null) {
            if (rightHeight() <= 1) {
                if (getParent() != null) {
                    getParent().balance();
                }
            } else {
                if (getRight().getLeft() == null || getRight().rightHeight() >= getRight().leftHeight()) {
                    rotateRightLeft();
                } else {
                    rotateRightRight();
                }
            }
        } else if (getRight() == null) {
            if (leftHeight() <= 1) {
                if (getParent() != null) {
                    getParent().balance();
                }
            } else {
                if (getLeft().getRight() == null || getLeft().leftHeight() >= getLeft().rightHeight()) {
                    rotateLeftRight();
                } else {
                    rotateLeftLeft();
                }
            }
        } else if ((Math.abs(leftHeight() - rightHeight()) <= 1)) {
            if (getParent() != null) {
                getParent().balance();
            }
        } else {
            if (leftHeight() > rightHeight()) {
                if (getLeft().leftHeight() >= getLeft().rightHeight()) {
                    rotateLeftRight();
                } else {
                    rotateLeftLeft();
                }
            } else {
                if (getRight().rightHeight() >= getRight().leftHeight()) {
                    rotateRightLeft();
                } else {
                    rotateRightRight();
                }
            }
        }
    }

    //Rotations
    public void rotateLeftRight() {
        AvlNode x = new AvlNode(getLeft().getRight(), getRight(), this, getValue());
        this.setValue(getLeft().getValue());
        this.setLeft(getLeft().getLeft());
        this.getLeft().setParent(this);
        this.setRight(x);
        updateHeightRecurs();
    }

    public void rotateRightLeft() {
        AvlNode x = new AvlNode(getLeft(), getRight().getLeft(), this, getValue());
        this.setValue(getRight().getValue());
        this.setRight(getRight().getRight());
        this.getRight().setParent(this);
        this.setLeft(x);
        updateHeightRecurs();
    }

    public void rotateLeftLeft() {
        AvlNode x = new AvlNode(getLeft().getRight().getRight(), getRight(), this, getValue());
        AvlNode y = new AvlNode(getLeft().getLeft(), getLeft().getRight().getLeft(), this, getLeft().getValue());
        this.setValue(getLeft().getRight().getValue());
        this.setLeft(y);
        this.setRight(x);
        updateHeightRecurs();
    }

    public void rotateRightRight() {
        AvlNode x = new AvlNode(getLeft(), getRight().getLeft().getLeft(), this, getValue());
        AvlNode y = new AvlNode(getRight().getLeft().getRight(), getRight().getRight(), this, getRight().getValue());
        this.setValue(getRight().getLeft().getValue());
        this.setLeft(x);
        this.setRight(y);
        updateHeightRecurs();
    }

    //After this are the printing functions
    @Override
    public void print() {
        String[] output = new String[getHeight()];
        for (int i = 0; i < getHeight(); i++) {
            output[i] = new String();
        }
        fill(output, 0);
        for (String line : output) {
            System.out.println(line);
        }
    }

    public void fill(String[] output, int stage) {
        if (getLeft() != null) {
            getLeft().fill(output, stage + 1);
        } else if (stage + 1 < output.length) {
            output[stage + 1] += repeatNTimes("   ", (int) Math.pow(2, output.length - stage + 1));
        }
        if (getRight() != null) {
            getRight().fill(output, stage + 1);
        } else if (stage + 1 < output.length) {
            output[stage + 1] += repeatNTimes("   ", (int) Math.pow(2, output.length - stage + 1));
        }
        output[stage] += repeatNTimes("   ", (int) Math.pow(2, output.length - stage))
                + String.valueOf(getValue())
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
        AvlNode test = new AvlNode(15);
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
