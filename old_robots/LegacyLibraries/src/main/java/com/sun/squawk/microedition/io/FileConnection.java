package com.sun.squawk.microedition.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("PMD")
public class FileConnection implements AutoCloseable {
    private final String m_url;
    private FileInputStream m_inputStream;
    private FileOutputStream m_outputStream;

    public FileConnection(String url) {
        m_url = url;
    }

    public InputStream openDataInputStream() throws FileNotFoundException {
        m_inputStream =  new FileInputStream(m_url);
        return m_inputStream;
    }

    public InputStream openInputStream() throws FileNotFoundException {
        return openDataInputStream();
    }

    public OutputStream openDataOutputStream() throws FileNotFoundException {
        m_outputStream = new FileOutputStream(m_url);
        return m_outputStream;
    }

    public OutputStream openOutputStream() throws FileNotFoundException {
        return openDataOutputStream();
    }

    @Override
    public void close() throws IOException {
        if (m_inputStream != null) {
            m_inputStream.close();
        }

        if (m_outputStream != null) {
            m_outputStream.close();
        }
    }
}
