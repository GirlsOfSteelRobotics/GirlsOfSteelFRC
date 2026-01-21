package com.gos.rebuilt;


//imports
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;

import java.util.ArrayList;

public class shooterSimBalls {
    //variables lol
    private double initialvx;
    private double initialvy;
    private Rotation2d theta;
    private double speed;
    private final StructArrayPublisher<Translation3d> m_publisher;

    //constructor
    public shooterSimBalls() {
        initialvx = 0;
        initialvy = 0;
        theta = Rotation2d.fromDegrees(45);
        speed = Units.feetToMeters(20);

        m_publisher = NetworkTableInstance.getDefault().getStructArrayTopic("ShotPreview", Translation3d.struct).publish();
    }

    public double calculateDih (double t) {
        return initialvx+ (Math.cos(theta.getRadians())*speed)*t;
    }

    public double calculateDiv (double t) {
        return initialvy + (Math.sin(theta.getRadians())*speed)*t +(-9.8/2)*(Math.pow(t,2));
    }

    public double calcPythagThing (double a, double b){
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    public void calcDihDiv() {
        ArrayList <Translation3d> listOfTrans= new ArrayList<Translation3d>(10);
        int i = 0;
        for (double t = 0; t<=5; t+=.2) {
            Translation3d trans = new Translation3d(calculateDih(t), 0, calculateDiv(t));
            listOfTrans.add(trans);
            i++;
        }

        m_publisher.accept(listOfTrans.toArray(new Translation3d[]{}));
    }

}

