package com.gos.reefscape.subsystems;

import com.gos.reefscape.enums.PIECoral;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OperatorCoralCommand extends SubsystemBase {
    private PIECoral m_selectedSetpoint = PIECoral.L2;

    public Command changeCoralPosition(PIECoral coralPosition) {
        return runOnce(() -> m_selectedSetpoint = coralPosition).ignoringDisable(true);
    }

    public PIECoral getSetpoint() {
        return m_selectedSetpoint;
    }

    @Override
    public void periodic() {
        SmartDashboard.putString("current coral position", m_selectedSetpoint.toString());
    }

    public void tellCoralPosition() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("coral position");
        debugTab.add(changeCoralPosition(PIECoral.L1).withName("L1"));
        debugTab.add(changeCoralPosition(PIECoral.L2).withName("L2"));
        debugTab.add(changeCoralPosition(PIECoral.L3).withName("L3"));
        debugTab.add(changeCoralPosition(PIECoral.L4).withName("L4"));



    }
}
