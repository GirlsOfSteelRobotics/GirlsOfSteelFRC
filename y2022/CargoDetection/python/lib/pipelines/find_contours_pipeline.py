from lib.pipelines.pipeline_params import ContourFilteringParams
import cv2


def find_and_filter_contours(
    image,
    contour_filtering: ContourFilteringParams,
):
    contours, _ = cv2.findContours(image, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    good_contours = []
    filtered_contours = []

    min_area, max_area = contour_filtering.area_pair
    min_aspect_ratio, max_aspect_ratio = contour_filtering.aspect_ratio_pair

    for contour in contours:
        rect = cv2.boundingRect(contour)
        aspect_ratio = rect[2] / rect[3]
        aspect_ratio_good = min_aspect_ratio <= aspect_ratio <= max_aspect_ratio

        area = cv2.contourArea(contour)
        good_area = min_area <= area <= max_area
        if good_area and aspect_ratio_good:
            good_contours.append(contour)
        else:
            filtered_contours.append(contour)

    return good_contours, filtered_contours
