import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case MAS:
                case MENOS:
                case MULT:
                case DIV:
                case MENOR:
                case MEN_IGUAL:
                case MAYOR:
                case MAY_IGUAL:
                case DOB_IGUAL:
                case DIFERENTE:
                case AND:
                case OR:
                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object res = solver.resolver();
                    System.out.println(res);
                    break;

                case VAR:
                    // Crear una variable. Usar tabla de simbolos
                    break;
                case IF:
                    break;
                case PRINT:
                    break;;
                case IGUAL: //Asignacion
                    break;
                case FOR:
                    break;
                case WHILE:
                    break;
                default:
                    break;
            }
        }
    }

}

