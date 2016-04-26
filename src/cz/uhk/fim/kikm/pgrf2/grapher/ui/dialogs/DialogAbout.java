package cz.uhk.fim.kikm.pgrf2.grapher.ui.dialogs;

import cz.uhk.fim.kikm.pgrf2.grapher.ui.frame.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * DialogAbout class represents a dialog with information about the author of this application.
 * Created by Jakub Kol on 8.4.16.
 */
public class DialogAbout extends JDialog {
    private static final String DIALOG_TITLE = "About";
    private final int width = 512;
    private final int height = 430;
    private final JLabel lblText = new JLabel("<html> This application was created as a programming assignment for the Computer Graphics 2 course" +
            " at the Faculty of informatics and management, University of Hradec Kralove. </html>");
    private final JLabel lbltAuthor = new JLabel("Author: Jakub Kol");
    private final JLabel lblStudyProgramme = new JLabel("Study programme: Applied informatics");
    private final JLabel lblContact = new JLabel("Contact: jakub.kol@uhk.cz // jakub.kol@hotmail.com");
    private final JPanel pnlSouth = new JPanel();
    private final JPanel pnlLogo = new PanelLogo();
    private final JPanel pnlCenter = new JPanel();

    public DialogAbout(MainFrame frame, boolean isModal) {
        super(frame, isModal);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle(DIALOG_TITLE);
        this.setResizable(false);
        this.setSize(width, height);
        this.setLocationRelativeTo(frame);
        this.setLayout(new BorderLayout(10, 10));
        setLabels();
        setPanels();
        this.setVisible(true);
    }

    private void setLabels() {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 13);
        lblText.setFont(font);
        lbltAuthor.setFont(font);
        lblStudyProgramme.setFont(font);
        lblContact.setFont(font);
    }

    private void setPanels() {
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.add(Box.createVerticalStrut(15));
        pnlCenter.add(lblText);
        pnlCenter.add(new JSeparator(SwingConstants.VERTICAL));
        pnlCenter.add(lbltAuthor);
        pnlCenter.add(new JSeparator(SwingConstants.VERTICAL));
        pnlCenter.add(lblStudyProgramme);
        pnlCenter.add(new JSeparator(SwingConstants.VERTICAL));
        pnlCenter.add(lblContact);
        pnlCenter.add(Box.createHorizontalStrut(15));
        pnlCenter.setVisible(true);
        this.add(pnlCenter, BorderLayout.CENTER);
        pnlSouth.setLayout(new FlowLayout());
        pnlSouth.setBackground(new Color(217, 217, 217));
        pnlSouth.setPreferredSize(new Dimension(width, height / 4 + 10));
        pnlSouth.setVisible(true);
        this.add(pnlSouth, BorderLayout.SOUTH);
        pnlLogo.setBackground(new Color(217, 217, 217));
        pnlLogo.setPreferredSize(new Dimension(100, height / 4));
        pnlLogo.setVisible(true);
        pnlSouth.add(pnlLogo);
    }

    private class PanelLogo extends JPanel {
        private Image image;

        public PanelLogo() {
            try {
                image = ImageIO.read(DialogAbout.class.getResource("/resources/logo/FIM_Logo.png"));
            } catch (IOException e) {
                // Cannot occur
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    }
}
