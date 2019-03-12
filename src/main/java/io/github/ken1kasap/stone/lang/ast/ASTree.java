package io.github.ken1kasap.stone.lang.ast;

import java.util.Iterator;

public abstract class ASTree implements Iterable<ASTree> {

    public abstract ASTree child(int i);

    public abstract int numChildren();

    public abstract Iterator<ASTree> children();

    public abstract String location();

    @Override
    public Iterator<ASTree> iterator() {
        return children();
    }
}
