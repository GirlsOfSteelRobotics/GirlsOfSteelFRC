
import pandas as pd
import plotly.graph_objects as go
import plotly.express as px
import plotly
import os

from libraries.scripts.datalog_parser.load_log import load_log


def plot_go_to_angle(dataframe, output_directory):
    cur_angle_key = "NT:/Chassis/Odometry/Angle (deg)"
    goal_angle_key = "NT:/Chassis/TurnToAngle/Goal"

    cur_angle = dataframe[cur_angle_key]
    goal_angle = dataframe[goal_angle_key]

    fig = go.Figure()
    fig.add_trace(go.Scatter(x=cur_angle.index, y=cur_angle.values, name="Current Angle"))
    fig.add_trace(go.Scatter(x=goal_angle.index, y=goal_angle.values, name="Goal Angle", mode="markers"))

    fig.write_html(os.path.join(output_directory, "go_to_angle.html"))

def main():
    directory = r"C:\Users\PJ\Documents\GitHub\gos_data\RapidReactRobotLogs"
    converted_file = os.path.join(directory, "FRC_20220402_203438.wpilog")

    df = load_log(converted_file)
    print("\n".join(sorted(list(df.keys()))))

    plot_go_to_angle(df, directory)


if __name__ == "__main__":
    # run with
    # python -m y2022.RapidReact.log_analyzer.make_plots
    main()
