package girlsofsteel.objects;

import edu.wpi.first.wpilibj.util.SortedVector;

public class ShooterLookupTable {

    private static final double MAX_SHOOTER_VELOCITY = 41.0;

    //table sorting shooter experimental values calculator
    private  final SortedVector.Comparator m_comparator = new MapDoubleComparator();
    //Sorted array sorts greatest to least
    private final SortedVector m_list = new SortedVector(m_comparator);

    //enters shooter data into the function that calculates the velocity the
    //ball should be shot at
    public ShooterLookupTable() {
        //new data (4/20/12):

        m_list.addElement(new MapDouble(2.87,20.75));
        m_list.addElement(new MapDouble(3.175,21.3));
        m_list.addElement(new MapDouble(3.785,21.9));
        m_list.addElement(new MapDouble(4.394,23.7));
        m_list.addElement(new MapDouble(5.004,24.82));
        m_list.addElement(new MapDouble(5.613,26.7));
        m_list.addElement(new MapDouble(6.223,27.7));
        m_list.addElement(new MapDouble(6.68,28.8));
        m_list.addElement(new MapDouble(24.1,193.5));

        //old data:
//        list.addElement(new MapDouble(0.0, 0.0));
//        list.addElement(new MapDouble(2.2733, 20.15));
//        list.addElement(new MapDouble(2.4257,20.5));
//        list.addElement(new MapDouble(2.5781,21.25));
//        list.addElement(new MapDouble(2.7305,21.75));
//        list.addElement(new MapDouble(2.8829,22.0));
//        list.addElement(new MapDouble(3.0353,22.5));
//        list.addElement(new MapDouble(3.1877,23.15));
//        list.addElement(new MapDouble(3.3401,23.25));
//        list.addElement(new MapDouble(3.4925,23.4));
//        list.addElement(new MapDouble(3.6449,24.1));
//        list.addElement(new MapDouble(3.7973,24.525));
//        list.addElement(new MapDouble(3.9497,24.9));
//        list.addElement(new MapDouble(4.1021,25.14));
//        list.addElement(new MapDouble(4.2161,25.3));
//        list.addElement(new MapDouble(4.4101,25.6));
//        list.addElement(new MapDouble(4.5593,26.25));
//        list.addElement(new MapDouble(4.7117,26.8));
//        list.addElement(new MapDouble(4.8641,27.2));
//        list.addElement(new MapDouble(5.0165,27.6));
//        list.addElement(new MapDouble(5.1689,27.8));
//        list.addElement(new MapDouble(5.3213,28.1));
//        list.addElement(new MapDouble(5.4637,28.4));
    }

    /**
     * @param distance, needs to get here from the camera*-
     * @return
     */
    public double getVelocityFrTable(double distance) {

        if (m_list.size() == 0) {
            return 0;//ends the getVelocityFrTable method -> sends a velocity of 0
        }


        //Sorted array is sorted greatest to least
        //assigns the last value to the lowest point -> and the first to the last
        MapDouble lowest = (MapDouble) m_list.lastElement();
        MapDouble highest = (MapDouble) m_list.firstElement();

        //PLEASE make a test point SUPER close to the basket
        if (distance < lowest.getDistance()) {//if the distance is lower than the
            //lowest point -> then it will return the velocity of the lowest point
            //in the table
            //draws a line from the origin to the lowest point -> finds the velocity
            //based on the distance
            return interpolate(distance, 0, 0, lowest.getDistance(), lowest.getVelocity());
        } //ALSO PLEASE make a test point on the other side or as close to the bridge as possible
        else if (distance > highest.getDistance()) {//same as above, but for a
            //higher number of the highest point in the table
            //creates a line between the highest point and a value that we won't reach
            //returns the velocity on this line -> at the highest
//            return interpolate(distance, highest.getDistance(), highest.getVelocity(), highest.getDistance() + 5, MAX_SHOOTER_VELOCITY);
            return MAX_SHOOTER_VELOCITY;//if the distance is above anything in
            //the table -> shoot at the highest the velocity
        }

        int index = (int) Math.ceil(m_list.size() / 2.0);
        MapDouble currentValue;
        MapDouble currentLow = new MapDouble(0.0, 0.0);
        MapDouble currentMax = new MapDouble(0.0, 0.0);
        boolean end = false;

        //find the values above & below the distance you're looking for
        while (!end) {
            currentValue = ((MapDouble) m_list.elementAt(index));

            if (currentValue.getDistance() == currentLow.getDistance() || currentValue.getDistance() == currentMax.getDistance()) {
                end = true;
            }


            if (currentValue.getDistance() < distance) {
                currentLow = currentValue;
                index = index - 1;
            } else if (currentValue.getDistance() > distance) {
                currentMax = currentValue;
                index = index + 1;
            } else {
                return currentValue.getVelocity();
            }
        }

        //assigns the distances & velocities
        double distance1 = currentLow.getDistance();
        double velocity1 = currentLow.getVelocity();
        double distance2 = currentMax.getDistance();
        double velocity2 = currentMax.getVelocity();
        //finds the velocity needed based on the distance
        return interpolate(distance, distance1, velocity1, distance2, velocity2);
    }

    //uses the distance that the camera is at (distance)
    //and the 2 sets of data that the distance lies between (distance & velocity)
    //to find the velocity the shooter wheel should be set at
    private double interpolate(double distance, double distance1, double velocity1, double distance2, double velocity2) {
        double velocity;
        velocity = velocity1 + (velocity2 - velocity1) * ((distance - distance1) / (distance2 - distance1));
        return velocity;
    }
}
