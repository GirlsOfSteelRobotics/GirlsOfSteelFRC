"""
Helper script for hackily running spotless on every file in the repository. Does so by cherry picking a branch that has
all the projects enabled, rather than only the relevant ones for this season.
"""
from libraries.scripts.gradle.gradlew_python_wrapper import run_gradle_commands
from libraries.scripts.git.git_python_wrappers import (
    commit_all_changes,
    checkout_files_from_branch,
)


def run_smart_spotless(run_all_projects=True, auto_commit=True, commands=None):
    all_project_files = ["styleguide/styleguide.gradle", "settings.gradle"]
    if run_all_projects:
        checkout_files_from_branch("origin/dnl_enable_all_projects_and_spotless", all_project_files)

    commands = commands or ["spotlessApply"]

    run_gradle_commands(commands)

    if run_all_projects:
        checkout_files_from_branch("HEAD", all_project_files)

    if auto_commit:
        commit_all_changes("Auto-Update: Ran spotless")
