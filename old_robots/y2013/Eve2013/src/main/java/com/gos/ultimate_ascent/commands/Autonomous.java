package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.objects.PositionInfo;
import com.gos.ultimate_ascent.subsystems.Chassis;
import com.gos.ultimate_ascent.subsystems.Feeder;
import com.gos.ultimate_ascent.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup {

    public Autonomous(Chassis chassis, Shooter shooter, Feeder feeder, int shootingPosition, int shots, boolean move) {
        int angle = PositionInfo.getAngle(shootingPosition);
        double speed = PositionInfo.getSpeed(shootingPosition);
        if (move) {
            //move forward at 30% for 2 seconds
            addSequential(new StartGyro(chassis, 0));
            addSequential(new AutoMove(chassis, 0, 0.3, 2));
            addSequential(new Rotate(chassis, PositionInfo.getAngle(shootingPosition),
                true));
        } else {
            addSequential(new StartGyro(chassis, angle));
        }
        addSequential(new AutoShootMany(shooter, feeder, shots, speed));
    }

}
