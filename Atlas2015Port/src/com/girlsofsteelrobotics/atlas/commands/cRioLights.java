///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package girlsofsteel.commands;
//
//import edu.wpi.first.wpilibj.SerialPort;
//import edu.wpi.first.wpilibj.SerialPort.WriteBufferMode.kFlushOnAccess;
//import edu.wpi.first.wpilibj.visa.VisaException;
//
///**
// *
// * @author Abby
// */
//public class cRioLights {
//
//    private final SerialPort ledSerial;
//
//    public cRioLights() throws VisaException {
//            ledSerial = new SerialPort(9600);
//            ledSerial.setWriteBufferMode(kFlushOnAccess);
//        //And then something like this to write out a character whenever you want the 
////lights to change:
//        byte[] msg = {'r'};
//
//        ledSerial.write(msg, 1);
//    }
//}
