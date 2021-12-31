package girlsofsteel.commands;

import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.DriveFlag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings({"PMD.TooManyFields", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
public class TuneP extends CommandBase {

    //set to what TuneI gives
    private double m_rightI;
    private double m_backI;
    private double m_leftI;
    private final double m_setpoint;
    private final double m_bP;
    private final double m_eP;
    private final double m_interval;
    private int m_counter;
    private boolean m_done;
    private boolean m_right;
    private boolean m_back;
    private boolean m_left;
    private final int m_numRates;
    private double[][] m_rightRates;
    private double[][] m_backRates;
    private double[][] m_leftRates;
    private double m_zeroTime;
    private double[][] m_setpointTimes;
    private double[][] m_setpointDeviations;
    private double[][] m_standardDeviations;

    private final Chassis m_chassis;

    public TuneP(Chassis chassis, DriveFlag drive, double setpoint, double beginningP, double endingP) {
        m_chassis = chassis;
        this.m_setpoint = setpoint;
        m_bP = beginningP;
        m_eP = endingP;
        this.m_interval = 0.0001;
        requires(chassis);
        requires(drive);
        int numPs = (int) Math.round((m_eP - m_bP) / m_interval);
        m_counter = 0;
        m_setpointTimes = new double[3][numPs];
        m_numRates = 50;//start number -- change if too long or too short
        m_rightRates = new double[numPs][m_numRates];
        m_backRates = new double[numPs][m_numRates];
        m_leftRates = new double[numPs][m_numRates];
        m_setpointDeviations = new double[3][numPs];
        m_standardDeviations = new double[3][numPs];
    }//constructor

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.initRatePIDs();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        for (double p = m_bP; p < m_eP; p += m_interval) {
            if (m_counter < m_setpointTimes[0].length || m_counter < m_rightRates[0].length) {
                //set p & i values
                m_chassis.setRightPIDRateValues(p, m_rightI, 0.0);
                m_chassis.setBackPIDRateValues(p, m_backI, 0.0);
                m_chassis.setLeftPIDRateValues(p, m_leftI, 0.0);
                //set setpoint to 0 -- all 3 wheels at the same time
                m_chassis.setRightPIDRate(0.0);
                m_chassis.setBackPIDRate(0.0);
                m_chassis.setLeftPIDRate(0.0);
                //get the time
                while (!m_done) {
                    if (m_chassis.getRightEncoderRate() == 0.0) {
                        m_right = true;
                    }
                    if (m_chassis.getBackEncoderRate() == 0.0) {
                        m_back = true;
                    }
                    if (m_chassis.getLeftEncoderRate() == 0.0) {
                        m_left = true;
                    }
                    if (m_right && m_back && m_left) {
                        m_done = true;
                    }
                }
                m_done = false;
                m_right = false;
                m_back = false;
                m_left = false;
                m_zeroTime = System.currentTimeMillis();
                //set setpoints
                m_chassis.setRightPIDRate(m_setpoint);
                m_chassis.setBackPIDRate(m_setpoint);
                m_chassis.setLeftPIDRate(m_setpoint);
                while (!m_done) {//check until all 3 pids have equaled the setpoint
                    if ((m_setpoint - 0.001) < m_chassis.getRightEncoderRate()
                        && m_chassis.getRightEncoderRate() > (m_setpoint + 0.001)
                        && !m_right) {
                        double time = System.currentTimeMillis();
                        m_setpointTimes[0][m_counter] = time - m_zeroTime;
                        m_right = true;
                    }
                    if ((m_setpoint - 0.001) < m_chassis.getBackEncoderRate()
                        && m_chassis.getBackEncoderRate() > (m_setpoint + 0.001)
                        && !m_back) {
                        double time = System.currentTimeMillis();
                        m_setpointTimes[1][m_counter] = time - m_zeroTime;
                        m_back = true;
                    }
                    if ((m_setpoint - 0.001) < m_chassis.getLeftEncoderRate()
                        && m_chassis.getLeftEncoderRate() > (m_setpoint + 0.001)
                        && !m_left) {
                        double time = System.currentTimeMillis();
                        m_setpointTimes[2][m_counter] = time - m_zeroTime;
                        m_left = true;
                    }
                    if (m_right && m_back && m_left) {
                        m_done = true;
                    }
                }//end while
                for (int j = 0; j < m_numRates; j++) {
                    m_rightRates[m_counter][j] = m_chassis.getRightEncoderRate();
                    m_backRates[m_counter][j] = m_chassis.getBackEncoderRate();
                    m_leftRates[m_counter][j] = m_chassis.getLeftEncoderRate();
                }//end for
                m_done = false;
                m_right = false;
                m_back = false;
                m_left = false;
                m_counter++;
            }//end for
        }
    }//end execute

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    @SuppressWarnings({"PMD.UseStringBufferForStringAppends", "PMD.ExcessiveMethodLength", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity"})
    protected void end() {
        m_chassis.stopRatePIDs();
        m_chassis.stopEncoders();
        m_chassis.stopJags();

        //right Ps & rates
        for (int p = 0; p < m_rightRates.length; p++) {//for every p value
            double sum = 0;
            for (int j = 0; j < m_rightRates[p].length; j++) {//for every rate
                //add to sum
                sum += m_rightRates[p][j];
            }//end rate for -- average
            //calculate mean
            double mean = sum / m_rightRates[p].length;
            double sumSquareDeviations = 0;
            for (int j = 0; j < m_rightRates[p].length; j++) {//for every rate
                //calculate square deviation
                sumSquareDeviations += ((m_rightRates[p][j] - mean) *
                    (m_rightRates[p][j] - mean));
            }//end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations /
                (m_rightRates[p].length - 1));
            m_setpointDeviations[0][p] = mean - m_setpoint;
            m_standardDeviations[0][p] = standardDeviation;
        }//end p for

        //back Ps & rates
        for (int p = 0; p < m_backRates.length; p++) {//for every p value
            double sum = 0;
            for (int j = 0; j < m_backRates[p].length; j++) {//for every rate
                //add to sum
                sum += m_backRates[p][j];
            }//end rate for -- average
            //calculate mean
            double mean = sum / m_backRates[p].length;
            double sumSquareDeviations = 0;
            for (int j = 0; j < m_backRates[p].length; j++) {//for every rate
                //calculate square deviation
                sumSquareDeviations += ((m_backRates[p][j] - mean) *
                    (m_backRates[p][j] - mean));
            }//end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations /
                (m_backRates[p].length - 1));
            m_setpointDeviations[1][p] = mean - m_setpoint;
            m_standardDeviations[1][p] = standardDeviation;
        }//end p for

        //left Ps & rates
        for (int p = 0; p < m_leftRates.length; p++) {//for every p value
            double sum = 0;
            for (int j = 0; j < m_leftRates[p].length; j++) {//for every rate
                //add to sum
                sum += m_leftRates[p][j];
            }//end rate for -- average
            //calculate mean
            double mean = sum / m_leftRates[p].length;
            double sumSquareDeviations = 0;
            for (int j = 0; j < m_leftRates[p].length; j++) {//for every rate
                //calculate square deviation
                sumSquareDeviations += ((m_leftRates[p][j] - mean) *
                    (m_leftRates[p][j] - mean));
            }//end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations /
                (m_leftRates[p].length - 1));
            m_setpointDeviations[2][p] = mean - m_setpoint;
            m_standardDeviations[2][p] = standardDeviation;
        }//end p for

        //for every i in averages
        String message = "";
        for (int i = 0; i < m_standardDeviations[0].length; i++) {
            message += ((i * m_interval) + m_bP) + " : " +
                m_setpointDeviations[0][i] + " " + m_standardDeviations[0][i] +
                " " + m_setpointTimes[0][i] + " " + m_setpointDeviations[1][i] +
                " " + m_standardDeviations[1][i] + " " + m_setpointTimes[1][i] +
                " " + m_setpointDeviations[2][i] + " " +
                m_standardDeviations[2][i] + " " + m_setpointTimes[2][i] + "\n";
        }//end for

        //print message to a file
        String url = "file///Tune_P.txt";

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

    }//end end()

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
