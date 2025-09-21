import javax.swing.*;
import java.awt.*;

public class SpielerPanel extends JPanel {
    private final JLabel punkteLabel;
    private final JLabel zugLabel;

    public SpielerPanel(String name) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 28));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        punkteLabel = new JLabel("0");
        punkteLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        punkteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        zugLabel = new JLabel("Your Turn");
        zugLabel.setFont(new Font("Arial", Font.BOLD, 20));
        zugLabel.setForeground(new Color(0,180,0));
        zugLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        zugLabel.setVisible(false);

        add(nameLabel);
        add(Box.createVerticalStrut(10));
        add(punkteLabel);
        add(Box.createVerticalStrut(10));
        add(zugLabel);
    }

    public void setPunkte(int s) { punkteLabel.setText(String.valueOf(s)); }
    public void setZug(boolean on) { zugLabel.setVisible(on); }
}
