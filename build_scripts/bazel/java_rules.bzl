"""
Girls of Steel rules for java. Mostly just adds automatic hooks into styleguide tools
"""

load("@bazelrio//:defs.bzl", "robot_java_binary")
load("@rule_junit//tools:junit.bzl", junit4_tests = "junit_tests")
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

def gos_junit4_test(name, srcs, deps = [], disable_pmd = False, disable_checkstyle = False, **kwargs):
    junit4_tests(
        name = name,
        srcs = srcs,
        javacopts = get_default_javac_opts(),
        deps = deps + ["@maven//:junit_junit"],
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
