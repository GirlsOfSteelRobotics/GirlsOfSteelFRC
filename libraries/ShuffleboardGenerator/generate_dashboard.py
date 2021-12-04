import yaml
from lib.generate_dashboard_structure import WidgetGenerator, TopLevelGenerator, maybe_add_standalone_buttons
import os
import sys



def get_this_directory():

    try:
        from rules_python.python.runfiles import runfiles
        r = runfiles.Create()
        this_file = r.Rlocation("__main__/libraries/ShuffleboardGenerator/generate_dashboard.py")
        return  os.path.dirname(this_file)

    except ModuleNotFoundError:
        return os.path.dirname(os.path.realpath(__file__))

def main(argv):
    import argparse

    parser = argparse.ArgumentParser()

    parser.add_argument('--config_file', required=True, help='The path to the config fie')
    parser.add_argument('--project_dir', help='The path to the project you are generating')
    parser.add_argument('--force_nt_names', action="store_true", help='Force overwriting the network table name constants')
    parser.add_argument('--force_utils', action="store_true", help='Force overwriting the utils class')
    parser.add_argument('--force_fxml', action="store_true", help='Force overwriting fxml files')
    parser.add_argument('--force_standalone_main', action="store_true", help='Force overwriting standalone main tester')
    parser.add_argument('--force_controller', action="store_true", help='Force overwriting the controller files')

    parser.add_argument('--force_all', action="store_true", help='Force overwriting all of the non-autogenerted files')
    parser.add_argument('--force_standard', action="store_true", help='Force overwriting all of the non-autogenerted files')

    args = parser.parse_args(argv)
    this_dir = get_this_directory()

    template_dir = os.path.join(this_dir, "lib", "templates")
    print(template_dir)

    if not args.project_dir:
        print("Output directory not specified, using config file location")
        args.project_dir = os.path.dirname(args.config_file)

    if args.force_all:
        args.force_nt_names = True
        args.force_utils = True
        args.force_fxml = True
        args.force_standalone_main = True
        args.force_controller = True

    if not os.path.exists(args.project_dir):
        raise Exception(f"The output directory '{args.project_dir}' must exist")

    config = yaml.load(open(args.config_file, 'r'), Loader=yaml.SafeLoader)

    for widget in config['widgets']:
        print(f"Running generation for widget \"{widget['widget_name']}\"")
        maybe_add_standalone_buttons(widget)
        gen = WidgetGenerator(template_dir, args.project_dir, config['base_package'], widget)
        gen.verify_config()

        gen.dump_single_components()
        gen.dump_widget()
        gen.dump_widget_top_level_data()

        gen.maybe_dump_fxml(args.force_fxml)
        gen.maybe_dump_standalone_main(args.force_standalone_main)
        gen.maybe_dump_controller(args.force_controller)
        gen.maybe_dump_shuffleboard_names(args.force_nt_names)

    top_level_gen = TopLevelGenerator(template_dir, args.project_dir, config)
    top_level_gen.dump_plugin()
    top_level_gen.maybe_dump_utils(args.force_utils)


if __name__ == "__main__":
    main(sys.argv[1:])
