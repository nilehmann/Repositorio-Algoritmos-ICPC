import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class TreeIsomorphism {

	static int MAX = 100000;
	static int n[] = new int[2];
	static List<Integer> adj[][] = new LinkedList[2][MAX];
	static List<Integer> leaves[] = new LinkedList[2];
	static List<Integer> childs[][] = new LinkedList[2][MAX];

	static int degree[][] = new int[2][MAX];

	static int types[][] = new int[2][MAX];
	static List<Integer> currtypes[] = new LinkedList[2];

	static List<Integer> t = new LinkedList<Integer>();

	static int typen = 1;

	static Map<String, Integer> hash;

	static int total[] = new int[2];

	static int compute(List<Integer> childs, int j) {
		t.clear();
		StringBuilder sb = new StringBuilder();
		for (Integer v : childs)
			t.add(types[j][v]);

		Collections.sort(t);

		for (Integer v : t) {
			sb.append(v);
			sb.append(' ');
		}

		String s = sb.toString();

		if (!hash.containsKey(s))
			hash.put(s, typen++);

		return hash.get(s);
	}

	static boolean isomorphic() {
		total[0] = 0;
		total[1] = 0;

		if (n[0] != n[1])
			return false;

		hash.clear();

		int N = n[0];

		while (true) {
			for (int j = 0; j < 2; ++j) {
				leaves[j].clear();
				for (int i = 0; i < N; ++i) {
					if (degree[j][i] == 1) {
						leaves[j].add(i);
						degree[j][i] = 0;
						total[j]++;
					}
				}
			}

			if (leaves[0].size() != leaves[1].size())
				return false;

			for (int j = 0; j < 2; ++j) {
				currtypes[j].clear();
				for (Integer v : leaves[j]) {
					if (adj[j][v].size() == 1)
						types[j][v] = 0;
					else
						types[j][v] = compute(childs[j][v], j);

					currtypes[j].add(types[j][v]);

					if (total[j] != N) {
						int k = -1;
						for (Integer u : adj[j][v]) {
							if (degree[j][u] > 0) {
								k = u;
								break;
							}
						}
						degree[j][k]--;
						if (degree[j][k] == 0)
							degree[j][k] = 1;
						childs[j][k].add(v);
					}
				}
			}

			Collections.sort(currtypes[0]);
			Collections.sort(currtypes[1]);

			if (!(currtypes[0].equals(currtypes[1])))
				return false;

			if (total[0] == N || total[1] == N)
				return true;
		}
	}

	// Test: 12489 - Combating cancer
	// http://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=3933
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		String line;
		hash = new HashMap<String, Integer>();
		while ((line = in.readLine()) != null) {
			n[0] = n[1] = Integer.parseInt(line);
			for (int j = 0; j < 2; ++j) {
				for (int i = 0; i < n[j]; ++i) {
					degree[j][i] = 0;
					adj[j][i] = new LinkedList<Integer>();
					childs[j][i] = new LinkedList<Integer>();
				}
				for (int i = 0; i < n[j] - 1; ++i) {
					st = new StringTokenizer(in.readLine());
					int a = Integer.parseInt(st.nextToken()) - 1;
					int b = Integer.parseInt(st.nextToken()) - 1;

					degree[j][a]++;
					degree[j][b]++;
					adj[j][a].add(b);
					adj[j][b].add(a);
				}
				leaves[j] = new LinkedList<Integer>();
				currtypes[j] = new LinkedList<Integer>();
			}
			if (isomorphic())
				sb.append("S\n");
			else
				sb.append("N\n");
		}
		System.out.print(sb);
	}
}
