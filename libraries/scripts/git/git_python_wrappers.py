"""
Python wrappers around git commands to ease script writing
"""
import subprocess


def __run_command(args, check_call=True, verbose=True):
    if verbose:
        print("Running command")
        print(" ".join(args))

    if check_call:
        subprocess.check_call(args)
    else:
        subprocess.call(args)


def commit_all_changes(message):
    # Add the files
    __run_command(["git", "add", "*"])

    # Commit the files
    __run_command(["git", "commit", "-m", message], check_call=False)


def checkout_files_from_branch(commitish, files):
    assert type(files) == list

    args = ["git", "checkout", commitish, "--"] + files
    __run_command(args)
