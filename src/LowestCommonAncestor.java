import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class LowestCommonAncestor {
	static int MAX_LOG = 13;
	static int MAX = 5000;

	static int N;
	static List<Integer> vecinos[] = new LinkedList[MAX];
	static int P[][] = new int[MAX][MAX_LOG + 1];
	static int prof[] = new int[MAX];

	// prof de la raiz debe ser cero y P[raiz][0] debe ser raiz
	static void tree(int n) {
		for (int v : vecinos[n]) {
			if (v != P[n][0]) {
				P[v][0] = n;
				prof[v] = prof[n] + 1;
				tree(v);
			}
		}
	}

	static void init_matrix() {
		for (int i = 0; i < N; ++i) {
			for (int j = 1; j <= MAX_LOG; j++) {
				P[i][j] = P[P[i][j - 1]][j - 1];
			}
		}
	}

	static int anc_dist(int a, int dist) {
		for (int i = 0; i <= MAX_LOG; i++) {
			if (((1 << i) & dist) != 0) {
				a = P[a][i];
			}
		}
		return a;
	}

	static int lca(int a, int b) {
		if (prof[a] < prof[b]) {
			int temp = a;
			a = b;
			b = temp;
		}
		a = anc_dist(a, prof[a] - prof[b]);
		if (a == b)
			return a;

		// Para calcular logaritmo
		// for (log = 1; 1 << log <= prof[p]; log++);
		// log--;

		for (int i = MAX_LOG; i >= 0; --i) {
			if (P[a][i] != P[b][i]) {
				a = P[a][i];
				b = P[b][i];
			}
		}
		return P[a][0];
	}

	// Test: 10938 - Flea Circus
	// http://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=1879
	public static void main(String[] args) throws NumberFormatException,
			IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int Q;

		while ((N = Integer.parseInt(in.readLine())) != 0) {
			for (int i = 0; i < N; i++)
				vecinos[i] = new LinkedList<Integer>();

			for (int i = 0; i < N - 1; i++) {
				st = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(st.nextToken()) - 1;
				int b = Integer.parseInt(st.nextToken()) - 1;
				vecinos[a].add(b);
				vecinos[b].add(a);
			}

			int raiz = 0;

			prof[raiz] = 0;
			P[raiz][0] = 0;
			tree(raiz);
			init_matrix();

			Q = Integer.parseInt(in.readLine());
			while (Q-- > 0) {
				st = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(st.nextToken()) - 1;
				int b = Integer.parseInt(st.nextToken()) - 1;

				int k = lca(a, b);
				int d = prof[a] + prof[b] - 2 * prof[k];

				if (prof[a] < prof[b]) {
					int temp = a;
					a = b;
					b = temp;
				}

				if ((d & 1) == 0) {
					System.out.println("The fleas meet at "
							+ (anc_dist(a, d >> 1) + 1) + ".");
				} else {
					int c = anc_dist(a, d >> 1);
					int u = P[c][0];
					System.out.println("The fleas jump forever between "
							+ (Math.min(c, u) + 1) + " and "
							+ (Math.max(u, c) + 1) + ".");
				}
			}
		}
	}
}
