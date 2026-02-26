import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class HexGrid extends JPanel {
    private final int side = 30; // Length of one side
    private final double h = side * Math.sin(Math.PI / 3); // Half height

    public HexGrid() {
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GREEN); // Classic strategy game green

        for (int r = 0; r < 8; r++) {      // Rows
            for (int c = 0; c < 10; c++) { // Columns
                double x = 50 + c * side * 1.5;
                double y = 50 + r * 2 * h;

                // The "Line 130" logic: Offset every odd column
                if (c % 2 != 0) {
                    y += h;
                }
                drawHex(g2, x, y);
            }
        }
    }

    private void drawHex(Graphics2D g2, double x, double y) {
        Path2D hex = new Path2D.Double();
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI / 3 * i;
            double px = x + side * Math.cos(angle);
            double py = y + side * Math.sin(angle);
            if (i == 0) hex.moveTo(px, py);
            else hex.lineTo(px, py);
        }
        hex.closePath();
        g2.draw(hex);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Program 3.1: HexGrid");
        f.add(new HexGrid());
        f.setSize(600, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
