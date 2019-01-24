package frc.robot;
import java.nio.ByteBuffer;
import edu.wpi.first.wpilibj.I2C;

public class ColorSensor{
    private I2C sensor;
    public ColorSensor(){
        sensor = new I2C(I2C.Port.kOnboard, 0x39);
    }
    public byte[] readData(int address, int length){
        byte[] buffer = new byte[length];
        if (address == -1 && sensor.readOnly(buffer, length) || address != -1 && sensor.read(address, length, buffer))
        {
            buffer = null;
        }
        return buffer;
    }
    public int writeData(int address, byte[] buffer, int length){
        int bufferLength = address == -1? 
        length: length + 1;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bufferLength);
        if(address != -1){
            byteBuffer.put((byte)address);
            System.out.println("address != -1");
        }
        byteBuffer.put(buffer);
        System.out.println("address == -1");
        if(sensor.writeBulk(byteBuffer, length)){
            length = 0;
            System.out.println("length = 0");
        }
        return length;
    }
}