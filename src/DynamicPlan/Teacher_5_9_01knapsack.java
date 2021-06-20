package DynamicPlan;

import javax.swing.*;

public class Teacher_5_9_01knapsack {
// ============== 从前往后切==============
    //从i开始，一共有n个物品；j是背包所能承受的重量
    public static int Knapsack(int[] V, int[] W, int i, int n, int j) {
        //仅有一个物品时，能放就放了
        if (i == n) {
            return j >= W[n] ? V[n] : 0;
        } else {//不断递归，降低规模，然后逐层返回
            if (j < W[i]) //如果放不下
                return Knapsack(V, W, i + 1, n, j);
            else {//能放下的时候，看装和不装哪个比较好（存在重复求解的问题）
                int maxv1 = Knapsack(V, W, i + 1, n, j);
                int maxv2 = Knapsack(V, W, i + 1, n, j - W[i]) + V[i];//装入的时候，需要考虑可选物品为i+1到n,背包容量为j-W[i]可装的最大价值

                return maxv1 > maxv2 ? maxv1 : maxv2;
            }
        }
    }

  /*
      填表，避免重复计算
     (层层递归，到底，然后返回，最终退出的时候i=1,n=10)
     (好像只有递归到的东西才填了表)
     */
    public static int Knapsack(int[] V, int[] W, int i, int n, int j, int[][] m) {
        if (i == n) {
           m[i][j]= (j >= W[n]) ? V[n]:0;
        } else if (m[i][j] > 0) {
            return m[i][j];
        } else {
            if (j < W[i]) //如果放不下
                m[i][j] = Knapsack(V, W, i + 1, n, j,m);
            else {//能放下的时候，选装和不装的较大值为最终值
                int maxv1 = Knapsack(V, W, i + 1, n, j,m);
                int maxv2 = Knapsack(V, W, i + 1, n, j - W[i],m) + V[i];

                m[i][j] = maxv1 > maxv2 ? maxv1 : maxv2;
            }
        }
        return m[i][j];
    }



// ============== 从后往前 排除掉待选物品==============
    //可选物品为从1开始到i；  j是背包所能承受的重量
    public static int Knapsack(int[] V, int[] W, int i, int j) {
        //仅有一个物品时，能放就放了
        if (i == 1) {
            return j >= W[1] ? V[1] : 0;
        } else {//不断递归，降低规模，然后逐层返回
            if (j < W[i]) //如果放不下
                return Knapsack(V, W, i - 1, j);
            else {//能放下的时候，看装和不装哪个比较好（存在重复求解的问题）
                int maxv1 = Knapsack(V, W, i - 1, j);
                int maxv2 = Knapsack(V, W, i - 1,  j - W[i]) + V[i];

                return maxv1 > maxv2 ? maxv1 : maxv2;
            }
        }
    }

    /*
        i对应可选物品为从1，2，。。。i
         j代表背包剩余容量
        填表，避免重复计算
       (层层递归，到底，然后返回)
       (好像只有递归到的东西才填了表)
       */
    public static int Knapsack(int[] V, int[] W, int i, int j, int[][] m) {
        if (i == 1) {
            m[i][j]= (j >= W[1]) ? V[1]:0;
        } else if (m[i][j] > 0) {
            return m[i][j];
        } else {
            if (j < W[i]) //如果放不下
                m[i][j] = Knapsack(V, W, i - 1,j,m);
            else {//能放下的时候，选装和不装的较大值为最终值
                int maxv1 = Knapsack(V, W, i - 1, j,m);
                int maxv2 = Knapsack(V, W, i - 1, j - W[i],m) + V[i];

                m[i][j] = maxv1 > maxv2 ? maxv1 : maxv2;
            }
        }
        return m[i][j];
    }
    /*
        非递归，
        从前往后一层一层的填表，然后输出最后的结果
        要会手动填
    * */
    public static int Knapsack(int[] V, int[] W,int c, int[][] m) {
        int n=V.length-1;//V.length是第一维的长度（0下标不算物品），n即对应物品的数量
        for(int j=1;j<=c;++j){//c是背包容量
            m[1][j]= (j>=W[1])?V[1]:0;//初始化 当背包不同容量时能不能放第一个物品的情况
        }

        for(int i=2;i<=n;i++){//n是可选的物品数量，对应填表每一行都要重新刷新填入的值
            for(int j=1;j<=c;++j){
                if(j<W[i]){//即背包容量放不下时
                    m[i][j]=m[i-1][j];
                }else{
                   /*
                    能放下时，根据 放还是不放 哪个价值大来决定
                    求后面的最优值，用到了前面的结果：如m[i-1][j-W[i]]
                    如果放了当前的，和 没放当前时的最优做法 价值一样，仍然选择不放当前的
                    */
                    m[i][j]=Math.max(m[i-1][j] , m[i-1][j-W[i]] + V[i]);
                }
            }
        }
        return m[n][c];//对应图的右下角值
    }

    /*
     得到背包到底放进去了哪几个
     m是填了最大价值的表   w是物品重量  c是背包容量  x是 是否放入的标记数组
 * */
    private static void TraceBack(int[][] m, int[] w, int c, int[] x) {
        int i=w.length-1;//即最后一个物品的下标
        //从后往前推，不断的更新  可选物品范围i 和 背包容量c，发现值不同时，说明放入了该物品

        for(;i>1;--i){//因为i=1时，i-1=0，无意义，所以i>1
            if(m[i][c]!=m[i-1][c]){
 //同一列的情况下，i行和上一行值不一样，说明放入了i这个物品。
 // 因为表是从上往下填的，这一行与上一行情况上唯一的不同，就是物品选择范围大了，而正是这个多出来的可选物品 导致了值的改变
               x[i]=1;
               c=c-w[i];
            }
        }

        if(m[1][c]!=0){//单独处理第一个物品
            x[1]=1;
        }
    }
//==============================================
    private static void Print_Ar(int[][] m) {
        int row = m.length;
        int col = m[0].length;
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                System.out.printf("%5d", m[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void Print_Goods(int[] x,int[] w,int[] v ) {
        for (int i = 1; i < x.length; ++i) {
          if(x[i]==1){
              System.out.printf("放入了第"+i+"个物品，其重量为："+w[i]+"其价值为："+v[i]);
              System.out.println();
          }
        }
        System.out.println();
    }


    public static void main(String[] args) {
        int n = 5;//物品数量
        int c = 10;//背包所能承受的容量

        int[] w = {0, 2, 2, 6, 5, 4};//物品重量，相当于0下标没放东西
        // 下标  0 1 2 3 4 5
        int[] v = {0, 6, 3, 5, 4, 6};//物品价值
        int[] x={0,0,0,0,0,0};//标记n个物品有没有放进背包，0号下标不算，1表示放进去了

        int[][] m = new int[n + 1][c + 1];//填表，以避免重复计算（(0行0列不算数，m[i][j]表示当可选物品范围为从i到n时，且可分配重量为j时，的最大价值)）

////============== 从前往后 排除掉待选物品=============
//      int maxv=Knapsack(v,w,1,n,c);
//        int maxv = Knapsack(v, w, 1, n, c, m);//填表法

//// ============== 从后往前 排除掉待选物品=============
//        int maxv = Knapsack(v, w,  n, c);//参数n表示：可选物品为从1开始到n,共有n个物品
//        int maxv = Knapsack(v, w,  n, c,m);//填表
        int maxv = Knapsack(v, w, c,m);//非递归
        System.out.println("最大价值为"+maxv);
        TraceBack(m,w,c,x);
//        Print_Ar(m);
        Print_Goods(x,w,v);
    }


}
