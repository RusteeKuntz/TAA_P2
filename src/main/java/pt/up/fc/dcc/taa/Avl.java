package pt.up.fc.dcc.taa;




public class Avl {
	protected Avl left;
	protected Avl right;
	protected Avl parent;
	protected int value;
	protected int height;
	
	/**
	 * Public constructor
	 * Constructor used to build a tree
	 * @param value The value of the root
	 */
	public Avl(int value) {
		left = null;
		right = null;
		parent = null;
		this.value = value;
		height = 1;
	}
	
	/**
	 * Private constructor
	 * Used in rotations
	 */
	private Avl(Avl left, Avl right, Avl parent, int value) {
		this.left = left;
		if (this.left != null)
			this.left.parent = this;
		this.right = right;
		if (this.right != null)
			this.right.parent = this;
		this.parent = parent;
		this.value = value;
		updateHeight();
	}
	
	/**
	 * Private constructor
	 * Used when a node is inserted
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
	
	
	public int leftHeight() {
		if (left == null)
			return 0;
		else
			return left.height;
	}
	
	public int rightHeight() {
		if (right == null)
			return 0;
		else
			return right.height;
	}
	
	
	public void updateHeightRecurs() {
		updateHeight();
		if (this.parent != null) {
			this.parent.updateHeightRecurs();
		}
	}
	
	public void updateHeight() {
		this.height = 1 + Math.max(this.rightHeight(),  this.leftHeight());
	}
	
	
	
	
	//Main public functions
	
	
	public void insert(int a) {
		boolean inserted = false;
		if (a <= value) {
			if (left == null) {
				left = new Avl(a, this);
				inserted = true;
			}
			else
				left.insert(a);
		}
		else {
			if (right == null) {
				right = new Avl(a, this);
				inserted = true;
			}
			else
				right.insert(a);
		}
		if (inserted) {
			updateHeightRecurs();
			balance();
		}
	}
	
	public boolean search(int a) {
		if (a == value)
			return true;
		else if (a < value) {
			if (left == null)
				return false;
			else
				return left.search(a);
		} else {
			if (right == null)
				return false;
			else
				return right.search(a);
		}
	}
	
	public void balance() {
		if (left == null) {
			if (rightHeight() <= 1) {
				if (parent != null)
					parent.balance();
			}
			else {
				if (right.left == null || right.rightHeight() >= right.leftHeight()) {
					rotateRightLeft();
				}
				else {
					rotateRightRight();
				}
			}
		}
		else if (right == null) {
			if (leftHeight() <= 1) {
				if (parent != null)
					parent.balance();
			}
			else {
				if (left.right == null || left.leftHeight() >= left.rightHeight()) {
					rotateLeftRight();
				}
				else {
					rotateLeftLeft();
				}
			}
		}
		else if ((Math.abs(leftHeight() - rightHeight()) <= 1)) {
			if (parent != null)
				parent.balance();
		}
		else {
			if (leftHeight() > rightHeight()) {
				if (left.leftHeight() >= left.rightHeight()) {
					rotateLeftRight();
				}
				else {
					rotateLeftLeft();
				}
			}
			else {
				if (right.rightHeight() >= right.leftHeight()) {
					rotateRightLeft();
				}
				else {
					rotateRightRight();
				}
			}
		}
	}
	
	
	//Rotations
	
	
	public void rotateLeftRight() {
		Avl x = new Avl(left.right, right, this, value);
		this.value = left.value;
		this.left = left.left;
		this.left.parent = this;
		this.right = x;
		updateHeightRecurs();
	}
	
	public void rotateRightLeft() {
		Avl x = new Avl(left, right.left, this, value);
		this.value = right.value;
		this.right = right.right;
		this.right.parent = this;
		this.left = x;
		updateHeightRecurs();
	}

	public void rotateLeftLeft() {
		Avl x = new Avl(left.right.right, right, this, value);
		Avl y = new Avl(left.left, left.right.left, this, left.value);
		this.value = left.right.value;
		this.left = y;
		this.right = x;
		updateHeightRecurs();
	}
	public void rotateRightRight() {
		Avl x = new Avl(left, right.left.left, this, value);
		Avl y = new Avl(right.left.right, right.right, this, right.value);
		this.value = right.left.value;
		this.left = x;
		this.right = y;
		updateHeightRecurs();
	}

	
	
	//After this are the printing functions
	
	public void print() {
		String[] output = new String[height];
		for (int i = 0 ; i < height ; i++) {
			output[i] = new String();
		}
		fill(output, 0);
		for (String line : output) {
			System.out.println(line);
		}
	}
	
	public void fill(String[] output, int stage) {
		if (left != null)
			left.fill(output, stage + 1);
		else if (stage + 1 < output.length)
			output[stage + 1] += repeatNTimes("   ", (int) Math.pow(2, output.length - stage + 1));
		if (right != null)
			right.fill(output, stage + 1);
		else if (stage + 1 < output.length)
			output[stage + 1] += repeatNTimes("   ", (int) Math.pow(2, output.length - stage + 1));
		output[stage] += repeatNTimes("   ", (int) Math.pow(2, output.length - stage))
											+ String.valueOf(value) +
											repeatNTimes("   ", (int) Math.pow(2, output.length - stage));
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
}
