public class PrintSolver {
    private final Nodo nodo;

    public PrintSolver(Nodo nodo) {
        this.nodo = nodo;
    }

    public void resolver() {
        resolver(nodo);
    }

    private void resolver(Nodo n) {
        SolverAritmetico solverAritmetico;

        if (n.getHijos() == null) {
            throw new RuntimeException("Se esperaba recibir argumentos");
        }

        Nodo child = n.getHijos().get(0);
        solverAritmetico = new SolverAritmetico(child);

        System.out.println(solverAritmetico.resolver());
    }
}