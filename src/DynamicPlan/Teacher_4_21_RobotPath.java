package DynamicPlan;

import java.util.Scanner;

/**
 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 问总共有多少条不同的路径？
 说明：m 和 n 的值均不超过 100。

 示例 1:
     输入: m = 3, n = 2
     输出: 3
     解释:
         从左上角开始，总共有 3 条路径可以到达右下角。
         1. 向右 -> 向右 -> 向下
         2. 向右 -> 向下 -> 向右
         3. 向下 -> 向右 -> 向右
 示例 2:
     输入: m = 7, n = 3
     输出: 28

 */
public class Teacher_4_21_RobotPath {
    private static int allWays(int m, int n) {
        //创建一个二维数组
        int[][] arr= new int[m][n];
        //先处理特殊情况
        //将arr[0][n]的位置都记上1步长
        for(int i=1;i<n;i++){
            arr[0][i]=1;
        }
        //将arr[m][0]的位置都记上1步长
        for(int j=0;j<m;j++){
            arr[j][0]=1;
        }


        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                arr[i][j]=arr[i-1][j]+arr[i][j-1];//向下 或 向右
            }
        }
        return arr[m-1][n-1];//第m行n列的下标（m-1，n-1）
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m=in.nextInt();
        int n=in.nextInt();

        System.out.println(allWays(m, n));
    }
}
