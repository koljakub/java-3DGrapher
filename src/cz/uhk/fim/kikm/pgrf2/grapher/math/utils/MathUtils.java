package cz.uhk.fim.kikm.pgrf2.grapher.math.utils;

/**
 * Utility class containing helper functions.
 * Created by Jakub Kol on 8.4.16.
 */
public final class MathUtils {
    /**
     * Class instantiation is not permitted.
     */
    private MathUtils() {
    }

    /**
     * Divides the interval defined by a minimum and a maximum value into N linearly spaced values.
     * The following method is widely used in Matlab - programming language for numerical mathematics.
     * @param min lower bound of the interval.
     * @param max upper bound of the interval.
     * @param points number of points between the lower(incl.) and the upper(incl.) bound.
     * @return float[] array containing N linearly spaced values, where N == points argument.
     */
    public static float[] linspace(float min, float max, int points) {
        float[] d = new float[points];
        for (int i = 0; i < points; i++){
            d[i] = min + i * (max - min) / (points - 1);
        }
        return d;
    }
}
