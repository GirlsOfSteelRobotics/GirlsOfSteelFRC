"""
"""

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

def download_external_archives():
    """
    Downloads the necessary rules from external repositories
    """

    # Download bzlmodrio <3
    BZLMODRIO_COMMITISH = "a25285f936e31e92cc81704a99861b93be66b8ba"
    BZLMODRIO_SHA256 = "a49a6bc95dd991642f0c084fa22d42e8f2dce980fabf77268a917b9ebe484f61"
    http_archive(
        name = "bzlmodrio",
        url = "https://github.com/bzlmodRio/bzlmodRio/archive/{}.tar.gz".format(BZLMODRIO_COMMITISH),
        sha256 = BZLMODRIO_SHA256,
        strip_prefix = "bzlmodRio-{}".format(BZLMODRIO_COMMITISH),
    )

    # Junit helper
    GERRIT_JUNIT_COMMITISH = "608fc3c457cf239bfad615efaeb014b504ca6c04"
    http_archive(
        name = "rule_junit",
        url = "https://github.com/GerritCodeReview/bazlets/archive/{}.tar.gz".format(GERRIT_JUNIT_COMMITISH),
        sha256 = "12bd3d30796335984cf317550bd22a9fa3ffb35b00a5ef56074fb2b2b16eec88",
        strip_prefix = "bazlets-{}".format(GERRIT_JUNIT_COMMITISH),
    )
