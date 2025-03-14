import json
import pathlib


from .pathing_generation_utils.choreo_utils import (
    generate_pose_variables_file,
    generate_drive_to_pose_variable_file,
)


def generate_choreo_variables_files(choreo_data, project_dir, package_name):
    generate_pose_variables_file(choreo_data, project_dir, package_name)
    generate_drive_to_pose_variable_file(choreo_data, project_dir, package_name)


def main():
    project_dir = pathlib.Path("y2025/Reefscape")
    choreo_file = "y2025/Reefscape/src/main/deploy/choreo/ChoreoAutos.chor"
    package_name = "com.gos.reefscape"

    choreo_data = json.load(open(choreo_file))

    generate_choreo_variables_files(choreo_data, project_dir, package_name)


if __name__ == "__main__":
    # py -m y2025.Reefscape.choreo_variables_to_java
    main()
