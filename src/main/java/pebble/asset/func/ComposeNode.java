package pebble.asset.func;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.NodeVisitor;
import com.mitchellbosecke.pebble.node.Node;
import com.mitchellbosecke.pebble.node.RenderableNode;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplateImpl;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ComposeNode implements RenderableNode {

    private final List<RenderableNode> nodes;

    public ComposeNode(List<RenderableNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void render(PebbleTemplateImpl pebbleTemplate, Writer writer, EvaluationContext evaluationContext) throws PebbleException, IOException {
        for (RenderableNode node : this.nodes) {
            node.render(pebbleTemplate, writer, evaluationContext);
        }
    }

    @Override
    public void accept(NodeVisitor nodeVisitor) {
        for (Node node : nodes){
            node.accept(nodeVisitor);
        }
    }
}
