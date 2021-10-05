package com.gos.steam_works.commands;

import com.gos.steam_works.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToGear extends Command {
    //double[] defaultValue = new double[0];
    // private static final double IMAGE_CENTER = IMAGE_WIDTH/2.0;
    // private static final double TOLERANCE = 5; //TODO: test this (in pixels)

    //double[] centerX = new double[3];
    // private double currentX;

    public enum Direction {
        kLeft, kRight
    }

    private double m_targetX;

    private final Direction m_direction;

    public TurnToGear(Direction direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.m_chassis);
        this.m_direction = direction;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("TurnToGear Initialized with direction " + m_direction);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_targetX = Robot.m_listener.targetX;

        if (m_direction == Direction.kRight) {
            Robot.m_chassis.turn(0.3, -1.0); // TODO: test
            System.out.println("turning right");
        } else if (m_direction == Direction.kLeft) {
            Robot.m_chassis.turn(0.3, 1.0);
            System.out.println("turning left");
        }

        /*
         * if(centerX.length == 2) { currentX = (centerX[0] + centerX[1])/2.0;
         * SmartDashboard.putBoolean("Gear in Sight", true); } else {
         * SmartDashboard.putBoolean("Gear In Sight", false); //TODO: test this
         * value and direction }
         */
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        System.out.println("targetX: " + m_targetX /*+ " CenterX Length: " + centerX.length*/);

        //return centerX.length == 2;
        return m_targetX >= 0;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.m_chassis.stop();
        System.out.println("TurnToGear Finished.");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
