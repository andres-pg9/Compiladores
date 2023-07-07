import java.util.List;

public class Parser {

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private final List<Token> tokens;

    private boolean hayErrores = false;

    private Token preanalisis;

    private int i = 0;

    public void parse() {

        preanalisis = tokens.get(i);
        PROGRAM();

        if (!hayErrores && !preanalisis.tipo.equals(TipoToken.EOF)) {
            Main.error(
                    preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void PROGRAM() {
        if (preanalisis.tipo.equals(TipoToken.CLASS) ||
                preanalisis.tipo.equals(TipoToken.FUNC) ||
                preanalisis.tipo.equals(TipoToken.VAR) ||
                preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.FOR) ||
                preanalisis.tipo.equals(TipoToken.IF) ||
                preanalisis.tipo.equals(TipoToken.PRINT) ||
                preanalisis.tipo.equals(TipoToken.RETURN) ||
                preanalisis.tipo.equals(TipoToken.WHILE) ||
                preanalisis.tipo.equals(TipoToken.LLA_IZQ)) {
            DECLARATION();
        }
    }

    void DECLARATION() {
        if (hayErrores)
            return;
        if (preanalisis.tipo == TipoToken.CLASS) {
            CLASS_DECL();
            DECLARATION();
        } else if (preanalisis.tipo == TipoToken.FUNC) {
            FUNC_DECL();
            DECLARATION();
        } else if (preanalisis.tipo == TipoToken.VAR) {
            VAR_DECL();
            DECLARATION();
        } else if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.FOR) ||
                preanalisis.tipo.equals(TipoToken.IF) ||
                preanalisis.tipo.equals(TipoToken.PRINT) ||
                preanalisis.tipo.equals(TipoToken.RETURN) ||
                preanalisis.tipo.equals(TipoToken.WHILE) ||
                preanalisis.tipo.equals(TipoToken.LLA_IZQ)) {
            STATEMENT();
            DECLARATION();
        }
    }

    void CLASS_DECL() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.CLASS)) {
            coincidir(TipoToken.CLASS);
            coincidir(TipoToken.IDENTIFICADOR);
            CLASS_INHER();
            coincidir(TipoToken.LLA_IZQ);
            FUNCTIONS();
            coincidir(TipoToken.LLA_DER);
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void CLASS_INHER() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.MENOR)) {
            coincidir(TipoToken.MENOR);
            coincidir(TipoToken.IDENTIFICADOR);
        }
    }

    void FUNC_DECL() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.FUNC)) {
            coincidir(TipoToken.FUNC);
            FUNCTION();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void VAR_DECL() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.VAR)) {
            coincidir(TipoToken.VAR);
            coincidir(TipoToken.IDENTIFICADOR);
            VAR_INIT();
            coincidir(TipoToken.PUN_COMA);
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void VAR_INIT() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.IGUAL)) {
            coincidir(TipoToken.IGUAL);
            EXPRESSION();
        }
    }

    void STATEMENT() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            EXPR_STMT();
        } else if (preanalisis.tipo == TipoToken.FOR) {
            FOR_STMT();
        } else if (preanalisis.tipo == TipoToken.IF) {
            IF_STMT();
        } else if (preanalisis.tipo == TipoToken.PRINT) {
            PRINT_STMT();
        } else if (preanalisis.tipo == TipoToken.RETURN) {
            RETURN_STMT();
        } else if (preanalisis.tipo == TipoToken.WHILE) {
            WHILE_STMT();
        } else if (preanalisis.tipo == TipoToken.LLA_IZQ) {
            BLOCK();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void EXPR_STMT() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            EXPRESSION();
            coincidir(TipoToken.PUN_COMA);
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void FOR_STMT() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.FOR)) {
            coincidir(TipoToken.FOR);
            coincidir(TipoToken.PAR_IZQ);
            FOR_STMT_1();
            FOR_STMT_2();
            FOR_STMT_3();
            coincidir(TipoToken.PAR_DER);
            STATEMENT();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void FOR_STMT_1() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.VAR)) {
            VAR_DECL();
        } else if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            EXPR_STMT();
        } else if (preanalisis.tipo.equals(TipoToken.PUN_COMA)) {
            coincidir(TipoToken.PUN_COMA);
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void FOR_STMT_2() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            EXPRESSION();
            coincidir(TipoToken.PUN_COMA);
        } else if (preanalisis.tipo.equals(TipoToken.PUN_COMA)) {
            coincidir(TipoToken.PUN_COMA);
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void FOR_STMT_3() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            EXPRESSION();
        }
    }

    void IF_STMT() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.IF)) {
            coincidir(TipoToken.IF);
            coincidir(TipoToken.PAR_IZQ);
            EXPRESSION();
            coincidir(TipoToken.PAR_DER);
            STATEMENT();
            ELSE_STATEMENT();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void ELSE_STATEMENT() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.ELSE)) {
            coincidir(TipoToken.ELSE);
            STATEMENT();
        }
    }

    void PRINT_STMT() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.PRINT)) {
            coincidir(TipoToken.PRINT);
            EXPRESSION();
            coincidir(TipoToken.PUN_COMA);
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token 1" + preanalisis.tipo);
        }
    }

    void RETURN_STMT() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.RETURN)) {
            coincidir(TipoToken.RETURN);
            RETURN_EXP_OPC();
            coincidir(TipoToken.PUN_COMA);
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token 2" + preanalisis.tipo);
        }
    }

    void RETURN_EXP_OPC() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            EXPRESSION();
        }
    }

    void WHILE_STMT() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.WHILE)) {
            coincidir(TipoToken.WHILE);
            coincidir(TipoToken.PAR_IZQ);
            EXPRESSION();
            coincidir(TipoToken.PAR_DER);
            STATEMENT();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void BLOCK() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.LLA_IZQ)) {
            coincidir(TipoToken.LLA_IZQ);
            BLOCK_DECL();
            coincidir(TipoToken.LLA_DER);
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void BLOCK_DECL() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.CLASS) ||
                preanalisis.tipo.equals(TipoToken.FUNC) ||
                preanalisis.tipo.equals(TipoToken.VAR) ||
                preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ)  ||
                preanalisis.tipo.equals(TipoToken.FOR) ||
                preanalisis.tipo.equals(TipoToken.IF) ||
                preanalisis.tipo.equals(TipoToken.PRINT) ||
                preanalisis.tipo.equals(TipoToken.RETURN) ||
                preanalisis.tipo.equals(TipoToken.WHILE) ||
                preanalisis.tipo.equals(TipoToken.LLA_IZQ)) {
            DECLARATION();
            BLOCK_DECL();
        }
    }

    void EXPRESSION() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            ASSIGNMENT();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void ASSIGNMENT() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            LOGIC_OR();
            ASSIGNMENT_OPC();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void ASSIGNMENT_OPC() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.IGUAL)) {
            coincidir(TipoToken.IGUAL);
            EXPRESSION();
        }
    }

    void LOGIC_OR() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            LOGIC_AND();
            LOGIC_OR_2();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void LOGIC_OR_2() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.OR)) {
            coincidir(TipoToken.OR);
            LOGIC_AND();
            LOGIC_OR_2();
        }
    }

    void LOGIC_AND() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            EQUALITY();
            LOGIC_AND_2();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void LOGIC_AND_2() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.AND)) {
            coincidir(TipoToken.AND);
            EQUALITY();
            LOGIC_AND_2();
        }
    }

    void EQUALITY() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            COMPARISON();
            EQUALITY_2();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void EQUALITY_2() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.DIFERENTE)) {
            coincidir(TipoToken.DIFERENTE);
            COMPARISON();
            EQUALITY_2();
        } else if (preanalisis.tipo.equals(TipoToken.DOB_IGUAL)) {
            coincidir(TipoToken.DOB_IGUAL);
            COMPARISON();
            EQUALITY_2();
        }
    }

    void COMPARISON() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            TERM();
            COMPARISON_2();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void COMPARISON_2() {
        if (hayErrores)
            return;
        if (preanalisis.tipo == TipoToken.MAYOR) {
            coincidir(TipoToken.MAYOR);
            TERM();
            COMPARISON_2();
        } else if (preanalisis.tipo == TipoToken.MAY_IGUAL) {
            coincidir(TipoToken.MAY_IGUAL);
            TERM();
            COMPARISON_2();
        } else if (preanalisis.tipo == TipoToken.MENOR) {
            coincidir(TipoToken.MENOR);
            TERM();
            COMPARISON_2();
        } else if (preanalisis.tipo == TipoToken.MEN_IGUAL) {
            coincidir(TipoToken.MEN_IGUAL);
            TERM();
            COMPARISON_2();
        }
    }

    void TERM() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ)) {
            FACTOR();
            TERM_2();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void TERM_2() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.MENOS)) {
            coincidir(TipoToken.MENOS);
            FACTOR();
            TERM_2();
        } else if (preanalisis.tipo.equals(TipoToken.MAS)) {
            coincidir(TipoToken.MAS);
            FACTOR();
            TERM_2();
        }
    }

    void FACTOR() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            UNARY();
            FACTOR_2();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void FACTOR_2() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.DIV)) {
            coincidir(TipoToken.DIV);
            UNARY();
            FACTOR_2();
        } else if (preanalisis.tipo.equals(TipoToken.MULT)) {
            coincidir(TipoToken.MULT);
            UNARY();
            FACTOR_2();
        }
    }

    void UNARY() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION)) {
            coincidir(TipoToken.NEGACION);
            UNARY();
        } else if (preanalisis.tipo.equals(TipoToken.MENOS)) {
            coincidir(TipoToken.MENOS);
            UNARY();
        } else if (preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            CALL();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void CALL() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            PRIMARY();
            CALL_2();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void CALL_2() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.PAR_IZQ)) {
            coincidir(TipoToken.PAR_IZQ);
            ARGUMENTS_OPC();
            coincidir(TipoToken.PAR_DER);
            CALL_2();
        } else if (preanalisis.tipo.equals(TipoToken.PUNTO)) {
            coincidir(TipoToken.PUNTO);
            coincidir(TipoToken.IDENTIFICADOR);
            CALL_2();
        }
    }

    void PRIMARY() {
        if (hayErrores)
            return;
        if (preanalisis.tipo == TipoToken.TRUE) {
            coincidir(TipoToken.TRUE);
        } else if (preanalisis.tipo == TipoToken.FALSE) {
            coincidir(TipoToken.FALSE);
        } else if (preanalisis.tipo == TipoToken.NULL) {
            coincidir(TipoToken.NULL);
        } else if (preanalisis.tipo == TipoToken.THIS) {
            coincidir(TipoToken.THIS);
        } else if (preanalisis.tipo == TipoToken.NUMERO) {
            coincidir(TipoToken.NUMERO);
        } else if (preanalisis.tipo == TipoToken.CADENA) {
            coincidir(TipoToken.CADENA);
        } else if (preanalisis.tipo == TipoToken.IDENTIFICADOR) {
            coincidir(TipoToken.IDENTIFICADOR);
        } else if (preanalisis.tipo == TipoToken.PAR_IZQ) {
            coincidir(TipoToken.PAR_IZQ);
            EXPRESSION();
            coincidir(TipoToken.PAR_DER);
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void FUNCTION() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.IDENTIFICADOR)) {
            coincidir(TipoToken.IDENTIFICADOR);
            coincidir(TipoToken.PAR_IZQ);
            PARAMETERS_OPC();
            coincidir(TipoToken.PAR_DER);
            BLOCK();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void FUNCTIONS() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.IDENTIFICADOR)) {
            FUNCTION();
            FUNCTIONS();
        }
    }

    void PARAMETERS_OPC() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.IDENTIFICADOR)) {
            PARAMETERS();
        }
    }

    void PARAMETERS() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.IDENTIFICADOR)) {
            coincidir(TipoToken.IDENTIFICADOR);
            PARAMETERS_2();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void PARAMETERS_2() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.COMA)) {
            coincidir(TipoToken.COMA);
            coincidir(TipoToken.IDENTIFICADOR);
            PARAMETERS_2();
        }
    }

    void ARGUMENTS_OPC() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            ARGUMENTS();
        }
    }

    void ARGUMENTS() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.NEGACION) ||
                preanalisis.tipo.equals(TipoToken.MENOS) ||
                preanalisis.tipo.equals(TipoToken.TRUE) ||
                preanalisis.tipo.equals(TipoToken.FALSE) ||
                preanalisis.tipo.equals(TipoToken.NULL) ||
                preanalisis.tipo.equals(TipoToken.THIS) ||
                preanalisis.tipo.equals(TipoToken.NUMERO) ||
                preanalisis.tipo.equals(TipoToken.CADENA) ||
                preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
                preanalisis.tipo.equals(TipoToken.PAR_IZQ) ) {
            EXPRESSION();
            ARGUMENTS_2();
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "No se esperaba el token " + preanalisis.tipo);
        }
    }

    void ARGUMENTS_2() {
        if (hayErrores)
            return;
        if (preanalisis.tipo.equals(TipoToken.COMA)) {
            coincidir(TipoToken.COMA);
            EXPRESSION();
            ARGUMENTS_2();
        }
    }

    void coincidir(TipoToken t) {
        if (hayErrores)
            return;

        if (preanalisis.tipo == t) {
            i++;
            preanalisis = tokens.get(i);
        } else {
            hayErrores = true;
            Main.error(preanalisis.numLinea, "Falta el token " + t);
        }
    }
}