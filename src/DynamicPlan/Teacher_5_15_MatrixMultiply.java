package DynamicPlan;

/**
 * @author QiangQin
 * * @date 2021/6/18
 */

public class Teacher_5_15_MatrixMultiply {
    /*
        矩阵a乘b=c，
        需要满足A的列数等于B的行数
        前缀：r即row（行）  ；c即column(列)
        三层for循环，时间复杂度高(即A:p*q  × B:q*r   时间复杂度为:p*q*r）
    */
    public static void MatrixMultiply(int[][] a, int[][] b, int[][] c, int ra, int rb, int ca, int cb) {
        if (ca != rb)
            return;
        for (int i = 0; i < ra; i++)//新矩阵的行
        {
            for (int j = 0; j < cb; j++)//新矩阵的列
            {
                int sum = 0;
                for (int k = 0; k < ca; k++) {//ca等于rb，写哪个都行
                    sum += a[i][k] * b[k][j];// 即sum += a[ra][ca]*b[rb][cb];
                }
                c[i][j] = sum;
            }
        }
    }

    //找到乘的次数最少 的 矩阵连乘次序
    // 方法1：暴力破解（至少有解，还可以边找规律）：列举所有可能的计算次序，并计算出每一种相应的数乘次数
    // 方法2：   动态规划（递归）      从第i个矩阵乘到第j
    private static int MatrixChain(int[] p, int i, int j) {
        if (i == j) //仅有一个矩阵时
            return 0;
        else { //if(i<j)
            //分割点k = i  将一串矩阵分为两部分
            int minnum = MatrixChain(p, i, i) + MatrixChain(p, i + 1, j) + p[i - 1] * p[i] * p[j];

            for (int k = i + 1; k < j; ++k) {
                int t = MatrixChain(p, i, k) + MatrixChain(p, k + 1, j) + p[i - 1] * p[k] * p[j];
                if (minnum > t){
                    minnum = t;
                }
            }

            return minnum;
        }
    }

//    填表，避免重复求解
private static int MatrixChain(int[] p, int i, int j,int[][] m) {
    if (i == j) //仅有一个矩阵时
        return 0;
    else if(m[i][j]>0)
        return m[i][j];
    else { //if(i<j)
        //分割点k = i  将一串矩阵分为两部分
        m[i][j] = MatrixChain(p, i, i,m) + MatrixChain(p, i + 1, j,m) + p[i - 1] * p[i] * p[j];

        for (int k = i + 1; k < j; ++k) {
            int t = MatrixChain(p, i, k,m) + MatrixChain(p, k + 1, j,m) + p[i - 1] * p[k] * p[j];
            if (m[i][j] > t){
                m[i][j] = t;
            }
        }

        return m[i][j];
    }
}
//  记录分割的位置点
private static int MatrixChain(int[] p, int i, int j,int[][] m,int[][] s) {
    if (i == j) //仅有一个矩阵时
        return 0;
    else if(m[i][j]>0)
        return m[i][j];
    else { //if(i<j)
       /*
        0分割点k = i  将一串矩阵分为两部分,作为基准，和其他分割法的次数进行比较
        如果只有两个矩阵相乘，那么只能以第一个作为划分点
        */
        m[i][j] = MatrixChain(p, i, i,m,s) + MatrixChain(p, i + 1, j,m,s) + p[i - 1] * p[i] * p[j];
        s[i][j]=i;

        for (int k = i + 1; k < j; ++k) {
            int t = MatrixChain(p, i, k,m,s) + MatrixChain(p, k + 1, j,m,s) + p[i - 1] * p[k] * p[j];
            if (m[i][j] > t){
                m[i][j] = t;
                s[i][j]=k;
            }
        }

        return m[i][j];
    }
}

    private static void Print_Ar(int[][] m) {
        int row = m. length;
        int col = m[0].length;
        for(int i = 0 ; i<row ;++i){
            for(int j = 0; j<col;++j){
                System. out. printf("%7d", m[i][j]);
            }
            System. out.println() ;
        }
        System.out.println();
    }

    private static void TraceBack(int[][] s, int i, int j ) {
        if(i<j){//起点小于终点
            TraceBack(s,i,s[i][j]);//s[i][j]记录了分割点k
            TraceBack(s,s[i][j]+1,j);
            System.out.println("Multiply A("+i+","+s[i][j]+") and A(" +(s[i][j]+1)+","+j+")");
        }
    }

    //====================根据划分结果怎么样实际乘矩阵=====================================
/*
    根据划分点进行相乘（MatrixMultiply），
    分解到底，先乘小的，再乘大的，最后返回一个二维矩阵的结果？？？？？？？？？？？？？？？？
 */


//非递归实现（窗口方案）
private static int NiceMatrixChain(int[] p, int[][] m, int[][] s) {
    //填表时0下标不算
    int len=p.length-1;//即矩阵个数

    for(int i=1;i<=len;i++)
        m[i][i]=0; //因为窗口长度为1时，仅有一个矩阵
    //r既是窗口的长度
    for(int r=2;r<=len;r++) {
        for(int i=1;i<=len-r+1;i++){//滑动窗口的起点（len-(r-1)   len-(r-2) ... len-(r-r)共n个）
            int j=i+r-1;//终点  （i  i+1 i+2 ... i+r-1 一共r个）

            m[i][j]=m[i][i]+m[i+1][j]+p[i-1]*p[i]*p[j];
            for(int k=i+1;k<j;k++){//和其他不同分割点进行比较
                int t=m[i][k]+m[k+1][j]+p[i-1]*p[k]*p[j];//代码谨慎复制粘贴，有可能上面和下面的逻辑不一样，找错很麻烦
                if(m[i][j]>t){
                    m[i][j]=t;
                }
            }
        }
        Print_Ar(m);//填完一个窗口长度就输出一次
    }
    return m[1][len];
}

    public static void main(String[] args) {

        int[] p = {30, 35, 15, 5, 10, 20, 25};//矩阵的维数  结合图理解
        int[][] m=new int[p.length][p.length];//记录从i到j矩阵的最小数乘次数
        int[][] s=new int[p.length][p.length];//记录分割点

        int len = p.length;//长度为7，实际上只有6个矩阵
//        int minmul = MatrixChain(p, 1, len - 1);
//        int minmul = MatrixChain(p, 1, len - 1,m);
//        int minmul = MatrixChain(p, 1, len - 1,m,s);
        int minmul = NiceMatrixChain(p,m,s);
        Print_Ar(m);
        Print_Ar(s);
//        TraceBack(s,1,len-1);//即从第1个 到 第6个矩阵
        System.out.println(minmul);


//====================根据划分结果怎么样实际乘矩阵=====================================
    /*
            输入数组
            AX[0]不算有效二维数组。
            AX[i]都是二维数组
     * */
//        int[][][] AX={A0,A1,A2,A3,A4,A5,A6};


////测试三维数组
//        int tt1[] = {1,2,3};
//        int tt2[] = {4,5,6};
//        int tt3[] = {7,8,9};
//        int ttt1[][] = {tt1,tt2,tt3};
//        int ttt2[][] = {tt3,tt1,tt2};
//        int ttt3[][] = {tt2,tt3,tt1};
//        int ttt4[][] = {tt1,tt3,tt2};
//        int ctrlpoints[][][] = {ttt1,ttt2,ttt3,ttt4};
//
//        System.out.println(ctrlpoints[1][2][2]); //看一下这个数是多少
    }


}
