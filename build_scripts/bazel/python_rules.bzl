# TODO add linters, automated tests, etc

def gos_py_library(name, **kwargs):
    native.py_library(
        name = name,
        **kwargs
    )

def gos_py_binary(name, **kwargs):
    native.py_binary(
        name = name,
        **kwargs
    )

def gos_py_test(name, **kwargs):
    native.py_test(
        name = name,
        **kwargs
    )
