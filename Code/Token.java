public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;
    final int numLinea; //Numero de Linea

    public Token(TipoToken tipo, String lexema, Object literal, int numLinea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.numLinea = numLinea;
    }


    public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.numLinea = 0;
    }



    // Métodos auxiliares
    public boolean esOperando(){
        switch (this.tipo){
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

    public boolean esOperador(){
        switch (this.tipo){
            case MAS:
            case MENOS:
            case MULT:
            case DIV:
            case MENOR:
            case MEN_IGUAL:
            case MAYOR:
            case MAY_IGUAL:
            case DOB_IGUAL:
            case DIFERENTE:
            case AND:
            case OR:
            case IGUAL:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case VAR:
            case IF:
            case PRINT:
            case ELSE:
            case FOR:
            case WHILE:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case IF:
            case ELSE:
            case FOR:
            case WHILE:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
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

    public int aridad(){
        switch (this.tipo) {
            case MULT:
            case DIV:
            case MAS:
            case MENOS :
            case IGUAL:
            case DOB_IGUAL:
            case MAYOR:
            case MAY_IGUAL:
            case MENOR:
            case MEN_IGUAL:
            case DIFERENTE:
            case AND:
            case OR:
                return 2;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Token)) {
            return false;
        }
        if(this.tipo == ((Token)obj).tipo){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "Linea " + numLinea + ": " + tipo + " " + lexema + " " + literal;
    }
}
