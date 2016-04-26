package cz.uhk.fim.kikm.pgrf2.grapher.ui.dialogs;

import cz.uhk.fim.kikm.pgrf2.grapher.graphics.renderer.Renderer;
import cz.uhk.fim.kikm.pgrf2.grapher.math.function3d.Function3D;
import cz.uhk.fim.kikm.pgrf2.grapher.math.function3d.FunctionParser;
import cz.uhk.fim.kikm.pgrf2.grapher.ui.frame.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Widget for displaying settings of the 3-D function.
 * Created by Jakub Kol on 8.4.16.
 */
public final class WidgetFunctionSettings extends JDialog {
    private static final String DIALOG_TITLE = "Function settings";
    private int width = 520;
    private int height = 260;
    private final MainFrame frame;
    private final Renderer renderer;
    private final JLabel lblFunction = new JLabel("f(x, y):");
    private final JLabel lblXMin = new JLabel("X min:", SwingConstants.RIGHT);
    private final JLabel lblXMax = new JLabel("X max:", SwingConstants.RIGHT);
    private final JLabel lblYMin = new JLabel("Y min:", SwingConstants.RIGHT);
    private final JLabel lblYMax = new JLabel("Y max:", SwingConstants.RIGHT);
    private final JLabel lblXPnts = new JLabel("X points:", SwingConstants.RIGHT);
    private final JLabel lblYPnts = new JLabel("Y points:", SwingConstants.RIGHT);
    private final JTextField txtfldFunction = new JTextField();
    private final JTextField txtfldXMin = new JTextField();
    private final JTextField txtfldXMax = new JTextField();
    private final JTextField txtfldYMin = new JTextField();
    private final JTextField txtfldYMax = new JTextField();
    private final JTextField txtfldXPnts = new JTextField();
    private final JTextField txtfldYPnts = new JTextField();
    private final JTextField[] txtFields = {txtfldFunction, txtfldXMin, txtfldXMax, txtfldYMin, txtfldYMax, txtfldXPnts, txtfldYPnts};
    private final JButton btnPlot = new JButton("Plot");
    private final JButton btnClear = new JButton("Clear");
    private final JButton btnClose = new JButton("Close");
    private final JPanel pnlTop = new JPanel();
    private final JPanel pnlCenter = new JPanel();
    private final JPanel pnlBottom = new JPanel();
    private final ImageIcon iconAttention = new ImageIcon(WidgetFunctionSettings.class.getResource("/resources/icons/error.png"));

    public WidgetFunctionSettings(MainFrame frame, boolean isModal, Renderer renderer) {
        super(frame, isModal);
        this.frame = frame;
        this.renderer = renderer;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle(DIALOG_TITLE);
        this.setResizable(false);
        this.setSize(width, height);
        this.setLayout(new BorderLayout());
        setTextFields();
        setButtons();
        setPanels();
        setActionListeners();
        setWindowListeners();
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
    }

    private void setTextFields() {
        txtfldFunction.setPreferredSize(new Dimension(350,35));
        txtfldFunction.setBorder(BorderFactory.createEmptyBorder());
        for(JTextField txtFld : txtFields) {
            txtFld.setBorder(BorderFactory.createEmptyBorder());
        }
    }

    private void setButtons() {
        btnPlot.setPreferredSize(new Dimension(95, 30));
        SwingUtilities.getRootPane(this).setDefaultButton(btnPlot);
        btnClear.setPreferredSize(new Dimension(95, 30));
        btnClose.setPreferredSize(new Dimension(95, 30));
    }

    private void setPanels() {
        pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlTop.setBorder(new EmptyBorder(15, 25, 15, 25));
        pnlTop.setBackground(new Color(217, 217, 217));
        pnlTop.add(lblFunction);
        pnlTop.add(txtfldFunction);
        this.add(pnlTop, BorderLayout.NORTH);
        pnlCenter.setLayout(new GridLayout(2, 6, 15, 10));
        pnlCenter.setBorder(new EmptyBorder(15, 25, 15, 25));
        pnlCenter.setBackground(new Color(230, 230, 230));
        pnlCenter.add(lblXMin);
        pnlCenter.add(txtfldXMin);
        pnlCenter.add(lblXMax);
        pnlCenter.add(txtfldXMax);
        pnlCenter.add(lblXPnts);
        pnlCenter.add(txtfldXPnts);
        pnlCenter.add(lblYMin);
        pnlCenter.add(txtfldYMin);
        pnlCenter.add(lblYMax);
        pnlCenter.add(txtfldYMax);
        pnlCenter.add(lblYPnts);
        pnlCenter.add(txtfldYPnts);
        this.add(pnlCenter, BorderLayout.CENTER);
        pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setBorder(new EmptyBorder(15, 25, 15, 25));
        pnlBottom.setBackground(new Color(242, 242, 242));
        pnlBottom.add(btnPlot);
        pnlBottom.add(btnClear);
        pnlBottom.add(btnClose);
        this.add(pnlBottom, BorderLayout.SOUTH);
    }

    private void setActionListeners() {
        btnPlot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Function3D function3D;
                try {
                    float xMin = Float.parseFloat(txtfldXMin.getText());
                    float xMax = Float.parseFloat(txtfldXMax.getText());
                    int xPointsCount = Integer.parseInt(txtfldXPnts.getText());
                    float yMin = Float.parseFloat(txtfldYMin.getText());
                    float yMax = Float.parseFloat(txtfldYMax.getText());
                    int yPointsCount = Integer.parseInt(txtfldYPnts.getText());
                    if(xPointsCount <= 0 || yPointsCount <= 0) {
                        JOptionPane.showMessageDialog(WidgetFunctionSettings.this, "Unable to plot the function - illegal function parameter(s).\nNumber of points on X and Y axis" +
                                " must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE, iconAttention);
                        return;
                    }
                    function3D = FunctionParser.parseExpression(txtfldFunction.getText(), xMin, xMax, yMin, yMax, xPointsCount, yPointsCount);
                } catch(NumberFormatException e1) {
                    JOptionPane.showMessageDialog(WidgetFunctionSettings.this, "Unable to plot the function - illegal function parameter(s)." +
                            "\nOnly numeric values are allowed.", "Error", JOptionPane.ERROR_MESSAGE, iconAttention);
                    return;
                } catch(IllegalArgumentException e2) {
                    JOptionPane.showMessageDialog(WidgetFunctionSettings.this, "Unable to plot the function - illegal function rule." +
                            "\nPlease correct it before plotting again.", "Error", JOptionPane.ERROR_MESSAGE, iconAttention);
                    return;
                }
                renderer.plotFunction(function3D);
                frame.refreshWidgetFuncInfo(function3D);
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int decision = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear all current function settings?", "Clear function settings", JOptionPane.YES_NO_OPTION, 0, iconAttention);
                if(decision == 0) {
                    for(JTextField txtFld : txtFields) {
                        txtFld.setText("");
                    }
                }
            }
        });

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WidgetFunctionSettings.this.dispose();
            }
        });
    }

    private void setWindowListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                frame.setWidgetFuncSettingsActive(true);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame.setWidgetFuncSettingsActive(false);
            }
        });
    }
}
