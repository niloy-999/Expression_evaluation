import java.util.Stack;
import java.util.StringTokenizer;

public class ExpressionEvaluator {
	String postfix;
    private int getPriority(char operator) {
        if (operator == '^') {
            return 3;
        } else if (operator == '*' || operator == '/') {
            return 2;
        } else if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '<' || operator == '>' || operator == '=' || operator == '!') {
            return 0;
        }

        return -1;
    }

    private boolean isOperator(char operator) {
        return (operator == '^' || operator == '*' || operator == '/' || operator == '+' || operator == '-' ||
                operator == '<' || operator == '>' || operator == '=' || operator == '!');
    }

    private boolean isOperand(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    private double doArithmetic(double left, double right, char operator)  throws ExpressionException {
        double result = 0;

        switch (operator) {
            case '*':
                result = left * right;
                break;
            case '/':
                if (right == 0) {
                    throw new ExpressionException("Math ERROR - Divide by zero");
                }
                result = left / right;
                break;
            case '+':
                result = left + right;
                break;
            case '-':
                result = left - right;
                break;
            case '^':
                result = Math.pow(left, right);
                break;
            case '<':
                result = left < right ? 1 : 0;
                break;
            case '>':
                result = left > right ? 1 : 0;
                break;
            case '=':
                result = left == right ? 1 : 0;
                break;
            case '!':
                result = left != right ? 1 : 0;
                break;
        }
        return result;
    }

    public double evaluate(String expression)  throws ExpressionException {
        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        StringTokenizer tokenizer = new StringTokenizer(expression, "()+-*/^<>=!", true);
       StringBuilder postfixExpression = new StringBuilder();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            
            if (token.isEmpty()) {
                continue;
            }

            if (isOperand(token)) {
                operands.push(Double.parseDouble(token));
                postfixExpression.append(token).append(" ");
            } else if (token.equals("(")) {
                operators.push('(');
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    char operator = operators.pop();
                    if (operands.size() < 2) {
                        throw new ExpressionException("Invalid Expression");
                    }
                    double right = operands.pop();
                    double left = operands.pop();
                    double result = doArithmetic(left, right, operator);
                    operands.push(result);
                    postfixExpression.append(operator).append(" ");
                }
                operators.pop(); // Pop the '('
            } else if (isOperator(token.charAt(0))) {
                char currentOperator = token.charAt(0);
                while (!operators.isEmpty() && getPriority(operators.peek()) >= getPriority(currentOperator)) {
                    char operator = operators.pop();
                    if (operands.size() < 2) {
                        throw new ExpressionException("Invalid Expression");
                    }
                    double right = operands.pop();
                    double left = operands.pop();
                    double result = doArithmetic(left, right, operator);
                    operands.push(result);
                    postfixExpression.append(operator).append(" ");
                }
                operators.push(currentOperator);
            } else {
                throw new ExpressionException("Invalid Token: " + token);
            }
        }

        while (!operators.isEmpty()) {
            char operator = operators.pop();
            if (operands.size() < 2) {
                throw new ExpressionException("Invalid Expression");
            }
            double right = operands.pop();
            double left = operands.pop();
            double result = doArithmetic(left, right, operator);
            operands.push(result);
            postfixExpression.append(operator).append(" ");
            
        }

        if (operands.size() == 1) {
        	postfix=postfixExpression.toString();
        	
            return operands.pop();
        }

        throw new ExpressionException("Invalid Expression");
    }
}