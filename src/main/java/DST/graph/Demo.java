package DST.graph;

import java.util.HashMap;

/*
https://adrianmejia.com/data-structures-for-beginners-graphs-time-complexity-tutorial/#Adjacency-List-Graph-HashMap-Implementation
Imagine that you need to represent the Facebook network as a graph.
You would have to create a matrix of 2 billion x 2 billion,
where most of it would be empty! Nobody would know everybody else,
just a few thousand at most.

In general, we deal with sparse graphs so that the matrix will waste a lot of
space. That’s why, in most implementations, we would use an adjacency list
rather than the matrix.

 */
public class Demo {
	public static void main(String[] args) {
		// @formatter:off
		/*
		*
		*  /-->2 --> 5 -->9
		* /
		* 1 --> 3 --> 6 --> 10
		* \     \
		*  \     \ --> 7
		*   \
		*    \-->4 -->8
		*/
		// @formatter:on
		Graph directedGraph = new Graph(Direction.DIRECTED);

		Node rootDirected = directedGraph.addEdge(1, 2);
		directedGraph.addEdge(1, 3);
		directedGraph.addEdge(1, 4);
		directedGraph.addEdge(2, 5);
		directedGraph.addEdge(3, 6);
		directedGraph.addEdge(3, 7);
		directedGraph.addEdge(4, 8);
		directedGraph.addEdge(5, 9);
		directedGraph.addEdge(6, 10);
		// breadth first search
		System.out.println("Directed graph breadth first search");
		directedGraph.bfs(rootDirected);
		//@formatter:off
		/*
		*
		*  /<-->2 <--> 5 <-->9
		* /
		* 1 <--> 3 <--> 6 <--> 10
		* \      \
		*  \      \ <--> 7
		*   \
		*    \<-->4 <-->8
		*/
		//@formatter:on

		Graph graph = new Graph(Direction.UNDIRECTED);

		Node rootUnDirected = graph.addEdge(1, 2);
		graph.addEdge(1, 3);
		graph.addEdge(1, 4);
		graph.addEdge(5, 2);
		graph.addEdge(6, 3);
		graph.addEdge(7, 3);
		graph.addEdge(8, 4);
		graph.addEdge(9, 5);
		graph.addEdge(10, 6);
		// breadth first search
		System.out.println("Unidirected graph breadth first search");
		graph.bfs(rootUnDirected);
		System.out.println("Unidirected graph depth first search");
		graph.dfs(rootUnDirected);
		System.out.println(
				"Unidirected graph depth first search using Recursion");
		// first create a empty hash map to pass for searching
		HashMap<Integer, Node> visitedVertex = new HashMap<>();
		graph.dfsUsingRecursion(rootUnDirected, visitedVertex);

	}
}