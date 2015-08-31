package com.jiangcoder.search;

public class manriver {
	private int[][] maxtri = new int[16][16];// 邻接矩阵
	private boolean[] order = new boolean[16];// 状态是否被访问过的数组
	private stack stack = new stack();

	/**
	 * judge the adjacency matrix elements true or false(1 or 0)
	 *
	 */

	private boolean isConnected(int x, int y) {// 判断x与y是否是邻接点
		String X = getformatString(x);
		String Y = getformatString(y);

		if (X.charAt(0) == Y.charAt(0)) // 人必须渡河
			return false;
		else {
			if (X.charAt(1) != Y.charAt(1) && X.charAt(2) != Y.charAt(2) && X.charAt(3) != Y.charAt(3)) {
				return false;// 人 羊 草 狼不能一起过
			} else if (X.charAt(1) != Y.charAt(1) && X.charAt(2) != Y.charAt(2)) {
				return false;// 人 羊 草 不能一起过
			} else if (X.charAt(1) != Y.charAt(1) && X.charAt(3) != Y.charAt(3)) {
				return false;// 人 羊 狼不能一起过
			} else if (X.charAt(2) != Y.charAt(2) && X.charAt(3) != Y.charAt(3)) {
				return false;// 人 草 狼不能一起过
			} else if (((X.charAt(0) != X.charAt(1)) && (X.charAt(1) != Y.charAt(1)))
					|| ((X.charAt(0) != X.charAt(2)) && (X.charAt(2) != Y.charAt(2)))
					|| ((X.charAt(0) != X.charAt(3)) && (X.charAt(3) != Y.charAt(3)))) {
				return false;// 羊、草、 狼分别在人的对岸,羊、草、狼不可能直接回到人这一边
			}
			return true;
		}
	}

	/**
	 * 
	 * produce the adjacency matrix
	 * 
	 */
	public void makeMaxtri() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (isConnected(i, j)) {
					maxtri[i][j] = 1;
				} else
					maxtri[i][j] = 0;
			}
		}
	}

	/**
	 * 
	 * 得到状态的字符串表示 0000 四位分别代表人 羊 草 狼
	 * 
	 */
	public String getformatString(int x) {

		String X = Integer.toBinaryString(x);// 十进制转为二进制字符串
		if (X.length() < 4 && X.length() >= 3) {
			X = "0" + X;
		} else if (X.length() < 3 && X.length() >= 2) {
			X = "00" + X;
		} else if (X.length() < 2 && X.length() >= 1) {
			X = "000" + X;
		}
		return X;
	}

	/**
	 * 
	 * dfs arithmetic dfs算法
	 *
	 */
	public void dfs() {
		stack.push(0);
		order[0] = true;
		while (!stack.isEmpty()) {
			if (stack.peek() == 15) {// 状态为1111，全部过河
				break;
			}
			int v = getUnvisitedVetex(stack.peek());
			if (v == -1) {
				try {
					stack.pop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

				stack.push(v);
				order[v] = true;
			}
		}
	}

	/**
	 * 
	 * 得到与输入节点相连的一个结点
	 * 
	 */
	public int getUnvisitedVetex(int x) {
		for (int j = 0; j < 16; j++) {

			if (maxtri[x][j] == 1 && !order[j]) {

				String X = getformatString(j);
				// 合法性判断
				if ((X.charAt(0) != X.charAt(1) && X.charAt(1) == X.charAt(3)) || // 羊狼在一起,如0101
						(X.charAt(0) != X.charAt(1) && X.charAt(1) == X.charAt(2))) {// 羊草在一起,如1000
					continue;
				} else {
					return j;
				}
			}
		}
		return -1;
	}

	/**
	 * 
	 * make order
	 * 
	 */
	public void printOrder() throws Exception {
		for (int i = 0; i < stack.length() - 1; i++) {
			int x = stack.peekByIndex(i);
			int y = stack.peekByIndex(i + 1);
			String X = getformatString(x);
			String Y = getformatString(y);
			String type = "";
			if (X.charAt(0) == '0') {
				type = "过河";
			}
			if (X.charAt(0) == '1') {
				type = "回来";
			}
			if (X.charAt(1) != Y.charAt(1)) {
				System.out.println("人带羊" + type);
			} else if (X.charAt(2) != Y.charAt(2)) {
				System.out.println("人带草" + type);
			} else if (X.charAt(3) != Y.charAt(3)) {
				System.out.println("人带狼" + type);
			} else {
				System.out.println("人自己" + type);
			}
		}
	}

	public static void main(String[] args) {
		manriver manriver = new manriver();
		manriver.makeMaxtri();
		manriver.dfs();
		try {
			manriver.printOrder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/**
 * 
 * 堆栈简单实现类
 * 
 */
class stack {
	private int[] num;
	private int value;

	public stack() {
		num = new int[20];
		value = -1;
	}

	public int peek() {

		return num[value];
	}

	public int pop() throws Exception {
		if (value >= 0) {
			return num[value--];
		} else
			throw new Exception("无元素！");

	}

	public void push(int xx) {
		if (value == -1) {
			value = 0;
			num[value] = xx;
		} else {
			num[++value] = xx;
		}
	}

	public int getSize() {
		return value;
	}

	public boolean isEmpty() {
		return (value < 0);
	}

	public int length() {
		return value + 1;
	}

	public int peekByIndex(int i) throws Exception {
		if (i < value + 1 && i >= 0) {
			return num[i];
		} else
			throw new Exception("未找到合适元素！");
	}
}
