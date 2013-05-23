import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Trie {
	static int N;
	static Nodo trie;
	static char start = 'A';

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String line;
		StringTokenizer st;

		while (!(line = in.readLine()).equals("-1")) {
			N = Integer.parseInt(line);
			trie = null;
			st = new StringTokenizer(in.readLine());
			for (int i = 0; i < N; ++i)
				trie = insert(st.nextToken(), trie);
			st = new StringTokenizer(in.readLine());
			for (int i = 0; i < N; ++i)
				trie = insert(st.nextToken(), trie);
		}
	}

	public static Nodo construct(String s) {
		Nodo n = new Nodo();
		Nodo m = n;
		for (int i = 0; i < s.length(); ++i) {
			m.childs[s.charAt(i) - start] = new Nodo();
			m = m.childs[s.charAt(i) - start];
		}
		m.end = true;
		return n;
	}

	public static Nodo insert(String s, Nodo n) {
		if (n == null)
			return construct(s);
		if (s.equals("")) {
			n.end = true;
			return n;
		}
		n.childs[s.charAt(0) - start] = insert(s.substring(1),
				n.childs[s.charAt(0) - start]);
		return n;
	}

	public static void printChilds(Nodo n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 26; ++i) {
			if (n.childs[i] != null) {
				sb.append((char) ('A' + i));
				if (n.childs[i].end)
					sb.append('*');
				sb.append(' ');
			}
		}
		System.out.println(sb);
	}
}

class Nodo {
	boolean end;
	Nodo[] childs = new Nodo[26];

	public Nodo() {
		end = false;
	}
}