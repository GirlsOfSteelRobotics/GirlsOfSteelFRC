"""
"""

load("@bazelrio//:defs.bzl", "setup_bazelrio")
load("@bazelrio//:deps.bzl", "setup_bazelrio_dependencies")
load("@rules_pmd//pmd:dependencies.bzl", "rules_pmd_dependencies")

def setup_dependencies():
    """
    Second step of dependency initialization.
    """
    rules_pmd_dependencies()
    setup_bazelrio_dependencies(
        toolchain_versions = "2022-1",
        wpilib_version = "2022.4.1",
        ni_version = "2022.4.0",
        opencv_version = "4.5.2-1",
        revlib_version = "2022.1.0",
        phoenix_version = "5.20.2",
        navx_version = "4.0.442",
    )
    setup_bazelrio()
