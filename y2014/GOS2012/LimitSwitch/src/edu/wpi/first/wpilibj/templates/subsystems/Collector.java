/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Maureen
 */
public class Collector extends Subsystem {

    DigitalInput collectorLimitSwitch = new DigitalInput(5);
    DigitalInput shooterLimitSwitch = new DigitalInput(6);
    int tally = 2;
    boolean pressedCollector = false;
    boolean pressedShooter = false;
    Jaguar brush = new Jaguar(1);

    public void tallyBalls() {
        while (pressedCollector == true) {
            if (collectorLimitSwitch.get() == true) { //true is not pressed
                tally++;
                System.out.println("switch not pressed");
                pressedCollector = false;
            }
        }
        while (pressedShooter == true) {
            if (shooterLimitSwitch.get() == true) { //true is not pressed
                System.out.println("Shooter not pressed");
                tally--;
                pressedShooter = false;
            }
        }
        if (tally >= 3) {
            brush.set(-.25);
        } else {
            brush.set(.25);
        }
        if (collectorLimitSwitch.get() == false) { //false is pressed
            System.out.println("pressed");
        }
        if (shooterLimitSwitch.get() == false) { //false is pressed
            System.out.println("Shooter pressed");
            pressedShooter = true;
        }
        System.out.println("tally:" + tally + " Shooter limit Switch: " + shooterLimitSwitch.get() + " Collector Limit Switch: " + shooterLimitSwitch.get());
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 2, "tally" + tally);

    }

    protected void initDefaultCommand() {
    }
}
