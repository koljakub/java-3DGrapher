package transforms;

/**
 * trida pro praci s kubickymi interpolacnimi krivkami v 3D
 * 
 * @author PGRF FIM UHK
 * @version 2016
 */

public class Cubic {
	public enum Type {FERGUSON , COONS, BEZIER};

	/**
	 * bazova matice
	 */
	private Mat4 baseMat = new Mat4();

	/**
	 * matice ridicich bodu
	 */
	private Mat4 controlMat;

	/**
	 * Vytvari kubiku
	 * 
	 * @param type
	 *            typ interpolacni krivky (FERGUSON, COONS, BEZIER)
	 */
	public Cubic(Type type) {
		switch (type) {
		case FERGUSON:
			baseMat.mat[0][0] = 2;
			baseMat.mat[0][1] = -2;
			baseMat.mat[0][2] = 1;
			baseMat.mat[0][3] = 1;

			baseMat.mat[1][0] = -3;
			baseMat.mat[1][1] = 3;
			baseMat.mat[1][2] = -2;
			baseMat.mat[1][3] = -1;

			baseMat.mat[2][0] = 0;
			baseMat.mat[2][1] = 0;
			baseMat.mat[2][2] = 1;
			baseMat.mat[2][3] = 0;

			baseMat.mat[3][0] = 1;
			baseMat.mat[3][1] = 0;
			baseMat.mat[3][2] = 0;
			baseMat.mat[3][3] = 0;
			break;

		case COONS:
			baseMat.mat[0][0] = -1;
			baseMat.mat[0][1] = 3;
			baseMat.mat[0][2] = -3;
			baseMat.mat[0][3] = 1;

			baseMat.mat[1][0] = 3;
			baseMat.mat[1][1] = -6;
			baseMat.mat[1][2] = 3;
			baseMat.mat[1][3] = 0;

			baseMat.mat[2][0] = -3;
			baseMat.mat[2][1] = 0;
			baseMat.mat[2][2] = 3;
			baseMat.mat[2][3] = 0;

			baseMat.mat[3][0] = 1;
			baseMat.mat[3][1] = 4;
			baseMat.mat[3][2] = 1;
			baseMat.mat[3][3] = 0;

			baseMat = baseMat.mul(1 / 6f);
			break;

		case BEZIER:
		default:
			baseMat.mat[0][0] = -1;
			baseMat.mat[0][1] = 3;
			baseMat.mat[0][2] = -3;
			baseMat.mat[0][3] = 1;

			baseMat.mat[1][0] = 3;
			baseMat.mat[1][1] = -6;
			baseMat.mat[1][2] = 3;
			baseMat.mat[1][3] = 0;

			baseMat.mat[2][0] = -3;
			baseMat.mat[2][1] = 3;
			baseMat.mat[2][2] = 0;
			baseMat.mat[2][3] = 0;

			baseMat.mat[3][0] = 1;
			baseMat.mat[3][1] = 0;
			baseMat.mat[3][2] = 0;
			baseMat.mat[3][3] = 0;
		}

	}

	/**
	 * inicializace pomoci zadane ctverice ridicich bodu
	 * 
	 * @param p1
	 *            ridici bod 1
	 * @param p2
	 *            ridici bod 2
	 * @param p3
	 *            ridici bod 3
	 * @param p4
	 *            ridici bod 4
	 */
	public void init(Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
		controlMat = new Mat4(p1, p2, p3, p4);
		controlMat = baseMat.mul(controlMat);
	}

	/**
	 * inicializace pomoci zadaneho pole ridicich bodu
	 * 
	 * @param points
	 *            pole ridicich bodu
	 */
	public void init(Point3D[] points) {
		init(points,0);
	}

	/**
	 * inicializace pomoci zadaneho pole ridicich bodu
	 * 
	 * @param points
	 *            pole ridicich bodu
	 * @param startIndex
	 *            index prvniho ridiciho bodu
	 */
	public void init(Point3D[] points, int startIndex) {
		controlMat = new Mat4(points[startIndex], points[startIndex + 1],
				points[startIndex + 2], points[startIndex + 3]);
		controlMat = baseMat.mul(controlMat);
	}

	/**
	 * vypocet souradnice bodu bikubiky podle parametru t z intervalu <0,1>
	 * 
	 * @param t
	 *            interpolacni parametr z intervalu <0,1>
	 * @return bod Point3D lezici na krivce
	 */
	public Point3D compute(double t) {
		if (t > 1)
			t = 1;
		if (t < 0)
			t = 0;

		Point3D res = new Point3D(t * t * t, t * t, t, 1);

		res = res.mul(controlMat);
		return new Point3D(res.ignoreW());
	}

}
