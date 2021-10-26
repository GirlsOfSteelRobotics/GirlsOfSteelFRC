package girlsofsteel.commands;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import com.sun.squawk.util.MathUtils;
import java.io.*;
import javax.microedition.io.Connector;

public class TuneI extends CommandBase {

    double setpoint;
    double bI;
    double eI;
    double interval;

    boolean right = false;
    boolean back = false;
    boolean left = false;

    double[][] rightRates;
    double[][] backRates;
    double[][] leftRates;
    int counter;
    int numRates;

    double[][] setpointDeviations;
    double[][] standardDeviations;

    public TuneI(double setpoint, double beginningI, double endingI) {
        this.setpoint = setpoint;
        bI = beginningI;
        eI = endingI;
        this.interval = 0.00001;
        requires(chassis);
        requires(drive);
        numRates = 50;//start number -- change if too long or too short
        int numIs = (int) MathUtils.round((eI-bI)/interval)+1;
        rightRates = new double[numIs][numRates];
        backRates = new double[numIs][numRates];
        leftRates = new double[numIs][numRates];
        counter = 0;
        setpointDeviations = new double[3][numIs];
        standardDeviations = new double[3][numIs];
    }//constructor

    // Called just before this Command runs the first time
    protected void initialize() {
        chassis.initEncoders();
        chassis.initRatePIDs();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        //set setpoint -- all 3 wheels at the same time
        chassis.setRightPIDRate(setpoint);
        chassis.setBackPIDRate(setpoint);
        chassis.setLeftPIDRate(setpoint);
        for(double i=bI; i<=eI; i+=interval){
            //set i values
            chassis.setRightPIDRateValues(0.0, i, 0.0);
            chassis.setBackPIDRateValues(0.0, i, 0.0);
            chassis.setLeftPIDRateValues(0.0, i, 0.0);
            //if within setpoint -> record rate for a given amount of time
            if(isWithinSetpoint() && counter < rightRates.length){
                for(int j=0; j<numRates; j++){
                    rightRates[counter][j] = chassis.getRightEncoderRate();
                    backRates[counter][j] = chassis.getBackEncoderRate();
                    leftRates[counter][j] = chassis.getLeftEncoderRate();
                }//end for
            }//end if
            //reset the within setpoint booleans
            right = false;
            back = false;
            left = false;
            counter++;
        }//end for

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

        //right Is & rates
        for(int i=0; i<rightRates.length; i++){//for every i value
            double sum = 0;
            for(int j=0; j<rightRates[i].length; j++){//for every rate
                //add to sum
                sum += rightRates[i][j];
            }//end rate for -- average
            //calculate mean
            double mean = sum/rightRates[i].length;
            double sumSquareDeviations = 0;
            for(int j=0; j<rightRates[i].length; j++){//for every rate
                //calculate square deviation
                sumSquareDeviations += ((rightRates[i][j]-mean) *
                        (rightRates[i][j]-mean));
            }//end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations/
                    (rightRates[i].length-1));
            setpointDeviations[0][i] = mean-setpoint;
            standardDeviations[0][i] = standardDeviation;
        }//end i for

        //back Is & rates
        for(int i=0; i<backRates.length; i++){//for every i value
            double sum = 0;
            for(int j=0; j<backRates[i].length; j++){//for every rate
                //add to sum
                sum += backRates[i][j];
            }//end rate for -- average
            //calculate mean
            double mean = sum/backRates[i].length;
            double sumSquareDeviations = 0;
            for(int j=0; j<backRates[i].length; j++){//for every rate
                //calculate square deviation
                sumSquareDeviations += ((backRates[i][j]-mean) *
                        (backRates[i][j]-mean));
            }//end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations/
                    (backRates[i].length-1));
            setpointDeviations[1][i] = mean - setpoint;
            standardDeviations[1][i] = standardDeviation;
        }//end i for

        //left Is & rates
        for(int i=0; i<leftRates.length; i++){//for every i value
            double sum = 0;
            for(int j=0; j<leftRates[i].length; j++){//for every rate
                //add to sum
                sum += leftRates[i][j];
            }//end rate for -- average
            //calculate mean
            double mean = sum/leftRates[i].length;
            double sumSquareDeviations = 0;
            for(int j=0; j<leftRates[i].length; j++){//for every rate
                //calculate square deviation
                sumSquareDeviations += ((leftRates[i][j]-mean) *
                        (leftRates[i][j]-mean));
            }//end rate for -- deviation
            //calculate standard deviation
            double standardDeviation = Math.sqrt(sumSquareDeviations/
                    (leftRates[i].length-1));
            setpointDeviations[2][i] = mean - setpoint;
            standardDeviations[2][i] = standardDeviation;
        }//end i for

        //for every i in averages
        String message = "";
        for (int i = 0; i < standardDeviations[0].length; i++) {
            message += ((i * interval) + bI) + " : " +
                    setpointDeviations[0][i] + " " + standardDeviations[0][i] +
                    " " + setpointDeviations[1][i] + " " +
                    standardDeviations[1][i] + " " + setpointDeviations[2][i] +
                    " " + standardDeviations[2][i] + "\n";
        }//end for

        //print message to a file
        String url = "file///Tune_I.txt";

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

    private boolean isWithinSetpoint(){
        if((setpoint-1.0) < chassis.getRightEncoderRate() &&
                chassis.getRightEncoderRate() > (setpoint+1.0)){
            right = true;
        }
        if((setpoint-1.0) < chassis.getBackEncoderRate() &&
                chassis.getBackEncoderRate() > (setpoint+1.0)){
            back = true;
        }
        if((setpoint-1.0) < chassis.getLeftEncoderRate() &&
                chassis.getLeftEncoderRate() > (setpoint+1.0)){
            left = true;
        }
        if(right && back && left)
            return true;
        return false;
    }

}
