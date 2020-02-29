package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.*;
import frc.robot.commands.autonomous.SingleShoot;
import frc.robot.subsystems.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public XboxController m_drivingPad;
    public XboxController m_operatingPad;

    public OI(Chassis chassis, ControlPanel controlPanel, Limelight limelight, Camera camera,
                Shooter shooter, ShooterIntake shooterIntake, ShooterConveyor shooterConveyor,
                 Lift lift, Winch winch) {
        m_drivingPad = new XboxController(0);
        m_operatingPad = new XboxController(1);

        new JoystickButton(m_operatingPad, Button.kA.value).whileHeld(new AutomatedConveyorIntake(shooterIntake, shooterConveyor));
        new JoystickButton(m_operatingPad, Button.kB.value).whileHeld(new RunShooterRPMWhileHeld(shooter, Constants.DEFAULT_RPM));
        //new JoystickButton(m_operatingPad, Button.kBumperLeft.value).whileHeld(new RunShooterRPMWhileHeld(shooter, 2000));
        new JoystickButton(m_operatingPad, Button.kX.value).whenPressed(new MovePiston(shooterIntake, true));       
        new JoystickButton(m_operatingPad, Button.kBumperLeft.value).whileHeld(new IntakeCells(shooterIntake, true));
        new edu.wpi.first.wpilibj2.command.button.Button(() -> m_operatingPad.getTriggerAxis(Hand.kLeft) > .8).whileHeld(new IntakeCells(shooterIntake, false));
        new JoystickButton(m_operatingPad, Button.kBumperRight.value).whileHeld(new Conveyor(shooterConveyor, true));
        new edu.wpi.first.wpilibj2.command.button.Button(() -> m_operatingPad.getTriggerAxis(Hand.kRight) > .8).whileHeld(new Conveyor(shooterConveyor, false));
        //new JoystickButton(m_operatingPad, Button.kBack.value).whileHeld();
        new JoystickButton(m_operatingPad, Button.kX.value).whenPressed(new MovePiston(shooterIntake, true));       
        new JoystickButton(m_operatingPad, Button.kY.value).whenPressed(new MovePiston(shooterIntake, false));
        new POVButton(m_operatingPad, 90).whenPressed(new TuneRPM(shooter));
        new POVButton(m_operatingPad, 0).whenPressed(new ConveyorAdvanceOneUnit(shooterConveyor));
        new POVButton(m_operatingPad, 180).whenPressed(new SingleShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM)); 
        new JoystickButton(m_operatingPad, Button.kStart.value).whileHeld(new RunShooterRPMWhileHeld(shooter, Constants.LONG_RPM));
 
        new JoystickButton(m_drivingPad, Button.kA.value).whileHeld(new HangerLift(lift, true));
        new JoystickButton(m_drivingPad, Button.kB.value).whileHeld(new HangerLift(lift, false));
        //new JoystickButton(m_drivingPad, Button.kB.value).whileHeld(new WinchWind(winch, true));
        //new JoystickButton(m_drivingPad, Button.kA.value).whileHeld(new WinchWind(winch, false));
        new edu.wpi.first.wpilibj2.command.button.Button(() -> m_drivingPad.getTriggerAxis(Hand.kLeft) > .8).whileHeld(new WinchWind(winch, false));
        new edu.wpi.first.wpilibj2.command.button.Button(() -> m_drivingPad.getTriggerAxis(Hand.kRight) > .8).whileHeld(new WinchWind(winch, true));
        new JoystickButton(m_drivingPad, Button.kY.value).whenPressed(new RotationControl(controlPanel));
        new POVButton(m_drivingPad, 0).whenPressed(new SwitchToCamClimb(camera));
        new POVButton(m_drivingPad, 180).whenPressed(new SwitchToCamIntake(camera));
        new POVButton(m_drivingPad, 90).whenHeld(new SpinControlPanel(controlPanel));
        new POVButton(m_drivingPad, 270).whenHeld(new AlignLeftRight(chassis, limelight));
        //new POVButton(m_drivingPad, 90).whenHeld(new AlignForwardBackward(chassis, limelight));
        
        new JoystickButton(m_drivingPad, Button.kBumperLeft.value).whileHeld(new DriveLessByJoystickWhenPressed(chassis, this));
        //new JoystickButton(m_drivingPad, Button.kB.value).whenPressed(new DriveByJoystick(chassis, this));


    }

    // Y is negated so that pushing the joystick forward results in positive values
    public double getJoystickSpeed() {
        return -m_drivingPad.getY(Hand.kLeft);
    }

    public double getJoystickSpin() {
        return m_drivingPad.getX(Hand.kRight);
    }

}
