import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.time.LocalTime;

public class AnaClock extends JPanel {

    public AnaClock() {
        // Set the "C64" style background
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Enable Antialiasing for smoother lines (unlike the C64!)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get current window dimensions
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 3;

        // Get current system time
        LocalTime now = LocalTime.now();
        double s = now.getSecond();
        double m = now.getMinute();
        double h = now.getHour();

        // 1. Draw Clock Face Points (Line 190-220 in BASIC)
        g2.setColor(Color.WHITE);
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30);
            int x = (int) (centerX + radius * Math.sin(angle));
            int y = (int) (centerY - radius * Math.cos(angle));
            g2.fillOval(x - 2, y - 2, 5, 5);
        }

        // 2. Calculate Hand Angles (Line 150-170 in BASIC)
        double secondAngle = Math.toRadians(s * 6);
        double minuteAngle = Math.toRadians((m + s / 60.0) * 6);
        double hourAngle   = Math.toRadians((h % 12 + m / 60.0) * 30);

        // 3. Draw the Hands
        drawHand(g2, centerX, centerY, secondAngle, radius * 0.9, Color.RED, 1);   // Second
        drawHand(g2, centerX, centerY, minuteAngle, radius * 0.8, Color.WHITE, 3); // Minute
        drawHand(g2, centerX, centerY, hourAngle,   radius * 0.5, Color.CYAN, 5);  // Hour
    }

    private void drawHand(Graphics2D g2, int x, int y, double angle, double length, Color color, int thickness) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        int endX = (int) (x + length * Math.sin(angle));
        int endY = (int) (y - length * Math.cos(angle));
        g2.draw(new Line2D.Double(x, y, endX, endY));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Program 1.1: AnaClock (Java Edition)");
        AnaClock clock = new AnaClock();
        frame.add(clock);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Main Loop: Repaint the clock every second
        while (true) {
            clock.repaint();
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
