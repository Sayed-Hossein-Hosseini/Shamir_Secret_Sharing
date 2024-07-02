import java.math.BigInteger;

public class ShamirSecretRecovery {
    BigInteger secret;

    public ShamirSecretRecovery(BigInteger[] shares, int t, int p) {
        secret = recoverSecret(shares, t, p);
    }

    private static BigInteger recoverSecret(BigInteger[] shares, int t, int p) {
        // بررسی اینکه تعداد قسمت‌ها کافی است یا نه
        if (shares.length < t) {
            System.out.println("تعداد قسمت‌ها کافی نیست.");
            return null;
        }

        // محاسبه ضرایب لانگرانژ
        BigInteger[] coefficients = new BigInteger[t];
        for (int i = 0; i < t; i++) {
            coefficients[i] = BigInteger.ONE;
            for (int j = 0; j < t; j++) {
                if (i != j) {
                    BigInteger difference = shares[j].subtract(BigInteger.valueOf(i));
                    BigInteger inverse = extendedEuclidean(difference, BigInteger.valueOf(p)).getFirst();

                    if (inverse == null) {
                        System.out.println("بازیابی راز با این قسمت‌ها امکان‌پذیر نیست (b عدد اول نیست).");
                        return null;
                    }

                    coefficients[i] = coefficients[i].multiply(inverse).mod(BigInteger.valueOf(p));
                }
            }
        }

        // recovery secret
        BigInteger secret = BigInteger.ZERO;
        for (int i = 0; i < t; i++) {
            BigInteger product = coefficients[i];
            for (int j = 0; j < t; j++) {
                if (i != j) {
                    product = product.multiply(coefficients[j]).mod(BigInteger.valueOf(p));
                }
            }
            secret = secret.add(shares[i].multiply(product).mod(BigInteger.valueOf(p))).mod(BigInteger.valueOf(p));
        }

        return secret;
    }

    // پیاده‌سازی الگوریتم اوکلید گسترده
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

    public BigInteger getSecret() {
        return secret;
    }

    public static void main(String[] args) {
        // مثال استفاده:
        BigInteger[] shares = { BigInteger.valueOf(87), BigInteger.valueOf(60), BigInteger.valueOf(81),
                BigInteger.valueOf(15), BigInteger.valueOf(123) };
        int t = 4;
        int p = 153;

        ShamirSecretRecovery recovery = new ShamirSecretRecovery(shares, t, p);
        BigInteger recoveredSecret = recovery.getSecret();
        System.out.println("رمز بازیابی شده: " + recoveredSecret);
    }
}
