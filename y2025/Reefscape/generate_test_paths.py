import pathlib

from .pathing_generation_utils.choreo_utils import run_choreo_cli
from .pathing_generation_utils.test_paths_utils import (
    generate_straight_paths,
    generate_rotation_paths,
    write_debug_tab_file,
)
from .pathing_generation_utils.choreo_file import ChoreoFile, Waypoint, Variable


def generate_test_paths(
    choreo_file: ChoreoFile, project_dir, package_name, output_trajectory_dir, run_cli
):
    starting_pos_right = choreo_file.pose_variables["StartingPosRight"]

    start_waypoint = Waypoint()
    start_waypoint.x = Variable.from_inches(16)
    start_waypoint.y = Variable.from_sub_variable(starting_pos_right, "y")
    start_waypoint.heading = Variable.from_degrees(0)

    end_waypoint = Waypoint()
    end_waypoint.x = Variable.from_sub_variable(starting_pos_right, "x")
    end_waypoint.y = Variable.from_sub_variable(starting_pos_right, "y")
    end_waypoint.heading = Variable.from_degrees(0)

    all_paths = []

    straight_accelerations = [None]
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

    rotation_waypoints = [
        Waypoint.from_xy_deg(x=0.4172362983226776, y=1.748888373374939, heading=0),
        Waypoint.from_xy_deg(x=4.14061187208, y=1.748888373374939, heading=180),
        Waypoint.from_xy_deg(x=7.863987445831299, y=1.748888373374939, heading=0),
    ]

    angular_velocities = [20, 45, 90, 180, 270, 360, None]
    all_paths.extend(
        generate_rotation_paths(output_trajectory_dir, angular_velocities, rotation_waypoints)
    )

    if run_cli:
        run_choreo_cli(all_paths)

    write_debug_tab_file(project_dir, output_trajectory_dir, package_name, all_paths)


def main():
    project_dir = pathlib.Path("y2025/Reefscape")
    choreo_dir = project_dir / r"src\main\deploy\choreo"

    package_name = "com.gos.reefscape"
    run_cli = True

    generate_test_paths(
        ChoreoFile(choreo_dir / "ChoreoAutos.chor"), project_dir, package_name, choreo_dir, run_cli
    )


if __name__ == "__main__":
    # py -m y2025.Reefscape.generate_test_paths
    main()
