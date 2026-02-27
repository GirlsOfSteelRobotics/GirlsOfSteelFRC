package com.gos.rebuilt;

import com.gos.lib.GetAllianceUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("PMD")
public class MatchTime {

    public static boolean shouldIShoot() {

        double time = DriverStation.getMatchTime();
        String whoStartedFirst = DriverStation.getGameSpecificMessage();
        boolean blue = GetAllianceUtil.isBlueAlliance();
        boolean winAuto = blue && "B".equals(whoStartedFirst) || !blue && "B".equals(whoStartedFirst);
        SmartDashboard.putNumber("MatchTime", time);

        if (time < 140 && time > 130) {  // auto and transition, everyone is active
            return true;
        } else if (time < 130 && time > 105) {  // shift 1
            return !winAuto;
        } else if (time < 105 && time > 80) {  // shift 2
            return winAuto;
        } else if (time < 80 && time > 55) {  // shift 3
            return !winAuto;
        } else if (time < 55 && time > 30) {  // shift 4
            return winAuto;
        } else if (time < 30 && time > 0) {  // end game, everyone is active
            return true;
        }
        return false;
    }

    public static double timeLeft() {
        double time = DriverStation.getMatchTime();
        if (time < 140 && time > 130) {  // auto and transition, everyone is active
            return time - 130;
        } else if (time < 130 && time > 105) {  // shift 1
            return time - 105;
        } else if (time < 105 && time > 80) {  // shift 2
            return time - 80;
        } else if (time < 80 && time > 55) {  // shift 3
            return time - 55;
        } else if (time < 55 && time > 30) {  // shift 4
            return time - 30;
        } else if (time < 30 && time > 0) {  // end game, everyone is active
            return time;
        }
        return -1;
    }


}
