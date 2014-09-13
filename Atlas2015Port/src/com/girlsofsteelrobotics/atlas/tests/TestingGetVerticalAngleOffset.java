/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.tests;
/**
 *
 * @author Resetar
 */
//import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.girlsofsteelrobotics.atlas.commands.CommandBase;

public class TestingGetVerticalAngleOffset extends CommandBase {
    
    double heightOfGoal = 2.57175; //needs to be changed based on initial height of robot
    double distance;
    double initialVelocity = 8.65; //based on initialVelocity of TEST KICKER (CHANGE)
    double gravity = 9.82;
    
    public double getVerticalAngleOffset(double x, double y) {
        x = distance;
        y = heightOfGoal; 
        double g = gravity;
        double v = initialVelocity;
        //for ( var x=1.524; x<4.572; x+.01){
        for ( int i=0; i<100; i++){
            x += 0.1;
            double positiveAngle = Math.atan((square(v)+Math.sqrt(fourthPower(v)-g*(g*square(x)+2*y*square(v))))/g*x);
            double negativeAngle = Math.atan((square(v)-Math.sqrt(fourthPower(v)-g*(g*square(x)+2*y*square(v))))/g*x);
            System.out.println("x = " + x + "\tpositiveAngle = "
                    + positiveAngle + "\tnegativeAngle = " + negativeAngle);
        }
        return 0; //change
    }
     private double square(double num1) {
        double squareNum1 = num1 * num1;
        return squareNum1;
    }
    
    private double fourthPower(double num1) {
        double powerFourNum1 = num1 * num1 * num1 * num1;
        return powerFourNum1;
    }

    private double getDistanceToTarget() { // -1 to 1 -> position horizontally of the backboard on the screen
        return NetworkTable.getTable("camera").getNumber("Distance", 0);
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
