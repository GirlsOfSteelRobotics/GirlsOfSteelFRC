import json
from .choreo_file import ChoreoFile, Variable


class BaseConstraint:
    from_index: int
    to_index: int = None
    data_type: str

    def __init__(self, data_type):
        self.data_type = data_type

    def to_constraint_json(self):
        return json.dumps(
            {
                "from": self.from_index,
                "to": self.to_index,
                "data": {
                    "type": self.data_type,
                    "props": self.get_props(),
                },
                "enabled": True,
            }
        )

    def get_props(self):
        raise Exception("No implemented")


class VelocityConstraint(BaseConstraint):
    max_velocity: Variable

    def __init__(self):
        BaseConstraint.__init__(self, "MaxVelocity")

    def get_props(self):
        return {"max": self.max_velocity.to_json()}


def create_velocity_constraint(choreo_file: ChoreoFile, variable_name: str, from_index, to_index):
    variable = choreo_file.velocity_variables[variable_name]

    constraint = VelocityConstraint()
    constraint.from_index = from_index
    constraint.to_index = to_index
    constraint.max_velocity = variable

    return constraint


def create_velocity_constraint_fps(max_velocity_fps, from_index, to_index):
    constraint = VelocityConstraint()
    constraint.from_index = from_index
    constraint.to_index = to_index
    constraint.max_velocity = Variable.from_linear_velocity_fps(max_velocity_fps)

    return constraint


class AngularVelocityConstraint(BaseConstraint):
    max_velocity: Variable

    def __init__(self):
        BaseConstraint.__init__(self, "MaxAngularVelocity")

    def get_props(self):
        return {"max": self.max_velocity.to_json()}


def create_zero_angular_velocity_constraint(from_index, to_index=None):
    to_index = to_index or from_index

    constraint = AngularVelocityConstraint()
    constraint.from_index = from_index
    constraint.to_index = to_index
    constraint.max_velocity = Variable.from_angular_velocity_rps(0)

    return constraint


def create_max_angular_velocity_constraint_dps(max_angular_velocity_dps, from_index, to_index):
    constraint = AngularVelocityConstraint()
    constraint.from_index = from_index
    constraint.to_index = to_index
    constraint.max_velocity = Variable.from_angular_velocity_dps(max_angular_velocity_dps)

    return constraint


class KeepInLaneConstraint(BaseConstraint):
    def __init__(self):
        BaseConstraint.__init__(self, "KeepInLane")

    def get_props(self):
        return {"tolerance": {"exp": "0.01 m", "val": 0.01}}


def create_keep_in_lane_constraint(from_index, to_index):
    constraint = KeepInLaneConstraint()

    constraint.from_index = from_index
    constraint.to_index = to_index

    return constraint


class StopPointConstraint(BaseConstraint):
    def __init__(self):
        BaseConstraint.__init__(self, "StopPoint")

    def get_props(self):
        return {"tolerance": {"exp": "0.01 m", "val": 0.01}}


def create_stop_point_constraint(index):
    output = StopPointConstraint()
    output.from_index = index
    return output
