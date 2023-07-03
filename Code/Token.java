public class Token {
    final TipoToken tipo;
    final String lexema;
    final Object literal;
    final int numLinea;

    public Token(TipoToken tipo, String lexema, Object literal, int numLinea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.numLinea = numLinea;
    }

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
        this.numLinea = 0;
    }

    public int classNumLinea() {
        return this.numLinea;
    }

    public TipoToken classTipo() {
        return tipo;
    }

    public String classLexema() {
        return lexema;
    }

    public Object classLiteral() {
        return literal;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Token)) {
            return false;
        }

        return this.tipo == ((Token) obj).tipo;
    }

    @Override
    public String toString() {
        return "Linea[" + numLinea + "]: " + tipo + " " + lexema + " " + literal;
    }

    public boolean esOperando() {
        switch (this.tipo) {
            case IDENTIFICADOR:
            case NUMERO:
            case CADENA:
            case TRUE:
            case FALSE:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador() {
        switch (this.tipo) {
            case MAS:
            case MENOS:
            case MULT:
            case DIV:
            case DOB_IGUAL:
            case DIFERENTE:
            case MAYOR:
            case MAY_IGUAL:
            case MENOR:
            case MEN_IGUAL:
            case AND:
            case OR:
            case IGUAL:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada() {
        switch (this.tipo) {
            case VAR:
            case IF:
            case PRINT:
            case ELSE:
            case WHILE:
            case FOR:
                return true;
            default:
                return false;
        }
    }

    public boolean isControlStructure() {
        switch (this.tipo) {
            case FOR:
            case WHILE:
            case IF:
            case ELSE:
                return true;
            default:
                return false;
        }
    }

    public boolean greaterEqualPrecedence(Token t) {
        return this.getPrecedence() >= t.getPrecedence();
    }


    private int getPrecedence() {
        switch (this.tipo) {
            case MULT:
            case DIV:
                return 7;
            case MAS:
            case MENOS:
                return 6;
            case MENOR:
            case MEN_IGUAL:
            case MAYOR:
            case MAY_IGUAL:
                return 5;
            case DOB_IGUAL:
            case DIFERENTE:
                return 4;
            case AND:
            case OR:
                return 3;
            case IGUAL:
                return 1;
        }

        return 0;
    }

    public int aridad() {
        switch (this.tipo) {
            case MULT:
            case DIV:
            case MAS:
            case MENOS:
            case DOB_IGUAL:
            case IGUAL:
            case DIFERENTE:
            case MAYOR:
            case MAY_IGUAL:
            case MENOR:
            case MEN_IGUAL:
            case AND:
            case OR:
                return 2;
            default:
                break;
        }
        return 0;
    }
}

