package cz.uhk.fim.kikm.pgrf2.grapher.ui.menus;

import cz.uhk.fim.kikm.pgrf2.grapher.ui.dialogs.DialogAbout;
import cz.uhk.fim.kikm.pgrf2.grapher.ui.dialogs.WidgetRendererSettings;
import cz.uhk.fim.kikm.pgrf2.grapher.ui.dialogs.WidgetFunctionSettings;
import cz.uhk.fim.kikm.pgrf2.grapher.ui.frame.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main menu.
 * Created by Jakub Kol on 8.4.16.
 */
public final class MainMenu extends JMenu {
    private final MainFrame frame;
    private JMenu submenuWidgets;
    private JMenuItem itemPlotSettings;
    private JMenuItem itemFunctionInfo;
    private JMenuItem itemRendSettings;
    private JMenuItem itemAbout;
    private JMenuItem itemExit;
    private ImageIcon iconSettings = new ImageIcon(MainMenu.class.getResource("/resources/icons/settings.png"));
    private ImageIcon iconAbout = new ImageIcon(MainMenu.class.getResource("/resources/icons/about.png"));
    private ImageIcon iconExit = new ImageIcon(MainMenu.class.getResource("/resources/icons/exit_small.png"));
    private ImageIcon iconExitDialog = new ImageIcon(MainMenu.class.getResource("/resources/icons/exit_medium.png"));
    private ImageIcon iconFuncDetails = new ImageIcon(MainMenu.class.getResource("/resources/icons/info.png"));

    public MainMenu(MainFrame frame) {
        super("Main Menu");
        this.frame = frame;
        setMainMenu();
        setSubmenu();
        setActionListeners();
    }

    private void setMainMenu() {
        submenuWidgets = new JMenu("Widgets");
        itemAbout = new JMenuItem("About", iconAbout);
        itemExit = new JMenuItem("Exit", iconExit);
        this.add(submenuWidgets);
        this.add(itemAbout);
        this.add(itemExit);
    }

    private void setSubmenu() {
        itemPlotSettings = new JMenuItem("Function settings", iconSettings);
        itemFunctionInfo = new JMenuItem("Function details", iconFuncDetails);
        itemRendSettings = new JMenuItem("Renderer settings", iconSettings);
        JMenuItem[] subMenuWidgetsItems = {itemPlotSettings, itemFunctionInfo, itemRendSettings};
        for(JMenuItem menuItem : subMenuWidgetsItems) {
            submenuWidgets.add(menuItem);
        }
    }

    private void setActionListeners() {
        itemPlotSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!frame.isWidgetFuncSettingsActive()) {
                    new WidgetFunctionSettings(frame, false, frame.getRenderer());
                }
            }
        });

        itemFunctionInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!frame.isWidgetFuncInfoActive()) {
                    frame.initWidgetFuncInfo();
                }
            }
        });

        itemRendSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!frame.isWidgetRendSettingsActive()) {
                    new WidgetRendererSettings(frame, false);
                }
            }
        });

        itemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DialogAbout(frame, true);
            }
        });

        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int decision = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, 0, iconExitDialog);
                if(decision == 0) {
                    frame.terminate();
                }
            }
        });
    }
}
