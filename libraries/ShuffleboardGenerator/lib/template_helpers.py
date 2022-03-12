
from jinja2 import Template
import re
import os


def _lower_first_char(input_str):
    if len(input_str) == 0:
        raise Exception("Cannot use an empty string for a field name!")
    return input_str[0].lower() + input_str[1:]


def camel_to_snake(input_str):
    s1 = re.sub('(.)([A-Z][a-z]+)', r'\1_\2', input_str)
    return re.sub('([a-z0-9])([A-Z])', r'\1_\2', s1).lower()


def _upper_snake_to_camel(input_str):
    components = input_str.lower().split('_')
    if len(components) == 0:
        raise Exception("Cannot use an empty string!")

    output = components[0] + ''.join(x.title() for x in components[1:])
    output = output[0].upper() + output[1:]
    return output


def _cap_first_char(var_name):
    return var_name[0].upper() + var_name[1:]


def package_to_dir(package_name):
    return package_name.replace(".", "/")


def _getter_name(variable):

    if variable['type'] == 'boolean':
        return "is"

    return "get"


def _is_array(variable):
    the_type = variable['type']
    return the_type.endswith("[]")


def _default_array(variable):
    base_type = variable['type'][:-2]
    return "{" + ", ".join([__default_value_for_type(base_type) for _ in range(variable['array_size'])]) + "}"


def __default_value_for_type(the_type):

    default_lookup = {}
    default_lookup["Double"] = "0.0"
    default_lookup["double"] = "0.0"
    default_lookup["Boolean"] = "false"
    default_lookup["boolean"] = "false"
    default_lookup["String"] = '""'

    return default_lookup[the_type]


def _default_value_lookup(variable):
    the_type = variable['type']

    if _is_array(variable):
        return "DEFAULT_" + camel_to_snake(variable['name']).upper()

    return __default_value_for_type(the_type)


def _on_key_released(child, entry, array_index = None):

    template_file = None

    if entry['type'] == "double":
        if 'sim_value' in entry:
            template_file = "java/keyboard_templates/key_released_sim_value.txt"
        elif "sim_incr" in entry:
            template_file = "java/keyboard_templates/key_released_sim_incr.txt"

    elif entry['type'] == "boolean":
        template_file = "java/keyboard_templates/key_released_boolean.txt"
    elif _is_array(entry):
        un_arrayed = entry.copy()
        un_arrayed['type'] = un_arrayed['type'][:-2]
        un_arrayed['name'] += str(array_index)
        return _on_key_released(child, un_arrayed)

    if template_file is None:
        return ""

    template_dir = os.path.join(os.path.dirname(os.path.realpath(__file__)), "templates")
    with open(os.path.join(template_dir, template_file), 'r') as f:
        template_input = f.read()
        template = Template(template_input)
        template = _add_template_functions(template)

    output = template.render(child=child, entry=entry)

    return output


def _get_keys(child, entry, array_index=None):
    if entry["type"] == "double":
        return f"{entry['sim_keys'][0]}/{entry['sim_keys'][1]}"
    elif entry["type"] == "boolean":
        return f"{entry['sim_keys']}"
    elif _is_array(entry):
        un_arrayed = entry.copy()
        un_arrayed["type"] = un_arrayed["type"][:-2]
        un_arrayed["name"] += str(array_index)
        return _get_keys(child, un_arrayed)
    else:
        return "UNKNOWN"


def _on_key_pressed(child, entry, array_index=None):

    template_file = None

    if entry['type'] == "double":
        if 'sim_value' in entry:
            template_file = "java/keyboard_templates/key_press_sim_value.txt"
        elif 'sim_incr' in entry:
            template_file = "java/keyboard_templates/key_press_sim_incr.txt"

    elif entry['type'] == "boolean":
        template_file = "java/keyboard_templates/key_press_boolean.txt"
    elif _is_array(entry):
        un_arrayed = entry.copy()
        un_arrayed['type'] = un_arrayed['type'][:-2]
        un_arrayed['name'] += str(array_index)
        return _on_key_pressed(child, un_arrayed)

    if not template_file:
        return ""

    template_dir = os.path.join(os.path.dirname(os.path.realpath(__file__)), "templates")
    with open(os.path.join(template_dir, template_file), 'r') as f:
        template_input = f.read()
        template = Template(template_input)
        template = _add_template_functions(template)

    output = template.render(child=child, entry=entry)
    return output


def _add_template_functions(template):
    template.globals['camel_to_snake'] = camel_to_snake
    template.globals['upper_snake_to_camel'] = _upper_snake_to_camel
    template.globals['lower_first_char'] = _lower_first_char
    template.globals['package_to_dir'] = package_to_dir
    template.globals['cap_first_char'] = _cap_first_char
    template.globals['on_key_pressed'] = _on_key_pressed
    template.globals["get_keys"] = _get_keys
    template.globals["on_key_released"] = _on_key_released
    template.globals['getter_name'] = _getter_name
    template.globals['default_value_lookup'] = _default_value_lookup
    template.globals['is_array'] = _is_array
    template.globals['default_array'] = _default_array


    return template


def load_template(template_dir, template_name):

    template_path = os.path.join(template_dir, template_name)

    with open(template_path, 'r') as f:
        template_input = f.read()
        template = Template(template_input)
        template = _add_template_functions(template)

        return template
