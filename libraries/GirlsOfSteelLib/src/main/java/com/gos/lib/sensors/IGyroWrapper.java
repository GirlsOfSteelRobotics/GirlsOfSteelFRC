package com.gos.lib.sensors;

public interface IGyroWrapper {

    void poll();

    double getYaw();

    void setYaw(double angle);
}
