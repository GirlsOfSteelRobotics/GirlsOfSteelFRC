import pathlib
from y2025.Reefscape.pathplanner_utils import write_pathplanner_auto


def generate_autos(pathplanner_dir):
    right_ebc_auto = [
        "StartingPosRightToE",
        "EToHumanPlayerRight",
        "HumanPlayerRightToB",
        "BToHumanPlayerRight",
        "HumanPlayerRightToC",
    ]
    write_pathplanner_auto(right_ebc_auto, pathplanner_dir / "RightEBC.auto", "Autos")

    right_gfedcb_auto = [
        "StartingPosRightToG",
        "GToHumanPlayerRight",
        "HumanPlayerRightToF",
        "FToHumanPlayerRight",
        "HumanPlayerRightToE",
        "EToHumanPlayerRight",
        "HumanPlayerRightToD",
        "DToHumanPlayerRight",
        "HumanPlayerRightToC",
        "CToHumanPlayerRight",
        "HumanPlayerRightToB",
    ]
    write_pathplanner_auto(right_gfedcb_auto, pathplanner_dir / "RightGFEDCB.auto", "Autos")

    center_GH_EF_IJ_auto = [
        "StartingPosCenterToH",
        "HToGH",
        "GHToProcessor",
        "ProcessorToEF",
        "EFToProcessor",
        "ProcessorToIJ",
    ]
    write_pathplanner_auto(center_GH_EF_IJ_auto, pathplanner_dir / "CenterGH_EF_IJ.auto", "Autos")

    left_hijkla_auto = [
        "StartingPosLeftToH",
        "HToHumanPlayerLeft",
        "HumanPlayerLeftToI",
        "IToHumanPlayerLeft",
        "HumanPlayerLeftToJ",
        "JToHumanPlayerLeft",
        "HumanPlayerLeftToK",
        "KToHumanPlayerLeft",
        "HumanPlayerLeftToL",
        "LToHumanPlayerLeft",
        "HumanPlayerLeftToA",
    ]
    write_pathplanner_auto(left_hijkla_auto, pathplanner_dir / "LeftHIJKLA.auto", "Autos")


def main():
    root_dir = pathlib.Path(r".")
    pathplanner_dir = root_dir / r"y2025\Reefscape\src\main\deploy\pathplanner/autos"

    generate_autos(pathplanner_dir)


if __name__ == "__main__":
    # py -m y2025.Reefscape.generate_pathplanner_autos
    main()
