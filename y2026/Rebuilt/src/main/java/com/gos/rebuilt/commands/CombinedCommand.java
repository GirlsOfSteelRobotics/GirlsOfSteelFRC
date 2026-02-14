package com.gos.rebuilt.commands;

import com.gos.rebuilt.subsystems.ChassisSubsystem;
import com.gos.rebuilt.subsystems.FeederSubsystem;
import com.gos.rebuilt.subsystems.IntakeSubsystem;
import com.gos.rebuilt.subsystems.PivotSubsystem;
import com.gos.rebuilt.subsystems.PizzaSubsystem;
import com.gos.rebuilt.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class CombinedCommand {

    private final ChassisSubsystem m_chassisSubsystem;
    private final FeederSubsystem m_feederSubsystem;
    private final PizzaSubsystem m_pizzaSubsystem;
    private final IntakeSubsystem m_intakeSubsystem; //NOPMD
    private final ShooterSubsystem m_shooterSubsystem;
    private final PivotSubsystem m_pivotSubsystem; //NOPMD

    public CombinedCommand(ChassisSubsystem chassis, FeederSubsystem feeder, PizzaSubsystem pizza, IntakeSubsystem intake, ShooterSubsystem shooter, PivotSubsystem pivot) {
        m_chassisSubsystem = chassis;
        m_pizzaSubsystem = pizza;
        m_intakeSubsystem = intake;
        m_shooterSubsystem = shooter;
        m_pivotSubsystem = pivot;
        m_feederSubsystem = feeder;
    }

    public Command shootBall() {
        return m_chassisSubsystem.createFaceHub()
            .alongWith((m_shooterSubsystem.createShootFromDistanceCommand(m_chassisSubsystem::getDistanceFromHub)))
            .alongWith(new WaitUntilCommand(this::readyToShoot)
                .andThen(m_pizzaSubsystem.createPizzaSpin500().alongWith(m_feederSubsystem.createFeederSpin500())));
    }

    public boolean readyToShoot() {
        return m_chassisSubsystem.facingHub() &&  m_shooterSubsystem.isAtGoalRPM();
    }

    public void createCombinedCommand(boolean inComp) {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Combined Commands");
        if (!inComp) {
            debugTab.add(shootBall().withName("Shoot"));
        }
    }
}
