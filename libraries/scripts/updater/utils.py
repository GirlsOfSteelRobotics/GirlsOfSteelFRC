import os
import re

DEFAULT_DIR_BLACKLIST = [
    ".git",
    "bazel-bin",
    "bazel-out",
    "bazel-testlogs",
    "bazel-gos_monorepo",
    ".gradle",
    "gradle",
    ".idea",
    ".ijwb",
    ".github",
    "build",
    "venv",
]


def walk_with_blacklist(search_root, dir_blacklist=None):
    if dir_blacklist is None:
        dir_blacklist = DEFAULT_DIR_BLACKLIST

    for root, dirs, files in os.walk(search_root):
        dirs[:] = [d for d in dirs if d not in dir_blacklist]

        yield root, dirs, files


def walk_for_extension(search_root, extension, dir_blacklist=None):
    out_files = []
    for root, dirs, files in walk_with_blacklist(search_root, dir_blacklist=dir_blacklist):
        out_files.extend([os.path.join(root, f) for f in files if f.endswith(extension)])

    return out_files


def regex_replace_file(file, replacements):
    with open(file, "rb") as f:
        contents = f.read().decode("utf-8")
    for repl_from, repl_to in replacements:
        contents = re.sub(repl_from, repl_to, contents)

    with open(file, "wb") as f:
        f.write(contents.encode("utf-8"))
