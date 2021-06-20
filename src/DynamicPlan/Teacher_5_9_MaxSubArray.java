package DynamicPlan;

/**
 * 算法分析与设计  3.4节（了解下  推广到矩阵求和）
 * 给定一个整数数组nums ，找到一个具有最大和的 连续子数组（子数组最少包含一个元素），返回其 最大和。
 * 输入:[-2,1,-3,4,-1,2,1,-5,4]输出:6
 * 解释:连续子数组[4,-1,2,1]的和最大，为6。
 */
public class Teacher_5_9_MaxSubArray {
    //动态规划  填表
    private static int MaxSubArray(int[] nums) {
        int len = nums.length;
//        处理特殊情况
        if (0 == len)
            return 0;
        if (len == 1)
            return nums[0];
//
        int[] dp = new int[len];
        dp[0] = nums[0];
        int maxSum = 0;
        for (int i = 1; i < len; ++i) {//保证i-1不出错，i从1开始
            //动态方程式
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            //和之前的max比
            maxSum = Math.max(dp[i], maxSum);
        }
        return maxSum;
    }

    /*
    动态规划  用临时值b、sum，不断的相互记录数据
        b[j]=max{b[j-1]+a[j],a[j]}
        即当b[j-1]>0时，b[j]=b[j-1]+a[j],否则，b[j]=a[j]
    */
    private static int MaxSum(int[] a) {
        int sum = 0,
                b = 0;
        for (int i = 0; i < a.length; i++) {
            if (b > 0) {// 即当b[j-1]>0时，b[j]=b[j-1]+a[j],否则，b[j]=a[j]
                b += a[i];
            } else {
                b = a[i];
            }

            if (b > sum)
                sum = b;
        }
        return sum;
    }


    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int maxv = MaxSubArray(nums);
        System.out.println(maxv);
    }
}
