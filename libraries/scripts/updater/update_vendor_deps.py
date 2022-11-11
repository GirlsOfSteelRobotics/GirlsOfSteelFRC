"""
Downloads the latest version of the vendor dependences, and replaces all copies of them in the repository
"""
import os
import shutil
import tempfile
from urllib.request import urlopen, Request
from libraries.scripts.updater.utils import walk_with_blacklist
from libraries.scripts.git.git_python_wrappers import commit_all_changes

CACHE_DIRECTORY = os.path.join(tempfile.gettempdir(), "gos_vendor_cache")


def download_latest_vendordeps(ignore_cache):
    # fmt: off
    vendor_dep_urls = {}
    vendor_dep_urls["navx_frc.json"] = f"https://raw.githubusercontent.com/rzblue/navx-frc/maven/navx_frc.json"
    vendor_dep_urls["Phoenix.json"] = f"https://maven.ctr-electronics.com/release/com/ctre/phoenix/Phoenix5-frc2023-latest.json"
    vendor_dep_urls["REVLib.json"] = "https://software-metadata.revrobotics.com/REVLib-2023.json"
    vendor_dep_urls["SnobotSim.json"] = "http://raw.githubusercontent.com/snobotsim/maven_repo/master/development/SnobotSim.json"
    # fmt: on

    vendor_files = []

    if not os.path.exists(CACHE_DIRECTORY):
        os.mkdir(CACHE_DIRECTORY)

    for name, url in vendor_dep_urls.items():
        cached_file = os.path.join(CACHE_DIRECTORY, name)
        vendor_files.append(cached_file)
        if os.path.exists(cached_file) and not ignore_cache:
            continue

        print(f"Downloading {cached_file}")

        req = Request(url)
        req.add_header(
            "User-Agent",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7",
        )
        data = urlopen(req).read()
        with open(cached_file, "wb") as f:
            f.write(data)

    return vendor_files


def update_vendor_deps(ignore_cache=True, auto_commit=True):

    # Used when a vendor changes its filename name
    vendor_replacements = {}

    download_latest_vendordeps(ignore_cache)

    for root, dirs, files in walk_with_blacklist("."):
        if root.endswith("vendordeps"):
            for vendor_file in files:

                if vendor_file in ["WPILibNewCommands.json"]:
                    continue
                elif vendor_file in vendor_replacements:
                    new_file = os.path.join(CACHE_DIRECTORY, vendor_replacements[vendor_file])
                    file_to_write = os.path.join(root, vendor_replacements[vendor_file])
                    os.remove(os.path.join(root, vendor_file))
                else:
                    new_file = os.path.join(CACHE_DIRECTORY, vendor_file)
                    file_to_write = os.path.join(root, vendor_file)

                shutil.copy(new_file, file_to_write)

    if auto_commit:
        commit_all_changes("Auto-Update: Updating vendor deps")


def main():
    if "BUILD_WORKSPACE_DIRECTORY" in os.environ:
        os.chdir(os.environ["BUILD_WORKSPACE_DIRECTORY"])

    update_vendor_deps(ignore_cache=True, auto_commit=False)


if __name__ == "__main__":
    # py -m libraries.scripts.updater.update_vendor_deps
    main()
