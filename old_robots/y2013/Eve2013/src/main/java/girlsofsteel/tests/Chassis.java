package girlsofsteel.tests;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Chassis extends CommandGroup {

    public Chassis(girlsofsteel.subsystems.Chassis chassis) {
        addSequential(new ChassisJags(chassis));
        //adds chassis PID as soon as the user says testing jags is done
        addSequential(new ChassisPID(chassis));
    }
}
