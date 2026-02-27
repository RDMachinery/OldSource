import javax.sound.sampled.*;
import java.nio.ByteBuffer;

public class PWMSynthesizer {

    private static final int SAMPLE_RATE = 44100;

    public static void main(String[] args) throws LineUnavailableException {
        // Audio Format: 44.1kHz, 16-bit, Mono, Signed, Little Endian
        AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, false);
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        
        line.open(format);
        line.start();

        System.out.println("Playing PWM Synth (440Hz)... Press Stop in your IDE to end.");

        double frequency = 440.0; // A4 Note
        double dutyCycle = 0.15;  // 15% width - gives it a sharp, synth-wave character
        double volume = 0.5;      // 50% volume to avoid clipping

        byte[] buffer = new byte[256];
        double phase = 0;

        // The Synthesis Loop
        while (true) {
            for (int i = 0; i < buffer.length / 2; i++) {
                // Calculate if the current phase is within the "High" part of the duty cycle
                short sampleValue;
                if (phase < dutyCycle) {
                    sampleValue = (short) (Short.MAX_VALUE * volume);
                } else {
                    sampleValue = (short) (Short.MIN_VALUE * volume);
                }

                // Write 16-bit sample to buffer (Little Endian)
                buffer[2 * i] = (byte) (sampleValue & 0xFF);
                buffer[2 * i + 1] = (byte) ((sampleValue >> 8) & 0xFF);

                // Advance phase based on frequency
                phase += frequency / SAMPLE_RATE;
                if (phase > 1.0) phase -= 1.0; // Wrap phase
            }
            // Push to audio hardware
            line.write(buffer, 0, buffer.length);
        }
    }
}
