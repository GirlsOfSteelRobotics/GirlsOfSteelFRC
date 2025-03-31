from typing import List, Dict
import pathlib
import json
import math


class Variable:
    name: str
    exp: str
    value: float

    def __repr__(self):
        return f"Variable '{self.name}': {self.exp} - {self.value}"

    @staticmethod
    def from_json(name, json_data) -> "Variable":
        output = Variable()
        output.name = name
        output.exp = json_data["exp"]
        output.value = json_data["val"]

        return output

    @staticmethod
    def from_sub_variable(parent_variable: "Variable", subfield_name: str) -> "Variable":
        subfield = getattr(parent_variable, subfield_name)
        output = Variable()
        output.name = parent_variable.name + "." + subfield_name
        output.exp = parent_variable.name + "." + subfield_name
        output.value = subfield.value

        return output

    @staticmethod
    def from_angular_velocity_rps(linear_velocity_radians_per_second) -> "Variable":
        output = Variable()

        output.name = f"{linear_velocity_radians_per_second} rad / s"
        output.exp = f"{linear_velocity_radians_per_second} rad / s"
        output.value = linear_velocity_radians_per_second

        return output

    @staticmethod
    def from_angular_velocity_dps(linear_velocity_degrees_per_second) -> "Variable":
        output = Variable()

        output.name = f"{linear_velocity_degrees_per_second} deg / s"
        output.exp = f"{linear_velocity_degrees_per_second} deg / s"
        output.value = math.radians(linear_velocity_degrees_per_second)

        return output

    @staticmethod
    def from_linear_velocity_fps(vel_fps) -> "Variable":
        output = Variable()

        output.name = f"{vel_fps} ft / s"
        output.exp = f"{vel_fps} ft / s"
        output.value = vel_fps * 0.3048

        print(output)

        return output

    @staticmethod
    def from_meters(distance_meters) -> "Variable":
        output = Variable()

        output.name = f"{distance_meters} m"
        output.exp = f"{distance_meters} m"
        output.value = distance_meters

        return output

    @staticmethod
    def from_inches(distance_inches):
        output = Variable()

        output.name = f"{distance_inches} in"
        output.exp = f"{distance_inches} in"
        output.value = distance_inches / 12 * 0.3048

        return output

    @staticmethod
    def from_degrees(degrees) -> "Variable":
        output = Variable()

        output.name = f"{degrees} deg"
        output.exp = f"{degrees} deg"
        output.value = math.radians(degrees)

        return output

    @staticmethod
    def from_radians(rads) -> "Variable":
        output = Variable()

        output.name = f"{rads} rad"
        output.exp = f"{rads} rad"
        output.value = rads

        return output

    def to_json(self):
        return {"exp": self.name, "val": self.value}


class PoseVariable:
    name: str
    x: Variable
    y: Variable
    heading: Variable

    @staticmethod
    def from_json(name, json_data) -> "PoseVariable":
        output = PoseVariable()

        output.name = name
        output.x = Variable.from_json("x", json_data["x"])
        output.y = Variable.from_json("y", json_data["y"])
        output.heading = Variable.from_json("heading", json_data["heading"])

        return output

    def to_waypoint(self):
        return json.dumps(
            {
                "x": {"exp": f"{ self.name }.x", "val": self.x.value},
                "y": {"exp": f"{ self.name }.y", "val": self.y.value},
                "heading": {"exp": f"{ self.name }.heading", "val": self.heading.value},
                "intervals": 40,
                "split": False,
                "fixTranslation": True,
                "fixHeading": True,
                "overrideIntervals": False,
            }
        )


class Waypoint:
    x: Variable
    y: Variable
    heading: Variable

    split: bool = False
    fix_heading: bool = True

    @staticmethod
    def from_xy_deg(x, y, heading) -> "Waypoint":
        output = Waypoint()
        output.x = Variable.from_meters(x)
        output.y = Variable.from_meters(y)
        output.heading = Variable.from_degrees(heading)

        return output

    def to_waypoint(self):
        config = {
            "x": {"exp": f"{ self.x.exp }", "val": self.x.value},
            "y": {"exp": f"{ self.y.exp }", "val": self.y.value},
        }
        if self.fix_heading:
            config.update({"heading": {"exp": f"{ self.heading.exp }", "val": self.heading.value}})
        else:
            config.update({"heading": {"exp": f"0 deg", "val": 0}})

        config.update(
            {
                "intervals": 40,
                "split": self.split,
                "fixTranslation": True,
                "fixHeading": self.fix_heading,
                "overrideIntervals": False,
            }
        )
        return json.dumps(config)


class ChoreoFile:
    pose_variables: Dict[str, PoseVariable] = {}
    velocity_variables: Dict[str, Variable] = {}
    distance_variables: Dict[str, Variable] = {}
    time_variables: Dict[str, Variable] = {}

    def __init__(self, choreo_file: pathlib.Path):
        json_data = json.loads(choreo_file.read_text())

        for name, pose_json in json_data["variables"]["poses"].items():
            self.pose_variables[name] = PoseVariable.from_json(name, pose_json)

        for name, variable_json in json_data["variables"]["expressions"].items():
            if variable_json["dimension"] == "Length":
                self.distance_variables[name] = Variable.from_json(name, variable_json["var"])
            elif variable_json["dimension"] == "LinVel":
                self.velocity_variables[name] = Variable.from_json(name, variable_json["var"])
            elif variable_json["dimension"] == "Time":
                self.time_variables[name] = Variable.from_json(name, variable_json["var"])
            else:
                raise Exception(f"Unknown dimension {variable_json['dimension']}")

    def __repr__(self):
        output = ""
        for name, var in self.pose_variables.items():
            output += f"  {name} - {var}\n"

        for name, var in self.velocity_variables.items():
            output += f"  {name} - {var}\n"

        for name, var in self.distance_variables.items():
            output += f"  {name} - {var}\n"

        return output


def create_intermediate_point_back_up_pose(
    choreo_file: ChoreoFile,
    pose_to_backup_from: PoseVariable,
    distance_variable_name: str,
    split: bool = False,
    fix_heading: bool = True,
):
    distance_variable = choreo_file.distance_variables[distance_variable_name]

    backup_x = pose_to_backup_from.x.value - distance_variable.value * math.cos(
        pose_to_backup_from.heading.value
    )
    backup_y = pose_to_backup_from.y.value - distance_variable.value * math.sin(
        pose_to_backup_from.heading.value
    )

    output = Waypoint()
    output.x = Variable.from_json(
        "FakeName",
        {
            "exp": f"{pose_to_backup_from.name}.x - {distance_variable_name} * cos({pose_to_backup_from.name}.heading)",
            "val": backup_x,
        },
    )
    output.y = Variable.from_json(
        "FakeName",
        {
            "exp": f"{pose_to_backup_from.name}.y - {distance_variable_name} * sin({pose_to_backup_from.name}.heading)",
            "val": backup_y,
        },
    )
    output.heading = Variable.from_json(
        "FakeName",
        {"exp": f"{pose_to_backup_from.name}.heading", "val": pose_to_backup_from.heading.value},
    )
    output.split = split
    output.fix_heading = fix_heading
    return output
