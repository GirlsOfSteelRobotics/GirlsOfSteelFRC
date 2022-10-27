import os
import subprocess


def __run_command(args, check_call=True, verbose=True):
    if verbose:
        print("Running command")
        print(" ".join(args))

    if check_call:
        subprocess.check_call(args)
    else:
        subprocess.call(args)


def run_gradle_commands(commands):
    assert type(commands) == list
    args = []

    if os.name == "nt":
        args.append("gradlew.bat")
    else:
        args.append("./gradlew")

    args.extend(commands)
    __run_command(args)
