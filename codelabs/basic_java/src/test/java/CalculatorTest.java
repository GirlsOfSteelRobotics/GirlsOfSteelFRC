import org.junit.jupiter.api.Test;

public class CalculatorTest {
    @Test
    public void testAddition() {
        Calculator myCalculator = new Calculator();
        double result = myCalculator.add(3, 4);
        assert result == 7;
    }

    @Test
    public void testSubtraction() {
        //TODO your code here
    }

    //TODO add tests for
    //  - Multiplication
    //  - Division
    //  - Square root
    //  - Powers


}
