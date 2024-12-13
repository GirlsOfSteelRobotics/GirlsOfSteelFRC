load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
load("//build_scripts/bazel/deps:download_external_archives.bzl", "download_external_archives")

download_external_archives()

load("//build_scripts/bazel/deps:setup_dependencies.bzl", "setup_dependencies")

setup_dependencies()

load("//build_scripts/bazel/deps:activate_dependencies.bzl", "activate_dependencies")

activate_dependencies()

load("@rules_python//python:repositories.bzl", "py_repositories", "python_register_toolchains")

python_register_toolchains(
    name = "python_3_10",
    ignore_root_user_error = True,
    python_version = "3.10",
)

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()

load("@gos_pip_deps//:requirements.bzl", "install_deps")

install_deps()

load("@rules_wpi_styleguide//dependencies:load_rule_dependencies.bzl", "load_styleguide_rule_dependencies")

load_styleguide_rule_dependencies()

load("@rules_wpi_styleguide//dependencies:load_dependencies.bzl", "load_styleguide_dependencies")

load_styleguide_dependencies()

load("@rules_wpi_styleguide//dependencies:load_transitive_dependencies.bzl", "load_styleguide_transitive_dependencies")

load_styleguide_transitive_dependencies()

load("@rules_wpi_styleguide//dependencies:setup_styleguide.bzl", "setup_styleguide")

setup_styleguide()

load("@rules_wpi_styleguide//dependencies:load_pins.bzl", "load_styleguide_pins")

load_styleguide_pins()

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
