import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LongestIncreasingSubsequence {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int[] A = { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15 };
		// int[] A = {1,2,3,4,5,6,7};

		// System.out.println(nn(A));

		ArrayList<Integer> b = nlogn(A);
		System.out.println(b.size());
		for (int i : b) {
			System.out.print(A[i] + " ");
		}
		System.out.println();
	}

	static ArrayList<Integer> nlogn(int[] a) {
		ArrayList<Integer> b = new ArrayList<Integer>();
		int[] p = new int[a.length];
		int left, right, L;

		if (a.length == 0)
			return b;
		b.add(0);
		L = 1;

		for (int i = 1; i < a.length; i++) {
			if (a[i] > a[b.get(L - 1)]) {
				p[i] = b.get(L - 1);
				b.add(i);
				++L;
				continue;
			}

			left = 0;
			right = L - 1;
			while (left < right) {
				int mid = (left + right) / 2;
				if (a[b.get(mid)] < a[i])
					left = mid + 1;
				else
					right = mid;
			}

			if (a[i] < a[b.get(left)]) {
				if (left > 0)
					p[i] = b.get(left - 1);
				b.set(left, i);
			}
		}
		System.out.println("length " + L);

		for (int i = L - 1, j = b.get(L - 1); i > 0; i--, j = p[j]) {
			b.set(i, j);
		}

		return b;
	}

	static int nn(int[] A) {
		int n = A.length;
		int M[] = new int[n];

		int max = Integer.MIN_VALUE;

		for (int i = 0; i < n; i++) {
			int local_max = 0;

			for (int j = 0; j < i; j++) {
				if (A[j] < A[i] && M[j] > local_max)
					local_max = M[j];

			}
			M[i] = local_max + 1;
			if (M[i] > max)
				max = M[i];
		}
		return max;
	}
}