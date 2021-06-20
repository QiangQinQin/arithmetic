package DynamicPlan;

/**
  解码方式：
 一条包含字母A-z的消息通过以下方式进行了编码:
 'A’ ->1
 'B' -> 2
 ...
 'z' ->26
 给定一个只包含数字的非空字符串，请计算解码方法的总数。题目数据保证答案肯定是一个 32位的整数。（即不可能超过int的最大值）

 示例1:
     输入: "12"
     输出:2
     解释:它可以解码为"AB"(1 2）或者"L"(12）。
 示例2:
     输入:"26"
     输出:3
     解释:它可以解码为“BZ”(2 26)，"VF"(22 6)，或者"BBF”(2 2 6)
 */
public class Teacher_5_15_Encode {

//    https://blog.csdn.net/weixin_43857345/article/details/108515231
    public static int numDecoding(char[] str) {
        int len = str.length;

        if (len == 0 || str[0] == '0' || str == null) {//没有有效数据时
            return 0;
        }
        if (len == 1) {//只有一个数据，只有一个解码方式
            return 1;
        }

        /*
            下标从0。。。i。。。len-1 共len个;
            dp[i]为str[0...i]的译码方法总和
        */
        int[] dp = new int[len];//默认为0
    /*
        经过以上if判断，能执行到此说说明
             数字首位不为0，
             每一位数字范围是0~9，
             合法编码范围应该是1。。。26
       */
        dp[0] = 1;//只有一个字符，且不为0时的  最大编码数为1
        if (str[0] == '1' || (str[0] == '2' && (str[1] >= '0' || str[1] <= '6'))) {
           dp[1]=2;//比如19分为：1 9或19,共两种编码方式
        }else{//比如29  37,只能拆成两个一位，即一种编码方式
            dp[1]=1;
        }
        for (int i = 2; i < len; i++) {
            if (str[i] == '0' && (str[i - 1] == 1 || str[i - 1] == 2)) {
                dp[i] = dp[i - 2];//0 1 ...i-2  i-1  i将i-1 i作为一个整体，0到i的总编码个数 等于 去掉后两位后前面的编码个数
            }
            if (str[i] == '0' && str[i - 1] > 2) {
                dp[i] = dp[i - 1];//比如0  30都不在合理范围内，所以去掉这个0
            }

            if (str[i - 1] == '1' || (str[i - 1] == '2' && (str[i] >= '0' || str[i] <= '6'))) {//合理范围内：比如10~19；20~26,可以当作一位编码也可以当作两位
                dp[i] = dp[i - 1] + dp[i-2];//
            }
        }
        return dp[len-1];
    }
    public static void main(String[] args) {
        String str="226";
        int num=numDecoding(str.toCharArray());
        System.out.println(num);
        //字符串常量”C Program”共有9个字符，但在内存中占10个字节，最后一个字节’\0’是系统自动加上的。（通过sizeof()函数可验证）
//        charAt(int index)
////      字符串截取然后转数字
//        String s="123456";
//        System.out.println(s.substring(0,2));//输出12（即[0,2)下标之间的东西）
//        System.out.println(String.valueOf(s)) ;
//        System.out.println(Integer.parseInt( s)) ;
    }
}
