from PyQt5.QtWidgets import QWidget
from PyQt5.uic import loadUi
from PyQt5.QtWidgets import QFileDialog
from PyQt5.QtCore import QThread, pyqtSignal
import os
import numpy
import time
import cv2
import traceback

from lib.pipelines.pipeline_params import (
    HsvThresholdParams,
    CircleDetectionParams,
    ContourFilteringParams,
)


class HsvParamsWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        loadUi("lib/ui/hsv_params_widget.ui", self)

    def connect_signals(self, callback):
        self.hue_slider.valueChanged.connect(callback)
        self.sat_slider.valueChanged.connect(callback)
        self.value_slider.valueChanged.connect(callback)
        self.invert_hue_btn.clicked.connect(callback)

    def set_params(self, hsv_thresh: HsvThresholdParams):
        for i in range(2):
            self.hue_slider.setSliderPosition((hsv_thresh.h[0], hsv_thresh.h[1]))
            self.sat_slider.setSliderPosition((hsv_thresh.s[0], hsv_thresh.s[1]))
            self.value_slider.setSliderPosition((hsv_thresh.v[0], hsv_thresh.v[1]))
        self.invert_hue_btn.setChecked(hsv_thresh.hue_inverted)

    def ui_to_params(self) -> HsvThresholdParams:
        h = self.hue_slider.value()
        s = self.sat_slider.value()
        v = self.value_slider.value()

        return HsvThresholdParams(h, s, v, self.invert_hue_btn.isChecked())


class CircleParamsWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        loadUi("lib/ui/circle_params_widget.ui", self)

    def connect_signals(self, callback):
        self.min_radius.valueChanged.connect(callback)
        self.max_radius.valueChanged.connect(callback)
        self.min_dist.valueChanged.connect(callback)
        self.max_canny.valueChanged.connect(callback)
        self.accuracy.valueChanged.connect(callback)
        self.matching_contour_threshold.valueChanged.connect(callback)

    def set_params(self, circle_params: CircleDetectionParams):
        self.min_radius.setValue(circle_params.radius_pair[0])
        self.max_radius.setValue(circle_params.radius_pair[1])
        self.min_dist.setValue(circle_params.min_dist)
        self.max_canny.setValue(circle_params.max_canny_thresh)
        self.accuracy.setValue(circle_params.accuracy)
        self.matching_contour_threshold.setValue(circle_params.matching_threshold)

    def ui_to_params(self) -> CircleDetectionParams:
        return CircleDetectionParams(
            (self.min_radius.value(), self.max_radius.value()),
            self.min_dist.value(),
            self.max_canny.value(),
            self.accuracy.value(),
            self.matching_contour_threshold.value(),
        )


class ContourFilterParamsWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        loadUi("lib/ui/contour_filter_params_widget.ui", self)

    def connect_signals(self, callback):
        self.area_slider.valueChanged.connect(callback)
        self.aspect_ratio_slider.valueChanged.connect(callback)

    def set_params(self, contour_filtering_params: ContourFilteringParams):
        for i in range(2):
            self.area_slider.setSliderPosition(contour_filtering_params.area_pair)
            self.aspect_ratio_slider.setSliderPosition(contour_filtering_params.aspect_ratio_pair)

    def ui_to_params(self) -> ContourFilteringParams:
        area = self.area_slider.value()
        aspect_ratio = self.aspect_ratio_slider.value()

        return ContourFilteringParams(area, aspect_ratio)
