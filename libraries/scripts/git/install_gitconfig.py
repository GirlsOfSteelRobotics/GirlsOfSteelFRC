"""
Helper script to install git goodies, and verify the user has been set up.
"""

import os
import subprocess


def config_exists(config_name):
    try:
        subprocess.check_call(
            ["git", "config", "--get", config_name],
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
        )
        return True
    except subprocess.CalledProcessError:
        return False


def add_config(config_name, config_value):
    args = ["git", "config", "--global", "--add", config_name, config_value]
    subprocess.check_call(
        args,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
    )


def install_hooks():
    with open(".git/hooks/pre-commit", 'w') as f:
        f.write("""#!/bin/sh
branch="$(git rev-parse --abbrev-ref HEAD)"

if [ "$branch" = "main" ]; then
  echo "GOS: You can't commit directly to the main branch! Please create a feature branch"
  exit 1
fi

is_gone="$(git status -sb | grep '\[gone\]')"
if [ -n "$is_gone" ]; then
  echo "GOS: You are on a branch with a remote upstream that has been deleted, you are probably incorrectly reusing a branch name?"
  exit 1
fi

unstaged_pathplanner_files="$(git ls-files --others --exclude-standard *pathplanner/**.path)"
if [ "$unstaged_pathplanner_files" ]; then
  echo "GOS: You have unstaged pathplanner files. Check for 'Unversioned Files' in the commit tab"
  echo $unstaged_pathplanner_files
  exit 1
fi
""")
    print("Added hooks")

def main():
    if "BUILD_WORKSPACE_DIRECTORY" in os.environ:
        os.chdir(os.environ["BUILD_WORKSPACE_DIRECTORY"])

    script_dir = os.path.dirname(os.path.realpath(__file__))

    aliases = {}
    aliases["slog"] = "log --oneline -n 40"
    aliases["glog"] = "log --oneline --graph"
    aliases["gloga"] = "glog --all"
    aliases["cane"] = "commit --amend --no-edit"
    aliases["dno"] = "diff --name-only"
    aliases["dnol"] = "diff --name-only HEAD~1"
    aliases["diffl"] = "diff HEAD~1"
    aliases["goscleanup"] = "!python " + os.path.join(script_dir, "cleanup_gone_branches.py").replace("\\", "/")

    if not config_exists("user.name"):
        username = input("Git does not know your name. Please enter your full name: ")
        add_config("user.name", username)
    if not config_exists("user.email"):
        email = input(
            "Git does not know your email address. Please enter your email associated with github: "
        )
        add_config("user.email", email)

    for alias_name, alias_command in aliases.items():
        if not config_exists("alias." + alias_name):
            print(f"Adding alias {alias_name}")
            add_config("alias." + alias_name, alias_command)

    install_hooks()

    # Prune branches on fetch
    subprocess.check_call(["git", "config", "--local", "remote.origin.prune", "true"])


if __name__ == "__main__":
    main()
