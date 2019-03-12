package io.github.ken1kasap.stone.lang.ast;

import io.github.ken1kasap.stone.lang.Token;

public class StringLiteral extends ASTLeaf {

    public StringLiteral(Token t) {
        super(t);
    }

    public String value() {
        return token().getText();
    }
}
