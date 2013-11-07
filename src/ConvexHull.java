import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*
 * Andrew's monotone chain convex hull algorithm
 */
public class ConvexHull {
	// http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=45
	static int N;
	static ArrayList<ArrayList<Point>> kingdoms;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		kingdoms = new ArrayList<ArrayList<Point>>();
		while ((N = Integer.parseInt(in.readLine())) != -1) {
			ArrayList<Point> kingdom = new ArrayList<Point>();

			for (int i = 0; i < N; ++i) {
				st = new StringTokenizer(in.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());

				kingdom.add(new Point(x, y));
			}
			kingdom = convexHull(kingdom);
			kingdoms.add(kingdom);
		}

		String line;
		double area = 0;
		while ((line = in.readLine()) != null) {
			st = new StringTokenizer(line);

			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			Point p = new Point(x, y);

			for (int i = 0; i < kingdoms.size(); ++i) {
				ArrayList<Point> k = kingdoms.get(i);
				if (inPolygon(p, k)) {
					area += area(k);
					kingdoms.remove(i);
					break;
				}
			}
		}
		System.out.printf("%.2f\n", area);
	}

	/**
	 * Area de cualquier polígono. Se debe repetir el primer punto
	 */
	static double area(ArrayList<Point> P) {
		double a = 0;
		for (int i = 0; i < P.size() - 1; ++i) {
			Point p1 = P.get(i);
			Point p2 = P.get(i + 1);
			a += p1.x * p2.y - p2.x * p1.y;
		}
		return Math.abs(a / 2);
	}

	/**
	 * Devuelve puntos en counter-clockwise repitiendo el primero.
	 */
	static ArrayList<Point> convexHull(ArrayList<Point> P) {
		Collections.sort(P);

		ArrayList<Point> H = new ArrayList<Point>(N);

		int k = 0;
		for (Point p : P) {
			while ((k = H.size()) >= 2
					&& cross(H.get(k - 2), H.get(k - 1), p) <= 0)
				H.remove(k - 1);
			H.add(p);
		}
		for (int i = P.size() - 2, t = H.size() + 1; i >= 0; i--) {
			Point p = P.get(i);
			while ((k = H.size()) >= t
					&& cross(H.get(k - 2), H.get(k - 1), p) <= 0)
				H.remove(k - 1);
			H.add(p);
		}

		return H;
	}

	/**
	 * Asume vertices en counter-clockwise. Polígono convexo. Se debe repetir el
	 * primer punto.
	 */
	static boolean inPolygon(Point p, ArrayList<Point> P) {
		for (int i = 0; i < P.size() - 1; ++i) {
			// <= Para no considerar la frontera
			// > Para vertices en clockwise
			if (cross(p, P.get(i), P.get(i + 1)) < 0)
				return false;
		}

		return true;
	}

	/**
	 * Producto cruz entre oa y ob. Si oab hace un giro hacia la izquierda
	 * devuelve mayor que cero, si lo hace a la derecha menor que cero. Cero si
	 * son colineales
	 */
	static int cross(Point o, Point a, Point b) {
		return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
	}

}

class Point implements Comparable<Point> {
	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Point p) {
		if (p.x != x)
			return x - p.x;

		return y - p.y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
