from lib.pipelines.pipeline_params import HsvThresholdParams, CircleDetectionParams, ContourFilteringParams, CropImageFilterParams


class CargoPipelineParams:
    def __init__(
        self,
        hsv: HsvThresholdParams,
        contour_filtering: ContourFilteringParams,
        circle_detection: CircleDetectionParams,
        image_crop: CropImageFilterParams,
    ):
        self.hsv = hsv
        self.contour_filtering = contour_filtering
        self.circle_detection = circle_detection
        self.image_crop = image_crop

    def __repr__(self):
        output = f"""
CargoPipelineParams(
    hsv={self.hsv},
    contour_filtering={self.contour_filtering},
    circle_detection={self.circle_detection},
    image_crop={self.image_crop},
"""
        return output


# Shop
BLUE_PARAMS = CargoPipelineParams(
    hsv=HsvThresholdParams((92, 114), (238, 255), (140, 255), False),
    contour_filtering=ContourFilteringParams((5, 4800), (0.5, 2.5)),
    circle_detection=CircleDetectionParams((5, 1000), 18, 25, 12, 10),
    image_crop=CropImageFilterParams([]),
)

RED_PARAMS = CargoPipelineParams(
    hsv=HsvThresholdParams((4, 176), (176, 255), (130, 255), True),
    contour_filtering=ContourFilteringParams((5, 4800), (0.5, 1.5)),
    circle_detection=CircleDetectionParams((12, 500), 10, 21, 13, 21),
    image_crop=CropImageFilterParams([]),
)


# # GPR
# BLUE_PARAMS = CargoPipelineParams(
#     hsv=HsvThresholdParams((107, 165), (95, 255), (22, 255), False),
#     contour_filtering=ContourFilteringParams((173, 3663), (0.25, 2.5)),
#     circle_detection=CircleDetectionParams((5, 500), 48, 35, 10, 17),
#     image_crop=CropImageFilterParams([CropImageFilterParams.CropInfo(min_x=0, min_y=160, max_x=None, max_y=320)]),
# )
#
# RED_PARAMS = CargoPipelineParams(
#     hsv=HsvThresholdParams((17, 172), (94, 254), (236, 255), True),
#     contour_filtering=ContourFilteringParams((60, 10000), (0.25, 2.5)),
#     circle_detection=CircleDetectionParams((5, 100), 26, 29, 10, 19),
#     image_crop=CropImageFilterParams(
#         [CropImageFilterParams.CropInfo(min_y=160, max_y=320)]
#     ),
# )
