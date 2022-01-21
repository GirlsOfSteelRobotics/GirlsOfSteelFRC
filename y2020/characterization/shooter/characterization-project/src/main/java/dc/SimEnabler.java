package dc;

import edu.wpi.first.hal.sim.DriverStationSim;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

public class SimEnabler implements Sendable {
  DriverStationSim sim = new DriverStationSim();

  public SimEnabler() {
    sim.setAutonomous(true);
  }

  public void setEnabled(boolean enabled) {
    sim.setEnabled(enabled);
    sim.notifyNewData();
    DriverStation.isNewControlData();
    while (DriverStation.isEnabled() != enabled) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException exception) {
        exception.printStackTrace();
      }
    }
  }

  @Override
  public String getName() {
    return "SimEnabler";
  }

  @Override
  public void setName(String name) {}

  @Override
  public String getSubsystem() {
    return "";
  }

  @Override
  public void setSubsystem(String subsystem) {}

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.addBooleanProperty("Enabled",
                               () -> DriverStation.isEnabled(),
                               enabled -> setEnabled(enabled));
  }
}
