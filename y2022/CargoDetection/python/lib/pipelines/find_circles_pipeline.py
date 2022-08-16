import cv2
import numpy as np

from lib.pipelines.pipeline_params import CircleDetectionParams


def find_filtered_hough_circles(img_threshold, contours, circle_params: CircleDetectionParams):

    filtered_threshold_image = np.zeros(img_threshold.shape, dtype=np.uint8)
    cv2.drawContours(filtered_threshold_image, contours, -1, 255, thickness=cv2.FILLED)

    circles = cv2.HoughCircles(
        filtered_threshold_image,
        cv2.HOUGH_GRADIENT,
        1,
        minDist=circle_params.min_dist,
        param1=circle_params.max_canny_thresh,
        param2=circle_params.accuracy,
        minRadius=circle_params.radius_pair[0],
        maxRadius=circle_params.radius_pair[1],
    )

    return circles, filtered_threshold_image


def find_valid_circles(contours, circles, circle_params: CircleDetectionParams):
    threshold = circle_params.matching_threshold
    unmatched_contours = contours
    matched_contours = []
    good_circles = []
    bad_circles = []

    if circles is None:
        return [], [], unmatched_contours, []

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

                unmatched_contours = tuple(
                    x for x in unmatched_contours if not np.array_equal(x, contour)
                )
                break
        else:
            bad_circles.append(c)

    unmatched_circles = np.uint16(np.around(bad_circles))
    good_circles = np.uint16(np.around(good_circles))

    return matched_contours, good_circles, unmatched_contours, unmatched_circles
