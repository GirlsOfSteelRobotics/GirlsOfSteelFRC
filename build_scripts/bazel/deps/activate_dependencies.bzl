"""
"""

load("@bzlmodrio//private/non_bzlmod:setup_dependencies.bzl", "get_java_dependencies", "setup_dependencies")
load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_python//python:pip.bzl", "pip_parse")
load("@rules_python//python:repositories.bzl", "python_register_toolchains")
load("//build_scripts/bazel/deps:versions.bzl", "SNOBOTSIM_VERSION")

def activate_dependencies():
    """
    Final step of dependencies initialization. Does the various installation steps (pip_install, maven_intstall, etc)
    """

    python_register_toolchains(
        name = "python_3_10",
        ignore_root_user_error = True,
        python_version = "3.10",
    )

    pip_parse(
        name = "gos_pip_deps",
        requirements_lock = "//build_scripts/bazel/deps:requirements_lock.txt",
        requirements_windows = "//build_scripts/bazel/deps:requirements_windows.txt",
    )

    setup_dependencies()

    maven_artifacts, maven_repositories = get_java_dependencies()
    maven_install(
        name = "maven",
        artifacts = maven_artifacts + [
            "com.google.guava:guava:21.0",
            "org.fxmisc.easybind:easybind:1.0.3",
            "org.junit.jupiter:junit-jupiter-api:5.8.2",
            "org.junit.jupiter:junit-jupiter-params:5.8.2",
            "org.junit.jupiter:junit-jupiter-engine:5.8.2",
            "org.junit.platform:junit-platform-commons:1.6.1",
            "org.junit.platform:junit-platform-console:1.6.1",
            "org.junit.platform:junit-platform-engine:1.6.1",
            "org.junit.platform:junit-platform-launcher:1.6.1",
            "org.junit.platform:junit-platform-suite-api:1.6.1",
            "org.snobotv2:snobot_sim_java_base:{v}".format(v = SNOBOTSIM_VERSION),
            "org.snobotv2:snobot_sim_java_navx:{v}".format(v = SNOBOTSIM_VERSION),
            "org.snobotv2:snobot_sim_java_phoenix5:{v}".format(v = SNOBOTSIM_VERSION),
            "org.snobotv2:snobot_sim_java_phoenix6:{v}".format(v = SNOBOTSIM_VERSION),
            "org.snobotv2:snobot_sim_java_revlib:{v}".format(v = SNOBOTSIM_VERSION),
            "org.snobotv2:snobot_swerve_sim:{v}".format(v = SNOBOTSIM_VERSION),
        ],
        repositories = maven_repositories + ["https://raw.githubusercontent.com/snobotsim/maven_repo/master/release", "https://raw.githubusercontent.com/snobotsim/maven_repo/master/development"],
        maven_install_json = "//build_scripts/bazel/deps:maven_install.json",
    )

    # Separate this because the maven_install_json doesn't download other OS native files
    maven_install(
        name = "maven_javafx",
        artifacts = [
            "org.openjfx:javafx-base:11",
            "org.openjfx:javafx-controls:11",
            "org.openjfx:javafx-fxml:11",
            "org.openjfx:javafx-graphics:11",
            "org.openjfx:javafx-swing:11",
            "org.openjfx:javafx-media:11",
            "org.openjfx:javafx-web:11",
        ],
        repositories = [
            "https://repo1.maven.org/maven2",
            "https://repo.maven.apache.org/maven2/",
        ],
    )
