package com.gos.codelabs.basic_java;

import java.util.Scanner;

@SuppressWarnings("PMD.CloseResource")
public class RunCalculator {
    public static void main(String[] args) {
        // creates a scanner that waits and processes keyboard input
        Scanner input = new Scanner(System.in);
        Calculator myCalculator = new Calculator();
        System.out.println("Input a number. What operation do you want to use? Add = 1, Subtract = 2, Multiply = 3, Divide = 4");
        double number = input.nextDouble();
        System.out.println("Enter x: ");
        double x = input.nextDouble();
        System.out.println("Enter y: ");
        double y = input.nextDouble();
        if (number == 1) {
            System.out.println(myCalculator.add(x, y));
        }
        if (number == 2) {
            System.out.println(myCalculator.subtract(x, y));
        }
        if (number == 3) {
            System.out.println(myCalculator.multiply(x, y));
        }
        if (number == 4) {
            System.out.println(myCalculator.divide(x, y));
        }
        input.close();

        // if statements would be helpful here: https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html
        // char operation = input.nextLine().charAt(0);
        // if (operation == "+") {
        //     TODO your code here
        // }

    }
}
