package com.gos.codelabs.basic_java;

import java.util.Scanner;

@SuppressWarnings("PMD.CloseResource")
public class RunCalculator {
    public static void main(String[] args) {
        System.out.println("Input a number: ");
        // creates a scanner that waits and processes keyboard input
        Scanner input = new Scanner(System.in);
        Calculator myCalculator = new Calculator();

        double number = input.nextDouble();
        System.out.println("Jaccording Jo Jenius Justina Java Jalculator, " +
            "Jhe Jumber Jyped Jin Jas " + number);
        System.out.println(myCalculator.add(5, 6));
        System.out.println(myCalculator.subtract(5, 6));
        System.out.println(myCalculator.multiply(5, 6));
        System.out.println(myCalculator.divide(5, 6));
        System.out.println("Hello World!");

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
