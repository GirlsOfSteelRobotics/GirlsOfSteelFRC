"""
"""

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

def download_external_archives():
    """
    Downloads the necessary rules from external repositories
    """

    # Download Extra java rules
    RULES_JVM_EXTERNAL_TAG = "4.2"
    RULES_JVM_EXTERNAL_SHA = "cd1a77b7b02e8e008439ca76fd34f5b07aecb8c752961f9640dea15e9e5ba1ca"
    http_archive(
        name = "rules_jvm_external",
        sha256 = RULES_JVM_EXTERNAL_SHA,
        strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
        url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
    )

    # Download BazelRio <3
    BAZELRIO_COMMITISH = "7bc23edff1834f5411525d6285c3a465656dc879"
    BAZELRIO_SHA256 = "72de37c55527b9a0deaf104d8df7e5d1ad59746518c1fc3414769009e33c37aa"
    http_archive(
        name = "bazelrio",
        url = "https://github.com/pjreiniger/bazelRio/archive/{}.tar.gz".format(BAZELRIO_COMMITISH),
        sha256 = BAZELRIO_SHA256,
        strip_prefix = "bazelrio-{}/bazelrio".format(BAZELRIO_COMMITISH),
    )

    # Download Setup python
    http_archive(
        name = "rules_python",
        sha256 = "a30abdfc7126d497a7698c29c46ea9901c6392d6ed315171a6df5ce433aa4502",
        strip_prefix = "rules_python-0.6.0",
        url = "https://github.com/bazelbuild/rules_python/archive/0.6.0.tar.gz",
    )

    # Junit helper
    GERRIT_JUNIT_COMMITISH = "608fc3c457cf239bfad615efaeb014b504ca6c04"
    http_archive(
        name = "rule_junit",
        url = "https://github.com/GerritCodeReview/bazlets/archive/{}.tar.gz".format(GERRIT_JUNIT_COMMITISH),
        sha256 = "12bd3d30796335984cf317550bd22a9fa3ffb35b00a5ef56074fb2b2b16eec88",
        strip_prefix = "bazlets-{}".format(GERRIT_JUNIT_COMMITISH),
    )

    # Download PMD
    rules_pmd_version = "4ee896258c9bedd6f935d5bf1a5c974feee12e0e"
    rules_pmd_sha = "53db0850cdded5a43704c992f6a3efdf4a56d95107d8152b39fa84efb840d886"
    http_archive(
        name = "rules_pmd",
        sha256 = rules_pmd_sha,
        strip_prefix = "bazel_rules_pmd-{v}".format(v = rules_pmd_version),
        url = "https://github.com/buildfoundation/bazel_rules_pmd/archive/{v}.tar.gz".format(v = rules_pmd_version),
    )
