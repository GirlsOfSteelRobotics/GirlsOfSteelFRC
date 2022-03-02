package com.gos.power_up;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */


public class Blobs {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public static final int MIN_BLOBS_FOR_LINE = 5;
    public static final int GOAL_DISTANCE = 50;
    public static final int ERROR_THRESHOLD = 20;

    @SuppressWarnings("PMD.UnnecessaryConstructor")
    public Blobs() {
        /*
        ArrayList<Blob> randomBlobs = makeBlobs(100); //randomly generated blobs
        ArrayList<Blob> blobList = new ArrayList<Blob>(); //filled with testblobs (below)

        Blob testBlob1 = new Blob(1, 1);
        Blob testBlob2 = new Blob(2, 1);
        Blob testBlob3 = new Blob(3, 1);
        Blob testBlob4 = new Blob(4, 1);
        Blob testBlob5 = new Blob(5, 1);
        Blob testBlob6 = new Blob(6, 1);
        Blob testBlob7 = new Blob(8, 8);
        Blob testBlob8 = new Blob(7, 1);

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
        */
    }

    public List<Blob> makeBlobs(int size) {
        List<Blob> randomBlobs = new ArrayList<>();

        //Populate randomBlobs with Blobs with x- and y-coords between 0 and 10
        for (int i = 0; i < size; i++) {
            double x = Math.random() * 10;
            double y = Math.random() * 10;
            Blob blob1 = new Blob(x, y);
            randomBlobs.add(blob1);
        }
        return randomBlobs;
    }

    public double findSlope(Blob blob1, Blob blob2) {
        double numer = blob2.m_y - blob1.m_y;
        double denom = blob2.m_x - blob1.m_x;
        return numer / denom;
    }

    //Finds distance between line (defined by b1 and b2) and point (b3)
    public static double findDistance(Blob b1, Blob b2, Blob b3) {
        double x1 = b1.m_x;
        double x2 = b2.m_x;
        double x3 = b3.m_x;
        double y1 = b1.m_y;
        double y2 = b2.m_y;
        double y3 = b3.m_y;

        return (Math.abs(((y2 - y1) * x3) - ((x2 - x1) * y3) + ((x2 * y1) - (y2 * x1))))
            / (Math.sqrt(((y2 - y1) * (y2 - y1)) + ((x2 - x1) * (x2 - x1))));
    }

    //returns an Arraylist of blobs sorted by x-coord
    public static List<Blob> sortByX(List<Blob> unsortedBlobs) {
        //THIS DESTROYS THE ORIGINAL LIST
        List<Blob> sortedBlobs = new ArrayList<>();

        while (!unsortedBlobs.isEmpty()) {
            //Find blob with least x-coord, move it to the end of sortedBlobs
            Blob min = unsortedBlobs.get(0);
            for (int i = 1; i < unsortedBlobs.size(); i++) {
                if (unsortedBlobs.get(i).m_x < min.m_x) {
                    min = unsortedBlobs.get(i);
                }
            }
            sortedBlobs.add(min);
            unsortedBlobs.remove(min);
        }

        return sortedBlobs;

    }

    //Returns sorted list of blobs without outliers
    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.CognitiveComplexity"})
    public static List<Blob> golfSac(List<Blob> blobList) {
        Blob[] endpoints = new Blob[2];
        double minErr = -1;
        double minStd = -1;
        final double STD_THRESHOLD = 0.5; // NOPMD
        List<Blob> returnBlobs = new ArrayList<>();

        //Find best line between two blobs
        for (int i = 0; i < blobList.size() - 1; i++) {
            for (int j = i + 1; j < blobList.size(); j++) {
                double tempStd = 0;
                double tempErr = 0;
                for (int k = 0; k < blobList.size(); k++) {
                    if (k != i && k != j) { //if not one of the endpoints
                        double distance = findDistance(blobList.get(i), blobList.get(j), blobList.get(k));
                        tempStd += Math.pow(distance, 2);
                        tempErr += distance;
                    }
                }
                tempStd = Math.pow(tempStd / blobList.size(), 0.5);

                if (minErr == -1 || tempErr < minErr) {
                    endpoints[0] = blobList.get(i);
                    endpoints[1] = blobList.get(j);
                    minErr = tempErr;
                    minStd = tempStd;
                }
            }
        }

        //Add inliers to list of returnBlobs
        for (Blob blob : blobList) {
            double dev = findDistance(endpoints[0], endpoints[1], blob);
            if (dev < STD_THRESHOLD * minStd) {
                returnBlobs.add(blob);
            }
        }

        return sortByX(returnBlobs);
    }

    public double findAvgDistance(List<Blob> blobList) {
        //requires input List to be sorted
        double sumDistance = 0;
        for (int i = 0; i < blobList.size() - 1; i++) {
            double x = Math.pow(blobList.get(i + 1).m_x - blobList.get(i).m_x, 2);
            double y = Math.pow(blobList.get(i + 1).m_y - blobList.get(i).m_y, 2);
            sumDistance += Math.pow(x + y, 0.5);
        }
        return sumDistance / (blobList.size() - 1);
    }

    public double distanceBetweenBlobs() {
        List<Blob> blobList = PipelineListener.blobList;
        if (blobList.size() < MIN_BLOBS_FOR_LINE) {
            return -1;
        }

        List<Blob> line = golfSac(golfSac(blobList));
        if (line.size() >= MIN_BLOBS_FOR_LINE) {
            System.out.println("Blobs: Line found. Size = " + line.size());
            return findAvgDistance(line);
        } else {
            System.out.println("Blobs: Line not found!");
            return -1;
        }
    }
}
