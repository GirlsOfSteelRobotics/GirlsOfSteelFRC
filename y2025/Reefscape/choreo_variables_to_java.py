import json
import re


def camel_case_to_snake_case(name):
    pattern = re.compile(r"(?<!^)(?=[A-Z])")
    name = pattern.sub("_", name).lower()
    return name.upper()


def write_variables_file(choreo_data, output_file):
    output_contents = """package com.gos.reefscape.generated;

import com.gos.reefscape.MaybeFlippedPose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class ChoreoPoses {
"""

    for poseName in choreo_data["variables"]["poses"]:
        pose = choreo_data["variables"]["poses"][poseName]
        constant_name = camel_case_to_snake_case(poseName)
        constant_name = re.sub("^([A-Z])_([A-Z])$", r"\1\2", constant_name)
        output_contents += f"    public static final MaybeFlippedPose2d {constant_name} = new MaybeFlippedPose2d({pose['x']['val']}, {pose['y']['val']}, Rotation2d.fromRadians({pose['heading']['val']}));\n"

    output_contents += "\n}\n"
    with open(output_file, "w") as f:
        f.write(output_contents)

    return output_contents


def write_drive_to_variables_file(choreo_data, output_file):
    output_contents = """package com.gos.reefscape.generated;

import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DriveToPositionDebugTab {
    private final ChassisSubsystem m_chassisSubsystem;

    public DriveToPositionDebugTab(ChassisSubsystem chassis) {
        m_chassisSubsystem = chassis;
    }


    public void createMoveRobotToPositionCommand() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Move Robot To Position");
"""

    for poseName in choreo_data["variables"]["poses"]:
        constant_name = camel_case_to_snake_case(poseName)
        constant_name = re.sub("^([A-Z])_([A-Z])$", r"\1\2", constant_name)
        output_contents += f'        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.{constant_name}).withName("{constant_name}"));\n'

    output_contents += "    }\n}\n"
    with open(output_file, "w") as f:
        f.write(output_contents)

    return output_contents


def main():
    choreo_file = "y2025/Reefscape/src/main/deploy/choreo/ChoreoAutos.chor"
    poses_file = "y2025/Reefscape/src/main/java/com/gos/reefscape/generated/ChoreoPoses.java"
    debug_tab_file = (
        "y2025/Reefscape/src/main/java/com/gos/reefscape/generated/DriveToPositionDebugTab.java"
    )

    choreo_data = json.load(open(choreo_file))

    write_variables_file(choreo_data, poses_file)
    write_drive_to_variables_file(choreo_data, debug_tab_file)


if __name__ == "__main__":
    # py -m y2025.Reefscape.choreo_variables_to_java
    main()
