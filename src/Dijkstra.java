import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Dijkstra {
	static Pair2 heap[];
	static int location[];
	static int size;
	static int N;
	static int dist[];
	static int dist2[];

	static List<Pair2> vecinos[];
	static List<Pair2> vecinos2[];

	// Test: 721 - Invitation Cards
	// http://http://livearchive.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3548

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(in.readLine());
		int Q;
		while (T-- > 0) {
			st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken());
			Q = Integer.parseInt(st.nextToken());

			vecinos = new ArrayList[N];
			vecinos2 = new ArrayList[N];

			dist = new int[N];
			dist2 = new int[N];

			for (int i = 0; i < N; i++) {
				vecinos[i] = new ArrayList<Pair2>();
				vecinos2[i] = new ArrayList<Pair2>();
			}

			initHeap(N);

			while (Q-- > 0) {
				st = new StringTokenizer(in.readLine());
				int n1 = Integer.parseInt(st.nextToken()) - 1;
				int n2 = Integer.parseInt(st.nextToken()) - 1;
				int d = Integer.parseInt(st.nextToken());
				vecinos[n1].add(new Pair2(n2, d));
				vecinos2[n2].add(new Pair2(n1, d));
			}
			dijkstra(0, vecinos, dist);
			int sum = 0;
			for (int i = 0; i < N; i++)
				sum += dist[i];
			dijkstra(0, vecinos2, dist2);
			for (int i = 0; i < N; i++)
				sum += dist2[i];
			System.out.println(sum);
		}
	}

	static void initHeap(int n) {
		size = 0;
		heap = new Pair2[n + 1];
		location = new int[n + 1];
	}

	static void dijkstra(int s, List<Pair2> graph[], int dist[]) {
		for (int i = 0; i < N; i++) {
			if (i != s)
				put(new Pair2(i, Integer.MAX_VALUE));
		}
		put(new Pair2(s, 0));
		while (!empty()) {
			Pair2 p = get();
			dist[p.key] = p.val;
			for (Pair2 v : graph[p.key]) {
				Pair2 vp = heap[location[v.key]];
				if (vp.val > p.val + v.val)
					update(v.key, p.val + v.val);
			}
		}
	}

	static boolean empty() {
		return size == 0;
	}

	static void update(int key, int val) {
		int i = location[key];
		heap[i].val = val;
		bubbleup(i);
		bubbledown(i);
	}

	static void put(Pair2 p) {
		heap[++size] = p;
		location[p.key] = size;
		bubbleup(size);
	}

	static Pair2 get() {
		Pair2 val = heap[1];
		heap[1] = heap[size--];
		bubbledown(1);

		return val;
	}

	static void swap(int i, int j) {
		Pair2 temp = heap[i];
		location[heap[i].key] = j;
		location[heap[j].key] = i;

		heap[i] = heap[j];
		heap[j] = temp;
	}

	static void bubbledown(int i) {
		while (2 * i <= size) {
			int k = 2 * i;
			if (k + 1 <= size && heap[k + 1].val < heap[k].val)
				k = k + 1;
			if (heap[k].val > heap[i].val)
				break;
			swap(i, k);
			i = k;
		}
	}

	static void bubbleup(int i) {
		while (i > 1 && heap[i / 2].val > heap[i].val) {
			swap(i, i / 2);
			i = i / 2;
		}
	}

}

class Pair2 {
	int key;
	int val;

	public Pair2(int k, int v) {
		key = k;
		val = v;
	}
}