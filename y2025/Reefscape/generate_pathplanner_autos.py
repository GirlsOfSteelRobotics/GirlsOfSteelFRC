import json
import pathlib
from .pathing_generation_utils.pathplanner_utils import write_pathplanner_auto


def generate_multi_coral_auto(pathplanner_dir, side, coral_positions):
    paths = []

    paths.append(f"StartingPos{side}To{coral_positions[0]}")
    for i in range(1, len(coral_positions)):
        this_coral = coral_positions[i - 1]
        next_coral = coral_positions[i]
        paths.append(f"{this_coral}ToHumanPlayer{side}")
        paths.append(f"HumanPlayer{side}To{next_coral}")

    name = f"{side}.coral." + ".".join(coral_positions) + ".auto"
    write_pathplanner_auto(paths, pathplanner_dir / name, "Autos")


def generate_multi_processor_auto(pathplanner_dir, side, coral, algae_positions):
    paths = []

    paths.append(f"StartingPos{side}To{coral}")
    paths.append(f"{coral}To{algae_positions[0]}")
    for i in range(1, len(algae_positions)):
        this_algae = algae_positions[i - 1]
        next_algae = algae_positions[i]
        paths.append(f"{this_algae}ToProcessor")
        paths.append(f"ProcessorTo{next_algae}")

    paths.append(f"{algae_positions[-1]}ToProcessor")

    name = f"{side}.processor." + "_".join(algae_positions) + ".auto"
    write_pathplanner_auto(paths, pathplanner_dir / name, "Autos")


def generate_multi_net_auto(pathplanner_dir, side, coral, algae_positions):
    paths = []

    paths.append(f"StartingPos{side}To{coral}")
    paths.append(f"{coral}To{algae_positions[0]}")
    for i in range(1, len(algae_positions)):
        this_algae = algae_positions[i - 1]
        next_algae = algae_positions[i]
        paths.append(f"{this_algae}ToBlueNet")
        paths.append(f"BlueNetTo{next_algae}")

    paths.append(f"{algae_positions[-1]}ToBlueNet")

    name = f"{side}.net." + "_".join(algae_positions) + ".auto"
    write_pathplanner_auto(paths, pathplanner_dir / name, "Autos")


def generate_pathplanner_autos(pathplanner_dir):
    # Right
    generate_multi_coral_auto(pathplanner_dir, "Right", ["E", "C", "B"])

    # Center
    generate_multi_coral_auto(pathplanner_dir, "Center", ["G"])
    generate_multi_processor_auto(pathplanner_dir, "Center", "H", ["GH", "EF", "IJ"])
    generate_multi_net_auto(pathplanner_dir, "Center", "H", ["GH", "EF"])

    # Left
    generate_multi_coral_auto(pathplanner_dir, "Left", ["I", "L", "J", "K"])


def delete_existing_auto_modes(pathplanner_dir):
    for auto_file in pathplanner_dir.glob("*.auto"):
        with open(auto_file) as f:
            json_contents = json.load(f)

        if "folder" in json_contents and json_contents["folder"] == "Autos":
            auto_file.unlink()


def main():
    root_dir = pathlib.Path(r".")
    pathplanner_dir = root_dir / r"y2025\Reefscape\src\main\deploy\pathplanner/autos"

    delete_existing_auto_modes(pathplanner_dir)
    generate_pathplanner_autos(pathplanner_dir)


if __name__ == "__main__":
    # py -m y2025.Reefscape.generate_pathplanner_autos
    main()
