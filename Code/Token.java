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


    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
        this.numLinea = 0;
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
