import json
import pathlib
import jinja2

TEMPLATE_DIR = "y2025/Reefscape/pathing_generation_utils/templates"


def load_choreo_variables(choreo_file: pathlib.Path):
    data = json.loads(choreo_file.read_text())
    variables = {}
    for var_name, var in data["variables"]["poses"].items():
        print(var_name)
        variables[var_name] = dict(
            name=var_name, x=var["x"]["val"], y=var["y"]["val"], heading=var["heading"]["val"]
        )

    return variables


def variable_to_waypoint(variables, variable_name):
    variable = variables[variable_name]

    return json.dumps(
        {
            "x": {"exp": f"{ variable['name'] }.x", "val": variable["x"]},
            "y": {"exp": f"{ variable['name'] }.y", "val": variable["y"]},
            "heading": {"exp": f"{ variable['name'] }.heading", "val": variable["heading"]},
            "intervals": 40,
            "split": False,
            "fixTranslation": True,
            "fixHeading": True,
            "overrideIntervals": False,
        }
    )


def create_path_between_variables(choreo_dir, variables, first_variable, second_variable):
    filename = f"{first_variable}To{second_variable}"

    template_loader = jinja2.FileSystemLoader(TEMPLATE_DIR)
    template_env = jinja2.Environment(loader=template_loader)
    template = template_env.get_template("choreo_trajectory.jinja2")

    path_to_write = choreo_dir / f"{filename}.traj"
    if path_to_write.exists():
        data = json.loads(path_to_write.read_text())
        waypoints = data["params"]["waypoints"]
        intermediate_waypoints = waypoints[1:-1]
        intermediate_waypoints = [json.dumps(waypoint) for waypoint in intermediate_waypoints]
    else:
        intermediate_waypoints = []

    start_waypoint = variable_to_waypoint(variables, first_variable)
    end_waypoint = variable_to_waypoint(variables, second_variable)

    waypoints = [start_waypoint] + intermediate_waypoints + [end_waypoint]

    constraints = [
        '    {"from":0, "to":'
        + str(len(waypoints) - 1)
        + ', "data":{"type":"MaxVelocity", "props":{"max":{"exp":"DefaultMaxVelocity", "val":1.524}}}, "enabled":true}'
    ]

    path_to_write.write_text(template.render(constraints=constraints, waypoints=waypoints))

    return filename
