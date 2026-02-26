import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;

public class PixelBlaster extends JPanel implements Runnable {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    // The "Magic" Ingredients
    private final BufferedImage canvas;
    private final int[] pixels;
    private final int[] stars;
    private final int NUM_STARS = 1000;

    public PixelBlaster() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // 1. Create a direct-access Buffer
        canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) canvas.getRaster().getDataBuffer()).getData();

        // Initialize star positions (x, y, speed)
        stars = new int[NUM_STARS * 3];
        for (int i = 0; i < stars.length; i += 3) {
            stars[i] = (int) (Math.random() * WIDTH);     // x
            stars[i + 1] = (int) (Math.random() * HEIGHT); // y
            stars[i + 2] = (int) (Math.random() * 5) + 1;  // speed
        }
    }

    @Override
    public void run() {
        while (true) {
            update();
            render();

            // Draw to screen and sync
            repaint();
            try { Thread.sleep(16); } catch (InterruptedException e) {}
        }
    }

    private void update() {
        // Clear the screen (Fastest way to wipe an array)
        Arrays.fill(pixels, 0);

        for (int i = 0; i < stars.length; i += 3) {
            // Update Y position
            stars[i + 1] += stars[i + 2];
            if (stars[i + 1] >= HEIGHT) stars[i + 1] = 0;

            // BLAST the pixel into the array
            // Math: (y * width) + x
            int index = (stars[i + 1] * WIDTH) + stars[i];

            // Set color (0xFFFFFF is white)
            if (index >= 0 && index < pixels.length) {
                pixels[index] = 0xFFFFFF;
            }
        }
    }

    private void render() {
        // In a complex engine, you'd do post-processing here
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 2. Blast the entire image to the GPU in one call
        g.drawImage(canvas, 0, 0, getWidth(), getHeight(), null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tight Loop Pixel Blasting");
        PixelBlaster blaster = new PixelBlaster();
        frame.add(blaster);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Thread(blaster).start();
    }
}