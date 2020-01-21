package network;

import java.util.List;
import myLib.utils.Utils;

/**
 * 頂点のクラス
 *
 * @author tadaki
 */
public class Node {

    private final String label;//ラベル
    private final List<Edge> edges;//頂点に連結した辺のリスト

    /**
     * コンストラクタ
     *
     * @param label
     */
    public Node(String label) {
        this.label = label;
        edges = Utils.createList();
    }

    /**
     * 隣接頂点のリストを返す
     *
     * @return
     */
    public List<Node> neighbours() {
        List<Node> list = Utils.createList();
        edges.stream().map((edge) -> edge.getEnd(this)).
                forEach((node) -> {
                    list.add(node);
                });
        return list;
    }

    /**
     * 指定した頂点が隣かを判定
     *
     * @param node
     * @return
     */
    public boolean isNeighbour(Node node) {
        for (Edge edge : edges) {
            if (edge.hasEnd(node)) {
                return true;
            }
        }
        return false;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public String getLabel() {
        return label;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return label;
    }
}
