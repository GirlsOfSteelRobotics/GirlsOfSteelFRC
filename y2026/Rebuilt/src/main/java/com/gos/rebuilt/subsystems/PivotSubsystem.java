package com.gos.rebuilt.subsystems;


import com.gos.rebuilt.Constants;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;

public class PivotSubsystem extends SubsystemBase {

    private final SparkFlex m_pivotMotor;
    private static final double GEAR_RATIO = 45.0;

    private SingleJointedArmSimWrapper m_pivotSimulator;


    public PivotSubsystem() {
        m_pivotMotor = new SparkFlex(Constants.PIVOT_MOTOR, MotorType.kBrushless);


        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeoVortex(1);
            SingleJointedArmSim armSim = new SingleJointedArmSim(gearbox, GEAR_RATIO, .01,
                0.381, Units.degreesToRadians(-220), Units.degreesToRadians(90), true, 0);
            m_pivotSimulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivotMotor, gearbox),
                RevEncoderSimWrapper.create(m_pivotMotor), true);
        }
    }

    public void setSpeed(double pow) {
        m_pivotMotor.set(pow);
    }

    public void stop() {
        m_pivotMotor.stopMotor();
    }

   @Override
    public void simulationPeriodic() {
        m_pivotSimulator.update();
    }



}

