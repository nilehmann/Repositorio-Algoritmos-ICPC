import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class geometria {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String s;

		while (!(s = in.readLine()).equals("0 0 0 0 0 0 0 0")) {
			StringTokenizer st = new StringTokenizer(s);
			double x1 = Double.parseDouble(st.nextToken());
			double y1 = Double.parseDouble(st.nextToken());
			double x2 = Double.parseDouble(st.nextToken());
			double y2 = Double.parseDouble(st.nextToken());
			double x3 = Double.parseDouble(st.nextToken());
			double y3 = Double.parseDouble(st.nextToken());
			double x4 = Double.parseDouble(st.nextToken());
			double y4 = Double.parseDouble(st.nextToken());

			Point p1 = new Point(x1, y1);
			Point p2 = new Point(x2, y2);
			Point p3 = new Point(x3, y3);
			Point p4 = new Point(x4, y4);

			int sentido = sentido(p1, p4, p2);
			if (sentido == sentido(p2, p4, p3)
					&& sentido == sentido(p3, p4, p1))
				System.out.println("Ah, sí, sí, sí.");
			else
				System.out.println("Nunca fue mi intención.");
		}
	}

	public static int sentido(Point p1, Point p2, Point p3) {
		double x1 = p2.x - p1.x;
		double x2 = p3.x - p2.x;
		double y1 = p2.y - p1.y;
		double y2 = p3.y - p2.y;
		double cross = x1 * y2 - y1 * x2;

		if (cross == 0)
			return 0;
		if (cross > 0)
			return 1;
		return -1;
	}

	public static double dist(Point p1, Point p2) {
		double dx = p1.x - p2.x;
		double dy = p1.y - p2.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public static double dist(Point p1, Point p2, Point p3) {
		double A = p2.y - p1.y;
		double B = p1.x - p2.x;
		double C = p2.x * p1.y - p1.x * p2.y;

		double D = Math.sqrt(A * A + B * B);
		A = A / D;
		B = B / D;
		C = C / D;

		return A * p3.x + B * p3.y + C;
	}

	public static class Point {
		double x;
		double y;

		public Point(double a, double b) {
			x = a;
			y = b;
		}
	}
}
