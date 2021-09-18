package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class DriveAtVelocity extends CommandBase {


    private final Chassis m_chassis;
    private final double m_velocity;
   


    public DriveAtVelocity(Chassis chassis, double velocity) {
        this.m_chassis = chassis;
        this.m_velocity = velocity;

        

        addRequirements(chassis);
    }
        
        

    @Override
    public void execute() { 
        m_chassis.smartVelocityControl(m_velocity, m_velocity);;
        // m_chassis.smartVelocityControl(48, 48);

    
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
        
    

    @Override
    public void end(boolean interrupted) {
        m_chassis.setSpeed(0);
        System.out.println("Drive At Velocity, end");
       
    }
}
