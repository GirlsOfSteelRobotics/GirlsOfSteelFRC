"""
"""

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

def download_external_archives():
    """
    Downloads the necessary rules from external repositories
    """

    # Download Extra java rules
    http_archive(
        name = "rules_jvm_external",
        sha256 = "d31e369b854322ca5098ea12c69d7175ded971435e55c18dd9dd5f29cc5249ac",
        strip_prefix = "rules_jvm_external-5.3",
        url = "https://github.com/bazelbuild/rules_jvm_external/releases/download/5.3/rules_jvm_external-5.3.tar.gz",
    )

    http_archive(
        name = "bzlmodrio-studica",
        sha256 = "530fb5c88def87fe86db491c8e61fe73fb5435330f831d39723b379817777ad6",
        strip_prefix = "bzlmodRio-studica-b190c1fcfaf6e2fd242e934c5109a1073ff84082",
        urls = ["https://github.com/bzlmodrio/bzlmodRio-studica/archive/b190c1fcfaf6e2fd242e934c5109a1073ff84082.tar.gz"],
    )

    # Download bzlmodrio <3
    BZLMODRIO_COMMITISH = "e4abbfba36d1ca3bb8af50a68296388a848999ba"
    BZLMODRIO_SHA256 = "6f9ceb5da448ca8dd676b30bfdc6cb078f149001906f6bb0dc57d74dbc0bf6c6"
    http_archive(
        name = "bzlmodrio",
        url = "https://github.com/bzlmodRio/bzlmodRio/archive/{}.tar.gz".format(BZLMODRIO_COMMITISH),
        sha256 = BZLMODRIO_SHA256,
        strip_prefix = "bzlmodRio-{}".format(BZLMODRIO_COMMITISH),
    )

    # Download Setup python
    http_archive(
        name = "rules_python",
        sha256 = "3b8b4cdc991bc9def8833d118e4c850f1b7498b3d65d5698eea92c3528b8cf2c",
        strip_prefix = "rules_python-0.30.0",
        url = "https://github.com/bazelbuild/rules_python/releases/download/0.30.0/rules_python-0.30.0.tar.gz",
    )

    # Junit helper
    GERRIT_JUNIT_COMMITISH = "608fc3c457cf239bfad615efaeb014b504ca6c04"
    http_archive(
        name = "rule_junit",
        url = "https://github.com/GerritCodeReview/bazlets/archive/{}.tar.gz".format(GERRIT_JUNIT_COMMITISH),
        sha256 = "12bd3d30796335984cf317550bd22a9fa3ffb35b00a5ef56074fb2b2b16eec88",
        strip_prefix = "bazlets-{}".format(GERRIT_JUNIT_COMMITISH),
    )
