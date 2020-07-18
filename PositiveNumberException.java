public class PositiveNumberException extends Exception {
        /**
    *
    */
    private static final long serialVersionUID = 1L;

    PositiveNumberException(String s) {
            super(s); //Throw an exception if number if is less than equal to zero
        }
}