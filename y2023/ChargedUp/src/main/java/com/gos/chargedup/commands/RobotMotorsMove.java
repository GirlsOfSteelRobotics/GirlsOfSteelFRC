package com.gos.chargedup.commands;


import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class RobotMotorsMove extends SequentialCommandGroup {
    public RobotMotorsMove() {


        //
        super();
    }


    private boolean isMotorMoving(SimableCANSparkMax motor){


        return true;
    }

    private double getAngle(RelativeEncoder encoder) {
        return encoder.getPosition();
    }
}