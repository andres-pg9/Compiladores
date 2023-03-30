package mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    Scanner(String source) {
        this.source = source;
    }

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        //Ciclos y condicionales
        palabrasReservadas.put("and", TipoToken.AND);
        palabrasReservadas.put("or", TipoToken.OR);
        palabrasReservadas.put("not", TipoToken.NOT);
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
        palabrasReservadas.put("/*", TipoToken.COMENT_MULT);
        palabrasReservadas.put("ID", TipoToken.ID);
        palabrasReservadas.put("NUM", TipoToken.NUMERO);
    }

    List<Token> scanTokens() {
        String buffer = "";
        /*String lineaAuxiliar = " ";
        lineaAuxiliar += " ";*/

        int estado = 0;

        for (int i = 0; i < this.source.length(); i++) {
            char caracter = this.source.charAt(i);
            switch (estado) {
                case 0:
                    if (caracter == '(') {
                        tokens.add(new Token(TipoToken.PAR_IZQ, "(", null, linea));
                        estado = 0;
                        break;
                    } else if (caracter == ')') {
                        tokens.add(new Token(TipoToken.PAR_DER, ")", null, linea));
                        estado = 0;
                        break;
                    } else if (caracter == '{') {
                        tokens.add(new Token(TipoToken.LLA_IZQ, "{", null, linea));
                        estado = 0;
                        break;
                    } else if (caracter == '}') {
                        tokens.add(new Token(TipoToken.LLA_DER, "}", null, linea));
                        estado = 0;
                        break;
                    } else if (caracter == ',') {
                        tokens.add(new Token(TipoToken.COMA, ",", null, linea));
                        estado = 0;
                        break;
                    } else if (caracter == '.') {
                        tokens.add(new Token(TipoToken.PUNTO, ".", null, linea));
                        estado = 0;
                        break;
                    } else if (caracter == ';') {
                        tokens.add(new Token(TipoToken.PUN_COMA, ";", null, linea));
                        estado = 0;
                        break;
                    } else if (caracter == '-') {
                        tokens.add(new Token(TipoToken.MENOS, "-", null, linea));
                        estado = 0;
                        break;
                    } else if (caracter == '+') {
                        tokens.add(new Token(TipoToken.MAS, "+", null, linea));
                        estado = 0;
                        break;
                    } else if (caracter == '*') {
                        tokens.add(new Token(TipoToken.MULT, "*", null, linea));
                        estado = 0;
                        break;
                    } else if (caracter == '/') {
                        estado = 1;
                        break;
                    } else if (caracter == '!') {
                        estado = 5;
                        break;
                    } else if (caracter == '=') {
                        estado = 6;
                        break;
                    } else if (caracter == '<') {
                        estado = 7;
                        break;
                    } else if (caracter == '>') {
                        estado = 8;
                        break;
                    } else if (Character.isDigit(caracter)) {
                        buffer += caracter;
                        estado = 9;
                        break;
                    } else if (Character.isLetter(caracter)) {
                        buffer += caracter;
                        estado = 10;
                        break;
                    } else{
                        break;}
                case 1:
                    if (caracter == '/') {
                        estado = 2;
                        break;
                    } else if (caracter == '*'){
                        estado = 3;
                        break;
                    }
                    else{
                        tokens.add(new Token(TipoToken.DIV, "/", null, linea));
                        break;
                    }
                case 2:
                    if (caracter == '\n') {
                        //tokens.add(new Token(TipoToken.COMENT,"//",null,linea));
                        estado = 0;
                        break;
                    } else{
                        estado = 2;
                        break;
                    }
                case 3:
                    if (caracter == '*'){
                        estado=4;
                        break;
                    }
                case 4:
                    if (caracter == '/') {
                        //tokens.add(new Token(TipoToken.COMENT_MULT,"/*",null,linea));
                        estado = 0;
                        break;
                    } else{
                        estado = 2;
                        break;
                    }
                        
                case 5:
                    if (caracter == '=') {
                        tokens.add(new Token(TipoToken.DIFERENTE, "!=", null, linea));
                        estado = 0;
                        break;
                    } else {
                        tokens.add(new Token(TipoToken.NOT, "!", null, linea));
                        estado = 0;
                        break;
                    }
                case 6:
                    if (caracter == '=') {
                        tokens.add(new Token(TipoToken.DOB_IGUAL, "==", null, linea));
                        estado = 0;
                        break;
                    } else {
                        tokens.add(new Token(TipoToken.IGUAL, "=", null, linea));
                        estado = 0;
                        break;
                    }
                case 7:
                    if (caracter == '=') {
                        tokens.add(new Token(TipoToken.MEN_IGUAL, "<=", null, linea));
                        estado = 0;
                        break;
                    } else {
                        tokens.add(new Token(TipoToken.MENOR, "<", null, linea));
                        estado = 0;
                        break;
                    }
                case 8:
                    if (caracter == '=') {
                        tokens.add(new Token(TipoToken.MAY_IGUAL, ">=", null, linea));
                        estado = 0;
                        break;
                    } else {
                        tokens.add(new Token(TipoToken.MAYOR, ">", null, linea));
                        estado = 0;
                        break;
                    }
                case 9:
                    if (Character.isDigit(caracter) || caracter== '.'){
                        buffer += caracter; 
                        break;
                    }
                    else{
                        tokens.add(new Token(TipoToken.NUMERO, "NUM", null, linea));
                        buffer = "";
                        estado = 0; 
                        break;
                    }

                case 10:
                    if (Character.isDigit(caracter) || Character.isLetter(caracter)) {
                        buffer += caracter; 
                        break;
                    }
                    else {
                        if (this.palabrasReservadas.containsValue(buffer)){
                            tokens.add(new Token(this.palabrasReservadas.get(caracter),null,this, linea));
                            //Token token = new Token(palabrasReservadas.get(current), current, null, this.linea);
                            buffer = "";
                            estado = 0; 
                            break;
                        }
                        else{
                            tokens.add(new Token(TipoToken.ID, "ID",this.source , this.linea));
                            buffer= "";
                            estado = 0; 
                            break;
                        }
                    }
            }
            this.linea += 1;
        }
        tokens.add(new Token(TipoToken.EOF, "",null , this.linea));
        return tokens;
    }

    public String clean(String cadena) {
        char[] simbolos = {'(', ')', '{', '}', ',', '.', ';', '-', '+', '*', '/', '=', '>', '<', '!'};
        String clean_str = "";
        String current = "";
        boolean flag = true;
        int estado = 0;
        int caracter = 0;
        int charIndex = 0;
        cadena = cadena.replaceAll(" ", "");
        while (flag) {
            switch (estado) {
                case 0:
                    if (contains(simbolos, cadena.charAt(caracter))) {
                        current = String.valueOf(cadena.charAt(caracter));
                        estado = 1;
                    } else if (Character.isDigit(cadena.charAt(caracter))) {
                        current = String.valueOf(cadena.charAt(caracter));
                        estado = 2;
                    } else if (Character.isLetter(cadena.charAt(caracter))) {
                        current += String.valueOf(cadena.charAt(caracter));
                        estado = 3;
                    } else if (cadena.charAt(caracter) == '"') {
                        current += String.valueOf(cadena.charAt(caracter));
                        estado = 4;
                    } else {
                        flag = false;
                    }
                    break;

                case 1:
                    if (contains(simbolos, cadena.charAt(caracter + 1))) {
                        current += String.valueOf(cadena.charAt(caracter + 1));
                        caracter++;
                        estado = 1;
                    } else {
                        clean_str += " " + current;
                        current = "";
                        caracter++;
                        estado = 0;
                    }

                    break;
                case 2:
                    if (Character.isDigit(cadena.charAt(caracter + 1)) || cadena.charAt(caracter + 1) == '.') {
                        current += String.valueOf(cadena.charAt(caracter + 1));
                        caracter++;
                    } else if (cadena.charAt(caracter + 1) == ',') {
                        clean_str += " " + current + " ,";
                        caracter += 2;
                        current = "";
                        estado = 0;
                    } else {
                        clean_str += " " + current;
                        current = "";
                        caracter++;
                        estado = 0;
                    }
                    break;
                case 3:
                    if (Character.isLetterOrDigit(cadena.charAt(caracter + 1))) {
                        current += String.valueOf(cadena.charAt(caracter + 1));
                        caracter++;
                    } else if (cadena.charAt(caracter + 1) == ',') {
                        clean_str += " " + current + " ,";
                        caracter += 2;
                        current = "";
                        estado = 0;
                    } else {
                        clean_str += " " + current + " ";
                        current = "";
                        caracter++;
                        estado = 0;
                    }
                case 4:
                    if (cadena.charAt(caracter+1) != '"') {
                        current += cadena.charAt(caracter+1);
                        caracter++; }
                    else {
                        clean_str += " " + current + " ";
                        current = "";
                        caracter ++;
                        estado = 0; }
            }
        }

        return clean_str;
    }

    public boolean contains(char[] simb, char c) {
        for (int i : simb) {
            if (i == c) {
                return true;
            }
        }
        return false;
    }
   /* private boolean contains(char[] simbolos, char charAt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/
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
