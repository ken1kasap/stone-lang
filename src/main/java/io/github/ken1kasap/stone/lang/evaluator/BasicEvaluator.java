package io.github.ken1kasap.stone.lang.evaluator;

import io.github.ken1kasap.stone.lang.exception.StoneException;
import io.github.ken1kasap.stone.lang.Token;
import io.github.ken1kasap.stone.lang.ast.ASTLeaf;
import io.github.ken1kasap.stone.lang.ast.ASTList;
import io.github.ken1kasap.stone.lang.ast.ASTree;
import io.github.ken1kasap.stone.lang.ast.BinaryExpr;
import io.github.ken1kasap.stone.lang.ast.BlockStmnt;
import io.github.ken1kasap.stone.lang.ast.IfStmnt;
import io.github.ken1kasap.stone.lang.ast.Name;
import io.github.ken1kasap.stone.lang.ast.NegativeExpr;
import io.github.ken1kasap.stone.lang.ast.NullStmnt;
import io.github.ken1kasap.stone.lang.ast.NumberLiteral;
import io.github.ken1kasap.stone.lang.ast.StringLiteral;
import io.github.ken1kasap.stone.lang.ast.WhileStmnt;
import io.github.ken1kasap.stone.lang.env.Environment;
import java.util.List;
import java.util.Objects;
import javassist.gluonj.*;

@Reviser
public class BasicEvaluator {

    public static final Integer TRUE = 1;
    public static final Integer FALSE = 0;

    @Reviser
    public static abstract class ASTreeEx extends ASTree {

        public abstract Object eval(Environment env);
    }

    @Reviser
    public static class ASTListEx extends ASTList {

        public ASTListEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Environment env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }

    @Reviser
    public static class ASTLeafEx extends ASTLeaf {

        public ASTLeafEx(Token t) {
            super(t);
        }

        public Object eval(Environment env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }

    @Reviser
    public static class NumberEx extends NumberLiteral {

        public NumberEx(Token t) {
            super(t);
        }

        public Object eval(Environment e) {
            return value();
        }
    }

    @Reviser
    public static class StringEx extends StringLiteral {

        public StringEx(Token t) {
            super(t);
        }

        public Object eval(Environment e) {
            return value();
        }
    }

    @Reviser
    public static class NameEx extends Name {

        public NameEx(Token t) {
            super(t);
        }

        public Object eval(Environment env) {
            Object value = env.get(name());
            if (value == null) {
                throw new StoneException("undefined name: " + name(), this);
            } else {
                return value;
            }
        }
    }

    @Reviser
    public static class NegativeEx extends NegativeExpr {

        public NegativeEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Environment env) {
            Object v = ((ASTreeEx) operand()).eval(env);
            if (v instanceof Integer) {
                return -((Integer) v);
            } else {
                throw new StoneException("bad type for -", this);
            }
        }
    }

    @Reviser
    public static class BinaryEx extends BinaryExpr {

        public BinaryEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Environment env) {
            String op = operator();
            if ("=".equals(op)) {
                Object right = ((ASTreeEx) right()).eval(env);
                return computeAssign(env, right);
            } else {
                Object left = ((ASTreeEx) left()).eval(env);
                Object right = ((ASTreeEx) right()).eval(env);
                return computeOp(left, op, right);
            }
        }

        protected Object computeAssign(Environment env, Object rvalue) {
            ASTree l = left();
            if (l instanceof Name) {
                env.put(((Name) l).name(), rvalue);
                return rvalue;
            } else {
                throw new StoneException("bad assignment", this);
            }
        }

        protected Object computeOp(Object left, String op, Object right) {
            if (left instanceof Integer && right instanceof Integer) {
                return computeNumber((Integer) left, op, (Integer) right);
            } else if (op.equals("+")) {
                return String.valueOf(left) + String.valueOf(right);
            } else if (op.equals("==")) {
                if (left == null) {
                    return right == null ? TRUE : FALSE;
                } else {
                    return left.equals(right) ? TRUE : FALSE;
                }
            } else {
                throw new StoneException("bad type", this);
            }
        }

        protected Object computeNumber(Integer left, String op, Integer right) {
            int a = left;
            int b = right;
            switch (op) {
                case "+":
                    return a + b;
                case "-":
                    return a - b;
                case "*":
                    return a * b;
                case "/":
                    return a / b;
                case "%":
                    return a % b;
                case "==":
                    return a == b ? TRUE : FALSE;
                case ">":
                    return a > b ? TRUE : FALSE;
                case "<":
                    return a < b ? TRUE : FALSE;
                default:
                    throw new StoneException("bad operator", this);
            }
        }
    }

    @Reviser
    public static class BlockEx extends BlockStmnt {

        public BlockEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Environment env) {
            Object result = 0;
            for (ASTree t : this) {
                if (!(t instanceof NullStmnt)) {
                    result = ((ASTreeEx) t).eval(env);
                }
            }
            return result;
        }
    }

    @Reviser
    public static class IfEx extends IfStmnt {

        public IfEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Environment env) {
            Object c = ((ASTreeEx) condition()).eval(env);
            if (c instanceof Integer && !Objects.equals((Integer) c, FALSE)) {
                return ((ASTreeEx) thenBlock()).eval(env);
            } else {
                ASTree b = elseBlock();
                if (b == null) {
                    return 0;
                } else {
                    return ((ASTreeEx) b).eval(env);
                }
            }
        }
    }

    @Reviser
    public static class WhileEx extends WhileStmnt {

        public WhileEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Environment env) {
            Object result = 0;
            for (;;) {
                Object c = ((ASTreeEx) condition()).eval(env);
                if (c instanceof Integer && Objects.equals((Integer) c, FALSE)) {
                    return result;
                } else {
                    result = ((ASTreeEx) body()).eval(env);
                }
            }
        }
    }
}
