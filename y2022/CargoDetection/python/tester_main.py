import sys

import os
from PyQt5.QtWidgets import QMainWindow
from PyQt5 import QtGui
from PyQt5.uic import loadUi

from PyQt5.QtWidgets import QApplication
from PyQt5.QtCore import pyqtSlot

import numpy
import traceback

from ui.subpanels import DEFAULT_FILE_DIR

from limelight_pipeline import runPipeline, get_threshold_image, Params, get_params, set_params


class Window(QMainWindow):

    def __init__(self, active_threshold_num=Params.RED_NUM, parent=None):
        print("Loading")
        super().__init__(parent)
        print("Loading")
        loadUi("ui/tester_window.ui", self)
        print("Loading")

        print("Loading")

        self.active_threshold_num = None
        self.original_img = None

        # self.directory_image_provider = DirectoryImageProvider()
        self.set_active_threshold_num(active_threshold_num)
        print("Loading")

        self.hsv_params.connect_signals(self.update_settings)
        self.circle_params.connect_signals(self.update_settings)
        print("Loading")

        self.tabWidget.widget(0).connect_image_callback(self.handle_image)
        self.tabWidget.widget(1).set_image_callback = self.handle_image
        print("Loading")

    def set_active_threshold_num(self, threshold_num):
        self.active_threshold_num = threshold_num

        params = get_params()
        self.hsv_params.set_params(params.get_hsv(self.active_threshold_num))
        self.circle_params.set_params(params.circle)

    def update_settings(self):

        hsv_min, hsv_max = self.hsv_params.ui_to_params()

        params = get_params()
        params.set_hsv(self.active_threshold_num, hsv_min, hsv_max)
        params.circle = self.circle_params.ui_to_params()
        set_params(params)

        self.process_image()

    @pyqtSlot(numpy.ndarray)
    def handle_image(self, img):
        self.original_img = img
        self.process_image()

    def process_image(self):
        if self.original_img is None:
            return

        def cv_to_qt(img):
            return QtGui.QImage(img.data, img.shape[1], img.shape[0], QtGui.QImage.Format_RGB888).rgbSwapped()

        original = self.original_img.copy()
        self.original_image.setPixmap(QtGui.QPixmap.fromImage(cv_to_qt(original)))

        try:
            # output_image = original
            _, output_image, _ = runPipeline(original, [self.active_threshold_num])
        except:
            output_image = original
            traceback.print_exc()

        self.threshold_image.setPixmap(QtGui.QPixmap.fromImage(cv_to_qt(get_threshold_image())))
        self.output_image.setPixmap(QtGui.QPixmap.fromImage(cv_to_qt(output_image)))


def main():
    os.chdir(os.path.dirname(os.path.realpath(__file__)))
    app = QApplication(sys.argv)

    if not os.path.exists(DEFAULT_FILE_DIR):
        os.mkdir(DEFAULT_FILE_DIR)

    # win = Window(active_threshold_num=Params.BLUE_NUM)
    win = Window(active_threshold_num=Params.RED_NUM)

    win.show()
    sys.exit(app.exec())


if __name__ == "__main__":
    main()
