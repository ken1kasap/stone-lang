package io.github.ken1kasap.stone.lang.ast;

import io.github.ken1kasap.stone.lang.Token;

public class NumberLiteral extends ASTLeaf {

    public NumberLiteral(Token t) {
        super(t);
    }

    public int value() {
        return token().getNumber();
    }
}
