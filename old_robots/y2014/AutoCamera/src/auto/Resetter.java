/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.gui.elements.BooleanBox;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.smartdashboard.types.DataType;
import java.awt.Color;

/**
 *
 * @author Heather
 */
public class Resetter extends Widget{
    
     public static final DataType[] TYPES = { DataType.BOOLEAN };
     public static final String NAME = "Resetter Thing";
     private int count=0;

    @Override
    public void setValue(Object o) {
        Boolean value = (Boolean) o;
        if(value==true && count>0){
            System.exit(0);
        } else if (value==true && count==0){
            Robot.getTable().putBoolean(getFieldName(), false);
        }
        count++;
    }

    @Override
    public void init() {
        setBackground(Color.BLUE);
        setOpaque(true);       
    }

    @Override
    public void propertyChanged(Property prprt) {
    }
    
}
