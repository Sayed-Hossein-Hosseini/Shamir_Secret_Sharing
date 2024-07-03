# Shamir's Secret Sharing Project

## Introduction

This project implements **Shamir's Secret Sharing** algorithm, which is used to divide a secret into multiple shares. With a threshold number of shares, the secret can be reconstructed. This is a fundamental technique in cryptography for securing sensitive information by distributing it among multiple parties.

## Classes and Methods

### 1. `ShamirSecretSharing.java`

This class handles the secret sharing process. The main methods are:

- **`ShamirSecretSharing(BigInteger prime)`**: Constructor that initializes the class with a prime number for modular arithmetic.

- **`List<BigInteger[]> splitSecret(BigInteger secret, int n, int t)`**: This method takes a secret, the number of shares (`n`), and the threshold (`t`). It returns a list of `n` shares.

  ###### Formula for Secret Sharing:
  To split a secret `s`, a polynomial of degree `t-1` is constructed:

  ![Secret Sharing Formula](https://latex.codecogs.com/png.image?\bg_white&space;f(x)=s+a_1x+a_2x^2+\cdots+a_{t-1}x^{t-1}\mod&space;p)


  Where `a_1, a_2, \ldots, a_{t-1}` are random coefficients. Each share is a point `(x, f(x))` on this polynomial.

```java
public class ShamirSecretSharing {
    private final BigInteger prime;
    private final SecureRandom random;

    public ShamirSecretSharing(BigInteger prime) {
        this.prime = prime;
        this.random = new SecureRandom();
    }

    public List<BigInteger[]> splitSecret(BigInteger secret, int n, int t) {
        // Implementation of secret splitting
    }
}
```

### 2. `SecretRecovery.java`

This class handles the recovery of the secret from the shares. The main methods are:

- **`SecretRecovery(BigInteger prime)`**: Constructor that initializes the class with a prime number for modular arithmetic.

- **`BigInteger reconstructSecret(List<BigInteger[]> shares)`**: This method takes a list of shares and reconstructs the secret using Lagrange interpolation.

  ###### Formula for Secret Recovery:
  To recover the secret, we use Lagrange interpolation:

 ![Secret Recovery Formula](https://latex.codecogs.com/png.image?\bg_white&space;s=\sum_{j=1}^{t}y_j\prod_{1\leq&space;m\leq&space;t,&space;m\neq&space;j}\frac{x_m}{x_m-x_j}\mod&space;p)


  Where `(x_j, y_j)` are the shares.

```java
public class SecretRecovery {
    private final BigInteger prime;

    public SecretRecovery(BigInteger prime) {
        this.prime = prime;
    }

    public BigInteger reconstructSecret(List<BigInteger[]> shares) {
        // Implementation of secret recovery
    }
}
```

### 3. `UserInterface.java`

This class handles user interaction. The main methods are:

- **`void displayMenu()`**: Displays the main menu with options for splitting and recovering secrets.

- **`void splitSecretOption(Scanner scanner)`**: Handles the process of splitting a secret by prompting the user for inputs and calling the `ShamirSecretSharing` class.

- **`void recoverSecretOption(Scanner scanner)`**: Handles the process of recovering a secret by prompting the user for shares and calling the `SecretRecovery` class.

```java
public class UserInterface {
    public void displayMenu() {
        // Display menu options
    }

    public void splitSecretOption(Scanner scanner) {
        // Handle secret splitting
    }

    public void recoverSecretOption(Scanner scanner) {
        // Handle secret recovery
    }
}
```

### 4. `Main.java`

This class contains the `main` method to run the application and handle user inputs through `UserInterface`.

```java
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface();

        while (true) {
            ui.displayMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    ui.splitSecretOption(scanner);
                    break;
                case 2:
                    ui.recoverSecretOption(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
```

## Secret Sharing Process

1. **Input**:
   - A prime number `p`.
   - Number of shares `n`.
   - Threshold number of shares `t`.
   - The secret `s`.

2. **Output**:
   - `n` shares, each containing an `(x, y)` pair.

3. **Steps**:
   - Generate a random polynomial of degree `t-1` where the constant term is the secret `s`.
   - Calculate `n` shares by evaluating the polynomial at different `x` values.

## Secret Recovery Process

1. **Input**:
   - The prime number `p`.
   - A subset of at least `t` shares.

2. **Output**:
   - The reconstructed secret.

3. **Steps**:
   - Use Lagrange interpolation to compute the constant term of the polynomial, which is the secret.

## How to Use

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/shamir-secret-sharing.git
   ```
2. Navigate to the project directory:
   ```bash
   cd shamir-secret-sharing
   ```
3. Compile the Java files:
   ```bash
   javac *.java
   ```
4. Run the main class:
   ```bash
   java Main
   ```

## Example

### Splitting a Secret

1. Choose the `Split secret` option.
2. Enter a prime number, number of shares, threshold, and the secret.
3. The program will output the generated shares.

### Recovering a Secret

1. Choose the `Recover secret` option.
2. Enter the prime number and provide the required shares.
3. The program will output the reconstructed secret.

## Formulas

- **Secret Sharing**: The polynomial `f(x)` is generated as:<br>
![Secret Sharing Formula](https://latex.codecogs.com/png.image?\bg_white&space;f(x)=s+a_1x+a_2x^2+\cdots+a_{t-1}x^{t-1}\mod&space;p)


- **Secret Recovery**: The secret is recovered using Lagrange interpolation:<br>
  ![Secret Recovery Formula](https://latex.codecogs.com/png.image?\bg_white&space;s=\sum_{j=1}^{t}y_j\prod_{1\leq&space;m\leq&space;t,&space;m\neq&space;j}\frac{x_m}{x_m-x_j}\mod&space;p)

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---

Feel free to replace the placeholder for your GitHub username and project link with the actual ones. This README should provide a comprehensive guide to your project, explaining each part in detail. If you need further customization or have more information to add, let me know!
