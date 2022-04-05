from lib.pipelines.pipeline_params import CropImageFilterParams
import cv2


def crop_image(img_threshold, crop_params: CropImageFilterParams):

    for crop_filter in crop_params.crop_pairs:
        top_right, bottom_left = crop_filter.to_rect_pair(img_threshold)
        cv2.rectangle(img_threshold, top_right, bottom_left, 0, -1)

    return img_threshold
