import java.util.List;

public class IfSolver {
    private final Nodo nodo;

    public IfSolver(Nodo nodo) {
        this.nodo = nodo;
    }

    public void resolver() {
        resolver(nodo);
    }

    private void resolver(Nodo n) {
        SolverAritmetico solverAritmetico;
        Arbol arbol;

        List<Nodo> children = n.getHijos();
        Nodo condition = children.get(0);

        Nodo parentIfBody = new Nodo(new Token(TipoToken.PUN_COMA, ";"));
        Nodo parentElseBody = new Nodo(new Token(TipoToken.PUN_COMA, ";"));

        if (children.get(children.size() - 1).getValue().classTipo() == TipoToken.ELSE) {
            parentElseBody.insertarHijos(children.get(children.size() - 1).getHijos());
        }
        else {
            parentElseBody = null;
        }

        parentIfBody.insertarHijos(children.subList(1, children.size()));

        solverAritmetico = new SolverAritmetico(condition);
        Object conditionResult = solverAritmetico.resolver();

        if (conditionResult instanceof Boolean) {
            if ((Boolean) conditionResult) {
                arbol = new Arbol(parentIfBody);
                arbol.recorrer();
            }
            else if (parentElseBody != null) {
                arbol = new Arbol(parentElseBody);
                arbol.recorrer();
            }
        }
        else {
            throw new RuntimeException("La condicion del IF debe ser logica");
        }

    }
}