import subprocess
from typing import Union, List


def run_choreo_cli(pathnames: Union[List, str]):
    cmd = []
    cmd.append("bazel")
    cmd.append("run")
    cmd.append("@bzlmodrio-choreolib//libraries/tools/choreo-cli:choreo-cli")
    cmd.append("--")
    cmd.append("--chor")
    cmd.append("y2025/Reefscape/src/main/deploy/choreo/ChoreoAutos.chor")
    cmd.append("--generate")
    cmd.append("--trajectory")
    if type(pathnames) == str:
        cmd.append(pathnames)
    else:
        cmd.append(",".join(pathnames))
    subprocess.check_call(cmd)
