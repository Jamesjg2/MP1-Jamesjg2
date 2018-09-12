import java.util.Scanner;

/**
 * A class that "encrypts" data through a simple transformation.
 * <p>
 * The provided code is incomplete. Modify it so that it works properly and passes the unit tests in
 * <code>EncryptTest.java</code>.
 *
 * @see <a href="https://cs125.cs.illinois.edu/MP/1/">MP1 Documentation</a>
 * @see <a href="https://en.wikipedia.org/wiki/Caesar_cipher">Caesar Cipher Documentation</a>
 */
public class Encrypt {
/**Sets minimum possible shift allowed.
 */
    public static final int MIN_SHIFT = -1024;
/** Sets maximum possible shift allowed.
 */
    public static final int MAX_SHIFT = 1024;

    /** Transformation start. */
    public static final int TRANSFORM_START = (int) ' ';

    /** Transformation end. */
    public static final int TRANSFORM_END = (int) '~';

    /** Modulo to use for our transformation. */
    public static final int TRANSFORM_MODULUS = TRANSFORM_END - TRANSFORM_START + 1;


    /**
     ** Encrypts an array.
     * @param line is the array that is being encrypted.
     * @param shift is the amount of shift that the array undergoes.
     * @return encrypted array.
     */
    public static char[] encrypter(final char[] line, final int shift) {

        if (line == null) {
            return null;
        }
        char[] enc = new char[line.length];
        if (shift < MIN_SHIFT || shift > MAX_SHIFT) {
            return null;
        }
        for (int i = 0; i < line.length; i++) {
            if (line[i] < TRANSFORM_START || line[i] > TRANSFORM_END) {
                return null;
            }
            if ((shift + (int) line[i]) > TRANSFORM_END) {
                enc[i] = (char) (TRANSFORM_START + ((shift + (int) line[i] - TRANSFORM_START) % TRANSFORM_MODULUS));
            } else if ((shift + (int) line[i]) < TRANSFORM_START) {
                enc[i] = (char) (TRANSFORM_END - Math.abs(shift + (int) line[i] - TRANSFORM_END) % TRANSFORM_MODULUS);
            } else {
               enc[i] = (char) ((shift + (int) line[i]));

            }
        }
        return enc;
    }

    /**
     * Decrypt a single line of text using a rotate-N transformation.
     * <p>
     * See comment for encrypter above.
     *
     * @param line array of characters to decrypt
     * @param shift amount to shift each character
     * @return line decrypted by rotating the specified amount
     * @see <a href="http://www.asciitable.com/">ASCII Character Table</a>
     */
    public static char[] decrypter(final char[] line, final int shift) {
        if (line == null) {
            return null;
        }
        char[] enc = new char[line.length];

        if (shift < MIN_SHIFT || shift > MAX_SHIFT) {
            return null;
        }

        for (int i = 0; i < line.length; i++) {
            if (line[i] < TRANSFORM_START || line[i] > TRANSFORM_END) {
                return null;
            }
            if (((int) line[i] - shift) < TRANSFORM_START) {
                enc[i] = (char) (TRANSFORM_END - Math.abs(-shift + (int) line[i] - TRANSFORM_END) % TRANSFORM_MODULUS);
            } else if ((-shift + (int) line[i]) > TRANSFORM_END) {
                enc[i] = (char) (TRANSFORM_START + ((-shift + (int) line[i] - TRANSFORM_START) % TRANSFORM_MODULUS));
            } else {
                enc[i] = (char) ((-shift + (int) line[i]));

            }
        }
        return enc;
    }

    /* ********************************************************************************************
     * You do not need to modify code below this comment.
     **********************************************************************************************/

    /**
     * Solicits a single line of text from the user, encrypts it using a random shift, and then
     * decrypts it.
     * <p>
     * You are free to review this function, but should not modify it. Note that this function is
     * not tested by the test suite, as it is purely to aid your own interactive testing.
     *
     * @param unused unused input arguments
     */
    @SuppressWarnings("resource")
    public static void main(final String[] unused) {

        String linePrompt = "Enter a line of text, or a blank line to exit:";
        String shiftPrompt = "Enter an integer to shift by:";

        /*
         * Two steps here: first get a line, then a shift integer.
         */
        Scanner lineScanner = new Scanner(System.in);
        repeat: while (true) {
            String line = null;
            Integer shift = null;

            System.out.println(linePrompt);
            //noinspection LoopStatementThatDoesntLoop
            while (lineScanner.hasNextLine()) {
                line = lineScanner.nextLine();
                if (line.equals("")) {
                    break repeat;
                } else {
                    break;
                }
            }

            System.out.println(shiftPrompt);
            while (lineScanner.hasNextLine()) {
                Scanner intScanner = new Scanner(lineScanner.nextLine());
                if (intScanner.hasNextInt()) {
                    shift = intScanner.nextInt();
                    if (intScanner.hasNext()) {
                        shift = null;
                        System.out.println("Invalid input: please enter only a single integer.");
                    }
                } else {
                    System.out.println("Invalid input: please enter an integer.");
                }
                intScanner.close();
                if (shift != null) {
                    break;
                }
            }

            if (line == null || line.equals("")) {
                throw new RuntimeException("Should have a line at this point");
            }
            if (shift == null) {
                throw new RuntimeException("Should have a shift value at this point");
            }

            char[] originalCharacterArray = line.toCharArray();
            char[] encryptedCharacterArray = encrypter(originalCharacterArray, shift);
            char[] decryptedCharacterArray = decrypter(encryptedCharacterArray, shift);

            System.out.println("Encrypted line with ROT-" + shift + ":");
            assert encryptedCharacterArray != null;
            System.out.println(String.valueOf(encryptedCharacterArray));
            System.out.println("Decrypted line:");
            System.out.println(String.valueOf(decryptedCharacterArray));
            System.out.println("Original line:");
            System.out.println(String.valueOf(originalCharacterArray));
        }
        lineScanner.close();
    }
}
