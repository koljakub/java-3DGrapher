package transforms;

/**
 * Trida pro nastaveni pohledove transformace
 * 
 * @author PGRF FIM UHK
 * @version 2016
 */
public class Camera {
	private double azimuth, radius, zenith;

	private boolean firstPerson; // true -> 1st person, false -> 3rd person

	private Vec3D eye, eyeVector, pos;

	private Mat4 view;

	/**
	 * Na zaklade parametru vypocte pohledovou matici
	 * 
	 */
	private void computeMatrix() {
		eyeVector = new Vec3D((double) (Math.cos(azimuth) * Math.cos(zenith)),
				(double) (Math.sin(azimuth) * Math.cos(zenith)),
				(double) Math.sin(zenith));
		if (firstPerson) {
			eye = new Vec3D(pos);
			view = new Mat4ViewRH(pos, eyeVector.mul(radius),
					new Vec3D((double) (Math.cos(azimuth) * Math.cos(zenith
							+ Math.PI / 2)),
							(double) (Math.sin(azimuth) * Math.cos(zenith
									+ Math.PI / 2)), (double) Math.sin(zenith
									+ Math.PI / 2)));
		} else {
			eye = pos.add(eyeVector.mul(-1 * radius));
			view = new Mat4ViewRH(eye, eyeVector.mul(radius),
					new Vec3D((double) (Math.cos(azimuth) * Math.cos(zenith
							+ Math.PI / 2)),
							(double) (Math.sin(azimuth) * Math.cos(zenith
									+ Math.PI / 2)), (double) Math.sin(zenith
									+ Math.PI / 2)));
		}
	}

	/**
	 * Vytvori kameru v pocatku s nulovym azimutem a zenitem
	 */
	public Camera() {
		azimuth = zenith = 0.0f;
		radius = 1.0f;
		pos = new Vec3D(0.0f, 0.0f, 0.0f);
		firstPerson = true;
		computeMatrix();
	}

	/**
	 * zvetsi uhel azimutu prictenim zmeny
	 * 
	 * @param ang
	 *            velikost zmeny azimutu v radianech
	 */
	public void addAzimuth(double ang) {
		azimuth += ang;
		computeMatrix();
	}

	/**
	 * zvetsi radius prictenim zmeny
	 * 
	 * @param ang
	 *            velikost zmeny radiusu
	 */
	public void addRadius(double dist) {
		if (radius + dist < 0.1f)
			return;
		radius += dist;
		computeMatrix();
	}

	/**
	 * zvetsi radius nasobenim koeficientem
	 * 
	 * @param scale
	 *            koeficient meritka radiusu
	 */
	public void mulRadius(double scale) {
		if (radius * scale < 0.1f)
			return;
		radius *= scale;
		computeMatrix();
	}

	/**
	 * zvetsi uhel zenitu prictenim zmeny
	 * 
	 * @param ang
	 *            velikost zmeny zenitu v radianech
	 */
	public void addZenith(double ang) {
		if (Math.abs(zenith + ang) <= Math.PI / 2) {
			zenith += ang;
			computeMatrix();
		}
	}

	/**
	 * nastaveni azimutu
	 * 
	 * @param ang
	 *            hodnota azimutu v radianech
	 */
	public void setAzimuth(double ang) {
		azimuth = ang;
		computeMatrix();
	}

	/**
	 * nastaveni radiusu
	 * 
	 * @param dist
	 *            hodnota radiusu
	 */
	public void setRadius(double dist) {
		radius = dist;
		computeMatrix();
	}

	/**
	 * nastaveni zenitu
	 * 
	 * @param ang
	 *            hodnota zenitu v radianech
	 */
	public void setZenith(double ang) {
		zenith = ang;
		computeMatrix();
	}

	/**
	 * pohyb kamerou ve smeru zaporneho pohledoveho vektoru
	 * 
	 * @param speed
	 *            rychlost pohybu
	 */
	public void backward(double speed) {
		forward((-1) * speed);
	}

	/**
	 * pohyb kamerou ve smeru kladneho pohledoveho vektoru
	 * 
	 * @param speed
	 *            rychlost pohybu
	 */
	public void forward(double speed) {
		pos = pos.add(new Vec3D(
				(double) (Math.cos(azimuth) * Math.cos(zenith)), (double) (Math
						.sin(azimuth) * Math.cos(zenith)), (double) Math
						.sin(zenith)).mul(speed));
		computeMatrix();
	}

	/**
	 * pohyb kamerou ve smeru kolmem k pohledovemu vektoru vlevo
	 * 
	 * @param speed
	 *            rychlost pohybu
	 */
	public void left(double speed) {
		right((-1) * speed);
	}

	/**
	 * pohyb kamerou ve smeru kolmem k pohledovemu vektoru vpravo
	 * 
	 * @param speed
	 *            rychlost pohybu
	 */
	public void right(double speed) {
		pos = pos.add(new Vec3D((double) Math.cos(azimuth - Math.PI / 2),
				(double) Math.sin(azimuth - Math.PI / 2), 0.0f).mul(speed));
		computeMatrix();

	}

	/**
	 * pohyb kamerou v svislem smeru dolu
	 * 
	 * @param speed
	 *            rychlost pohybu
	 */
	public void down(double speed) {
		pos = pos.add(new Vec3D(0, 0, -speed));
		computeMatrix();
	}

	/**
	 * pohyb kamerou v svislem smeru nahoru
	 * 
	 * @param speed
	 *            rychlost pohybu
	 */
	public void up(double speed) {
		pos = pos.add(new Vec3D(0, 0, speed));
		computeMatrix();
	}

	/**
	 * pohyb kamerou ve smeru vektoru
	 * 
	 * @param dir
	 *            vektor smeru pohybu
	 */
	public void move(Vec3D dir) {
		pos = pos.add(dir);
		computeMatrix();
	}

	/**
	 * nastaveni pozice kamery
	 * 
	 * @param apos
	 *            nova pozice kamery
	 */
	public void setPosition(Vec3D apos) {
		pos = new Vec3D(apos);
		computeMatrix();
	}

	/**
	 * vraci informaci o nastaveni kamery
	 * 
	 * @return nastaveni kamery 1st/3rd osoba
	 */
	public boolean getFirstPerson() {
		return firstPerson;
	}

	/**
	 * nastaveni kamery
	 * 
	 * @param fp
	 *            nastaveni kamery true->1st, false->3rd osoba
	 */
	public void setFirstPerson(boolean fp) {
		firstPerson = fp;
		computeMatrix();
	}

	/**
	 * vraci pozici oka kamery, zavisi na 1st/3rd osobe
	 * 
	 * @return pozice oka
	 */
	public Vec3D getEye() {
		return eye;
	}

	/**
	 * vraci pohledovy vektor kamery
	 * 
	 * @return pohledovy vektor
	 */
	public Vec3D getEyeVector() {
		return eyeVector;
	}

	/**
	 * vraci pozici kamery
	 * 
	 * @return pozice kamery
	 */
	public Vec3D getPosition() {
		return pos;
	}

	/**
	 * vraci pohledovou matici aktualniho nastaveni kamery
	 * 
	 * @return pohledova matice
	 */
	public Mat4 getViewMatrix() {
		return view;
	}

	/**
	 * vraci azimut v radianech aktualniho nastaveni kamery
	 * 
	 * @return azimut
	 */
	public double getAzimuth() {
		return azimuth;
	}

	/**
	 * vraci zenit v radianech aktualniho nastaveni kamery
	 * 
	 * @return zenit
	 */
	public double getZenith() {
		return zenith;
	}
}
