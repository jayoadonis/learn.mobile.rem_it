package learn.mobile.rem_it.misc

import java.util.Stack

class Calculator {
    private var evalExpr: EvalExpr? = null

    /**
     *
     * @param evalExpr - An Object that accept infix expression
     */
    fun setEvalExpr(evalExpr: EvalExpr?) {
        this.evalExpr = evalExpr
    }

    /**
     *
     * @param evalExpr - Infix expression
     */
    fun setEvalExpr(evalExpr: String?) {
        this.evalExpr = EvalExpr(evalExpr)
    }

    val postfix: String
        get() {
            checkNotNull(evalExpr) { "EvalExpr is not set" }
            return evalExpr!!.toPostfix()
        }

    fun calc(): Double {
        return calcPostfix(postfix)
    }

    companion object {
        private fun calcPostfix(postfixExpr: String): Double {
            val operands = Stack<Double>()
            val expr = postfixExpr.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            for (token in expr) {
                if (token.isBlank()) continue
                val TRIMMED_TOKEN = token.trim { it <= ' ' }
                //      if (TRIMMED_TOKEN.isEmpty()) continue;
                val currentChar = TRIMMED_TOKEN[0]
                if (EvalExpr.isOperator(currentChar) && TRIMMED_TOKEN.length == 1) {
                    check(!(operands.size < 2)) { "Invalid postfix expression: insufficient operands" }
                    val y = operands.pop()
                    val x = operands.pop()
                    operands.push(calc(currentChar, x, y))
                } else {
//        try {
                    operands.push(TRIMMED_TOKEN.toDouble())
                    //        }
//        catch( final NumberFormatException nFEx) {
//
//        }
                }
            }
            if (operands.size != 1) throw IllegalStateException("Invalid postfix expression: no leftover operands/no possible final output.")
            return operands.peek()
        }

        private fun calc(op: Char, operandI: Double, operandII: Double): Double {
//    System.out.printf("%.2f %c %.2f\n", operandI, op, operandII);
            return when (op) {
                '-' -> operandI - operandII
                '+' -> operandI + operandII
                '%' -> operandI % operandII
                '/' -> {
                    if (operandII == 0.0) throw ArithmeticException("Division by zero")
                    operandI / operandII
                }

                '*' -> operandI * operandII
                '^' -> {
                    Math.pow(operandI, operandII)
                }

                else -> throw IllegalArgumentException("Unsupported operator: $op")
            }
        }
    }
}