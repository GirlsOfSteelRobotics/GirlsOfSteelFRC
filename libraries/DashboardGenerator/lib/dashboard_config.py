import re
from typing import List, Union

import yaml


class SmartName:
    def __init__(self, name: str):
        self.name = name

    def __repr__(self):
        return self.name

    def __add__(
        self,
        o: Union[str,],
    ) -> str:
        if type(o) == str:
            return self.name + o
        raise

    def lower_first_char(self) -> str:
        if len(self.name) == 0:
            raise Exception("Cannot use an empty string for a field name!")
        return self.name[0].lower() + self.name[1:]

    def upper_first_char(self) -> str:
        if len(self.name) == 0:
            raise Exception("Cannot use an empty string for a field name!")
        return self.name[0].upper() + self.name[1:]

    def as_hyphen_case(self) -> str:
        s1 = re.sub("(.)([A-Z][a-z]+)", r"\1-\2", self.name)
        return re.sub("([a-z0-9])([A-Z])", r"\1-\2", s1).lower()

    def as_snake_case(self) -> str:
        s1 = re.sub("(.)([A-Z][a-z]+)", r"\1_\2", self.name)
        return re.sub("([a-z0-9])([A-Z])", r"\1_\2", s1).lower()

    def as_camel(self) -> str:
        return self.name

    def upper_snake_to_camel(self) -> str:
        components = self.name.lower().split("_")
        if len(components) == 0:
            raise Exception("Cannot use an empty string!")

        output = components[0] + "".join(x.title() for x in components[1:])
        output = output[0].upper() + output[1:]
        return output

    def camel_to_snake(self) -> str:
        return self.as_snake_case()

    def camel_to_upper_snake(self) -> str:
        return self.camel_to_snake().upper()


class SmartType:
    DEFAULT_LOOKUP = {
        "Double": "0.0",
        "double": "0.0",
        "Boolean": "false",
        "boolean": "false",
        "String": '""',
    }

    def __init__(self, t: str, array_size: int):
        self.type = t
        self.is_array = t.endswith("[]")
        if self.is_array:
            self.base_type = t[:-2]
            self.array_size = array_size
            if self.array_size is None:
                raise Exception("You must specify an array size when using an array type")

    def __repr__(self):
        return self.type

    def default_value(self) -> str:
        if self.is_array:
            output = "{" + ", ".join([self.DEFAULT_LOOKUP[self.base_type]] * self.array_size) + "}"
            return output
        return self.DEFAULT_LOOKUP[self.type]

    def getter_name(self) -> str:
        if self.type == "boolean":
            return "is"
        return "get"

    def is_number(self) -> bool:
        return self.type in ["double", "Double", "int", "Integer"]

    def is_boolean(self) -> bool:
        return self.type in ["boolean", "Boolean"]

    def is_string(self) -> bool:
        return self.type in ["String"]


class PackageName:
    def __init__(self, package: str):
        self.package = package

    def __repr__(self):
        return self.package

    def as_dir(self) -> str:
        return self.package.replace(".", "/")

    def as_dash(self) -> str:
        return self.package.replace("_", "-")

    def join(self, other):
        return PackageName(self.package + "." + other.package)


class EntryConfig:
    def __init__(
        self,
        t: str,
        name: str,
        dashboard_constant: str,
        sim_keys: Union[List[str], None],
        sim_value: Union[str, None],
        sim_incr: Union[str, None],
        array_size: Union[int, None] = None,
    ):
        self.type = SmartType(t, array_size)
        self.name = SmartName(name)
        self.dashboard_constant = SmartName(dashboard_constant)
        self.sim_keys = sim_keys
        self.sim_value = sim_value
        self.sim_incr = sim_incr
        if self.type.is_array:
            self.sim_keys_array = []

    def __repr__(self):
        return f"{self.type} - {self.name}"

    def de_array(self, array_index: int):
        return EntryConfig(
            self.type.type[:-2],
            self.name + str(array_index),
            self.dashboard_constant,
            self.sim_keys_array[array_index],
            self.sim_value,
            self.sim_incr,
            array_size=None,
        )

    @staticmethod
    def from_yaml(config):
        return EntryConfig(
            config["type"],
            config["name"],
            config["dasboard_constant"],
            config.get("sim_keys", []),
            config.get("sim_value", None),
            config.get("sim_incr", None),
            config.get("array_size", None),
        )

    def to_dict(self):
        config = {
            "type": self.type.type,
            "name": self.name.name,
            "dasboard_constant": self.dashboard_constant.name,
        }

        if self.sim_keys:
            config["sim_keys"] = self.sim_keys
        if self.sim_value:
            config["sim_value"] = self.sim_value
        if self.sim_incr:
            config["sim_incr"] = self.sim_incr

        return config

    def default_value(self) -> str:
        return self.type.default_value()


class ChildTableConfig:
    def __init__(self, table: str, table_name: str, entries: List[EntryConfig]):
        self.table = SmartName(table)
        self.table_name = SmartName(table_name)
        self.entries = entries

    @staticmethod
    def from_yaml(config):
        return ChildTableConfig(
            config["table"],
            config["table_name"],
            [EntryConfig.from_yaml(c) for c in config["entries"]],
        )

    def to_dict(self):
        config = {
            "table": self.table.name,
            "table_name": self.table_name.name,
            "entries": [e.to_dict() for e in self.entries],
        }
        return config

    def combined_variable_name(self, entry) -> str:
        return self.table.lower_first_char() + entry.name.upper_first_char()


class BaseShape:
    def __init__(self, name: str, t: str, x: str, y: str, color: str):
        self.name = SmartName(name)
        self.type = t
        self.x = x
        self.y = y
        self.color = color


class Rectangle(BaseShape):
    def __init__(self, rotates: bool, width: str, height: str, **common_kwargs):
        super().__init__(**common_kwargs)
        self.width = width
        self.height = height
        self.rotates = rotates


class Arc(BaseShape):
    def __init__(self, radius: str, **common_kwargs):
        super().__init__(**common_kwargs)
        self.radius = radius


class Circle(BaseShape):
    def __init__(self, radius: str, **common_kwargs):
        super().__init__(**common_kwargs)
        self.radius = radius


def _dump_shape(shape: BaseShape):
    config = {
        "type": shape.type,
        "name": shape.name.name,
        "x": shape.x,
        "y": shape.y,
        "color": shape.color,
    }

    if shape.type == "Rectangle":
        shape: Rectangle
        config["height"] = shape.height
        config["width"] = shape.width
        config["rotates"] = shape.rotates
    elif shape.type == "Arc":
        shape: Arc
        config["radius"] = shape.radius
    elif shape.type == "Circle":
        shape: Circle
        config["radius"] = shape.radius

    return config


def _create_shape(shape_config) -> BaseShape:
    t = shape_config["type"]
    default_kwargs = dict(
        name=shape_config["name"],
        t=t,
        x=shape_config.get("x", None),
        y=shape_config.get("y", None),
        color=shape_config.get("color", "papayacream"),
    )
    if t == "Rectangle":
        return Rectangle(
            width=shape_config.get("width", None),
            height=shape_config.get("height", None),
            rotates=shape_config.get("rotates", False),
            **default_kwargs,
        )
    elif t == "Arc":
        return Arc(
            radius=shape_config.get("radius", None),
            **default_kwargs,
        )
    elif t == "Circle":
        return Circle(
            radius=shape_config.get("radius", None),
            **default_kwargs,
        )
    else:
        raise Exception(f"Unexpected shape type {t}")


class WidgetConfig:
    def __init__(
        self,
        table: str,
        package_name: str,
        widget_name: str,
        sd_constant: str,
        children_tables: List[ChildTableConfig],
        shapes: List[BaseShape],
    ):
        self.table = SmartName(table)
        self.package_name = PackageName(package_name)
        self.widget_name = widget_name
        self.sd_table_name_constant = SmartName(sd_constant)
        self.children_tables = children_tables
        self.shapes = shapes

        if len(self.children_tables) == 1:
            child = self.children_tables[0]
            if self.table.name != child.table.name:
                raise Exception(
                    f"If you only have one child, some short circuits will be used. Because of that, the widget table ('{self.table}') must match the child table ('{child.table}')"
                )

        self.has_nested_tables = len(self.children_tables) != 1

    @staticmethod
    def from_yaml(config):
        return WidgetConfig(
            config["table"],
            config["package_name"],
            config["widget_name"],
            config["sd_table_name_constant"],
            [ChildTableConfig.from_yaml(c) for c in config["children_tables"]],
            [_create_shape(c) for c in config.get("shapes", [])],
        )

    def to_dict(self):
        config = {
            "table": self.table.name,
            "package_name": self.package_name.package,
            "widget_name": self.widget_name,
            "sd_table_name_constant": self.sd_table_name_constant.name,
            "children_tables": [c.to_dict() for c in self.children_tables],
            "shapes": [_dump_shape(s) for s in self.shapes],
        }

        return config


class DashboardConfig:
    def __init__(
        self,
        base_package: str,
        plugin_summary: str,
        plugin_name: str,
        widgets: List[WidgetConfig],
        manual_widgets: List[str],
    ):
        self.base_package = PackageName(base_package)
        self.plugin_summary = SmartName(plugin_summary)
        self.plugin_name = SmartName(plugin_name)
        self.widgets = widgets
        self.manual_widgets = manual_widgets

    @staticmethod
    def from_yaml_file(config_file):
        config = yaml.load(open(config_file, "r"), Loader=yaml.SafeLoader)
        return DashboardConfig.from_yaml(config)

    @staticmethod
    def from_yaml(config):
        return DashboardConfig(
            config["base_package"],
            config["plugin_summary"],
            config["plugin_name"],
            [WidgetConfig.from_yaml(c) for c in config["widgets"]],
            config.get("manual_widgets", []),
        )

    def to_dict(self):
        config = {
            "base_package": self.base_package.package,
            "plugin_summary": self.plugin_summary.name,
            "plugin_name": self.plugin_name.name,
            "widgets": [w.to_dict() for w in self.widgets],
            "manual_widgets": self.manual_widgets,
        }

        return config
