import pandas as pd
import plotly.graph_objects as go
import plotly.express as px
import plotly
import os

from libraries.scripts.datalog_parser.load_log import load_log
import libraries.scripts.datalog_parser.utils as utils


def plot_go_to_angle(dataframe, output_directory):
    cur_angle_key = "NT:/Chassis/Odometry/Angle (deg)"
    goal_angle_key = "NT:/Chassis/TurnToAngle/Goal"

    cur_angle = dataframe[cur_angle_key]
    goal_angle = dataframe[goal_angle_key]

    fig = go.Figure()
    fig.add_trace(go.Scatter(x=cur_angle.index, y=cur_angle.values, name="Current Angle"))
    fig.add_trace(
        go.Scatter(x=goal_angle.index, y=goal_angle.values, name="Goal Angle", mode="markers")
    )

    fig.write_html(os.path.join(output_directory, "go_to_angle.html"))


def plot_intake_angle(dataframe, output_directory):
    cur_angle_left_key = "NT:/CollectorSubsystem/Left Intake (deg)"
    cur_angle_right_key = "NT:/CollectorSubsystem/Right Intake (deg)"
    goal_angle_key = "NT:/CollectorSubsystem/Angle Goal"

    cur_angle_left = dataframe[cur_angle_left_key]
    cur_angle_right = dataframe[cur_angle_right_key]
    goal_angle = dataframe[goal_angle_key]

    fig = go.Figure()
    fig.add_trace(
        go.Scatter(x=cur_angle_left.index, y=cur_angle_left.values, name="Current Angle Left")
    )
    fig.add_trace(
        go.Scatter(x=goal_angle.index, y=goal_angle.values, name="Goal Angle", mode="markers")
    )
    fig.add_trace(
        go.Scatter(x=cur_angle_right.index, y=cur_angle_right.values, name="Current Angle Right")
    )

    fig.write_html(os.path.join(output_directory, "intake_angle.html"))


def plot_shooter_rpm(dataframe, output_directory):
    shooter_rpm_key = "NT:/Shooter/Shooter RPM"
    shooter_goal_key = "NT:/Shooter/Shooter Goal"
    backspin_rpm_key = "NT:/Shooter/Backspin RPM"
    backspin_goal_key = "NT:/Shooter/Backspin Goal"

    shooter_rpm = dataframe[shooter_rpm_key]
    shooter_goal_key = dataframe[shooter_goal_key]
    backspin_rpm_key = dataframe[backspin_rpm_key]
    backspin_goal_key = dataframe[backspin_goal_key]

    fig = go.Figure()
    fig.add_trace(go.Scatter(x=shooter_rpm.index, y=shooter_rpm.values, name="Shooter Rpm"))
    fig.add_trace(
        go.Scatter(x=backspin_rpm_key.index, y=backspin_rpm_key.values, name="Backspin Rpm")
    )
    fig.add_trace(
        go.Scatter(
            x=shooter_goal_key.index, y=shooter_goal_key.values, name="Shooter Goal", mode="markers"
        )
    )
    fig.add_trace(
        go.Scatter(x=backspin_goal_key.index, y=backspin_goal_key.values, name="Backspin Goal")
    )

    fig.update_yaxes(range=[0, 4000])

    fig.write_html(os.path.join(output_directory, "shooter_rpm.html"))


def plot_shooting_window(df, output_directory, extra_time_buffer=5):
    # Plot the whole log
    plot_intake_angle(df, output_directory)
    plot_go_to_angle(df, output_directory)
    plot_shooter_rpm(df, output_directory)

    # Get the times blocks we were spinning the shooter
    time_windows = utils.load_command_running_windows(
        df, "ShooterSubsystem", "AutoLimelightConveyorAndShooterCommand"
    )
    print(time_windows)

    for i, (min_time, max_time) in enumerate(time_windows):
        if min_time is None or max_time is None:
            print("Uh oh")
            continue
        filtered = utils.filter_by_time(
            df, min_time - extra_time_buffer, max_time + extra_time_buffer
        )
        filtered_output_directory = os.path.join(output_directory, f"Shooter{i}")
        if not os.path.exists(filtered_output_directory):
            os.mkdir(filtered_output_directory)

        plot_intake_angle(filtered, filtered_output_directory)
        plot_go_to_angle(filtered, filtered_output_directory)
        plot_shooter_rpm(filtered, filtered_output_directory)


def main():
    directory = r"C:\Users\girls\Desktop\Worlds Robot Data"
    log_name = "FRC_20220421_153958_GALILEO_Q18"

    #     directory = r"C:\Users\PJ\Documents\GitHub\gos_data\RapidReactRobotLogs"
    #     log_name = "FRC_20220402_203438"

    converted_file = os.path.join(directory, f"{log_name}.wpilog")
    output_directory = os.path.join(directory, log_name)
    if not os.path.exists(output_directory):
        os.mkdir(output_directory)

    df = load_log(converted_file)

    # Debug what things are in log
    utils.save_dataframe_metadata(df, output_directory)
    print(f"Outputing to {output_directory}")

    plot_shooting_window(df, output_directory)


if __name__ == "__main__":
    # run with
    # py -m y2022.RapidReact.log_analyzer.make_plots
    main()
