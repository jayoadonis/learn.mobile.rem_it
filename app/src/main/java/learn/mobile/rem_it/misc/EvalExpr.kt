package learn.mobile.rem_it.misc

import java.util.Stack


class EvalExpr(infixExpr: String?) {
    private var infixExpr: String

    init {
        require(!(infixExpr == null || infixExpr.isBlank())) { "Expression cannot be null or blank" }
        this.infixExpr = infixExpr
    }

    fun setInfixExpr(infixExpr: String?) {
        if (infixExpr != null && !infixExpr.isBlank()) {
            this.infixExpr = infixExpr
        }
    }

    fun toPostfix(): String {
        val operators = Stack<Char>()
        val postFix: MutableList<String> = ArrayList()

        //REM: Tokenize the input expression
        val tokens = tokenizeIII(infixExpr)
        for (token in tokens) {
            val firstChar = token[0]
            if (isOperand(firstChar) || isOperator(firstChar) && token.length > 1 && isOperand(
                    token[1]
                )
            ) {
                //REM: Add operands directly to postfix
                postFix.add(token)
            } else if (isOperator(firstChar)) {
                //REM: Handle operator precedence and associativity
                while (!operators.isEmpty() &&
                    hasHighestOrEqualPrecedence(operators.peek(), firstChar)
                ) {
                    postFix.add(operators.pop().toString())
                }
                operators.push(firstChar)
            } else if (firstChar == '(') {
                //REM: Push '(' onto the stack
                operators.push(firstChar)
            } else if (firstChar == ')') {
                //REM: Pop operators until '(' is encountered
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postFix.add(operators.pop().toString())
                }
                require(!(operators.isEmpty() || operators.pop() != '(')) { "Mismatched parentheses in expression" }
            }
        }

        //REM: Pop remaining operators
        while (!operators.isEmpty()) {
            val op = operators.pop()
            require(!(op == '(' || op == ')')) { "Mismatched parentheses in expression" }
            postFix.add(op.toString())
        }
        return java.lang.String.join(" ", postFix)
    }

    private fun tokenizeI(expr: String): List<String> {
        val tokens: MutableList<String> = ArrayList()
        val operand = StringBuilder()
        for (i in 0 until expr.length) {
            val c = expr[i]
            if (Character.isDigit(c) || c == '.' /*|| c == '^'*/) {
                //REM: Build multi-digit numbers or decimal numbers
//        if( c == '^' ) {
//          if (!operand.isEmpty()) {
//            tokens.add(operand.toString());
//            operand.setLength(0);
//          }
//        }
//        else {
                operand.append(c)
                //        }
            } else if (isOperator(c) || c == '(' || c == ')') {
                //REM: Add the operand number token (if any)
                if (!operand.isEmpty()) {
                    tokens.add(operand.toString())
                    operand.setLength(0)
                }
                //REM: Add the operator or parentheses
                tokens.add(c.toString())
            } else require(Character.isWhitespace(c)) { "Invalid character in expression: $c" }
        }

        //REM: Add the last number token (if any)
        if (!operand.isEmpty()) {
            tokens.add(operand.toString())
        }

//    System.out.println("<><> " + (tokens) );
        return tokens
    }

    private fun tokenizeII(expr: String): List<String> {
        val tokens: MutableList<String> = ArrayList()
        val current = StringBuilder()
        for (i in 0 until expr.length) {
            val c = expr[i]
            if (Character.isDigit(c) || c == '.') {
                //REM: Build multi-digit numbers or decimal numbers
                current.append(c)
            } else if (isOperator(c) || c == '^') {
                //REM: Handle signed numbers
                if ((c == '-' || c == '+') && (i == 0 || isOperator(expr[i - 1]) || expr[i - 1] == '(')) {
                    //REM: Part of a signed number
                    current.append(c)
                } else {
                    //REM: Add the current number token (if any)
                    if (!current.isEmpty()) {
                        tokens.add(current.toString())
                        current.setLength(0)
                    }
                    //REM: Add the operator
                    tokens.add(c.toString())
                }
            } else if (c == '(' || c == ')') {
                //REM: Add the current number token (if any)
                if (!current.isEmpty()) {
                    tokens.add(current.toString())
                    current.setLength(0)
                }
                //REM: Add the parenthesis
                tokens.add(c.toString())
            } else require(Character.isWhitespace(c)) { "Invalid character in expression: $c" }
        }

        //REM: Add the last number token (if any)
        if (!current.isEmpty()) {
            tokens.add(current.toString())
        }
        return tokens
    }

    private fun tokenizeIII(expr: String): List<String> {
        val tokens: MutableList<String> = ArrayList()
        val operand = StringBuilder()
        var expectOperand = true //REM: Tracks if we expect an operand (true) or operator (false)
        for (i in 0 until expr.length) {
            val c = expr[i]
            if (Character.isDigit(c) || c == '.') {
                //REM: Build multi-digit or decimal numbers
                operand.append(c)
                expectOperand = false //REM: Next, we expect an operator
            } else if ((c == '-' || c == '+') && expectOperand /*|| expr.charAt(i - 1) == '('*/) {
                //REM: Handle negative and explicit positive unary/sign number. i.e: -3 or +3
                operand.append(c)
            } else if (isOperator(c) || c == '(' || c == ')') {
                //REM: Finalize the current operand (if any)
                if (!operand.isEmpty()) {
                    tokens.add(operand.toString())
                    operand.setLength(0)
                }
                //REM: Add the operator or parentheses
                tokens.add(c.toString())
                expectOperand = c == '(' //REM: After '(' we expect an operand
            } else require(Character.isWhitespace(c)) { "Invalid character in expression: $c" }
        }

        //REM: Add the last operand (if any)
        if (!operand.isEmpty()) {
            tokens.add(operand.toString())
        }
        return tokens
    }

    companion object {
        private fun isOperand(op: Char): Boolean {
            return Character.isDigit(op) || op == '.'
        }

        private fun hasHighestOrEqualPrecedence(opI: Char, opII: Char): Boolean {
            val p1 = getPrecedenceOf(opI)
            val p2 = getPrecedenceOf(opII)
            //    if (p1 == p2)
//      return opI != '^';
            return p1 >= p2
        }

        private fun getPrecedenceOf(op: Char): Int {
            return when (op) {
                '+', '-' -> 1
                '/', '%' -> 2
                '*' -> 3
                '^' -> 4
                else -> -1
            }
        }

        fun isOperator(op: Char): Boolean {
            return when (op) {
                '+', '-', '/', '%', '*', '^' -> true
                else -> false
            }
        }
    }
}

