/*
 * SimpleGraphView.java
 *
 * Created on March 8, 2007, 7:49 PM; Updated May 29, 2007
 *
 * Copyright March 8, 2007 Grotto Networking
 */
package Platform.Util.Profiler.observercode.Diagram;

import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;


/**
 *
 * @author Dr. Greg M. Bernstein
 */
public class SimpleGraphView {

    OrderedSparseMultigraph<String, String> g;
    Layout<String, String> layout;
    BasicVisualizationServer<String, String> vv;
    int width=1024; int height=800;
    /** Creates a new instance of SimpleGraphView */
    public SimpleGraphView() {
        // Graph<V, E> where V is the type of the vertices and E is the type of the edges
        g = new OrderedSparseMultigraph<String, String>();
        // The Layout<V, E> is parameterized by the vertex and edge types
        layout = new KKLayout(g);
        layout.setSize(new Dimension( width,  height)); // sets the initial size of the layout space
        // The BasicVisualizationServer<V,E> is parameterized by the vertex and edge types
        vv = new BasicVisualizationServer<String, String>(layout);
        vv.setPreferredSize(new Dimension( width,  height)); //Sets the viewing area size

        // Setup up a new vertex to paint transformer...
        Transformer<String, Paint> vertexPaint = new Transformer<String, Paint>() {

            public Paint transform(String i) {
                return Color.GREEN;
            }
        };
        // Set up a new stroke Transformer for the edges
//        float dash[] = {10.0f};
//        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
//             BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
//        Transformer<String, Stroke> edgeStrokeTransformer =
//              new Transformer<String, Stroke>() {
//            public Stroke transform(String s) {
//                return edgeStroke;
//            }
//        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
//        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);



    }

    void Show() {
        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }

    void test() {
        // Add some vertices. From above we defined these to be type Integer.
        g.addVertex((String) "1");
        g.addVertex((String) "2");
        g.addVertex((String) "3");
        g.addVertex((String) "4");
        g.addVertex((String) "5");
        g.addVertex((String) "6");
        g.addVertex((String) "7");
        g.addVertex((String) "8");
        g.addVertex((String) "9");

        // Note that the default is for undirected edges, our Edges are Strings.
        g.addEdge("A", "1", "2"); // Note that Java 1.5 auto-boxes primitives
        g.addEdge("B", "5", "3");
        g.addEdge("C", "1", "7");
        g.addEdge("D", "2", "4");
        g.addEdge("E", "8", "4");
        g.addEdge("F", "2", "3");
        g.addEdge("G", "6", "1");
        g.addEdge("H", "2", "9");

        layout.initialize();
    }

    void test2() {
        g.addVertex((String) "10");
        g.addVertex((String) "11");
        g.addVertex((String) "12");

        g.addEdge("z", "10", "2");
        g.addEdge("y", "11", "3");
        g.addEdge("x", "12", "7");


        layout.initialize();
    }

    public static void main(String[] args) {
        SimpleGraphView sgv = new SimpleGraphView(); //We create our graph in here
        sgv.Show();

        sgv.test();
        sgv.test2();
    }
}
