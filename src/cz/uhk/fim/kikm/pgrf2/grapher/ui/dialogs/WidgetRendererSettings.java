package cz.uhk.fim.kikm.pgrf2.grapher.ui.dialogs;

import cz.uhk.fim.kikm.pgrf2.grapher.graphics.renderer.RenderingMode;
import cz.uhk.fim.kikm.pgrf2.grapher.ui.frame.MainFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Widget for displaying settings of the renderer.
 * Created by Jakub Kol on 12.4.16.
 */
public class WidgetRendererSettings extends JDialog {
    private static final String DIALOG_TITLE = "Renderer settings";
    private final MainFrame frame;
    private final int width = 250;
    private final int height = 230;
    private final JRadioButton radbtnModeSolid = new JRadioButton("Solid: ");
    private final JRadioButton radbtnModeWire = new JRadioButton("Wireframe: ");
    private final JRadioButton radbtnModeBoth = new JRadioButton("Both (recommended): ");
    private final JRadioButton radbtnAxesEnab = new JRadioButton("Enabled (recommended): ");
    private final JRadioButton radbtnAxesDisab = new JRadioButton("Disabled: ");
    private final JButton btnClose = new JButton("Close");
    private final JPanel pnlNorth = new JPanel();
    private final JPanel pnlCenter = new JPanel();
    private final JPanel pnlSouth = new JPanel();

    public WidgetRendererSettings(MainFrame frame, boolean isModal) {
        super(frame, isModal);
        this.frame = frame;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle(DIALOG_TITLE);
        this.setResizable(false);
        this.setSize(width, height);
        this.setLocationRelativeTo(frame);
        this.setLayout(new BorderLayout());
        setRadioButtons();
        setButtons();
        setPanels();
        setItemListeners();
        setActionListeners();
        setWindowListeners();
        this.setVisible(true);
    }

    private void setRadioButtons() {
        radbtnModeWire.setSelected(false);
        radbtnModeWire.setHorizontalTextPosition(SwingConstants.LEFT);
        radbtnModeWire.setFocusable(false);
        radbtnModeSolid.setSelected(false);
        radbtnModeSolid.setHorizontalTextPosition(SwingConstants.LEFT);
        radbtnModeSolid.setFocusable(false);
        radbtnModeBoth.setSelected(true);
        radbtnModeBoth.setHorizontalTextPosition(SwingConstants.LEFT);
        radbtnModeBoth.setFocusable(false);
        radbtnModeBoth.setEnabled(false);
        radbtnAxesEnab.setSelected(true);
        radbtnAxesEnab.setHorizontalTextPosition(SwingConstants.LEFT);
        radbtnAxesEnab.setFocusable(false);
        radbtnAxesEnab.setEnabled(false);
        radbtnAxesDisab.setSelected(false);
        radbtnAxesDisab.setHorizontalTextPosition(SwingConstants.LEFT);
        radbtnAxesDisab.setFocusable(false);
    }

    private void setButtons() {
        btnClose.setPreferredSize(new Dimension(95, 30));
    }

    private void setPanels() {
        pnlNorth.setBorder(new TitledBorder(" Rendering mode "));
        pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.Y_AXIS));
        pnlNorth.add(radbtnModeWire);
        pnlNorth.add(radbtnModeSolid);
        pnlNorth.add(radbtnModeBoth);
        this.add(pnlNorth, BorderLayout.NORTH);
        pnlCenter.setBorder(new TitledBorder(" Coordinate axes "));
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.add(radbtnAxesEnab);
        pnlCenter.add(radbtnAxesDisab);
        this.add(pnlCenter, BorderLayout.CENTER);
        pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlSouth.add(btnClose);
        this.add(pnlSouth, BorderLayout.SOUTH);
    }

    private void setItemListeners() {
        radbtnModeWire.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    radbtnModeWire.setEnabled(false);
                    radbtnModeSolid.setSelected(false);
                    radbtnModeBoth.setSelected(false);
                    frame.getRenderer().setRenderingMode(RenderingMode.MODE_WIREFRAME);
                }
                if(e.getStateChange() == ItemEvent.DESELECTED) {
                    radbtnModeWire.setEnabled(true);
                }
            }
        });

        radbtnModeSolid.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    radbtnModeSolid.setEnabled(false);
                    radbtnModeWire.setSelected(false);
                    radbtnModeBoth.setSelected(false);
                    frame.getRenderer().setRenderingMode(RenderingMode.MODE_SOLID);
                }
                if(e.getStateChange() == ItemEvent.DESELECTED) {
                    radbtnModeSolid.setEnabled(true);
                }
            }
        });

        radbtnModeBoth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    radbtnModeBoth.setEnabled(false);
                    radbtnModeWire.setSelected(false);
                    radbtnModeSolid.setSelected(false);
                    frame.getRenderer().setRenderingMode(RenderingMode.MODE_WIREFRAME_SOLID);
                }
                if(e.getStateChange() == ItemEvent.DESELECTED) {
                    radbtnModeBoth.setEnabled(true);
                }
            }
        });

        radbtnAxesEnab.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    radbtnAxesEnab.setEnabled(false);
                    radbtnAxesDisab.setSelected(false);
                    frame.getRenderer().setAxesEnabled(true);
                }
                if(e.getStateChange() == ItemEvent.DESELECTED) {
                    radbtnAxesEnab.setEnabled(true);
                }
            }
        });

        radbtnAxesDisab.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    radbtnAxesDisab.setEnabled(false);
                    radbtnAxesEnab.setSelected(false);
                    frame.getRenderer().setAxesEnabled(false);
                }
                if(e.getStateChange() == ItemEvent.DESELECTED) {
                    radbtnAxesDisab.setEnabled(true);
                }
            }
        });
    }

    private void setActionListeners() {
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WidgetRendererSettings.this.dispose();
            }
        });
    }

    private void setWindowListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                frame.setWidgetRendSettingsActive(true);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame.setWidgetRendSettingsActive(false);
            }
        });
    }
}
