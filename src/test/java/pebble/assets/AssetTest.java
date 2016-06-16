package pebble.assets;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.StringLoader;
import org.junit.Test;
import pebble.asset.PebbleAssetExtensionBuilder;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class AssetTest {

    @Test
    public void testMappedAsset() throws PebbleException, IOException {
        String template = "<script src=\"{{requireAsset('js/a.js')}}\"></script>";

        PebbleEngine engine = engine("http://assets.example.com", "assets");
        String result = evaluate(template, engine);
        assertEquals(result,  "<script src=\"http://assets.example.com/assets/js/a.1122.js\"></script>");

        engine = engine("http://assets.example.com", null);
        result = evaluate(template, engine);
        assertEquals(result,  "<script src=\"http://assets.example.com/js/a.1122.js\"></script>");

        engine = engine(null, null);
        result = evaluate(template, engine);
        assertEquals(result,  "<script src=\"/js/a.1122.js\"></script>");

        engine = engine(null, "assets");
        result = evaluate(template, engine);
        assertEquals(result,  "<script src=\"/assets/js/a.1122.js\"></script>");

    }

    @Test
    public void testUnMappedAsset() throws PebbleException, IOException {
        String template = "<script src=\"{{requireAsset('b.js')}}\"></script>";

        PebbleEngine engine = engine("http://assets.example.com", "assets");
        String result = evaluate(template, engine);
        assertEquals(result,  "<script src=\"http://assets.example.com/assets/b.js\"></script>");

        engine = engine("http://assets.example.com", null);
        result = evaluate(template, engine);
        assertEquals(result,  "<script src=\"http://assets.example.com/b.js\"></script>");

        engine = engine(null, null);
        result = evaluate(template, engine);
        assertEquals(result,  "<script src=\"/b.js\"></script>");

        engine = engine(null, "assets");
        result = evaluate(template, engine);
        assertEquals(result,  "<script src=\"/assets/b.js\"></script>");
    }


    private String evaluate(String template, PebbleEngine engine) throws PebbleException, IOException {
        StringWriter writer = new StringWriter();
        engine.getTemplate(template).evaluate(writer);
        return writer.toString();
    }

    private PebbleEngine engine(String host){
        return engine(host, null);
    }

    private PebbleEngine engine(String host, String base) {
        PebbleEngine.Builder builder = new PebbleEngine.Builder();

        PebbleAssetExtensionBuilder extensionBuilder =
                new PebbleAssetExtensionBuilder();

        Map map = new HashMap<>();
        if (base != null){
            map.put(base + "/js/a.js", base+"/js/a.1122.js");
        } else {
            map.put("js/a.js", "js/a.1122.js");
        }

        builder.extension(
                extensionBuilder.assetsHost(host)
                        .basePath(base)
                        .revMap(map).build()
        ).loader(new StringLoader());

        return builder.build();
    }


}
