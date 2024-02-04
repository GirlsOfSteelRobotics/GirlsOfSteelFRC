from libraries.DashboardGenerator.lib.dashboard_config import DashboardConfig
from libraries.DashboardGenerator.lib.generate_shuffleboard import (
    generate_shuffleboard_dashboard,
)
from libraries.DashboardGenerator.lib.generate_webdash import (
    generate_web_dashboard,
)
import os
import sys


def get_this_directory():
    try:
        from rules_python.python.runfiles import runfiles

        r = runfiles.Create()
        this_file = r.Rlocation("__main__/libraries/ShuffleboardGenerator/generate_dashboard.py")
        return os.path.dirname(this_file)

    except ModuleNotFoundError:
        return os.path.dirname(os.path.realpath(__file__))


def main(argv):
    import argparse

    parser = argparse.ArgumentParser()

    parser.add_argument("--config_file", required=True, help="The path to the config fie")
    parser.add_argument("--project_dir", help="The path to the project you are generating")
    parser.add_argument(
        "--force_nt_names",
        action="store_true",
        help="Force overwriting the network table name constants",
    )
    parser.add_argument(
        "--force_utils", action="store_true", help="Force overwriting the utils class"
    )
    parser.add_argument("--force_fxml", action="store_true", help="Force overwriting fxml files")
    parser.add_argument(
        "--force_standalone_main",
        action="store_true",
        help="Force overwriting standalone main tester",
    )
    parser.add_argument(
        "--force_controller", action="store_true", help="Force overwriting the controller files"
    )

    parser.add_argument(
        "--force_all",
        action="store_true",
        help="Force overwriting all of the non-autogenerted files",
    )
    parser.add_argument(
        "--force_standard",
        action="store_true",
        help="Force overwriting all of the non-autogenerted files",
    )
    parser.add_argument(
        "--generate_web_dashboard", action="store_true", help="If present, will generate web dashboard"
    )

    args = parser.parse_args(argv)

    if args.force_all:
        args.force_nt_names = True
        args.force_utils = True
        args.force_fxml = True
        args.force_standalone_main = True
        args.force_controller = True

    generate_dashboard(
        args.config_file,
        args.project_dir,
        args.force_nt_names,
        args.force_utils,
        args.force_fxml,
        args.force_standalone_main,
        args.force_controller,
        args.generate_web_dashboard
    )


def generate_dashboard(
    config_file,
    project_dir,
    force_nt_names,
    force_utils,
    force_fxml,
    force_standalone_main,
    force_controller,
    generate_web_dash=False,
):
    this_dir = get_this_directory()
    generator_directory = this_dir

    if not project_dir:
        print("Output directory not specified, using config file location")
        project_dir = os.path.dirname(config_file)

    if not os.path.exists(os.path.dirname(project_dir)):
        raise Exception(f"The output directory '{os.path.dirname(project_dir)}' must exist")

    config = DashboardConfig.from_yaml_file(config_file)

    generate_shuffleboard_dashboard(
        generator_directory,
        project_dir + "Dashboard",
        config,
        force_nt_names,
        force_utils,
        force_fxml,
        force_standalone_main,
        force_controller,
    )

    if generate_web_dash:
        generate_web_dashboard(
            generator_directory,
            project_dir + "WebDashboard/Plugin",
            config,
            force_nt_names,
            force_utils,
            force_controller,
        )

    print(f"Generating dashboard config in '{os.path.abspath(project_dir)}'")


if __name__ == "__main__":
    "py -m libraries.DashboardGenerator.generate_dashboard --config_file=y2022/RapidReactDashboard/dashboard.yml"
    main(sys.argv[1:])
