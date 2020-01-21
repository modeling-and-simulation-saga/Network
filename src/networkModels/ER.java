package networkModels;

import java.io.IOException;
import network.*;

/**
 * Erdos-Renni random network
 *
 * @author tadaki
 */
public class ER extends AbstractNetwork {

    private final int n;//頂点の数
    private final int numEdges;//辺の数

    /**
     * コンストラクタ
     *
     * @param n
     * @param numEdges
     */
    public ER(int n, int numEdges) {
        super("ER(" + String.valueOf(n) + "," + String.valueOf(numEdges) + ")");
        this.n = n;
        this.numEdges = numEdges;
    }

    @Override
    public void createNetwork() {
        createNodes();
        createEdges();
    }

    /**
     * n 個の頂点を生成
     */
    private void createNodes() {
        for (int i = 0; i < n; i++) {
            addNode(new Node(String.valueOf(i)));
        }
    }

    /**
     * random に辺を生成
     */
    private void createEdges() {




        }
    }

    /**
     * サンプルを生成
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        int n = 100;
        int numEdges = 500;
        ER er = new ER(n, numEdges);
        er.createNetwork();
        NetworkFile.outputPajekData("er.net", er);
    }
}
