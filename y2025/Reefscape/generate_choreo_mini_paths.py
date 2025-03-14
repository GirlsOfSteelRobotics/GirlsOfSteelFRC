import pathlib
from .pathing_generation_utils.choreo_utils import run_choreo_cli
from .pathing_generation_utils.pathplanner_utils import write_pathplanner_auto
from .pathing_generation_utils.mini_paths_utils import (
    create_path_between_variables,
    load_choreo_variables,
)

create_path = create_path_between_variables


def generate_reef_to_hp(choreo_dir, pathplanner_dir, variables):
    reef_to_human_player_left = []
    human_player_left_to_reef = []
    for reef_position in ["A", "B", "L", "K", "J", "I", "G", "H"]:
        reef_to_human_player_left.append(
            create_path(choreo_dir, variables, reef_position, "HumanPlayerLeft")
        )
        human_player_left_to_reef.append(
            create_path(choreo_dir, variables, "HumanPlayerLeft", reef_position)
        )
    write_pathplanner_auto(
        reef_to_human_player_left, pathplanner_dir / "ToLeftHumanPlayer.auto", "Mini Paths"
    )

    reef_to_human_player_right = []
    human_player_right_to_reef = []
    for reef_position in ["A", "B", "C", "D", "E", "F", "G", "H"]:
        reef_to_human_player_right.append(
            create_path(choreo_dir, variables, reef_position, "HumanPlayerRight")
        )
        human_player_right_to_reef.append(
            create_path(choreo_dir, variables, "HumanPlayerRight", reef_position)
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


def generate_from_starting_positions(choreo_dir, pathplanner_dir, variables):
    start_to_reef = []
    for reef_position in ["D", "E", "F", "G"]:
        start_to_reef.append(create_path(choreo_dir, variables, "StartingPosRight", reef_position))

    for reef_position in ["H", "I", "J", "K"]:
        start_to_reef.append(create_path(choreo_dir, variables, "StartingPosLeft", reef_position))

    for reef_position in ["H", "G"]:
        start_to_reef.append(create_path(choreo_dir, variables, "StartingPosCenter", reef_position))

    write_pathplanner_auto(start_to_reef, pathplanner_dir / "StartToReef.auto", "Mini Paths")

    return start_to_reef


def generate_algae_to_processor(choreo_dir, pathplanner_dir, variables):
    algae_to_processor = []
    processor_to_algae = []
    for algae_position in ["AB", "CD", "EF", "GH", "EF", "IJ"]:
        algae_to_processor.append(create_path(choreo_dir, variables, algae_position, "Processor"))
        processor_to_algae.append(create_path(choreo_dir, variables, "Processor", algae_position))

    write_pathplanner_auto(
        algae_to_processor, pathplanner_dir / "AlgaeToProcessor.auto", "Mini Paths"
    )
    write_pathplanner_auto(
        processor_to_algae, pathplanner_dir / "ProcessorToAlgae.auto", "Mini Paths"
    )

    return algae_to_processor + processor_to_algae


def generate_algae_to_net(choreo_dir, pathplanner_dir, variables):
    algae_to_net = []
    net_to_algae = []
    for algae_position in ["GH", "EF", "IJ"]:
        algae_to_net.append(create_path(choreo_dir, variables, algae_position, "BlueNet"))
        net_to_algae.append(create_path(choreo_dir, variables, "BlueNet", algae_position))

    write_pathplanner_auto(net_to_algae, pathplanner_dir / "NetToAlgae.auto", "Mini Paths")
    write_pathplanner_auto(algae_to_net, pathplanner_dir / "AlgaeToNet.auto", "Mini Paths")

    return algae_to_net + net_to_algae


def generate_reef_to_algae(choreo_dir, variables):
    paths = []
    paths.append(create_path(choreo_dir, variables, "H", "GH"))
    return paths


def generate_choreo_mini_paths(choreo_file, traj_output_dir, pathplanner_dir, run_cli):
    variables = load_choreo_variables(choreo_file)

    all_paths = []

    all_paths.extend(generate_from_starting_positions(traj_output_dir, pathplanner_dir, variables))
    all_paths.extend(generate_reef_to_hp(traj_output_dir, pathplanner_dir, variables))
    all_paths.extend(generate_algae_to_processor(traj_output_dir, pathplanner_dir, variables))
    all_paths.extend(generate_algae_to_net(traj_output_dir, pathplanner_dir, variables))
    all_paths.extend(generate_reef_to_algae(traj_output_dir, variables))

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
