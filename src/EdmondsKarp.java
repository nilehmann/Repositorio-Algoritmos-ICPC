import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;


public class EdmondsKarp {
	static int MAX = 100;
	
	//test
	
	static List<Integer> vecinos[] = new ArrayList[MAX];
	static int C[][] = new int[MAX][MAX]; //Capacidad
	static int F[][] = new int[MAX][MAX]; //Asignacion Flujo
	static int P[] = new int[MAX]; //Camino;
	static int N; //Numero de nodos

	static int edmonds_karp(int s, int t){
		int f, m;		
		f = 0;
		while((m = bfs(s,t)) != 0){
			f += m;
			
			int u,v;
			v = t;
			while(v != s){
				u = P[v];
				F[u][v] += m;
				F[v][u] -= m;
				v = u;
			}
			
		}
		return f;
	}
	
	static int bfs(int s, int t){
		Queue<Integer> q = new LinkedList<Integer>();
		int M[] = new int[N];
		
		Arrays.fill(P,0, N, -1);
		P[s] = -2;
		M[s] = Integer.MAX_VALUE;
		
		q.offer(s);
		while(!q.isEmpty()){
			int u = q.poll();
			
			for(int v : vecinos[u]){
				int cf = C[u][v] - F[u][v];
				if(cf > 0 && P[v] == -1){
					P[v] = u;
					M[v] = Math.min(M[u], cf);
					if(v == t)
						return M[t];
					else
						q.offer(v);
				}
			}
		}
		return 0;
	}
	
	
	
	//Test: 820 - Internet Bandwidth http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=761
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int s,t,e,c, u, v;
		int network = 1;
		while( (N = Integer.parseInt(in.readLine())) != 0){
			st = new StringTokenizer(in.readLine());
			
			s = Integer.parseInt(st.nextToken()) - 1;
			t = Integer.parseInt(st.nextToken()) - 1;
			e = Integer.parseInt(st.nextToken());
			
			for (int i = 0; i < N; i++) {
				vecinos[i] = new ArrayList<Integer>();
				Arrays.fill(C[i], 0, N, 0);
				Arrays.fill(F[i], 0, N, 0);
			}
			
			
			while(e-- > 0){
				st = new StringTokenizer(in.readLine());
				u = Integer.parseInt(st.nextToken()) - 1;
				v = Integer.parseInt(st.nextToken()) - 1;
				c = Integer.parseInt(st.nextToken());
				vecinos[u].add(v); vecinos[v].add(u);
				C[u][v] = C[v][u] += c;
			}
			sb.append("Network "+(network++)+"\n");
			sb.append("The bandwidth is "+ edmonds_karp(s,t)+".\n\n");
		}
		System.out.print(sb);
		
	}
	
}

