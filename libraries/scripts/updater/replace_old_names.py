"""
Runs a regex replace on any of the class names that got changed in the past year updates of wpilib / vendor deps
"""
from urllib.request import urlopen
import json
from libraries.scripts.updater.utils import walk_for_extension, regex_replace_file
from libraries.scripts.git.git_python_wrappers import commit_all_changes


def __run_replacement(replacements, root=".", dir_blacklist=None):

    java_files = walk_for_extension(root, "java", dir_blacklist=dir_blacklist)

    for java_file in java_files:
        regex_replace_file(java_file, replacements)


def run_standard_replacement(auto_commit):

    # Last sync Dec 19, 2021
    wpilib_replacements_url = "https://raw.githubusercontent.com/wpilibsuite/vscode-wpilib/main/vscode-wpilib/resources/java_replacements.json"

    raw_json_data = urlopen(wpilib_replacements_url).read().decode("utf-8")
    json_data = json.loads(raw_json_data)

    replacements = []
    for replacement_json in json_data[0]["replacements"]:
        replacement_to = replacement_json["to"]
        # Python-ize the replacement substitution
        replacement_to = replacement_to.replace("$1", r"\1").replace("$2", r"\2")
        if "$" in replacement_to:
            raise Exception(f"Make this smarter. To = '{replacement_to}")
        replacements.append((replacement_json["from"], replacement_to))

    # Run these on all the files
    __run_replacement(replacements)

    if auto_commit:
        commit_all_changes("Auto-Update: Ran standard vscode replacements")


def run_our_additional_replacements(auto_commit):
    replacements = []

    # Put our smarter-than-wpilib replacements here
    # fmt: off

    # Deprecated Buttons
    replacements.append((r"\.whileHeld\((new .*?\(.*?\)), true\);", r".whileTrue(\1);"))
    replacements.append((r"\.whileHeld\(", r".whileTrue("))
    replacements.append((r"\.whenHeld\(", r".whileTrue("))
    replacements.append((r"\.whenReleased\(", r".onFalse("))
    replacements.append((r"\.whenPressed\(", r".onTrue("))
    replacements.append((r"\.whenActive\(", r".onTrue("))
    replacements.append((r"\.onTrue\((new .*?\(.*?\)), true\);", r".onTrue(\1);"))
    replacements.append((r"new Button\(", r"new Trigger("))
    replacements.append((r"new edu.wpi.first.wpilibj2.command.button.Button\(", r"new Trigger("))
    replacements.append((r" Button (.*?) = new JoystickButton", r" JoystickButton \1 = new JoystickButton"))
    replacements.append((r"import edu.wpi.first.wpilibj2.command.button.Button;", "import edu.wpi.first.wpilibj2.command.button.Trigger;"))

    # Deprecated Commands
    replacements.append((r"\.withInterrupt\(", ".until("))

    # Now uses Rotation2d
    replacements.append((r"\.drivePolar\((.*), (.*?), (.*?)\);", r".drivePolar(\1, Rotation2d.fromDegrees(\2), \3);"))
    replacements.append((r"\.driveCartesian\((.*), (.*?), (.*?), (.*?)\);", r".driveCartesian(\1, \2, \3, Rotation2d.fromDegrees(\4));"))

    # Now uses Rotation2d
    replacements.append((r"ElevatorSim\((.*), (.*), (.*), (.*), (.*), (.*)\);", r"ElevatorSim(\1, \2, \3, \4, \5, \6, true);"))
    replacements.append((r"ElevatorSim\((\n.*),(\n.*),(\n.*),(\n.*),(\n.*),(\n.*)\);", r"ElevatorSim(\1, \2, \3, \4, \5, \6, true);"))
    replacements.append((r"ElevatorSim\((\n.*),(\n.*),(\n.*),(\n.*),(\n.*),(\n.*),(\n.*)\);", r"ElevatorSim(\1, \2, \3, \4, \5, \6, true, \7);"))
    # fmt: on

    # Run these on all the files
    __run_replacement(replacements)

    if auto_commit:
        commit_all_changes("Auto-Update: Ran our additional replacements")


def run_all_replacements(auto_commit=True):
    run_standard_replacement(auto_commit=auto_commit)
    run_our_additional_replacements(auto_commit=auto_commit)


if __name__ == "__main__":
    #  py -m libraries.scripts.updater.replace_old_names
    run_all_replacements(auto_commit=False)
