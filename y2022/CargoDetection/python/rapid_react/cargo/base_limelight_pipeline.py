from rapid_react.cargo.cargo_pipeline import CargoPipeline
from rapid_react.cargo.cargo_pipeline_params import BLUE_PARAMS, RED_PARAMS

RED_NUM = 0
BLUE_NUM = 1

__RED_PIPELINE = CargoPipeline(RED_PARAMS)
__BLUE_PIPELINE = CargoPipeline(BLUE_PARAMS)


def get_active_pipeline(active_pipeline):
    if active_pipeline == RED_NUM:
        return __RED_PIPELINE
    elif active_pipeline == BLUE_NUM:
        return __BLUE_PIPELINE
    else:
        raise Exception(f"Unknown pipeline {active_pipeline}")


def runPipeline(image, ll_robot):

    ll_python = []

    if len(ll_robot) >= 1:
        active_threshold_num = int(ll_robot[0])
    else:
        active_threshold_num = BLUE_NUM
        print(f"Could not determine pipeline from robot table {ll_robot}")

    pipeline = get_active_pipeline(active_threshold_num)
    largest_contour = pipeline.run(image)

    pipeline.print_stopwatch()

    # return the largest contour for the LL crosshair, the modified image, and custom robot data
    return largest_contour, image, ll_python
