package girlsofsteel.tests;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Chassis extends CommandGroup {
    
    public Chassis(){
        addSequential(new ChassisJags());
        //adds chassis PID as soon as the user says testing jags is done
        addSequential(new ChassisPID());
    } 
}

