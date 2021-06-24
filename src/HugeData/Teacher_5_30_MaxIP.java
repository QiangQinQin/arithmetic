package HugeData;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author QiangQin
 * * @date 2021/6/24
 */
class IPNode{
    Integer ip;
    int ref;//y引用次数
    //重写比较函数，按ref排序
}

class IPUtil {
//    int  4字节；long共8字节；ipv4 4字节;ipv6 的地址占用16个字节
    /**
     * 将IP地址转化成整数的方法如下：
     * 1、通过String的split方法按.分隔得到4个长度的数组
     * 2、通过左移位操作（<<）给每一段的数字加权，第一段的权为2的24次方，第二段的权为2的16次方，第三段的权为2的8次方，最后一段的权为1
     */
    public static long ipToLong(String strIp) {
        String[]ip = strIp.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }

    /**
     * 将十进制整数形式转换成127.0.0.1字符串形式的ip地址
     * 1、将整数值进行右移位操作（>>>），右移24位(即除以2^24，只剩了高8位)，右移时高位补0，得到的数字即为第一段IP。
     * 2、通过与操作符（&）将整数值的高8位(即1字节)设为0，再右移16位，得到的数字即为第二段IP。
     * 3、通过与操作符吧整数值的高16位设为0，再右移8位，得到的数字即为第三段IP。
     * 4、通过与操作符吧整数值的高24位设为0，得到的数字即为第四段IP。
     */
    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }
}

public class Teacher_5_30_MaxIP {
    public static void main(String[] args) {
        HashMap<Integer,Integer> iphash =new HashMap<Integer, Integer>();
        //      ip地址   引用次数

        Queue<IPNode> que=new PriorityQueue<IPNode>();
        /*优先级队列：
            优先队列中，元素被赋予优先级。当访问元素时，具有最高优先级的元素最先删除。
            优先队列具有最高级先出(first in，largestout）的行为特征。
            通常采用堆数据结构来实现。
       */

        System.out.println(new IPUtil().ipToLong("219.239.110.138"));
        System.out.println(new IPUtil().longToIP(18537472));
    }
}
