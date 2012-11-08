import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Sieve {
	static int MAX = 10000;
	static int n_primes;
	static int primes[] = new int[1229];
	static boolean sieve[] = new boolean[MAX];
	
	
	static int phi(int x){
		int coprimes = 1;
		int curr = 0;		
		while(curr < n_primes && x > 1){
			int e = 0;
			while(x%primes[curr] == 0){
				e++;
				x /= primes[curr];
			}
			if(e > 0)
				coprimes *= pow(primes[curr],e)- pow(primes[curr],e-1);
			curr++;
		}
		if(x > 1)
			coprimes *= pow(x,1) - 1;
		return coprimes;
	}
	
	static int pow(int base, int exp){
		int pow = 1;
		for(int i = 0; i < 32; ++i){
			if((exp & (1 << i)) != 0)
				pow *= base;
			base *= base;
		}
		return pow;
	}

	
	static void init_sieve(){
		for(int i = 4; i < MAX; i += 2)
			sieve[i] = true;
		primes[n_primes++ ] = 2;		
		for(int i = 3; i < MAX; i +=2){
			if(!sieve[i]){
				primes[n_primes++] = i;
				for(int j = i*i; j < MAX; j += 2*i)
					sieve[j] = true;
				
			}
		}
	}
	
	// Test: 2777 - Visible Lattice Points
	// http://acm.zju.edu.cn/onlinejudge/showProblem.do?problemId=1777	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		init_sieve();
		int N;
		int C = Integer.parseInt(in.readLine());
		for(int caso =1; caso <=C ;++caso){
			N = Integer.parseInt(in.readLine());
			int sum = 0;
			for(int i = 1; i <= N; ++i)
				sum += phi(i)*2;
			sum++;
			
			System.out.println(caso+" "	+N+" "+sum);
		}
		
	}
	
}
