package pt.up.fc.dcc.taa;

public class Redblack {

    protected Redblack left;
    protected Redblack right;
    protected Redblack parent;
    protected int value;
    protected int height;
    protected boolean red;

    /**
     * Public constructor Constructor used to build a tree
     *
     * @param value The value of the root
     */
    public Redblack(int value) {
        left = null;
        right = null;
        parent = null;
        this.value = value;
        red = false;
        height = 1;
    }

    /**
     * Private constructor Used in rotations
     */
    private Redblack(Redblack left, Redblack right, Redblack parent, int value, boolean red) {
        this.left = left;
        this.parent = parent;
        this.value = value;
        this.red = red;
        if (this.left != null) {
            this.left.parent = this;
        }
        if (this.right != null) {
            this.right.parent = this;
        }
    }

    /**
     * Private constructor Used when a node is inserted
     *
     * @param value The value of the inserted node
     * @param parent The parent of the inserted node
     */
    private Redblack(int value, Redblack parent) {
        left = null;
        right = null;
        this.parent = parent;
        this.value = value;
        red = true;
        height = 1;
    }

    //Auxiliary tree functions
    public int leftHeight() {
        if (left == null) {
            return 0;
        } else {
            return left.height();
        }
    }

    public int rightHeight() {
        if (right == null) {
            return 0;
        } else {
            return right.height();
        }
    }

    public int height() {
        return 1 + Math.max(leftHeight(), rightHeight());
    }

    public void updateLeft() {
        if (parent == null) {
            red = false;
        } else {
            if (this == parent.left) {
                if (parent.right == null || !parent.right.red) {
                    Redblack b = new Redblack(right, parent.right, this, parent.value, true);
                    red = false;
                    right = b;
                    if (parent.parent != null) {
                        if (parent.parent.left == parent) {
                            parent.parent.left = this;
                        } else {
                            parent.parent.right = this;
                        }
                    }
                    parent = parent.parent;
                } else {
                    red = false;
                    parent.right.red = false;
                    if (parent.parent == null) {
                        parent.red = false;
                    } else if (parent.parent.left == parent) {
                        parent.parent.updateLeft();
                    } else {
                        parent.parent.updateRight();
                    }
                }
            } else {
                if (parent.left == null || !parent.left.red) {
                    Redblack b = new Redblack(parent.left, left.left, parent, parent.value, true);
                    parent.value = left.value;
                    left = left.right;
                    left.parent = this;
                    parent.left = b;
                } else {
                    red = false;
                    parent.left.red = false;
                    if (parent.parent == null) {
                        parent.red = false;
                    } else if (parent.parent.left == parent) {
                        parent.parent.updateLeft();
                    } else {
                        parent.parent.updateRight();
                    }
                }
            }
        }
    }

    public void updateRight() {
        if (parent == null) {
            red = false;
        } else {
            if (this == parent.left) {
                if (parent.right == null || !parent.right.red) {
                    Redblack b = new Redblack(right.right, parent.right, parent, parent.value, true);
                    parent.value = right.value;
                    right = right.left;
                    right.parent = this;
                    parent.right = b;
                } else {
                    red = false;
                    parent.right.red = false;
                    if (parent.parent == null) {
                        parent.red = false;
                    } else if (parent.parent.left == parent) {
                        parent.parent.updateLeft();
                    } else {
                        parent.parent.updateRight();
                    }
                }
            } else {
                if (parent.left == null || !parent.left.red) {
                    Redblack b = new Redblack(left, parent.left, this, parent.value, true);
                    red = false;
                    left = b;
                    if (parent.parent != null) {
                        if (parent.parent.left == parent) {
                            parent.parent.left = this;
                        } else {
                            parent.parent.right = this;
                        }
                    }
                    parent = parent.parent;
                } else {
                    red = false;
                    parent.left.red = false;
                    parent.red = true;
                    if (parent.parent == null) {
                        parent.red = false;
                    } else if (parent.parent.left == parent) {
                        parent.parent.updateLeft();
                    } else {
                        parent.parent.updateRight();
                    }
                }
            }
        }
    }

    //Main public functions
    public void insert(int a) {
        if (a <= value) {
            if (left == null) {
                left = new Redblack(a, this);
                if (red) {
                    updateLeft();
                }
            } else {
                left.insert(a);
            }
        } else {
            if (right == null) {
                right = new Redblack(a, this);
                if (red) {
                    updateRight();
                }
            } else {
                right.insert(a);
            }
        }
    }

    public boolean search(int a) {
        if (a == value) {
            return true;
        } else if (a < value) {
            if (left == null) {
                return false;
            } else {
                return left.search(a);
            }
        } else {
            if (right == null) {
                return false;
            } else {
                return right.search(a);
            }
        }
    }

    //After this are the printing functions
    public void print() {
        int height = height();
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
        Redblack test = new Redblack(15);
        test.insert(5);
        test.insert(25);
        test.insert(30);
        test.insert(35);
        test.insert(40);
        test.insert(50);
        test.insert(4);
        test.insert(3);
        Redblack[] liste = new Redblack[9];
        liste[0] = test;
        liste[1] = test.left;
        liste[2] = test.left.left;
        liste[3] = test.left.right;
        liste[4] = test.right;
        liste[5] = test.right.left;
        liste[6] = test.right.right;
        liste[7] = test.right.right.left;
        liste[8] = test.right.right.right;
        for (Redblack node : liste) {
            System.out.println(node.left != null);
            System.out.println(node.right != null);
            System.out.println(node.value);
            System.out.println(node.red ? "red" : "black");
            System.out.println();
        }
        test.print();
    }
}
