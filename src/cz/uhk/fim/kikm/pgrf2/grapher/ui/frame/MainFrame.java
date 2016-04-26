package cz.uhk.fim.kikm.pgrf2.grapher.ui.frame;

import com.jogamp.opengl.util.Animator;
import cz.uhk.fim.kikm.pgrf2.grapher.graphics.renderer.Renderer;
import cz.uhk.fim.kikm.pgrf2.grapher.math.function3d.Function3D;
import cz.uhk.fim.kikm.pgrf2.grapher.ui.controllers.CamMouseController;
import cz.uhk.fim.kikm.pgrf2.grapher.ui.dialogs.WidgetFunctionInfo;
import cz.uhk.fim.kikm.pgrf2.grapher.ui.menus.MainMenu;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main UI frame.
 * Created by Jakub Kol on 30.3.16.
 */
public class MainFrame extends JFrame {
    private static final String FRAME_TITLE = "3D-Grapher, software for 3D visualizations.";
    private GLCanvas glCanvas;
    private Animator animator;
    private boolean isWidgetFuncSettingsActive = false;
    private boolean isWidgetFuncInfoActive = false;
    private boolean isWidgetRendSettingsActive = false;
    private WidgetFunctionInfo widgetFuncInfo;
    private Renderer renderer;
    private final ImageIcon iconExitDialog = new ImageIcon(MainMenu.class.getResource("/resources/icons/exit_medium.png"));

    public MainFrame() {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setTitle(this.FRAME_TITLE);
        this.setUndecorated(true);
        initMainMenu();
        setWindowListeners();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        renderer = new Renderer(this.getWidth(), this.getHeight());
        initGLCanvas();
        this.setVisible(true);
    }

    public boolean isWidgetFuncSettingsActive() {
        return isWidgetFuncSettingsActive;
    }

    public void setWidgetFuncSettingsActive(boolean widgetFuncSettingsActive) {
        isWidgetFuncSettingsActive = widgetFuncSettingsActive;
    }

    public boolean isWidgetFuncInfoActive() {
        return isWidgetFuncInfoActive;
    }

    public void setWidgetFuncInfoActive(boolean widgetFuncInfoActive) {
        isWidgetFuncInfoActive = widgetFuncInfoActive;
    }

    public void initWidgetFuncInfo() {
        widgetFuncInfo = new WidgetFunctionInfo(MainFrame.this, false);
    }

    public void refreshWidgetFuncInfo(Function3D function3D) {
        if(isWidgetFuncInfoActive) {
            widgetFuncInfo.refreshData(function3D);
        }
    }

    public boolean isWidgetRendSettingsActive() {
        return isWidgetRendSettingsActive;
    }

    public void setWidgetRendSettingsActive(boolean widgetRendSettingsActive) {
        isWidgetRendSettingsActive = widgetRendSettingsActive;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void terminate() {
        animator.stop();
        this.dispose();
    }

    private void initMainMenu() {
        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.add(new MainMenu(MainFrame.this));
        this.setJMenuBar(mainMenuBar);
    }

    private void initGLCanvas() {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities glc = new GLCapabilities(glp);
        glCanvas = new GLCanvas(glc);
        glCanvas.addGLEventListener(renderer);
        CamMouseController cmc = new CamMouseController(renderer, CamMouseController.DEFAULT_SCR_SPEED);
        glCanvas.addMouseListener(cmc);
        glCanvas.addMouseMotionListener(cmc);
        glCanvas.addMouseWheelListener(cmc);
        this.add(glCanvas);
        animator = new Animator(glCanvas);
        animator.start();
    }

    private void setWindowListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int decision = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, 0, iconExitDialog);
                if(decision == 0) {
                    MainFrame.this.terminate();
                }
            }
        });
    }
}
