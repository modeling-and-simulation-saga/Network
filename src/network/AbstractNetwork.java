package network;

import java.io.IOException;
import java.util.List;
import myLib.utils.Utils;

/**
 *
 * @author tadaki
 */
abstract public class AbstractNetwork {

    private final String label;
    private int numNode = 0;
    protected final List<Node> nodes;

    /**
     * コンストラクタ
     *
     * @param label
     */
    public AbstractNetwork(String label) {
        this.label = label;
        nodes = Utils.createList();
    }

    /**
     * Networkに頂点を追加
     *
     * @param node
     */
    public void addNode(Node node) {
        if (!nodes.contains(node)) {//nodeが新規の場合
            if (nodes.add(node)) {
                numNode++;
            }
        }
    }

    /**
     * 二つの頂点を結ぶ辺を追加
     *
     * @param n1
     * @param n2
     * @return
     */
    public Edge connectNodes(Node n1, Node n2) {
        //labelの生成
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(n1.getLabel()).append(",");
        sb.append(n2.getLabel()).append(")");
        return connectNodes(n1, n2, sb.toString());
    }

    /**
     * 二つの頂点を結ぶ辺を追加
     *
     * @param n1
     * @param n2
     * @param label
     * @return
     */
    public Edge connectNodes(Node n1, Node n2, String label) {
        if (nodes.contains(n1) && nodes.contains(n2)) {//n1とn2が存在している
            Edge edge = new Edge(label);//新しい辺を生成
            //edgeの両端にn1とn2を追加
            edge.addEnd(n1);
            edge.addEnd(n2);
            //n1に接続する辺としてedgeを追加
            n1.addEdge(edge);
            //n2に接続する辺としてedgeを追加
            n2.addEdge(edge);
            return edge;
        }
        return null;
    }

    /**
     * 実際にNetworkを構築する抽象メソッド
     *
     */
    abstract public void createNetwork();

    // Getters and Setters
    public List<Edge> getEdges(Node node) {
        if (nodes.contains(node)) {
            return node.getEdges();
        }
        return null;
    }

    public String getLabel() {
        return label;
    }

    public int getNumNode() {
        return numNode;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    static public void main(String args[]) throws IOException {
        //抽象クラスのインスタンスを生成
        AbstractNetwork network = new AbstractNetwork("testNetwork") {
            //実装の無いメソッドを具体的に記述
            @Override
            public void createNetwork() {
                int n = 3;
                for (int i = 0; i < n; i++) {
                    addNode(new Node(String.valueOf(i)));
                }
                connectNodes(nodes.get(0), nodes.get(1));
                connectNodes(nodes.get(1), nodes.get(2));
                connectNodes(nodes.get(2), nodes.get(0));
            }
        };
        network.createNetwork();
        //pajek用データを出力
        NetworkFile.outputPajekData(network.getLabel() + ".net", network);
    }
}
