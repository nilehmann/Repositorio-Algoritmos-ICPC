import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RangeMinimumQuery {
	static int B; // Numero de valores del byte
	static int P; // Primo del hash
	static int L; // Numero de bytes
	static int N; // Numero de comandos

	static int[] arr = new int[100000];
	static int log = 17;
	static int[] tree = new int[300001];

	static int init(int I, int J, int n) {
		if (I == J)
			return tree[n] = compute(I);
		int K = (I + J) / 2 + 1;
		return tree[n] = paste(I, K, J, init(I, K - 1, 2 * n),
				init(K, J, 2 * n + 1));
	}

	static int paste(int I, int K, int J, int V1, int V2) {
		// return arr[V1] < arr[V2] ? V1 : V2;
		return ((int) (((long) V1 * powmod((long) B, J + 1 - K)) % P) + V2) % P;
	}

	static int compute(int I) {
		// return I;
		return arr[I];
	}

	static void update(int S, int T, int I, int V, int n) {
		if (S > I || T < I)
			return;
		if (S == T) {
			arr[I] = V;
			tree[n] = compute(I);
			return;
		}
		tree[n] = getNewValue(S, T, I, V, n);
		int K = (S + T) / 2 + 1;
		update(S, K - 1, I, V, 2 * n);
		update(K, T, I, V, 2 * n + 1);
	}

	static int getNewValue(int S, int T, int I, int V, int n) {
		// return arr[tree[n]] < V ? tree[n] : V
		int k = (int) ((powmod((long) B, T - I) * (long) arr[I]) % P);
		int l = (int) ((powmod((long) B, T - I) * (long) V) % P);
		int s = (tree[n] - k + l) % P;
		return s >= 0 ? s : (s + P) % P;
	}

	static int compute(int S, int T, int I, int J, int n) {
		if (S > J || T < I)
			return 0;
		if (I <= S && J >= T)
			return (int) (((powmod((long) B, J - T) * (long) tree[n])) % P);
		int K = (S + T) / 2 + 1;
		return (compute(S, K - 1, I, J, 2 * n) + compute(K, T, I, J, 2 * n + 1))
				% P;
	}

	// Test: 12365 - Jupiter Attacks!
	// http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3787
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String line;
		StringTokenizer st;
		int I, J;
		while (!(line = in.readLine()).equals("0 0 0 0")) {
			st = new StringTokenizer(line);
			B = Integer.parseInt(st.nextToken());
			P = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			N = Integer.parseInt(st.nextToken());
			for (int i = 0; i < L; ++i)
				arr[i] = 0;
			int l = 2 * pow2(L);
			for (int i = 1; i <= l; ++i)
				tree[i] = 0;
			while (N-- > 0) {
				st = new StringTokenizer(in.readLine());
				char C = st.nextToken().charAt(0);
				switch (C) {
				case 'E':
					I = Integer.parseInt(st.nextToken()) - 1;
					J = Integer.parseInt(st.nextToken()) % P;
					if (arr[I] != J)
						update(0, L - 1, I, J, 1);
					break;
				case 'H':
					I = Integer.parseInt(st.nextToken()) - 1;
					J = Integer.parseInt(st.nextToken()) - 1;
					sb.append(compute(0, L - 1, I, J, 1) + "\n");
					break;
				}
			}
			sb.append("-\n");
		}
		System.out.print(sb);
	}

	static long powmod(long n, int p) {
		if (p == 0)
			return 1;
		if (p == 1)
			return n % P;
		if ((p & 1) == 0)
			return powmod(n * n % P, p / 2) % P;
		else
			return n * powmod(n * n % P, p / 2) % P;
	}

	static int pow2(int n) {
		int l = 1;
		while (n > l)
			l <<= 1;
		return l;
	}
}
