import os
import json

def parse_command_group(command_data, indent):
    command_type = command_data['type']

    output = ""

    if command_type in ["deadline", "sequential", "parallel"]:
        if command_type == "sequential":
            command_type = "sequence"
        output += f"{indent}Commands.{command_type}(\n"
        subcommand_data = []
        for subcommand in command_data["data"]["commands"]:
            subcommand_data.append(parse_command_group(subcommand, indent + "    "))
        output += ",\n".join(subcommand_data)
        output += f"\n{indent})"
    elif command_type == "named":
        output += f'{indent}NamedCommands.getCommand("{command_data["data"]["name"]}")'
    elif command_type == "path":
        output += f'{indent}AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory("{command_data["data"]["pathName"]}"))'
    else:
        raise Exception(f"Unexpected type {command_type}")

    return output

def main():
    autos_path = "src/main/deploy/pathplanner/autos"
    modes_path = "src/main/java/com/gos/crescendo2024/auton/modes"

    if not os.path.exists(modes_path):
        os.mkdir(modes_path)

    for auto_file in os.listdir(autos_path):
        full_file = os.path.join(autos_path, auto_file)
        with open(full_file, 'r') as f:
            auto_data = json.load(f)

            txt_data = parse_command_group(auto_data['command'], "            ")
            txt_data += ";"

        folder = auto_data['folder']
        package_name = "com.gos.crescendo2024.auton.modes"
        if folder is not None:
            mode_folder = os.path.join(modes_path, folder.lower())
            package_name += "." + folder.lower()
        else:
            mode_folder = modes_path

        if not os.path.exists(mode_folder):
            os.mkdir(mode_folder)

        print(mode_folder)
        class_name = auto_file[:-5]
        auto_mode_file = os.path.join(mode_folder, class_name + ".java")
        with open(auto_mode_file, 'w') as f:
            f.write(MODE_FILE_TEMPLATE.format(
                package = package_name,
                class_name = class_name,
                commands_txt = txt_data,
            ))

    auto_mode_enums = [
        "FOUR_NOTE_012",
        "FOUR_NOTE_034",
        "FOUR_NOTE_045",
        "FOUR_NOTE_MIDDLE_012"
        "THREE_NOTE_12",
        "THREE_NOTE_MIDDLE_52"
        "THREE_NOTE_76",
        "THREE_NOTE_65",
        "ASSHOLEAUTO"
        "TWO_NOTE_MIDDLE_1",
        "TWO_NOTE_MIDDLE_2"
        "TWO_NOTE_RIGHT_6",
        "TWO_NOTE_RIGHT_7"
        "ONE_NOTE_JUST_SHOOT"
        "ONE_NOTE_AND_LEAVE_WING"
        "NO_NOTE_LEAVE_WING"
        "BITCHAUTO"
    ]



MODE_FILE_TEMPLATE = """
package {package};

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class {class_name} {{
    
    public static Command create() {{
        return 
{commands_txt}
    }}
}}

"""

AUTOS_TEMPLATE = """
// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.auton;

import com.gos.lib.properties.GosDoubleProperty;
import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class Autos {
    public enum StartPosition {
        STARTING_LOCATION_AMP_SIDE,
        STARTING_LOCATION_MIDDLE,
        STARTING_LOCATION_SOURCE_SIDE,
        CURRENT_LOCATION
    }

    public enum AutoModes {
        //Four Notes - Amp
        FOUR_NOTE_012("FourNoteAmpSide012Choreo", StartPosition.STARTING_LOCATION_AMP_SIDE, List.of(0, 1, 2)),
        FOUR_NOTE_034("FourNoteAmpSide034Choreo", StartPosition.STARTING_LOCATION_AMP_SIDE, List.of(0, 3, 4)),
        FOUR_NOTE_045("FourNoteAmpSide045Choreo", StartPosition.STARTING_LOCATION_AMP_SIDE, List.of(0, 4, 5)),
        FOUR_NOTE_MIDDLE_012("FourNoteMiddle012Choreo", StartPosition.STARTING_LOCATION_MIDDLE, List.of(0, 1, 2)),
        // Three Notes - Middle
        THREE_NOTE_12("ThreeNoteMiddle12Choreo", StartPosition.STARTING_LOCATION_MIDDLE, List.of(1, 2)),
        THREE_NOTE_MIDDLE_52("ThreeNoteMiddle52Choreo", StartPosition.STARTING_LOCATION_MIDDLE, List.of(5, 2)),
        //Three Notes - Source
        THREE_NOTE_76("ThreeNotesSourceSide76Choreo", StartPosition.STARTING_LOCATION_SOURCE_SIDE, List.of(7, 6)),
        THREE_NOTE_65("ThreeNotesSourceSide65Choreo", StartPosition.STARTING_LOCATION_SOURCE_SIDE, List.of(6, 5)),
        //Asshohole Auto (Three Note - Source)
        ASSHOLEAUTO("AThreeNoteSource65Choreo", StartPosition.STARTING_LOCATION_SOURCE_SIDE, List.of()),
        //Two Notes - Amp
        //Two Notes - Middle
        TWO_NOTE_MIDDLE_1("TwoNoteMiddle1Choreo", StartPosition.STARTING_LOCATION_MIDDLE, List.of(1)),
        TWO_NOTE_MIDDLE_2("TwoNoteMiddle2Choreo", StartPosition.STARTING_LOCATION_MIDDLE, List.of(2)),
        //Two Notes - Source
        TWO_NOTE_RIGHT_6("TwoNoteSourceSide6Choreo", StartPosition.STARTING_LOCATION_SOURCE_SIDE, List.of(6)),
        TWO_NOTE_RIGHT_7("TwoNoteSourceSide7Choreo", StartPosition.STARTING_LOCATION_SOURCE_SIDE, List.of(7)),
        //Just shoot preload
        ONE_NOTE_JUST_SHOOT("OneNoteJustShoot", StartPosition.CURRENT_LOCATION, List.of()),
        //Preload and drive
        ONE_NOTE_AND_LEAVE_WING("OneNoteSourceSideAndLeaveWingChoreo", StartPosition.CURRENT_LOCATION, List.of()),
        //No shooting
        NO_NOTE_LEAVE_WING("NoNoteLeaveWingChoreo", StartPosition.CURRENT_LOCATION, List.of()),
        //Bitch Auto
        BITCHAUTO("DisruptionAuto", StartPosition.STARTING_LOCATION_SOURCE_SIDE, List.of());


        public final String m_modeName;
        public final StartPosition m_location;
        public final List<Integer> m_notes;

        AutoModes(String modeName, StartPosition location, List<Integer> notes) {
            m_modeName = modeName;
            m_location = location;
            m_notes = notes;
        }
    }

    private static final AutoModes DEFAULT_MODE = AutoModes.ONE_NOTE_JUST_SHOOT;

    private final SendableChooser<AutoModes> m_autonChooser;

    private final Map<AutoModes, Command> m_modes;

    private static final GosDoubleProperty AUTON_TIMEOUT = new GosDoubleProperty(false, "autoTimeoutSeconds", 0);


    public Autos() {
        m_autonChooser = new SendableChooser<>();
        m_modes = new HashMap<>();

        for (AutoModes mode : AutoModes.values()) {
            Command autoCommand;
            switch (mode) {
                {
            }
            if (mode == DEFAULT_MODE) {
                m_autonChooser.setDefaultOption(mode.m_modeName, mode);
            } else {
                m_autonChooser.addOption(mode.m_modeName, mode);
            }
            m_modes.put(mode, new DeferredCommand(() -> new WaitCommand(AUTON_TIMEOUT.getValue()), new HashSet<>()).andThen(AutoBuilder.buildAuto(mode.m_modeName)));
        }

        SmartDashboard.putData("Auto Chooser", m_autonChooser);
    }

    public Command getSelectedAutonomous() {
        AutoModes mode = m_autonChooser.getSelected();
        return m_modes.get(mode);
    }

    public AutoModes autoModeLightSignal() {
        return m_autonChooser.getSelected();
    }
}
"""


if __name__ == "__main__":
    main()