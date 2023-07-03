import java.util.List;

public class SolverAsignacion {
    private Nodo nodo;

    public SolverAsignacion(Nodo nodo) {
        this.nodo = nodo;
    }

    public void solve() {
        solve(nodo);
    }

    private void solve(Nodo n) {
        TablaSimbolos tablaSimbolos = TablaSimbolos.indexTabla();
        SolverAritmetico arithmeticSolver;
        Arbol arbol;

        List<Nodo> children = n.getHijos();
        Nodo variable = children.get(0);
        Nodo value = children.get(1);

        arithmeticSolver = new SolverAritmetico(value);
        Object valueResult = arithmeticSolver.solve();

        tablaSimbolos.asignar(variable.getValue().classLexema(), valueResult);

    }
}