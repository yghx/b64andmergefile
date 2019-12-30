package lambda.test;

import org.junit.Test;

public class LambdaTest {
    @Test
    public void lambdaTestInt() {
        LP.print(System.out::println);
    }
}

class LP {
    static void print(ILambda lambda) {
        for (int i = 0; i < 3; i++) {
            lambda.printInt(i);
        }
    }
}
