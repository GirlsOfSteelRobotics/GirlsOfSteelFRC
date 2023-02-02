import sys

import os
from PyQt5.QtWidgets import QMainWindow, QMessageBox
from PyQt5 import QtGui
from PyQt5.uic import loadUi

from PyQt5.QtWidgets import QApplication
from PyQt5.QtCore import pyqtSlot

import numpy
import traceback

from lib.ui.input_subpanels import DEFAULT_FILE_DIR

USE_AMALGAMATION = False
if USE_AMALGAMATION:
    from amalgum_pipeline import runPipeline, RED_NUM, BLUE_NUM, get_active_pipeline
else:
    from rapid_react.cargo.base_limelight_pipeline import (
        runPipeline,
        RED_NUM,
        BLUE_NUM,
        get_active_pipeline,
    )


class Window(QMainWindow):
    def __init__(self, active_threshold_num=RED_NUM, parent=None):
        super().__init__(parent)
        loadUi("rapid_react/cargo/ui/tester_window.ui", self)

        self.active_threshold_num = None
        self.original_img = None

        self.set_active_threshold_num(active_threshold_num)

        self.params_settings.connect_signals(self.update_settings)
        self.params_settings.save_params_btn.clicked.connect(self.save_settings)

        self.streaming_tab.connect_image_callback(self.handle_image)
        self.snapshots_tab.set_image_callback = self.handle_image

    def set_active_threshold_num(self, threshold_num):
        self.active_threshold_num = threshold_num

        params = get_active_pipeline(self.active_threshold_num).params
        self.params_settings.set_params(params)

    def update_settings(self):
        try:
            params = get_active_pipeline(self.active_threshold_num).params
            self.params_settings.ui_to_params(params)
            print(params)
        except:
            traceback.print_exc()

        self.process_image()

    def save_settings(self):
        print("Saving settings")

        msg = QMessageBox()
        msg.setIcon(QMessageBox.Information)
        msg.setInformativeText(
            "PJ is bad at QT. Copy the details into the appropriate color in y2022/CargoDetection/python/rapid_react/cargo/cargo_pipeline_params.py"
        )

        params = get_active_pipeline(self.active_threshold_num).params
        msg.setDetailedText(str(params))

        msg.exec_()

    @pyqtSlot(numpy.ndarray)
    def handle_image(self, img):
        self.original_img = img
        self.process_image()

    def process_image(self):
        if self.original_img is None:
            return

        def cv_to_qt(img):
            return QtGui.QImage(
                img.data, img.shape[1], img.shape[0], QtGui.QImage.Format_RGB888
            ).rgbSwapped()

        original = self.original_img.copy()
        self.original_image.setPixmap(QtGui.QPixmap.fromImage(cv_to_qt(original)))

        try:
            _, output_image, _ = runPipeline(original, [self.active_threshold_num])
        except:
            output_image = original
            traceback.print_exc()

        active_pipeline = get_active_pipeline(self.active_threshold_num)
        self.threshold_image.setPixmap(
            QtGui.QPixmap.fromImage(cv_to_qt(active_pipeline.threshold_image))
        )
        self.output_image.setPixmap(QtGui.QPixmap.fromImage(cv_to_qt(output_image)))


def main():
    this_dir = os.path.dirname(os.path.realpath(__file__))
    os.chdir(this_dir)

    app = QApplication(sys.argv)

    if not os.path.exists(DEFAULT_FILE_DIR):
        os.mkdir(DEFAULT_FILE_DIR)

    amalgum_pipeline = os.path.join(this_dir, "amalgum_pipeline.py")
    if os.path.exists(amalgum_pipeline) and not USE_AMALGAMATION:
        os.remove(os.path.join(this_dir, "amalgum_pipeline.py"))

    default_snapshot_directory = os.path.join(DEFAULT_FILE_DIR, "practiceFieldBlue")
    default_snapshot_directory = os.path.join(DEFAULT_FILE_DIR, "practiceFieldRed", "practiceField")

    # win = Window(active_threshold_num=BLUE_NUM)
    win = Window(active_threshold_num=RED_NUM)

    # win.snapshots_tab.load_directory(os.path.abspath(default_snapshot_directory))

    win.show()
    sys.exit(app.exec())


if __name__ == "__main__":
    main()
