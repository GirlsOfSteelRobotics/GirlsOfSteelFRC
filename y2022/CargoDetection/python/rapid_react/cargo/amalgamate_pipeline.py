import os


def strip_imports_and_append(of, filename):
    print(f"Loading {filename}")
    with open(filename, "r") as f:
        for line in f.readlines():
            if line.startswith("import"):
                continue
            if line.startswith("from"):
                continue

            of.write(line)


def amalgamate_pipeline():
    this_dir = r"C:\Users\PJ\Documents\GitHub\gos_monorepo\y2022\CargoDetection\python"
    pipelines_dir = os.path.join(this_dir, "lib", "pipelines")
    game_dir = os.path.join(this_dir, "rapid_react", "cargo")

    with open(os.path.join(this_dir, "amalgum_pipeline.py"), "w") as f:
        f.write(
            """
from typing import Tuple, List
import cv2
import numpy as np
import time
"""
        )
        strip_imports_and_append(f, os.path.join(pipelines_dir, "pipeline_params.py"))
        strip_imports_and_append(f, os.path.join(pipelines_dir, "crop_image_pipeline.py"))
        strip_imports_and_append(f, os.path.join(pipelines_dir, "find_circles_pipeline.py"))
        strip_imports_and_append(f, os.path.join(pipelines_dir, "find_contours_pipeline.py"))
        strip_imports_and_append(f, os.path.join(pipelines_dir, "hsv_threshold_pipeline.py"))
        strip_imports_and_append(f, os.path.join(game_dir, "cargo_pipeline_params.py"))
        strip_imports_and_append(f, os.path.join(game_dir, "cargo_pipeline.py"))
        strip_imports_and_append(f, os.path.join(game_dir, "base_limelight_pipeline.py"))
    pass


if __name__ == "__main__":
    amalgamate_pipeline()
