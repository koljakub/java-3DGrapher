package cz.uhk.fim.kikm.pgrf2.grapher.main;

import cz.uhk.fim.kikm.pgrf2.grapher.ui.frame.MainFrame;

import javax.media.opengl.GLProfile;
import javax.swing.*;

/**
 * Main class used for app initialization.
 * Created by Jakub Kol on 30.3.16.
 */
public final class Main {
    /**
    * Class instantiation is not permitted.
    */
    private Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GLProfile.initSingleton();
                new MainFrame();
            }
        });
    }
}
