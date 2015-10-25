import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TopologicalOrdering {
	 static Queue<Vertex> toplogicalOrder1(Graph g)

	
	{
		Queue<Vertex> Q=new LinkedList<>();
		int count=0;
		Queue<Vertex> Top =new LinkedList<Vertex> ();
		for(Vertex i:g.V)// set indegree for all the nodes
		{
			if(i!=null)
			{
				for(Edge j:i.Adj)
				{
					j.To.indegree++;
				}
			}
		}
		for(Vertex i:g.V)// get into the queue  vertices with 0 indegree
		{
			if(i!=null)
			{
				if(i.indegree==0)
				{
					Q.add(i);
				}
			}
		}
		while(!Q.isEmpty())// while the queue is not empty
		{
			Vertex u=Q.remove();
			Top.add(u);
			count++;
			for(Edge j:u.Adj)// update indegree for all the vertices that have moved out
			{
				j.To.indegree--;
				if(j.To.indegree==0)// if indegree becomes 0 add to queue
				{
					Q.add(j.To);
				}
			}
			
		}
		if(count!=g.V.length)// if we have count not equal to no of vertices not a DAG
		{
			System.out.println("Not a DAG "+count+" "+g.V.length);
			return null;
		}
		else
		{
			return Top;
		}
		
	}

	 static Stack<Vertex> DFSVisit(Vertex u,Stack<Vertex> s)
	{
		u.seen=true;
		u.active=true;
		for(Edge e:u.Adj)// set nodes to seen and active and for all its to nodes call DFSVisit
		{
			if(!e.To.seen)
			{
				DFSVisit(e.To,s);
			}
			else
			{
				if(e.To.active)
				{
					System.out.println("Not a DAG");
					break;
				}
			}
			
		}
		s.push(u);// pust the elememt into the stack
		u.active=false;
		return s;
	}
	static Stack<Vertex> toplogicalOrder2(Graph g)
	{
		Stack<Vertex> out=new Stack<>();
		for(Vertex u:g.V)// set all nodes to not seen and not active
		{
			if(u!=null)
			{
			u.seen=false;
			u.active=false;
			}
		}
		
		for(Vertex u:g.V)// if a vertex is not visited call DFSVisit for it
		{
			if(u!=null && !u.seen)
			{
				out=DFSVisit(u,out);
			}
		}
		
		return out;
	}
  
	
	
	
}
