package commonSenseApproachToDataStructures.graph;

/*// @formatter:off
 * HIGH LEVEL STRUCTURE
 *          ---->cynthia---->jumanji --->tom
 *  	  / 	|
 * alice / 		|
 * 		 \		|
 *  	  \		|
 *  		----->bob --> Sam --> mike
 *
 * IMPLEMENTATION
 *  Node or Vertex as a class
 *  Each Node stores a string
 *  Adjacent Vertex or Nodes or Edges stored using array
 */
// @formatter:on
public class Demo {
	public static void main(String[] args) {
// add graph nodes
		Graph root = new Graph("alice");
		Graph bob = new Graph("bob");
		Graph cynthia = new Graph("cynthia");
		Graph jumanji = new Graph("jumanji");
		Graph tom = new Graph("tom");
		Graph sam = new Graph("sam");
		Graph mike = new Graph("mike");
// add relationships or connections or edges
		root.addAdjacentVertex(bob);
		bob.addAdjacentVertex(sam);
		sam.addAdjacentVertex(mike);
		root.addAdjacentVertex(cynthia);
		cynthia.addAdjacentVertex(jumanji);
		cynthia.addAdjacentVertex(tom);
		bob.addAdjacentVertex(cynthia);
		cynthia.addAdjacentVertex(bob);

// print by depth first search
		root.printByDepthFirst(root);
		Graph lookFor = root.searchBydepthFirst(root, "bob");
		System.out.println(lookFor.toString());
	}
}
