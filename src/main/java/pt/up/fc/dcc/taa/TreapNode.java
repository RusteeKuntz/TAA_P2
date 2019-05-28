/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.up.fc.dcc.taa;

import java.util.Objects;

/**
 *
 * @author Ayy lmao
 */
public class TreapNode implements Tree<KPPair> {

    private TreapNode left = null;
    private TreapNode right = null;
    private TreapNode parent = null;
    private KPPair kp;

    public TreapNode(KPPair kp) {
        this.kp = kp;
    }

    public TreapNode(TreapNode left, TreapNode right, TreapNode parent, KPPair kp) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.kp = kp;
    }

    public TreapNode(KPPair kp, TreapNode parent) {
        this.kp = kp;
        this.parent = parent;
    }

    public TreapNode getLeft() {
        return left;
    }

    public void setLeft(TreapNode left) {
        this.left = left;
    }

    public TreapNode getRight() {
        return right;
    }

    public void setRight(TreapNode right) {
        this.right = right;
    }

    public TreapNode getParent() {
        return parent;
    }

    public void setParent(TreapNode parent) {
        this.parent = parent;
    }

    public KPPair getKp() {
        return kp;
    }

    public void setKp(KPPair kp) {
        this.kp = kp;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.getLeft());
        hash = 67 * hash + Objects.hashCode(this.getRight());
        hash = 67 * hash + Objects.hashCode(this.getParent());
        hash = 67 * hash + Objects.hashCode(this.getKp());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TreapNode other = (TreapNode) obj;
        if (!Objects.equals(this.left, other.left)) {
            return false;
        }
        if (!Objects.equals(this.right, other.right)) {
            return false;
        }
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        if (!Objects.equals(this.kp, other.kp)) {
            return false;
        }
        return true;
    }

    @Override
    public void insert(KPPair kpp) {
        boolean inserted = false;
        if (kpp.compareTo(getKp()) < 1) {
            if (getLeft() == null) {
                setLeft(new TreapNode(kpp, this));
                inserted = true;
            } else {
                getLeft().insert(kpp);
            }
        } else {
            if (getRight() == null) {
                setRight(new TreapNode(kpp, this));
                inserted = true;
            } else {
                getRight().insert(kpp);
            }
        }
        if (inserted) {
            balance();
        }
    }

    private void balance() {
        while (getParent() != null && getKp().comparePriority(getParent().getKp()) < 0) {
            if (this.equals(getParent().getLeft())) {
                rotateRight();
            } else {
                rotateLeft();
            }
        }
    }

    private void rotateRight() {
        TreapNode y = getParent();
        if (y.getParent() != null && y.equals(y.getParent().getLeft())) {
            y.getParent().setLeft(this);
        } else {
            y.getParent().setRight(this);
        }
        setParent(y.getParent());
        y.setLeft(getRight());
        y.getLeft().setParent(y);
        setRight(y);
    }

    private void rotateLeft() {
        TreapNode y = getParent();
        if (y.getParent() != null && y.equals(y.getParent().getLeft())) {
            y.getParent().setLeft(this);
        } else {
            y.getParent().setRight(this);
        }
        setParent(y.getParent());
        y.setRight(getLeft());
        y.getRight().setParent(y);
        setLeft(y);
    }
    
    @Override
    public KPPair search(KPPair kpp) {
        switch (kpp.compareTo(getKp())) {
            case 0:
                return getKp();
            case -1:
                return getLeft() == null ? null : getLeft().search(kpp);
            case 1:
                return getRight() == null ? null : getLeft().search(kpp);
            default:
                return null;
        }
    }

    
    @Override
    public void print() {
        System.out.print("Not implemented for this tree");
    }

    /*
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
                + "<" + String.valueOf(kp.getKey()) + "," + String.valueOf(kp.getPriority()) + ">"
                + repeatNTimes("   ", (int) Math.pow(2, output.length - stage));
    }

    public static String repeatNTimes(String s, int n) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append(s);
        }
        return builder.toString();
    }
    * 
    */

    @Override
    public boolean remove(KPPair t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
