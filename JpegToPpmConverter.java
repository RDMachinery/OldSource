import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
public class JpegToPpmConverter {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java JpegToPpmConverter <input.jpg> <output.ppm>");
            return;
        }

        JpegToPpmConverter cnv = new JpegToPpmConverter();
        cnv.convertJpegToPPM(args[0], args[1]);
        int[] pixels = null;
        try {
            pixels = cnv.readPPM(args[1]);
        }catch(IOException ex) {
            System.out.println("Fatal error reading PPM file: " + ex.getMessage());
            System.exit(-1);
        }

        // Do something with the pixel array here...

        // Write out the pixel array as a JPEG.

    }
    public void convertJpegToPPM(String filename, String outputFilename) {
        try {
            File inputFile = new File(filename);
            BufferedImage image = ImageIO.read(inputFile);

            if (image == null) {
                throw new IOException("Error: Could not decode JPEG.");
            }

            int width = image.getWidth();
            int height = image.getHeight();

            // Use a BufferedOutputStream to handle raw bytes directly
            try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFilename))) {

                // 1. Write the PPM Header (ASCII)
                String header = String.format("P6\n%d %d\n255\n", width, height);
                out.write(header.getBytes(java.nio.charset.StandardCharsets.US_ASCII));

                // 2. Iterate through pixels and write raw RGB bytes
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int rgb = image.getRGB(x, y);

                        // Extract R, G, B components from the 32-bit int
                        // This bypasses the signed byte promotion issue by
                        // isolating the bits before they are written.
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = (rgb) & 0xFF;

                        out.write(r);
                        out.write(g);
                        out.write(b);
                    }
                }
                out.flush();
                System.out.println("Converted " + filename + " to " + outputFilename);
            }

        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        }


    }
    public int[] readPPM(String filename) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename))) {

            // 1. Parse the ASCII Header
            // We read bytes manually to find the three header lines
            String magic = readLine(bis); // Should be "P6"
            if (!magic.equals("P6")) {
                throw new IOException("Not a binary PPM (P6) file");
            }

            String dims = readLine(bis);
            while (dims.startsWith("#")) dims = readLine(bis); // Skip comments
            String[] parts = dims.split("\\s+");
            int width = Integer.parseInt(parts[0]);
            int height = Integer.parseInt(parts[1]);

            String maxVal = readLine(bis); // Should be "255"

            // 2. Prepare the destination array
            int[] pixels = new int[width * height];

            // 3. Read the Binary Body
            // We read 3 bytes (R, G, B) for every 1 pixel
            for (int i = 0; i < pixels.length; i++) {
                int r = bis.read();
                int g = bis.read();
                int b = bis.read();

                if (r == -1 || g == -1 || b == -1) break;

                // THE FIX: Java's 'read()' returns an int 0-255,
                // but we mask anyway for logical certainty.
                r = r & 0xFF;
                g = g & 0xFF;
                b = b & 0xFF;
                int a = 255; // Opaque alpha

                // Pack into the 32-bit integer array
                pixels[i] = (a << 24) | (r << 16) | (g << 8) | b;
            }

            return pixels;
        }
    }

    // Helper to read ASCII header lines from a byte stream
    private String readLine(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = in.read()) != -1) {
            if (b == '\n') break;
            sb.append((char) b);
        }
        return sb.toString().trim();
    }

}
