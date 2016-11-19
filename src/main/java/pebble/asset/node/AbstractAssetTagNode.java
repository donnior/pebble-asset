package pebble.asset.node;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.NodeVisitor;
import com.mitchellbosecke.pebble.node.AbstractRenderableNode;
import com.mitchellbosecke.pebble.node.expression.Expression;
import com.mitchellbosecke.pebble.node.expression.MapExpression;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplateImpl;
import pebble.asset.RevAssetPathResolver;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

public abstract class AbstractAssetTagNode extends AbstractRenderableNode {

    private final Expression<?> sourcesExpression;

    private final MapExpression mapExpression;
    private final RevAssetPathResolver resolver;

    private static String[] booleanAttributes = new String[]{
            "allowFullscreen","async","autofocus","autoplay",
            "checked","compact","controls","declare","default","defaultChecked",
            "defaultMuted","defaultSelected","defer","disabled","draggable",
            "enabled","formNoValidate","hidden","imageSmoothingEnabled","indeterminate","isMap",
            "loop","multiple","muted","noHref","noResize","noShade","noValidate","noWrap",
            "open","pauseOnExit","readOnly","required","reversed",
            "selected","spellcheck","translate","trueSpeed","typeMustMatch"
    };

    public AbstractAssetTagNode(int lineNumber, Expression<?> sourcesExpression, MapExpression mapExpression, RevAssetPathResolver resolver) {
        super(lineNumber);
        this.sourcesExpression = sourcesExpression;
        this.mapExpression = mapExpression;
        this.resolver = resolver;
    }

    @Override
    public void render(PebbleTemplateImpl pebbleTemplate, Writer writer, EvaluationContext evaluationContext) throws PebbleException, IOException {

        Object source = sourcesExpression.evaluate(pebbleTemplate, evaluationContext);

        Map<?, ?> map = new HashMap<>();
        if (this.mapExpression != null) {
            Map tempMap = this.mapExpression.evaluate(pebbleTemplate, evaluationContext);
            map = new HashMap<>(tempMap);
        }

        if (source == null) {
            throw new PebbleException(
                    null,
                    String.format("The asset source in an asset tag evaluated to NULL.", source),
                    getLineNumber(), pebbleTemplate.getName());
        }

        if (source instanceof Collection) {
            Iterator i = ((Collection)source).iterator();
            while (i.hasNext()) {
                writeNodeText(writer, i.next().toString(), map);
                writer.write("\n");
            }
        } else {
            writeNodeText(writer, source, map);
        }

    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    protected void writeNodeText(Writer writer, Object source, Map data) throws IOException {
        Map map = new HashMap(data);
        map.put("src", this.resolvedAssetPath(source.toString()));

        writer.write("<"+this.tagName());

        Iterator<Map.Entry> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            writer.write(" ");
            writeProp(writer, entry.getKey().toString(), entry.getValue());
        }
        writer.write(" ></");
        writer.write(this.tagName());
        writer.write(">");

    }

    protected String resolvedAssetPath(String source) {
        return this.resolver.resolvePath(source);
    }

    public abstract String tagName();


    protected String[] booleanAttributes(){
        return booleanAttributes;
    }

    protected boolean isBooleanAttribute(String key){
        for (String attr : this.booleanAttributes()) {
            if (key.equals(attr)){
                return true;
            }
        }
        return false;
    }

    protected void writeProp(Writer writer, String key, Object value) throws IOException {
        if (isBooleanAttribute(key)) {
            this.writeBooleanAttribute(writer, key, value);
        } else {
            this.writeNotBooleanAttribute(writer, key, value);
        }
    }

    private void writeBooleanAttribute(Writer writer, String key, Object value) throws IOException {
        if (isTrueValueForBooleanAttributeValue(key, value.toString())) {
            writer.write(key);
        }
    }

    private boolean isTrueValueForBooleanAttributeValue(String key, String value) {
        return key.equalsIgnoreCase(value) || "true".equalsIgnoreCase(value);
    }

    private void writeNotBooleanAttribute(Writer writer, String key, Object value) throws IOException {
        writer.write(key);
        writer.write("=");
        writer.write("'");
        writer.write(value.toString());
        writer.write("'");
    }
}

