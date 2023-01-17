from PyQt5.QtWidgets import QVBoxLayout, QWidget, QFrame
from PyQt5.uic import loadUi
import os


def load_ui_file(filename, parent):
    try:
        from rules_python.python.runfiles import runfiles

        r = runfiles.Create()
        resolved_filename = r.Rlocation("__main__/" + filename)

    except ModuleNotFoundError:
        resolved_filename = filename

    loadUi(resolved_filename, parent)


class TopLevelConfigWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/ShuffleboardGenerator/lib/ui/top_level_config.ui", self)

    def config_to_view(self, config):
        self.m_basePackageName.setText(config["base_package"])
        self.m_pluginSummary.setText(config["plugin_summary"])
        self.m_pluginName.setText(config["plugin_name"])

    def view_to_config(self):
        config = {}
        config["base_package"] = self.m_basePackageName.text()
        config["plugin_summary"] = self.m_pluginSummary.text()
        config["plugin_name"] = self.m_pluginName.text()

        return config


class WidgetTopLevelConfigWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/ShuffleboardGenerator/lib/ui/widget_top_level_config.ui", self)

    def config_to_view(self, widget_config):
        self.table_name.setText(widget_config["table"])
        self.widget_name.setText(widget_config["widget_name"])
        self.subpackage_name.setText(widget_config["package_name"])
        self.nt_constant.setText(widget_config["sd_table_name_constant"])

        self.child_table_config.config_to_view(widget_config["children_tables"][0])
        self.shapes_widget.config_to_view(widget_config["shapes"])

    def view_to_config(self):
        widget_config = {}
        widget_config["table"] = self.table_name.text()
        widget_config["package_name"] = self.subpackage_name.text()
        widget_config["widget_name"] = self.widget_name.text()
        widget_config["sd_table_name_constant"] = self.nt_constant.text()

        widget_config["children_tables"] = [self.child_table_config.view_to_config()]
        widget_config["shapes"] = self.shapes_widget.view_to_config()

        return widget_config


class ShapeWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/ShuffleboardGenerator/lib/ui/shape_config.ui", self)
        self.setStyleSheet(
            "QFrame {"
            "background-color: rgb(0, 0, 0);"
            "border-width: 1;"
            "border-radius: 3;"
            "border-style: solid;"
            "border-color: rgb(10, 10, 10)"
            "}"
        )

        self.type_combo.setCurrentText("Rectangle")
        self.name_txt.setText("shapeName")

    def config_to_view(self, shapes_config):
        self.type_combo.setCurrentText(shapes_config["type"])
        self.name_txt.setText(shapes_config["name"])

    def view_to_config(self):
        return {"type": self.type_combo.currentText(), "name": self.name_txt.text()}


class TopLevelShapesConfigWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/ShuffleboardGenerator/lib/ui/shape_top_level_config.ui", self)

        self.shapes_layout = QVBoxLayout()
        self.scrollAreaWidgetContents.setLayout(self.shapes_layout)

        self.add_shape_btn.clicked.connect(self.add_shape)

        self.shapes_widgets = []

    def add_shape(self):
        shape_widget = ShapeWidget()
        self.shapes_widgets.append(shape_widget)
        self.shapes_layout.addWidget(shape_widget)

        return shape_widget

    def config_to_view(self, shapes_config):
        for shape_config in shapes_config:
            shape_widget = self.add_shape()
            shape_widget.config_to_view(shape_config)

    def view_to_config(self):
        table_shapes = []
        for shape_widget in self.shapes_widgets:
            shape_config = shape_widget.view_to_config()
            table_shapes.append(shape_config)

        return table_shapes


class ChildTableConfig(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/ShuffleboardGenerator/lib/ui/child_table_config.ui", self)

        self.fields_layout = QVBoxLayout()
        self.scrollAreaWidgetContents.setLayout(self.fields_layout)

        self.add_field_btn.clicked.connect(self.add_field)
        self.field_widgets = []

    def add_field(self):
        field_widget = FieldConfigWidget()
        self.field_widgets.append(field_widget)
        self.fields_layout.addWidget(field_widget)

        return field_widget

    def config_to_view(self, child_table_config):
        self.table_name.setText(child_table_config["table"])
        self.table_constant.setText(child_table_config["table_name"])

        for field_config in child_table_config["entries"]:
            field_widget = self.add_field()
            field_widget.config_to_view(field_config)

    def view_to_config(self):
        table_fields = []
        for field_widget in self.field_widgets:
            field_config = field_widget.view_to_config()
            table_fields.append(field_config)

        child_table_config = {}
        child_table_config["table"] = self.table_name.text()
        child_table_config["table_name"] = self.table_constant.text()
        child_table_config["entries"] = table_fields

        return child_table_config


class FieldConfigWidget(QFrame):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/ShuffleboardGenerator/lib/ui/field_config.ui", self)
        self.setStyleSheet(
            "QFrame {"
            "background-color: rgb(0, 0, 0);"
            "border-width: 1;"
            "border-radius: 3;"
            "border-style: solid;"
            "border-color: rgb(10, 10, 10)"
            "}"
        )

        self.is_motor_speed.stateChanged.connect(self.__handle_motor_speed_checked)

        # Initialize with default values
        self.type_box.setCurrentText("double")
        self.field_name.setText("fieldName")
        self.sd_constant.setText("FIELD_NAME")
        self.is_motor_speed.setChecked(True)
        self.sim_increment.setText("2")

    def __handle_motor_speed_checked(self, val):
        if self.is_motor_speed.isChecked():
            self.sim_increment_lbl.hide()
            self.sim_increment.hide()
        else:
            self.sim_increment_lbl.show()
            self.sim_increment.show()

    def config_to_view(self, field_config):
        self.type_box.setCurrentText(field_config["type"])
        self.field_name.setText(field_config["name"])
        self.sd_constant.setText(field_config["dasboard_constant"])
        self.is_motor_speed.setChecked("sim_incr" not in field_config)
        self.sim_increment.setText(str(field_config.get("sim_incr", 2)))

    def view_to_config(self):
        field_config = {}
        field_config["type"] = self.type_box.currentText()
        field_config["name"] = self.field_name.text()
        field_config["dasboard_constant"] = self.sd_constant.text()
        if not self.is_motor_speed.isChecked():
            field_config["sim_incr"] = 2

        return field_config


class GenerateConfigWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/ShuffleboardGenerator/lib/ui/generate_config.ui", self)

        # Initialize default values
        self.force_utils.setChecked(True)
        self.force_nt_names.setChecked(True)
        self.force_tester.setChecked(True)

    def get_forced_generation_kwargs(self):
        output = {}

        output["force_utils"] = self.force_utils.isChecked()
        output["force_nt_names"] = self.force_nt_names.isChecked()
        output["force_standalone_main"] = self.force_tester.isChecked()
        output["force_fxml"] = self.force_fxml.isChecked()
        output["force_controller"] = self.force_controller.isChecked()

        return output
