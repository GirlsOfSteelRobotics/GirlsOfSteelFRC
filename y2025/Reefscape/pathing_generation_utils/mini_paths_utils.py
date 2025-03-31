import json
import pathlib
import jinja2

from .choreo_utils import TEMPLATE_DIR
from .choreo_file import ChoreoFile, create_intermediate_point_back_up_pose
from .choreo_constraints import (
    create_velocity_constraint,
    create_keep_in_lane_constraint,
    create_zero_angular_velocity_constraint,
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


def create_path_from_waypoints_with_straight_backoff(
    choreo_file: ChoreoFile,
    trajectory_output_dir: pathlib.Path,
    start_variable_name: str,
    end_variable_name: str,
    backoff_from_variable_name: str,
    backoff_distance_variable_name: str,
    velocity_variable_name: str,
    always_include_keep_in_lane: bool = False,
    events=[],
):
    if backoff_from_variable_name == start_variable_name:
        constraints = [
            create_velocity_constraint(choreo_file, velocity_variable_name, 0, 2),
        ]
        if always_include_keep_in_lane:
            constraints.append(create_zero_angular_velocity_constraint(1))
            constraints.append(create_keep_in_lane_constraint(0, 1))
    else:
        constraints = [
            create_velocity_constraint(choreo_file, velocity_variable_name, 0, 2),
            create_keep_in_lane_constraint(1, 2),
            create_zero_angular_velocity_constraint(1),
        ]

    filename = f"{start_variable_name}To{end_variable_name}"

    first_waypoint = choreo_file.pose_variables[start_variable_name]
    last_waypoint = choreo_file.pose_variables[end_variable_name]

    intermediate_waypoints = [
        create_intermediate_point_back_up_pose(
            choreo_file,
            choreo_file.pose_variables[backoff_from_variable_name],
            backoff_distance_variable_name,
        )
    ]
    waypoints = [first_waypoint] + intermediate_waypoints + [last_waypoint]

    return create_path_between_waypoints(
        trajectory_output_dir, filename, waypoints, constraints, events
    )


def create_path_between_waypoints(
    trajectory_output_dir, filename, waypoints, constraints, events=None
):
    template_loader = jinja2.FileSystemLoader(TEMPLATE_DIR)
    template_env = jinja2.Environment(loader=template_loader)
    template = template_env.get_template("choreo_trajectory.jinja2")

    path_to_write = trajectory_output_dir / f"{filename}.traj"

    events = events or []

    path_to_write.write_text(
        template.render(constraints=constraints, waypoints=waypoints, events=events)
    )

    return filename
