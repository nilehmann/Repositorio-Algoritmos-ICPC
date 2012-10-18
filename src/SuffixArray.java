import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/*No calcula la matriz usa comparador*/
public class SuffixArray {
	static int maxlen= 100002;
	
	
	static int P[], b[], lcp[], N;
	static Integer sa[];
	static StringBuilder S;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		
		P = new int[maxlen];
		b = new int[maxlen];
		lcp = new int[maxlen];
		sa = new Integer[maxlen];
		
		int T = Integer.parseInt(in.readLine());
		while (T-- > 0) {
			S = new StringBuilder();
			S.append(in.readLine());
			S.append('$');
			N = S.length();
			buildSA();
			
			int c = 0;
			int l;
			
			buildLCP();
			for (int i = 1; i < N; i++) {
				l = lcp[i];
				c += N-sa[i] - 1  - l;
			}
			System.out.println(c);
		}
		
	}
	
	
	
	static void buildSA() {		
		for (int i = 0; i < N; i++){
			sa[i] = i; 
			P[i] = S.charAt(i);
		}
		Comp cmp = new Comp(0,P);
		b[0] = 0;
		b[N - 1] = 0;
		for (int cnt = 1; b[N - 1] != N - 1; cnt <<= 1) {
			cmp.cnt = cnt;
			Arrays.sort(sa,0, N, cmp);
			for (int i = 0; i < N - 1; i++)
				b[i + 1] = b[i] + (cmp.compare(sa[i], sa[i + 1]) < 0 ? 1:0);
			for (int i = 0; i < N; i++)
				P[sa[i]] = b[i];
		}
	}
	

	static void buildLCP() {
		int h = 0, rank[] = new int[N];
		for (int i = 0; i < N; i++) 
			rank[sa[i]] = i;
		for (int i = 0; i < N; i++) {
			if(rank[i] > 0){
				int k = sa[rank[i] - 1]; //El que viene antes del sufijo que parte en i;
				while(i+h < N && k+h < N && S.charAt(i+h) == S.charAt(k+h)) h++;
				lcp[rank[i]] = h;
				if(h > 0) h--;
			}
		}
	}
	
	
}


class Comp implements Comparator<Integer>{
	int cnt, P[];
	
	public Comp(int a, int b[]){
		cnt = a;
		P = b;
	}
	

	@Override
	public int compare(Integer a, Integer b) {		
		if(P[a] == P[b])
			return P[a+cnt] - P[b+cnt];
		return P[a] - P[b];
	}
	
}