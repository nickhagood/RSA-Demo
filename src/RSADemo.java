import java.math.BigInteger;
import java.util.*;

/**
 * @author Jonathan Pingilley and Nick Hagood
 * @version 3/14/2012
 */
public class RSADemo
{
	private BigInteger p, q, N, e, d, message;
	private final static BigInteger one = new BigInteger("1");
	
	/**
	 * Generates a public and private key using RSA encryption.
	 */
	private void generate()
	{
		Random rand = new Random();
		
		// Assign random prime to p
		p = BigInteger.probablePrime(64, rand);
		
		// Assign random prime to q
		q = BigInteger.probablePrime(64, rand);
		
        // Compute N = p X q
		N = p.multiply(q);
				
		// Compute PHI(N)=(p-1)X(q-q)
		BigInteger phiN = (p.subtract(one)).multiply(q.subtract(one));
		
		// This works more times than it doesn't and is suitable for this project's scope.
		e = new BigInteger("11");
		
		BigInteger rPrime = e.gcd(phiN);
		if (rPrime.equals(BigInteger.ONE)) {
			d = e.modInverse(phiN);
			System.out.println("e: " + e + "\nd: " + d);
		}
		else {
			generate(); // If the rPrime doesn't equal 1
		}
	}
	
	/**
	 * Takes a message as input and returns its cypher text.
	 * @param	mess		An integer representation of the message to encrypt.
	 * @return 	message		A BigInteger representation of the encrypted message. 
	 */
	private BigInteger encrypt(int mess)
	{
		int intIn = mess;
		BigInteger bigInt = new BigInteger(String.valueOf(intIn));

		message = bigInt.modPow(e, N);
		System.out.println("\nEncryption: " + message + "\n");
		return message;
	}
	
	/**
	 * Takes a cypher text as input and returns the original message.
	 * @param	crypt	A BigInteger representation of the encrypted message.
	 * @return 	dec		A BigInteger representation of the decrypted message.
	 */
	private BigInteger decrypt(BigInteger crypt)
	{
		crypt = message;
		BigInteger dec = crypt.modPow(d, N);
		System.out.println(dec);
		return dec;
	}
	
	/**
	 * Prints out a user menu.
	 */
	private void displayMenu()
	{
		System.out.println("\nPlease enter the number of the menu "
			+ "option you would like to perform.\n"
			+ "0) Exit program\n"
			+ "1) Generate keys\n"
			+ "2) Encrypt\n"
			+ "3) Decrypt\n");
	}
	
	/**
	 * Takes the user's input.
	 * @return	num		An integer input by the user.
	 */
	private int getInput()
	{
		Scanner scan = new Scanner(System.in);
		int num;
		try	{
			num = scan.nextInt(); scan.nextLine();
		}
		catch (Exception e) {
			System.out.println("Error: You must enter an integer. Try again.");
			num = getInput();
		}
		return num;
	}
	
	/**
	 * An accessor method to run the program.
	 */
	public void run()
	{
		displayMenu();
		int choice = getInput();
		BigInteger encNum = new BigInteger("0");
		while (choice != 0) {
			if (choice == 1) {
				generate();
			}
			else if (choice == 2) {
				try {
					System.out.print("\nPlease enter an integer to encrypt: ");
					encNum = encrypt(getInput());
				}
				catch(Exception e) {
					System.out.println("Error: You must generate the keys before you can encrypt.");
				}
			}
			else if (choice == 3) {
				if (!encNum.equals(BigInteger.ZERO)) {
					System.out.println("\nDecrypting encrypted number... ");
					decrypt(encNum);
				}
				else {
					System.out.println("\nError: You must encrypt a number before you may decrypt it.");
				}
			}
			else {
				System.out.println("Error: Not a menu selection. Try again.");
			}
			
			displayMenu();
			choice = getInput();
		}
	}
	
	public static void main(String[] args)
	{
		RSADemo demo = new RSADemo();
		demo.run();
	}
}