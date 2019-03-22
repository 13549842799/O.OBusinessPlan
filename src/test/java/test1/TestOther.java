package test1;

import java.util.LinkedList;

public class TestOther {
	
	
	public int[][] createMap(int sideLength){
		
		Node start = new Node(0,0,0);
		sideLength = sideLength >= 10 ? sideLength : 10;
		int[][] map = new int[sideLength][sideLength];
		map[start.x][start.y] = 1;
		
		
		return null;
	}
	
	public void createPath(int[][] map,Node cur){
		 for (int i = 0; i < 4; i++) {
			Node next = new Node(cur.x+dire[i][0],cur.y+dire[i][1],cur.dist);
			if ( next.x < 0 || next.y <0 || map[next.x][next.y] == 2) {
				
			}
		}
	}
	
	int[][] dire = new int[][]{{0,-1},{1,0},{0,1},{-1,0}};
	
	/**
	 * 广度优先算法
	 * 目的：算出迷宫的最短路线
	 * 算法：首先把这个迷宫当成一个二维数组，0表示可以通过，1表示墙
	 * 首先确定方向的坐标
	 * 确定初始的节点
	 * @return
	 */
	public Node requareShortest(){
		
		
		int[][] map = new int[][]{};
		boolean[][] path = new boolean[][]{};
		
		Node start = new Node();
		Node end = new Node();
		
		LinkedList<Node> queue = new LinkedList<>();
		queue.add(start);
		while (!queue.isEmpty()) {
	        Node cur = queue.pop();
	        //取出一个节点，展开这个节点的四个方向
	        Node next = null;
			for (int i = 0; i < 4; i++) {
				int nextX = cur.x+dire[i][0];
				int nextY = cur.y+dire[i][1];
				int nextDist = cur.dist+1;
				next = new Node(nextX, nextY, nextDist,cur);
				if ( nextX == end.x && nextY == end.y ) {
					return next;
				}
				if ( nextX <0 || nextY < 0 || path[nextX][nextY]) {
					continue;
				}
				path[nextX][nextY] = true;
				queue.add(next);
			}
		}
		
		return null;
	}
	
	
	class Node {
		
		int x;
		int y;
		int dist;
		Node pre;
		public Node(){}
		
		public Node(int x,int y,int dist){
			this.x = x;
			this.y = y;
			this.dist = dist;
		}
		public Node(int x,int y,int dist,Node pre){
			this(x, y, dist);
			this.pre = pre;
		}
		
		
	}

}
