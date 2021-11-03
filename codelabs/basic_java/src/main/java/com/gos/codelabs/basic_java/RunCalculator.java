package com.gos.codelabs.basic_java;

import java.util.Scanner;

@SuppressWarnings("PMD.CloseResource")
public class RunCalculator {
    public static void main(String[] args) {
        // creates a scanner that waits and processes keyboard input
        Scanner input = new Scanner(System.in);
        Calculator myCalculator = new Calculator();
        System.out.print("Put in one number: ");
        double numberOne = input.nextDouble();
        System.out.print("Put in another number: ");
        double numberTwo = input.nextDouble();
        System.out.print("Input the operation you would like to complete: ");
        //could have a try block or a nextDouble command to test for return character
        String test = input.nextLine(); //gets rid of character return after 2nd number
        //System.out.println(test); --> helped w/ a test
        char operation = input.nextLine().charAt(0); //looks @ string and returns 1 number
        if (operation == '+') {
            System.out.println(myCalculator.add(numberOne, numberTwo));
        }
        else if (operation == '-') {
            System.out.println(myCalculator.subtract(numberOne, numberTwo));
        }
        else if (operation == '/') {
            System.out.println(myCalculator.divide(numberOne, numberTwo));
        }
        else if (operation == '*') {
            System.out.println(myCalculator.multiply(numberOne, numberTwo));
        }
        else {
            System.out.println("ERROR!!! Please input an operation. Your inability to follow directions is annoying.");
        }
        input.close();
        //add is a function
        // TODO Task 1: Ask for 2 numbers and output the sum using a myCalculator - check!
        // TODO Bonus Task 2: Ask which operation the user would like to use (use input.nextLine())
        //              and then run that operation
        // if statements would be helpful here: https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html
        // char operation = input.nextLine().charAt(0);
        // if (operation == "+") {
        //     TODO your code here
        // }

    }
}
