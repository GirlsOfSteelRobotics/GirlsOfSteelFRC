package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lift;

public class HangerLift extends CommandBase {
    private final Lift m_hangerLift;
    private final boolean m_lift;

    public HangerLift(Lift hangerLift, boolean lift) {
        this.m_hangerLift = hangerLift;
        this.m_lift = lift;

        super.addRequirements(hangerLift);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (m_lift)    {
            m_hangerLift.liftUp();
        }
        else {
            m_hangerLift.liftDown();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_hangerLift.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
