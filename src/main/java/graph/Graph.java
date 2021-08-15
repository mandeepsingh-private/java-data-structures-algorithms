package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Graph {
	// Tracks whether graph uni-directed or directed
	Direction edgeDirection;
	// tracks the nodes in graph
	HashMap<Integer, Node> nodes;

	Graph(Direction e) {
		// The HashMap key is the node’s value,
		// and the HashMap value is Node
		// stores all nodes and edges of Graph
		nodes = new HashMap<>();
		this.edgeDirection = e;
	}

	Node addVertex(Integer n) {
		// check if node exits in graph HashMap nodes
		if (nodes.containsKey(n)) {
			return nodes.get(n);
		}
		// if not found create new
		Node vertex = new Node(n);
		nodes.put(n, vertex);
		return vertex;
	}

	Node addEdge(Integer s, Integer d) {
		// call add vertex method, which also adds node to graph if it does not
		// exits
		Node source = this.addVertex(s);
		Node destination = this.addVertex(d);
		source.addAdjacent(destination);
		if (this.edgeDirection.equals(Direction.UNDIRECTED)) {
			destination.addAdjacent(source);
		}
		return source;
	}

	Integer removeVertex(Integer n) {
		Node toRemove = null;
		// go across all nodes
		for (Node ele : nodes.values()) {
			if (ele.value.equals(n)) {
				ArrayList<Node> adjacents = ele.getAdjacents();
				for (Node adjNode : adjacents) {
					if (adjNode.value.equals(n)) {
						toRemove = adjNode;
						break; // get out of this for loop
					}
				}
				// remove the node from adjacents
				if (!toRemove.equals(null))
					adjacents.remove(toRemove);
				break;
			}
		}
		if (!toRemove.equals(null)) {
			// remove the entry from HashMap
			nodes.remove(toRemove.value);
			return n;
		}
		return -1; // node as not found
	}

	void removeEdge(Node source, Node destination) {
		Node s = this.nodes.get(source.value);
		Node d = this.nodes.get(destination.value);
		if (!s.equals(null) && !d.equals(null)) {
			s.removeAdjacents(d);
			if (this.edgeDirection.equals(Direction.UNDIRECTED)) {
				d.removeAdjacents(s);
			}
		}

	}

	// Breadth first search using queue
	void bfs(Node first) {
		HashMap<Integer, Node> visited = new HashMap<>();
		Queue<Node> queue = new LinkedList<>();
		queue.add(first);
		while (!queue.isEmpty()) {
			Node node = queue.remove();
			// System.out.println("Removed form queue : " + node.value);
			// if node i not null and is not visited before
			if (!node.equals(null) & !visited.containsKey(node.value)) {
				visited.put(node.value, node);
				System.out.println("Visited : " + node.value);
				for (Node n : node.getAdjacents()) {
					// System.out.println("Adding to Queue : " + n.value);
					queue.add(n);
				}
			}
		}
	}

	// Depth first search using stack
	void dfs(Node first) {
		HashMap<Integer, Node> visited = new HashMap<>();
		Stack<Node> stack = new Stack<>();
		stack.push(first);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			// System.out.println("Removed form queue : " + node.value);
			// if node i not null and is not visited before
			if (!node.equals(null) & !visited.containsKey(node.value)) {
				visited.put(node.value, node);
				System.out.println("Visited : " + node.value);
				for (Node n : node.getAdjacents()) {
					// System.out.println("Adding to Queue : " + n.value);
					stack.push(n);
				}
			}
		}
	}
}
