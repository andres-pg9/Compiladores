import java.util.Objects;

public class SolverAritmetico {

    private final Nodo nodo;

    public SolverAritmetico(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object resolver() {
        return resolver(nodo);
    }

    private Object resolver(Nodo n) {
        TablaSimbolos tablaSimbolos = TablaSimbolos.indexTabla();
        // No tiene hijos, es un operando
        if (n.getHijos() == null) {
            if(n.getValue().classTipo() == TipoToken.NUMERO || n.getValue().classTipo() == TipoToken.CADENA){
                return n.getValue().classLiteral();
            }
            else if(n.getValue().classTipo() == TipoToken.IDENTIFICADOR){
                return tablaSimbolos.obtener(n.getValue().classLexema());
            }
            else if (n.getValue().classTipo() == TipoToken.TRUE || n.getValue().classTipo() == TipoToken.FALSE) {
                return Boolean.parseBoolean(n.getValue().classLexema());
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);

        // Operaciones con double
        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double) {
            switch (n.getValue().classTipo()) {
                case MAS:
                    return ((Double) resultadoIzquierdo + (Double) resultadoDerecho);
                case MENOS:
                    return ((Double) resultadoIzquierdo - (Double) resultadoDerecho);
                case MULT:
                    return ((Double) resultadoIzquierdo * (Double) resultadoDerecho);
                case DIV:
                    return ((Double) resultadoIzquierdo / (Double) resultadoDerecho);
                case DOB_IGUAL:
                    return resultadoIzquierdo.equals(resultadoDerecho);
                case DIFERENTE:
                    return !resultadoIzquierdo.equals(resultadoDerecho);
                case MENOR:
                    return ((Double) resultadoIzquierdo < (Double) resultadoDerecho);
                case MEN_IGUAL:
                    return ((Double) resultadoIzquierdo <= (Double) resultadoDerecho);
                case MAYOR:
                    return ((Double) resultadoIzquierdo > (Double) resultadoDerecho);
                case MAY_IGUAL:
                    return ((Double) resultadoIzquierdo >= (Double) resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String) {
            boolean equals = String.valueOf(resultadoIzquierdo).equals(String.valueOf(resultadoDerecho));
            switch (n.getValue().classTipo()) {
                case MAS:
                    return String.valueOf(resultadoIzquierdo).concat(String.valueOf(resultadoDerecho));
                case DOB_IGUAL:
                    return equals;
                case DIFERENTE:
                    return !equals;
            }
        }
        else if (resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Boolean) {
            switch (n.getValue().classTipo()) {
                case AND:
                    return ((Boolean) resultadoIzquierdo && (Boolean) resultadoDerecho);
                case OR:
                    return ((Boolean) resultadoIzquierdo || (Boolean) resultadoDerecho);
                case DOB_IGUAL:
                    return resultadoIzquierdo == resultadoDerecho;
                case DIFERENTE:
                    return resultadoIzquierdo != resultadoDerecho;
            }
        }
        else if (resultadoIzquierdo instanceof Double && resultadoDerecho instanceof String ||
                resultadoIzquierdo instanceof String && resultadoDerecho instanceof Double) {
            if (Objects.requireNonNull(n.getValue().classTipo()) == TipoToken.MAS) {
                return resultadoIzquierdo + resultadoDerecho.toString();
            }
        }
        else {
            throw new RuntimeException("Los tipos de datos no son iguales");
        }

        return null;
    }
}
