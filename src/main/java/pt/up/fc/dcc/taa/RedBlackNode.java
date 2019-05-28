package pt.up.fc.dcc.taa;

public class RedBlackNode implements Tree<Integer> {

    private RedBlackNode left = null;
    private RedBlackNode right = null;
    private RedBlackNode parent = null;
    private Integer value;
    private int height;
    private boolean red;

    /**
     * Public constructor Constructor used to build a tree
     *
     * @param value The value of the root
     */
    public RedBlackNode(Integer value) {
        this.value = value;
        red = false;
        height = 1;
    }

    /**
     * Private constructor Used in rotations
     */
    private RedBlackNode(RedBlackNode left, RedBlackNode right, RedBlackNode parent,
            Integer value, boolean red) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.value = value;
        this.red = red;
        if (this.left != null) {
            this.left.setParent(this);
        }
        if (this.right != null) {
            this.right.setParent(this);
        }
    }

    /**
     * Private constructor Used when a node is inserted
     *
     * @param value The value of the inserted node
     * @param parent The parent of the inserted node
     */
    private RedBlackNode(Integer value, RedBlackNode parent) {
        this.parent = parent;
        this.value = value;
        red = true;
        height = 1;
    }
    
        /**
     * @return the left
     */
    public RedBlackNode getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(RedBlackNode left) {
        this.left = left;
    }

    /**
     * @return the right
     */
    public RedBlackNode getRight() {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(RedBlackNode right) {
        this.right = right;
    }

    /**
     * @return the parent
     */
    public RedBlackNode getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the red
     */
    public boolean isRed() {
        return red;
    }

    /**
     * @param red the red to set
     */
    public void setRed(boolean red) {
        this.red = red;
    }

    //Auxiliary tree functions
    public int leftHeight() {
        if (getLeft() == null) {
            return 0;
        } else {
            return getLeft().height();
        }
    }

    public int rightHeight() {
        if (getRight() == null) {
            return 0;
        } else {
            return getRight().height();
        }
    }

    public int height() {
        return 1 + Math.max(leftHeight(), rightHeight());
    }

    public void updateLeft() {
        if (getParent() == null) {
            setRed(false);
        } else {
            if (this == getParent().getLeft()) {
                if (getParent().getRight() == null || !getParent().getRight().isRed()) {
                    RedBlackNode b = new RedBlackNode(getRight(), getParent().getRight(), this, getParent().getValue(), true);
                    setRed(false);
                    setRight(b);
                    if (getParent().getParent() != null) {
                        if (getParent().getParent().getLeft() == getParent()) {
                            getParent().getParent().setLeft(this);
                        } else {
                            getParent().getParent().setRight(this);
                        }
                    }
                    setParent(getParent().getParent());
                } else {
                    setRed(false);
                    getParent().getRight().setRed(false);
                    if (getParent().getParent() == null) {
                        getParent().setRed(false);
                    } else if (getParent().getParent().getLeft() == getParent()) {
                        getParent().getParent().updateLeft();
                    } else {
                        getParent().getParent().updateRight();
                    }
                }
            } else {
                if (getParent().getLeft() == null || !getParent().getLeft().isRed()) {
                    RedBlackNode b = new RedBlackNode(getParent().getLeft(), getLeft().getLeft(), getParent(), getParent().getValue(), true);
                    getParent().setValue(getLeft().getValue());
                    setLeft(getLeft().getRight());
                    getLeft().setParent(this);
                    getParent().setLeft(b);
                } else {
                    setRed(false);
                    getParent().getLeft().setRed(false);
                    if (getParent().getParent() == null) {
                        getParent().setRed(false);
                    } else if (getParent().getParent().getLeft() == getParent()) {
                        getParent().getParent().updateLeft();
                    } else {
                        getParent().getParent().updateRight();
                    }
                }
            }
        }
    }

    public void updateRight() {
        if (getParent() == null) {
            setRed(false);
        } else {
            if (this == getParent().getLeft()) {
                if (getParent().getRight() == null || !getParent().getRight().isRed()) {
                    RedBlackNode b = new RedBlackNode(getRight().getRight(), getParent().getRight(), getParent(), getParent().getValue(), true);
                    getParent().setValue(getRight().getValue());
                    setRight(getRight().getLeft());
                    getRight().setParent(this);
                    getParent().setRight(b);
                } else {
                    setRed(false);
                    getParent().getRight().setRed(false);
                    if (getParent().getParent() == null) {
                        getParent().setRed(false);
                    } else if (getParent().getParent().getLeft() == getParent()) {
                        getParent().getParent().updateLeft();
                    } else {
                        getParent().getParent().updateRight();
                    }
                }
            } else {
                if (getParent().getLeft() == null || !getParent().getLeft().isRed()) {
                    RedBlackNode b = new RedBlackNode(getLeft(), getParent().getLeft(), this, getParent().getValue(), true);
                    setRed(false);
                    setLeft(b);
                    if (getParent().getParent() != null) {
                        if (getParent().getParent().getLeft() == getParent()) {
                            getParent().getParent().setLeft(this);
                        } else {
                            getParent().getParent().setRight(this);
                        }
                    }
                    setParent(getParent().getParent());
                } else {
                    setRed(false);
                    getParent().getLeft().setRed(false);
                    getParent().setRed(true);
                    if (getParent().getParent() == null) {
                        getParent().setRed(false);
                    } else if (getParent().getParent().getLeft() == getParent()) {
                        getParent().getParent().updateLeft();
                    } else {
                        getParent().getParent().updateRight();
                    }
                }
            }
        }
    }

    //Main public functions
    @Override
    public void insert(Integer a) {
        if (a <= getValue()) {
            if (getLeft() == null) {
                setLeft(new RedBlackNode(a, this));
                if (isRed()) {
                    updateLeft();
                }
            } else {
                getLeft().insert(a);
            }
        } else {
            if (getRight() == null) {
                setRight(new RedBlackNode(a, this));
                if (isRed()) {
                    updateRight();
                }
            } else {
                getRight().insert(a);
            }
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

    //After this are the printing functions
    @Override
    public void print() {
        int h = height();
        String[] output = new String[h];
        for (int i = 0; i < h; i++) {
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
        RedBlackNode test = new RedBlackNode(15);
        test.insert(5);
        test.insert(25);
        test.insert(30);
        test.insert(35);
        test.insert(40);
        test.insert(50);
        test.insert(4);
        test.insert(3);
        RedBlackNode[] liste = new RedBlackNode[9];
        liste[0] = test;
        liste[1] = test.getLeft();
        liste[2] = test.getLeft().getLeft();
        liste[3] = test.getLeft().getRight();
        liste[4] = test.getRight();
        liste[5] = test.getRight().getLeft();
        liste[6] = test.getRight().getRight();
        liste[7] = test.getRight().getRight().getLeft();
        liste[8] = test.getRight().getRight().getRight();
        for (RedBlackNode node : liste) {
            System.out.println(node.getLeft() != null);
            System.out.println(node.getRight() != null);
            System.out.println(node.getValue());
            System.out.println(node.isRed() ? "red" : "black");
            System.out.println();
        }
        test.print();
    }

    @Override
    public boolean remove(Integer t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
