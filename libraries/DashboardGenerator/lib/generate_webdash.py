import os

from libraries.DashboardGenerator.lib.dashboard_config import WidgetConfig, SmartType
from libraries.DashboardGenerator.lib.template_helpers import *


class WidgetGenerator:
    def __init__(
        self, template_dir: str, project_dir: str, overall_package, widget_config: WidgetConfig
    ):
        self.project_package_name = overall_package
        self.widget_config = widget_config
        self.template_dir = template_dir

        self.default_kwargs = {}
        self.default_kwargs["widget"] = self.widget_config

        self.widget_name = self.widget_config.package_name.package.replace("_", "-")

        self.src_dir = os.path.abspath(os.path.join(project_dir, "src"))
        self.component_directory = os.path.join(self.src_dir, self.widget_name)

    def get_javascript_type(self, type: SmartType):
        if type.is_number():
            return "Number"
        if type.is_boolean():
            return "Boolean"
        if type.is_string():
            return "String"
        return "TODO"

    def __render_template_to_file(self, rel_template_file: str, output_file: str, **kwargs) -> None:
        template_file = os.path.join(self.template_dir, rel_template_file)
        try:
            template = load_template2(template_file)
            template.globals[
                "subtable_name"
            ] = (
                lambda widget, child: f"{widget.package_name.as_dash()}-{child.table.as_hyphen_case()}"
            )
            template.globals["javascript_type"] = self.get_javascript_type
            render_template_to_file(template, output_file, **kwargs)
        except:
            print(f"Failed to render template {template_file}")
            raise

    def dump_data_structures(self):
        data_path = os.path.join(self.component_directory, "datatypes.ts")
        self.__render_template_to_file("datatype.jinja2", data_path, **self.default_kwargs)

    def dump_component(self):
        output_file = os.path.join(self.src_dir, f"{self.widget_name}.ts")
        self.__render_template_to_file("component.jinja2", output_file, **self.default_kwargs)

    ########################
    # The maybe generators
    ########################

    def maybe_dump_renderer(self, force):
        output_file = os.path.join(self.component_directory, "renderer.ts")
        if force or not os.path.exists(output_file):
            print("Create optional file " + output_file)
            self.__render_template_to_file("renderer.jinja2", output_file, **self.default_kwargs)

    def maybe_dump_shuffleboard_names(self, force):
        output_file = os.path.join(self.component_directory, "smart-dashboard-names.ts")
        if force or not os.path.exists(output_file):
            print("Create optional file " + output_file)
            self.__render_template_to_file(
                "smart_dashboard_names.jinja2", output_file, **self.default_kwargs
            )


def generate_web_dashboard(
    generator_directory,
    project_dir,
    config,
    force_nt_names,
    force_utils,
    force_controller,
):
    template_dir = os.path.join(generator_directory, "lib", "templates", "webdash")

    for widget in config.widgets:
        print(f'Running generation for widget "{widget.widget_name}"')
        gen = WidgetGenerator(template_dir, project_dir, config.base_package, widget)

        gen.dump_data_structures()
        gen.dump_component()

        gen.maybe_dump_shuffleboard_names(force_nt_names)
        gen.maybe_dump_renderer(force_controller)

    project_files_templates = os.path.join(template_dir, "project_files")
    for root, _, files in os.walk(project_files_templates):
        for f in files:
            template_file = os.path.join(root, f)
            rel_path = os.path.relpath(template_file, project_files_templates)
            output_file = os.path.join(project_dir, rel_path[:-7])

            # data_template = load_template(project_files_templates, rel_path)
            # with open(output_file, "w") as f:
            #     f.write(data_template.render(overall_config=config))
