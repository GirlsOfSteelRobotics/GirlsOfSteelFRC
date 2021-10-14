package girlsofsteel.commands;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.util.MathUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.microedition.io.Connector;

public class TuneP extends CommandBase {

    //set to what TuneI gives
    double rightI = 0.0;
    double backI = 0.0;
    double leftI = 0.0;
    double setpoint;
    double bP;
    double eP;
    double interval;
    int counter;
    boolean done = false;
    boolean right = false;
    boolean back = false;
    boolean left = false;
    int numRates;
    double[][] rightRates;
    double[][] backRates;
    double[][] leftRates;
    double zeroTime;
    double[][] setpointTimes;
    double[][] setpointDeviations;
    double[][] standardDeviations;

    public TuneP(double setpoint, double beginningP, double endingP) {
        this.setpoint = setpoint;
        bP = beginningP;
        eP = endingP;
        this.interval = 0.0001;
        requires(chassis);
        requires(drive);
        int numPs = (int) MathUtils.round((eP - bP) / interval);
        counter = 0;
        setpointTimes = new double[3][numPs];
        numRates = 50;//start number -- change if too long or too short
        rightRates = new double[numPs][numRates];
        backRates = new double[numPs][numRates];
        leftRates = new double[numPs][numRates];
        setpointDeviations = new double[3][numPs];
        standardDeviations = new double[3][numPs];
    }//constructor

    // Called just before this Command runs the first time
    protected void initialize() {
        chassis.initEncoders();
        chassis.initRatePIDs();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        for (double p = bP; p < eP; p += interval) {
            if (counter < setpointTimes[0].length || counter < rightRates[0].length) {
                //set p & i values
                chassis.setRightPIDRateValues(p, rightI, 0.0);
                chassis.setBackPIDRateValues(p, backI, 0.0);
                chassis.setLeftPIDRateValues(p, leftI, 0.0);
                //set setpoint to 0 -- all 3 wheels at the same time
                chassis.setRightPIDRate(0.0);
                chassis.setBackPIDRate(0.0);
                chassis.setLeftPIDRate(0.0);
                //get the time
                while (!done) {
                    if (chassis.getRightEncoderRate() == 0.0) {
                        right = true;
                    }
                    if (chassis.getBackEncoderRate() == 0.0) {
                        back = true;
                    }
                    if (chassis.getLeftEncoderRate() == 0.0) {
                        left = true;
                    }
                    if (right && back && left) {
                        done = true;
                    }
                }
                done = false;
                right = false;
                back = false;
                left = false;
                zeroTime = System.currentTimeMillis();
                //set setpoints
                chassis.setRightPIDRate(setpoint);
                chassis.setBackPIDRate(setpoint);
                chassis.setLeftPIDRate(setpoint);
                while (!done) {//check until all 3 pids have equaled the setpoint
                    if ((setpoint - 0.001) < chassis.getRightEncoderRate()
                            && chassis.getRightEncoderRate() > (setpoint + 0.001)
                            && !right) {
                        double time = System.currentTimeMillis();
                        setpointTimes[0][counter] = time - zeroTime;
                        right = true;
                    }
                    if ((setpoint - 0.001) < chassis.getBackEncoderRate()
                            && chassis.getBackEncoderRate() > (setpoint + 0.001)
                            && !back) {
                        double time = System.currentTimeMillis();
                        setpointTimes[1][counter] = time - zeroTime;
                        back = true;
                    }
                    if ((setpoint - 0.001) < chassis.getLeftEncoderRate()
                            && chassis.getLeftEncoderRate() > (setpoint + 0.001)
                            && !left) {
                        double time = System.currentTimeMillis();
                        setpointTimes[2][counter] = time - zeroTime;
                        left = true;
                    }
                    if (right && back && left) {
                        done = true;
                    }
                }//end while
                for (int j = 0; j < numRates; j++) {
                    rightRates[counter][j] = chassis.getRightEncoderRate();
                    backRates[counter][j] = chassis.getBackEncoderRate();
                    leftRates[counter][j] = chassis.getLeftEncoderRate();
                }//end for
                done = false;
                right = false;
                back = false;
                left = false;
                counter++;
            }//end for
        }
    }//end execute

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        chassis.stopRatePIDs();
        chassis.stopEncoders();
        chassis.stopJags();

        //right Ps & rates
        for(int p=0; p<rightRates.length; p++){//for every p value
            double sum = 0;
            for(int j=0; j<rightRates[p].length; j++){//for every rate
                //add to sum
                sum += rightRates[p][j];
            }//end rate for -- average
            //calculate mean
            double mean = sum/rightRates[p].length;
            double sumSquareDeviations = 0;
            for(int j=0; j<rightRates[p].length; j++){//for every rate
                //calculate square deviation
                sumSquareDeviations += ((rightRates[p][j]-mean) * 
                        (rightRates[p][j]-mean));
            }//end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations/
                    (rightRates[p].length-1));
            setpointDeviations[0][p] = mean-setpoint;
            standardDeviations[0][p] = standardDeviation;
        }//end p for
        
        //back Ps & rates
        for(int p=0; p<backRates.length; p++){//for every p value
            double sum = 0;
            for(int j=0; j<backRates[p].length; j++){//for every rate
                //add to sum
                sum += backRates[p][j];
            }//end rate for -- average
            //calculate mean
            double mean = sum/backRates[p].length;
            double sumSquareDeviations = 0;
            for(int j=0; j<backRates[p].length; j++){//for every rate
                //calculate square deviation
                sumSquareDeviations += ((backRates[p][j]-mean) * 
                        (backRates[p][j]-mean));
            }//end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations/
                    (backRates[p].length-1));
            setpointDeviations[1][p] = mean - setpoint;
            standardDeviations[1][p] = standardDeviation;
        }//end p for
        
        //left Ps & rates
        for(int p=0; p<leftRates.length; p++){//for every p value
            double sum = 0;
            for(int j=0; j<leftRates[p].length; j++){//for every rate
                //add to sum
                sum += leftRates[p][j];
            }//end rate for -- average
            //calculate mean
            double mean = sum/leftRates[p].length;
            double sumSquareDeviations = 0;
            for(int j=0; j<leftRates[p].length; j++){//for every rate
                //calculate square deviation
                sumSquareDeviations += ((leftRates[p][j]-mean) * 
                        (leftRates[p][j]-mean));
            }//end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations/
                    (leftRates[p].length-1));
            setpointDeviations[2][p] = mean - setpoint;
            standardDeviations[2][p] = standardDeviation;
        }//end p for
        
        //for every i in averages
        String message = "";
        for (int i = 0; i < standardDeviations[0].length; i++) {
            message += ((i * interval) + bP) + " : " + 
                    setpointDeviations[0][i] + " " + standardDeviations[0][i] +
                    " " + setpointTimes[0][i] + " " + setpointDeviations[1][i] +
                    " " + standardDeviations[1][i] + " " + setpointTimes[1][i] +
                    " " + setpointDeviations[2][i] + " " + 
                    standardDeviations[2][i] + " " + setpointTimes[2][i] + "\n";
        }//end for
        
        //print message to a file
        String url = "file///Tune_P.txt";
        
        String contents = "";
        try{
            FileConnection c = (FileConnection) Connector.open(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    c.openDataInputStream()));
            String line;
            while((line = reader.readLine()) != null){
                contents += line + "\n";
            }
            c.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        try{
            FileConnection c = (FileConnection) Connector.open(url);
            OutputStreamWriter writer = new OutputStreamWriter(
                    c.openDataOutputStream());
            writer.write(contents + message);
            c.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
    }//end end()

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}