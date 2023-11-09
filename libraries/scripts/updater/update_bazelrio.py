import os
import re
import json
import hashlib
import subprocess
from libraries.scripts.updater.update_vendor_deps import download_latest_vendordeps
from libraries.scripts.updater.replace_gradlerio_files import get_gradlerio_version
from libraries.scripts.git.git_python_wrappers import commit_all_changes
from libraries.scripts.updater.utils import auto_retry_download


def get_java_dep_sha(vendor_dep, version):
    java_dep = vendor_dep["javaDependencies"][0]
    group_path = java_dep["groupId"].replace(".", "/")
    artifact_id = java_dep["artifactId"]
    url = "/".join(
        [
            vendor_dep["mavenUrls"][0],
            group_path,
            java_dep["artifactId"],
            version,
            f"{artifact_id}-{version}.jar",
        ]
    )
    data = auto_retry_download(url)
    sha = hashlib.sha256(data).hexdigest()
    return sha


def update_bazelrio_rule():
    bazelrio_repo = "bzlmodRio/bzlmodRio"
    bazelrio_branch = "refactor_dev"

    data = auto_retry_download(
        f"https://api.github.com/repos/{bazelrio_repo}/branches/{bazelrio_branch}"
    )
    bazelrio_commitish = json.loads(data.decode("utf-8"))["commit"]["sha"]

    bazelrio_url = f"https://github.com/{bazelrio_repo}/archive/{bazelrio_commitish}.tar.gz"
    data = auto_retry_download(bazelrio_url)
    bazelrio_sha256 = hashlib.sha256(data).hexdigest()

    with open("build_scripts/bazel/deps/download_external_archives.bzl", "r") as f:
        contents = f.read()

    contents = re.sub(
        'BZLMODRIO_COMMITISH = ".*"', f'BZLMODRIO_COMMITISH = "{bazelrio_commitish}"', contents
    )
    contents = re.sub(
        'BZLMODRIO_SHA256 = ".*"', f'BZLMODRIO_SHA256 = "{bazelrio_sha256}"', contents
    )

    with open("build_scripts/bazel/deps/download_external_archives.bzl", "w") as f:
        f.write(contents)


def update_bazelrio(auto_commit=True, ignore_cache=False):
    update_bazelrio_rule()

    vendor_deps_files = download_latest_vendordeps(ignore_cache)

    java_libraries_with_sha = []

    bazelrio_versions = ""
    for vendor_file in vendor_deps_files:
        if vendor_file.endswith("WPILibNewCommands.json"):
            continue
        with open(vendor_file, "r") as f:
            vendor_name = os.path.basename(vendor_file)[: -len(".json")].upper()
            vendor_name = vendor_name.replace("-", "_")
            vendor_name = vendor_name.replace(".", "_")
            vendor_dep = json.load(f)
            version = vendor_dep["version"]
            bazelrio_versions += f'{vendor_name}_VERSION = "{version}"\n'

            if vendor_name in java_libraries_with_sha:
                bazelrio_versions += (
                    f'{vendor_name}_VERSION_SHA = "{get_java_dep_sha(vendor_dep, version)}"\n'
                )

    bazelrio_versions += f'WPILIB_VERSION = "{get_gradlerio_version()}"\n'

    with open("build_scripts/bazel/deps/versions.bzl", "w") as f:
        f.write(bazelrio_versions)

    subprocess.check_call(["bazel", "run", "@unpinned_maven//:pin"])

    if auto_commit:
        commit_all_changes("Auto-Update: Updating bazelrio")


def main():
    if "BUILD_WORKSPACE_DIRECTORY" in os.environ:
        os.chdir(os.environ["BUILD_WORKSPACE_DIRECTORY"])

    update_bazelrio(auto_commit=False, ignore_cache=True)


if __name__ == "__main__":
    # py -m libraries.scripts.updater.update_bazelrio
    main()
