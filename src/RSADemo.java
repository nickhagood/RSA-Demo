import java.math.BigInteger;
import java.util.*;

/**
 * @author Nick Hagood
 * @version Version 1.0
 */
public class RSADemo {
	private Scanner scan = new Scanner(System.in);
	private BigInteger p, q, n, e, d, message;
	private final static BigInteger ONE = new BigInteger("1");
	
	/**
	 * Generates a public and private key using RSA encryption.
	 */
	private void generate() {
		Random rand = new Random();
		
		// Assign random prime to p.
		p = BigInteger.probablePrime(64, rand);
		
		// Assign random prime to q.
		q = BigInteger.probablePrime(64, rand);
		
		// Compute N = p X q
		n = p.multiply(q);
				
		// Compute PHI(N)=(p-1)X(q-q)
		BigInteger phiN = (p.subtract(ONE)).multiply(q.subtract(ONE));
		
		// This works more often than it doesn't and is suitable for this demo's scope.
		e = new BigInteger("11");
		
		BigInteger rPrime = e.gcd(phiN);
		if (rPrime.equals(BigInteger.ONE)) {
			d = e.modInverse(phiN);
			System.out.println("e: " + e + "\nd: " + d);
		}
		else {
			generate(); // If rPrime doesn't equal 1
		}
	}
	
	/**
	 * Takes a message as input and returns its cypher text.
	 * @param	mess	An integer representation of the message to encrypt.
	 * @return	message	A BigInteger representation of the encrypted message. 
	 */
	private BigInteger encrypt(int mess) {
		int intIn = mess;
		BigInteger bigInt = new BigInteger(String.valueOf(intIn));

		message = bigInt.modPow(e, n);
		System.out.println("Encryption: " + message);
		return message;
	}
	
	/**
	 * Takes a cypher text as input and returns the original message.
	 * @param	crypt	A BigInteger representation of the encrypted message.
	 * @return	dec	A BigInteger representation of the decrypted message.
	 */
	private BigInteger decrypt(BigInteger crypt) {
		crypt = message;
		BigInteger dec = crypt.modPow(d, n);
		System.out.println(dec);
		return dec;
	}
	
	/**
	 * Prints out a user menu.
	 */
	private void displayMenu() {
		System.out.println("\nPlease enter the number of the menu "
			+ "option you would like to select.\n"
			+ "0) Exit program\n"
			+ "1) Generate keys\n"
			+ "2) Encrypt\n"
			+ "3) Decrypt\n");
	}
	
	/**
	 * Takes the user's input.
	 * @return	num	An integer input by the user.
	 */
	private int getInput() {
	    while (!scan.hasNextInt()) {
            System.out.println("Invalid input: You must enter an integer.");
            scan.next();
        }
        int num = scan.nextInt();
        scan.nextLine();
		return num;
	}
	
	/**
	 * Closes scanner and calls System.exit(0).
	 */
	private void Quit() {
		scan.close();
		System.out.println("Goodbye!");
		System.exit(0);
	}
	
	/**
	 * An accessor method to run the program.
	 */
	public void run() {
		displayMenu();
		int choice = getInput();
		BigInteger encNum = new BigInteger("0");
		boolean canEncrypt = false;
		
		while (choice != 0) {
			switch (choice) {
				case 0: // 0) Exit program
					Quit();
					break;
				case 1: // 1) Generate keys
					generate();
					canEncrypt = true;
					break;
				case 2: // 2) Encrypt
					if (canEncrypt) {
						System.out.print("Please enter an integer to encrypt: ");
						encNum = encrypt(getInput());
					}
					else {
						System.out.println("You must generate the keys before you can encrypt an integer.");
					}
					break;
				case 3: // 3) Decrypt
					if (!encNum.equals(BigInteger.ZERO)) {
						System.out.print("Decrypted integer: ");
						decrypt(encNum);
					}
					else {
						System.out.println("You must encrypt an integer before you may decrypt it.");
					}
					break;
				default:
					System.out.println("Invalid selection.");
					break;
			}
			
			displayMenu();
			choice = getInput();
		}
		Quit();
	}
	
	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		RSADemo demo = new RSADemo();
		demo.run();
	}
}