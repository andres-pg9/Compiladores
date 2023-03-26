package mx.ipn.escom.compiladores;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    // Palabras clave:
    AND, OR, IF, ELSE, CLASS, FUNC, RETURN,
    FOR, DO, WHILE,  TRUE, FALSE, INT, FLOAT,
    VAR, LONG, DOUBLE, SHORT, VOID, CHAR, STRING,
    PAR_IZQ, PAR_DER, LLA_IZQ, LLA_DER, COMA, PUNTO,
    PUN_COMA, MENOS, MAS, MULT, DIV, DIFERENTE, IGUAL,
    DOB_IGUAL, MENOR, MEN_IGUAL, MAYOR, MAY_IGUAL, COMENT,

    // Final de cadena
    EOF
}
