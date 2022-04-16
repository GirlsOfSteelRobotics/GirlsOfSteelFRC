import time
import numpy as np
import cv2

from lib.pipelines.crop_image_pipeline import crop_image
from lib.pipelines.erode_dialate_pipeline import dilate_image, erode_image
from lib.pipelines.find_circles_pipeline import find_filtered_hough_circles, find_valid_circles
from lib.pipelines.find_contours_pipeline import find_and_filter_contours
from lib.pipelines.hsv_threshold_pipeline import run_hsv_threshold
from rapid_react.cargo.cargo_pipeline_params import CargoPipelineParams


class CargoPipeline:
    def __init__(self, params: CargoPipelineParams):
        self.params = params

        self.contours_failed_contour_filtering = []
        self.contours_failed_circle_matching = []
        self.unmatched_circles = []

        self.surviving_contours = []
        self.surviving_circles = []

        self.best_contour = None
        self.threshold_image = None

        self.prev_time = time.time()
        self.processing_time = None
        self.process_start = self.prev_time
        self.stopwatch = {}

        self.process_ctr = 0

    def debug_results(self):
        def debug_contour(contour):
            rect = cv2.boundingRect(contour)
            aspect_ratio = rect[2] / rect[3]
            area = cv2.contourArea(contour)
            if area <= 1:
                return ""
            return f"    Rect: {rect}\tRatio: {aspect_ratio}\tArea: {area}\n"

        output = ""

        output += "Contours: Failed Filtering\n"
        for contour in self.contours_failed_contour_filtering:
            output += debug_contour(contour)

        output += "Contours: Failed Circle Matching\n"
        for contour in self.contours_failed_circle_matching:
            output += debug_contour(contour)

        output += "Contours: Passed!\n"
        for contour in self.surviving_contours:
            output += debug_contour(contour)

        print(output)

    def stopwatch_split(self, tag):
        t = time.time()
        diff = t - self.prev_time
        self.stopwatch[tag] = diff
        self.prev_time = t

    def reset(self):

        self.contours_failed_contour_filtering = []
        self.contours_failed_circle_matching = []
        self.unmatched_circles = []

        self.surviving_contours = []
        self.surviving_circles = []

        self.best_contour = None
        self.threshold_image = None

        self.prev_time = time.time()
        self.process_start = self.prev_time
        self.processing_time = None
        self.stopwatch = {}

    def annotate_image(self, image):

        BAD_CONTOUR_COLOR = (0, 0, 0)
        UNMATCHED_CONTOUR_COLOR = (252, 0, 249)
        UNMATCHED_CIRCLES_COLOR = (125, 1, 123)

        MATCHED_CONTOURS_COLOR = (72, 250, 43)
        MATCHED_CIRCLES_COLOR = (71, 182, 54)
        BEST_CONTOUR_COLOR = (0, 255, 255)

        def annotate_circles(circles, color):
            for i in circles:
                cv2.circle(image, (i[0], i[1]), i[2], color, 2)

        def annotate_contours(contours, color):
            cv2.drawContours(image, contours, -1, color, 2)

        annotate_circles(self.unmatched_circles, UNMATCHED_CIRCLES_COLOR)
        annotate_contours(self.contours_failed_contour_filtering, BAD_CONTOUR_COLOR)
        annotate_contours(self.contours_failed_circle_matching, UNMATCHED_CONTOUR_COLOR)

        annotate_circles(self.surviving_circles, MATCHED_CIRCLES_COLOR)
        annotate_contours(self.surviving_contours, MATCHED_CONTOURS_COLOR)

        if self.best_contour is not None:
            x, y, w, h = cv2.boundingRect(self.best_contour)
            cv2.rectangle(image, (x, y), (x + w, y + h), BEST_CONTOUR_COLOR, 2)

    def run(self, image):
        self.process_ctr += 1

        self.reset()

        img_threshold = run_hsv_threshold(image, self.params.hsv)
        self.stopwatch_split("HSV Threshold")

        img_threshold = crop_image(img_threshold, self.params.image_crop)
        self.stopwatch_split("Crop")

        good_contours, self.contours_failed_contour_filtering = find_and_filter_contours(img_threshold, self.params.contour_filtering)
        self.stopwatch_split("Contours")

        filtered_hough_circles, filtered_threshold_image = find_filtered_hough_circles(img_threshold, good_contours, self.params.circle_detection)
        self.stopwatch_split("Find Circles")

        self.surviving_contours, self.surviving_circles, self.contours_failed_circle_matching, self.unmatched_circles = find_valid_circles(good_contours, filtered_hough_circles, self.params.circle_detection)
        self.stopwatch_split("Match Circles")

        self.threshold_image = cv2.bitwise_and(image, image, mask=img_threshold)
        # self.threshold_image = cv2.bitwise_and(image, image, mask=filtered_threshold_image)

        if len(self.surviving_contours) > 0:
            self.best_contour = max(self.surviving_contours, key=cv2.contourArea)
            # self.best_contour = min(self.surviving_contours, key=self.y_value)

        self.annotate_image(image)
        self.stopwatch_split("Annotate")

        self.processing_time = time.time() - self.process_start

        return np.array([[]]) if self.best_contour is None else self.best_contour

    def y_value(self, contour):
        x,y,w,h = cv.boundingRect(contour)
        return y

    def print_stopwatch(self, throttle=0):
        if throttle != 0 and self.process_ctr % throttle != 0:
            return

        output = f"Timing: "
        if self.processing_time != 0:
            output += f"{1 / self.processing_time:0.00f} FPS "
        output += f"{self.processing_time}\n"
        for key in self.stopwatch:
            output += f"    {key:20}: {self.stopwatch[key]}\n"

        print(output)
