import java.math.BigInteger;

public class ShamirSecretRecovery {
    BigInteger secret;

    public ShamirSecretRecovery(BigInteger[] shares, int t, int p) { // ShamirSecretRecovery
        secret = recoverSecret(shares, t, p);
    }

    private static BigInteger recoverSecret(BigInteger[] shares, int t, int p) {
        // Checking whether the number of shares is sufficient or not
        if (shares.length < t) {
            return null;
        }

        // Calculation of Langerage coefficients
        BigInteger[] coefficients = new BigInteger[t];
        for (int i = 0; i < t; i++) {
            coefficients[i] = BigInteger.ONE;
            for (int j = 0; j < t; j++) {
                if (i != j) {
                    // محاسبه معکوس (shares[j].subtract(BigInteger.valueOf(i)))
                    BigInteger inverse = extendedEuclidean(shares[j].subtract(BigInteger.valueOf(i)), BigInteger.valueOf(p)).getFirst();

                    // بررسی اینکه آیا b اول است یا خیر
                    if (inverse == null) {
                        System.out.println("بازسازی راز با این سهم ها امکان پذیر نیست (b اول نیست).");
                        return null;
                    }

                    // ضرب ضریب با معکوس
                    coefficients[i] = coefficients[i].multiply(inverse).mod(BigInteger.valueOf(p));
                }
            }
        }

        // Reconstruction of the secret
        BigInteger secret = BigInteger.ZERO;
        for (int i = 0; i < t; i++) {
            // محاسبه دستی حاصلضرب ضرایب لانژراژ
            BigInteger product = coefficients[i];
            for (int j = 0; j < t; j++) {
                if (i != j) {
                    product = product.multiply(coefficients[j]).mod(BigInteger.valueOf(p));
                }
            }

            // ضرب سهم با حاصلضرب ضرایب
            secret = secret.add(shares[i].multiply(product).mod(BigInteger.valueOf(p))).mod(BigInteger.valueOf(p));
        }

        return secret;
    }

    public BigInteger getSecret() {
        return secret;
    }

    // پیاده سازی الگوریتم اقلیدس گسترده
    private static Pair<BigInteger, BigInteger> extendedEuclidean(BigInteger a, BigInteger b) {
        BigInteger q, r, x, y, x0, y0;

        x0 = BigInteger.ONE;
        y0 = BigInteger.ZERO;
        x = BigInteger.ZERO;
        y = BigInteger.ONE;

        while (!b.equals(BigInteger.ZERO)) {
            q = a.divide(b);
            r = a.subtract(q.multiply(b));

            x = y;
            y = y0.subtract(q.multiply(x0));

            a = b;
            b = r;

            x0 = x;
            y0 = y;
        }

        return new Pair<>(y, x0); // (معکوس b، x)
    }

    // کلاس برای ذخیره مقادیر x و y
    private static class Pair<T1, T2> {
        private final T1 first;
        private final T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }

        public T1 getFirst() {
            return first;
        }

        public T2 getSecond() {
            return second;
        }
    }
}
