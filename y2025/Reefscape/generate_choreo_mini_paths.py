import pathlib

from .pathing_generation_utils.choreo_utils import run_choreo_cli
from .pathing_generation_utils.pathplanner_utils import write_pathplanner_auto

from .pathing_generation_utils.choreo_file import (
    ChoreoFile,
    create_intermediate_point_back_up_pose,
)

from .pathing_generation_utils.choreo_constraints import (
    create_velocity_constraint,
    create_keep_in_lane_constraint,
    create_stop_point_constraint,
)

from .pathing_generation_utils.mini_paths_utils import (
    create_event_marker,
    create_path_between_waypoints,
    create_path_from_waypoints_with_straight_backoff,
)


def generate_reef_to_hp(
    choreo_file: ChoreoFile, trajectory_output_dir: pathlib.Path, pathplanner_dir: pathlib.Path
):
    def to_and_from_reef_helper(
        choreo_file: ChoreoFile,
        trajectory_output_dir: pathlib.Path,
        start_variable_name: str,
        end_variable_name: str,
    ):
        events = []
        if len(start_variable_name) == 1:
            backoff_variable_name = start_variable_name
            distance_variable = "CoralBackupDistance"
        else:
            distance_variable = "CoralApproachDistance"
            backoff_variable_name = end_variable_name
            events.append(create_event_marker(1, -0.1, "RaiseElevator", "ElevatorPrepTime"))
        return create_path_from_waypoints_with_straight_backoff(
            choreo_file,
            trajectory_output_dir,
            start_variable_name,
            end_variable_name,
            backoff_from_variable_name=backoff_variable_name,
            backoff_distance_variable_name=distance_variable,
            velocity_variable_name="DefaultMaxVelocity",
            events=events,
        )

    reef_to_human_player_left = []
    human_player_left_to_reef = []
    for reef_position in ["A", "B", "L", "K", "J", "I"]:
        reef_to_human_player_left.append(
            to_and_from_reef_helper(
                choreo_file,
                trajectory_output_dir,
                reef_position,
                "HumanPlayerLeft",
            )
        )
        human_player_left_to_reef.append(
            to_and_from_reef_helper(
                choreo_file,
                trajectory_output_dir,
                "HumanPlayerLeft",
                reef_position,
            )
        )
    write_pathplanner_auto(
        reef_to_human_player_left, pathplanner_dir / "ToLeftHumanPlayer.auto", "Mini Paths"
    )
    write_pathplanner_auto(
        human_player_left_to_reef, pathplanner_dir / "LeftHumanPlayerToReef.auto", "Mini Paths"
    )

    reef_to_human_player_right = []
    human_player_right_to_reef = []
    for reef_position in ["A", "B", "C", "D", "E", "F"]:
        reef_to_human_player_right.append(
            to_and_from_reef_helper(
                choreo_file,
                trajectory_output_dir,
                reef_position,
                "HumanPlayerRight",
            )
        )
        human_player_right_to_reef.append(
            to_and_from_reef_helper(
                choreo_file,
                trajectory_output_dir,
                "HumanPlayerRight",
                reef_position,
            )
        )
    write_pathplanner_auto(
        reef_to_human_player_right, pathplanner_dir / "ToRightHumanPlayer.auto", "Mini Paths"
    )
    write_pathplanner_auto(
        human_player_right_to_reef, pathplanner_dir / "RightHumanPlayerToReef.auto", "Mini Paths"
    )

    return (
        reef_to_human_player_left
        + reef_to_human_player_right
        + human_player_right_to_reef
        + human_player_left_to_reef
    )


def generate_from_starting_pos(
    choreo_file: ChoreoFile, trajectory_output_dir: pathlib.Path, pathplanner_dir: pathlib.Path
):
    def starting_position_helper(start_variable_name: str, end_variable_name: str):
        events = [create_event_marker(1, -0.1, "RaiseElevator", "ElevatorPrepTime")]
        return create_path_from_waypoints_with_straight_backoff(
            choreo_file,
            trajectory_output_dir,
            start_variable_name,
            end_variable_name,
            backoff_from_variable_name=end_variable_name,
            backoff_distance_variable_name="CoralApproachDistance",
            velocity_variable_name="DefaultPreloadSpeed",
            events=events,
        )

    start_to_reef = []
    for reef_position in ["D", "E", "F", "G"]:
        pass
        start_to_reef.append(
            starting_position_helper(
                "StartingPosRight",
                reef_position,
            )
        )

    for reef_position in ["H", "I", "J", "K"]:
        start_to_reef.append(
            starting_position_helper(
                "StartingPosLeft",
                reef_position,
            )
        )

    for reef_position in ["H", "G"]:
        start_to_reef.append(
            starting_position_helper(
                "StartingPosCenter",
                reef_position,
            )
        )

    write_pathplanner_auto(start_to_reef, pathplanner_dir / "StartToReef.auto", "Mini Paths")

    return start_to_reef


def algae_backup_helper(
    choreo_file: ChoreoFile,
    trajectory_output_dir: pathlib.Path,
    start_variable_name: str,
    end_variable_name: str,
):
    if len(start_variable_name) == 2:
        backoff_variable_name = start_variable_name
    else:
        backoff_variable_name = end_variable_name
    return create_path_from_waypoints_with_straight_backoff(
        choreo_file,
        trajectory_output_dir,
        start_variable_name,
        end_variable_name,
        backoff_from_variable_name=backoff_variable_name,
        backoff_distance_variable_name="AlgaeBackupDistance",
        velocity_variable_name="DrivingWithAlgaeVelocity",
        always_include_keep_in_lane=True,
    )


def generate_algae_to_processor(
    choreo_file: ChoreoFile, trajectory_output_dir: pathlib.Path, pathplanner_dir: pathlib.Path
):
    algae_to_processor = []
    processor_to_algae = []
    for algae_position in ["AB", "CD", "EF", "GH", "EF", "IJ"]:
        algae_to_processor.append(
            algae_backup_helper(
                choreo_file,
                trajectory_output_dir,
                algae_position,
                "Processor",
            )
        )
        processor_to_algae.append(
            algae_backup_helper(
                choreo_file,
                trajectory_output_dir,
                "Processor",
                algae_position,
            )
        )

    write_pathplanner_auto(
        algae_to_processor, pathplanner_dir / "AlgaeToProcessor.auto", "Mini Paths"
    )
    write_pathplanner_auto(
        processor_to_algae, pathplanner_dir / "ProcessorToAlgae.auto", "Mini Paths"
    )

    return algae_to_processor + processor_to_algae


def generate_algae_to_net(
    choreo_file: ChoreoFile, trajectory_output_dir: pathlib.Path, pathplanner_dir: pathlib.Path
):
    algae_to_net = []
    net_to_algae = []
    for algae_position in ["GH", "EF", "IJ"]:
        algae_to_net.append(
            algae_backup_helper(
                choreo_file,
                trajectory_output_dir,
                algae_position,
                "BlueNet",
            )
        )
        net_to_algae.append(
            algae_backup_helper(
                choreo_file,
                trajectory_output_dir,
                "BlueNet",
                algae_position,
            )
        )

    write_pathplanner_auto(net_to_algae, pathplanner_dir / "NetToAlgae.auto", "Mini Paths")
    write_pathplanner_auto(algae_to_net, pathplanner_dir / "AlgaeToNet.auto", "Mini Paths")

    return algae_to_net + net_to_algae


def coral_to_algae_helper(
    choreo_file: ChoreoFile,
    trajectory_output_dir: pathlib.Path,
    start_variable_name: str,
    end_variable_name: str,
):
    events = []
    velocity_variable_name = "DefaultCoralToAlgaeVelocity"

    if len(start_variable_name) == 1:
        constraints = [
            create_velocity_constraint(choreo_file, velocity_variable_name, 0, 2),
            create_stop_point_constraint(1),
        ]
        intermediate_waypoints = [
            create_intermediate_point_back_up_pose(
                choreo_file,
                choreo_file.pose_variables[end_variable_name],
                "CoralToAlgaeBackup",
                split=True,
                fix_heading=False,
            )
        ]
    else:
        constraints = [
            create_velocity_constraint(choreo_file, velocity_variable_name, 0, 2),
            create_keep_in_lane_constraint(1, 2),
        ]
        intermediate_waypoints = []

    filename = f"{start_variable_name}To{end_variable_name}"

    first_waypoint = choreo_file.pose_variables[start_variable_name]
    last_waypoint = choreo_file.pose_variables[end_variable_name]

    waypoints = [first_waypoint] + intermediate_waypoints + [last_waypoint]

    return create_path_between_waypoints(
        trajectory_output_dir, filename, waypoints, constraints, events
    )


def generate_reef_to_algae(
    choreo_file: ChoreoFile, trajectory_output_dir: pathlib.Path, pathplanner_dir: pathlib.Path
):
    paths = []
    for algae_pos in ["EF", "GH", "IJ"]:
        paths.append(
            coral_to_algae_helper(choreo_file, trajectory_output_dir, algae_pos[0], algae_pos)
        )
        paths.append(
            coral_to_algae_helper(choreo_file, trajectory_output_dir, algae_pos[1], algae_pos)
        )

    write_pathplanner_auto(paths, pathplanner_dir / "CoralToAlgae.auto", "Mini Paths")
    return paths


def generate_ice_creams(
    choreo_file: ChoreoFile, trajectory_output_dir: pathlib.Path, pathplanner_dir: pathlib.Path
):
    def ice_cream_helper(start_variable_name: str, end_variable_name: str):
        if "IceCream" in start_variable_name:
            backoff_variable_name = start_variable_name
        else:
            backoff_variable_name = end_variable_name
        return create_path_from_waypoints_with_straight_backoff(
            choreo_file,
            trajectory_output_dir,
            start_variable_name,
            end_variable_name,
            backoff_from_variable_name=backoff_variable_name,
            backoff_distance_variable_name="AlgaeBackupDistance",
            velocity_variable_name="DefaultMaxVelocity",
        )

    all_paths = []

    all_paths.append(ice_cream_helper("Processor", "RightIceCream"))
    all_paths.append(ice_cream_helper("C", "RightIceCream"))
    all_paths.append(ice_cream_helper("D", "RightIceCream"))
    all_paths.append(ice_cream_helper("RightIceCream", "Processor"))

    all_paths.append(ice_cream_helper("J", "LeftIceCream"))
    all_paths.append(ice_cream_helper("K", "LeftIceCream"))
    all_paths.append(ice_cream_helper("LeftIceCream", "BlueNet"))

    return all_paths


def generate_choreo_mini_paths(
    choreo_file: ChoreoFile,
    traj_output_dir: pathlib.Path,
    pathplanner_dir: pathlib.Path,
    run_cli: bool,
):
    all_paths = []

    all_paths.extend(generate_from_starting_pos(choreo_file, traj_output_dir, pathplanner_dir))
    all_paths.extend(generate_reef_to_hp(choreo_file, traj_output_dir, pathplanner_dir))
    all_paths.extend(generate_algae_to_processor(choreo_file, traj_output_dir, pathplanner_dir))
    all_paths.extend(generate_algae_to_net(choreo_file, traj_output_dir, pathplanner_dir))
    all_paths.extend(generate_reef_to_algae(choreo_file, traj_output_dir, pathplanner_dir))
    all_paths.extend(generate_ice_creams(choreo_file, traj_output_dir, pathplanner_dir))

    if run_cli:
        run_choreo_cli(all_paths)


def main():
    root_dir = pathlib.Path(r".")
    choreo_dir = root_dir / r"y2025\Reefscape\src\main\deploy\choreo"
    pathplanner_dir = root_dir / r"y2025\Reefscape\src\main\deploy\pathplanner/autos"
    run_cli = True

    choreo_file = ChoreoFile(choreo_dir / r"ChoreoAutos.chor")

    generate_choreo_mini_paths(choreo_file, choreo_dir, pathplanner_dir, run_cli)


if __name__ == "__main__":
    # py -m y2025.Reefscape.generate_choreo_mini_paths
    main()
