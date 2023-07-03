import java.util.Arrays;

public class PrintSolver {
    private final Nodo nodo;

    public PrintSolver(Nodo nodo) {
        this.nodo = nodo;
    }

    public void solve() {
        solve(nodo);
    }

    private void solve(Nodo n) {
        SolverAritmetico arithmeticSolver;

        if (n.getHijos() == null) {
            throw new RuntimeException("Se esperaba recibir argumentos");
        }

        Nodo child = n.getHijos().get(0);
        arithmeticSolver = new SolverAritmetico(child);

        System.out.println(arithmeticSolver.solve());
    }
}