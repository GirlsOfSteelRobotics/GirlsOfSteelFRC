import os

from libraries.DashboardGenerator.lib.dashboard_config import (
    DashboardConfig,
    WidgetConfig,
    PackageName,
    EntryConfig,
)

from libraries.DashboardGenerator.lib.template_helpers import (
    render_template_to_file,
    load_template2,
)
from jinja2 import Template


class WidgetGenerator:
    def __init__(
        self,
        template_dir: str,
        project_dir: str,
        overall_package: PackageName,
        widget_config: WidgetConfig,
    ):
        self.project_package_name = overall_package
        self.widget_config = widget_config
        self.template_dir = template_dir

        self.widget_package = self.project_package_name.join(widget_config.package_name)

        self.gen_src_dir = os.path.abspath(
            os.path.join(project_dir, "src", "dashboard_gen", "java")
        )
        self.resource_dir = os.path.abspath(os.path.join(project_dir, "src", "main", "resources"))
        self.src_dir = os.path.abspath(os.path.join(project_dir, "src", "main", "java"))

        self.project_package_name_as_rel_dir = self.project_package_name.as_dir()
        self.widget_package_name_as_rel_dir = self.widget_package.as_dir()

        self.widget_package_src_dir = os.path.join(
            self.src_dir, self.widget_package_name_as_rel_dir
        )
        self.widget_package_gen_dir = os.path.join(
            self.gen_src_dir, self.widget_package_name_as_rel_dir
        )
        self.widget_resource_dir = os.path.join(
            self.resource_dir, self.widget_package_name_as_rel_dir
        )

        self.default_kwargs = {
            "widget": self.widget_config,
            "widget_package_name": self.widget_package,
            "project_package_name": self.project_package_name,
        }

    def _on_key_released(self, child, entry: EntryConfig, array_index=None):
        template_file = None

        if entry.type.is_number():
            if entry.sim_value:
                template_file = "java/keyboard_templates/key_released_sim_value.txt"
            elif entry.sim_incr:
                template_file = "java/keyboard_templates/key_released_sim_incr.txt"

        elif entry.type.is_boolean():
            template_file = "java/keyboard_templates/key_released_boolean.txt"
        elif entry.type.is_array:
            un_arrayed = entry.de_array(array_index)
            return self._on_key_released(child, un_arrayed)

        if template_file is None:
            print(f"No template for {entry}")
            return ""

        template = self.__load_template(template_file)
        return template.render(child=child, entry=entry)

    def _get_keys(self, child, entry: EntryConfig, array_index=None):
        if entry.type.is_number():
            return f"{entry.sim_keys[0]}/{entry.sim_keys[1]}"
        elif entry.type.is_boolean():
            return f"{entry.sim_keys}"
        elif entry.type.is_array:
            un_arrayed = entry.de_array(array_index)
            return self._get_keys(child, un_arrayed)
        else:
            return "UNKNOWN"

    def _on_key_pressed(self, child, entry: EntryConfig, array_index=None):
        template_file = None

        if entry.type.is_number():
            if entry.sim_value:
                template_file = "java/keyboard_templates/key_press_sim_value.txt"
            elif entry.sim_incr:
                template_file = "java/keyboard_templates/key_press_sim_incr.txt"

        elif entry.type.is_boolean():
            template_file = "java/keyboard_templates/key_press_boolean.txt"
        elif entry.type.is_array:
            un_arrayed = entry.de_array(array_index)
            return self._on_key_pressed(child, un_arrayed)

        if not template_file:
            return ""

        template = self.__load_template(template_file)
        output = template.render(child=child, entry=entry)
        return output

    def __load_template(self, rel_template_file: str) -> Template:
        template_file = os.path.join(self.template_dir, rel_template_file)
        template = load_template2(template_file)
        template.globals["on_key_pressed"] = self._on_key_pressed
        template.globals["get_keys"] = self._get_keys
        template.globals["on_key_released"] = self._on_key_released

        return template

    def __render_template_to_file(self, rel_template_file: str, output_file: str, **kwargs) -> None:
        template = self.__load_template(rel_template_file)
        render_template_to_file(template, output_file, **kwargs)

    def dump_single_components(self) -> None:
        for child in sorted(self.widget_config.children_tables, key=lambda x: 1):
            kwargs = self.default_kwargs.copy()
            kwargs["child"] = child

            data_path = os.path.join(self.widget_package_gen_dir, child.table + "Data.java")
            data_type_path = os.path.join(
                self.widget_package_gen_dir, child.table + "DataType.java"
            )

            self.__render_template_to_file(
                "dashboard_gen/widget/data_template.txt", data_path, **kwargs
            )
            self.__render_template_to_file(
                "dashboard_gen/widget/data_type_template.txt", data_type_path, **kwargs
            )

    def dump_widget(self) -> None:
        kwargs = self.default_kwargs.copy()

        output_file = os.path.join(
            self.widget_package_gen_dir, self.widget_config.table + "Widget.java"
        )

        self.__render_template_to_file(
            "dashboard_gen/widget/widget_template.txt", output_file, **kwargs
        )

    def dump_widget_top_level_data(self) -> None:
        if not self.widget_config.has_nested_tables:
            return

        data_output_file = os.path.join(
            self.widget_package_gen_dir, self.widget_config.table + "Data.java"
        )
        data_type_output_file = os.path.join(
            self.widget_package_gen_dir, self.widget_config.table + "DataType.java"
        )
        self.__render_template_to_file(
            "dashboard_gen/widget/parent_data_template.txt", data_output_file, **self.default_kwargs
        )
        self.__render_template_to_file(
            "dashboard_gen/widget/parent_data_type_template.txt",
            data_type_output_file,
            **self.default_kwargs,
        )

    ########################
    # The maybe generators
    ########################

    def maybe_dump_fxml(self, force: bool) -> None:
        snake_name = self.widget_config.table.camel_to_snake()

        controller_file = os.path.join(self.widget_resource_dir, snake_name + ".fxml")
        widget_file = os.path.join(self.widget_resource_dir, snake_name + "_widget.fxml")

        kwargs = self.default_kwargs.copy()

        if force or not os.path.exists(widget_file):
            print("Create optional file " + widget_file)
            self.__render_template_to_file(
                "resources/fxml_widget_template.txt", widget_file, **kwargs
            )

        if force or not os.path.exists(controller_file):
            print("Create optional file " + controller_file)
            self.__render_template_to_file(
                "resources/fxml_controller_template.txt", controller_file, **kwargs
            )

    def maybe_dump_standalone_main(self, force: bool) -> None:
        output_file = os.path.join(
            self.widget_package_src_dir, self.widget_config.table + "StandaloneMain.java"
        )

        if force or not os.path.exists(output_file):
            print("Create optional file " + output_file)
            self.__render_template_to_file(
                "java/standalone_main_template.txt", output_file, **self.default_kwargs
            )

    def maybe_dump_controller(self, force: bool) -> None:
        output_file = os.path.join(
            self.widget_package_src_dir, self.widget_config.table + "Controller.java"
        )
        if force or not os.path.exists(output_file):
            print("Create optional file " + output_file)
            self.__render_template_to_file(
                "java/widget/controller_template.txt", output_file, **self.default_kwargs
            )

    def maybe_dump_shuffleboard_names(self, force: bool) -> None:
        # project_package_src_dir = os.path.abspath(os.path.join(project_dir, "src", "main", "java"))
        output_file = os.path.join(self.widget_package_src_dir, "SmartDashboardNames.java")
        if force or not os.path.exists(output_file):
            print("Create optional file " + output_file)
            self.__render_template_to_file(
                "java/widget/shuffleboard_names_template.txt", output_file, **self.default_kwargs
            )


class TopLevelGenerator:
    def __init__(self, template_dir: str, project_dir: str, overall_config: DashboardConfig):
        self.overall_config = overall_config
        self.template_dir = template_dir
        self.package_name_as_dir = overall_config.base_package.as_dir()
        self.gen_src_dir = os.path.abspath(
            os.path.join(project_dir, "src", "dashboard_gen", "java")
        )
        self.main_src_dir = os.path.abspath(os.path.join(project_dir, "src", "main", "java"))
        self.plugin_dump_gen_dir = os.path.join(self.gen_src_dir, self.package_name_as_dir)
        self.plugin_dump_main_dir = os.path.join(self.main_src_dir, self.package_name_as_dir)

    def __render_template_to_file(self, rel_template_file: str, output_file: str, **kwargs) -> None:
        template_file = os.path.join(self.template_dir, rel_template_file)
        template = load_template2(template_file)

        render_template_to_file(template, output_file, **kwargs)

    def dump_plugin(self) -> None:
        output_file = os.path.join(
            self.plugin_dump_gen_dir, self.overall_config.plugin_name.name + ".java"
        )
        self.__render_template_to_file(
            "dashboard_gen/plugin_template.txt", output_file, overall_config=self.overall_config
        )

    def maybe_dump_utils(self, force: bool) -> None:
        output_file = os.path.join(self.plugin_dump_main_dir, "Utils.java")
        if force or not os.path.exists(output_file):
            self.__render_template_to_file(
                "java/Utils.java.txt", output_file, overall_config=self.overall_config
            )


def maybe_add_standalone_buttons(widget_config: WidgetConfig) -> None:
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
        ("Z", "X"),
        ("C", "V"),
        ("B", "N"),
        ("M", "COMMA"),
        ("PERIOD", "SLASH"),
        ("NUMPAD1", "NUMPAD2"),
        ("NUMPAD4", "NUMPAD5"),
        ("NUMPAD7", "NUMPAD8"),
        ("NUMPAD6", "NUMPAD9"),
        ("PLUS", "MINUS"),
        ("F1", "F2"),
        ("F3", "F4"),
        ("F5", "F6"),
        ("F7", "F8"),
    ]

    def populate(entry_: EntryConfig):
        if entry_.type.is_boolean():
            if not entry_.sim_keys:
                entry.sim_keys = boolean_keys.pop(0)
        else:
            if not entry_.sim_keys:
                entry_.sim_keys = double_keys.pop(0)
            if not entry_.sim_value and not entry_.sim_incr:
                entry_.sim_value = 0.25

    for child in widget_config.children_tables:
        for entry in child.entries:
            if entry.type.is_array:
                for i in range(entry.type.array_size):
                    entry.sim_keys_array.append(double_keys.pop(0))
                    populate(entry.de_array(i))
            else:
                populate(entry)


def generate_shuffleboard_dashboard(
    generator_directory: str,
    project_dir: str,
    config: DashboardConfig,
    force_nt_names: bool,
    force_utils: bool,
    force_fxml: bool,
    force_standalone_main: bool,
    force_controller: bool,
):
    template_dir = os.path.join(generator_directory, "lib", "templates", "shuffleboard")

    for widget in config.widgets:
        print(f'Running generation for widget "{widget.widget_name}"')
        maybe_add_standalone_buttons(widget)
        gen = WidgetGenerator(template_dir, project_dir, config.base_package, widget)

        gen.dump_single_components()
        gen.dump_widget()
        gen.dump_widget_top_level_data()

        gen.maybe_dump_fxml(force_fxml)
        gen.maybe_dump_standalone_main(force_standalone_main)
        gen.maybe_dump_controller(force_controller)
        gen.maybe_dump_shuffleboard_names(force_nt_names)

    top_level_gen = TopLevelGenerator(template_dir, project_dir, config)
    top_level_gen.dump_plugin()
    top_level_gen.maybe_dump_utils(force_utils)
