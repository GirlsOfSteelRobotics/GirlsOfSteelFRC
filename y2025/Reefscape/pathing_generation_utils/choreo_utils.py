import subprocess
from typing import Union, List
import re
import jinja2
import pathlib


TEMPLATE_DIR = "y2025/Reefscape/pathing_generation_utils/templates"


def run_choreo_cli(pathnames: Union[List, str]):
    use_local_version = True
    cmd = []
    if use_local_version:
        # home = pathlib.Path.home()
        # cmd.append(home / r"Downloads/choreo-cli.exe")
        cmd.append(r"C:\Users\PJ\git\frc-utils\Choreo\target\debug\choreo-cli.exe")
    else:
        cmd.append("bazel")
        cmd.append("run")
        cmd.append("@bzlmodrio-choreolib//libraries/tools/choreo-cli:choreo-cli")
        cmd.append("--")
    cmd.append("--chor")
    cmd.append("y2025/Reefscape/src/main/deploy/choreo/ChoreoAutos.chor")
    cmd.append("--generate")
    cmd.append("--trajectory")
    if type(pathnames) == str:
        cmd.append(pathnames)
    else:
        cmd.append(",".join(pathnames))
    subprocess.check_call(cmd)


def __camel_case_to_snake_case(name):
    pattern = re.compile(r"(?<!^)(?=[A-Z])")
    name = pattern.sub("_", name).lower()
    return name.upper()


def __sanitize_variable_name(name):
    name = __camel_case_to_snake_case(name)
    name = re.sub("^([A-Z])_([A-Z])$", r"\1\2", name)
    return name


def generate_pose_variables_file(choreo_data, project_dir: pathlib.Path, package_name: str):
    template_loader = jinja2.FileSystemLoader(TEMPLATE_DIR)
    template_env = jinja2.Environment(loader=template_loader)
    template_env.filters["sanitize_name"] = __sanitize_variable_name
    template = template_env.get_template("choreo_variables_file.jinja2")

    base_package_dir = project_dir / "src/main/java" / package_name.replace(".", "/")
    if not base_package_dir.exists():
        raise Exception(f"Base package '{base_package_dir}' does not exist")
    output_file = base_package_dir / "generated" / "ChoreoPoses.java"
    output_file.parent.mkdir(parents=True, exist_ok=True)
    print(f"Writing 'ChoreoPoses' file at {output_file}")

    output_file.write_text(
        template.render(
            package_name=package_name,
            poses=choreo_data["variables"]["poses"],
        )
    )


def generate_drive_to_pose_variable_file(choreo_data, project_dir: pathlib.Path, package_name: str):
    template_loader = jinja2.FileSystemLoader(TEMPLATE_DIR)
    template_env = jinja2.Environment(loader=template_loader)
    template_env.filters["sanitize_name"] = __sanitize_variable_name
    template = template_env.get_template("debug_tab_drive_to_choreo_variables.jinja2")

    base_package_dir = project_dir / "src/main/java" / package_name.replace(".", "/")
    if not base_package_dir.exists():
        raise Exception(f"Base package '{base_package_dir}' does not exist")
    output_file = base_package_dir / "generated" / "DriveToPositionDebugTab.java"
    output_file.parent.mkdir(parents=True, exist_ok=True)

    print(f"Writing 'Drive To Pose' file at {output_file}")
    output_file.write_text(
        template.render(
            package_name=package_name,
            poses=choreo_data["variables"]["poses"],
        )
    )
