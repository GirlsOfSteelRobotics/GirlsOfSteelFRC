package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.objects.PositionInfo;
import com.gos.ultimate_ascent.subsystems.Chassis;
import com.gos.ultimate_ascent.subsystems.Feeder;
import com.gos.ultimate_ascent.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Autonomous extends SequentialCommandGroup {

    public Autonomous(Chassis chassis, Shooter shooter, Feeder feeder, int shootingPosition, int shots, boolean move) {
        int angle = PositionInfo.getAngle(shootingPosition);
        double speed = PositionInfo.getSpeed(shootingPosition);
        if (move) {
            //move forward at 30% for 2 seconds
            addCommands(new StartGyro(chassis, 0));
            addCommands(new AutoMove(chassis, 0, 0.3, 2));
            addCommands(new Rotate(chassis, PositionInfo.getAngle(shootingPosition),
                true));
        } else {
            addCommands(new StartGyro(chassis, angle));
        }
        addCommands(new AutoShootMany(shooter, feeder, shots, speed));
    }

}
