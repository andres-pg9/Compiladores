import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GeneradorPostfija {

    private final List<Token> infija;
    private final Stack<Token> stack;
    private final List<Token> postfija;

    public GeneradorPostfija(List<Token> infija) {
        this.infija = infija;
        this.stack = new Stack<>();
        this.postfija = new ArrayList<>();
    }

    public List<Token> convertir() {
        boolean controlStructure = false;
        Stack<Token> stackControlStructure = new Stack<>();

        for (int i = 0; i < infija.size(); i++) {
            Token t = infija.get(i);

            if (t.classTipo() == TipoToken.EOF) {
                break;
            }

            if (t.esPalabraReservada()) {
                postfija.add(t);
                if (t.isControlStructure()) {
                    controlStructure = true;
                    stackControlStructure.push(t);
                }
            }
            else if (t.esOperando()) {
                postfija.add(t);
            }
            else if (t.classTipo() == TipoToken.PAR_IZQ) {
                stack.push(t);
            }
            else if (t.classTipo() == TipoToken.PAR_DER) {
                while (!stack.isEmpty() && stack.peek().classTipo() != TipoToken.PAR_IZQ) {
                    Token temp = stack.pop();
                    postfija.add(temp);
                }
                if (!stack.isEmpty()) {
                    if (stack.peek().classTipo() == TipoToken.PAR_IZQ) {
                        stack.pop();
                    }
                }
                if (controlStructure && infija.get(i + 1).classTipo() == TipoToken.LLA_IZQ) {
                    postfija.add(new Token(TipoToken.PUN_COMA, ";"));
                }
            }
            else if (t.esOperador()) {
                while (!stack.isEmpty() && stack.peek().greaterEqualPrecedence(t)) {
                    Token temp = stack.pop();
                    postfija.add(temp);
                }
                stack.push(t);
            }
            else if (t.classTipo() == TipoToken.PUN_COMA) {
                while (!stack.isEmpty() && stack.peek().classTipo() != TipoToken.LLA_IZQ) {
                    Token temp = stack.pop();
                    postfija.add(temp);
                }
                postfija.add(t);
            }
            else if (t.classTipo() == TipoToken.LLA_IZQ) {
                // Se mete a la pila, tal como el parentesis. Este paso
                // pudiera omitirse, sólo hay que tener cuidado en el manejo
                // del "}".
                stack.push(t);
            }
            else if (t.classTipo() == TipoToken.LLA_DER && controlStructure) {

                // Primero verificar si hay un else:
                if (infija.get(i + 1).classTipo() == TipoToken.ELSE) {
                    // Sacar el "{" de la pila
                    stack.pop();
                }
                else {
                    // En este punto, en la pila sólo hay un token: "{"
                    // El cual se extrae y se añade un ";" a cadena postfija,
                    // El cual servirá para indicar que se finaliza la estructura
                    // de control.
                    stack.pop();
                    postfija.add(new Token(TipoToken.PUN_COMA, ";"));

                    // Se extrae de la pila de estrucuras de control, el elemento en el tope
                    Token aux = stackControlStructure.pop();

                    if (aux.classTipo() == TipoToken.ELSE) {
                        stackControlStructure.pop();
                        postfija.add(new Token(TipoToken.PUN_COMA, ";"));
                    }

                    if (stackControlStructure.isEmpty()) {
                        controlStructure = false;
                    }
                }


            }
        }
        while (!stack.isEmpty()) {
            Token temp = stack.pop();
            postfija.add(temp);
        }

        while (!stackControlStructure.isEmpty()) {
            stackControlStructure.pop();
            postfija.add(new Token(TipoToken.PUN_COMA, ";"));
        }

        return postfija;
    }

}