package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Chassis;
import com.gos.ultimate_ascent.subsystems.DriveFlag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
public class TuneI extends GosCommand {

    private final double m_setpoint;
    private final double m_bI;
    private final double m_eI;
    private final double m_interval;

    private boolean m_right;
    private boolean m_back;
    private boolean m_left;

    private double[][] m_rightRates;
    private double[][] m_backRates;
    private double[][] m_leftRates;
    private int m_counter;
    private final int m_numRates;

    private double[][] m_setpointDeviations;
    private double[][] m_standardDeviations;

    private final Chassis m_chassis;

    public TuneI(Chassis chassis, DriveFlag drive, double setpoint, double beginningI, double endingI) {
        m_chassis = chassis;
        this.m_setpoint = setpoint;
        m_bI = beginningI;
        m_eI = endingI;
        this.m_interval = 0.00001;
        addRequirements(chassis);
        addRequirements(drive);
        m_numRates = 50; //start number -- change if too long or too short
        int numIs = (int) Math.round((m_eI - m_bI) / m_interval) + 1;
        m_rightRates = new double[numIs][m_numRates];
        m_backRates = new double[numIs][m_numRates];
        m_leftRates = new double[numIs][m_numRates];
        m_counter = 0;
        m_setpointDeviations = new double[3][numIs];
        m_standardDeviations = new double[3][numIs];
    } //constructor

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initRatePIDs();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        //set setpoint -- all 3 wheels at the same time
        m_chassis.setRightPIDRate(m_setpoint);
        m_chassis.setBackPIDRate(m_setpoint);
        m_chassis.setLeftPIDRate(m_setpoint);
        for (double i = m_bI; i <= m_eI; i += m_interval) {
            //set i values
            m_chassis.setRightPIDRateValues(0.0, i, 0.0);
            m_chassis.setBackPIDRateValues(0.0, i, 0.0);
            m_chassis.setLeftPIDRateValues(0.0, i, 0.0);
            //if within setpoint -> record rate for a given amount of time
            if (isWithinSetpoint() && m_counter < m_rightRates.length) {
                for (int j = 0; j < m_numRates; j++) {
                    m_rightRates[m_counter][j] = m_chassis.getRightEncoderRate();
                    m_backRates[m_counter][j] = m_chassis.getBackEncoderRate();
                    m_leftRates[m_counter][j] = m_chassis.getLeftEncoderRate();
                } //end for
            } //end if
            //reset the within setpoint booleans
            m_right = false;
            m_back = false;
            m_left = false;
            m_counter++;
        } //end for

    } //end execute

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    @SuppressWarnings("PMD.UseStringBufferForStringAppends")
    public void end(boolean interrupted) {
        m_chassis.stopRatePIDs();
        m_chassis.stopEncoders();
        m_chassis.stopJags();

        //right Is & rates
        for (int i = 0; i < m_rightRates.length; i++) { //for every i value
            double sum = 0;
            for (int j = 0; j < m_rightRates[i].length; j++) { //for every rate
                //add to sum
                sum += m_rightRates[i][j];
            } //end rate for -- average
            //calculate mean
            double mean = sum / m_rightRates[i].length;
            double sumSquareDeviations = 0;
            for (int j = 0; j < m_rightRates[i].length; j++) { //for every rate
                //calculate square deviation
                sumSquareDeviations += ((m_rightRates[i][j] - mean) * (m_rightRates[i][j] - mean));
            } //end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations / (m_rightRates[i].length - 1));
            m_setpointDeviations[0][i] = mean - m_setpoint;
            m_standardDeviations[0][i] = standardDeviation;
        } //end i for

        //back Is & rates
        for (int i = 0; i < m_backRates.length; i++) { //for every i value
            double sum = 0;
            for (int j = 0; j < m_backRates[i].length; j++) { //for every rate
                //add to sum
                sum += m_backRates[i][j];
            } //end rate for -- average
            //calculate mean
            double mean = sum / m_backRates[i].length;
            double sumSquareDeviations = 0;
            for (int j = 0; j < m_backRates[i].length; j++) { //for every rate
                //calculate square deviation
                sumSquareDeviations += ((m_backRates[i][j] - mean) * (m_backRates[i][j] - mean));
            } //end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations / (m_backRates[i].length - 1));
            m_setpointDeviations[1][i] = mean - m_setpoint;
            m_standardDeviations[1][i] = standardDeviation;
        } //end i for

        //left Is & rates
        for (int i = 0; i < m_leftRates.length; i++) { //for every i value
            double sum = 0;
            for (int j = 0; j < m_leftRates[i].length; j++) { //for every rate
                //add to sum
                sum += m_leftRates[i][j];
            } //end rate for -- average
            //calculate mean
            double mean = sum / m_leftRates[i].length;
            double sumSquareDeviations = 0;
            for (int j = 0; j < m_leftRates[i].length; j++) { //for every rate
                //calculate square deviation
                sumSquareDeviations += ((m_leftRates[i][j] - mean) * (m_leftRates[i][j] - mean));
            } //end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations / (m_leftRates[i].length - 1));
            m_setpointDeviations[2][i] = mean - m_setpoint;
            m_standardDeviations[2][i] = standardDeviation;
        } //end i for

        //for every i in averages
        String message = "";
        for (int i = 0; i < m_standardDeviations[0].length; i++) {
            message += ((i * m_interval) + m_bI) + " : "
                + m_setpointDeviations[0][i] + " " + m_standardDeviations[0][i]
                + " " + m_setpointDeviations[1][i] + " "
                + m_standardDeviations[1][i] + " " + m_setpointDeviations[2][i]
                + " " + m_standardDeviations[2][i] + "\n";
        } //end for

        //print message to a file
        String url = "file///Tune_I.txt";

        String contents = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
            Files.newInputStream(Paths.get(url))))) {

            String line;
            while ((line = reader.readLine()) != null) {
                contents += line + "\n";
            }
        } catch (IOException ex) {
            ex.printStackTrace(); // NOPMD
        }

        try (OutputStreamWriter writer = new OutputStreamWriter(
            Files.newOutputStream(Paths.get(url)))) {
            writer.write(contents + message);
        } catch (IOException ex) {
            ex.printStackTrace(); // NOPMD
        }
    } //end end()



    private boolean isWithinSetpoint() {
        if ((m_setpoint - 1.0) < m_chassis.getRightEncoderRate() && m_chassis.getRightEncoderRate() > (m_setpoint + 1.0)) {
            m_right = true;
        }
        if ((m_setpoint - 1.0) < m_chassis.getBackEncoderRate() && m_chassis.getBackEncoderRate() > (m_setpoint + 1.0)) {
            m_back = true;
        }
        if ((m_setpoint - 1.0) < m_chassis.getLeftEncoderRate() && m_chassis.getLeftEncoderRate() > (m_setpoint + 1.0)) {
            m_left = true;
        }
        return m_right && m_back && m_left;
    }

}
