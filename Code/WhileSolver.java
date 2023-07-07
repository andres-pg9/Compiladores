import java.util.List;

public class WhileSolver {
    private final Nodo nodo;

    public WhileSolver(Nodo nodo) {
        this.nodo = nodo;
    }

    public void resolver() {
        resolver(nodo);
    }

    private void resolver(Nodo n) {
        SolverAritmetico arithmeticSolver;
        Arbol arbol;

        List<Nodo> children = n.getHijos();
        Nodo condition = children.get(0);
        children.remove(0);

        Nodo cuerpoPadre = new Nodo(new Token(TipoToken.PUN_COMA, ";"));
        cuerpoPadre.insertarHijos(children);

        arithmeticSolver = new SolverAritmetico(condition);
        Object conditionResult = arithmeticSolver.resolver();

        arbol = new Arbol(cuerpoPadre);

        if (conditionResult instanceof Boolean) {
            while ((Boolean) conditionResult) {
                arbol.recorrer();
                conditionResult = arithmeticSolver.resolver();
            }
        }
        else {
            throw new RuntimeException("La condicion del While debe ser logica");
        }

    }
}