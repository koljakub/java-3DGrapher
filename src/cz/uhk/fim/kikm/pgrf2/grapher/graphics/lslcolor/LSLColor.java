package cz.uhk.fim.kikm.pgrf2.grapher.graphics.lslcolor;

/**
 * LSL enum contains various colors in LSL format.
 * LSL color is specified as a 3-D vector which consists of 3 float values, ranging from 0.0f to 1.0f, such
 * that [red, green, blue].
 * Example: orange = [1.0f, 0.5f, 0.0f] - 100% red, 50% green, 0% blue
 * Created by Jakub Kol on 9.4.16.
 */
public enum LSLColor {
    RED_1(0.4f,0.0f, 0.0f),
    RED_2(0.6f, 0.0f, 0.0f),
    RED_3(0.8f, 0.2f, 0.0f),
    ORANGE_1(0.8f, 0.4f, 0.0f),
    ORANGE_2(1.0f, 0.6f, 0.0f),
    YELLOW_1(1.0f, 1.0f, 0.0f),
    GREEN_1(0.2f, 1.0f, 0.4f),
    GREEN_2(0.2f, 1.0f, 0.6f),
    TURQUOISE_1(0.2f, 1.0f, 0.8f),
    BLUE_1(0.2f, 0.8f, 0.8f),
    BLUE_2(0.2f, 0.6f, 0.8f),
    BLUE_3(0.2f, 0.4f, 0.8f),
    BLUE_4(0.2f, 0.2f, 0.8f),
    BLUE_5(0.2f, 0.2f, 0.6f),
    BLUE_6(0.2f, 0.0f, 0.4f);

    /**
     * First component of the vector - Red color percentage.
     */
    private float x;

    /**
     * Second component of the vector - Green color percentage.
     */
    private float y;

    /**
     * Third component of the vector - Blue color percentage.
     */
    private float z;

    private LSLColor(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Getter. Returns the percentage of a red color.
     * @return float value which represents the percentage of a red color.
     */
    public float getX() {
        return x;
    }

    /**
     * Getter. Returns the percentage of a green color.
     * @return float value which represents the percentage of a green color.
     */
    public float getY() {
        return y;
    }

    /**
     * Getter. Returns the percentage of a blue color.
     * @return float value which represents the percentage of a blue color.
     */
    public float getZ() {
        return z;
    }
}
