import json
import math
import pathlib

from .pathing_generation_utils.choreo_utils import run_choreo_cli
from .pathing_generation_utils.pathplanner_utils import write_pathplanner_auto
from .pathing_generation_utils.mini_paths_utils import (
    create_path_between_variables,
    load_choreo_pose_variables,
    load_choreo_velocity_variables,
    load_choreo_distance_variables,
    velocity_variable_to_constraint,
    variable_to_waypoint,
    create_path_between_waypoints,
    create_keep_in_lane_constraint,
    create_stop_point_constraint,
)

create_path = create_path_between_variables


def generate_reef_to_hp(choreo_dir, pathplanner_dir, pose_variables):
    reef_to_human_player_left = []
    human_player_left_to_reef = []
    for reef_position in ["A", "B", "L", "K", "J", "I", "G", "H"]:
        reef_to_human_player_left.append(
            create_path(choreo_dir, pose_variables, reef_position, "HumanPlayerLeft")
        )
        human_player_left_to_reef.append(
            create_path(choreo_dir, pose_variables, "HumanPlayerLeft", reef_position)
        )
    write_pathplanner_auto(
        reef_to_human_player_left, pathplanner_dir / "ToLeftHumanPlayer.auto", "Mini Paths"
    )

    reef_to_human_player_right = []
    human_player_right_to_reef = []
    for reef_position in ["A", "B", "C", "D", "E", "F", "G", "H"]:
        reef_to_human_player_right.append(
            create_path(choreo_dir, pose_variables, reef_position, "HumanPlayerRight")
        )
        human_player_right_to_reef.append(
            create_path(choreo_dir, pose_variables, "HumanPlayerRight", reef_position)
        )
    write_pathplanner_auto(
        reef_to_human_player_right, pathplanner_dir / "ToRightHumanPlayer.auto", "Mini Paths"
    )

    return (
        reef_to_human_player_left
        + reef_to_human_player_right
        + human_player_right_to_reef
        + human_player_left_to_reef
    )


def generate_from_starting_positions(choreo_dir, pathplanner_dir, pose_variables, vel_variables):
    constraints = [velocity_variable_to_constraint(vel_variables, "DefaultPreloadSpeed", 0, 1)]

    start_to_reef = []
    for reef_position in ["D", "E", "F", "G"]:
        start_to_reef.append(
            create_path(choreo_dir, pose_variables, "StartingPosRight", reef_position, constraints)
        )

    for reef_position in ["H", "I", "J", "K"]:
        start_to_reef.append(
            create_path(choreo_dir, pose_variables, "StartingPosLeft", reef_position, constraints)
        )

    for reef_position in ["H", "G"]:
        start_to_reef.append(
            create_path(choreo_dir, pose_variables, "StartingPosCenter", reef_position, constraints)
        )

    write_pathplanner_auto(start_to_reef, pathplanner_dir / "StartToReef.auto", "Mini Paths")

    return start_to_reef


def algae_backup_helper(
    choreo_dir,
    pose_variables,
    distance_variables,
    first_variable,
    second_variable,
    base_constraints,
):
    filename = f"{first_variable}To{second_variable}"

    start_waypoint = variable_to_waypoint(pose_variables, first_variable)
    end_waypoint = variable_to_waypoint(pose_variables, second_variable)

    if len(first_variable) == 2:
        constraints = base_constraints + [create_keep_in_lane_constraint(0, 1)]
        reef_variable_name = first_variable
    else:
        constraints = base_constraints + [create_keep_in_lane_constraint(1, 2)]
        reef_variable_name = second_variable
    reef_var = pose_variables[reef_variable_name]

    backup_x = reef_var["x"] - distance_variables["AlgaeBackupDistance"]["value"]["val"] * math.cos(
        reef_var["heading"]
    )
    backup_y = reef_var["y"] - distance_variables["AlgaeBackupDistance"]["value"]["val"] * math.sin(
        reef_var["heading"]
    )
    intermediate_waypoints = [
        json.dumps(
            {
                "x": {
                    "exp": f"{ reef_var['name'] }.x - AlgaeBackupDistance * cos({ reef_var['name'] }.heading)",
                    "val": backup_x,
                },
                "y": {
                    "exp": f"{ reef_var['name'] }.y - AlgaeBackupDistance * sin({ reef_var['name'] }.heading)",
                    "val": backup_y,
                },
                "heading": {"exp": f"{ reef_var['name'] }.heading", "val": reef_var["heading"]},
                "intervals": 40,
                "split": False,
                "fixTranslation": True,
                "fixHeading": True,
                "overrideIntervals": False,
            }
        )
    ]

    waypoints = [start_waypoint] + intermediate_waypoints + [end_waypoint]
    return create_path_between_waypoints(choreo_dir, filename, waypoints, constraints)


def coral_to_algae_helper(
    choreo_dir,
    pose_variables,
    distance_variables,
    first_variable,
    second_variable,
    base_constraints,
):
    filename = f"{first_variable}To{second_variable}"

    start_waypoint = variable_to_waypoint(pose_variables, first_variable)
    end_waypoint = variable_to_waypoint(pose_variables, second_variable)

    constraints = base_constraints

    if len(first_variable) == 2:
        reef_variable_name = first_variable
    else:
        reef_variable_name = second_variable
    reef_var = pose_variables[reef_variable_name]

    backup_x = reef_var["x"] - distance_variables["CoralToAlgaeBackup"]["value"]["val"] * math.cos(
        reef_var["heading"]
    )
    backup_y = reef_var["y"] - distance_variables["CoralToAlgaeBackup"]["value"]["val"] * math.sin(
        reef_var["heading"]
    )
    intermediate_waypoints = [
        json.dumps(
            {
                "x": {
                    "exp": f"{ reef_var['name'] }.x - CoralToAlgaeBackup * cos({ reef_var['name'] }.heading)",
                    "val": backup_x,
                },
                "y": {
                    "exp": f"{ reef_var['name'] }.y - CoralToAlgaeBackup * sin({ reef_var['name'] }.heading)",
                    "val": backup_y,
                },
                "heading": {"exp": "0 deg", "val": 0},
                "intervals": 40,
                "split": True,
                "fixTranslation": True,
                "fixHeading": False,
                "overrideIntervals": False,
            }
        )
    ]

    waypoints = [start_waypoint] + intermediate_waypoints + [end_waypoint]
    return create_path_between_waypoints(choreo_dir, filename, waypoints, constraints)


def generate_algae_to_processor(
    choreo_dir, pathplanner_dir, pose_variables, vel_variables, distance_variables
):
    constraints = [
        velocity_variable_to_constraint(vel_variables, "DefaultMaxVelocity", 0, 2),
    ]

    algae_to_processor = []
    processor_to_algae = []
    for algae_position in ["AB", "CD", "EF", "GH", "EF", "IJ"]:
        algae_to_processor.append(
            algae_backup_helper(
                choreo_dir,
                pose_variables,
                distance_variables,
                algae_position,
                "Processor",
                constraints,
            )
        )
        processor_to_algae.append(
            algae_backup_helper(
                choreo_dir,
                pose_variables,
                distance_variables,
                "Processor",
                algae_position,
                constraints,
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
    choreo_dir, pathplanner_dir, pose_variables, vel_variables, distance_variables
):
    constraints = [
        velocity_variable_to_constraint(vel_variables, "DefaultMaxVelocity", 0, 2),
    ]

    algae_to_net = []
    net_to_algae = []
    for algae_position in ["GH", "EF", "IJ"]:
        algae_to_net.append(
            algae_backup_helper(
                choreo_dir,
                pose_variables,
                distance_variables,
                algae_position,
                "BlueNet",
                constraints,
            )
        )
        net_to_algae.append(
            algae_backup_helper(
                choreo_dir,
                pose_variables,
                distance_variables,
                "BlueNet",
                algae_position,
                constraints,
            )
        )

    write_pathplanner_auto(net_to_algae, pathplanner_dir / "NetToAlgae.auto", "Mini Paths")
    write_pathplanner_auto(algae_to_net, pathplanner_dir / "AlgaeToNet.auto", "Mini Paths")

    return algae_to_net + net_to_algae


def generate_reef_to_algae(
    choreo_dir, pathplanner_dir, pose_variables, vel_variables, distance_variables
):
    constraints = [
        velocity_variable_to_constraint(vel_variables, "DefaultCoralToAlgaeVelocity", 0, 2),
        create_stop_point_constraint(1),
    ]

    paths = []
    for algae_pos in ["EF", "GH", "IJ"]:
        paths.append(
            coral_to_algae_helper(
                choreo_dir, pose_variables, distance_variables, algae_pos[0], algae_pos, constraints
            )
        )
        paths.append(
            coral_to_algae_helper(
                choreo_dir, pose_variables, distance_variables, algae_pos[1], algae_pos, constraints
            )
        )

    write_pathplanner_auto(paths, pathplanner_dir / "CoralToAlgae.auto", "Mini Paths")
    return paths


def generate_choreo_mini_paths(choreo_file, traj_output_dir, pathplanner_dir, run_cli):
    pose_variables = load_choreo_pose_variables(choreo_file)
    vel_variables = load_choreo_velocity_variables(choreo_file)
    distance_variables = load_choreo_distance_variables(choreo_file)

    all_paths = []

    all_paths.extend(
        generate_from_starting_positions(
            traj_output_dir, pathplanner_dir, pose_variables, vel_variables
        )
    )
    all_paths.extend(generate_reef_to_hp(traj_output_dir, pathplanner_dir, pose_variables))
    all_paths.extend(
        generate_algae_to_processor(
            traj_output_dir, pathplanner_dir, pose_variables, vel_variables, distance_variables
        )
    )
    all_paths.extend(
        generate_algae_to_net(
            traj_output_dir, pathplanner_dir, pose_variables, vel_variables, distance_variables
        )
    )
    all_paths.extend(
        generate_reef_to_algae(
            traj_output_dir, pathplanner_dir, pose_variables, vel_variables, distance_variables
        )
    )

    if run_cli:
        run_choreo_cli(all_paths)


def main():
    root_dir = pathlib.Path(r".")
    choreo_dir = root_dir / r"y2025\Reefscape\src\main\deploy\choreo"
    pathplanner_dir = root_dir / r"y2025\Reefscape\src\main\deploy\pathplanner/autos"
    choreo_file = choreo_dir / r"ChoreoAutos.chor"
    run_cli = True

    generate_choreo_mini_paths(choreo_file, choreo_dir, pathplanner_dir, run_cli)


if __name__ == "__main__":
    # py -m y2025.Reefscape.generate_choreo_mini_paths
    main()
