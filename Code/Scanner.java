package mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        //Ciclos y condicionales
        palabrasReservadas.put("and", TipoToken.AND);
        palabrasReservadas.put("or", TipoToken.OR);
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("else", TipoToken.ELSE);
        palabrasReservadas.put("true", TipoToken.TRUE);
        palabrasReservadas.put("false", TipoToken.FALSE);
        palabrasReservadas.put("for", TipoToken.FOR);
        palabrasReservadas.put("do", TipoToken.DO);
        palabrasReservadas.put("while", TipoToken.WHILE);
        palabrasReservadas.put("case", TipoToken.CASE);
        palabrasReservadas.put("break", TipoToken.BREAK);
        palabrasReservadas.put("catch", TipoToken.CATCH);
        palabrasReservadas.put("switch", TipoToken.SWITCH);
        palabrasReservadas.put("default", TipoToken.DEFAULT);
        //Modificadores de accseso
        palabrasReservadas.put("public", TipoToken.PUBLIC);
        palabrasReservadas.put("private", TipoToken.PRIVATE);
        palabrasReservadas.put("protected", TipoToken.PROTECTED);
        palabrasReservadas.put("static", TipoToken.STATIC);
        palabrasReservadas.put("final", TipoToken.FINAL);
        palabrasReservadas.put("void", TipoToken.VOID);
        //Tipos de datos
        palabrasReservadas.put("boolean", TipoToken.BOOLEAN);
        palabrasReservadas.put("int", TipoToken.INT);
        palabrasReservadas.put("double", TipoToken.DOUBLE);
        palabrasReservadas.put("float", TipoToken.FLOAT);
        palabrasReservadas.put("string", TipoToken.STRING);
        palabrasReservadas.put("char", TipoToken.CHAR);
        palabrasReservadas.put("long", TipoToken.LONG);
        palabrasReservadas.put("short", TipoToken.SHORT);
        //Palabras Reservadas
        palabrasReservadas.put("class", TipoToken.CLASS);
        palabrasReservadas.put("func", TipoToken.FUNC);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("print", TipoToken.PRINT);
        palabrasReservadas.put("this", TipoToken.THIS);
        palabrasReservadas.put("system", TipoToken.SYSTEM);

        //Tokens
        palabrasReservadas.put("(", TipoToken.PAR_IZQ);
        palabrasReservadas.put(")", TipoToken.PAR_DER);
        palabrasReservadas.put("{", TipoToken.LLA_IZQ);
        palabrasReservadas.put("}", TipoToken.LLA_DER);
        palabrasReservadas.put(",", TipoToken.COMA);
        palabrasReservadas.put(".", TipoToken.PUNTO);

        palabrasReservadas.put(";", TipoToken.PUN_COMA);
        palabrasReservadas.put("-", TipoToken.MENOS);
        palabrasReservadas.put("+", TipoToken.MAS);
        palabrasReservadas.put("*", TipoToken.MULT);
        palabrasReservadas.put("/", TipoToken.DIV);
        palabrasReservadas.put("!=", TipoToken.DIFERENTE);
        palabrasReservadas.put("=", TipoToken.IGUAL);

        palabrasReservadas.put("==", TipoToken.DOB_IGUAL);
        palabrasReservadas.put("<", TipoToken.MENOR);
        palabrasReservadas.put("<=", TipoToken.MEN_IGUAL);
        palabrasReservadas.put(">", TipoToken.MAYOR);
        palabrasReservadas.put(">=", TipoToken.MAY_IGUAL);
        palabrasReservadas.put("//", TipoToken.COMENT);
    }

    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens(){
        //Aquí va el corazón del scanner.

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        tokens.add(new Token(TipoToken.EOF, "", null, linea));

        return tokens;
    }
}

/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */