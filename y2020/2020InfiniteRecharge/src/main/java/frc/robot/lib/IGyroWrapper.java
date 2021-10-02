package frc.robot.lib;

public interface IGyroWrapper {

    void poll();

    double getYaw();

    void setYaw(double angle);
}
