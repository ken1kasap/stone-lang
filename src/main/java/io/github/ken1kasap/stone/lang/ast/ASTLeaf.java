package io.github.ken1kasap.stone.lang.ast;

import io.github.ken1kasap.stone.lang.Token;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;

public class ASTLeaf extends ASTree {

    private static final List<ASTree> EMPTY = Collections.EMPTY_LIST;

    protected Token token;

    public ASTLeaf(Token token) {
        this.token = token;
    }

    @Override
    public ASTree child(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int numChildren() {
        return 0;
    }

    @Override
    public Iterator<ASTree> children() {
        return EMPTY.iterator();
    }

    @Override
    public String location() {
        return "at line " + token.getLineNumber();
    }

    public Token token() {
        return token;
    }

    @Override
    public String toString() {
        return token.getText();
    }
}
