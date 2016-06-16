package pebble.assets;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.StringLoader;
import org.junit.Test;
import pebble.staticrev.PebbleAssetExtensionBuilder;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class AssetTest {

    @Test
    public void testMappedAsset() throws PebbleException, IOException {
        String template = "<script src=\"{{requireAsset('a.js')}}\"></script>";

        PebbleEngine engine = engine("http://assets.example.com");
        StringWriter writer = new StringWriter();
        engine.getTemplate(template).evaluate(writer);

        assertEquals(writer.toString(),
                "<script src=\"http://assets.example.com/assets/a.1122.js\"></script>");
    }

    @Test
    public void testUnMappedAsset() throws PebbleException, IOException {
        String template = "<script src=\"{{requireAsset('b.js')}}\"></script>";

        PebbleEngine engine = engine("http://assets.example.com");
        StringWriter writer = new StringWriter();
        engine.getTemplate(template).evaluate(writer);

        assertEquals(writer.toString(),
                "<script src=\"http://assets.example.com/assets/b.js\"></script>");
    }

    @Test
    public void testMappedWithoutHostAsset() throws PebbleException, IOException {
        String template = "<script src=\"{{requireAsset('a.js')}}\"></script>";

        PebbleEngine engine = engine(null);
        StringWriter writer = new StringWriter();
        engine.getTemplate(template).evaluate(writer);

        assertEquals(writer.toString(),
                "<script src=\"/assets/a.1122.js\"></script>");
    }

    private PebbleEngine engine(String host) {
        PebbleEngine.Builder builder = new PebbleEngine.Builder();

        PebbleAssetExtensionBuilder extensionBuilder =
                new PebbleAssetExtensionBuilder();

        Map map = new HashMap<>();
        map.put("assets/a.js", "assets/a.1122.js");

        builder.extension(
                extensionBuilder.assetsHost(host)
                        .basePath("assets")
                        .revMap(map).build()
        ).loader(new StringLoader());

        return builder.build();
    }


}
