package DynamicPlan;

/**
 * @author QiangQin
 * * @date 2021/6/13
 */
public class Teacher_4_21_MaxCommonSubsequence {
    public static void main(String[] args) {
        //不用0下标的值，为0时认为串为空了
        //         0   1   2   3   4    5   6    7   m=8
        char[] X={'#','A','B','C','B','D','A','B'};
        char[] Y={'#','B','D','C','A','B','A'};//n=7
        int[][] c=new int[X.length][Y.length];//0...length-1(保存计算结果)
        int[][] s=new int[X.length][Y.length];//步数标记（标记收缩方式）
//        int maxlen=LCSLength(X,Y,X.length-1,Y.length-1);//传入最后一个元素下标位置
//        int maxlen=LCSLength(X,Y,X.length-1,Y.length-1,c);//传入最后一个元素下标位置
//         int maxlen=LCSLength(X,Y,X.length-1,Y.length-1,c,s);//传入最后一个元素下标位置
        int maxlen=LCSLength(X,Y,c,s);
        System.out.println(maxlen);
        Print_Array(c);
        Print_Array(s);

        BackPack(X,X.length-1,Y.length-1,s);
    }




    //递归  根据图
    private static int LCSLength(char[] X, char[] Y, int i, int j) {
        if(i==0  || j==0){
            return  0;
        }else if(X[i]==Y[j]){
            return LCSLength(X,Y,i-1,j-1)+1;
        }else{// X[i]!=Y[j]
            return Math.max(LCSLength(X,Y,i-1,j),LCSLength(X,Y,i,j-1));//Math.max库函数  i j的范围里有重复的区域，可将其存到表里
        }
    }
    //即将计算结果存到表里，默认为0（函数多了一个参数c）
    private static int LCSLength(char[] X, char[] Y, int i, int j,int[][] c) {
        if(i==0  || j==0){
            return  0;
        }else if(c[i][j]>0) {//不等于0,说明已经计算过了,就不进入下面的情况了
            return c[i][j];
        }else if(X[i]==Y[j]){
            c[i][j]=LCSLength(X,Y,i-1,j-1,c)+1;//用了C，就不用递归了！！！
        }else{// X[i]!=Y[j]
            c[i][j]=Math.max(LCSLength(X,Y,i-1,j,c),LCSLength(X,Y,i,j-1,c));//Math.max库函数  i j的范围里有重复的区域，可将其存到表里
        }
        return c[i][j];
    }
    //打印展示
    private static void Print_Array(int[][] c) {
        for(int i=0;i<c.length;++i){//行数
            for(int j=0;j<c[i].length;++j){
                System.out.printf("%4d",c[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    //想知道最长的公共子序列是哪几个字母
    //递归到的部分才填了表
    private static int LCSLength(char[] X, char[] Y, int i, int j,int[][] c,int[][] s) {
        if(i==0  || j==0){
            return  0;
        }else if(c[i][j]>0) {//不等于0,说明已经计算过了,就不进入下面的情况了
            return c[i][j];
        }else if(X[i]==Y[j]){
            c[i][j]=LCSLength(X,Y,i-1,j-1,c,s)+1;//即发现共同元素
            s[i][j]=1;//行减1，列减1的标志
        }else{// X[i]!=Y[j]
            int lena=LCSLength(X,Y,i-1,j,c,s);//加入s参数
            int lenb=LCSLength(X,Y,i,j-1,c,s);
            if(lena>lenb){
                c[i][j]=lena;//更长的
                s[i][j]=2;//行减1时的标记，因为选择了LCSLength(X,Y,i-1,j,c,s) 即排除掉X的最后一个
            }else{
                c[i][j]=lenb;//更长的
                s[i][j]=3;//列减1时的标记
            }
        }
        return c[i][j];
    }

    //填表算法（ 非递归   要会手动模拟）
    public static  int LCSLength(char[] X,char[] Y,int[][] c,int[][] s){
        //第0行 第0列默认为0 ，算已经填过了
        for(int i = 1; i<=X.length -1; ++i){//X=0的那一行 视为 填了
            for(int j = 1;j<=Y.length-1; ++j){
                if(X[i] == Y[j]){
                    c[i][j] = c[i-1][j-1] + 1;
                    s[i][j]=1;
                }
                else{
//                    c[i][j] = Math.max(c[i-1][j],c[i][j-1]);
                    if(c[i-1][j] > c[i][j-1]){
                        c[i][j] = c[i-1][j];
                        s[i][j] = 2;
                    } else{
                        c[i][j] = c[i][j-1];
                        s[i][j] = i;
                    }
                }
            }
        }
        return  c[X.length-1][Y.length-1];//右下角，即最长公共子序列长度
    }


    //打印展示（B D A B ）   s记录了方向改变
    private static void BackPack(char[] X, int i, int j, int[][] s) {
        if (i == 0 || j == 0)
            return;
        if (s[i][j] == 1) {//只打印等于1的
            BackPack(X, i - 1, j - 1, s);//递归进去，然后层层返回
            System.out.print(X[i] + " ");
        } else if (s[i][j] == 2) {//对应行减1
            BackPack(X,i - 1, j, s);
        } else {
            BackPack(X, i,j - 1, s);
        }
    }
}
