"""
"""

load("@bazelrio//:defs.bzl", "setup_bazelrio")
load("@bazelrio//:deps.bzl", "setup_bazelrio_dependencies")
load("@rules_pmd//pmd:dependencies.bzl", "rules_pmd_dependencies")
load("//build_scripts/bazel/deps:versions.bzl", "NAVX_FRC_VERSION", "PHOENIX_VERSION", "REVLIB_VERSION", "WPILIB_VERSION")

SNOBOT_SIM_VERSION = "2022.2.2.0"

def setup_dependencies():
    """
    Second step of dependency initialization.
    """
    rules_pmd_dependencies()
    setup_bazelrio_dependencies(
        wpilib_version = WPILIB_VERSION,
        revlib_version = REVLIB_VERSION,
        phoenix_version = PHOENIX_VERSION,
        navx_version = NAVX_FRC_VERSION,
    )
    setup_bazelrio()
