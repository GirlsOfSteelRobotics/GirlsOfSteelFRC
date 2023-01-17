import sys

import yaml
import os
from PyQt5.QtWidgets import QApplication, QMainWindow, QFileDialog, QMessageBox
import qdarkstyle
import traceback

from libraries.ShuffleboardGenerator.generate_dashboard import (
    generate_dashboard,
    get_this_directory,
)
from libraries.ShuffleboardGenerator.lib.ui.subpanels import load_ui_file


class Window(QMainWindow):

    DEFAULT_FILE_DIR = os.path.join(
        get_this_directory(), "..", "..", "y2023", "ChargedUpDashboard"
    )
    DEFAULT_FILE = os.path.join(DEFAULT_FILE_DIR, "dashboard.yml")

    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/ShuffleboardGenerator/lib/ui/main_window_config.ui", self)
        self.centralwidget.setLayout(self.main_layout)
        self.config_file = None
        self.actionSave.setEnabled(False)

        self.setStyleSheet(qdarkstyle.load_stylesheet())

        self.connect_signals()

    def connect_signals(self):
        self.generate_config_widget.generate_btn.clicked.connect(self.handle_gen_clicked)

        self.actionOpen.triggered.connect(self.handle_open_file)
        self.actionSave.triggered.connect(self.save_config)

    def handle_open_file(self):
        filename, _ = QFileDialog.getOpenFileName(
            self, "Open file", self.DEFAULT_FILE_DIR, "Dashboard Files (*.yml)"
        )
        if filename:
            self.load_config(filename)

    def handle_gen_clicked(self):

        # Save the file anytime we run generation
        self.save_config()

        try:
            generate_dashboard(
                self.config_file,
                project_dir=None,
                **self.generate_config_widget.get_forced_generation_kwargs(),
            )

            msg_icon = QMessageBox.Information
            msg_text = "Generation Complete"
            msg_info = ""
        except:
            print(f"Could not save")
            traceback.print_exc()

            msg_icon = QMessageBox.Critical
            msg_text = "Could not run generation. Check your fields"
            msg_info = traceback.format_exc()

        msg = QMessageBox()
        msg.setIcon(msg_icon)
        msg.setText(msg_text)
        msg.setInformativeText(msg_info)
        msg.exec_()

    def load_config(self, config_file):

        with open(config_file, "r") as f:
            config = yaml.safe_load(f)

        self.toplevel_widget.config_to_view(config)

        widget_config = config["widgets"][0]
        self.widget_toplevel_widget.config_to_view(widget_config)

        # Now that we have a config, we can enable the save button
        self.config_file = config_file
        self.actionSave.setEnabled(False)

    def save_config(self):

        config = self.toplevel_widget.view_to_config()
        widget_config = self.widget_toplevel_widget.view_to_config()
        config["widgets"] = [widget_config]

        with open(self.config_file, "w") as f:
            yaml.dump(config, f, sort_keys=False, indent=19)


def main():
    app = QApplication(sys.argv)
    win = Window()
    win.load_config(Window.DEFAULT_FILE)
    win.show()
    sys.exit(app.exec())


if __name__ == "__main__":
    "py -m libraries.ShuffleboardGenerator.gui"
    main()
