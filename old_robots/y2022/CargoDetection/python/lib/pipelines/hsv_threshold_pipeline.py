from lib.pipelines.pipeline_params import HsvThresholdParams
import cv2


def run_hsv_threshold(image, hsv_params: HsvThresholdParams):
    img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

    if hsv_params.hue_inverted:
        bottom_min = (0, hsv_params.s[0], hsv_params.v[0])
        bottom_max = (hsv_params.h[0], hsv_params.s[1], hsv_params.v[1])

        top_min = (hsv_params.h[1], hsv_params.s[0], hsv_params.v[0])
        top_max = (180, hsv_params.s[1], hsv_params.v[1])

        img_threshold_bottom = cv2.inRange(img_hsv, bottom_min, bottom_max)
        img_threshold_top = cv2.inRange(img_hsv, top_min, top_max)
        img_threshold = img_threshold_bottom | img_threshold_top
    else:
        min_thresh, max_thresh = hsv_params.to_min_max_pair()
        img_threshold = cv2.inRange(img_hsv, min_thresh, max_thresh)

    return img_threshold
