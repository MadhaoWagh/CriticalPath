import java.util.ArrayList;
import java.util.LinkedList;



  public class Vertex   {
	  static final int INFINITY = Integer.MAX_VALUE;
	public int name; // name of the vertex
	public boolean seen; // flag to check if the vertex has already been visited
	public ArrayList<Vertex> parent;
	public int duration; // duration of the task
	public int ec;
	public int lc;
	public int slack;
	public int indexPQ;  // index of node in priotity queue
	public LinkedList<Edge> Adj; // adjacency list
	public int indegree;// for topological ordering algo 1
	public boolean active;// for topological ordering algo 2

	/**
	 * Constructor for the vertex
	 * 
	 * @param n
	 *            : int - name of the vertex
	 */
	Vertex(int n) {
	    name = n;
	    seen = false;
	    parent = new ArrayList<>();
	    ec = 0;
	    lc=INFINITY;
	    slack=0;
	    Adj = new LinkedList<Edge>();
	    indegree=0;
	    active=false;
	}

	public void putIndex(int index) {
	    indexPQ = index;
	}

	public int getIndex() {
	    return indexPQ;
	}


	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
	    return Integer.toString(name);
	}
    }