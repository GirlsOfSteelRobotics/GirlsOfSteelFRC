
import re
import sys
import pathlib


def camel_case_to_upper_snake_case(variable):
    pattern = re.compile(r'(?<!^)(?=[A-Z])')
    name = pattern.sub('_', variable).upper()
    return name


def run_conversion(file_to_convert: pathlib.Path):
    contents = file_to_convert.read_text()

    constants = re.findall("public static final .* (.*) =", contents)

    for constant in constants:
        constant_as_snake = camel_case_to_upper_snake_case(constant)
        contents = re.sub(
            f"public static final (.*) {constant} =",
            f"public static final \\1 {constant_as_snake} =",
            contents)

        contents = re.sub(
            constant + r"\[",
            f"{constant_as_snake}[",
            contents)
        contents = re.sub(
            constant + r"\.",
            f"{constant_as_snake}.",
            contents)
        contents = re.sub(
            constant + r"\,",
            f"{constant_as_snake},",
            contents)

    file_to_convert.write_text(contents)


def main(argv):
    file_to_convert = pathlib.Path(argv[0])

    run_conversion(file_to_convert)


if __name__ == "__main__":
    # python3 -m libraries.scripts.gosify_constants_styleguide y2025/Reefscape/src/main/java/org/littletonrobotics/frc2025/FieldConstants.java
    main(sys.argv[1:])