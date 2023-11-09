from PyQt5.QtWidgets import QVBoxLayout, QWidget, QFrame, QTabWidget
from PyQt5.uic import loadUi
from typing import List, Dict, Union

from libraries.DashboardGenerator.lib.dashboard_config import (
    DashboardConfig,
    WidgetConfig,
    BaseShape,
    ChildTableConfig,
    EntryConfig,
    Rectangle,
    Circle,
    Arc,
)


def load_ui_file(filename, parent):
    try:
        from rules_python.python.runfiles import runfiles

        r = runfiles.Create()
        resolved_filename = r.Rlocation("__main__/" + filename)

    except ModuleNotFoundError:
        resolved_filename = filename

    loadUi(resolved_filename, parent)


def set_text_if_not_null(widget, text: Union[str, None]):
    if text is not None:
        widget.setText(str(text))


class TopLevelConfigWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/DashboardGenerator/lib/ui/top_level_config.ui", self)

        self.tabWidget: QTabWidget
        self.tabs: List[WidgetTopLevelConfigWidget] = []
        self.m_addWidgetButton.clicked.connect(self.add_widget)

    def add_widget(self, tab_name="New Widget"):
        tab = WidgetTopLevelConfigWidget(parent=self)
        self.tabs.append(tab)
        self.tabWidget.addTab(tab, tab_name)
        return tab

    def config_to_view(self, config: DashboardConfig):
        self.m_basePackageName.setText(config.base_package.package)
        self.m_pluginSummary.setText(config.plugin_summary.name)
        self.m_pluginName.setText(config.plugin_name.name)
        self.manual_widgets = config.manual_widgets

        for widget in config.widgets:
            tab = self.add_widget(widget.table.name)
            tab.config_to_view(widget)

    def view_to_config(self) -> DashboardConfig:
        return DashboardConfig(
            self.m_basePackageName.text(),
            self.m_pluginSummary.text(),
            self.m_pluginName.text(),
            [w.view_to_config() for w in self.tabs],
            self.manual_widgets,
        )


class WidgetTopLevelConfigWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/DashboardGenerator/lib/ui/widget_top_level_config.ui", self)

        self.child_table_config_tabs: QTabWidget
        self.tabs: List[ChildTableConfigWidget] = []

        self.shapes_tab = TopLevelShapesConfigWidget()
        self.child_table_config_tabs.addTab(self.shapes_tab, "*Widget Shapes*")

        self.add_child_table_btn.clicked.connect(self.add_child_table)

    def add_child_table(self, tab_name="New Child"):
        tab = ChildTableConfigWidget(parent=self)
        self.tabs.append(tab)
        self.child_table_config_tabs.addTab(tab, tab_name)
        return tab

    def config_to_view(self, widget_config: WidgetConfig):
        self.table_name.setText(widget_config.table.name)
        self.widget_name.setText(widget_config.widget_name)
        self.subpackage_name.setText(widget_config.package_name.package)
        self.nt_constant.setText(widget_config.sd_table_name_constant.name)

        for child in widget_config.children_tables:
            tab = self.add_child_table(child.table.name)
            tab.config_to_view(child)

        self.shapes_tab.config_to_view(widget_config.shapes)

    def view_to_config(self) -> WidgetConfig:
        return WidgetConfig(
            self.table_name.text(),
            self.subpackage_name.text(),
            self.widget_name.text(),
            self.nt_constant.text(),
            [c.view_to_config() for c in self.tabs],
            # [],
            self.shapes_tab.view_to_config(),
        )


class ShapeWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/DashboardGenerator/lib/ui/shape_config.ui", self)
        self.setStyleSheet(
            "QFrame {"
            "background-color: rgb(0, 0, 0);"
            "border-width: 1;"
            "border-radius: 3;"
            "border-style: solid;"
            "border-color: rgb(10, 10, 10)"
            "}"
        )

        self.type_combo.currentIndexChanged.connect(self.__handle_type_changed)
        self.__handle_type_changed(None)

        self.type_combo.setCurrentText("Rectangle")
        self.name_txt.setText("shapeName")

    def __handle_type_changed(self, _):
        if self.type_combo.currentText() == "Rectangle":
            self.width_txt.show()
            self.height_txt.show()
            self.radius_txt.hide()

            self.width_lbl.show()
            self.height_lbl.show()
            self.radius_lbl.hide()
        elif self.type_combo.currentText() in ["Arc", "Circle"]:
            self.width_txt.hide()
            self.height_txt.hide()
            self.radius_txt.show()

            self.width_lbl.hide()
            self.height_lbl.hide()
            self.radius_lbl.show()
        else:
            raise

    def config_to_view(self, shapes_config: BaseShape):
        self.type_combo.setCurrentText(shapes_config.type)
        self.name_txt.setText(shapes_config.name.name)

        set_text_if_not_null(self.x_txt, shapes_config.x)
        set_text_if_not_null(self.y_txt, shapes_config.y)
        set_text_if_not_null(self.color_txt, shapes_config.color)

        if isinstance(shapes_config, Rectangle):
            set_text_if_not_null(self.width_txt, shapes_config.width)
            set_text_if_not_null(self.height_txt, shapes_config.height)
        elif isinstance(shapes_config, Arc) or isinstance(shapes_config, Circle):
            set_text_if_not_null(self.radius_txt, shapes_config.radius)

    def view_to_config(self) -> BaseShape:
        t = self.type_combo.currentText()
        base_kwargs = dict(
            name=self.name_txt.text(),
            t=t,
            x=self.x_txt.text() or None,
            y=self.y_txt.text() or None,
            color=self.color_txt.text(),
        )

        if t == "Rectangle":
            return Rectangle(
                width=self.width_txt.text() or None,
                height=self.height_txt.text() or None,
                rotates=self.rotates_box.isChecked(),
                **base_kwargs
            )
        if t == "Circle":
            return Circle(radius=self.radius_txt.text() or None, **base_kwargs)
        if t == "Arc":
            return Arc(radius=self.radius_txt.text() or None, **base_kwargs)

        raise


class TopLevelShapesConfigWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/DashboardGenerator/lib/ui/shape_top_level_config.ui", self)

        self.shapes_layout = QVBoxLayout()
        self.scrollAreaWidgetContents.setLayout(self.shapes_layout)

        self.add_shape_btn.clicked.connect(self.add_shape)

        self.shapes_widgets = []

    def add_shape(self):
        shape_widget = ShapeWidget()
        self.shapes_widgets.append(shape_widget)
        self.shapes_layout.addWidget(shape_widget)

        return shape_widget

    def config_to_view(self, shapes_config: List[BaseShape]):
        for shape_config in shapes_config:
            shape_widget = self.add_shape()
            shape_widget.config_to_view(shape_config)

    def view_to_config(self) -> List[BaseShape]:
        table_shapes = []
        for shape_widget in self.shapes_widgets:
            shape_config = shape_widget.view_to_config()
            table_shapes.append(shape_config)

        return table_shapes


class ChildTableConfigWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/DashboardGenerator/lib/ui/child_table_config.ui", self)

        self.fields_layout = QVBoxLayout()
        self.scrollAreaWidgetContents.setLayout(self.fields_layout)

        self.add_field_btn.clicked.connect(self.add_field)
        self.field_widgets = []

    def add_field(self):
        field_widget = FieldConfigWidget()
        self.field_widgets.append(field_widget)
        self.fields_layout.addWidget(field_widget)

        return field_widget

    def config_to_view(self, child_table_config: ChildTableConfig):
        self.table_name.setText(child_table_config.table.name)
        self.table_constant.setText(child_table_config.table_name.name)

        for field_config in child_table_config.entries:
            field_widget = self.add_field()
            field_widget.config_to_view(field_config)

    def view_to_config(self) -> ChildTableConfig:
        table_fields = []
        for field_widget in self.field_widgets:
            field_config = field_widget.view_to_config()
            table_fields.append(field_config)

        return ChildTableConfig(self.table_name.text(), self.table_constant.text(), table_fields)


class FieldConfigWidget(QFrame):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/DashboardGenerator/lib/ui/field_config.ui", self)
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
        self.type_box.currentIndexChanged.connect(self.__handle_type_changed)

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

    def __handle_type_changed(self, val):
        if self.type_box.currentText() == "double":
            self.is_motor_speed.show()
        else:
            self.is_motor_speed.hide()

    def config_to_view(self, field_config: EntryConfig):
        self.type_box.setCurrentText(field_config.type.type)
        self.field_name.setText(field_config.name.name)
        self.sd_constant.setText(field_config.dashboard_constant.name)
        self.is_motor_speed.setChecked(not field_config.sim_incr)
        self.sim_increment.setText(str(field_config.sim_incr or 2))

    def view_to_config(self) -> EntryConfig:
        return EntryConfig(
            self.type_box.currentText(),
            self.field_name.text(),
            self.sd_constant.text(),
            None,
            None,
            None if self.is_motor_speed.isChecked() else self.sim_increment.text(),
        )


class GenerateConfigWidget(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        load_ui_file("libraries/DashboardGenerator/lib/ui/generate_config.ui", self)

        # Initialize default values
        self.force_utils.setChecked(True)
        self.force_nt_names.setChecked(True)
        self.force_tester.setChecked(True)

    def get_forced_generation_kwargs(self) -> Dict[str, bool]:
        output = {
            "force_utils": self.force_utils.isChecked(),
            "force_nt_names": self.force_nt_names.isChecked(),
            "force_standalone_main": self.force_tester.isChecked(),
            "force_fxml": self.force_fxml.isChecked(),
            "force_controller": self.force_controller.isChecked(),
        }

        return output
