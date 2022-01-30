package at.schalktech.badlisp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Invalid number of Arguments!");
            System.exit(1);
        }

	    Path inputPath = Path.of(args[0]);

        String input = "";
	    try {
            input = Files.readString(inputPath);
        } catch (IOException exception) {
            System.err.println("Error reading input File!");
            System.exit(1);
        }

	    Lexer lexer = new Lexer(input);
        while(lexer.hasNext()) {
            Token token = lexer.next();
            System.out.println(token);
        }
    }
}
