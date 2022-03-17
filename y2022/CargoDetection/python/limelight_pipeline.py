import cv2
import numpy as np
import time


RED_NUM = 0
BLUE_NUM = 1

PRINT_CTR = 0


class BlueParams:
    def __init__(self):

        #blue
        self.hsv = ((82, 104, 64), (112, 255, 255))
        self.contour_filtering = {'min_area': 5, 'max_area': 10000, 'min_aspect_ratio': 0.25, 'max_aspect_ratio': 2.5}
        self.circle = {'min_radius': 5, 'max_radius': 500, 'min_dist': 48, 'max_canny_thresh': 35, 'accuracy': 10, 'matching_threshold': 17}
        self.crop_filter = [{'min_y': 160, 'max_y': 320}]

    def print_settings(self):
        print("Pipeline Settings:\n")
        print(f"        self.blue_hsv = {self.blue_hsv}")
        print(f"        self.red_hsv = {self.red_hsv}")
        print(f"        self.contour_filtering = {self.contour_filtering}")
        print(f"        self.circle = {self.circle}")
        print(f"        self.crop_filter = {self.crop_filter}")


class RedParams:

    def __init__(self):
        #red
        self.hsv = ((17, 94, 236), (172, 245, 255))
        self.contour_filtering = {'min_area': 5, 'max_area': 10000, 'min_aspect_ratio': 0.25, 'max_aspect_ratio': 2.5}
        self.circle = {'min_radius': 5, 'max_radius': 500, 'min_dist': 26, 'max_canny_thresh': 29, 'accuracy': 10, 'matching_threshold': 19}
        self.crop_filter = [{'min_y': 160, 'max_y': 320}]



__RED_PARAMS = RedParams()
__BLUE_PARAMS = BlueParams()
__THRESHOLD_IMAGE = None


def get_threshold_image():
    return __THRESHOLD_IMAGE


def set_params(active_pipeline, params):
    params.print_settings()
    if active_pipeline == RED_NUM:
        global __RED_PARAMS
        __RED_PARAMS = params
    elif active_pipeline == BLUE_NUM:
        global __BLUE_PARAMS
        __BLUE_PARAMS = params
    else:
        raise Exception("AHHH")


def get_params(active_pipeline):
    if active_pipeline == RED_NUM:
        return __RED_PARAMS
    elif active_pipeline == BLUE_NUM:
        return __BLUE_PARAMS
    else:
        raise Exception("AHHH")


class FilteringResults:
    def __init__(self):
        self.contours_failed_contour_filtering = []
        self.contours_failed_circle_matching = []
        self.unmatched_circles = []

        self.surviving_contours = []
        self.surviving_circles = []


def get_matching_contours(params, contours, circles, filtering_results):
    threshold = params.circle["matching_threshold"]

    unmatched_contours = contours

    if circles is None or len(circles) == 0:
        filtering_results.unmatched_circles = []
        filtering_results.contours_failed_circle_matching = contours
        return [], []

    num_circles = len(circles[0])
    num_contours = len(contours)
    max_iterations = num_circles * num_contours
    if max_iterations > 250:
        print(f"Too many matches {max_iterations}: circles: {num_circles}, contors: {num_contours}")
        filtering_results.unmatched_circles = np.uint16(np.around([c for c in circles[0, :]]))
        filtering_results.contours_failed_circle_matching = contours
        return [], []

    matched_contours = []
    good_circles = []
    bad_circles = []

    for c in circles[0, :]:
        x_center = c[0]
        y_center = c[1]

        for contour in unmatched_contours:
            mu = cv2.moments(contour)
            if mu["m00"] == 0:
                continue

            x_diff = abs(x_center - mu["m10"] / mu["m00"])
            y_diff = abs(y_center - mu["m01"] / mu["m00"])
            if x_diff < threshold and y_diff < threshold:
                matched_contours.append(contour)
                good_circles.append(c)

                unmatched_contours = tuple(x for x in unmatched_contours if not np.array_equal(x, contour))
                break
        else:
            bad_circles.append(c)

    filtering_results.unmatched_circles = np.uint16(np.around(bad_circles))
    filtering_results.contours_failed_circle_matching = unmatched_contours

    good_circles = np.uint16(np.around(good_circles))

    return matched_contours, good_circles


def run_hsv_threshold(params, active_threshold_num, image):
    # convert the input image to the HSV color space
    img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

    # convert the hsv to a binary image by removing any pixels
    # that do not fall within the following HSV Min/Max values
    if active_threshold_num == RED_NUM:
        hsv_min, hsv_max = params.hsv
        lower_threshold = (0, hsv_min[1], hsv_min[2]), (hsv_min[0], hsv_max[1], hsv_max[2])
        upper_threshold = (hsv_max[0], hsv_min[1], hsv_min[2]), (255, hsv_max[1], hsv_max[2])
        img_threshold_lower = cv2.inRange(img_hsv, *lower_threshold)
        img_threshold_upper = cv2.inRange(img_hsv, *upper_threshold)
        img_threshold = img_threshold_lower | img_threshold_upper
    elif active_threshold_num == BLUE_NUM:
        hsv_min, hsv_max = params.hsv
        img_threshold = cv2.inRange(img_hsv, hsv_min, hsv_max)
    else:
        raise Exception(f"Unknown threshold number {active_threshold_num}")


    #print(image.shape)
    for crop_filter in params.crop_filter:
        cv2.rectangle(img_threshold, (0, crop_filter["min_y"]), (image.shape[1], crop_filter["max_y"]), 0, -1)

    global __THRESHOLD_IMAGE
    __THRESHOLD_IMAGE = cv2.bitwise_and(image, image, mask=img_threshold)

    return img_threshold


def find_and_filter_contours(params, img_threshold, filter_results):
    filters = params.contour_filtering

    contours, _ = cv2.findContours(img_threshold,
                                   cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    good_contours = []

    for contour in contours:
        rect = cv2.boundingRect(contour)
        aspect_ratio = rect[2] / rect[3]
        aspect_ratio_good = filters["min_aspect_ratio"] <= aspect_ratio <= filters["max_aspect_ratio"]

        area = cv2.contourArea(contour)
        good_area = filters["min_area"] <= area <= filters["max_area"]
        if good_area and aspect_ratio_good:
            good_contours.append(contour)
        else:
            filter_results.contours_failed_contour_filtering.append(contour)

    return good_contours


def annotate_image(image, filtering_results, best_contour):

    BAD_CONTOUR_COLOR = (0, 0, 0)
    UNMATCHED_CONTOUR_COLOR = (252, 0, 249)
    UNMATCHED_CIRCLES_COLOR = (125, 1, 123)

    MATCHED_CONTOURS_COLOR = (72, 250, 43)
    MATCHED_CIRCLES_COLOR = (71, 182, 54)
    BEST_CONTOUR_COLOR = (0,255,255)

    def annotate_circles(circles, color):
        for i in circles:
            cv2.circle(image, (i[0], i[1]), i[2], color, 2)

    def annotate_contours(contours, color):
        cv2.drawContours(image, contours, -1, color, 2)

    annotate_circles(filtering_results.unmatched_circles, UNMATCHED_CIRCLES_COLOR)
    annotate_contours(filtering_results.contours_failed_contour_filtering, BAD_CONTOUR_COLOR)
    annotate_contours(filtering_results.contours_failed_circle_matching, UNMATCHED_CONTOUR_COLOR)

    annotate_circles(filtering_results.surviving_circles, MATCHED_CIRCLES_COLOR)
    annotate_contours(filtering_results.surviving_contours, MATCHED_CONTOURS_COLOR)

    if len(filtering_results.surviving_contours) > 0:
        x, y, w, h = cv2.boundingRect(best_contour)
        cv2.rectangle(image, (x, y), (x + w, y + h), BEST_CONTOUR_COLOR, 2)


# runPipeline() is called every frame by Limelight's backend.
def runPipeline(image, llrobot):
    start_time = time.time()

    if len(llrobot) == 1:
        active_threshold_num, = llrobot
    else:
        active_threshold_num = Params.BLUE_NUM

    if active_threshold_num == RED_NUM:
        params = __RED_PARAMS
    elif active_threshold_num == BLUE_NUM:
        params =  __BLUE_PARAMS
    else:
        raise Exception("AHH")

    img_threshold = run_hsv_threshold(params, active_threshold_num, image)
    post_hsv = time.time()

    filter_results = FilteringResults()

    # find contours in the new binary image
    contours = find_and_filter_contours(params, img_threshold, filter_results)
    post_contours = time.time()

    circles = cv2.HoughCircles(img_threshold, cv2.HOUGH_GRADIENT, 1,
                               minDist=params.circle["min_dist"],
                               param1=params.circle["max_canny_thresh"],
                               param2=params.circle["accuracy"],
                               minRadius=params.circle["min_radius"],
                               maxRadius=params.circle["max_radius"])
    post_circles = time.time()

    largest_contour = np.array([[]])

    filter_results.surviving_contours, filter_results.surviving_circles = get_matching_contours(params,
                                                                                                contours, circles, filter_results)
    post_contour_matching = time.time()

    if len(filter_results.surviving_contours) > 0:
        largest_contour = max(filter_results.surviving_contours, key=cv2.contourArea)

    annotate_image(image, filter_results, largest_contour)
    post_annotation = time.time()

    hsv_time = post_hsv - start_time
    contours_time = post_contours - post_hsv
    circles_time = post_circles - post_contours
    contour_matching_time = post_contour_matching - post_circles
    annotation_time = post_annotation - post_contour_matching
    total_time = post_annotation - start_time

    global PRINT_CTR
    if PRINT_CTR % 100 == 0:
        print(f"Total: {total_time} - Timing: HSV={hsv_time}, Contour={contours_time}, Circles={circles_time}, Contour Matching={contour_matching_time}, Annotation={annotation_time}")
    PRINT_CTR += 1

    llpython = []

    # return the largest contour for the LL crosshair, the modified image, and custom robot data
    return largest_contour, image, llpython
