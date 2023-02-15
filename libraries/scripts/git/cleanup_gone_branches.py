"""
Queries local branches and will delete any branches that no longer have an upstream (i.e. deleted from github once merged)
"""
import re
import os
import subprocess


def get_gone_branches():
    args = ["git", "branch", "-vv"]

    gone_branches = []
    branches = subprocess.check_output(args).decode("utf-8")
    for line in branches.split("\n"):
        search = re.findall(r"[ *] (\w+).*: gone", line)
        if search:
            branch = search[0].strip()
            gone_branches.append(branch)
    return gone_branches


def delete_gone_branches(gone_branches):
    print("Will attempt to delete the following branches:")
    for branch in gone_branches:
        print(f"  {branch}")
    ans = None

    while ans not in ["y", "n"]:
        ans = input("Do you want to continue? [y/n]: ").lower()

    if ans == "y":
        for branch in gone_branches:
            args = ["git", "branch", "-D", branch]
            subprocess.check_call(args)


def main():
    if "BUILD_WORKSPACE_DIRECTORY" in os.environ:
        os.chdir(os.environ["BUILD_WORKSPACE_DIRECTORY"])

    gone_branches = get_gone_branches()
    delete_gone_branches(gone_branches)


if __name__ == "__main__":
    main()
