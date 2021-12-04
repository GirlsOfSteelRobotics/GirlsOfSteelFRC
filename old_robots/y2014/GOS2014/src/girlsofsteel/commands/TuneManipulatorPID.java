/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author arushibandi This is the Manual Manipulator PID Tuner on
 * SmartDashboard Arushi can use it.
 */
public class TuneManipulatorPID extends CommandBase {

    double p = 0;
    double i = 0;
    double d = 0;
    double setpoint = 0;
    boolean pid = false;

    public TuneManipulatorPID() {
        requires(manipulator);
    }

    @Override
    protected void initialize() {
        manipulator.initEncoder();
        if (pid) {
            manipulator.resetPIDError();
            manipulator.startPID();
            SmartDashboard.putNumber("Pivot P", 0);
            SmartDashboard.putNumber("Pivot I", 0);
            SmartDashboard.putNumber("Pivot D", 0);
            SmartDashboard.putNumber("Pivot setpoint", 0);

            SmartDashboard.putNumber("Pivot encoder distance", 0.0);
            SmartDashboard.putNumber("Pivot encoder get", 0.0);
        }
    }

    @Override
    protected void execute() {
        if (pid) {
            p = SmartDashboard.getNumber("Pivot P", 0);
            i = SmartDashboard.getNumber("Pivot I", 0);
            d = SmartDashboard.getNumber("Pivot D", 0);
            setpoint = SmartDashboard.getNumber("Pivot setpoint", 0);

            if (setpoint != 0) {
                manipulator.setSetPoint((double) setpoint);
                manipulator.setPID(p, i, d);
            }
            SmartDashboard.putNumber("Pivot Error: ", manipulator.getError());
            SmartDashboard.putNumber("P: ", p);
        }
        SmartDashboard.putNumber("Pivot encoder distance", manipulator.getAbsoluteDistance());
        SmartDashboard.putNumber("REAL PIVOT ENCODER", manipulator.getDistance());
        SmartDashboard.putNumber("Pivot encoder get", manipulator.getEncoder());

        System.out.println("PIVOT ENCODER: " + manipulator.getAbsoluteDistance());
        System.out.print("\tRaw pivot: " + manipulator.getRaw());
    }

    @Override
    protected boolean isFinished() {
        return setpoint > 110 || setpoint < -17;

    }

    @Override
    protected void end() {
        manipulator.stopManipulator();
        manipulator.stopEncoder();
        if (pid) {
            manipulator.disablePID();
        }
    }

    @Override
    protected void interrupted() {
        end();
    }
}
