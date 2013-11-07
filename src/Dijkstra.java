import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Dijkstra {
	static int MAX = 1000000;

	static int N;
	static List<Pair2> graph[] = new ArrayList[MAX];
	static List<Pair2> graph2[] = new ArrayList[MAX];
	static int dist[] = new int[MAX + 1];

	static void dijkstra(int s, List<Pair2> graph[]) {
		Queue<Pair2> q = new PriorityQueue<Pair2>();

		for (int i = 0; i < N; i++)
			dist[i] = Integer.MAX_VALUE;

		dist[s] = 0;
		q.offer(new Pair2(s, 0));
		while (!q.isEmpty()) {
			Pair2 u = q.poll();

			if (u.dist > dist[u.node])
				continue;

			dist[u.node] = u.dist;

			for (Pair2 v : graph[u.node]) {
				int new_dist = v.dist + dist[u.node];

				if (new_dist < dist[v.node]) {
					q.offer(new Pair2(v.node, new_dist));
					dist[v.node] = new_dist;
				}
			}
		}
	}

	// Test: 721 - Invitation Cards
	// http://livearchive.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3548

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(in.readLine());
		int Q;
		while (T-- > 0) {
			st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken());
			Q = Integer.parseInt(st.nextToken());

			for (int i = 0; i < N; i++) {
				graph[i] = new ArrayList<Pair2>();
				graph2[i] = new ArrayList<Pair2>();
			}

			while (Q-- > 0) {
				st = new StringTokenizer(in.readLine());
				int n1 = Integer.parseInt(st.nextToken()) - 1;
				int n2 = Integer.parseInt(st.nextToken()) - 1;
				int d = Integer.parseInt(st.nextToken());
				if (n1 != n2) {
					graph[n1].add(new Pair2(n2, d));
					graph2[n2].add(new Pair2(n1, d));
				}
			}
			dijkstra(0, graph);
			int sum = 0;
			for (int i = 0; i < N; i++)
				sum += dist[i];
			dijkstra(0, graph2);
			for (int i = 0; i < N; i++)
				sum += dist[i];

			System.out.println(sum);
		}
	}
}

class Pair2 implements Comparable<Pair2> {
	int node;
	int dist;

	public Pair2(int k, int v) {
		node = k;
		dist = v;
	}

	public int compareTo(Pair2 arg) {
		return dist - arg.dist;
	}
}