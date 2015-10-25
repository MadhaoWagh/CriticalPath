
import java.io.*;
import java.util.*;

/**
 * 
 * @author rbk
 * Graph class for Project 2
 */

/**
 * Class to represent a graph
 * 
 *
 */
public class Graph implements Iterable<Vertex> {
static final int INFINITY = Integer.MAX_VALUE;
    public Vertex[] V; // array of vertices
    public int N; // number of verices in the graph

    /**
     * Constructor for Graph
     * 
     * @param size
     *            : int - number of vertices
     */
    Graph(int size,int[] durations) {
	N = size;
	V = new Vertex[size + 2];
	//create an array of Vertex objects
		for (int i = 1; i <= size; i++)
		{
		    V[i] = new Vertex(i);
			V[i].duration=durations[i-1];
		}
    }
    /**
     * Class that represents an arc in a Graph
     *
     */
  

    /**
     * Class to represent a vertex of a graph
     * 
     *
     */
  


    /**
     * Method to add an arc to the graph
     * 
     * @param a
     *            : int - the head of the arc
     * @param b
     *            : int - the tail of the arc
     * @param weight
     *            : int - the weight of the arc
     */
    void addEdge(int a, int b) {
    	Edge e;
    
    		 e = new Edge(V[a], V[b]);
    		 V[a].Adj.add(e);
    		 V[b].parent.add(V[a]);
    		
	
	
	//if(V[b].parent==null ||V[b].parent.duration<V[a].duration)
	
	//V[b].Adj.add(e);// commented out because we need a directed graph
    }

    /**
     * Method to create an instance of VertexIterator
     */
    public Iterator<Vertex> iterator() {
	return new VertexIterator<Vertex>(V, N);
    }

    /**
     * A Custom Iterator Class for iterating through the vertices in a graph
     * 
     *
     * @param <Vertex>
     */
    @SuppressWarnings("hiding")
	private class VertexIterator<Vertex> implements Iterator<Vertex> {
	private int nodeIndex = 0;
	private Vertex[] iterV;// array of vertices to iterate through
	private int iterN; // size of the array

	/**
	 * Constructor for VertexIterator
	 * 
	 * @param v
	 *            : Array of vertices
	 * @param n
	 *            : int - Size of the graph
	 */
	private VertexIterator(Vertex[] v, int n) {
	    nodeIndex = 0;
	    iterV = v;
	    iterN = n;
	}

	/**
	 * Method to check if there is any vertex left in the iteration
	 * Overrides the default hasNext() method of Iterator Class
	 */
	public boolean hasNext() {
	    return nodeIndex != iterN;
	}

	/**
	 * Method to return the next Vertex object in the iteration
	 * Overrides the default next() method of Iterator Class
	 */
	public Vertex next() {
	    nodeIndex++;
	    return iterV[nodeIndex];
	}

	/**
	 * Throws an error if a vertex is attempted to be removed
	 */
	public void remove() {
	    throw new UnsupportedOperationException();
	}
    }

    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;
	if (args.length > 0) {
	    File inputFile = new File(args[0]);
	    in = new Scanner(inputFile);
	} else {
	    in = new Scanner(System.in);
	}
	Graph g = readGraph(in);
	g.printGraph();
    }

    static Graph readGraph(Scanner in) {
	// read the graph related parameters
    String s = in.next();
	int n = 0; // number of vertices in the graph
	int m = 0; // number of edges in the graph
  	if(s.charAt(0) == '#') 
  	{
  		s=in.nextLine();
  		n = in.nextInt(); // number of vertices in the graph
  		m = in.nextInt(); // number of edges in the graph
  		
  	}
  	else
  	{
  		n=Integer.parseInt(s);
  		m=in.nextInt();
	
  	}
	int[] durations= new int[n];
	for(int i=0;i<n;i++)
	{
		durations[i]=in.nextInt();
	}

	// create a graph instance
	Graph g = new Graph(n,durations);
	for (int i = 0; i < m; i++) {
	    int u = in.nextInt();
	    int v = in.nextInt();
	   // int w = in.nextInt();
	    g.addEdge(u, v);
	}
	// add start and end nodes
	 
//	g.initialize();
	in.close();
	AddStartEnd(g);
	return g;
    }
    static void AddStartEnd(Graph g)
	 {
    	g.V[0]=new Vertex(0);
    	g.V[g.N+1]=new Vertex(g.N+1);
		 Set<Vertex> mySet = new HashSet<>(Arrays.asList(g.V));
		 for(Vertex v :g.V)
		 {
			 if(v!=null)
			 {
			 if(v.Adj.size()==0 && v.name!=g.N+1 && v.name!=0 )
			 {
				 g.addEdge(v.name, g.V[g.N+1].name);
			 }
			 else
			 {
				 for(Edge e:v.Adj)
				 {
					 mySet.remove(e.To);
				 }
			 }
			 }
		 }
		 mySet.remove(g.V[0]);
		 mySet.remove(g.V[g.N+1]);
		 for(Vertex v :mySet)
		 {
			 if(v!=null)
				 g.addEdge(g.V[0].name,v.name);
		 }
		 
	 }


    /**
     * Method to initialize a graph
     *  1) Sets the parent of every vertex as null
     *  2) Sets the seen attribute of every vertex as false 
     *  3) Sets the distance of every vertex as infinite
     * 
     * @param g
     *            : Graph - The reference to the graph
     */
    void initialize() {
	for (Vertex u : this) {
	    u.seen = false;
	    u.parent = null;
	   // u.weight = INFINITY;
	}
    }

    /**
     * Method to print the graph
     * 
     * @param g
     *            : Graph - The reference to the graph
     */
    void printGraph() {
	for (Vertex u : this.V) {
	    System.out.print(u +" :");
	    for(Edge e: u.Adj) {
		System.out.print(e);
	    }
	    System.out.println();
	}
	

    }
}
