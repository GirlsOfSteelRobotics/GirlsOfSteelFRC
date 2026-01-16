package com.gos.rebuilt.subsystems;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
    private final SparkFlex m_climberMotor;
    private final RelativeEncoder m_climberEncoder;
    private final GosDoubleProperty CLIMBING_SPEED = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES,"climbingSpeed",1);

    public ClimberSubsystem(){
        m_climberMotor = new SparkFlex(2, MotorType.kBrushless);
        m_climberEncoder = m_climberMotor.getEncoder();
    }

    public void climbUp(){
        m_climberMotor.set(CLIMBING_SPEED.getValue());
    }
    public void climbDown(){
        m_climberMotor.set(-CLIMBING_SPEED.getValue());
    }
    public void stop(){
        m_climberMotor.stopMotor();
    }
    public void climbToHeight(double height){

    }

    public Command createClimbingUpCommand() {
        return this.runEnd(this::climbUp, this::stop).withName("Climb Up");
    }
    public Command createClimbingDownCommand() {
        return this.runEnd(this::climbDown, this::stop).withName("Climb Down");
    }
    public void addClimberDebugCommands(){
        ShuffleboardTab tab = Shuffleboard.getTab("Climber");
        tab.add(createClimbingUpCommand());
        tab.add(createClimbingDownCommand());
    }
}
