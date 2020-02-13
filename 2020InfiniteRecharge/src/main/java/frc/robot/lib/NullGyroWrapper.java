package frc.robot.lib;

public class NullGyroWrapper implements IGyroWrapper {

    @Override
    public void poll() {

    }

    @Override
    public double getYaw() {
        return 0;
    }

    @Override
    public void setYaw(double angle) {

    }
}
