package pebble.asset.parser;

import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.node.expression.MapExpression;
import pebble.asset.RevAssetPathResolver;
import pebble.asset.node.AbstractAssetTagNode;
import pebble.asset.node.ImageAssetTageNode;

public class ImageIncludeTagTokenParser extends AbstractAssetTagTokenParser {

    public ImageIncludeTagTokenParser(RevAssetPathResolver resolver) {
        super(resolver);
    }

    @Override
    public String getTag() {
        return "imageInclude";
    }

    @Override
    AbstractAssetTagNode tagNode(int lineNumber, Expression<?> expression, MapExpression mapExpression, RevAssetPathResolver resolver) {
        return new ImageAssetTageNode(lineNumber, expression, mapExpression, resolver);
    }
}
