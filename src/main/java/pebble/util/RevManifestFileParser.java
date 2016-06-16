package pebble.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class RevManifestFileParser {

    public static Map<String, String> parse(String filePath) {
        return parse(new File(filePath));
    }

    public static Map<String, String> parse(File file) {
        try {
            String content = Files.toString(file, Charset.forName("UTF-8"));
            return fromContent(content);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static Map<String, String> fromContent(String jsonContent){
        try {
            return new ObjectMapper().readValue(jsonContent, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
