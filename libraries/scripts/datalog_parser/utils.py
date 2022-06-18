
import re
import os


def get_command_key(subsystem_name):
    return f"NT:/LiveWindow/{subsystem_name}/.command"


def get_subsystems(dataframe):
    subsystems = []
    for key in dataframe.keys():
        matches = re.search(f"NT\:\/LiveWindow\/(.*)\/\.command", key)
        if matches:
            subsystem = matches.group(1)
            subsystems.append(subsystem)

    return subsystems


def get_commands_in_log(dataframe):
    subsystems = get_subsystems(dataframe)

    commands = set()
    for subsystem in subsystems:
        command_key = get_command_key(subsystem)
        filtered_df = dataframe[dataframe[command_key].notnull()]
        filtered_df = filtered_df[filtered_df[command_key] != "none"]
        unique_commands = filtered_df[command_key].unique()
        for c in unique_commands:
            commands.add((subsystem, c))

    return commands


def load_command_running_windows(dataframe, subsystem_name, command_name):
    command_key = get_command_key(subsystem_name)

    filtered = dataframe[dataframe[command_key].notnull()]
    last_on_time = None
    time_pairs = []
    for index, row in filtered.iterrows():
        command = row[command_key]

        # We got our guy. Mark the start time
        if command == command_name:
            last_on_time = index
        # (Some) command went from running to not running
        # If our target command is running, we know we have a complete time window
        elif command == "none":
            if last_on_time is not None:
                time_pairs.append((last_on_time, index))
        # A different command took over. This also completes a time window
        else:
            time_pairs.append((last_on_time, index))
            last_on_time = None

    return time_pairs

def filter_by_time(dataframe, min_time, max_time):
    return dataframe[(dataframe.index >= min_time) & (dataframe.index <= max_time)]

def save_dataframe_metadata(df, output_directory):
    with open(os.path.join(output_directory, 'metadata.txt'), 'w') as f:
        f.write("\n\nAll available keys:\n")
        f.write("\n".join(sorted(list(df.keys()))))

        f.write("\n\nAvailable Subsystems:\n")
        f.write("\n".join(get_subsystems(df)))

        f.write("\n\nAvailable Commands:\n")
        f.write("\n".join(f"{x[1]:45}({x[0]})" for x in sorted(get_commands_in_log(df))))

