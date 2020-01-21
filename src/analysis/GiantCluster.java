package analysis;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import myLib.utils.FileIO;
import myLib.utils.Utils;
import network.*;
import networkModels.ER;

/**
 *
 * @author tadaki
 */
public class GiantCluster {

    private final AbstractNetwork network;
    private List<Set<Node>> clusters;

    /**
     * 改行コード
     */
    public static final String NL = System.getProperty("line.separator");

    public GiantCluster(AbstractNetwork network) {
        this.network = network;
    }

    /**
     * clusterへの分割
     *
     * @return cluster数
     */
    public int findClusters() {
        clusters = Utils.createList();
        List<Node> nodes = Utils.createList();
        network.getNodes().stream().forEach(
                node -> nodes.add(node)
        );
        while (!nodes.isEmpty()) {
            Node start = nodes.get(0);
            Set<Node> checked = BFS(network, start);
            checked.stream().forEach(
                    n -> nodes.remove(n)
            );
            clusters.add(checked);
        }
        return clusters.size();
    }

    /**
     * 最大のclusterを求める
     *
     * @return
     */
    public Set<Node> findLargestCluster() {
        Set<Node> largest = Utils.createSet();
        for (Set<Node> set : clusters) {
            if (set.size() > largest.size()) {
                largest = set;
            }
        }
        return largest;
    }

    /**
     * 最大のclusterを大きさを求める
     *
     * @return
     */
    public int findLargestClusterSize() {
        return findLargestCluster().size();
    }

    /**
     * 幅優先探索
     *
     * @param bNetwork 対象となるグラフ
     * @param start 開始Node
     * @return 開始ノードから到達できる頂点の集合
     */
    public static Set<Node> BFS(AbstractNetwork bNetwork, Node start) {
        Set<Node> checked = Utils.createSet();
        Queue<Node> queue = new ConcurrentLinkedQueue<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Node v = queue.poll();
            List<Edge> edges = bNetwork.getEdges(v);
            if (!edges.isEmpty()) {
                edges.stream().map(//v に接する全ての辺
                        e -> e.getEnd(v)//v と反対側の頂点wを得る
                ).filter(//wがchecked にもqueueにも含まれていない
                        w -> (!checked.contains(w) && !queue.contains(w))
                ).forEach(//wをqueueへ加える
                        w -> queue.add(w));
            }
            checked.add(v);
        }
        return checked;
    }

    /**
     * クラスタの情報をファイルへ出力
     *
     * @param clusters
     * @param filename
     * @throws IOException
     */
    public static void showClusters(List<Set<Node>> clusters, String filename)
            throws IOException {
        try (BufferedWriter out = FileIO.openWriter(filename)) {
            String str = clusters2String(clusters);
            out.write(str);
            out.newLine();
        }
    }

    /**
     * クラスタの情報を文字列へ変換
     *
     * @param clusters
     * @return
     */
    public static String clusters2String(List<Set<Node>> clusters) {
        StringBuilder sb = new StringBuilder();
        for (Set<Node> set : clusters) {
            sb.append("{");
            set.stream().forEach(
                    n -> sb.append(n.toString()).append(",")
            );
            int last = sb.length() - 1;
            sb.deleteCharAt(last);
            sb.append("}");
            sb.append(NL);
        }
        return sb.toString();
    }

    /**
     * clusterを返す
     *
     * @return
     */
    public List<Set<Node>> getClusters() {
        return clusters;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        int n = 1000;
        int numStep = 200;
        try (BufferedWriter out = FileIO.openWriter("GiantClusterSize.txt")) {
            for (int i = 1; i < numStep; i++) {
                double p = (4. / n) * i / numStep;
                int m = (int) (p * n * (n - 1) / 2);
                ER er = new ER(n, m);
                er.createNetwork();
                analysis.GiantCluster gc = new analysis.GiantCluster(er);
                int k = gc.findClusters();
                int maxSize = gc.findLargestClusterSize();
                FileIO.writeSSV(out, p, maxSize, k, m);
            }
        }
    }

}
