import os
import sys
import shutil

if "BUILD_WORKSPACE_DIRECTORY" in os.environ:
    os.chdir(os.environ["BUILD_WORKSPACE_DIRECTORY"])
else:
    sys.path.append(os.path.join(os.path.dirname(os.path.realpath(__file__)), "..", "..", ".."))

from libraries.scripts.updater.utils import walk_for_extension, regex_replace_file


def __run_replacement(replacements, root=".", dir_blacklist=None):

    java_files = walk_for_extension(root, "java", dir_blacklist=dir_blacklist)

    for java_file in java_files:
        regex_replace_file(java_file, replacements)


def run_converesion(project_path):
    old_vendor_dep = os.path.join(project_path, "vendordeps", "WPILibOldCommands.json")
    if os.path.exists(old_vendor_dep):
        os.remove(old_vendor_dep)

    new_vendor_dep = os.path.join(project_path, "vendordeps", "WPILibNewCommands.json")
    print(new_vendor_dep)
    if not os.path.exists(new_vendor_dep):
        shutil.copy("y2022/RapidReact/vendordeps/WPILibNewCommands.json", new_vendor_dep)

    replacements = []
    # fmt: off
    replacements.append(("import edu\.wpi\.first\.wpilibj\.command\.Command;", "import edu.wpi.first.wpilibj2.command.CommandBase;"))
    replacements.append(("import edu\.wpi\.first\.wpilibj\.command\.Subsystem;", "import edu.wpi.first.wpilibj2.command.SubsystemBase;"))
    replacements.append(("import edu\.wpi\.first\.wpilibj\.command\.CommandGroup;", "import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;"))
    replacements.append(("import edu\.wpi\.first\.wpilibj\.command\.Scheduler;", "import edu.wpi.first.wpilibj2.command.CommandScheduler;"))
    replacements.append(("import edu\.wpi\.first\.wpilibj\.command\.InstantCommand;", "import edu.wpi.first.wpilibj2.command.InstantCommand;"))
    replacements.append(("import edu\.wpi\.first\.wpilibj\.command\.PrintCommand;", "import edu.wpi.first.wpilibj2.command.PrintCommand;"))
    replacements.append(("import edu\.wpi\.first\.wpilibj\.command\.WaitCommand;", "import edu.wpi.first.wpilibj2.command.WaitCommand;"))

    replacements.append(("import edu\.wpi\.first\.wpilibj\.buttons\.Button;", "import edu.wpi.first.wpilibj2.command.button.Button;"))
    replacements.append(("import edu\.wpi\.first\.wpilibj\.buttons\.JoystickButton;", "import edu.wpi.first.wpilibj2.command.button.JoystickButton;"))
    replacements.append(("import edu\.wpi\.first\.wpilibj\.buttons\.POVButton;", "import edu.wpi.first.wpilibj2.command.button.POVButton;"))
    replacements.append(("import edu\.wpi\.first\.wpilibj\.buttons\.Trigger;", "import edu.wpi.first.wpilibj2.command.button.Trigger;"))

    replacements.append(("import edu.wpi.first.wpilibj.PIDController;", "import edu.wpi.first.math.controller.PIDController;"))

    replacements.append((r"@Override\n    protected boolean isFinished\(\) {", "@Override\n    public boolean isFinished() {"))
    replacements.append((r"@Override\n    protected void execute\(\) {", "@Override\n    public void execute() {"))
    replacements.append((r"@Override\n    protected void end\(\) {", "@Override\n    public void end(boolean interrupted) {"))
    replacements.append((r"@Override\n    protected void initialize\(\) {", "@Override\n    public void initialize() {"))
    replacements.append((r"    \/\/ Called when another command which requires one or more of the same\n    \/\/ subsystems is scheduled to run\n    \@Override\n    protected void interrupted\(\) \{\n        end\(\)\;\n    \}", ""))
    replacements.append((r"    \/\/ Called when another command which requires one or more of the same\n    \/\/ subsystems is scheduled to run\n    \@Override\n    protected void interrupted\(\) \{\n    \}", ""))
    replacements.append((r"    \@Override\n    protected void interrupted\(\) \{\n        \/\/ TODO Auto\-generated method stub\n        end\(\)\;\n    \}", ""))
    replacements.append((r"    \@Override\n    protected void interrupted\(\) \{\n        end\(\)\;\n    \}", ""))
    replacements.append((r"    \@Override\n    protected void interrupted\(\) \{\n        end\(\)\;\n        \/\/ TODO Auto\-generated method stub\n\n    \}", ""))
    replacements.append((r"    \@Override\n    protected void interrupted\(\) \{\n        \/\/ TODO Auto\-generated method stub\n\n    \}", ""))
    replacements.append((r"    \@Override\n    protected void interrupted\(\) \{\n    \}", ""))
    replacements.append((r"    \@Override\n    protected void interrupted\(\) \{\n\n    \}", ""))

    replacements.append((r"        requires\(", "        addRequirements("))

    replacements.append((r"    \@Override\n    public void initDefaultCommand\(\) \{\n        \/\/ Set the default command for a subsystem here\.\n        \/\/ setDefaultCommand\(new MySpecialCommand\(\)\)\;\n    \}", ""))
    replacements.append((r"    \@Override\n    public void initDefaultCommand\(\) \{\n        \/\/ Set the default command for a subsystem here\.\n    \}", ""))
    replacements.append((r"    \@Override\n    public void initDefaultCommand\(\) \{\n        \/\/ Set the default command for a subsystem here\.\n        \/\/setDefaultCommand\(new MySpecialCommand\(\)\)\;\n    \}", ""))
    replacements.append((r"    \@Override\n    public void initDefaultCommand\(\) \{\n    \}", ""))
    replacements.append((r"    \@Override\n    public void initDefaultCommand\(\) \{\n\n    \}", ""))
    replacements.append((r"    \@Override\n    public void initDefaultCommand\(\) \{\n        \/\/ Set the default command for a subsystem here\.\n        \/\/setDefaultCommand\(new MySpecialCommand\(\)\)\;\n\n    \}", ""))
    replacements.append((r"    \@Override\n    public void initDefaultCommand\(\) \{\n        \/\/ Set the default command for a subsystem here\.\n        \/\/setDefaultCommand\(new MySpecialCommand\(\)\)\;\n        \/\/There\'s no default command for this subsystem\n    \}", ""))
    replacements.append((r"    \@Override\n    protected void initDefaultCommand\(\) \{\n    \}", ""))

    replacements.append((r" Scheduler\.getInstance\(\)", " CommandScheduler.getInstance()"))

    replacements.append(("addSequential", "addCommands"))
    replacements.append((r"m_autonomousCommand\.start\(\);", "m_autonomousCommand.schedule();"))

    replacements.append(("extends Command {", "extends CommandBase {"))
    replacements.append(("extends Subsystem {", "extends SubsystemBase {"))
    replacements.append(("extends CommandGroup {", "extends SequentialCommandGroup {"))
    # fmt: on

    __run_replacement(replacements, root=project_path)


def main():

    robot_projects = [
        "old_robots/y2011/GOS2011/robolatest",
        "old_robots/y2012/Watson2012",
        "old_robots/y2013/Eve2013",
        "old_robots/y2014/GOS2014",
        "old_robots/y2015/GoS2015",
        "old_robots/y2016/Neohuman_Assassination_Golem",
        "old_robots/y2016/OutreachBot",
        "old_robots/y2016/TeamFBI_EOPP",
        "old_robots/y2016/TeamSquirtle",
        "old_robots/y2017/Secondary_Human_Training_Golem",
        "old_robots/y2017/Team1",
        "old_robots/y2017/Team_2",
        "y2018/2018Beta",
        "y2018/2018PowerUp",
        "y2019/2019DeepSpace",
        "y2019/OffenseBot",
    ]

    for project_path in robot_projects:
        run_converesion(project_path)


if __name__ == "__main__":
    main()
