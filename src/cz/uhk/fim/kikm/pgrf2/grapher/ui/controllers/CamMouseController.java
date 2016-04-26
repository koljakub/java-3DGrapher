package cz.uhk.fim.kikm.pgrf2.grapher.ui.controllers;

import cz.uhk.fim.kikm.pgrf2.grapher.graphics.renderer.Renderer;
import transforms.Vec3D;

import java.awt.event.*;

/**
 * Mouse controller for the camera.
 * Created by Jakub Kol on 2/22/16.
 */
public final class CamMouseController implements MouseListener, MouseMotionListener, MouseWheelListener {
    public static final double DEFAULT_SCR_SPEED = 3.0F;
    private final Renderer renderer;
    private double scrollSpeed;
    private int oldX = 0;
    private int oldY = 0;
    private int deltaX = 0;
    private int deltaY = 0;
    private double azimuth = -0.7332428166093358;
    private double zenith = -0.5767767762450013;
    private Vec3D eyeVector = new Vec3D(-0.5610092005498384, -0.5453249884220466, -0.6228076219033658);
    private Vec3D upVector = new Vec3D(-0.3649765197412065, 0.8382247055548379, -0.4051808028955741);
    private Vec3D posVector = new Vec3D(29.35801003315666, 26.43582564036165, 32.9948999741305);
    private Vec3D targetVector = new Vec3D(0.0, 0.0, 0.0);

    public CamMouseController(Renderer renderer, double scrollSpeed) {
        this.renderer = renderer;
        this.scrollSpeed = scrollSpeed;
        targetVector = posVector.add(eyeVector);
        renderer.updateCameraCoords(posVector, targetVector, upVector);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        oldX = e.getX();
        oldY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        deltaX = oldX - e.getX();
        deltaY = oldY - e.getY();
        zenith += (Math.PI * deltaY) / renderer.getGlCanvasHeight();
        azimuth += (Math.PI * deltaX) / renderer.getGlCanvasWidth();
        eyeVector = new Vec3D((Math.sin(azimuth) * Math.cos(zenith)),
                              (Math.sin(zenith)),
                              -Math.cos(azimuth) * Math.cos(zenith)
        );
        upVector = new Vec3D(Math.sin(azimuth) * Math.cos(zenith + Math.PI / 2),
                             Math.sin(zenith + Math.PI / 2),
                            -Math.cos(azimuth) * Math.cos(zenith + Math.PI / 2)
        );
        targetVector = posVector.add(eyeVector);
        renderer.updateCameraCoords(posVector, targetVector, upVector);
        oldX = e.getX();
        oldY = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        posVector = posVector.sub(eyeVector.mul(e.getPreciseWheelRotation()).mul(scrollSpeed));
        targetVector = posVector.add(eyeVector);
        renderer.updateCameraCoords(posVector, targetVector, upVector);
    }

    // Unused methods - GLCanvas unfortunately does not support MouseAdapter.
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}