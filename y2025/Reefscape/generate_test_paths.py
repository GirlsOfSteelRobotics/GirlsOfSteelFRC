import pathlib
from .pathing_generation_utils.choreo_utils import run_choreo_cli
from .pathing_generation_utils.test_paths_utils import (
    generate_straight_paths,
    generate_rotation_paths,
    write_debug_tab_file,
)


def generate_test_paths(project_dir, package_name, output_trajectory_dir, run_cli):
    start_waypoint = dict(x=0.4172362983226776, y=1.748888373374939, heading=0)
    end_waypoint = dict(x=7.863987445831299, y=1.748888373374939, heading=0)

    all_paths = []

    straight_accelerations = [1, 4, 9, None]
    straight_velocities = [1, 5, 10, 13, None]
    all_paths.extend(
        generate_straight_paths(
            output_trajectory_dir,
            straight_accelerations,
            straight_velocities,
            start_waypoint,
            end_waypoint,
        )
    )

    angular_velocities = [20, 45, 90, 180, 270, 360, None]
    all_paths.extend(generate_rotation_paths(output_trajectory_dir, angular_velocities))

    if run_cli:
        run_choreo_cli(all_paths)

    write_debug_tab_file(project_dir, output_trajectory_dir, package_name, all_paths)


def main():
    project_dir = pathlib.Path("y2025/Reefscape")
    choreo_dir = project_dir / r"src\main\deploy\choreo"

    package_name = "com.gos.reefscape"
    # debug_paths_file = (
    #         root_dir / "y2025/Reefscape/src/main/java/com/gos/reefscape/generated/DebugPathsTab.java"
    # )
    run_cli = True

    generate_test_paths(project_dir, package_name, choreo_dir, run_cli)


if __name__ == "__main__":
    # py -m y2025.Reefscape.generate_test_paths
    main()
