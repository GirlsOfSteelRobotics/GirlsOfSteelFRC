"""
Girls of Steel python rules. Mostly just wrappers around the native rules with hooks into linting and auto-testing
"""

# TODO add linters, automated tests, etc

load("@rules_python//python:defs.bzl", "py_binary", "py_library", "py_test")

def gos_py_library(name, srcs = [], skip_include_test = False, **kwargs):
    py_library(
        name = name,
        srcs = srcs,
        **kwargs
    )

    if not skip_include_test:
        __create_include_test(name, srcs)

def gos_py_binary(name, srcs = [], skip_include_test = False, **kwargs):
    py_binary(
        name = name,
        srcs = srcs,
        **kwargs
    )

    if not skip_include_test:
        __create_include_test(name, srcs)

def gos_py_test(name, **kwargs):
    py_test(
        name = name,
        **kwargs
    )

def __create_include_test(lib, srcs):
    if not srcs:
        return

    test_contents = """
import unittest

class ImportTest(unittest.TestCase):

"""

    for src in srcs:
        if src.endswith(".py"):
            src = src[:-3]

        test_name = "{}_test".format(src)
        src_as_import = native.package_name().replace("/", ".") + "." + src

        test_contents += """

    def test_include_{test_name}(self):

        import {src_as_import}
""".format(test_name = test_name, src_as_import = src_as_import)

    test_file_base = "{}_import_test".format(lib)
    test_file_name = "{}.py".format(test_file_base)
    gen_name = "gen_{}".format(test_file_base)

    test_contents += """

if __name__ == "__main__":
    unittest.main() # run all tests
"""

    native.genrule(
        name = gen_name,
        outs = [test_file_name],
        cmd = "echo '{}' >> $@".format(test_contents),
    )
    py_test(
        name = test_file_base,
        srcs = [test_file_name],
        deps = [lib],
    )
