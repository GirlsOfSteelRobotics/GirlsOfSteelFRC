"""
"""

load("@bazel_tools//tools/build_defs/repo:jvm.bzl", "jvm_maven_import_external")
load("@bazelrio//:deps.bzl", "setup_bazelrio_dependencies")
load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_pmd//pmd:toolchains.bzl", "rules_pmd_toolchains")
load("@rules_python//python:pip.bzl", "pip_install", "pip_parse")
load("//build_scripts/bazel/deps:versions.bzl", "PATHPLANNERLIB_VERSION", "PATHPLANNERLIB_VERSION_SHA", "PHOTONLIB_JSON_1_0_VERSION", "PHOTONLIB_JSON_1_0_VERSION_SHA", "SNOBOTSIM_SHA", "SNOBOTSIM_VERSION")

def activate_dependencies():
    """
    Final step of dependencies initialization. Does the various installation steps (pip_install, maven_intstall, etc)
    """
    PMD_VERSION = "6.39.0"
    rules_pmd_toolchains(pmd_version = PMD_VERSION)

    # To regenerate lock file:
    #
    # .\venv\Scripts\activate
    # pip uninstall -r requirements.txt
    # pip install -r requirements.txt
    # pip freeze >> build_scripts/bazel/deps/requirements_lock.txt
    pip_parse(
        name = "gos_pip_deps",
        requirements_lock = "//build_scripts/bazel/deps:requirements_lock.txt",
    )
    pip_install(
        name = "__bazelrio_deploy_pip_deps",
        requirements = "@bazelrio//scripts/deploy:requirements.txt",
    )

    setup_bazelrio_dependencies()

    jvm_maven_import_external(
        name = "snobot_sim",
        artifact = "org.snobotv2:snobot_sim_java:{v}".format(v = SNOBOTSIM_VERSION),
        artifact_sha256 = SNOBOTSIM_SHA,
        server_urls = ["https://raw.githubusercontent.com/snobotsim/maven_repo/master/release"],
    )

    jvm_maven_import_external(
        name = "photonvision",
        artifact = "org.photonvision:PhotonLib-java:{v}".format(v = PHOTONLIB_JSON_1_0_VERSION),
        artifact_sha256 = PHOTONLIB_JSON_1_0_VERSION_SHA,
        server_urls = ["https://maven.photonvision.org/repository/internal", "https://maven.photonvision.org/repository/snapshots"],
    )

    jvm_maven_import_external(
        name = "pathplanner",
        artifact = "com.pathplanner.lib:PathplannerLib-java:{v}".format(v = PATHPLANNERLIB_VERSION),
        artifact_sha256 = PATHPLANNERLIB_VERSION_SHA,
        server_urls = ["https://3015rangerrobotics.github.io/pathplannerlib/repo"],
    )

    maven_install(
        name = "maven",
        artifacts = [
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
            "org.ejml:ejml-simple:0.38",
        ],
        repositories = ["https://repo1.maven.org/maven2", "http://raw.githubusercontent.com/snobotsim/maven_repo/master/development"],
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
