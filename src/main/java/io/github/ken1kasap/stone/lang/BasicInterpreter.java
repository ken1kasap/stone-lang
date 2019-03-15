package io.github.ken1kasap.stone.lang;

import io.github.ken1kasap.stone.lang.ast.ASTree;
import io.github.ken1kasap.stone.lang.ast.NullStmnt;
import io.github.ken1kasap.stone.lang.env.Environment;
import io.github.ken1kasap.stone.lang.env.impl.BasicEnv;
import io.github.ken1kasap.stone.lang.evaluator.BasicEvaluator;

public class BasicInterpreter {

    public static void main(String[] args) throws ParseException {
        run(new BasicParser(), new BasicEnv());
    }

    public static void run(BasicParser bp, Environment env) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            ASTree t = bp.parse(lexer);
            if (!(t instanceof NullStmnt)) {
                Object r = ((BasicEvaluator.ASTreeEx) t).eval(env);
                System.out.println("=> " + r);
            }
        }
    }
}
