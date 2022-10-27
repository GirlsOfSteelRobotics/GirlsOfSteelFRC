package com.gos.codelabs.basic_java;

import java.util.Scanner;

@SuppressWarnings("PMD.CloseResource")
public class RunCalculator {
    public static void main(String[] args) {
        Calculator myCalculator = new Calculator();
        double sum = myCalculator.add(5.0, 6.0);
        double difference = myCalculator.subtract(7.0, 8.0);
        double multiply = myCalculator.multiply(8437.0, 8394398.0);
        double divide = myCalculator.divide(81.0, 9.0);

        System.out.println(sum);
        System.out.println(difference);
        System.out.println(multiply);
        System.out.println(divide);

        System.out.println("Input a number: ");
        // creates a scanner that waits and processes keyboard input
        Scanner input = new Scanner(System.in);
        double number = input.nextDouble();
        System.out.println("Sienna typed the number " + number);
        input.close();
        // TODO Task 1: Ask for 2 numbers and output the sum using a myCalculator
        // TODO Bonus Task 2: Ask which operation the user would like to use (use input.nextLine())
        //              and then run that operation
        // if statements would be helpful here: https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html
        // char operation = input.nextLine().charAt(0);
        // if (operation == "+") {
        //     TODO your code here
        // }

    }
}
