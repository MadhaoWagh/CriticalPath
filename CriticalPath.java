

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class CriticalPath {
	 private static int phase = 0;
	   private static long startTime, endTime, elapsedTime;
	   
	   public static void timer()
	    {
	        if(phase == 0) {
		    startTime = System.currentTimeMillis();
		    phase = 1;
		} else {
		    endTime = System.currentTimeMillis();
	            elapsedTime = endTime-startTime;
	            System.out.println("Time: " + elapsedTime + " msec.");
	            memory();
	            phase = 0;
	        }
	    }

	    public static void memory() {
	        long memAvailable = Runtime.getRuntime().totalMemory();
	        long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
	        System.out.println("Memory: " + memUsed/1000000 + " MB / " + memAvailable/1000000 + " MB.");
	    }
	   /*
	    * This method prints the EC LC and Slack values for all the nodes in the graph
	    */
	static void print(Graph g) {
		System.out.println("\nTask\tEC\tLC\tSlack");
			for (Vertex u : g.V) {
				if(u!=null && u.name!=0 && u.name!=g.N+1)
				{
			    System.out.print(u +"\t"+u.ec+ "\t"+u.lc+"\t"+u.slack);
			    System.out.println();
				}
			}
			}
	/*
	 * This method sets all the EC,LC and slack values in the graph, it first calcluates EC in topological order
	 * during this pocess it also stores all the nodes in a stack, that gives the reverse topological order to calculate the LC and slack
	 */
	static void SetValues (Graph g){
		
		Queue<Vertex> TopOrder=TopologicalOrdering.toplogicalOrder1(g);
		Stack<Vertex> RevOrder=new Stack<>();
		Vertex v;
		while(!TopOrder.isEmpty())
		{
			 v= TopOrder.poll();
			RevOrder.push(v);
			//System.out.println(v.name);
			Vertex p;
			if(v.parent.size()>0)	
			{
				int i=v.parent.size()-1;
			while(i>=0)
			{
				p=v.parent.get(i);
				if(v.ec<(p.ec+v.duration))
					v.ec=p.ec+v.duration;
				i=i-1;
			}
			}
			else
			{
				v.ec=v.duration;
			}
					
			
		}
		while(!RevOrder.isEmpty())
		{
			v=RevOrder.pop();
			if(v.Adj.size()==0)
			{
				v.lc=v.ec;
			}
			else
			{
				for (Edge e:v.Adj)
				{
					if(v.lc>(e.To.lc-e.To.duration))
					{
						v.lc=e.To.lc-e.To.duration;
					}
				
				}
			}
			v.slack=v.lc-v.ec;
		}
	
	}
/*
 * This code calculates the critical path for the graph, it first calculates the values by using the SetValues method
 * Then it uses the findpath method to find all the candidates for critical paths
 * The method later finds paths from these paths that have length equal to the critical path length and prints them on the screen
 */
	static void CriticalPaths(Graph g)
	{
		 Stack<Vertex> path=new Stack<>();;
		 ArrayList<ArrayList<Vertex>> CPaths=new ArrayList<>();;
		
		
		SetValues(g);
		
		for(Vertex v:g.V)
		{
			v.seen=false;
		}
		int sum=0;
		findpath(g.V[0], g.N+1,g.V[g.N+1].ec,path,CPaths,sum);
		int cnt=0;
		for(Vertex vv:g.V)
		{
			if(vv.slack==0)
			{
				cnt++;
			}
		}
		String s="";
		int j=1;
		s= Integer.toString(g.V[g.N+1].ec)+" "+Integer.toString(cnt-2)+" "+CPaths.size();
		System.out.println(s);
		for(ArrayList<Vertex> al:CPaths)
		{
			 
			s=""+j+": ";
			j++;
			for(Vertex v :al)
			{
				if(v.name!=0 && v.name!=g.N+1)
				{
				s=s+v+" ";
				}
			}
			System.out.println(s);
		}
	
	
		
		
	}
	/*
	 * Given a vertex this method recursively calls itself to find critical paths in the graph , whenever it finds a path it inputs it to the 
	 * Cpaths array, while finding the paths we filter all paths that are not critical paths or don't meet the length requirements of our critical paths
	 */

	static void findpath(Vertex v,int target,int CPL,Stack<Vertex> path,ArrayList<ArrayList<Vertex>> CPaths, int sum){
		
		if(v.name == target){
			//System.out.println(path);
			ArrayList<Vertex> p=new ArrayList<>(path);
			int  l=0;
			for(Vertex vv:p)
			{
				l+=vv.duration;
			}
			if(l==CPL)
			CPaths.add(p);
			//print all elements in path		
		}
		else{
			v.seen = true;
			path.push(v);
			sum=sum+v.duration;

			//for all 'adjacentNode' adjacent to currentNode:
			for(Edge e:v.Adj)
			{
				if(e.To.seen == false && e.To.slack==0 && sum+e.To.duration<=CPL && v.ec+e.To.duration==e.To.ec)
				{
	                findpath(e.To,target,CPL,path,CPaths,sum);
				}
			}

			v.seen = false;
			path.pop();
			sum=sum-v.duration;
		}
	}
	
	
	
	
	public static void main(String[] args)
	{
		Scanner in = null;
		if(args.length>0)
		{
			File input=new File(args[0]);
			try{
				in=new Scanner(input);
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			in=new Scanner(System.in);
		}
		
		Graph g = Graph.readGraph(in);
		//g.printGraph();
		//SetValues(g);
		timer();
		CriticalPaths(g);
		print(g);
		timer();
	}

}
