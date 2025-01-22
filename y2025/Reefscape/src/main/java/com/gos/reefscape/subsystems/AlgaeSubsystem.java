package com.gos.reefscape.subsystems;


import com.gos.reefscape.Constants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeSubsystem extends SubsystemBase {


    private final SparkFlex m_algaeMotor;
    private final DigitalInput m_algaeSensor;

     public AlgaeSubsystem() {
        m_algaeMotor = new SparkFlex(Constants.ALGAE_MOTOR_ID, MotorType.kBrushless);
        m_algaeSensor = new DigitalInput(Constants.ALGAE_SENSOR_ID);

     }


public void intakeStop(){m_algaeMotor.set(0);}
public void algaeIntakeOut(){m_algaeMotor.set(-1);}
public void algaeIntakeIn(){m_algaeMotor.set(1);}
public boolean hasAlgae(){return !m_algaeSensor.get();}

public Command createMoveIntakeOutCommand(){
    return this.runEnd(this::algaeIntakeOut, this::intakeStop).withName("Intake Out");
}
public Command createIntakeUntilAlgaeCommand(){
    return createMoveIntakeInCommand().until(this::hasAlgae).withName("Intake Till Piece");
}
public Command createMoveIntakeInCommand(){
        return this.runEnd(this::algaeIntakeIn, this::intakeStop).withName("Intake In");
    }
}

