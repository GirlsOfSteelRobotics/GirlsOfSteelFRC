load("@rules_pmd//pmd:dependencies.bzl", "rules_pmd_dependencies")
load("@bazelrio//:defs.bzl", "setup_bazelrio")
load("@bazelrio//:deps.bzl", "setup_bazelrio_dependencies")

def setup_dependencies():
    rules_pmd_dependencies()
    setup_bazelrio_dependencies(
        toolchain_versions = "2021",
        wpilib_version = "2021.3.1",
        ni_version = "2020.9.2",
        opencv_version = "3.4.7-5",
        sparkmax_version = "1.5.4",
        phoenix_version = "5.19.4",
        navx_version = "4.0.425",
    )
    setup_bazelrio()
