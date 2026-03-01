import java.io.FileOutputStream;
import java.io.IOException;

public class RomWriter {

    public static void main(String[] args) throws IOException {
        FileOutputStream fout = new FileOutputStream("rom.bin");
        int[] rom  = new int[32768]; //{ 0xCA, 0xFE, 0xBA, 0xBE};

        for(int i=0; i<rom.length; i++) {
            rom[i] = 0xea;
        }

        int[] program = {
                0xa9, 0xff,         // LDA #$FF
                0x8d, 0x02, 0x60,   // STA $6002

                0xa9, 0x55,         // LDA #$55
                0x8d, 0x00, 0x60,   // STA $6000

                0xa9, 0xaa,         // LDA #$AA
                0x8d, 0x00, 0x60,   // STA 

                0x4c, 0x05, 0x80
        };

        for(int i=0; i<program.length; i++) {
            rom[i] = program[i];
        }

        // Reset vector
        rom[0x7ffc] = 0x00;
        rom[0x7ffd] = 0x80;

        for(int i=0; i<rom.length; i++) {
            fout.write(unsignedByte(rom[i]));
        }

        fout.close();
    }

    private static int unsignedByte(int i) {
        if( i <= 127 )
            return i;
        else
            return i-256;
    }
}
