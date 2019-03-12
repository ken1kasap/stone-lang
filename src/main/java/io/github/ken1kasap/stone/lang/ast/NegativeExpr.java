package io.github.ken1kasap.stone.lang.ast;

import java.util.List;

public class NegativeExpr extends ASTList {

    public NegativeExpr(List<ASTree> c) {
        super(c);
    }

    public ASTree operand() {
        return child(0);
    }

    @Override
    public String toString() {
        return "-" + operand();
    }
}
