package ughImDying;

import java.util.ArrayList;

public class EliminateOutliers{


	public static ArrayList<Blob> makeBlobs(int size){
		ArrayList<Blob> bloblist = new ArrayList<Blob>();
		for(int i = 0; i < size; i++){
			double x = Math.random()*10+1;
			double y = Math.random()*10+1;
			Blob blob1 = new Blob(x, y, 1);
			bloblist.add(blob1);
		}
		return bloblist;
	}

	public static double findSlope(Blob[] bloblist){

		double slope = 0;
		for(int i = 1; i< 9; i++){
			double numer = (bloblist[i].y - bloblist[i+1].y);
			double denom = (bloblist[i].x - bloblist[i+1].x);
			slope = numer/denom;
		}

		return slope;

	}


	public static double findDistance(Blob b1, Blob b2, Blob b3){
		double x1 = b1.x;
		double x2 = b2.x;
		double x3 = b3.x;
		double y1 = b1.y;
		double y2 = b2.y;
		double y3 = b3.y;
		double dist = ((Math.abs(((y2 - y1)*x3) - ((x2 - x1)*y3) + ((x2*y1)-(y2*x1))))/(Math.sqrt(((y2 - y1)*(y2 - y1)) + ((x2 - x1)*(x2 - x1)))));
		
		
		return dist;
	}
	
	public static void printBlob(Blob b){
		System.out.println(b.x + ", " + b.y);
	}
	
	
	public static ArrayList<Blob> sortByX(ArrayList<Blob> blobList){
		
		ArrayList<Blob> returnBlobs = new ArrayList<Blob>();
		while(blobList.size() != 0){
			Blob min = blobList.get(0);
			for(int i =1; i < blobList.size(); i++){
				if (blobList.get(i).x < min.x){
					min = blobList.get(i);
				}
				
			}

			returnBlobs.add(min);
			blobList.remove(min);
			
		}
		
		return returnBlobs;
		
	}
	
	public static ArrayList<Blob> golfRansac(ArrayList<Blob> blobList){
		Blob[] goodBlobs = new Blob[2]; 
		double minerr = -1;
		double err = 0;
		double std = 0;
		ArrayList<Blob> returnBlobs = new ArrayList<Blob>();
		
		//Find best line between two blobs
		for(int i = 0; i < blobList.size() -1 ; i++){
			for(int j = i+1; j < blobList.size(); j++){
				std = 0; 
				err = 0;
				for(int k = 0; k < blobList.size(); k++){
					if(k != i && k != j)
					{
						std += Math.pow((findDistance(blobList.get(i), blobList.get(j), blobList.get(k))),2);
						err += findDistance(blobList.get(i), blobList.get(j), blobList.get(k));
					}
				}
				std = std/blobList.size();
				
				if (minerr == -1 || err < minerr){
					goodBlobs[0] = blobList.get(i);
					goodBlobs[1] = blobList.get(j);
					minerr = err;
				
				}
				
			}
		}
		
		//Get rid of outliers:
		std = Math.pow(std, 0.5);
		for(int i = 0; i < blobList.size(); i++){
			double dev = findDistance(goodBlobs[0], goodBlobs[1], blobList.get(i));
			if (dev<0.5*std){
				returnBlobs.add(blobList.get(i));
			}
			
		}
		
		//printBlob(goodBlobs[0]);
		//printBlob(goodBlobs[1]);
		//System.out.println();
		
		return sortByX(returnBlobs);
	}
	


public static void main(String[] args) {
	ArrayList<Blob> randomBlobs = makeBlobs(100);
//	System.out.println(findSlope());
	
	Blob testBlob1 = new Blob(1, 1, 1);
	Blob testBlob2 = new Blob(2, 1, 1);
	Blob testBlob3 = new Blob(3, 1, 1);
	Blob testBlob4 = new Blob(4, 1, 1);
	Blob testBlob5 = new Blob(3, 3, 1);
	Blob testBlob6 = new Blob(5, 3, 1);
	Blob testBlob7 = new Blob(7, 2, 1);
	Blob testBlob8 = new Blob(5, 1, 1);
//	
	
	Blob[] blobs = new Blob[8];
	ArrayList<Blob> blobList = new ArrayList<Blob>();
	blobList.add(testBlob1);
	blobList.add(testBlob2);
	blobList.add(testBlob3);
	blobList.add(testBlob4);
	blobList.add(testBlob5);
	blobList.add(testBlob6);
	blobList.add(testBlob7);
	blobList.add(testBlob8);
	blobs[0]= testBlob1;
	blobs[1]= testBlob2;
	blobs[2]= testBlob3;
	blobs[3]= testBlob4;
	blobs[4]= testBlob5;
	blobs[5]= testBlob6;
	blobs[6]= testBlob7;
	blobs[7]= testBlob8;
	
	
	for (int i = 0; i < randomBlobs.size(); i++)
	{
		printBlob(randomBlobs.get(i));
	}
	
	ArrayList<Blob> line = golfRansac(golfRansac(randomBlobs));
	System.out.println("Size = " + line.size());
	for (int i = 0; i < line.size(); i++)
	{
		printBlob(line.get(i));
	}

}

}

