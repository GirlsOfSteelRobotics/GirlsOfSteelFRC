package javax.microedition.io;

import com.sun.squawk.microedition.io.FileConnection;

public class Connector {
    public static FileConnection open(String url) {
        return new FileConnection(url);
    }
}
