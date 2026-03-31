package com.gos.rebuilt.subsystems;


import com.gos.rebuilt.Constants;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.gos.rebuilt.subsystems.PivotSide.DEPLOYED_ANGLE;

public class PivotSubsystem extends SubsystemBase {

    private final PivotSide m_left;
    private final PivotSide m_right;

    public PivotSubsystem() {
        m_left = new PivotSide(Constants.PIVOT_MOTOR_LEFT, true, "Left");
        m_right = new PivotSide(Constants.PIVOT_MOTOR_RIGHT, false, "Right");
    }


    @Override
    public void periodic() {
        m_left.periodic();
        m_right.periodic();
    }


    @Override
    public void simulationPeriodic() {
        m_left.simulationPeriodic();
        m_right.simulationPeriodic();
    }

    public void addPivotDebugCommands(boolean areWeAtACompetitionOrNotBoolean) {
        ShuffleboardTab debugTabPivot = Shuffleboard.getTab("arm pivot");
        if (!areWeAtACompetitionOrNotBoolean) {
            debugTabPivot.add(createPivotMoveToSpeed());

            debugTabPivot.add(createMovePivotUpCommand());
            debugTabPivot.add(createMovePivotDownCommand());

            debugTabPivot.add(createMovePivotToAngleCommand(0.0));
            debugTabPivot.add(createMovePivotToAngleCommand(15.0));
            debugTabPivot.add(createMovePivotToAngleCommand(30.0));
            debugTabPivot.add(createMovePivotToAngleCommand(45.0));
            debugTabPivot.add(createMovePivotToAngleCommand(90.0));

        }
        debugTabPivot.add(createPivotToCoastModeCommand().withName("Move pivot with coasting"));
        debugTabPivot.add(createResetEncoderUpCommand());
        debugTabPivot.add(createResetEncoderDownCommand());
        debugTabPivot.add(createSyncEncoderCommand());
    }

    private void syncEncoders() {
        m_left.syncEncoders();
        m_right.syncEncoders();
    }

    private void moveArmToAngle(double angle) {
        m_left.moveArmToAngle(angle);
        m_right.moveArmToAngle(angle);
    }

    public void stop() {
        m_left.stop();
        m_right.stop();
    }

    public void setSpeed(double speed) {
        m_left.setSpeed(speed);
        m_right.setSpeed(speed);
    }

    public void resetEncoderUp() {
        m_left.resetEncoderUp();
        m_right.resetEncoderUp();
    }

    public void resetEncoderDown() {
        m_left.resetEncoderDown();
        m_right.resetEncoderDown();
    }

    public void movePivotAtTuningSpeed() {
        m_left.movePivotAtTuningSpeed();
        m_right.movePivotAtTuningSpeed();
    }

    private void setIdleMode(IdleMode idleMode) {
        m_left.setIdleMode(idleMode);
        m_right.setIdleMode(idleMode);
    }

    public void clearStickyFaults() {
        m_left.clearStickyFaults();
        m_right.clearStickyFaults();
    }

    public double getAngle() {
        return m_left.getAngle();
    }

    public double getGoalAngle() {
        return m_left.getGoalAngle();
    }

    public Command createSyncEncoderCommand() {
        return run(this::syncEncoders).withName("Sync Encoders");
    }

    public Command createMovePivotDownCommand() {
        return runEnd(() -> moveArmToAngle(DEPLOYED_ANGLE), this::stop)
            .withName("Go down");
    }

    public Command createMovePivotDownCommandLowMotorPercentage() {
        return runEnd(() -> setSpeed(.02), this::stop)
            .withName("Go down with set speed 2%");
    }

    public Command createMovePivotUpCommand() {
        return runEnd(() -> moveArmToAngle(0), this::stop)
            .withName("Go up");
    }

    public Command createMovePivotToAngleCommand(double angle) {
        return runEnd(() -> moveArmToAngle(angle), this::stop)
            .withName("Go to angle" + angle);
    }

    public Command createPivotMoveToSpeed() {
        return runEnd(this::movePivotAtTuningSpeed, this::stop).withName("move pivot to speed");
    }

    public Command createResetEncoderUpCommand() {
        return run(this::resetEncoderUp).withName("Reset Encoder UP").ignoringDisable(true);
    }

    public Command createResetEncoderDownCommand() {
        return run(this::resetEncoderDown).withName("Reset Encoder DOWN").ignoringDisable(true);
    }

    public Command createPivotToCoastModeCommand() {
        return this.runEnd(
                () -> setIdleMode(IdleMode.kCoast),
                () -> setIdleMode(IdleMode.kBrake))
            .ignoringDisable(true).withName("Pivot to Coast");
    }
}

