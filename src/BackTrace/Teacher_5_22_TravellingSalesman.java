package BackTrace;

/**
 * 旅行售货员问题:
 * 某售货员要到若干城市去推销商品，已知各城市之间的路程(或旅费)。
 * 他要选定一条从驻地出发，经过每个城市一遍，最后回到驻地的路线，使总的路程（或总旅费）最小
 * <p>
 * 简单回路：图的顶点序列中，除了第一个顶点和最后一个顶点相同外，其余顶点不重复出现的回路。
 */
class Graph {
    int n;//顶点个数
    int c;//当前路径费用
    int cc;//最优路径费用
    int[] x;//当前路径
    int[] cx;//最优路径
    int[][] cost;//结点之间的花费

    //    初始化(v是结点个数)
    public Graph(int v) {
        n = v;
        c = 0;
        cc = Integer.MAX_VALUE;//最优花费是 无穷(即没有回路时，到不了)
        x = new int[n + 1];//0下标不用，下标有效范围是1.。。n，数组值对应结点的名字
        cx = new int[n + 1];
        cost = new int[n + 1][n + 1];
// 方法1：从文件读取图
//方法2：手动给出图的邻接矩阵（对应wps图）
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                cost[i][j] = Integer.MAX_VALUE;// 填成无穷大，即不通
            }
        }
        for (int i = 1; i <= n; ++i)
            cost[i][i] = 0;//即自己到自己
        //从1到其他结点
        cost[1][2] = cost[2][1] = 30;
        cost[1][3] = cost[3][1] = 6;
        cost[1][4] = cost[4][1] = 4;
        //从2到其他结点
        cost[2][3] = cost[3][2] = 5;
        cost[2][4] = cost[4][2] = 10;
        //从3到其他结点
        cost[3][4] = cost[4][3] = 20;
    }

    private static void Swap_Arr(int[] arr, int j, int k) {
        if (k != j) {
            int tmp = arr[j];
            arr[j] = arr[k];
            arr[k] = tmp;
        }
    }
/*
  利用全排列穷举所有可能
    方法1：借鉴NiceGet_Subset（非递归）利用br[]获得所有可能的排列
    方法2：利用perm(递归）得到全排列
    参考Teacher_3_20_Factoria_binSearch
*/

  /*
    因为1下标对应起始顶点，并不参与到 后续路径点的 全排列中去，所以传入的是起始节点的下一个结点的坐标，如2
    建议手动模拟加深理解
*/
    private void BackTrack(int i) {
        if (i == n) {
            //此时到了最后一个结点，若当前与前一个结点相通  且 与首结点相通  且  花销小于最优解时
            if (cost[x[i - 1]][x[i]] != Integer.MAX_VALUE  && cost[x[i]][x[1]] != Integer.MAX_VALUE  && (c+cost[x[i - 1]][x[i]]+cost[x[i]][x[1]])<cc) {
                cc=c+cost[x[i - 1]][x[i]]+cost[x[i]][x[1]];//更新最优解值cc
                for(int j = 1; j<=n;++j) {//更新最优解序列cx
                    cx[j] = x[j];//j是下标序列  x[j]是结点名字  x[]即路径
                }
            }
        } else {
//             注意j可等于n，因为要往前交换。例如：1 2 3 {4} 算完后，回退，i=3,j++后等于4，交换后为1 2 4 {3}
            for (int j = i; j <= n; ++j) { //x[i-1]是 这层BackTrack(i)的待全排列结点的前一起始节点（如1 {2，3,4}的1）
                //即x[i-1]到x[j]之间 有边相相联  且  加上该边权值后当前开销 比 最优开销小 时才进行操作。
                if (cost[x[i - 1]][x[j]] != Integer.MAX_VALUE  && c + cost[x[i - 1]][x[j]] < cc) {
                    Swap_Arr(x, j, i);//调整x序列，将j处字符固定到i位置,变成和i-1相联：如：1 2{3,4}的2和2交换，然后和1相邻
                    c+=cost[x[i-1]][x[i]];//因为已经把j固定到i了，和i-1相邻

                    BackTrack(i + 1);//递归，起点后移，以便得到当前首字母的全排列

                    c-=cost[x[i-1]][x[i]];
                    Swap_Arr(x, j, i);//恢复原样（等for里j++，再交换，变成：1 3{2，4} ； 1 4{2，3}）
                }
            }
        }
    }

//    把v结点作为起始结点，放在1位置
    public int minPath(int v) {
        if (v < 0 || v > n)
            return -1;
        /*把v结点作为起始结点，放在1位置
          从1出发,初始序列：1 2 3 4     从3出发：3 2 1 4*/
        for (int i = 1; i <= n; ++i) {
            x[i] = i;
        }
        Swap_Arr(x, 1, v);

        BackTrack(2);//因为1下标对应起始顶点，并不参与到 后续路径点的 全排列中去
        return cc;
    }


}

public class Teacher_5_22_TravellingSalesman {

    public static void main(String[] args) {
        Graph graph = new Graph(4);
        int minpath=graph.minPath(1);
        System.out.println(minpath);
    }

}
