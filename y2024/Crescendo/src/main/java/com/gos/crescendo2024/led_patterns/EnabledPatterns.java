package com.gos.crescendo2024.led_patterns;

import com.gos.crescendo2024.led_patterns.subpatterns.HasPiecePattern;
import com.gos.crescendo2024.led_patterns.subpatterns.InShooterPattern;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;
import com.gos.lib.led.LEDBoolean;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class EnabledPatterns {
    // Subsystems
    private final IntakeSubsystem m_intake;
    private final ArmPivotSubsystem m_arm;
    private final ChassisSubsystem m_chassis;
    private final ShooterSubsystem m_shooter;

    // Patterns
    private final LEDBoolean m_armGood;
    private final LEDBoolean m_chassisGood;
    private final LEDBoolean m_shooterGood;
    private final LEDBoolean m_noteSeen;
    //    private final AprilTagPattern m_aprilTagsSeen;
    private final LEDBoolean m_hasPieceConstant;
    private final HasPiecePattern m_hasPiecePattern;
    private final InShooterPattern m_inShooterPattern;

    public EnabledPatterns(AddressableLEDBuffer buffer, int numberOfLeds, IntakeSubsystem intake, ChassisSubsystem chassis, ArmPivotSubsystem arm, ShooterSubsystem shooter) {
        m_intake = intake;
        m_chassis = chassis;
        m_shooter = shooter;
        m_arm = arm;

        // TODO(gpr) Refactor patterns to show april tags and always-present "has piece"
        m_inShooterPattern = new InShooterPattern(buffer, numberOfLeds);
        m_hasPiecePattern = new HasPiecePattern(buffer, numberOfLeds);
        m_armGood = new LEDBoolean(buffer, 0, 10, Color.kGreen, Color.kRed);
        m_chassisGood = new LEDBoolean(buffer, 10, 20, Color.kGreen, Color.kRed);
        m_shooterGood = new LEDBoolean(buffer, 20, 30, Color.kGreen, Color.kRed);
        m_noteSeen = new LEDBoolean(buffer, 30, 40, Color.kOrange, Color.kBlack);
        //        m_aprilTagsSeen = new AprilTagPattern(buffer, 40, 52, chassis);
        m_hasPieceConstant = new LEDBoolean(buffer, 40, 52,  Color.kTomato, Color.kBlack);
    }

    public void writeLED() {
        m_armGood.setStateAndWrite(m_arm.isArmAtGoal());
        m_shooterGood.setStateAndWrite(m_shooter.isShooterAtGoal());
        m_chassisGood.setStateAndWrite(m_chassis.isAngleAtGoal());
        m_noteSeen.setStateAndWrite(m_chassis.isNoteDetected());
        //        m_aprilTagsSeen.writeLED();
        m_hasPieceConstant.setStateAndWrite(m_intake.hasGamePiece());
        m_hasPiecePattern.update(m_intake.hasGamePiece());
        m_hasPiecePattern.writeLeds();
        m_inShooterPattern.update(m_shooter.isPieceInShooter());
        m_inShooterPattern.writeLeds();
    }
}
