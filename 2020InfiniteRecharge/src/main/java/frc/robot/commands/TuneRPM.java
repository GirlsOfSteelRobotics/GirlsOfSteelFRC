package frc.robot.commands;

import frc.robot.subsystems.Shooter;

public class TuneRPM {
    /**
 * This function sets the speed for the RPM
 * @param setSpeed speed in RPM
 */
    public void setRPM(double setSpeed) {
        return;
    }
    /**
 * This function tests the speed, will make the robot move
 * @param testSpeed speed in RPM
 */

    public void testRPM(double testSpeed) {
        Shooter shooter = new Shooter();
        RunShooterRPM runShooter = new RunShooterRPM(shooter, testSpeed);
        runShooter.execute();
    }

    public TuneRPM() {

    }

    public static void main(String... args) {

        TuneRPM tuneRPM = new TuneRPM();
        tuneRPM.setRPM(400);
    }
}



