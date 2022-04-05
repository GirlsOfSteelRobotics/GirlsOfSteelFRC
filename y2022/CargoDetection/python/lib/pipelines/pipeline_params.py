from typing import Tuple, List


class HsvThresholdParams:
    def __init__(
        self,
        h: Tuple[int, int],
        s: Tuple[int, int],
        v: Tuple[int, int],
        hue_inverted: bool,
    ):
        self.h = h
        self.s = s
        self.v = v
        self.hue_inverted = hue_inverted

    def to_min_max_pair(self):
        return (self.h[0], self.s[0], self.v[0]), (self.h[1], self.s[1], self.v[1])

    def __repr__(self):
        return self.debug_str("")

    def debug_str(self, indent=""):
        return f"{indent}HsvThresholdParams({self.h}, {self.s}, {self.v}, {self.hue_inverted})"


class ContourFilteringParams:
    def __init__(
        self, area_pair: Tuple[float, float], aspect_ratio_pair: Tuple[float, float]
    ):
        self.area_pair = area_pair
        self.aspect_ratio_pair = aspect_ratio_pair

    def __repr__(self):
        return self.debug_str("")

    def debug_str(self, indent=""):
        return f"{indent}ContourFilteringParams({self.area_pair}, {self.aspect_ratio_pair})"


class CircleDetectionParams:
    def __init__(
        self,
        radius_pair: Tuple[int, int],
        min_dist,
        max_canny_thresh,
        accuracy,
        matching_threshold,
    ):
        self.radius_pair = radius_pair
        self.min_dist = min_dist
        self.max_canny_thresh = max_canny_thresh
        self.accuracy = accuracy
        self.matching_threshold = matching_threshold

    def __repr__(self):
        return self.debug_str("")

    def debug_str(self, indent=""):
        return f"{indent}CircleDetectionParams({self.radius_pair}, {self.min_dist}, {self.max_canny_thresh}, {self.accuracy}, {self.matching_threshold})"


class CropImageFilterParams:
    class CropInfo:
        def __init__(
            self, min_x: int = 0, min_y: int = 0, max_x: int = None, max_y: int = None
        ):
            self.min_x = min_x
            self.min_y = min_y
            self.max_x = max_x
            self.max_y = max_y

        def __repr__(self):
            return self.debug_str("")

        def debug_str(self, indent=""):
            return f"{indent}CropImageFilterParams.CropInfo(min_x={self.min_x}, min_y={self.min_y}, max_x={self.max_x}, max_y={self.max_y})"

        def to_rect_pair(self, image):
            max_x = self.max_x or image.shape[1]
            max_y = self.max_y or image.shape[0]

            return (self.min_x, self.min_y), (max_x, max_y)

    def __init__(self, crop_pairs: List[CropInfo]):
        self.crop_pairs = crop_pairs

    def __repr__(self):
        return self.debug_str("")

    def debug_str(self, indent=""):
        return f"{indent}CropImageFilterParams({self.crop_pairs})"
