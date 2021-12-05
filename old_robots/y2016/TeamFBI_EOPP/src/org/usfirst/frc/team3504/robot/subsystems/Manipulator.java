package org.usfirst.frc.team3504.robot.subsystems;


    import edu.wpi.first.wpilibj.SpeedController;
    import org.usfirst.frc.team3504.robot.RobotMap;

            import edu.wpi.first.wpilibj.command.Subsystem;

    /**
     *
     */
    public class Manipulator extends Subsystem {
        private final SpeedController conveyorBeltMotorRight = RobotMap.conveyorBeltMotorRight;
        private final SpeedController conveyorBeltMotorLeft = RobotMap.conveyorBeltMotorLeft;

        // Put methods for controlling this subsystem
        // here. Call these from Commands.

        @Override
        public void initDefaultCommand() {
            // Set the default command for a subsystem here.
            //setDefaultCommand(new MySpecialCommand());
        }

        public void manipulatorConveyorBeltMotorRight (boolean fwd) {
            if (fwd) {
                conveyorBeltMotorRight.set(1.0);
            }
            else{
                conveyorBeltMotorRight.set(-1.0);
            }

        }

        public void manipulatorConveyorBeltMotorLeft (boolean fwd) {
            if (fwd) {
                conveyorBeltMotorLeft.set(1.0);
            }
            else{
                conveyorBeltMotorLeft.set(-1.0);
            }

        }

        public void stopConveyorBeltMotor() {
            conveyorBeltMotorRight.set(0.0);
            conveyorBeltMotorLeft.set(0.0);
        }

    }
