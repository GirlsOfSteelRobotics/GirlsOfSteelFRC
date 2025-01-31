import json


def write_variables(choreo_data):
    output = ""

    for poseName in choreo_data["variables"]["poses"]:
        pose = choreo_data["variables"]["poses"][poseName]
        output += f"    public static final Pose2d {poseName} = new Pose2d({pose['x']['val']}, {pose['y']['val']}, Rotation2d.fromRadians({pose['heading']['val']}));\n"
    return output


def main():
    choreo_file = "y2025/Reefscape/src/main/deploy/choreo/ChoreoAutos.chor"
    output_file = "y2025/Reefscape/src/main/java/com/gos/reefscape/ChoreoPoses.java"

    choreo_data = json.load(open(choreo_file))


    output_contents = """package com.gos.reefscape;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class ChoreoPoses {
"""

    output_contents += write_variables(choreo_data)

    output_contents += "\n}"
    with open(output_file, "w") as f:
        f.write(output_contents)


if __name__ == "__main__":
    # py -m y2025.Reefscape.choreo_variables_to_java
    main()
