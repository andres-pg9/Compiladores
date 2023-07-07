import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GeneradorPostfija {

    private final List<Token> infija;
    private final Stack<Token> pila;
    private final List<Token> postfija;

    public GeneradorPostfija(List<Token> infija) {
        this.infija = infija;
        this.pila = new Stack<>();
        this.postfija = new ArrayList<>();
    }

    public List<Token> convertir() {
        boolean estructuraDeControl = false;
        Stack<Token> pilaEstructurasDeControl = new Stack<>();

        for (int i = 0; i < infija.size(); i++) {
            Token t = infija.get(i);

            if (t.classTipo() == TipoToken.EOF) {
                break;
            }

            if (t.esPalabraReservada()) {
                postfija.add(t);
                if (t.esEstructuraDeControl()) {
                    estructuraDeControl = true;
                    pilaEstructurasDeControl.push(t);
                }
            }
            else if (t.esOperando()) {
                postfija.add(t);
            }
            else if (t.classTipo() == TipoToken.PAR_IZQ) {
                pila.push(t);
            }
            else if (t.classTipo() == TipoToken.PAR_DER) {
                while (!pila.isEmpty() && pila.peek().classTipo() != TipoToken.PAR_IZQ) {
                    Token temp = pila.pop();
                    postfija.add(temp);
                }
                if (!pila.isEmpty()) {
                    if (pila.peek().classTipo() == TipoToken.PAR_IZQ) {
                        pila.pop();
                    }
                }
                if (estructuraDeControl && infija.get(i + 1).classTipo() == TipoToken.LLA_IZQ) {
                    postfija.add(new Token(TipoToken.PUN_COMA, ";"));
                }
            }
            else if (t.esOperador()) {
                while (!pila.isEmpty() && pila.peek().precedenciaMayorIgual(t)) {
                    Token temp = pila.pop();
                    postfija.add(temp);
                }
                pila.push(t);
            }
            else if (t.classTipo() == TipoToken.PUN_COMA) {
                while (!pila.isEmpty() && pila.peek().classTipo() != TipoToken.LLA_IZQ) {
                    Token temp = pila.pop();
                    postfija.add(temp);
                }
                postfija.add(t);
            }
            else if (t.classTipo() == TipoToken.LLA_IZQ) {
                // Se mete a la pila, tal como el parentesis. Este paso
                // pudiera omitirse, sólo hay que tener cuidado en el manejo
                // del "}".
                pila.push(t);
            }
            else if (t.classTipo() == TipoToken.LLA_DER && estructuraDeControl) {

                // Primero verificar si hay un else:
                if (infija.get(i + 1).classTipo() == TipoToken.ELSE) {
                    // Sacar el "{" de la pila
                    pila.pop();
                }
                else {
                    // En este punto, en la pila sólo hay un token: "{"
                    // El cual se extrae y se añade un ";" a cadena postfija,
                    // El cual servirá para indicar que se finaliza la estructura
                    // de control.
                    pila.pop();
                    postfija.add(new Token(TipoToken.PUN_COMA, ";"));

                    // Se extrae de la pila de estrucuras de control, el elemento en el tope
                    Token aux = pilaEstructurasDeControl.pop();

                    if (aux.classTipo() == TipoToken.ELSE) {
                        pilaEstructurasDeControl.pop();
                        postfija.add(new Token(TipoToken.PUN_COMA, ";"));
                    }

                    if (pilaEstructurasDeControl.isEmpty()) {
                        estructuraDeControl = false;
                    }
                }


            }
        }
        while (!pila.isEmpty()) {
            Token temp = pila.pop();
            postfija.add(temp);
        }

        while (!pilaEstructurasDeControl.isEmpty()) {
            pilaEstructurasDeControl.pop();
            postfija.add(new Token(TipoToken.PUN_COMA, ";"));
        }

        return postfija;
    }

}