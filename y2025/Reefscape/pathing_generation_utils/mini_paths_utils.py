import json
import pathlib
import jinja2

TEMPLATE_DIR = "y2025/Reefscape/pathing_generation_utils/templates"


def load_choreo_pose_variables(choreo_file: pathlib.Path):
    data = json.loads(choreo_file.read_text())
    variables = {}
    for var_name, var in data["variables"]["poses"].items():
        variables[var_name] = dict(
            name=var_name, x=var["x"]["val"], y=var["y"]["val"], heading=var["heading"]["val"]
        )

    return variables


def load_choreo_velocity_variables(choreo_file: pathlib.Path):
    data = json.loads(choreo_file.read_text())
    variables = {}
    for var_name, var in data["variables"]["expressions"].items():
        if var["dimension"] == "LinVel":
            variables[var_name] = dict(name=var_name, value=var["var"])

    return variables


def load_choreo_distance_variables(choreo_file: pathlib.Path):
    data = json.loads(choreo_file.read_text())
    variables = {}
    for var_name, var in data["variables"]["expressions"].items():
        if var["dimension"] == "Length":
            variables[var_name] = dict(name=var_name, value=var["var"])

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


def velocity_variable_to_constraint(variables, variable_name, from_index, to_index):
    variable = variables[variable_name]

    return json.dumps(
        {
            "from": from_index,
            "to": to_index,
            "data": {
                "type": "MaxVelocity",
                "props": {"max": {"exp": variable_name, "val": variable["value"]["val"]}},
            },
            "enabled": True,
        }
    )


def create_keep_in_lane_constraint(from_index, end_index):
    return (
        f'    {{"from":{from_index}, "to":{end_index}, '
        + '"data":{"type":"KeepInLane", "props":{"tolerance":{"exp":"0.01 m", "val":0.01}}}, "enabled":true}'
    )


def create_stop_point_constraint(index):
    return (
        f'    {{"from":{index}, "to":null, '
        + '"data":{"type":"StopPoint", "props":{}}, "enabled":true}'
    )


def create_event_marker(index, offset, named_command, variable_name=None):
    variable_name = variable_name or f"{offset} s"
    return (
        '{"name":"Marker", "from":{"target":'
        + str(index)
        + ', "targetTimestamp":null, "offset":{"exp":"'
        + variable_name
        + '", "val":'
        + str(offset)
        + '}}, "event":{"type":"named", "data":{"name":"'
        + str(named_command)
        + '"}}}'
    )


def create_path_between_variables(
    choreo_dir, variables, first_variable, second_variable, constraints=None, events=None
):
    filename = f"{first_variable}To{second_variable}"

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

    if constraints is None:
        constraints = []

    return create_path_between_waypoints(choreo_dir, filename, waypoints, constraints, events)


def create_path_between_waypoints(choreo_dir, filename, waypoints, constraints, events=None):
    template_loader = jinja2.FileSystemLoader(TEMPLATE_DIR)
    template_env = jinja2.Environment(loader=template_loader)
    template = template_env.get_template("choreo_trajectory.jinja2")

    path_to_write = choreo_dir / f"{filename}.traj"

    events = events or []

    if constraints and events:
        print("Stuff")
        print(type(constraints[0]))
        print(type(events[0]))
        print(constraints)
        print(events)

    path_to_write.write_text(
        template.render(constraints=constraints, waypoints=waypoints, events=events)
    )

    return filename
