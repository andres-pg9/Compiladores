import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private final Map<String, Object> tablaSimbolos = new HashMap<>();
    private static TablaSimbolos index;

    public static TablaSimbolos indexTabla() {
        if (index == null) {
            index = new TablaSimbolos();
        }
        return index;
    }

    public boolean existeIdentificador(String identificador) {

        return tablaSimbolos.containsKey(identificador);
    }

    public Object obtener(String identificador) {
        if (existeIdentificador(identificador)) {
            return tablaSimbolos.get(identificador);
        }
        throw new RuntimeException(identificador + " no está declarado");
    }

    public void asignar(String identificador, Object value) {
        if (existeIdentificador(identificador)) {
            tablaSimbolos.put(identificador, value);
        }
        else {
            throw new RuntimeException(identificador + " no está declarado");
        }
    }

    public void asignarVar(String identifier, Object value) {
        if (existeIdentificador(identifier)) {
            throw new RuntimeException(identifier + " ya está declarado");
        }
        tablaSimbolos.put(identifier, value);
    }

}