/**
 * This class implements a simple Java class boilerplate code generator. The idea
 * is as follows: You enter your requirements into the system using as many nouns and verbs
 * as you can to describe what must be built. The program scans your text, and iterates
 * through each word. For each word, it wants to know: Is this a noun, or a verb?
 *
 * Once it has got the data from you it then generates a single class whose private fields
 * are the nouns and whose verbs are the methods. A default constructor and main is also generated.
 *
 * @author mariogianota <mariogianota@protonmail.com>
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class RequirementsToBoilerplate {

    private List<String> nouns = new ArrayList<String>();
    private List<String> verbs = new ArrayList<String>();

    private String requirements;

    private StringBuffer code = new StringBuffer();

    private String programName;

    public RequirementsToBoilerplate() {

    }

    public void enterRequirements() throws IOException {
        System.out.println("Enter filename containing your requirements: that uses as many nouns and verbs as possible to describe the system.");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.next();
        File f = new File(filename);
        java.io.FileInputStream fin = new java.io.FileInputStream(f);
        byte[] bytes = fin.readAllBytes();
        String s = new String(bytes);
        this.requirements = s;
        System.out.println("Requirements read in successfully.");
        embedRequirements();
    }
    private void embedRequirements() {
        Scanner s = new Scanner(code.toString());
        code.append("/**\n");
        while(s.hasNext()) {
            String line = s.nextLine();
            line += "\n";
            code.append(line);
        }
        code.append("\n*/\n");
    }
    public void obtainNounsAndVerbs() {
        Scanner scanner = new Scanner(requirements);
        while( scanner.hasNext() ) {
            String word = scanner.next();
       Loop:
       {
           System.out.println("Is the word \"" + word + "\" a noun, or a verb, or shall I skip it (n/v/s): ");
           Scanner newScanner = new Scanner(System.in);
           String response = newScanner.next();
           if (response.equalsIgnoreCase("n")) {
               nouns.add(word);
           } else if (response.equalsIgnoreCase("v")) {
               verbs.add(word);
           } else if (response.equalsIgnoreCase("s")) {
               continue;
           } else {
               System.out.println("Incorrect response.");
                break Loop;
           }
       }
         }
        System.out.println("Loaded " + nouns.size() + " nouns and " + verbs.size() + " verbs.");
    }
    public void askProgramName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the class: ");
        programName = scanner.next();

    }
    public void generateBoilerplate() {
        code.append("public class " + programName + " {");
        generateFields();
        generateConstructor();
        generateMethods();
        generateMain();
        code.append("}");
    }

    private void generateFields() {
        for(int i=0; i<nouns.size(); i++) {
            code.append("\t" + "private String " +nouns.get(i) + ";\n");
        }
        code.append("\n");
    }
    private void generateConstructor() {
        code.append("public " + programName + "(){\n}\n");
    }
    private void generateMethods() {
        for (int i = 0; i<verbs.size(); i++) {
            code.append("public void " + verbs.get(i) + "(){\n}\n");
        }
    }
    private void generateMain() {
        code.append("public static void main(String[] args) {\n}\n");
    }
    private void saveCode() throws IOException {
        FileOutputStream fout = new FileOutputStream(programName+".java");
        fout.write(code.toString().getBytes());
        fout.close();
        System.out.println(programName+",java written to disk.");
    }
    public static void main(String[] args) {
        RequirementsToBoilerplate rtb = new RequirementsToBoilerplate();
        try {
            rtb.enterRequirements();
        }catch(java.io.IOException ex) {
            System.out.println("Error reading requirements from file: " + ex.getMessage());
            System.exit(-1);
        }
        rtb.askProgramName();
        rtb.obtainNounsAndVerbs();
        rtb.generateBoilerplate();
        try {
            rtb.saveCode();
        }catch(IOException ex) {
            System.out.println("Error saving code to file: " + ex.getMessage());
        }
    }
}
