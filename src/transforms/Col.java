package transforms;

import java.util.Locale;

/**
 * trida pro praci s barvou v modelu RGBA
 * 
 * @author PGRF FIM UHK
 * @version 2016
 */

public class Col {

	public final double r, g, b, a;

	/**
	 * Vytvari barvu RGBA
	 * 
	 */
	public Col() {
		r = g = b = 0.0f;
		a = 1.0f;
	}

	/**
	 * Vytvari barvu RGBA
	 * 
	 * @param c
	 *            barva RGBA
	 */
	public Col(Col c) {
		r = c.r;
		g = c.g;
		b = c.b;
		a = c.a;
	}

	/**
	 * Vytvari barvu RGBA
	 * 
	 * @param p
	 *            bod Point3D
	 */
	public Col(Point3D p) {
		r = p.x;
		g = p.y;
		b = p.z;
		a = p.w;
	}

	/**
	 * Vytvari barvu RGBA
	 * 
	 * @param rgb
	 *            barva RGB
	 */
	public Col(int rgb) {
		a = 1.0f;
		r = ((rgb >> 16) & 0xffL) / 255.0f;
		g = ((rgb >> 8) & 0xffL) / 255.0f;
		b = (rgb & 0xffL) / 255.0f;
	}

	/**
	 * Vytvari barvu RGBA
	 * 
	 * @param argb
	 *            barva RGBA
	 * @param isAlpha
	 *            je alpha kanal
	 */
	public Col(int argb, boolean isAlpha) {
		if (isAlpha)
			a = ((argb >> 24) & 0xffL) / 255.0f;
		else
			a = 1.0f;
		r = ((argb >> 16) & 0xffL) / 255.0f;
		g = ((argb >> 8) & 0xffL) / 255.0f;
		b = (argb & 0xffL) / 255.0f;
	}

	/**
	 * Vytvari barvu RGBA
	 * 
	 * @param r
	 *            barevny kanal R <0;255>
	 * @param g
	 *            barevny kanal G <0;255>
	 * @param b
	 *            barevny kanal B <0;255>
	 */
	public Col(int r, int g, int b) {
		a = 1.0f;
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
	}

	/**
	 * Vytvari barvu RGBA
	 * 
	 * @param r
	 *            barevny kanal R <0;255>
	 * @param g
	 *            barevny kanal G <0;255>
	 * @param b
	 *            barevny kanal B <0;255>
	 * @param a
	 *            barevny kanal A <0;255>
	 */
	public Col(int r, int g, int b, int a) {
		this.a = a / 255.0f;
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
	}

	/**
	 * Vytvari barvu RGBA
	 * 
	 * @param r
	 *            barevny kanal R <0;1>
	 * @param g
	 *            barevny kanal G <0;1>
	 * @param b
	 *            barevny kanal B <0;1>
	 */
	public Col(double r, double g, double b) {
		a = 1.0f;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Vytvari barvu RGBA
	 * 
	 * @param r
	 *            barevny kanal R <0;1>
	 * @param g
	 *            barevny kanal G <0;1>
	 * @param b
	 *            barevny kanal B <0;1>
	 * @param a
	 *            barevny kanal A <0;1>
	 */
	public Col(double r, double g, double b, double a) {
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public double getR() {
		return r;
	}

	public double getG() {
		return g;
	}

	public double getB() {
		return b;
	}

	public double getA() {
		return a;
	}

	/**
	 * Soucet barev bez alpha kanalu, bez saturace
	 * 
	 * @param c
	 *            barva RGB
	 * @return nova instance barvy RGBA
	 */
	public Col addna(Col c) {
		return new Col(r + c.r, g + c.g, b + c.b);
	}

	/**
	 * Nasobeni skalarem bez alpha kanalu, bez saturace
	 * 
	 * @param x
	 *            skalar
	 * @return nova instance barvy RGBA
	 */
	public Col mulna(double x) {
		return new Col(r * x, g * x, b * x);
	}

	/**
	 * Soucet barev vcetne alpha kanalu, neni saturace
	 * 
	 * @param c
	 *            barva RGBA
	 * @return nova instance barvy RGBA
	 */
	public Col add(Col c) {
		return new Col(r + c.r, g + c.g, b + c.b, a + c.a);
	}

	/**
	 * Nasobeni skalarem vcetne alpha kanalu, bez saturace
	 * 
	 * @param x
	 *            skalar
	 * @return nova instance barvy RGBA
	 */
	public Col mul(double x) {
		return new Col(r * x, g * x, b * x, a * x);
	}

	/**
	 * Nasobeni barvou RGBA vcetne alpha kanalu, bez saturace
	 * 
	 * @param c
	 *            barva RGBA
	 * @return nova instance barvy RGBA
	 */
	public Col mul(Col c) {
		return new Col(r * c.r, g * c.g, b * c.b, a * c.a);
	}

	/**
	 * Gama korekce
	 * 
	 * @param gamma
	 *            koeficient korekce
	 * @return nova instance barvy RGBA
	 */
	public Col gamma(double gamma) {
		return new Col(Math.pow(r, gamma), Math.pow(g, gamma), Math.pow(b,
				gamma), a);
	}

	/**
	 * Saturace barvy, orezani na rozsah <0;1>
	 * 
	 * @return nova instance barvy RGBA
	 */
	public Col saturate() {
		return new Col(Math.max(0, Math.min(r, 1)),
				Math.max(0, Math.min(g, 1)), Math.max(0, Math.min(b, 1)), a);
	}

	/**
	 * Vraci barvu v 16 bitovem formatu RGB
	 * 
	 * @return barva ve formatu 0xRGB
	 */
	public int getRGB() {
		return (((int) (r * 255.0f)) << 16) | (((int) (g * 255.0f)) << 8)
				| (int) (b * 255.0f);
	}

	/**
	 * Vraci barvu v 16 bitovem formatu RGB
	 * 
	 * @return barva ve formatu 0xARGB
	 */
	public int getARGB() {
		return (((int) (a * 255.0f)) << 24) | (((int) (r * 255.0f)) << 16)
				| (((int) (g * 255.0f)) << 8) | (int) (b * 255.0f);
	}

	/**
	 * vypis RGBA barvy do stringu
	 * 
	 * @return textovy retezec
	 */
	@Override
	public String toString() {
		return String
				.format(Locale.US, "(%4.1f,%4.1f,%4.1f,%4.1f)", r, g, b, a);
	}

	/**
	 * formatovany vypis RGBA barvy do stringu
	 * 
	 * @param format
	 *            String jedne slozky
	 * @return textovy retezec
	 */
	public String toString(String format) {
		return String.format(Locale.US, "(" + format + "," + format + ","
				+ format + "," + format + ")", r, g, b, a);
	}
}
