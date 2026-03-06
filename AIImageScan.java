import java.util.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class AIImageScan {

    private int width;
    private int height;

    private int[] red;
    private int[] green;
    private int[] blue;
    private int[] alpha;

    public AIImageScan(int size) {
    }


    public void process() {

    }

    /**
     * Loads the RGB and Alpha values from the image into the arrays.
     *
     */
    public void loadRGBValues(String filePath, int size) throws java.io.IOException {
        File file = new File(filePath);
        BufferedImage img = ImageIO.read(file);

        // Resize/Sample the image to fit our vector size
        int[] retina = new int[size];
        int width = img.getWidth();
        int height = img.getHeight();
        this.width = width;
        this.height = height;

        red = new int[(size/3)+1];
        green = new int[(size/3)+1];
        blue = new int[(size/3)+1];
        alpha = new int[(size/3)+1];
        int alphaIndex = 0;
        int redIndex = 0;
        int greenIndex = 0;
        int blueIndex = 0;

        for (int i = 0; i < size; i++) {
            int x = (i * width / size);
            int y = height / 2; // Sample across the middle
            int pixel = img.getRGB(x, y);

// 1. Shift bits to the right to move the channel to the '0-7' position
// 2. Use '& 0xFF' to mask out everything except the 8 bits we want
            try {
                alpha[alphaIndex++] = (pixel >> 24) & 0xFF;
                red[redIndex++] = (pixel >> 16) & 0xFF;
                green[greenIndex++] = (pixel >> 8) & 0xFF;
                blue[blueIndex++] = pixel & 0xFF;
            }catch(ArrayIndexOutOfBoundsException ex) {
                System.out.println("Warning: array index out of bounds. Loaded " + alphaIndex + " values.");
            }
        }
    }
    public static void main(String[] args) {
        AIImageScan scanner = new AIImageScan(4);
        int[] photo = null;

        try {
            scanner.loadRGBValues(args[0], Integer.parseInt(args[1]));
        }catch(java.io.IOException ex) {
            System.out.println("Fatal error obtaining image: " + ex.getMessage());
            System.out.println("System will now exit.");
            System.exit(-1);
        }
        System.out.println("\n--- Processing image ---");
        scanner.process();
    }
}
