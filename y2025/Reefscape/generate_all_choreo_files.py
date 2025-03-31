from .pathing_generation_utils.choreo_file import ChoreoFile
from .choreo_variables_to_java import generate_choreo_variables_files
from .generate_test_paths import generate_test_paths
from .generate_pathplanner_autos import generate_pathplanner_autos
from .generate_choreo_mini_paths import generate_choreo_mini_paths

import json
import pathlib


def delete_pathplanner_autos(project_dir):
    pathplanner_dir = project_dir / "src/main/deploy/pathplanner/autos"
    for auto in pathplanner_dir.glob("*.auto"):
        auto.unlink()


def delete_generated_java(project_dir, package_name):
    generated_java_dir = (
        project_dir / "src/main/java" / package_name.replace(".", "/") / "generated"
    )
    for f in generated_java_dir.glob("*.java"):
        f.unlink()


def delete_all_files(project_dir, package_name):
    delete_pathplanner_autos(project_dir)
    delete_generated_java(project_dir, package_name)

    choreo_dir = project_dir / "src/main/deploy/choreo"
    for traj in choreo_dir.glob("*.traj"):
        traj.unlink()


def generate_all(project_dir, choreo_file, package_name, delete_existing_files, run_cli):
    choreo_data = json.load(open(choreo_file))

    pathplanner_dir = project_dir / "src/main/deploy/pathplanner/autos"

    if delete_existing_files:
        # delete_pathplanner_autos(project_dir)
        # delete_generated_java(project_dir, package_name)
        delete_all_files(project_dir, package_name)
        traj_output_dir = choreo_file.parent
    else:
        traj_output_dir = choreo_file.parent

    generate_choreo_variables_files(choreo_data, project_dir, package_name)
    generate_test_paths(ChoreoFile(choreo_file), project_dir, package_name, traj_output_dir, run_cli)
    generate_pathplanner_autos(pathplanner_dir)
    generate_choreo_mini_paths(ChoreoFile(choreo_file), traj_output_dir, pathplanner_dir, run_cli)


def main():
    delete_existing_files = True
    run_cli = True
    project_dir = pathlib.Path("y2025/Reefscape")
    choreo_file = project_dir / "src/main/deploy/choreo/ChoreoAutos.chor"
    package_name = "com.gos.reefscape"

    generate_all(project_dir, choreo_file, package_name, delete_existing_files, run_cli)


if __name__ == "__main__":
    # py -m y2025.Reefscape.generate_all_choreo_files
    main()
