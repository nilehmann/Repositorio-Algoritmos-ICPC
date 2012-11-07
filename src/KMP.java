import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class KMP {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String s;
		while(!(s = in.readLine()).equals("Si No")){
			StringTokenizer st = new StringTokenizer(s);
			System.out.println(kmpSearch(st.nextToken().toCharArray(), st.nextToken().toCharArray(), 0));
		}
	}
	
	public static int kmpSearch(char[] pattern, char[] text, int start)
	{
		int[] f = new int[pattern.length];
		f[0] = 0;
		for (int j = 1; j < f.length; j++)
		{
			int i = f[j - 1];
			while (i > 0 && pattern[i] != pattern[j])
			{
				i = f[i];
			}
			if (pattern[i] == pattern[j])
				f[j] = i;
			else
				f[j] = 0;
		}

		int k = start;
		int j = 0;
		while (k < text.length && j < pattern.length)
		{
			while (j > 0 && text[k] != pattern[j])
			{
				j = f[j];
			}
			if (text[k] == pattern[j])
			{
				j++;
			}
			k++;
		}
		if (j == pattern.length)
			return k - j;
		return -1;
	}
}
