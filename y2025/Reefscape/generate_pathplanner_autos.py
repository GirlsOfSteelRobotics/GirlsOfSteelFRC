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

    name = f"{side}" + "".join(coral_positions) + ".auto"
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

    name = f"{side}" + "_".join(algae_positions) + ".auto"
    write_pathplanner_auto(paths, pathplanner_dir / name, "Autos")


def generate_pathplanner_autos(pathplanner_dir):
    generate_multi_coral_auto(pathplanner_dir, "Right", ["E", "B", "C"])
    generate_multi_coral_auto(pathplanner_dir, "Right", ["G", "F", "E", "D", "C", "B"])

    generate_multi_processor_auto(pathplanner_dir, "Center", "H", ["GH", "EF", "IJ"])

    generate_multi_coral_auto(pathplanner_dir, "Left", ["H", "I", "J", "K", "L", "A"])


def main():
    root_dir = pathlib.Path(r".")
    pathplanner_dir = root_dir / r"y2025\Reefscape\src\main\deploy\pathplanner/autos"

    generate_pathplanner_autos(pathplanner_dir)


if __name__ == "__main__":
    # py -m y2025.Reefscape.generate_pathplanner_autos
    main()
