package frc.robot.commands;

import frc.robot.subsystems.Limelight;
import com.gos.lib.DeadbandHelper;
import frc.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AlignLeftRight extends CommandBase {

    private final Limelight m_limelight;
    private final Chassis m_chassis;
    private final DeadbandHelper m_deadbandHelper;

    public AlignLeftRight(Chassis chassis, Limelight limelight) {
        this.m_limelight = limelight;
        this.m_chassis = chassis;
        m_deadbandHelper = new DeadbandHelper(5);

        addRequirements(limelight, chassis);

    }

    @Override
    public void initialize() {
        m_limelight.zoom1X();
    }

    @Override
    public void execute() {
        m_chassis.setSteer(m_limelight.getSteerCommand());
        m_limelight.zoomIfNeeded();
    }

    @Override
    public void end(boolean interrupted) {
        m_limelight.zoom1X();
    }

    @Override
    public boolean isFinished() {
        boolean isFinished = m_limelight.limelightIsAimed();
        m_deadbandHelper.setIsGood(isFinished);
        if (m_deadbandHelper.isFinished()) {
            System.out.println("Done!");
            return true;
        }
        else {
            return false;
        }
    }

}
