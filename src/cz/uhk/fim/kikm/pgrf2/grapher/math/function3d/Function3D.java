package cz.uhk.fim.kikm.pgrf2.grapher.math.function3d;

/**
 * Function3D class represents a standard mathematical function of two variables.
 * Such function is defined by a 2-D domain and a corresponding co-domain via
 * the function rule, eg.: f(x, y) = x^2 + y^2.
 * Created by Jakub Kol on 9.4.16.
 */
public final class Function3D {
    /**
     * Field holding String representation of the function rule.
     */
    private final String functionRule;

    /**
     * Domain - X-coordinates.
     * Array of X-coordinates of points from the function's domain.
     */
    private final float[] domainX;

    /**
     * Domain - Y-coordinates.
     * Array of Y-coordinates of points from the function's domain.
     */
    private final float[] domainY;

    /**
     * Co-domain.
     * 2-D array containing values from the function's co-domain.
     */
    private final float[][] codomain;

    /**
     * The lowest value in the co-domain.
     */
    private float min;

    /**
     * The highest value in the co-domain.
     */
    private float max;

    public Function3D(String functionRule, float[] domainX, float[] domainY, float[][] codomain) {
        this.functionRule = functionRule;
        this.domainX = domainX;
        this.domainY = domainY;
        this.codomain = codomain;
        this.min = findMin();
        this.max = findMax();
    }

    /**
     * Getter. Returns X coordinates of points from the domain.
     * @return float[] array containing X coordinates of points from the domain.
     */
    public float[] getDomainX() {
        return domainX;
    }

    /**
     * Getter. Returns Y coordinates of points from the domain.
     * @return float[] array containing Y coordinates of points from the domain.
     */
    public float[] getDomainY() {
        return domainY;
    }

    /**
     * Getter. Returns the co-domain.
     * @return float[][] array containing values from the function's co-domain.
     */
    public float[][] getCodomain() {
        return codomain;
    }

    /**
     * Getter.
     * @return float - the lowest value in the co-domain.
     */
    public float getMin() {
        return min;
    }

    /**
     * Getter.
     * @return float - the highest value in the co-domain.
     */
    public float getMax() {
        return max;
    }

    /**
     * Getter. Returns the function rule.
     * @return String representation of the function rule.
     */
    public String getFunctionRule() {
        return functionRule;
    }

    /**
     * Helper function. Finds the lowest value in the co-domain.
     * Asymptotic complexity: O(N^2).
     * @return the lowest value in the co-domain.
     */
    private float findMin() {
        float tmpMin = codomain[0][0];
        for(int i = 0; i < codomain.length; i++) {
            for(int j = 0; j < codomain[0].length; j++) {
                if(codomain[i][j] < tmpMin) {
                    tmpMin = codomain[i][j];
                }
            }
        }
        return tmpMin;
    }

    /**
     * Helper function. Finds the highest value in the co-domain.
     * * Asymptotic complexity: O(N^2).
     * @return the highest value in the co-domain.
     */
    private float findMax() {
        float tmpMax = codomain[0][0];
        for(int i = 0; i < codomain.length; i++) {
            for(int j = 0; j < codomain[0].length; j++) {
                if(codomain[i][j] > tmpMax) {
                    tmpMax = codomain[i][j];
                }
            }
        }
        return tmpMax;
    }
}
