package com.gos.codelabs.basic_java;

import org.junit.Test;

public class CalculatorTest {
    @Test
    public void testAddition() {
        Calculator myCalculator = new Calculator();
        double result = myCalculator.add(3, 4);
        assert result == 7;
        result = myCalculator.add(-3,3);
        assert result == 0;
        result = myCalculator.subtract(2,3);
        assert result == -1;
        result = myCalculator.divide(2,2);
        assert result == 1;
        result = myCalculator.multiply(2,3);
        assert result == 6;
    }

    @Test
    public void testSubtraction() {
    }

    //TODO add tests for
    //  - Multiplication
    //  - Division
    //  - Square root
    //  - Powers


}
