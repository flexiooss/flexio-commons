package io.flexio.docker.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class TUtils {
    static public String resourceAsString(String resourceName) throws IOException {
        try(
                InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
                Reader reader = new InputStreamReader(in)
        ) {
            StringBuilder result = new StringBuilder();
            char [] buffer = new char[1024];
            for(int read = reader.read(buffer) ; read != -1 ; read = reader.read(buffer)) {
                result.append(buffer, 0, read);
            }
            return result.toString();
        }
    }
}
