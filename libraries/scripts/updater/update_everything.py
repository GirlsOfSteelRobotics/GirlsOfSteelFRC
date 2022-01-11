import os
import sys

if "BUILD_WORKSPACE_DIRECTORY" in os.environ:
    os.chdir(os.environ["BUILD_WORKSPACE_DIRECTORY"])
else:
    sys.path.append(
        os.path.join(os.path.dirname(os.path.realpath(__file__)), "..", "..", "..")
    )

from libraries.scripts.updater.replace_gradlerio_files import (
    replace_gradlerio_files_in_parts,
)
from libraries.scripts.updater.update_vendor_deps import update_vendor_deps
from libraries.scripts.updater.replace_old_names import run_all_replacements
from libraries.scripts.gradle.run_spotless import run_smart_spotless


def update_everything():
    if "BUILD_WORKSPACE_DIRECTORY" in os.environ:
        os.chdir(os.environ["BUILD_WORKSPACE_DIRECTORY"])

    update_vendor_deps()
    replace_gradlerio_files_in_parts()
    run_all_replacements()
    run_smart_spotless()


if __name__ == "__main__":
    update_everything()
