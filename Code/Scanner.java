import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    private final String source;

    private final List<Token> tokens;

    public static int numLinea;

    private static final Map<String, TipoToken> palabrasReservadas;

    private final StringBuilder lexema;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and", TipoToken.AND);
        palabrasReservadas.put("else", TipoToken.ELSE);
        palabrasReservadas.put("class", TipoToken.CLASS);
        palabrasReservadas.put("false", TipoToken.FALSE );
        palabrasReservadas.put("for", TipoToken.FOR );
        palabrasReservadas.put("func", TipoToken.FUNC); //definir funciones
        palabrasReservadas.put("if", TipoToken.IF );
        palabrasReservadas.put("null", TipoToken.NULL);
        palabrasReservadas.put("or", TipoToken.OR);
        palabrasReservadas.put("print", TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("this", TipoToken.THIS);
        palabrasReservadas.put("true", TipoToken.TRUE);
        palabrasReservadas.put("var", TipoToken.VAR); //definir variables
        palabrasReservadas.put("while", TipoToken.WHILE);
    }

    Scanner(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
        numLinea = 1;
        this.lexema = new StringBuilder();
    }

    public List<Token> scanTokens() {
        int state = 0;

        for (int i = 0; i <= source.length(); i++) {
            char caracter = caracterActual(i, source.length());
            numLinea = incremento(caracter);

            switch (state) {
                case 0:
                    if (caracter != '\0') {
                        if (caracter == '<') {
                            state = 1;
                            lexema.append(caracter);
                        } else if (caracter == '=') {
                            state = 2;
                            lexema.append(caracter);
                        } else if (caracter == '>') {
                            state = 3;
                            lexema.append(caracter);
                        } else if (caracter == '!') {
                            state = 4;
                            lexema.append(caracter);
                        } else if (caracter == '(') {
                            state = 5;
                            lexema.append(caracter);
                        } else if (caracter == ')') {
                            state = 6;
                            lexema.append(caracter);
                        } else if (caracter == '[') {
                            state = 7;
                            lexema.append(caracter);
                        } else if (caracter == ']') {
                            state = 8;
                            lexema.append(caracter);
                        } else if (caracter == '{') {
                            state = 9;
                            lexema.append(caracter);
                        } else if (caracter == '}') {
                            state = 10;
                            lexema.append(caracter);
                        } else if (caracter == '"') {
                            state = 11;
                            lexema.append(caracter);
                        } else if (Character.isDigit(caracter)) {
                            state = 12;
                            lexema.append(caracter);
                        } else if (caracter == '+') {
                            state = 18;
                            lexema.append(caracter);
                        } else if (caracter == '-') {
                            state = 19;
                            lexema.append(caracter);
                        } else if (caracter == '*') {
                            state = 20;
                            lexema.append(caracter);
                        } else if (caracter == '/') {
                            state = 21;
                            lexema.append(caracter);
                        } else if (caracter == '%') {
                            state = 22;
                            lexema.append(caracter);
                        } else if (Character.isLetter(caracter)) {
                            state = 25;
                            lexema.append(caracter);
                        } else if (caracter == '_') {
                            state = 26;
                            lexema.append(caracter);
                        } else if (caracter == ' ' ||
                                caracter == '\t' ||
                                caracter == '\n' ||
                                caracter == '\r') {
                            state = 27;
                            lexema.append(caracter);
                        } else if (caracter == ';') {
                            lexema.append(caracter);
                            tokenID(TipoToken.PUN_COMA, lexema.toString());
                        } else if (caracter == ',') {
                            lexema.append(caracter);
                            tokenID(TipoToken.COMA, lexema.toString());
                        } else if (caracter == '.') {
                            lexema.append(caracter);
                            tokenID(TipoToken.PUNTO, lexema.toString());
                        } else {
                            Main.error(numLinea, "No se puede concatenar: " + caracter);
                        }
                    }
                    break;
                // Operadores relacionales
                case 1:
                    state = 0;
                    if (caracter == '=') {
                        lexema.append(caracter);
                        tokenID(TipoToken.MEN_IGUAL, lexema.toString());
                    } else {
                        i--;
                        tokenID(TipoToken.MENOR, lexema.toString());
                    }
                    break;
                case 2:
                    state = 0;
                    if (caracter == '=') {
                        lexema.append(caracter);
                        tokenID(TipoToken.DOB_IGUAL, lexema.toString());
                    } else {
                        i--;
                        tokenID(TipoToken.IGUAL, lexema.toString());
                    }
                    break;
                case 3:
                    state = 0;
                    if (caracter == '=') {
                        lexema.append(caracter);
                        tokenID(TipoToken.MAY_IGUAL, lexema.toString());
                    } else {
                        i--;
                        tokenID(TipoToken.MAYOR, lexema.toString());
                    }
                    break;
                case 4:
                    state = 0;
                    if (caracter == '=') {
                        lexema.append(caracter);
                        tokenID(TipoToken.DIFERENTE, lexema.toString());
                    } else {
                        i--;
                        tokenID(TipoToken.NEGACION, lexema.toString());
                    }
                    break;
                // Paréntesis y Corchetes y llaves
                case 5:
                    i--;
                    state = 0;
                    tokenID(TipoToken.PAR_IZQ, lexema.toString());
                    break;
                case 6:
                    i--;
                    state = 0;
                    tokenID(TipoToken.PAR_DER, lexema.toString());
                    break;
                case 7:
                    i--;
                    state = 0;
                    tokenID(TipoToken.COR_IZQ, lexema.toString());
                    break;
                case 8:
                    i--;
                    state = 0;
                    tokenID(TipoToken.COR_DER, lexema.toString());
                    break;
                case 9:
                    i--;
                    state = 0;
                    tokenID(TipoToken.LLA_IZQ, lexema.toString());
                    break;
                case 10:
                    i--;
                    state = 0;
                    tokenID(TipoToken.LLA_DER, lexema.toString());
                    break;
                // Cadenas
                case 11:
                    if (caracter != '"' && caracter != '\0') {
                        lexema.append(caracter);
                    } else if (caracter == '\0') {
                        Main.error(numLinea, "No se puede concatenar: " + lexema);
                    } else {
                        state = 0;
                        lexema.append(caracter);
                        tokenCadenas(
                                TipoToken.CADENA,
                                lexema.toString(),
                                lexema.substring(1, lexema.length() - 1));
                    }
                    break;
                // Numeros
                case 12:
                    if (caracter >= '0' && caracter <= '9') {
                        lexema.append(caracter);
                    } else if (caracter == '.') {
                        state = 13;
                        lexema.append(caracter);
                    } else if (caracter == 'e' || caracter == 'E') {
                        state = 15;
                        lexema.append(caracter);
                    } else {
                        i--;
                        state = 0;
                        tokenCadenas(TipoToken.NUMERO, lexema.toString(), Double.parseDouble(lexema.toString()));
                    }
                    break;
                case 13:
                    if (caracter >= '0' && caracter <= '9') {
                        state = 14;
                        lexema.append(caracter);
                    } else {
                        Main.error(numLinea, "No se puede concatenar: " + lexema);
                    }
                    break;
                case 14:
                    if (caracter >= '0' && caracter <= '9') {
                        lexema.append(caracter);
                    } else if (caracter == 'e' || caracter == 'E') {
                        state = 15;
                        lexema.append(caracter);
                    } else {
                        i--;
                        state = 0;
                        tokenCadenas(TipoToken.NUMERO, lexema.toString(), Double.parseDouble(lexema.toString()));
                    }
                    break;
                case 15:
                    if (caracter >= '0' && caracter <= '9') {
                        state = 17;
                        lexema.append(caracter);
                    } else if (caracter == '+' || caracter == '-') {
                        state = 16;
                        lexema.append(caracter);
                    } else {
                        Main.error(numLinea, "No se puede concatenar: " + lexema);
                    }
                    break;
                case 16:
                    if (caracter >= '0' && caracter <= '9') {
                        state = 17;
                        lexema.append(caracter);
                    } else {
                        throw new RuntimeException("No se puede concatenar: " + lexema);
                    }
                    break;
                case 17:
                    if (caracter >= '0' && caracter <= '9') {
                        lexema.append(caracter);
                    } else {
                        i--;
                        state = 0;
                        tokenCadenas(TipoToken.NUMERO, lexema.toString(), Double.parseDouble(lexema.toString()));
                    }
                    break;
                // Operadores Aritmeticos
                case 18:
                    state = 0;
                    if (caracter == '=') {
                        lexema.append(caracter);
                        tokenID(TipoToken.MAS_IGUAL, lexema.toString());
                    } else {
                        i--;
                        tokenID(TipoToken.MAS, lexema.toString());
                    }
                    break;
                case 19:
                    state = 0;
                    if (caracter == '=') {
                        lexema.append(caracter);
                        tokenID(TipoToken.MENOS_IGUAL, lexema.toString());
                    } else {
                        i--;
                        tokenID(TipoToken.MENOS, lexema.toString());
                    }
                    break;
                case 20:
                    state = 0;
                    if (caracter == '=') {
                        lexema.append(caracter);
                        tokenID(TipoToken.MULT_IGUAL, lexema.toString());
                    } else {
                        i--;
                        tokenID(TipoToken.MULT, lexema.toString());
                    }
                    break;
                case 21:
                    state = 0;
                    if (caracter == '=') {
                        lexema.append(caracter);
                        tokenID(TipoToken.DIV_IGUAL, lexema.toString());
                    } else if (caracter == '/') {
                        state = 28;
                        lexema.append(caracter);
                    } else if (caracter == '*') {
                        state = 29;
                        lexema.append(caracter);
                    } else {
                        i--;
                        tokenID(TipoToken.DIV, lexema.toString());
                    }
                    break;
                case 22:
                    state = 0;
                    if (caracter == '=') {
                        lexema.append(caracter);
                        tokenID(TipoToken.MOD_IGUAL, lexema.toString());
                    } else {
                        i--;
                        tokenID(TipoToken.MOD, lexema.toString());
                    }
                    break;
                // Identificadores
                case 25:
                    if (caracter >= 'a' &&
                            caracter <= 'z' ||
                            caracter >= 'A' &&
                                    caracter <= 'Z'
                            ||
                            caracter >= '0' &&
                                    caracter <= '9'
                            ||
                            caracter == '_') {
                        lexema.append(caracter);
                    } else {
                        i--;
                        state = 0;
                        tokenID(TipoToken.IDENTIFICADOR, lexema.toString());
                    }
                    break;
                case 26:
                    if (caracter >= 'a' &&
                            caracter <= 'z' ||
                            caracter >= 'A' &&
                                    caracter <= 'Z'
                            ||
                            caracter >= '0' &&
                                    caracter <= '9') {
                        state = 25;
                        lexema.append(caracter);
                    } else if (caracter == '_') {
                        lexema.append(caracter);
                    } else {
                        Main.error(numLinea, "No se puede concatenar: " + lexema);
                    }
                    break;
                // Delimitadores
                case 27:
                    if (caracter == ' ' ||
                            caracter == '\t' ||
                            caracter == '\n' ||
                            caracter == '\r') {
                        lexema.append(caracter);
                    } else {
                        i--;
                        state = 0;
                        lexema.delete(0, lexema.length());
                    }
                    break;
                // Comentarios
                case 28:
                    if (caracter != '\n') {
                        lexema.append(caracter);
                    } else {
                        i--;
                        state = 0;
                        lexema.delete(0, lexema.length());
                    }
                    break;
                case 29:
                    if (caracter == '*') {
                        state = 30;
                    } else {
                        lexema.append(caracter);
                    }
                    break;
                case 30:
                    if (caracter == '/') {
                        state = 31;
                    }
                    lexema.append(caracter);
                    break;
                case 31:
                    i--;
                    state = 0;
                    lexema.delete(0, lexema.length());
                    break;
                default:
                    Main.error(numLinea, "No se puede concatenar: " + caracter);
            }
        }
        tokens.add(new Token(TipoToken.EOF, "", null, numLinea));


        tokens.add(new Token(TipoToken.EOF, "", null, numLinea));


        return tokens;
    }

    private void tokenID(TipoToken type, String lexeme) {
        if (type == TipoToken.IDENTIFICADOR) {
            type = palabrasReservadas.getOrDefault(lexeme, TipoToken.IDENTIFICADOR);
        }
        tokens.add(new Token(type, lexeme, null, numLinea));
        this.lexema.delete(0, this.lexema.length());
    }

    private void tokenCadenas(TipoToken type, String lexeme, Object literal) {
        tokens.add(new Token(type, lexeme, literal, numLinea));
        this.lexema.delete(0, this.lexema.length());
    }

    private int incremento(char current) {
        if (current == '\n')
            numLinea++;
        return numLinea;
    }

    private char caracterActual(int index, int sourceLength) {
        if (index >= sourceLength) {
            return '\0';
        }
        return source.charAt(index);
    }
}