package cz.uhk.fim.kikm.pgrf2.grapher.math.function3d;

import cz.uhk.fim.kikm.pgrf2.grapher.math.utils.MathUtils;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * FunctionParser class is used for converting String representation of a 3-D function rule into an object of
 * Function3D class. Parsing is implemented using exp4j lightweight library which contains
 * Shunting yard algorithm based expression parser.
 * Created by Jakub Kol on 30.3.16.
 */
public final class FunctionParser {
    /**
     * Class instantiation is not permitted.
     */
    private FunctionParser() {
    }

    /**
     * Converts the function defined by following arguments into an object of Function3D class.
     * @param functionRule Standard 3-D function rule. Letters 'x' and 'y' are used as function variables.
     * @param xMin lower bound of the X-coordinate range.
     * @param xMax upper bound of the X-coordinate range.
     * @param yMin lower bound of the Y-coordinate range.
     * @param yMax upper bound of the Y-coordinate range.
     * @param xPointsCount number of points in the X-coordinate range.
     * @param yPointsCount number of points in the Y-coordinate range.
     * @return Function3D representation of the function defined by functionRule and method's arguments.
     * @throws IllegalArgumentException throws IAE exception when one or more arguments are non-numeric.
     */
    public static Function3D parseExpression(String functionRule, float xMin, float xMax, float yMin, float yMax, int xPointsCount, int yPointsCount) throws IllegalArgumentException {
        float[] domainX = MathUtils.linspace(xMin, xMax, xPointsCount);
        float[] domainY = MathUtils.linspace(yMin, yMax, yPointsCount);
        float[][] codomain = new float[domainX.length][domainY.length];
        Expression expr = new ExpressionBuilder(functionRule).variables("x", "y").build();
        for(int i = 0; i < domainX.length; i++) {
            for (int j = 0; j < domainY.length; j++) {
                expr.setVariable("x", domainX[i]).setVariable("y", domainY[j]);
                codomain[i][j] = (float)expr.evaluate();
            }
        }
        return new Function3D(functionRule, domainX, domainY, codomain);
    }
}
