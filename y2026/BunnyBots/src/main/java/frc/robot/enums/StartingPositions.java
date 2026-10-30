package frc.robot.enums;

import com.gos.lib.pathing.MaybeFlippedPose2d;
import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.choreo_gen.ChoreoVars;

public enum StartingPositions {
    OVEN(ChoreoVars.Poses.oven),
    PANTRY1(ChoreoVars.Poses.pantry1),
    PANTRY2(ChoreoVars.Poses.pantry2),
    PANTRY3(ChoreoVars.Poses.pantry3),
    PANTRY4(ChoreoVars.Poses.pantry4),
    PANTRYBASE(ChoreoVars.Poses.pantryBase),
    STARTKITCHENCENTERLINE(ChoreoVars.Poses.startKitchenCenterLine),
    STARTKITCHENOVEN(ChoreoVars.Poses.startKitchenOven),
    STARTKITCHENPANTRY(ChoreoVars.Poses.startKitchenPantry);

    public MaybeFlippedPose2d m_pose;

    StartingPositions(Pose2d pose) {
        m_pose = new MaybeFlippedPose2d(pose);


    }

    public String variableName() {
        switch (this) {
        case OVEN:
            return "oven";
        case PANTRY1:
            return "pantry1";
        case PANTRY2:
            return "pantry2";
        case PANTRY3:
            return "pantry3";
        case PANTRY4:
            return "pantry4";
        case PANTRYBASE:
            return "pantryBase";
        case STARTKITCHENCENTERLINE:
            return "startKitchenCenterLine";
        case STARTKITCHENOVEN:
            return "startKitchenOven";
        case STARTKITCHENPANTRY:
            return "startKitchenPantry";
        default:
            throw new IllegalArgumentException();
        }
    }
}
