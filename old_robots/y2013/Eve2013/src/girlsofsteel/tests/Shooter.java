package girlsofsteel.tests;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Shooter extends CommandGroup {

    public Shooter(){
        addSequential(new ShooterJags());
        addSequential(new ShooterPID());
    }

}
