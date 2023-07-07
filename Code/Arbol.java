public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            try {
                Token t = n.getValue();
                switch (t.classTipo()) {
                    // Operadores aritméticos
                    case MAS:
                    case MENOS:
                    case MULT:
                    case DIV:
                    case MENOR:
                    case MAYOR:
                    case MEN_IGUAL:
                    case MAY_IGUAL:
                    case DOB_IGUAL:
                    case DIFERENTE:
                    case AND:
                    case OR:
                        SolverAritmetico solver = new SolverAritmetico(n);
                        solver.resolver();
                        break;

                    case VAR:
                        // Crear una variable. Usar tabla de simbolos
                        VarSolver varSolver = new VarSolver(n);
                        varSolver.resolver();
                        break;
                    case IF:
                        IfSolver ifSolver = new IfSolver(n);
                        ifSolver.resolver();
                        break;
                    case FOR:
                        ForSolver forSolver = new ForSolver(n);
                        forSolver.resolver();
                        break;
                    case WHILE:
                        WhileSolver whileSolver = new WhileSolver(n);
                        whileSolver.resolver();
                        break;
                    case PRINT:
                        PrintSolver printSolver = new PrintSolver(n);
                        printSolver.resolver();
                        break;
                    case IGUAL:
                        SolverAsignacion solverAsignacion = new SolverAsignacion(n);
                        solverAsignacion.resolver();
                    default:
                        break;

                }
            }
            catch (Exception e){
                Main.error(n.getValue().classNumLinea(), e.getMessage());
                break;
            }

        }
    }

}
