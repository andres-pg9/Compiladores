package mx.ipn.escom.compiladores;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    // Palabras clave:

    //Ciclos y condicionales
    AND, OR, NOT, IF, ELSE, TRUE, FALSE, FOR, DO, WHILE, CASE, BREAK, CATCH, SWITCH, DEFAULT,

    //Modificadores de Acceso
    PUBLIC, PRIVATE, PROTECTED, STATIC, FINAL, VOID,

    //Tipos de Datos
    BOOLEAN, INT, DOUBLE, FLOAT, STRING, CHAR, LONG, SHORT,

    //Palabras Reservadas
    CLASS, FUNC, RETURN, PRINT, THIS, SYSTEM,

    //Tokens

    PAR_IZQ, PAR_DER, LLA_IZQ, LLA_DER, COMA, PUNTO,
    PUN_COMA, MENOS, MAS, MULT, DIV, DIFERENTE, IGUAL,
    DOB_IGUAL, MENOR, MEN_IGUAL, MAYOR, MAY_IGUAL, COMENT, COMENT_MULT, ID,
    NUMERO,

    // Final de cadena
    EOF
}
