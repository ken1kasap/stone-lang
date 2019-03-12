package io.github.ken1kasap.stone.lang.ast;

import io.github.ken1kasap.stone.lang.Token;

public class Name extends ASTLeaf {

    public Name(Token t) {
        super(t);
    }

    public String name() {
        return token().getText();
    }
}
