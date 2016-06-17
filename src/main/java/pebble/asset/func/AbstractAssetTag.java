package pebble.asset.func;

import com.mitchellbosecke.pebble.node.RenderableNode;
import com.mitchellbosecke.pebble.node.TextNode;
import com.mitchellbosecke.pebble.tokenParser.AbstractTokenParser;
import pebble.asset.impl.RevAssetPathResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractAssetTag extends AbstractTokenParser {

    private RevAssetPathResolver resolver;

    public AbstractAssetTag(){}

    public AbstractAssetTag(RevAssetPathResolver resolver) {
        this.resolver = resolver;
    }

    public RenderableNode nodeForValues(Object value, int lineNumber) {
        if (value instanceof Collection) {
            List<RenderableNode> nodes = new ArrayList<>();
            for (Object obj : ((Collection) value)) {
                nodes.add(textNodeForAsset(obj.toString(), lineNumber));
            }
            return new ComposeNode(nodes);
        } else {
            return textNodeForAsset(value.toString(), lineNumber);
        }
    }

    private TextNode textNodeForAsset(String assertName, int lineNumber) {
        String path = assertPath(assertName);
        return singleTextNodeForAsset(path, lineNumber);
    }

    protected String assertPath(String assertName) {
        return this.resolver.resolvePath(assertName);
    }

    abstract TextNode singleTextNodeForAsset(String assetPath, int lineNumber);

}
