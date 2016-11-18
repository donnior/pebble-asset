package pebble.asset.node;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.node.expression.MapExpression;
import pebble.asset.RevAssetPathResolver;

import java.io.IOException;
import java.io.Writer;

public class JavascriptAssetTagNode extends AbstractAssetTagNode {

    public JavascriptAssetTagNode(int lineNumber, Expression<?> sourcesExpression, MapExpression mapExpression, RevAssetPathResolver resolver) {
        super(lineNumber, sourcesExpression, mapExpression, resolver);
    }

    @Override
    public void writeProp(Writer writer, String key, Object value) throws IOException {
        if ("async".equals(key)) {
            if ("true".equals(value.toString()) || true == Boolean.valueOf(value.toString())) {
                writer.write("async");
            }
        } else {
            super.writeProp(writer, key, value);
        }
    }

    @Override
    public String tagName() {
        return "script";
    }
}
