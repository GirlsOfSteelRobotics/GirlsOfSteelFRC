import os
import sys
from libraries.scripts.updater.replace_gradlerio_files import (
    replace_gradlerio_files,
)
from libraries.scripts.updater.update_vendor_deps import update_vendor_deps
from libraries.scripts.updater.replace_old_names import run_all_replacements
from libraries.scripts.updater.update_bazelrio import update_bazelrio
from libraries.scripts.gradle.run_spotless import run_smart_spotless


def update_everything():
    if "BUILD_WORKSPACE_DIRECTORY" in os.environ:
        os.chdir(os.environ["BUILD_WORKSPACE_DIRECTORY"])

    update_vendor_deps()
    replace_gradlerio_files(True)
    run_all_replacements()
    update_bazelrio()
    run_smart_spotless(
        commands=["spotlessGroovyGradleApply", "spotlessMiscApply", "spotlessXmlApply"]
    )


if __name__ == "__main__":
    # py -m libraries.scripts.updater.update_everything
    update_everything()
