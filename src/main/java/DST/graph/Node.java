package DST.graph;

import java.util.ArrayList;

/*
 * https://adrianmejia.com/data-structures-for-beginners-graphs-time-complexity-tutorial/#Adjacency-List-Graph-HashMap-Implementation
 */
public class Node {
	Integer value;
	ArrayList<Node> adjacentsList;

	Node(Integer v) {
		this.value = v;
		adjacentsList = new ArrayList<>();
	}

	void addAdjacent(Node n) {
		adjacentsList.add(n);
	}

	void removeAdjacents(Node n) {
		for (Node elem : adjacentsList) {
			if (elem.value == n.value) {
				adjacentsList.remove(elem);
			}
		}

	}

	boolean isAdjacent(Node n) {
		for (Node elem : adjacentsList) {
			if (elem.value == n.value) {
				return true;
			}
		}
		return false;
	}

	ArrayList<Node> getAdjacents() {
		return adjacentsList;
	}
}
