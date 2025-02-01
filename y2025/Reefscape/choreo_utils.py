import subprocess


def run_choreo_cli(pathname):
    cmd = []
    cmd.append("bazel")
    cmd.append("run")
    cmd.append("@bzlmodrio-choreolib//libraries/tools/choreo-cli:choreo-cli")
    cmd.append("--")
    cmd.append("--chor")
    cmd.append("y2025/Reefscape/src/main/deploy/choreo/ChoreoAutos.chor")
    cmd.append("--generate")
    cmd.append("--trajectory")
    cmd.append(pathname)
    subprocess.check_call(cmd)
