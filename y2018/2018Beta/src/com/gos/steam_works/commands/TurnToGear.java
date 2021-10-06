package com.gos.steam_works.commands;

import com.gos.steam_works.GripPipelineListener;
import com.gos.steam_works.subsystems.Chassis;
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

    private final Chassis m_chassis;
    private final GripPipelineListener m_listener;
    private final Direction m_direction;

    public TurnToGear(Chassis chassis, GripPipelineListener listener, Direction direction) {
        m_chassis = chassis;
        m_listener = listener;
        requires(m_chassis);
        this.m_direction = direction;
    }


    @Override
    protected void initialize() {
        System.out.println("TurnToGear Initialized with direction " + m_direction);
    }


    @Override
    protected void execute() {
        m_targetX = m_listener.targetX;

        if (m_direction == Direction.kRight) {
            m_chassis.turn(0.3, -1.0); // TODO: test
            System.out.println("turning right");
        } else if (m_direction == Direction.kLeft) {
            m_chassis.turn(0.3, 1.0);
            System.out.println("turning left");
        }

        /*
         * if(centerX.length == 2) { currentX = (centerX[0] + centerX[1])/2.0;
         * SmartDashboard.putBoolean("Gear in Sight", true); } else {
         * SmartDashboard.putBoolean("Gear In Sight", false); //TODO: test this
         * value and direction }
         */
    }


    @Override
    protected boolean isFinished() {
        System.out.println("targetX: " + m_targetX /*+ " CenterX Length: " + centerX.length*/);

        //return centerX.length == 2;
        return m_targetX >= 0;
    }


    @Override
    protected void end() {
        m_chassis.stop();
        System.out.println("TurnToGear Finished.");
    }


}
