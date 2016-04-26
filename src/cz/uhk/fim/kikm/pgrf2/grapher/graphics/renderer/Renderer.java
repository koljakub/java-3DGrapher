package cz.uhk.fim.kikm.pgrf2.grapher.graphics.renderer;

import cz.uhk.fim.kikm.pgrf2.grapher.graphics.lslcolor.LSLColor;
import cz.uhk.fim.kikm.pgrf2.grapher.math.function3d.Function3D;
import cz.uhk.fim.kikm.pgrf2.grapher.math.utils.MathUtils;
import transforms.Vec3D;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * Renderer class represents a GPU-accelerated 3-D function renderer.
 * GPU acceleration is implemented using JOGL, Java bindings for OpenGL.
 * Created by Jakub Kol on 7.4.16.
 */
public final class Renderer implements GLEventListener {
    private final GLU glu = new GLU();

    /**
     * Width of the target canvas.
     */
    private double glCanvasWidth;

    /**
     * Height of the target canvas.
     */
    private double glCanvasHeight;

    /**
     * Active rendering mode.
     */
    private RenderingMode renderingMode = RenderingMode.MODE_WIREFRAME_SOLID;

    /**
     * Color palette.
     */
    private final LSLColor[] colors = LSLColor.values();

    /**
     * 3-D Vector holding a position of the camera.
     */
    private Vec3D posVector = new Vec3D(0.0, 0.0, 0.0);

    /**
     * 3-D Vector holding camera's target point.
     */
    private Vec3D targetVector = new Vec3D(0.0, 0.0, 0.0);

    /**
     * 3-D Up vector.
     */
    private Vec3D upVector = new Vec3D(0.0, 0.0, 0.0);

    /**
     * State variable. State changes with every request for rendering a new function.
     */
    private boolean stateChanged = false;

    /**
     * State variable. This variable holds a state of coordinate axes.
     */
    private boolean axesEnabled = true;

    // Function3D class related fields ----------------------
    // See Function3D javadoc for more information ----------
    private Function3D function3D;
    private float[][] grid;
    private float[] domainX;
    private float[] domainY;
    //-------------------------------------------------------

    public Renderer(int glCanvasWidth, int glCanvasHeight) {
        this.glCanvasWidth = glCanvasWidth;
        this.glCanvasHeight = glCanvasHeight;
    }

    /**
     * Setter. Changes the rendering mode.
     * @param renderingMode new rendering mode.
     */
    public void setRenderingMode(RenderingMode renderingMode) {
        this.renderingMode = renderingMode;
        if(function3D != null) {
            stateChanged = true;
        }
    }

    /**
     * Setter. Sets coordinate axes enabled / disabled.
     * @param axesEnabled boolean value which determines the state of coordinate axes.
     */
    public void setAxesEnabled(boolean axesEnabled) {
        this.axesEnabled = axesEnabled;
        if(function3D != null) {
            stateChanged = true;
        }
    }

    /**
     * Getter. Returns the active rendering mode.
     * @return RenderingMode instance which specifies the active rendering mode.
     */
    public RenderingMode getRenderingMode() {
        return renderingMode;
    }

    /**
     * Getter. Returns width of the target canvas.
     * @return width of the target canvas.
     */
    public double getGlCanvasWidth() {
        return glCanvasWidth;
    }

    /**
     * Getter. Returns height of the target canvas.
     * @return height of the target canvas.
     */
    public double getGlCanvasHeight() {
        return glCanvasHeight;
    }

    /**
     * Method for updating coordinates related to the camera.
     * @param posVector position of the camera.
     * @param targetVector position of the camera's target point.
     * @param upVector up vector.
     */
    public void updateCameraCoords(Vec3D posVector, Vec3D targetVector, Vec3D upVector) {
        this.posVector = posVector;
        this.targetVector = targetVector;
        this.upVector = upVector;
    }

    /**
     * Method for 3-D function plotting. Sends a request for plotting a new 3-D function.
     * @param function3D Function3D instance representing a 3-D function.
     */
    public void plotFunction(Function3D function3D) {
        this.function3D = function3D;
        this.domainX = function3D.getDomainX();
        this.domainY = function3D.getDomainY();
        this.grid = function3D.getCodomain();
        stateChanged = true;
    }

    /*
    * --------------------------------------------------------------------------------------------
    * OpenGL -------------------------------------------------------------------------------------
    * All algorithms are implemented in the "immediate mode"(legacy).
    * Performance is optimized via display lists.
    * --------------------------------------------------------------------------------------------
    */
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {}

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glViewport(0, 0, i2, i3);
        glCanvasWidth = i2;
        glCanvasHeight = i3;
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        setupCamera(gl);
        if(stateChanged == true) {
            updateDisplayLists(gl);
            stateChanged = false;
        }
        if(function3D != null) {
            if(axesEnabled) {
                gl.glCallList(1);
            }
            switch(renderingMode) {
                case MODE_WIREFRAME:
                    gl.glCallList(3);
                    break;
                case MODE_SOLID:
                    gl.glCallList(2);
                    break;
                case MODE_WIREFRAME_SOLID:
                    gl.glCallList(3);
                    gl.glCallList(2);
                    break;
            }
        }
    }

    /**
     * Method for updating display lists. Update is needed with every request for rendering a new function.
     * @param gl GL2 instance.
     */
    private void updateDisplayLists(GL2 gl) {
        // List #1 - coordinate axes
        gl.glNewList(1, GL2.GL_COMPILE);
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(0, 0, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(15, 0, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, 15, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, 0, 15);
        gl.glEnd();
        gl.glEndList();

        // List #2 - solid mesh
        gl.glNewList(2, GL2.GL_COMPILE);
        for(int j = 0; j < domainY.length - 1; j++) {
            gl.glEnable(GL2.GL_POLYGON_OFFSET_FILL);
            gl.glPolygonOffset(4f, 4f);
            gl.glBegin(GL2.GL_QUADS);
            for(int i = 0; i < domainY.length - 1; i++) {
                assignColor(gl, grid[i][j + 1]);
                gl.glVertex3f(domainX[i], grid[i][j + 1], domainY[j + 1]);
                assignColor(gl, grid[i][j]);
                gl.glVertex3f(domainX[i], grid[i][j], domainY[j]);
                assignColor(gl, grid[i + 1][j]);
                gl.glVertex3f(domainX[i + 1], grid[i + 1][j], domainY[j]);
                assignColor(gl, grid[i + 1][j + 1]);
                gl.glVertex3f(domainX[i + 1], grid[i + 1][j + 1], domainY[j + 1]);
            }
            gl.glEnd();
        }
        gl.glEndList();

        // List #3 - wireframe mesh
        gl.glNewList(3, GL2.GL_COMPILE);
        for(int j = 0; j < domainY.length - 1; j++) {
            gl.glBegin(GL2.GL_LINE_STRIP);
            for(int i = 0; i < domainY.length - 1; i++) {
                gl.glColor3f(0, 0, 0);
                gl.glVertex3f(domainX[i], grid[i][j + 1], domainY[j + 1]);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3f(domainX[i], grid[i][j], domainY[j]);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3f(domainX[i + 1], grid[i + 1][j], domainY[j]);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3f(domainX[i + 1], grid[i + 1][j + 1], domainY[j + 1]);
            }
            gl.glEnd();
        }
        gl.glBegin(GL2.GL_LINES);
        for(int k = 0; k < domainX.length - 1; k++) {
            gl.glColor3f(0, 0, 0);
            gl.glVertex3f(domainX[k], grid[k][domainY.length - 1], domainY[domainY.length - 1]);
            gl.glColor3f(0, 0, 0);
            gl.glVertex3f(domainX[k + 1], grid[k+1][domainY.length - 1], domainY[domainY.length - 1]);
        }
        gl.glEnd();
        gl.glEndList();
        gl.glDisable(GL2.GL_POLYGON_OFFSET_FILL);
    }

    /**
     * Method for setting up the camera.
     * @param gl GL2 instance.
     */
    private void setupCamera(GL2 gl) {
        double aspectRatio = glCanvasWidth / glCanvasHeight;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45, aspectRatio, 1, 1000.0);
        glu.gluLookAt(posVector.x, posVector.y, posVector.z,
                targetVector.x, targetVector.y, targetVector.z,
                upVector.x, upVector.y, upVector.z
        );
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * Helper method which assigns a color from the color palette to the particular float value.
     * @param gl GL2 instance.
     * @param value float value.
     */
    private void assignColor(GL2 gl, float value) {
        float[] funcValsRange = MathUtils.linspace(function3D.getMax(), function3D.getMin(), 15);
        for (int i = 0; i < funcValsRange.length; i++) {
            if (value >= funcValsRange[i]) {
                gl.glColor3f(colors[i].getX(), colors[i].getY(), colors[i].getZ());
                return;
            }
        }
    }
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
}
