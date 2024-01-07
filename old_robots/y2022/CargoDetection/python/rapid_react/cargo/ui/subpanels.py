from PyQt5.QtWidgets import QWidget
from PyQt5.uic import loadUi

from rapid_react.cargo.cargo_pipeline_params import CargoPipelineParams


class ParamsWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        loadUi("rapid_react/cargo/ui/params_widget.ui", self)

    def set_params(self, params: CargoPipelineParams):
        self.hsv_tab.set_params(params.hsv)
        self.circles_tab.set_params(params.circle_detection)
        self.contour_filter_tab.set_params(params.contour_filtering)

    def ui_to_params(self, params: CargoPipelineParams):
        params.hsv = self.hsv_tab.ui_to_params()
        params.circle_detection = self.circles_tab.ui_to_params()
        params.contour_filtering = self.contour_filter_tab.ui_to_params()

    def connect_signals(self, cb):
        self.hsv_tab.connect_signals(cb)
        self.circles_tab.connect_signals(cb)
        self.contour_filter_tab.connect_signals(cb)
