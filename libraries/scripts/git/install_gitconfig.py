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
    subprocess.check_call(
        ["git", "config", "--global", "--add", config_name, config_value],
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
    )


def main():
    if "BUILD_WORKSPACE_DIRECTORY" in os.environ:
        os.chdir(os.environ["BUILD_WORKSPACE_DIRECTORY"])

    aliases = {}
    aliases["slog"] = "log --oneline -n 40"
    aliases["glog"] = "log --oneline --graph"
    aliases["gloga"] = "glog --all"
    aliases["cane"] = "commit --amend --no-edit"
    aliases["dno"] = "diff --name-only"
    aliases["dnol"] = "diff --name-only HEAD~1"
    aliases["diffl"] = "diff HEAD~1"

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


if __name__ == "__main__":
    main()
