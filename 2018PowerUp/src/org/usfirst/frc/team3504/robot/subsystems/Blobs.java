package org.usfirst.frc.team3504.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.Blob;
import org.usfirst.frc.team3504.robot.PipelineListener;

/**
 *
 */


public class Blobs extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static final int MIN_BLOBS_FOR_LINE = 5;
	public static final int GOAL_DISTANCE = 50;
	public static final int ERROR_THRESHOLD = 20;

	public Blobs() {
		ArrayList<Blob> randomBlobs = makeBlobs(100); //randomly generated blobs
		ArrayList<Blob> blobList = new ArrayList<Blob>(); //filled with testblobs (below)
		
		Blob testBlob1 = new Blob(1, 1);
		Blob testBlob2 = new Blob(2, 1);
		Blob testBlob3 = new Blob(2, 1);
		Blob testBlob4 = new Blob(2, 1);
		Blob testBlob5 = new Blob(2, 1);
		Blob testBlob6 = new Blob(2, 1);
		Blob testBlob7 = new Blob(2, 1);
		Blob testBlob8 = new Blob(2, 1);
		
		blobList.add(testBlob1);
		blobList.add(testBlob2);
		blobList.add(testBlob3);
		blobList.add(testBlob4);
		blobList.add(testBlob5);
		blobList.add(testBlob6);
		blobList.add(testBlob7);
		blobList.add(testBlob8);
		
		//Print arraylist of random blobs
		for (int i = 0; i < randomBlobs.size(); i++)
		{
			printBlob(randomBlobs.get(i));
		}
		
		//Eliminate outliers
		ArrayList<Blob> line = golfSac(golfSac(randomBlobs));
		
		//Print remaining blobs (inliers)
		System.out.println("Size = " + line.size());
		for (int i = 0; i < line.size(); i++)
		{
			printBlob(line.get(i));
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public ArrayList<Blob> makeBlobs(int size){
		ArrayList<Blob> randomBlobs = new ArrayList<Blob>();
		
		//Populate randomBlobs with Blobs with x- and y-coords between 0 and 10
		for(int i = 0; i < size; i++){
			double x = Math.random()*10;
			double y = Math.random()*10;
			Blob blob1 = new Blob(x, y);
			randomBlobs.add(blob1);
		}
		return randomBlobs;
	}
    
    public double findSlope(Blob blob1, Blob blob2){
		double numer = (blob2.y - blob1.y);
		double denom = (blob2.x - blob1.x);
		double slope = numer/denom;
		return slope;
	}
    
    //Finds distance between line (defined by b1 and b2) and point (b3)
    public double findDistance(Blob b1, Blob b2, Blob b3){
		double x1 = b1.x;
		double x2 = b2.x;
		double x3 = b3.x;
		double y1 = b1.y;
		double y2 = b2.y;
		double y3 = b3.y;
		double dist = ((Math.abs(((y2 - y1)*x3) - ((x2 - x1)*y3) + ((x2*y1)-(y2*x1))))
				/(Math.sqrt(((y2 - y1)*(y2 - y1)) + ((x2 - x1)*(x2 - x1)))));
		
		return dist;
	}
    
    public static void printBlob(Blob b){
		System.out.println(b.x + ", " + b.y);
	}
    
    //returns an Arraylist of blobs sorted by x-coord
	public ArrayList<Blob> sortByX(ArrayList<Blob> unsortedBlobs){
		//THIS DESTROYS THE ORIGINAL LIST
		ArrayList<Blob> sortedBlobs = new ArrayList<Blob>();
		
		while(unsortedBlobs.size() != 0){
			//Find blob with least x-coord, move it to the end of sortedBlobs
			Blob min = unsortedBlobs.get(0);
			for(int i =1; i < unsortedBlobs.size(); i++){
				if (unsortedBlobs.get(i).x < min.x){
					min = unsortedBlobs.get(i);
				}
			}
			sortedBlobs.add(min);
			unsortedBlobs.remove(min);
		}
		
		return sortedBlobs;
		
	}
	
	//Returns sorted list of blobs without outliers
	public ArrayList<Blob> golfSac(ArrayList<Blob> blobList){
		Blob[] endpoints = new Blob[2]; 
		double minErr = -1;
		double minStd = -1;
		final double STD_THRESHOLD = 0.5;
		ArrayList<Blob> returnBlobs = new ArrayList<Blob>();
		
		//Find best line between two blobs
		for(int i = 0; i < blobList.size() -1 ; i++){
			for(int j = i+1; j < blobList.size(); j++){
				double tempStd = 0; 
				double tempErr = 0;
				for(int k = 0; k < blobList.size(); k++){
					if(k != i && k != j) //if not one of the endpoints
					{
						double distance = findDistance(blobList.get(i), blobList.get(j), blobList.get(k));
						tempStd += Math.pow(distance, 2);
						tempErr += distance;
					}
				}
				tempStd = Math.pow((tempStd/blobList.size()), 0.5);
				
				if (minErr == -1 || tempErr < minErr){
					endpoints[0] = blobList.get(i);
					endpoints[1] = blobList.get(j);
					minErr = tempErr;
					minStd = tempStd;
				}
			}
		}
		
		//Add inliers to list of returnBlobs
		for(int i = 0; i < blobList.size(); i++){
			double dev = findDistance(endpoints[0], endpoints[1], blobList.get(i));
			if (dev < STD_THRESHOLD * minStd){
				returnBlobs.add(blobList.get(i));
			}
		}
		
		return sortByX(returnBlobs);
	}
	
	public double findAvgDistance(ArrayList<Blob> blobList)
	{
		//requires input ArrayList to be sorted
		double sumDistance = 0;
		for (int i = 0; i < blobList.size() - 1; i++)
		{
			double x = Math.pow(blobList.get(i+1).x - blobList.get(i).x, 2);
			double y = Math.pow(blobList.get(i+1).y - blobList.get(i).y, 2);
			sumDistance += Math.pow(x + y, 0.5);
		}
		return sumDistance / (blobList.size() - 1);
	}
	
	public double distanceBetweenBlobs()
	{
		ArrayList<Blob> blobList = PipelineListener.blobList;
		if (blobList.size() < MIN_BLOBS_FOR_LINE) return -1;
		
		ArrayList<Blob> line = golfSac(golfSac(blobList));
		if (line.size() >= MIN_BLOBS_FOR_LINE)
		{
			System.out.print("Blobs: Line found. Size = " + line.size());
			return findAvgDistance(line);
		}
		else return -1;
	}
}
