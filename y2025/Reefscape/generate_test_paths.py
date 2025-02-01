import json
import pathlib
import itertools
import subprocess
from jinja2 import Template
from y2025.Reefscape.choreo_utils import run_choreo_cli


def main():
    root_dir = pathlib.Path(r"C:\Users\PJ\git\gos\GirlsOfSteelFRC")
    choreo_dir = root_dir / r"y2025\Reefscape\src\main\deploy\choreo"

    accelerations = [1, 4, 9, None]
    velocities = [1, 5, 10, 13, None]

    paths_to_do = []

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

        has_constraints = a is not None or v is not None

        contents = Template(TRAJECTORY_TEMPLATE).render(
            name=traj_name, max_accel=a, max_velocity_fps=v, has_constraints=has_constraints
        )
        output_file.write_text(contents)
        paths_to_do.append(traj_name)

    for path in paths_to_do:
        run_choreo_cli(path)


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
    {"x":{"exp":"0.4172362983226776 m", "val":0.4172362983226776}, "y":{"exp":"1.748888373374939 m", "val":1.748888373374939}, "heading":{"exp":"0 rad", "val":0.0}, "intervals":495, "split":false, "fixTranslation":true, "fixHeading":true, "overrideIntervals":false},
    {"x":{"exp":"7.863987445831299 m", "val":7.863987445831299}, "y":{"exp":"1.748888373374939 m", "val":1.748888373374939}, "heading":{"exp":"0 rad", "val":0.0}, "intervals":40, "split":false, "fixTranslation":true, "fixHeading":true, "overrideIntervals":false}],
  "constraints":[
    {"from":"first", "to":null, "data":{"type":"StopPoint", "props":{}}, "enabled":true},
    {"from":"last", "to":null, "data":{"type":"StopPoint", "props":{}}, "enabled":true}{%- if has_constraints %},{% endif %}
{%- if max_accel != None %}
    {"from":0, "to":1, "data":{"type":"MaxAcceleration", "props":{"max":{"exp":"{{ max_accel }} m / s ^ 2", "val":{{ max_accel|float }}}}}, "enabled":true}{%- if max_velocity_fps != None %},{% endif %}
{%- endif %}
{%- if max_velocity_fps != None %}
    {"from":0, "to":1, "data":{"type":"MaxVelocity", "props":{"max":{"exp":"{{ max_velocity_fps }} ft / s", "val":{{ max_velocity_fps * 0.3048 }}}}}, "enabled":true}
{%- endif %}
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
