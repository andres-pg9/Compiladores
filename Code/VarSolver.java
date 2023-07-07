public class VarSolver {
    private final Nodo nodo;

    public VarSolver(Nodo nodo) {
        this.nodo = nodo;
    }

    public void resolver() {
        resolver(nodo);
    }

    private void resolver(Nodo n) {
        TablaSimbolos tablaSimbolos = TablaSimbolos.indexTabla();
        SolverAritmetico solverAritmetico;

        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().size() > 1 ? n.getHijos().get(1) : null;

        solverAritmetico = new SolverAritmetico(der);

        String resultadoIzq = izq.getValue().classLexema();
        Object resultadoDer = der == null ? null : solverAritmetico.resolver();

        tablaSimbolos.asignarVar(resultadoIzq, resultadoDer);

    }
}