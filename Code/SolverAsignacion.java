import java.util.List;

public class SolverAsignacion {
    private Nodo nodo;

    public SolverAsignacion(Nodo nodo) {
        this.nodo = nodo;
    }

    public void resolver() {
        resolver(nodo);
    }

    private void resolver(Nodo n) {
        TablaSimbolos tablaSimbolos = TablaSimbolos.indexTabla();
        SolverAritmetico solverAritmetico;
        Arbol arbol;

        List<Nodo> children = n.getHijos();
        Nodo variable = children.get(0);
        Nodo value = children.get(1);

        solverAritmetico = new SolverAritmetico(value);
        Object valueResult = solverAritmetico.resolver();

        tablaSimbolos.asignar(variable.getValue().classLexema(), valueResult);

    }
}