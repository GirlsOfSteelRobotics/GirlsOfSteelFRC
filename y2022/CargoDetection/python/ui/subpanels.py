
from PyQt5.QtWidgets import QVBoxLayout, QWidget, QFrame
from PyQt5.uic import loadUi
from PyQt5.QtWidgets import QApplication, QMainWindow, QFileDialog, QMessageBox
from PyQt5.QtCore import QThread, pyqtSignal, pyqtSlot
import os
import numpy
import time
import cv2
import traceback

DEFAULT_FILE_DIR = os.path.join(os.path.abspath(os.path.dirname(os.path.realpath(__file__))), "../../../../../limelight_images")

class HsvParamsWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        loadUi("ui/hsv_params_widget.ui", self)

    def connect_signals(self, callback):
        self.hue_slider.valueChanged.connect(callback)
        self.sat_slider.valueChanged.connect(callback)
        self.value_slider.valueChanged.connect(callback)

    def set_params(self, hsv_thresh):
        for i in range(2):
            self.hue_slider.setSliderPosition((hsv_thresh[0][0], hsv_thresh[1][0]))
            self.sat_slider.setSliderPosition((hsv_thresh[0][1], hsv_thresh[1][1]))
            self.value_slider.setSliderPosition((hsv_thresh[0][2], hsv_thresh[1][2]))

    def ui_to_params(self):
        h = self.hue_slider.value()
        s = self.sat_slider.value()
        v = self.value_slider.value()

        return (h[0], s[0], v[0]), (h[1], s[1], v[1])


class CircleParamsWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        loadUi("ui/circle_params_widget.ui", self)

    def connect_signals(self, callback):
        self.min_radius.valueChanged.connect(callback)
        self.max_radius.valueChanged.connect(callback)
        self.min_dist.valueChanged.connect(callback)
        self.max_canny.valueChanged.connect(callback)
        self.accuracy.valueChanged.connect(callback)
        self.matching_contour_threshold.valueChanged.connect(callback)

    def set_params(self, circle_params):
        self.min_radius.setValue(circle_params["min_radius"])
        self.max_radius.setValue(circle_params["max_radius"])
        self.min_dist.setValue(circle_params["min_dist"])
        self.max_canny.setValue(circle_params["max_canny_thresh"])
        self.accuracy.setValue(circle_params["accuracy"])
        self.matching_contour_threshold.setValue(circle_params["matching_threshold"])

    def ui_to_params(self):
        return {
            "min_radius": self.min_radius.value(),
            "max_radius": self.max_radius.value(),
            "min_dist": self.min_dist.value(),
            "max_canny_thresh": self.max_canny.value(),
            "accuracy": self.accuracy.value(),
            "matching_threshold": self.matching_contour_threshold.value()
        }


class ContourFilterParamsWidget(QWidget):

    def __init__(self, parent=None):
        super().__init__(parent)
        loadUi("ui/contour_filter_params_widget.ui", self)

    def connect_signals(self, callback):
        self.area_slider.valueChanged.connect(callback)
        self.aspect_ratio_slider.valueChanged.connect(callback)

    def set_params(self, contour_filtering_params):
        for i in range(2):
            self.area_slider.setSliderPosition((contour_filtering_params["min_area"], contour_filtering_params["max_area"]))
            self.aspect_ratio_slider.setSliderPosition((contour_filtering_params["min_aspect_ratio"], contour_filtering_params["max_aspect_ratio"]))

    def ui_to_params(self):
        area = self.hue_slider.value()
        aspect_ratio = self.sat_slider.value()

        return {
            'min_area': area[0],
            'max_area': area[1],
            'min_aspect_ratio': aspect_ratio[0],
            'max_aspect_ratio': aspect_ratio[1]
        }


# class ImageIteratorWidget(QWidget):
#     def __init__(self, parent=None):
#         super().__init__(parent)
#         loadUi("ui/image_iterator_widget.ui", self)
#
#         self.images = []
#         self.image_ctr = 0
#
#     def load_directory(self, directory):
#         self.images = []
#         self.image_ctr = 0
#
#         for f in os.listdir(directory):
#             self.images.append(os.path.join(directory, f))
#
#     def reset_image(self):
#         return self.images[self.image_ctr]


class DirectoryImageProviderWidget(QWidget):

    def __init__(self, parent=None):
        super().__init__(parent)
        loadUi("ui/directory_image_provider_widget.ui", self)

        self.load_directory_btn.pressed.connect(self.select_directory)
        self.set_image_callback = None
        self.image_list.itemClicked.connect(self.load_image)
        self.width = None

    def select_directory(self):
        print("Selecting directory")
        # QFileDialog.getExistingDirectory(self, "Select Directory")
        directory = QFileDialog.getExistingDirectory(self, 'Open Directory', DEFAULT_FILE_DIR)
        self.selected_directory.setText(directory)

        images = []

        if directory:
            for f in os.listdir(directory):
                images.append(f)

        self.image_list.addItems(images)

    def load_image(self, item):
        image_path = os.path.join(self.selected_directory.text(), item.text())
        print(image_path)
        image = cv2.imread(image_path)

        if self.width:
            (h, w) = image.shape[:2]
            r = self.width / float(w)
            dim = (self.width, int(h * r))
            image = cv2.resize(image, dim, interpolation=cv2.INTER_AREA)

        if self.set_image_callback:
            self.set_image_callback(image)



class StreamImageProviderWidget(QWidget):
    class StreamingThread(QThread):
        changePixmap = pyqtSignal(numpy.ndarray)

        def __init__(self, parent):
            super().__init__(parent)
            self.url = None

            self.cap = None
            self.snapshot_directory = None
            self.running = False
            self.take_snapshot = False
            self.record_stream = False
            self.last_snapshot_time = time.time()

            self.image_ctr = 0

        def save_image(self, frame):
            cv2.imwrite(os.path.join(self.snapshot_directory, f"{self.image_ctr:06d}.png"), frame)
            self.image_ctr += 1

        def run(self):
            self.cap = cv2.VideoCapture(self.url)
            self.running = True

            while self.running:

                _, frame = self.cap.read()

                try:
                    if self.take_snapshot:
                        self.save_image(frame)
                        self.take_snapshot = False
                    elif self.record_stream:
                        t = time.time()
                        if (t - self.last_snapshot_time) > 0.25:
                            self.save_image(frame)
                            self.last_snapshot_time = t
                except:
                    traceback.print_exc()

                self.changePixmap.emit(frame)

    def __init__(self, parent=None):
        super().__init__(parent)
        loadUi("ui/stream_image_provider_widget.ui", self)

        self.thread = self.StreamingThread(self)
        self.connect_button.clicked.connect(self.toggle_stream)

        self.snapshot_dir_btn.pressed.connect(self.select_directory)

        def __take_snapshot():
            self.thread.take_snapshot = True

        def __record_stream():
            self.thread.record_stream = self.record_stream_btn.isChecked()

        self.take_snapshot_btn.pressed.connect(__take_snapshot)
        self.record_stream_btn.clicked.connect(__record_stream)

    def select_directory(self):
        print("Selecting directory")
        # QFileDialog.getExistingDirectory(self, "Select Directory")
        directory = QFileDialog.getExistingDirectory(self, 'Open Directory', DEFAULT_FILE_DIR)
        self.snapshot_dir.setText(directory)
        self.thread.snapshot_directory = directory

        if directory:
            self.take_snapshot_btn.setEnabled(True)
            self.record_stream_btn.setEnabled(True)
        else:
            self.take_snapshot_btn.setEnabled(False)
            self.record_stream_btn.setEnabled(False)

    def connect_image_callback(self, cb):
        self.thread.changePixmap.connect(cb)

    def toggle_stream(self):
        self.thread.url = self.stream_url.text()

        if self.connect_button.isChecked():
            self.thread.start()
        else:
            self.thread.running = False

