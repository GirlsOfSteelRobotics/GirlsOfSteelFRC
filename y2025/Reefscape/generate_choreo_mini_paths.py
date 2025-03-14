import json
import pathlib
from jinja2 import Template
from y2025.Reefscape.pathplanner_utils import write_pathplanner_auto


def load_choreo_variables(choreo_file: pathlib.Path):
    data = json.loads(choreo_file.read_text())
    variables = {}
    for var_name, var in data["variables"]["poses"].items():
        print(var_name)
        variables[var_name] = dict(
            name=var_name, x=var["x"]["val"], y=var["y"]["val"], heading=var["heading"]["val"]
        )

    return variables


def create_path(choreo_dir, variables, first_variable, second_variable):
    filename = f"{first_variable}To{second_variable}"

    contents = Template(TRAJECTORY_TEMPLATE).render(
        name=filename,
        first_pose=variables[first_variable],
        second_pose=variables[second_variable],
    )

    path_to_write = choreo_dir / f"{filename}.traj"
    path_to_write.write_text(contents)

    return filename


def main():
    root_dir = pathlib.Path(r".")
    choreo_dir = root_dir / r"y2025\Reefscape\src\main\deploy\choreo"
    pathplanner_dir = root_dir / r"y2025\Reefscape\src\main\deploy\pathplanner/autos"
    variables = load_choreo_variables(choreo_dir / r"ChoreoAutos.chor")

    generate_from_starting_positions(choreo_dir, pathplanner_dir, variables)
    generate_reef_to_hp(choreo_dir, pathplanner_dir, variables)
    generate_algae_to_processor(choreo_dir, pathplanner_dir, variables)
    generate_algae_to_net(choreo_dir, pathplanner_dir, variables)


def generate_reef_to_hp(choreo_dir, pathplanner_dir, variables):
    reef_to_human_player_left = []
    for reef_position in ["A", "B", "L", "K", "J", "I", "G", "H"]:
        reef_to_human_player_left.append(
            create_path(choreo_dir, variables, reef_position, "HumanPlayerLeft")
        )
        create_path(choreo_dir, variables, "HumanPlayerLeft", reef_position)
    write_pathplanner_auto(
        reef_to_human_player_left, pathplanner_dir / "ToLeftHumanPlayer.auto", "Mini Paths"
    )

    reef_to_human_player_right = []
    for reef_position in ["A", "B", "C", "D", "E", "F", "G", "H"]:
        reef_to_human_player_right.append(
            create_path(choreo_dir, variables, reef_position, "HumanPlayerRight")
        )
        create_path(choreo_dir, variables, "HumanPlayerRight", reef_position)
    write_pathplanner_auto(
        reef_to_human_player_right, pathplanner_dir / "ToRightHumanPlayer.auto", "Mini Paths"
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


def generate_algae_to_net(choreo_dir, pathplanner_dir, variables):
    algae_to_net = []
    net_to_algae = []
    for algae_position in ["GH", "EF", "IJ"]:
        algae_to_net.append(create_path(choreo_dir, variables, algae_position, "BlueNet"))
        net_to_algae.append(create_path(choreo_dir, variables, "BlueNet", algae_position))

    write_pathplanner_auto(net_to_algae, pathplanner_dir / "NetToAlgae.auto", "Mini Paths")
    write_pathplanner_auto(algae_to_net, pathplanner_dir / "AlgaeToNet.auto", "Mini Paths")


TRAJECTORY_TEMPLATE = """{
 "name":"{{ name }}",
 "version":1,
 "snapshot":{
  "waypoints":[],
  "constraints":[],
  "targetDt":0.05
 },
 "params":{
  "waypoints":[
    {"x":{"exp":"{{ first_pose.name }}.x", "val":{{ first_pose.x }}}, "y":{"exp":"{{ first_pose.name }}.y", "val":{{ first_pose.y }}}, "heading":{"exp":"{{ first_pose.name }}.heading", "val":{{ first_pose.heading }}}, "intervals":40, "split":false, "fixTranslation":true, "fixHeading":true, "overrideIntervals":false},
    {"x":{"exp":"{{ second_pose.name }}.x", "val":{{ second_pose.x }}}, "y":{"exp":"{{ second_pose.name }}.y", "val":{{ second_pose.y }}}, "heading":{"exp":"{{ second_pose.name }}.heading", "val":{{ second_pose.heading }}}, "intervals":40, "split":false, "fixTranslation":true, "fixHeading":true, "overrideIntervals":false}],
  "constraints":[
    {"from":"first", "to":null, "data":{"type":"StopPoint", "props":{}}, "enabled":true},
    {"from":"last", "to":null, "data":{"type":"StopPoint", "props":{}}, "enabled":true},
    {"from":0, "to":1, "data":{"type":"MaxVelocity", "props":{"max":{"exp":"DefaultMaxVelocity", "val":1.524}}}, "enabled":true}],
  "targetDt":{
   "exp":"0.05 s",
   "val":0.05
  }
 },
 "trajectory":{
  "sampleType":null,
  "waypoints":[],
  "samples":[],
  "splits":[]
 },
 "events":[]
}

"""

if __name__ == "__main__":
    # py -m y2025.Reefscape.generate_choreo_mini_paths
    main()
