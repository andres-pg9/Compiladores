import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static boolean existenErrores = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.exit(64);
        } else if (args.length == 1) {
            ejecutarArchivo(args[0]);
        } else {
            ejecutarPrompt();
        }
    }

    private static void ejecutarArchivo(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ejecutar(new String(bytes));
        if (existenErrores) {
            System.exit(65);
        }
    }

    private static void ejecutarPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print(">>> ");
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            ejecutar(line);
            existenErrores = false;
        }
    }

    private static void ejecutar(String source) {
        Scanner scanner = new Scanner(source); //Scanner
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);  //Parser
        parser.parse();

        GeneradorPostfija gpf = new GeneradorPostfija(tokens);
        List<Token> postfija = gpf.convertir(); //Pasar a notacion postfija

        GeneradorAST gast = new GeneradorAST(postfija);
        Arbol programa = gast.generarAST();//Crear el AST
        programa.recorrer(); //Recorrer con los solvers y la tabla de simbolos

    }

      /*
    El método error se puede usar desde las distintas clases
    para reportar los errores:
    Interprete.error(....);
     */

    public static void error(int numLinea, String message) {
        reportar(numLinea, "", message);
    }

    private static void reportar(int numLinea, String donde, String mensaje) {
        System.err.println(
                "[Linea: " + numLinea + "] Error" + donde + ": " + mensaje);
        existenErrores = true;
    }
}