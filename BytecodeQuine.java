import java.io.*;
import java.util.Base64;

public class BytecodeQuine {

    public static void main(String[] args) throws Exception {
        String className = BytecodeQuine.class.getSimpleName() + ".class";

        // Read our own bytecode
        InputStream in = BytecodeQuine.class.getResourceAsStream(className);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int n;
        while ((n = in.read(data)) != -1) {
            buffer.write(data, 0, n);
        }
        in.close();

        String encoded = Base64.getEncoder().encodeToString(buffer.toByteArray());
        System.out.println("Encoded: "+ encoded.toString());
        // Print Java source that embeds this bytecode
        System.out.println("import java.io.*;");
        System.out.println("import java.util.Base64;");
        System.out.println();
        System.out.println("public class BytecodeQuine {");
        System.out.println("    public static void main(String[] args) throws Exception {");
        System.out.println("        String bytecode = \"" + encoded + "\";");
        System.out.println("        byte[] bytes = Base64.getDecoder().decode(bytecode);");
        System.out.println("        System.out.write(bytes);");
        System.out.println("    }");
        System.out.println("}");
    }
}
