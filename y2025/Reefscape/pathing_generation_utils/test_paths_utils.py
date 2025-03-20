import json
import math
import jinja2
import pathlib
import itertools
from typing import List
from .choreo_utils import (
    max_velocity_constraint,
    max_acceleration_constraint,
    max_angular_velocity_constraint,
)

TEMPLATE_DIR = "y2025/Reefscape/pathing_generation_utils/templates"


def format_waypoint(waypoint):
    template_str = '{"x":{"exp":"{{ waypoint.x }} m", "val":{{ waypoint.x }}}, "y":{"exp":"{{ waypoint.y }} m", "val":{{ waypoint.y }}}, "heading":{"exp":"{{ waypoint.heading }} deg", "val":{{ waypoint.heading | to_radians }}}, "intervals":495, "split":false, "fixTranslation":true, "fixHeading":true, "overrideIntervals":false}'

    template_env = jinja2.Environment()
    template_env.filters["to_radians"] = math.radians
    return template_env.from_string(template_str).render(waypoint=waypoint)


def generate_straight_paths(
    output_dir, accelerations: List[float], velocities: List[float], start_waypoint, stop_waypoint
) -> List[pathlib.Path]:
    all_paths = []
    waypoints = [format_waypoint(waypoint) for waypoint in [start_waypoint, stop_waypoint]]
    print(waypoints)

    template_loader = jinja2.FileSystemLoader(TEMPLATE_DIR)
    template_env = jinja2.Environment(loader=template_loader)
    template_env.filters["to_radians"] = math.radians
    template = template_env.get_template("choreo_trajectory.jinja2")

    for a, v in itertools.product(accelerations, velocities):
        if a is None and v is None:
            traj_name = f"TestPath_Maxmpss_Maxfps"
        elif a is None:
            traj_name = f"TestPath_Maxmpss_{v:02}fps"
        elif v is None:
            traj_name = f"TestPath_{a}mpss_Maxfps"
        else:
            traj_name = f"TestPath_{a}mpss_{v:02}fps"
        output_file = output_dir / (traj_name + ".traj")

        constraints = []
        if a is not None:
            constraints.append(max_acceleration_constraint(a))
        if v is not None:
            constraints.append(max_velocity_constraint(v))

        contents = template.render(name=traj_name, constraints=constraints, waypoints=waypoints)
        output_file.write_text(contents)
        all_paths.append(traj_name)

    return all_paths


def generate_rotation_paths(output_dir, angular_velocities: List[float]) -> List[pathlib.Path]:
    all_paths = []

    waypoints = [
        dict(x=0.4172362983226776, y=1.748888373374939, heading=0),
        dict(x=4.14061187208, y=1.748888373374939, heading=180),
        dict(x=7.863987445831299, y=1.748888373374939, heading=0),
    ]
    waypoints = [format_waypoint(waypoint) for waypoint in waypoints]

    template_loader = jinja2.FileSystemLoader(TEMPLATE_DIR)
    template_env = jinja2.Environment(loader=template_loader)
    template_env.filters["to_radians"] = math.radians
    template = template_env.get_template("choreo_trajectory.jinja2")

    for omega in angular_velocities:
        constraints = []
        if omega is None:
            traj_name = f"TestRotation_MaxDegPerSec"
        else:
            traj_name = f"TestRotation_{omega:03}DegPerSec"
            constraints.append(max_angular_velocity_constraint(omega))

        contents = template.render(name=traj_name, constraints=constraints, waypoints=waypoints)

        output_file = output_dir / (traj_name + ".traj")
        output_file.write_text(contents)
        all_paths.append(traj_name)

    return all_paths


def write_debug_tab_file(project_dir, choreo_dir, package_name, all_paths):
    def trajectory_invalid(trajectory_name):
        print(trajectory_name)
        with open(choreo_dir / (trajectory_name + ".traj")) as f:
            json_contents = json.load(f)
        if json_contents["trajectory"]["waypoints"]:
            return False
        return True

    template_loader = jinja2.FileSystemLoader(TEMPLATE_DIR)
    template_env = jinja2.Environment(loader=template_loader)
    template_env.filters["is_invalid"] = trajectory_invalid
    template = template_env.get_template("debug_tab_test_paths.jinja2")

    base_package_dir = project_dir / "src/main/java" / package_name.replace(".", "/")
    if not base_package_dir.exists():
        raise Exception(f"Base package '{base_package_dir}' does not exist")
    output_file = base_package_dir / "generated" / "DebugPathsTab.java"
    output_file.parent.mkdir(parents=True, exist_ok=True)

    print(f"Writing test paths debug tab to {output_file}")

    output_file.write_text(template.render(package_name=package_name, paths=all_paths))
