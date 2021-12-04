load("//build_scripts/bazel:java_rules.bzl", "gos_java_library")

def _generate_version_file_impl(ctx):
    out = ctx.actions.declare_file(ctx.attr.output_file)
    ctx.actions.expand_template(
        output = out,
        template = ctx.file.template,
        substitutions = {
            "${plugin_version}": "TODO - Built with bazel",
            "${package_name}": ctx.attr.package,
        },
    )
    return [DefaultInfo(files = depset([out]))]

generate_version_file = rule(
    implementation = _generate_version_file_impl,
    attrs = {
        "output_file": attr.string(mandatory = True),
        "package": attr.string(mandatory = True),
        "template": attr.label(
            allow_single_file = True,
            mandatory = True,
        ),
    },
)

def shuffleboard_widget(name, package, srcs = None, **kwargs):
    generate_version_file(
        name = "generate-version",
        output_file = "PluginVersion.java",
        package = package,
        template = "//build_scripts/bazel/shuffleboard:version_template",
    )

    if not srcs:
        srcs = native.glob(["src/main/java/**/*.java", "src/dashboard_gen/java/**/*.java"]) + [":generate-version"]

    gos_java_library(
        name = name,
        srcs = srcs,
        resources = native.glob(["src/main/resources/**"]),
        deps = [
            "@maven//:org_fxmisc_easybind_easybind",
            "@maven_javafx//:org_openjfx_javafx_base",
            "@maven_javafx//:org_openjfx_javafx_controls",
            "@maven_javafx//:org_openjfx_javafx_fxml",
            "@maven_javafx//:org_openjfx_javafx_graphics",
            "@maven//:com_google_guava_guava",
            "@bazelrio//libraries/java/wpilib/shuffleboard",
        ] + select({
            "@bazel_tools//src/conditions:linux_x86_64": [
                "@maven_javafx//:org_openjfx_javafx_controls_linux",
                "@maven_javafx//:org_openjfx_javafx_graphics_linux",
                "@maven_javafx//:org_openjfx_javafx_base_linux",
                "@maven_javafx//:org_openjfx_javafx_fxml_linux",
            ],
            "@bazel_tools//src/conditions:darwin": [
                "@maven_javafx//:org_openjfx_javafx_controls_mac",
                "@maven_javafx//:org_openjfx_javafx_graphics_mac",
                "@maven_javafx//:org_openjfx_javafx_base_mac",
                "@maven_javafx//:org_openjfx_javafx_fxml_mac",
            ],
            "@bazel_tools//src/conditions:windows": [
                "@maven_javafx//:org_openjfx_javafx_controls_win",
                "@maven_javafx//:org_openjfx_javafx_graphics_win",
                "@maven_javafx//:org_openjfx_javafx_base_win",
                "@maven_javafx//:org_openjfx_javafx_fxml_win",
            ],
            "//conditions:default": [],
        }),
        **kwargs
    )
