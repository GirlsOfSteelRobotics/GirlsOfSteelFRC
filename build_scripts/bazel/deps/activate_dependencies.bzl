load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_pmd//pmd:toolchains.bzl", "rules_pmd_toolchains")
load("@rules_python//python:pip.bzl", "pip_install")
load("@bazelrio//:deps.bzl", "setup_bazelrio_dependencies")
load("@bazel_tools//tools/build_defs/repo:jvm.bzl", "jvm_maven_import_external")

def activate_dependencies():
    PMD_VERSION = "6.39.0"
    rules_pmd_toolchains(pmd_version = PMD_VERSION)

    pip_install(
        name = "gos_pip_deps",
        requirements = "//:requirements.txt",
    )
    pip_install(
        name = "__bazelrio_deploy_pip_deps",
        requirements = "@bazelrio//scripts/deploy:requirements.txt",
    )

    setup_bazelrio_dependencies()

    jvm_maven_import_external(
        name = "snobot_sim",
        artifact = "org.snobotv2:snobot_sim_java:2022.2.2.0",
        artifact_sha256 = "656a265bd7cc7eb3035341a7880ce24940b4afcec184123247774c3511872e9b",
        server_urls = ["https://raw.githubusercontent.com/snobotsim/maven_repo/master/release"],
    )

    maven_install(
        name = "maven",
        artifacts = [
            "com.google.guava:guava:21.0",
            "org.fxmisc.easybind:easybind:1.0.3",
            "junit:junit:4.12",
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
