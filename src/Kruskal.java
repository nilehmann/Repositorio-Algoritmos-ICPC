import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Kruskal {
	static int VMAX = 100000;
	static int EMAX = 100000;
	
	static int E;
	static int V;
	static Edge edges[] = new Edge[EMAX];

	static int parent[] = new int[EMAX];
	static int rank[] = new int[EMAX];
	
	static List<Edge> kruskal(){
		init();
		Arrays.sort(edges, 0 , E);
		List<Edge> MST = new LinkedList<Edge>();
		for(int i = 0; i < E; ++i){
			Edge e = edges[i];
			if(find(e.startNode) != find(e.endNode)){
				union(e.startNode, e.endNode);
				MST.add(e);
			}
		}
		return MST;
	}
	
	static void init(){
		for(int i = 0; i < V; ++i)
			parent[i] = i;
	}
	
	static int find(int x){
		if(x != parent[x])
			parent[x] = find(parent[x]);
		return parent[x];
	}
	
	static void union(int x, int y){
		if(rank[x] < rank[y]){
			int temp = y;
			y = x;
			x = temp;
		}
		parent[y] = x;
		rank[x] = rank[x] + rank[y] + 1;			
	}
	
}

class Edge implements Comparable<Edge>{
	int startNode;
	int endNode;
	int dist;
	public Edge(int sn, int en, int dist){
		startNode = sn;
		endNode = en;
		this.dist = dist;
	}
	
	public int compareTo(Edge o) {
		if(dist < o.dist)
			return -1;
		if(dist > o.dist)
			return 1;
		return 0;
	}
}