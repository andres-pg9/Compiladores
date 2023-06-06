public enum TipoToken {
    //PALABRAS RESERVADAS
    AND, FALSE, FOR, FUNC, IF,
    NULL, OR, PRINT, RETURN,
    THIS,TRUE, VAR, WHILE, CLASS, ELSE,

    // TIPO TOKEN ID, CADENAS Y NUMEROS
    IDENTIFICADOR, CADENA, NUMERO,

    //SIGNO DEL LENGUAJE
    PAR_IZQ, PAR_DER, LLA_IZQ, LLA_DER, COMA, PUNTO,
    PUN_COMA, MENOS, MAS, MULT, DIV, NEGACION,  DIFERENTE, IGUAL,
    DOB_IGUAL, MENOR, MEN_IGUAL, MAYOR, MAY_IGUAL,

    //FINAL DE CADENA
    EOF
}
