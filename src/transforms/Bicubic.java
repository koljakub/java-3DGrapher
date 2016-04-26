package transforms;

/**
 * trida pro praci s bikubickymi interpolacnimi plochami v 3D
 * 
 * @author PGRF FIM UHK
 * @version 2016
 */

public class Bicubic {
	private final Cubic k1, k2, k3, k4, k5;

	/**
	 * Vytvari bikubiku
	 * 
	 * @param type
	 *            typ interpolacni krivky Cubic.Type (FERGUSON, COONS, BEZIER)
	 */
	public Bicubic(Cubic.Type type) {
		k1 = new Cubic(type);
		k2 = new Cubic(type);
		k3 = new Cubic(type);
		k4 = new Cubic(type);
		k5 = new Cubic(type);
	}

	/**
	 * Inicializace pomoci 4x4 ridicich bodu
	 * 
	 * @param b11
	 * @param b12
	 * @param b13
	 * @param b14
	 * @param b21
	 * @param b22
	 * @param b23
	 * @param b24
	 * @param b31
	 * @param b32
	 * @param b33
	 * @param b34
	 * @param b41
	 * @param b42
	 * @param b43
	 * @param b44
	 */
	public void init(Point3D b11, Point3D b12, Point3D b13, Point3D b14,
			Point3D b21, Point3D b22, Point3D b23, Point3D b24, Point3D b31,
			Point3D b32, Point3D b33, Point3D b34, Point3D b41, Point3D b42,
			Point3D b43, Point3D b44) {
		k1.init(b11, b12, b13, b14);
		k2.init(b21, b22, b23, b24);
		k3.init(b31, b32, b33, b34);
		k4.init(b41, b42, b43, b44);
	}

	/**
	 * Inicializace pomoci pole ridicich bodu
	 * 
	 * @param points
	 *            pole ridicich bodu
	 */
	public void init(Point3D[] points) {
		init(points, 0);
	}

	/**
	 * Inicializace pomoci pole ridicich bodu
	 * 
	 * @param points
	 *            pole ridicich bodu
	 * @param startIndex
	 *            index prvniho ridiciho bodu
	 */
	public void init(Point3D[] points, int startIndex) {
		k1.init(points, startIndex);
		k2.init(points, startIndex + 4);
		k3.init(points, startIndex + 8);
		k4.init(points, startIndex + 12);
	}

	/**
	 * Vypocet souradnice bodu bikubicke plochy podle parametru u,v z intervalu
	 * <0,1>
	 * 
	 * @param u
	 *            interpolacni parametr z intervalu <0,1>
	 * @param v
	 *            interpolacni parametr z intervalu <0,1>
	 * @return bod Point3D lezici na plose
	 */
	public Point3D compute(double u, double v) {
		if (u > 1)
			u = 1;
		if (u < 0)
			u = 0;
		if (v > 1)
			v = 1;
		if (v < 0)
			v = 0;

		final Point3D u1, u2, u3, u4;
		u1 = k1.compute(u);
		u2 = k2.compute(u);
		u3 = k3.compute(u);
		u4 = k4.compute(u);
		k5.init(u1, u2, u3, u4);
		Point3D t = k5.compute(v);
		return t;
	}

}
