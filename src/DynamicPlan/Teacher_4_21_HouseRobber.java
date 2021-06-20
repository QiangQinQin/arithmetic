package DynamicPlan;

/**
 打家劫舍
 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
 如果两间相邻的房屋在 同一晚上 被小偷闯入，系统会自动报警。
 给定一个代表每个房屋存放金额的非负整数 数组 ，计算你不触动警报装置的情况下， 一夜之内 能够偷窃到的 最高金额。
 示例1:
     输入:[1,2,3,1]输出:4
     解释:偷窃1号房屋(金额= 1)，然后偷窃3号房屋（金额=3)。
         偷窃到的最高金额= 1 + 3 = 4 。
 示例2:
     输入:[2,7,9,3,1]输出:12
     解释:偷窃1号房屋（金额= 2)，偷窃3号房屋(金额= 9)，接着偷窃5号房屋（金额
         偷窃到的最高金额=2+9 +1 = 12 。
 */

public class Teacher_4_21_HouseRobber {

    public static int rob(int []nums) {
        if (nums.length == 0)
            return 0;

        int len = nums.length;
        if (len == 1) {
            return nums[0];
        }

        int[] dp = new int[len];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for(int i=2;i<len;i++){
            dp[i]=Math.max(dp[i-1],dp[i-2]+nums[i]);
        }
        return dp[len-1];
    }
    public static void main(String[] args) {
        int[] nums={2,7,9,3,1};
        System.out.println(rob(nums) );
    }
}
