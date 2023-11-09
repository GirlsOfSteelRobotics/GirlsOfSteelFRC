import os
from jinja2 import Template


def load_template2(template_file: str) -> Template:
    with open(template_file, "r") as f:
        template_contents = f.read()
    return Template(template_contents)


def render_template_to_file(template: Template, output_file: str, **kwargs):
    parent_dir = os.path.dirname(output_file)
    if not os.path.exists(parent_dir):
        os.makedirs(parent_dir)

    with open(output_file, "w") as f:
        f.write(template.render(**kwargs))
