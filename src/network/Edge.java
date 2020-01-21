package network;

import java.io.IOException;
import java.util.List;
import myLib.utils.Utils;

/**
 * 無向辺のクラス
 *
 * @author tadaki
 */
public class Edge {

    private String label;//ラベル
    private final List<Node> ends;//両端の頂点

    /**
     * コンストラクタ
     *
     * @param label
     */
    public Edge(String label) {
        this.label = label;
        ends = Utils.createList();
    }

    /**
     * add node to the edge
     *
     * @param node
     */
    public void addEnd(Node node) {
        if (ends.size() > 1) {
            throw new RuntimeException("No more node added to an edge");
        }
        ends.add(node);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public List<Node> getEnds() {
        return ends;
    }

    /**
     * get other node of specified one
     *
     * @param node
     * @return
     */
    public Node getEnd(Node node) {
        if (ends.size() == 2 && ends.contains(node)) {
            if (ends.get(0) == node) {
                return ends.get(1);
            }
            return ends.get(0);
        }
        return null;
    }

    /**
     * 指定した頂点が辺の一方に在るかを判定
     *
     * @param node
     * @return
     */
    public boolean hasEnd(Node node) {
        return ends.contains(node);
    }

}
