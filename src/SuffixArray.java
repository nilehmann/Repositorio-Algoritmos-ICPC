import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*Calcula toda la matriz usando entradas que implementan comparable*/
public class SuffixArray {
	static int maxlg = 20;
	static int maxlen = 100000;
	
	static String S;
	static int N;
	static entry sa[];
	static int	P[][];
	static int stp;
	static int lcp[];
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		
		P = new int[maxlg][maxlen];
		sa = new entry[maxlen];
		lcp = new int[maxlen];
		
		S = in.readLine();
		while (S.charAt(0) != '*') {
			N = S.length();
			buildSA();
			
//			for (int i = 0; i < N; i++) {
//				System.out.println(S.substring(sa[i].p));
//			}
						
			int c = 0;
			int lcpant = 0;
			int l;
//			buildLCP();
			for (int i = 1; i < N; i++) {
//				l = lcp[i];
				l = getlcp(sa[i].p, sa[i-1].p);
				
				if(l >= lcpant){
					c += l -lcpant;
				}
				lcpant = l;
			}
			System.out.println(c);
			

			S = in.readLine();		
		}
		
	}
	
	
	static int getlcp ( int x, int y) {
		int k, ret = 0;
		if (x == y) return N - x;
		for (k = stp - 1; k >= 0 && x < N && y < N; k--)		
			if (P[k][x] == P[k][y]){
				x += 1 << k; 
				y += 1 << k; 
				ret += 1 << k;
			}
		return ret;
	}
	
	
	static void buildLCP() {
		int h = 0, rank[] = new int[N];
		for (int i = 0; i < N; i++) 
			rank[sa[i].p] = i;
		for (int i = 0; i < N; i++) {
			if(rank[i] > 0){
				int k = sa[rank[i] - 1].p; //El que viene antes del sufijo que parte en i;
				while(i+h < N && k+h < N && S.charAt(i+h) == S.charAt(k+h)) h++;
				lcp[rank[i]] = h;
				if(h > 0) h--;
			}
		}
	}
	
	static void buildSA(){
		for (int i = 0; i < N; i++) {
			P[0][i] = S.charAt(i);		
		}
		
		int cnt;
		boolean salir = false;
		for(stp = 1, cnt = 1; !salir; cnt <<=1 , ++stp ){
			for (int i = 0; i < N; i++) {
				int b = i+cnt < N ? P[stp-1][i+cnt] : -1;
				sa[i] = new entry(P[stp-1][i], b, i);
			}
			Arrays.sort(sa,0, N);
			
			salir = true;
			for (int i = 0; i < N; i++) {
				if(i > 0 && sa[i].equals(sa[i-1])){
					salir = false;
					P[stp][sa[i].p] = P[stp][sa[i-1].p];
				}
				else
					P[stp][sa[i].p] = i;
			}
		}
	}
	
}

class entry implements Comparable<entry>{
	int p;
	int first;
	int second;
	
	public entry(int a, int b, int c){
		first = a;
		second = b;
		p = c;
	}
	
	
	public boolean equals(Object o){
		entry e = (entry) o;
		return e.first == this.first && this.second == e.second;
	}
	
	@Override
	public int compareTo(entry o) {
		if(this.first == o.first)
			return this.second - o.second;
		return this.first - o.first;
	}
	
}