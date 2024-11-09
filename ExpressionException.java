public class ExpressionException extends Exception {
    
    private static final long serialVersionUID = 1L;

	public ExpressionException() {
        super("ExpressionException: Invalid Expression");
    }

    public ExpressionException(String message) {
        super(message); 
    }
}
