import os
import shutil
from libraries.ShuffleboardGenerator.lib.template_helpers import *
from libraries.ShuffleboardGenerator.lib.template_helpers import (
    package_to_dir,
    load_template,
    camel_to_snake,
)


class WidgetGenerator:
    def __init__(self, template_dir, project_dir, overall_package, widget_config):

        self.project_package_name = overall_package
        self.widget_config = widget_config
        self.template_dir = template_dir

        self.widget_package_name = self.project_package_name + "." + widget_config["package_name"]

        self.gen_src_dir = os.path.abspath(
            os.path.join(project_dir, "src", "dashboard_gen", "java")
        )
        self.resource_dir = os.path.abspath(os.path.join(project_dir, "src", "main", "resources"))
        self.src_dir = os.path.abspath(os.path.join(project_dir, "src", "main", "java"))

        self.project_package_name_as_rel_dir = package_to_dir(self.project_package_name)
        self.widget_package_name_as_rel_dir = package_to_dir(self.widget_package_name)

        self.project_package_src_dir = os.path.join(
            self.src_dir, self.project_package_name_as_rel_dir
        )
        self.widget_package_src_dir = os.path.join(
            self.src_dir, self.widget_package_name_as_rel_dir
        )

        self.widget_package_gen_dir = os.path.join(
            self.gen_src_dir, self.widget_package_name_as_rel_dir
        )
        self.widget_resource_dir = os.path.join(
            self.resource_dir, self.widget_package_name_as_rel_dir
        )

        # Setup the directories
        self.__make_dir_if_not_exists(self.gen_src_dir)
        self.__make_dir_if_not_exists(self.resource_dir)
        self.__make_dir_if_not_exists(self.src_dir)
        self.__make_dir_if_not_exists(self.widget_package_gen_dir)
        self.__make_dir_if_not_exists(self.project_package_src_dir)
        self.__make_dir_if_not_exists(self.widget_resource_dir)
        self.__make_dir_if_not_exists(self.widget_package_src_dir)
        self.__make_dir_if_not_exists(self.project_package_src_dir)

        self.default_kwargs = {}
        self.default_kwargs["widget"] = self.widget_config
        self.default_kwargs["widget_package_name"] = self.widget_package_name
        self.default_kwargs["project_package_name"] = self.project_package_name

    def _load_template(self, template_file):
        return load_template(self.template_dir, template_file)

    def dump_single_components(self):

        data_template = self._load_template("dashboard_gen/widget/data_template.txt")
        data_type_template = self._load_template("dashboard_gen/widget/data_type_template.txt")

        for child in sorted(self.widget_config["children_tables"], key=lambda x: 1):
            kwargs = self.default_kwargs.copy()
            kwargs["child"] = child

            data_dump = data_template.render(**kwargs)
            data_type_dump = data_type_template.render(**kwargs)

            data_path = os.path.join(self.widget_package_gen_dir, child["table"] + "Data.java")
            data_type_path = os.path.join(
                self.widget_package_gen_dir, child["table"] + "DataType.java"
            )
            open(data_path, "w").write(data_dump)
            open(data_type_path, "w").write(data_type_dump)

    def dump_widget(self):

        kwargs = self.default_kwargs.copy()
        template_output = self._load_template("dashboard_gen/widget/widget_template.txt").render(
            **kwargs
        )

        output_file = os.path.join(
            self.widget_package_gen_dir, self.widget_config["table"] + "Widget.java"
        )
        open(output_file, "w").write(template_output)

    def dump_widget_top_level_data(self):
        if len(self.widget_config["children_tables"]) == 1:
            return
        data_template_output = self._load_template(
            "dashboard_gen/widget/parent_data_template.txt"
        ).render(**self.default_kwargs)
        data_type_template_output = self._load_template(
            "dashboard_gen/widget/parent_data_type_template.txt"
        ).render(**self.default_kwargs)

        data_output_file = os.path.join(
            self.widget_package_gen_dir, self.widget_config["table"] + "Data.java"
        )
        data_type_output_file = os.path.join(
            self.widget_package_gen_dir, self.widget_config["table"] + "DataType.java"
        )
        open(data_output_file, "w").write(data_template_output)
        open(data_type_output_file, "w").write(data_type_template_output)

    ########################
    # The maybe generators
    ########################

    def maybe_dump_fxml(self, force):

        snake_name = camel_to_snake(self.widget_config["table"])

        controller_file = os.path.join(self.widget_resource_dir, snake_name + ".fxml")
        widget_file = os.path.join(self.widget_resource_dir, snake_name + "_widget.fxml")

        kwargs = self.default_kwargs.copy()

        if force or not os.path.exists(widget_file):
            print("Create optional file " + widget_file)
            template_output = self._load_template("resources/fxml_widget_template.txt").render(
                kwargs
            )
            open(widget_file, "w").write(template_output)

        if force or not os.path.exists(controller_file):
            print("Create optional file " + controller_file)
            template_output = self._load_template("resources/fxml_controller_template.txt").render(
                kwargs
            )
            open(controller_file, "w").write(template_output)

    def maybe_dump_standalone_main(self, force):
        output_file = os.path.join(
            self.widget_package_src_dir, self.widget_config["table"] + "StandaloneMain.java"
        )

        kwargs = self.default_kwargs.copy()
        if force or not os.path.exists(output_file):
            print("Create optional file " + output_file)
            open(output_file, "w").write(
                self._load_template("java/standalone_main_template.txt").render(**kwargs)
            )

    def maybe_dump_controller(self, force):
        kwargs = self.default_kwargs.copy()
        template_output = self._load_template("java/widget/controller_template.txt").render(
            **kwargs
        )

        output_file = os.path.join(
            self.widget_package_src_dir, self.widget_config["table"] + "Controller.java"
        )
        if force or not os.path.exists(output_file):
            print("Create optional file " + output_file)
            open(output_file, "w").write(template_output)

    def maybe_dump_shuffleboard_names(self, force):
        template_output = self._load_template("java/widget/shuffleboard_names_template.txt").render(
            **self.default_kwargs.copy()
        )

        # project_package_src_dir = os.path.abspath(os.path.join(project_dir, "src", "main", "java"))
        output_file = os.path.join(self.widget_package_src_dir, "SmartDashboardNames.java")
        if force or not os.path.exists(output_file):
            print("Create optional file " + output_file)
            open(output_file, "w").write(template_output)

    def verify_config(self):
        if len(self.widget_config["children_tables"]) == 1:
            child = self.widget_config["children_tables"][0]
            if self.widget_config["table"] != child["table"]:
                raise Exception(
                    f"If you only have one child, some short circuits will be used. Because of that, the widget table ('{self.widget_config['table']}') must matach the child table ('{child['table']}')"
                )

    def __make_dir_if_not_exists(self, dir_name):
        if not os.path.exists(dir_name):
            os.makedirs(dir_name)


class TopLevelGenerator:
    def __init__(self, template_dir, project_dir, overall_config):
        self.overall_config = overall_config
        self.template_dir = template_dir
        self.package_name_as_dir = package_to_dir(overall_config["base_package"])
        self.gen_src_dir = os.path.abspath(
            os.path.join(project_dir, "src", "dashboard_gen", "java")
        )
        self.main_src_dir = os.path.abspath(os.path.join(project_dir, "src", "main", "java"))
        self.plugin_dump_gen_dir = os.path.join(self.gen_src_dir, self.package_name_as_dir)
        self.plugin_dump_main_dir = os.path.join(self.main_src_dir, self.package_name_as_dir)

    def _load_template(self, template_file):
        return load_template(self.template_dir, template_file)

    def dump_plugin(self):

        template_output = self._load_template("dashboard_gen/plugin_template.txt").render(
            overall_config=self.overall_config
        )

        output_file = os.path.join(
            self.plugin_dump_gen_dir, self.overall_config["plugin_name"] + ".java"
        )
        open(output_file, "w").write(template_output)

    def maybe_dump_utils(self, force):

        template_output = self._load_template("java/Utils.java.txt").render(
            overall_config=self.overall_config
        )

        output_file = os.path.join(self.plugin_dump_main_dir, "Utils.java")

        if force or not os.path.exists(output_file):
            open(output_file, "w").write(template_output)


def maybe_add_standalone_buttons(widget_config):

    boolean_keys = [f"DIGIT{i}" for i in range(1, 10)] + ["DIGIT0"]
    double_keys = [
        ("Q", "A"),
        ("W", "S"),
        ("E", "D"),
        ("R", "F"),
        ("T", "G"),
        ("Y", "H"),
        ("U", "J"),
        ("I", "K"),
        ("O", "L"),
        ("P", "SEMICOLON"),
        ("BRACELEFT", "QUOTE"),
    ]

    def populate(base_type):
        if base_type == "boolean":
            if "sim_keys" not in entry:
                entry["sim_keys"] = boolean_keys.pop(0)
        else:
            if "sim_keys" not in entry:
                entry["sim_keys"] = double_keys.pop(0)
            if "sim_value" not in entry and "sim_incr" not in entry:
                entry["sim_value"] = 0.25

    for child in widget_config["children_tables"]:
        for entry in child["entries"]:
            base_type = entry["type"]
            if base_type.endswith("[]"):
                for i in range(entry["array_size"]):
                    base_type = base_type[:-2]
                    populate(base_type)
            else:
                populate(base_type)
