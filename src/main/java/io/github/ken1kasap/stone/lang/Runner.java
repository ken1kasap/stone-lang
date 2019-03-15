package io.github.ken1kasap.stone.lang;

import io.github.ken1kasap.stone.lang.evaluator.BasicEvaluator;
import javassist.gluonj.util.Loader;

public class Runner {

    public static void main(String[] args) throws Throwable {
        Loader.run(BasicInterpreter.class, args, BasicEvaluator.class);
    }
}
