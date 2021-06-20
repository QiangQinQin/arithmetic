package BackTrace;

/**
 * @author QiangQin
 * * @date 2021/6/20
 */

class NQueen{
    int n;//皇后个数
    int[] x;//一维数组映射成二维(用下标表示第几行;数组值 对应那一行第几个放了皇后)
    int sum;//总共的排列个数

    //构造函数
    public NQueen (int ix) {
        n = ix;
        x = new int[ix];
        sum = 0;
    }

    public int nQueen() {
        BackTrace(0);
        return sum;
    }

//   对照一维数组映射成二维图
    private boolean place(int  k) {//说明已经一行一行的放到了k行

// 因为皇后不在同一行,避免了行冲突
        for(int i=0;i<k;i++) {// 判断k行新放的这个和以前行的冲突不
           if(x[i]== x[k] || (k-i)==Math.abs(x[k]-x[i]) ) { // 列冲突  对角线冲突
                return false;
           }
        }
        return true;
    }
    //递归
    private void BackTrace(int t) {
        //注意边界问题和特殊情况处理
        if(t>=n)//数组下标t对应行数
            sum++;
        else{
            for(int i = 0; i<n;++i){//每一行都有n个位置可以放皇后
                x[t] = i;//即皇后放在第t行的i位置
                if(place(t)){//如果该位置和之前的不冲突,就可以向下继续探索怎么放
                    BackTrace(  t+1) ;//递归
                }
            }
        }
    }


    //非递归
}

public class Teacher_5_16_nQueen {
    public static void main(String[] args) {
        NQueen q = new NQueen(4);//简化为4后问题
        int sum=q.nQueen();
        System.out.println(sum);
    }
}


