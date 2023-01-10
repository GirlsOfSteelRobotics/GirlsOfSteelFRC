"""
Downloads and overwrites any gradlerio specific files and gradle build files
"""

import re
from urllib.request import urlopen
from libraries.scripts.updater.utils import walk_for_extension, regex_replace_file
from libraries.scripts.git.git_python_wrappers import (
    commit_all_changes,
    checkout_files_from_branch,
)


def download_and_overwrite(url, destination, replacements=None):
    data = load_url(url)

    if replacements:
        contents = data.decode("utf-8")
        for repl_from, repl_to in replacements:
            contents = re.sub(repl_from, repl_to, contents)

        data = contents.encode("utf-8")

    with open(destination, "wb") as f:
        f.write(data)


def standard_update_robot_build_file(contents, build_file):
    with open(build_file, "w") as f:
        f.write(contents.replace("\r", ""))


def custom_update_robot_build_file(remote_build_file_data, build_file, latest_version):
    IGNORE_TEST_FAILURES_TEXT = "test {\n    ignoreFailures = true\n}"
    IGNORE_PMD_FAILURES_TEXT = "pmd {\n    ignoreFailures = true\n}"

    with open(build_file, "r") as f:
        original_contents = f.read()
        robot_class_matches = re.search(r'def ROBOT_MAIN_CLASS = "(.*)"', original_contents)
        robot_class = robot_class_matches.group(1)
        raw_build_contents = remote_build_file_data.replace(
            "###GRADLERIOREPLACE###", latest_version
        ).replace("###ROBOTCLASSREPLACE###", robot_class)

        internal_project_dependencies = re.findall(
            r"compile project(.*)", original_contents
        ) + re.findall(r"implementation project(.*)", original_contents)

        ignore_test_failures = re.findall(IGNORE_TEST_FAILURES_TEXT, original_contents)
        ignore_pmd_failures = re.findall(IGNORE_PMD_FAILURES_TEXT, original_contents)

    new_build_contents = ""
    lines_iter = iter(raw_build_contents.split("\n"))
    in_deps = False
    for line in lines_iter:
        if internal_project_dependencies:
            if line.startswith("dependencies {"):
                in_deps = True
            if in_deps and line.startswith("}"):
                new_build_contents += "\n\n"
                for internal_project in internal_project_dependencies:
                    new_build_contents += f"    implementation project{internal_project}\n"
                in_deps = False
        new_build_contents += line.rstrip() + "\n"

    if ignore_test_failures:
        new_build_contents += IGNORE_TEST_FAILURES_TEXT + "\n"

    if ignore_pmd_failures:
        new_build_contents += IGNORE_PMD_FAILURES_TEXT + "\n"

    with open(build_file, "wb") as f:
        f.write(new_build_contents.encode("utf-8"))


def update_non_robot_build_file(build_file, latest_version):

    replacements = []
    replacements.append(
        (
            ' +id "edu\.wpi\.first\.GradleRIO" version "(.*)"',
            f'    id "edu.wpi.first.GradleRIO" version "{latest_version}"',
        )
    )
    replacements.append((r"wpi\.deps\.wpilib\(\)", "wpi.java.deps.wpilib()"))
    replacements.append((r"wpi\.deps\.vendor\.java\(\)", "wpi.java.vendor.java()"))
    regex_replace_file(build_file, replacements)


def load_url(url_suffix, pinned_version="main"):
    project_subpath = f"wpilibsuite/vscode-wpilib"

    download_url = "/".join(
        ["https://raw.githubusercontent.com", project_subpath, pinned_version, url_suffix]
    )
    # print(f"Downloading from {download_url}")

    # content_url = "/".join(["https://github.com", project_subpath, "blob/", pinned_version, url_suffix])
    # print(f"See content at {content_url}")

    return urlopen(download_url).read()


def get_gradlerio_version():
    return load_url("vscode-wpilib/resources/gradle/version.txt").decode("utf-8").strip()


def update_build_files(run_custom_updates):
    latest_version = get_gradlerio_version()

    remote_build_file_data = load_url("vscode-wpilib/resources/gradle/java/build.gradle").decode(
        "utf-8"
    )

    raw_build_files = walk_for_extension(".", "build.gradle")
    for build_file in raw_build_files:
        with open(build_file, "r") as f:
            contents = f.read()
            if "edu.wpi.first.GradleRIO" in contents:
                if "def ROBOT_MAIN_CLASS = " in contents:
                    if run_custom_updates:
                        custom_update_robot_build_file(
                            remote_build_file_data, build_file, latest_version
                        )
                    else:
                        standard_update_robot_build_file(remote_build_file_data, build_file)
                else:
                    update_non_robot_build_file(build_file, latest_version)


def replace_gradlerio_files(run_custom_updates):
    # fmt: off
    download_and_overwrite("vscode-wpilib/resources/gradle/shared/gradlew", "gradlew")
    download_and_overwrite("vscode-wpilib/resources/gradle/shared/gradlew.bat", "gradlew.bat")
    download_and_overwrite("vscode-wpilib/resources/gradle/shared/gradle/wrapper/gradle-wrapper.jar", "gradle/wrapper/gradle-wrapper.jar")
    download_and_overwrite("vscode-wpilib/resources/gradle/shared/gradle/wrapper/gradle-wrapper.properties", "gradle/wrapper/gradle-wrapper.properties")
    download_and_overwrite("vscode-wpilib/resources/gradle/java/.wpilib/wpilib_preferences.json", ".wpilib/wpilib_preferences.json", replacements=[('"teamNumber": -1', '"teamNumber": 3504')])
    # fmt: on

    update_build_files(run_custom_updates)


def replace_gradlerio_files_in_parts():
    # Do a little git hack to break the update into two parts
    replace_gradlerio_files(False)
    commit_all_changes("Auto-Update: Raw update of gradlerio files")
    checkout_files_from_branch("HEAD~1", ["."])
    replace_gradlerio_files(True)
    commit_all_changes("Auto-Update: Update gradlerio files with our additions")


if __name__ == "__main__":
    replace_gradlerio_files(True)
