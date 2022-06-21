package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Chassis;

public class TestGoToLocation extends SequentialCommandGroup {

    public TestGoToLocation(Chassis chassis) {
        //degree change relative to beginning position of the robot to end facing
        //runs the TurnToSetPoint and MoveToSetPoint commands in sequence to go to location.
        double x = SmartDashboard.getNumber("GTL,x", 0.0);
        double y = SmartDashboard.getNumber("GTL,y", 0.0);
        double degreesToFace = SmartDashboard.getNumber("GTL,degrees to face", 0.0);
        double degreesToTurn = Math.atan2(y, x) * 180 / Math.PI;
        double distanceToMove = Math.sqrt((x * x) + (y * y));
        addCommands(new TurnToSetPoint(chassis, degreesToTurn));
        addCommands(new MoveToSetPoint(chassis, distanceToMove));
        addCommands(new TurnToSetPoint(chassis, degreesToFace - degreesToTurn));
    }
}
