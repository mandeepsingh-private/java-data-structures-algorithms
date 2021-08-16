package commonSenseApproachToDataStructures.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

	String value;
	ArrayList<Graph> adjancentVertices;

	Graph(String v) {
		value = v;
		// connections or relationships of vertex or node
		adjancentVertices = new ArrayList<>();

	}

	void addAdjacentVertex(Graph v) {
		adjancentVertices.add(v);
	}

	public String toString() {
		return "Found: " + value;
	}

	void printByDepthFirst(Graph start) {
		// first create a empty hash map to pass for searching
		HashMap<Graph, Boolean> visitedVertex = new HashMap<>();
		printByDepthFirst(start, visitedVertex);
	}

	// depth first print using RECURSION and
	// a hash table to track visited vertex
	void printByDepthFirst(Graph currentVertex,
			HashMap<Graph, Boolean> visitedVertex) {

		// Mark vertex as visited by adding it to the hash table
		visitedVertex.put(currentVertex, true);
		// Print the vertex's value, so we can make sure our traversal really
		// works
		System.out.println("Visited: " + currentVertex.value);
		// Iterate through the current vertex's adjacent vertices
		for (Graph v : currentVertex.adjancentVertices) {
			if (!visitedVertex.containsKey(v)) {
				// Recursively call this method on the adjacent vertex:
				printByDepthFirst(v, visitedVertex);
			}
		}
	}

	Graph searchBydepthFirst(Graph currentVertex, String searchValue) {
		// first create a empty hash map to pass for searching
		HashMap<Graph, Boolean> visitedVertex = new HashMap<>();
		return searchBydepthFirst(currentVertex, searchValue, visitedVertex);
	}

	// depth first search using RECURSION and
	// a hash table to track visited vertex
	Graph searchBydepthFirst(Graph currentVertex, String searchValue,
			HashMap<Graph, Boolean> visitedVertex) {
		if (currentVertex.value.equals(searchValue)) {
			return currentVertex;
		}
		// Mark vertex as visited by adding it to the hash table
		visitedVertex.put(currentVertex, true);
		// Iterate through the current vertex's adjacent vertices
		for (Graph v : currentVertex.adjancentVertices) {
			// If the adjacent vertex is the vertex we're searching for, just
			// return that
			// vertex
			if (v.value.equals(searchValue)) {
				return v;
			}
			if (!visitedVertex.containsKey(v)) {
				// Recursively call this method on the adjacent vertex:
				printByDepthFirst(v, visitedVertex);
			}
		}
		// If we haven't found the vertex we're searching for:
		return null;
	}
}