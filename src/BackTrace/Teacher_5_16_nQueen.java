package BackTrace;

/**
 * @author QiangQin
 * * @date 2021/6/20
 */

//class NQueen {
//    int n;//皇后个数
//    int[] x;//一维数组映射成二维(用下标表示第几行;数组值 对应那一行第几个放了皇后)
//    int sum;//皇后不冲突的 总放法
//
//    //构造函数
//    public NQueen(int ix) {
//        n = ix;//皇后个数
//        x = new int[ix];
//        sum = 0;
//    }
//
//    public int nQueen() {
//        BackTrace(0);
//        return sum;
//    }
//
//    //输出棋盘(@代表皇后,#代表空)
//    public void PrintQ() {
//        for (int i = 0; i < n; ++i) {
//            for (int j = 0; j < n; ++j) {
//                // 此位置是第i行第j列
//                if (x[i] == j) {//表明此处有皇后
//                    System.out.print('@');//  注意@字符的表示; printf 按指定格式输出
//                } else {
//                    System.out.print('#');
//                }
//            }
//            System.out.println();//输出完一行后换行
//        }
//        System.out.println();//输出完一种可行的方式后换行
//    }
////   对照一维数组映射成二维图
//        private boolean place ( int k){//说明已经一行一行的放到了k行
//            // 因为皇后不在同一行,避免了行冲突
//            for (int i = 0; i < k; i++) {// 判断k行新放的这个和以前行的冲突不
//                if (x[i] == x[k] || (k - i) == Math.abs(x[k] - x[i])) { // 列冲突  对角线冲突
//                    return false;
//                }
//            }
//            return true;
//        }
//        //递归
//        private void BackTrace ( int t){
//            //写程序注意边界问题和特殊情况处理
//            if (t >= n){ //数组下标t对应行数
//                sum++;//如果一直没冲突的递归过了0...i-1行,表示这是一种可行的安置方式
//                PrintQ();//搜到后立即输出,不然继续往下搜索就找不到了
//            }
//            else {
//                for (int i = 0; i < n; ++i) {//每一行都有n个位置可以放皇后
//                    x[t] = i;//即皇后放在第t行的i位置
//                    if (place(t)) {//如果该位置和之前的不冲突,就可以向下继续探索怎么放
//                        BackTrace(t + 1);//递归
//                    }
//                }
//            }
//        }
//
//    }

//===========================非递归==============================================
class NQueen {
    int n;//皇后个数
    int[] x;//一维数组映射成二维(用下标表示第几行;数组值 对应那一行第几个放了皇后)
    int sum;//皇后不冲突的 总放法

    //构造函数
    public NQueen(int nx) {
        n = nx;//皇后个数
        x = new int[nx + 1];//0下标不用，行数、列数从1开始符合自觉（对应图）
        sum = 0;
    }

    //输出棋盘(@代表皇后,#代表空)
    public void PrintQ() {
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                // 此位置是第i行第j列
                if (x[i] == j) {//表明此处有皇后
                    System.out.print('@');//  注意@字符的表示; printf 按指定格式输出
                } else {
                    System.out.print('#');
                }
            }
            System.out.println();//输出完一行后换行
        }
        System.out.println();//输出完一种可行的方式后换行
    }

    //剪枝函数
    private boolean place(int k) {//新皇后想放到第k行
        boolean res = true;//result
        // 因为皇后不在同一行,避免了行冲突；（对照映射图，注意0行不用）
        for (int i = 1; i < k; i++) {// 判断k行新放的这个和以前行的皇后冲突不
            if (x[i] == x[k] || (k - i) == Math.abs(x[k] - x[i])) { // 列冲突  对角线冲突（ k-i即行数相减 一定为正 ）
                res = false;
                break;
            }
        }
        return res;
    }

    /*
      非递归(类比NiceGet_Subset)
      回溯法（由于palce减枝策略，不用穷举所有可能）
     n行n列，皇后个数为n
     其中x[i]是所放的列数  i是摆放的行数；1.。。4是合法位置；0代表皇后未进入；5代表皇后已出来
    */
    public int BackTrack() {
        int i = 1;//表示从第一行开始放
        x[i] = 0;//表示皇后还没有进入该行摆放；
        while (i > 0) {
            x[i] += 1;//表示想要前进一个位置放
            while (x[i] <= n && !place(i)) {   //直到x[i]越界 或者 摆放合法而跳出while
                x[i] += 1; //列数（即x[i]）在范围内 却 和前面皇后冲突时，就继续试探该行下一个位置
            }

            if (x[i] <= n) {//表示摆放的列数是合法的。
                if (i == n) {//放完了所有的行没有冲突
                    sum += 1;
                    PrintQ();
                } else {//即此时i<n
                    ++i;//去下一行放皇后
                    x[i] = 0;//表示准备进入棋盘1列位置
                }
            } else {//即当皇后从这一行移出了界限时
                --i;//重新探索上一行皇后的下一个位置；
                   // 当探索到每一行的x[i]都等于n（即探索完了所有可能性）,就会不断--i，退出 大while
            }
        }
        return sum;
    }
}

public class Teacher_5_16_nQueen {
    public static void main(String[] args) {
        NQueen q = new NQueen(4);//简化为4后问题
        int sum = q.BackTrack();
        System.out.println(sum);
//            q.PrintQ();
            /*
            会输出
                ###@
                ###@
                ###@
                ###@
             因为遍历完了所有方格的所有方式 然后退出了. 在最后时执行了x[t] = i;
            */
    }
}


