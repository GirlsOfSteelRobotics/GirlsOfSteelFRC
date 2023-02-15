import os
import json
import hashlib
from urllib.request import urlopen
from libraries.scripts.updater.update_vendor_deps import download_latest_vendordeps
from libraries.scripts.updater.replace_gradlerio_files import get_gradlerio_version
from libraries.scripts.git.git_python_wrappers import commit_all_changes


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
    data = urlopen(url).read()
    sha = hashlib.sha256(data).hexdigest()
    return sha


def update_bazelrio(auto_commit=True, ignore_cache=False):
    vendor_deps_files = download_latest_vendordeps(ignore_cache)

    java_libraries_with_sha = [
        "SNOBOTSIM",
        "PHOTONLIB_JSON_1_0",
        "PATHPLANNERLIB",
    ]

    bazelrio_versions = ""
    for vendor_file in vendor_deps_files:
        with open(vendor_file, "r") as f:
            vendor_name = os.path.basename(vendor_file)[: -len(".json")].upper()
            vendor_name = vendor_name.replace("-", "_")
            vendor_name = vendor_name.replace(".", "_")
            vendor_dep = json.load(f)
            version = vendor_dep["version"].replace("+", "_").replace("_", "_")
            bazelrio_versions += f'{vendor_name}_VERSION = "{version}"\n'

            if vendor_name in java_libraries_with_sha:
                bazelrio_versions += (
                    f'{vendor_name}_VERSION_SHA = "{get_java_dep_sha(vendor_dep, version)}"\n'
                )

    bazelrio_versions += f'WPILIB_VERSION = "{get_gradlerio_version()}"\n'

    with open("build_scripts/bazel/deps/versions.bzl", "w") as f:
        f.write(bazelrio_versions)

    if auto_commit:
        commit_all_changes("Auto-Update: Updating bazelrio")


def main():
    if "BUILD_WORKSPACE_DIRECTORY" in os.environ:
        os.chdir(os.environ["BUILD_WORKSPACE_DIRECTORY"])

    update_bazelrio(auto_commit=False, ignore_cache=True)


if __name__ == "__main__":
    # py -m libraries.scripts.updater.update_bazelrio
    main()
