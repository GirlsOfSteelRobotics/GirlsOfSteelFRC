package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class Robot extends IterativeRobot {
	  
	CANTalon _talon = new CANTalon(1);	
	Joystick _joy = new Joystick(0);	
	StringBuilder _sb = new StringBuilder();
	int _loops = 0;
	boolean _lastButton1 = false;
	/** save the target position to servo to */
	double targetPositionRotations;
	
	public void robotInit() {
		/* lets grab the 360 degree position of the MagEncoder's absolute position */
		//int absolutePosition = _talon.getPulseWidthPosition() & 0xFFF; /* mask out the bottom12 bits, we don't care about the wrap arounds */
		int absolutePosition = 0;
        /* use the low level API to set the quad encoder signal */
        _talon.setEncPosition(absolutePosition);
        
        /* choose the sensor and sensor direction */
        // Competition/practice robots have Cross The Road Electronics encoders
        //_talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);  
        // Test board has QuadEncoder
        _talon.setFeedbackDevice(FeedbackDevice.QuadEncoder); 
        _talon.reverseSensor(true);
        _talon.configEncoderCodesPerRev(256); // if using FeedbackDevice.QuadEncoder, otherwise comment out
        //_talon.configPotentiometerTurns(XXX); // if using FeedbackDevice.AnalogEncoder or AnalogPot

        /* set the peak and nominal outputs, 12V means full */
        _talon.configNominalOutputVoltage(+0.0, -0.0);
        _talon.configPeakOutputVoltage(+12.0, -12.0);
        /* set the allowable closed-loop error,
         * Closed-Loop output will be neutral within this range.
         * See Table in Section 17.2.1 for native units per rotation. 
         */
        _talon.setAllowableClosedLoopErr(0); /* always servo */
        /* set closed loop gains in slot0 */
        _talon.setProfile(0);
        _talon.setF(0.0);
        _talon.setP(8.0);
        _talon.setI(0.003); 
        _talon.setD(0.08);    

	}
    /**
     * This function is called periodically during operator control
     */
    public void teleopInit(){
    	_talon.setEncPosition(0);
    	_talon.set(0);    	
    }
    
    public void teleopPeriodic() {
    	/* get gamepad axis */
    	double leftYstick = _joy.getAxis(AxisType.kY);
    	double motorOutput = _talon.getOutputVoltage() / _talon.getBusVoltage();
    	boolean button1 = _joy.getRawButton(1);
    	boolean button2 = _joy.getRawButton(2);
    	/* prepare line to print */
		_sb.append("\tout:");
		_sb.append(motorOutput);
        _sb.append("\tpos:");
        _sb.append(_talon.getPosition() );
        /* on button1 press enter closed-loop mode on target position */
        if(!_lastButton1 && button1) {
        	/* Position mode - button just pressed */
        	targetPositionRotations = leftYstick * 1.0; /* 50 Rotations in either direction */
        	_talon.changeControlMode(TalonControlMode.Position);
        	_talon.set(targetPositionRotations); /* 50 rotations in either direction */
        }
        /* on button2 just straight drive */
        if(button2) {
        	/* Percent voltage mode */
        	_talon.changeControlMode(TalonControlMode.PercentVbus);
        	_talon.set(leftYstick);
        }
        /* if Talon is in position closed-loop, print some more info */
        if( _talon.getControlMode() == TalonControlMode.Position) {
        	/* append more signals to print when in speed mode. */
        	_sb.append("\terrNative:");
        	_sb.append(_talon.getClosedLoopError());
        	_sb.append("\ttrg:");
        	_sb.append(targetPositionRotations);
        }
        /* print every ten loops, printing too much too fast is generally bad for performance */ 
        if(++_loops >= 10) {
        	_loops = 0;
        	System.out.println(_sb.toString());
        }
        _sb.setLength(0);
        /* save button state for on press detect */
        _lastButton1 = button1;
    }
}
