import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

public class Kosaraju {
	static Map<String, Set<String>> dgraph;
	static Map<String, Set<String>> tgraph;
	static Set<String> nodes;
	static int n, m;
	static Map<String, Set<String>> components;
	static Stack<String> stack;
	static Set<String> visited;

	static void kosaraju() {
		visited = new HashSet<String>();
		for (String s : nodes) {
			if (!visited.contains(s)) {
				visited.add(s);
				topSort(s);
			}
		}

		while (!stack.isEmpty()) {
			String s = stack.pop();
			visited = new HashSet<String>();
			visited.add(s);
			dfs(s);
			components.put(s, new HashSet<String>(visited));

			stack.removeAll(visited);
			for (String v : visited) {
				Set<String> neigh = dgraph.get(v);
				if (neigh != null)
					for (String u : neigh)
						tgraph.get(u).remove(v);
			}
		}
	}

	static void topSort(String s) {
		Set<String> neigh = dgraph.get(s);
		if (neigh != null) {
			for (String v : neigh) {
				if (!visited.contains(v)) {
					visited.add(v);
					topSort(v);
				}
			}
		}
		stack.push(s);
	}

	static void dfs(String s) {
		Set<String> neigh = tgraph.get(s);
		if (neigh != null) {
			for (String v : neigh) {
				if (!visited.contains(v)) {
					visited.add(v);
					dfs(v);
				}
			}
		}
	}
	
	
	// Test: 247 - Calling Circles
	// http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=183
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		String s;
		int c = 1;

		while (!(s = in.readLine()).equals("0 0")) {
			dgraph = new HashMap<String, Set<String>>();
			tgraph = new HashMap<String, Set<String>>();
			nodes = new HashSet<String>();
			stack = new Stack<String>();
			components = new HashMap<String, Set<String>>();

			st = new StringTokenizer(s);
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());

			for (int i = 0; i < m; i++) {
				st = new StringTokenizer(in.readLine());
				String a = st.nextToken();
				String b = st.nextToken();

				Set<String> neigh = dgraph.get(a);
				if (neigh == null) {
					neigh = new HashSet<String>();
					dgraph.put(a, neigh);
				}
				neigh.add(b);

				neigh = tgraph.get(b);
				if (neigh == null) {
					neigh = new HashSet<String>();
					tgraph.put(b, neigh);
				}
				neigh.add(a);

				nodes.add(a);
				nodes.add(b);
			}

			kosaraju();

			if (c > 1)
				System.out.println();
			System.out.println("Calling circles for data set " + (c++) + ":");
			for (String k : components.keySet()) {
				Set<String> comp = components.get(k);
				StringBuilder sb = new StringBuilder();
				for (String v : comp)
					sb.append(v + ", ");
				sb.delete(sb.length() - 2, sb.length() - 1);

				/*
				 * no sé porque delete deja espacios D: así que por eso el trim
				 */
				System.out.println(sb.toString().trim());
			}

		}
	}
}
