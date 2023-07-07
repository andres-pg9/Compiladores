import java.util.List;

public class ForSolver {
    private Nodo nodo;

    public ForSolver(Nodo nodo) {
        this.nodo = nodo;
    }

    public void resolver() {
        resolver(nodo);
    }

    private void resolver(Nodo n) {
        SolverAritmetico solverAritmetico;
        Arbol arbol;

        List<Nodo> children = n.getHijos();
        Nodo assignDeclaration = children.get(0);
        Nodo condition = children.get(1);
        Nodo steps = children.get(2);

        Nodo cuerpoPadre = new Nodo(new Token(TipoToken.PUN_COMA, ";"));
        cuerpoPadre.insertarHijos(children.subList(3, children.size()));
        cuerpoPadre.insertarHijo(steps);

        arbol = new Arbol(cuerpoPadre);

        // Es una declaración
        if (assignDeclaration.getValue().classTipo() == TipoToken.VAR) {
            VarSolver varSolver = new VarSolver(assignDeclaration);
            varSolver.resolver();
        }
        // Es una asignación, la variable ya ha sido declarada
        else if (assignDeclaration.getValue().classTipo() == TipoToken.IGUAL) {
            SolverAsignacion solverAsignacion = new SolverAsignacion(assignDeclaration);
            solverAsignacion.resolver();
        }

        solverAritmetico = new SolverAritmetico(condition);
        Object conditionResult = solverAritmetico.resolver();

        if (conditionResult instanceof Boolean) {
            while ((Boolean) conditionResult) {
                arbol.recorrer();
                conditionResult = solverAritmetico.resolver();
            }
        }

    }
}