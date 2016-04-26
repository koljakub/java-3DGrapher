package cz.uhk.fim.kikm.pgrf2.grapher.ui.dialogs;

import cz.uhk.fim.kikm.pgrf2.grapher.math.function3d.Function3D;
import cz.uhk.fim.kikm.pgrf2.grapher.ui.frame.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Widget for displaying parameters of the function on the canvas.
 * Created by Jakub Kol on 11.4.16.
 */
public final class WidgetFunctionInfo extends JDialog {
    private static final String DIALOG_TITLE = "Function details";
    private final int width = 520;
    private final int height = 200;
    private final MainFrame frame;
    private final JLabel lblFunctionRule = new JLabel("Function rule:");
    private final JLabel lblXRange = new JLabel("X range:");
    private final JLabel lblYRange = new JLabel("Y range:");
    private final JLabel lblZRange = new JLabel("Z range:");
    private final JLabel lblRuleValue = new JLabel();
    private final JLabel lblXRangeValue = new JLabel();
    private final JLabel lblYRangeValue = new JLabel();
    private final JLabel lblZRangeValue = new JLabel();
    private final JPanel pnlWidget = new JPanel();
    private final JPanel pnlFunctionRule = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel pnlXRange = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel pnlYRange = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel pnlZRange = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel pnlBtnClose = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private final JButton btnClose = new JButton("Close");

    public WidgetFunctionInfo(MainFrame frame, boolean isModal) {
        super(frame, isModal);
        this.frame = frame;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle(DIALOG_TITLE);
        this.setResizable(false);
        this.setSize(width, height);
        this.setLayout(new BorderLayout());
        initButtons();
        setPanels();
        setActionListeners();
        setWindowListeners();
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
    }

    public void refreshData(Function3D function3D) {
        lblRuleValue.setText("f(x, y) = " + function3D.getFunctionRule());
        lblXRangeValue.setText("< " + String.format("%.2f",function3D.getDomainX()[0])
                + ", " + String.format("%.2f",function3D.getDomainX()[function3D.getDomainX().length-1]) + " >");
        lblYRangeValue.setText("< " + String.format("%.2f",function3D.getDomainY()[0])
                + ", " + String.format("%.2f",function3D.getDomainY()[function3D.getDomainY().length-1]) + " >");
        lblZRangeValue.setText("< " + String.format("%.2f",function3D.getMin())
                + ", " + String.format("%.2f",function3D.getMax()) + " >");
    }

    private void setPanels() {
        pnlWidget.setLayout(new BoxLayout(pnlWidget, BoxLayout.Y_AXIS));
        pnlWidget.setBorder(new EmptyBorder(15, 25, 15, 25));
        this.add(pnlWidget, BorderLayout.CENTER);
        pnlWidget.add(pnlFunctionRule);
        pnlWidget.add(pnlXRange);
        pnlWidget.add(pnlYRange);
        pnlWidget.add(pnlZRange);
        pnlWidget.add(pnlBtnClose);
        pnlWidget.add(pnlBtnClose);
        pnlFunctionRule.add(lblFunctionRule);
        pnlFunctionRule.add(lblRuleValue);
        pnlXRange.add(lblXRange);
        pnlXRange.add(lblXRangeValue);
        pnlYRange.add(lblYRange);
        pnlYRange.add(lblYRangeValue);
        pnlZRange.add(lblZRange);
        pnlZRange.add(lblZRangeValue);
        pnlBtnClose.add(btnClose);
    }

    private void initButtons() {
        btnClose.setPreferredSize(new Dimension(95, 30));
    }

    private void setActionListeners() {
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WidgetFunctionInfo.this.dispose();
            }
        });
    }

    private void setWindowListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                frame.setWidgetFuncInfoActive(true);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame.setWidgetFuncInfoActive(false);
            }
        });
    }
}
