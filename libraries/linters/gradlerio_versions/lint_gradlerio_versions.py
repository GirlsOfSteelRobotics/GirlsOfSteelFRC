import sys
import os
import re
import collections
import json
import shutil


def get_build_file_versions(build_files):
    versions = collections.defaultdict(list)

    for build_file in build_files:
        with open(build_file, 'r') as f:
            for line in f.readlines():
                matches = re.search(r'edu\.wpi\.first\.GradleRIO.* version "(.*)"', line)
                if matches:
                    version = matches.group(1)
                    versions[version].append(build_file)

    return versions


def get_vendor_deps_versions(vendor_deps):
    vendor_versions = {}
    for vendor_name, vendor_files in vendor_deps.items():
        versions = collections.defaultdict(list)

        for vendor_file in vendor_files:
            with open(vendor_file, 'r') as f:
                vendor_dep = json.load(f)
                version = vendor_dep['version']
                versions[version].append(vendor_file)

        vendor_versions[vendor_name] = versions

    return vendor_versions


def get_versions(base_directory):
    build_files = []
    vendor_deps = collections.defaultdict(list)

    for root, _, files in os.walk(base_directory):
        for f in files:
            full_file = os.path.join(root, f)
            if f == "build.gradle":
                build_files.append(full_file)
            elif "vendordeps" in os.path.dirname(full_file):
                vendor_deps[f].append(full_file)

    gradlerio_versions = get_build_file_versions(build_files)
    vendor_deps_versions = get_vendor_deps_versions(vendor_deps)

    return gradlerio_versions, vendor_deps_versions


def fix_vendordep_version(versions):
    sorted_versions = sorted(list(versions.keys()), reverse=True)
    newest_version = sorted_versions[0]
    newest_file = versions[newest_version][0]

    print(f"Using {newest_file}, version {newest_version}")

    for version, files in versions.items():
        if version == newest_version:
            continue

        for f in files:
            print(f"  Fixing {f}")
            shutil.copy(newest_file, f)


def fix_gradlerio_build_file(versions):
    sorted_versions = sorted(list(versions.keys()), reverse=True)
    newest_version = sorted_versions[0]
    newest_file = versions[newest_version][0]
    print(f"Using {newest_file}, version {newest_version}")

    for version, files in versions.items():
        if version == newest_version:
            continue

        for bad_file in files:
            print(f"  Fixing {bad_file}")
            new_content = ""
            with open(bad_file, 'r') as f:
                for line in f.readlines():
                    matches = re.search(r'edu\.wpi\.first\.GradleRIO.* version "(.*)"', line)
                    if matches:
                        new_content += f'    id "edu.wpi.first.GradleRIO" version "{newest_version}"\n'
                    else:
                        new_content += line

            with open(bad_file, 'w') as f:
                f.write(new_content)


def get_this_directory():

    try:
        from rules_python.python.runfiles import runfiles
        r = runfiles.Create()
        this_file = r.Rlocation("__main__/libraries/linters/gradlerio_versions/lint_gradlerio_versions.py")
        return  os.path.dirname(this_file)

    except ModuleNotFoundError:
        return os.path.dirname(os.path.realpath(__file__))

def main():

    base_directory = os.path.join(get_this_directory(), "..", "..", "..")

    gradlerio_versions, vendor_deps_versions = get_versions(base_directory)

    passed = True

    if len(gradlerio_versions) == 0:
        raise Exception(f"No build files were found. Check base directory '{base_directory}'")


    if len(gradlerio_versions) != 1:
        fix_gradlerio_build_file(gradlerio_versions)
        passed = False

    for vendor_name, vendor_versions in vendor_deps_versions.items():
        if len(vendor_versions) != 1:
            passed = False
            fix_vendordep_version(vendor_versions)

    if not passed:
        sys.exit(-1)


if __name__ == "__main__":
    main()
