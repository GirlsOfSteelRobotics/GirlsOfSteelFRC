"""
Girls of Steel rules for java. Mostly just adds automatic hooks into styleguide tools
"""

load("@bazelrio//:defs.bzl", "robot_java_binary")
load("@rule_junit//tools:junit.bzl", junit5_tests = "junit_tests")
load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("@rules_pmd//pmd:defs.bzl", "pmd")

def __styleguide(name, srcs, disable_pmd, disable_checkstyle):
    if not disable_pmd:
        pmd(
            name = name + "-pmd_analysis",
            srcs = srcs,
            rulesets = ["//styleguide:pmd_rules"],
            tags = ["java-styleguide"],
        )
    else:
        print("PMD Disabled for " + name)  # buildifier: disable=print

def get_default_javac_opts():
    return ["-Werror", "-Xlint:all"]

def gos_java_library(name, srcs, disable_pmd = False, disable_checkstyle = False, javacopts = [], **kwargs):
    java_library(
        name = name,
        srcs = srcs,
        javacopts = get_default_javac_opts() + javacopts,
        **kwargs
    )

    __styleguide(name, srcs, disable_pmd, disable_checkstyle)

def gos_java_binary(
        name,
        main_class = None,
        srcs = [],
        deps = [],
        runtime_deps = [],
        disable_pmd = False,
        disable_checkstyle = False,
        **kwargs):
    java_binary(
        name = name,
        srcs = srcs,
        deps = deps,
        runtime_deps = runtime_deps,
        main_class = main_class,
        javacopts = get_default_javac_opts(),
        **kwargs
    )

    if srcs:
        __styleguide(name, srcs, disable_pmd, disable_checkstyle)

def gos_junit5_test(name, srcs, deps = [], runtime_deps = [], args = [], package = "org", disable_pmd = False, disable_checkstyle = False, **kwargs):

    junit_deps = [
        "@maven//:org_junit_jupiter_junit_jupiter_api",
        "@maven//:org_junit_jupiter_junit_jupiter_params",
        "@maven//:org_junit_jupiter_junit_jupiter_engine",
    ]

    junit_runtime_deps = [
        "@maven//:org_junit_platform_junit_platform_commons",
        "@maven//:org_junit_platform_junit_platform_console",
        "@maven//:org_junit_platform_junit_platform_engine",
        "@maven//:org_junit_platform_junit_platform_launcher",
        "@maven//:org_junit_platform_junit_platform_suite_api",
    ]

    native.java_test(
        name = name,
        srcs = srcs,
        deps = deps + junit_deps,
        runtime_deps = runtime_deps + junit_runtime_deps,
        args = args + ["--select-package", package],
        main_class = "org.junit.platform.console.ConsoleLauncher",
        use_testrunner = False,
        **kwargs
    )

    if srcs:
        __styleguide(name, srcs, disable_pmd, disable_checkstyle)

def gos_java_robot(
        name,
        main_class,
        srcs = [],
        deps = [],
        runtime_deps = [],
        data = [],
        disable_pmd = False,
        disable_checkstyle = False,
        **kwargs):
    robot_java_binary(
        name = name,
        team_number = 3504,
        main_class = main_class,
        srcs = srcs,
        deps = deps,
        data = data,
        runtime_deps = runtime_deps + [
            "@maven//:org_ejml_ejml_simple",
        ],
        javacopts = get_default_javac_opts(),
        **kwargs
    )

    if srcs:
        __styleguide(name, srcs, disable_pmd, disable_checkstyle)
