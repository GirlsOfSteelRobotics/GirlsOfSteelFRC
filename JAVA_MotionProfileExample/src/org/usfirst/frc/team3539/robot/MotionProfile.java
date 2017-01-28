package org.usfirst.frc.team3539.robot;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class MotionProfile {
	
	public ArrayList<ArrayList<Double>> points;
	public double profile[][];
	
	public MotionProfile(String filename){
		InputStream is = this.getClass().getResourceAsStream(filename);
		Scanner s = new Scanner(is);
		while(s.hasNext())
		{
			ArrayList<Double> arr = new ArrayList<Double>();
			arr.add(s.nextDouble()); //p
			arr.add(s.nextDouble()); //v
			arr.add(s.nextDouble()); //d
			
			points.add(arr);
		}
		s.close();
		
	}

}
