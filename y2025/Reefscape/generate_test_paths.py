import math
import pathlib
import itertools
import jinja2
from y2025.Reefscape.choreo_utils import run_choreo_cli


def load_template(template_str) -> jinja2.Template:
    env = jinja2.Environment()
    env.filters["to_radians"] = lambda x: math.radians(x)
    return env.from_string(template_str)


def max_velocity_constraint(max_velocity_fps):
    template = """    {"from":"first", "to":"last", "data":{"type":"MaxVelocity", "props":{"max":{"exp":"{{ max_velocity_fps }} ft / s", "val":{{ max_velocity_fps * 0.3048 }}}}}, "enabled":true}"""
    return load_template(template).render(max_velocity_fps=max_velocity_fps)


def max_acceleration_constraint(max_acceleration):
    template = """    {"from":"first", "to":"last", "data":{"type":"MaxAcceleration", "props":{"max":{"exp":"{{ max_accel }} m / s ^ 2", "val":{{ max_accel|float }}}}}, "enabled":true}"""
    return load_template(template).render(max_accel=max_acceleration)


def max_angular_velocity_constrain(max_omega):
    template = """    {"from":"first", "to":"last", "data":{"type":"MaxAngularVelocity", "props":{"max":{"exp":"{{max_omega_dps}} deg / s", "val":{{max_omega_rps}}}}}, "enabled":true}"""
    return load_template(template).render(
        max_omega_dps=max_omega, max_omega_rps=math.radians(max_omega)
    )


def generate_straight_paths(choreo_dir):
    accelerations = [1, 4, 9, None]
    velocities = [1, 5, 10, 13, None]

    paths_to_do = []

    waypoints = [
        dict(x=0.4172362983226776, y=1.748888373374939, heading=0.0),
        dict(x=7.863987445831299, y=1.748888373374939, heading=0.0),
    ]

    for a, v in itertools.product(accelerations, velocities):
        if a is None and v is None:
            traj_name = f"TestPath_Maxmpss_Maxfps"
        elif a is None:
            traj_name = f"TestPath_Maxmpss_{v:02}fps"
        elif v is None:
            traj_name = f"TestPath_{a}mpss_Maxfps"
        else:
            traj_name = f"TestPath_{a}mpss_{v:02}fps"
        output_file = choreo_dir / (traj_name + ".traj")

        constraints = []
        if a is not None:
            constraints.append(max_acceleration_constraint(a))
        if v is not None:
            constraints.append(max_velocity_constraint(v))

        contents = load_template(TRAJECTORY_TEMPLATE).render(
            name=traj_name, constraints=constraints, waypoints=waypoints
        )
        output_file.write_text(contents)
        paths_to_do.append(traj_name)

    return paths_to_do


def generate_rotation_paths(choreo_dir):
    angular_velocities = [20, 45, 90, 180, 270, 360, None]

    paths_to_do = []

    waypoints = [
        dict(x=0.4172362983226776, y=1.748888373374939, heading=0.0),
        dict(x=4.14061187208, y=1.748888373374939, heading=180.0),
        dict(x=7.863987445831299, y=1.748888373374939, heading=0.0),
    ]

    for omega in angular_velocities:
        constraints = []
        if omega is None:
            traj_name = f"TestRotation_MaxDegPerSec"
        else:
            traj_name = f"TestRotation_{omega:03}DegPerSec"
            constraints.append(max_angular_velocity_constrain(omega))

        contents = load_template(TRAJECTORY_TEMPLATE).render(
            name=traj_name, constraints=constraints, waypoints=waypoints
        )

        output_file = choreo_dir / (traj_name + ".traj")
        output_file.write_text(contents)
        paths_to_do.append(traj_name)

    return paths_to_do


def main():
    root_dir = pathlib.Path(".")
    choreo_dir = root_dir / r"y2025\Reefscape\src\main\deploy\choreo"
    run_cli = True

    all_test_paths = []
    all_test_paths.extend(generate_straight_paths(choreo_dir))
    all_test_paths.extend(generate_rotation_paths(choreo_dir))

    if run_cli:
        run_choreo_cli(all_test_paths)

    all_paths_str = ""
    for path in all_test_paths:
        all_paths_str += f'        debugPathsTab.add(createDebugPathCommand("{path}"));\n'

    print(all_paths_str)


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
{%- for waypoint in waypoints %}
    {"x":{"exp":"{{ waypoint.x }} m", "val":{{ waypoint.x }}}, "y":{"exp":"{{ waypoint.y }} m", "val":{{ waypoint.y }}}, "heading":{"exp":"{{ waypoint.heading }} deg", "val":{{ waypoint.heading | to_radians }}}, "intervals":495, "split":false, "fixTranslation":true, "fixHeading":true, "overrideIntervals":false}{% if not loop.last %},{% endif %}
{%- endfor %}
],
  "constraints":[
    {"from":"first", "to":null, "data":{"type":"StopPoint", "props":{}}, "enabled":true},
    {"from":"last", "to":null, "data":{"type":"StopPoint", "props":{}}, "enabled":true}{% for constraint in constraints %},
{{ constraint }}
{%- endfor %}
],
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
    # py -m y2025.Reefscape.generate_test_paths
    main()
