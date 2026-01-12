load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("//build_scripts/bazel/deps:download_external_archives.bzl", "download_external_archives")
load("//build_scripts/bazel/deps:versions.bzl", "CHOREOLIB_VERSION", "NAVX_FRC_VERSION", "PATHPLANNERLIB_VERSION", "PHOENIX6_VERSION", "PHOENIX_VERSION", "PHOTONLIB_JSON_1_0_VERSION", "REVLIB_VERSION", "SNOBOTSIM_VERSION", "WPILIB_VERSION")

download_external_archives()

load("@bzlmodrio//private/non_bzlmod:download_dependencies.bzl", "download_dependencies")

download_dependencies(
    allwpilib_version = WPILIB_VERSION,
    choreolib_version = CHOREOLIB_VERSION,
    pathplannerlib_version = PATHPLANNERLIB_VERSION,
    phoenix6_version = PHOENIX6_VERSION,
    phoenix_version = PHOENIX_VERSION,
    photonlib_version = PHOTONLIB_JSON_1_0_VERSION,
    revlib_version = REVLIB_VERSION,
    rules_pmd_version = "6.43.0",
    rules_wpi_styleguide_version = "1.0.0",
    # apriltaglib_version = None,
    # imgui_version = None,
    # libssh_version = None,
    studica_version = NAVX_FRC_VERSION,

    # Always the default version of these libraries
    # rules_spotless_version = None,
    # rules_wpi_styleguide_version = None,
    # ni_version = None,
    # opencv_version = None,
    # rules_bazelrio_version = None,
    # rules_toolchains_version = None,
    # rules_checkstyle_version = None,
)

########################
# Setup Dependencies
########################
load("@bazel_features//:deps.bzl", "bazel_features_deps")

bazel_features_deps()

load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

load("@rules_java//java:rules_java_deps.bzl", "rules_java_dependencies")

rules_java_dependencies()

load("@rules_java//java:repositories.bzl", "rules_java_toolchains")

rules_java_toolchains()

load("@rules_python//python:repositories.bzl", "py_repositories", "python_register_toolchains")

py_repositories()

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_shell//shell:repositories.bzl", "rules_shell_dependencies", "rules_shell_toolchains")

rules_shell_dependencies()

rules_shell_toolchains()

load("@bzlmodrio//private/non_bzlmod:setup_dependencies.bzl", "get_java_dependencies", "setup_dependencies")

setup_dependencies()

########################

load("@rules_jvm_external//:defs.bzl", "maven_install")

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
    maven_install_json = "//build_scripts/bazel/deps:maven_install.json",
    # version_conflict_policy = "pinned",
    repositories = maven_repositories + [
        "https://raw.githubusercontent.com/snobotsim/maven_repo/master/release",
        "https://raw.githubusercontent.com/snobotsim/maven_repo/master/development",
    ],
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

python_register_toolchains(
    name = "python_3_10",
    ignore_root_user_error = True,
    python_version = "3.10",
)

load("@rules_python//python:pip.bzl", "pip_parse")

pip_parse(
    name = "gos_pip_deps",
    requirements_lock = "//build_scripts/bazel/deps:requirements_lock.txt",
    requirements_windows = "//build_scripts/bazel/deps:requirements_windows.txt",
)

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()

load("@gos_pip_deps//:requirements.bzl", "install_deps")

install_deps()

#####################
# Styleguide
#####################
http_archive(
    name = "rules_wpi_styleguide",
    sha256 = "6f8b428bf07319c26ea1187d868fc2538c29fafccad7717e0043411b0cd6466f",
    url = "https://github.com/bzlmodRio/rules_wpi_styleguide/releases/download/2025.06.22/rules_wpi_styleguide-2025.06.22.tar.gz",
)

load("@rules_checkstyle//dependencies:load_dependencies.bzl", "load_checkstyle_dependencies")
load("@rules_pmd//dependencies:load_dependencies.bzl", "load_pmd_dependencies")
load("@rules_spotless//dependencies:load_dependencies.bzl", "load_spotless_dependencies")
load("@rules_wpiformat//dependencies:load_dependencies.bzl", "load_wpiformat_dependencies")

load_checkstyle_dependencies()

load_pmd_dependencies()

load_wpiformat_dependencies()

load_spotless_dependencies()

load("@rules_checkstyle_dependencies//:defs.bzl", checkstyle_pinned_maven_install = "pinned_maven_install")
load("@rules_pmd_dependencies//:defs.bzl", pmd_pinned_maven_install = "pinned_maven_install")
load("@rules_spotless_dependencies//:defs.bzl", spotless_pinned_maven_install = "pinned_maven_install")
load("@rules_wpiformat_pip//:requirements.bzl", "install_deps")

install_deps()

pmd_pinned_maven_install()

checkstyle_pinned_maven_install()

spotless_pinned_maven_install()
#####################

http_archive(
    name = "rules_bzlmodrio_jdk",
    integrity = "sha256-CuS7x85kbqJ7k3gC8Bae5BWgCd/SHRogBXd8jJ/d7+k=",
    strip_prefix = "rules_bzlmodrio_jdk-002eda2bf3dcb98c68aa6ab7b6d8c30112b7892e",
    urls = [
        "https://github.com/bzlmodRio/rules_bzlmodrio_jdk/archive/002eda2bf3dcb98c68aa6ab7b6d8c30112b7892e.zip",
    ],
)

load("@rules_bzlmodrio_jdk//:maven_deps.bzl", "setup_legacy_setup_jdk_dependencies")

setup_legacy_setup_jdk_dependencies()
