from PyQt5.QtWidgets import QWidget
from PyQt5.uic import loadUi
from PyQt5.QtWidgets import QFileDialog
from PyQt5.QtCore import QThread, pyqtSignal
import os
import numpy
import time
import cv2
import traceback

DEFAULT_FILE_DIR = os.path.join(
    os.path.abspath(os.path.dirname(os.path.realpath(__file__))),
    "../../../../../../limelight_images",
)


class DirectoryImageProviderWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        loadUi("lib/ui/directory_image_provider_widget.ui", self)

        self.load_directory_btn.pressed.connect(self.select_directory)
        self.set_image_callback = None
        self.image_list.currentItemChanged.connect(self.load_image)
        self.width = None

    def select_directory(self):
        directory = QFileDialog.getExistingDirectory(self, "Open Directory", DEFAULT_FILE_DIR)
        self.load_directory(directory)

    def load_directory(self, directory):

        self.selected_directory.setText(directory)
        self.image_list.clear()

        images = []

        if directory:
            for f in os.listdir(directory):
                images.append(f)

        self.image_list.addItems(images)

    def load_image(self, item):
        image_path = os.path.join(self.selected_directory.text(), item.text())
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
            cv2.imwrite(
                os.path.join(self.snapshot_directory, f"{self.image_ctr:06d}.png"),
                frame,
            )
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
        loadUi("lib/ui/stream_image_provider_widget.ui", self)

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
        directory = QFileDialog.getExistingDirectory(self, "Open Directory", DEFAULT_FILE_DIR)
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
