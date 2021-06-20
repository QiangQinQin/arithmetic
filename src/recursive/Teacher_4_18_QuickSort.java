package recursive;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/*
 * 快速排序
 * 思想：
 *      在待排序列中确定一个 基准mar，一般来说array[0], 然后确定指针low,high分别指向待排序列的第一个元素和最后一个元素，

 * 1. 从后往前 比较array[high]和mar,
 *      如果high位置元素大于基准，high继续往前（左）走；
 *      如果high位置元素小于或等于基准，high表示的元素 移动 至前面位置；
 * 2.从前往后比较array[low]和mar，
 *      如果low位置元素小于基准，low继续往后（右）走；
 *      如果low位置元素大于基准，low表示的元素 移动 至后面位置，
 * 重复1.2操作，直到low  high相等，然后就把基准放在这里
 * 以上为一次划分，最终的结果为：基准的左边都是比它小的元素，基准的右边都是比它大的元素；

 * 针对基准左右两边的序列 继续 进行一次更细的划分，最终即可得到一个完全有序的序列
 *
 * 平均的复杂度是O(nlogn)，这里的n和logn, 分别代表了调用栈的高度，和完成每层的时间，
 * */
public class Teacher_4_18_QuickSort {

//问题1：对数组进行快排
//问题2：对单链表进行快排（只有next,没有pre）:改为单逼近方案
    public static void Print_Ar(int[] br) {
        for (int i = 0; i < br.length; ++i) {
            System.out.print(br[i] + " ");
        }
        System.out.println();
    }

    /*
       分割
          最后tmp左边都是比他小的以及相等的 右边都是比他大的；
          返回 基准值最终的下标
       */
    public static int Parition(int[] br, int left, int right) {
        int i = left, j = right;
        int tmp = br[i];
        //i==j时停止
        while (i < j) {
            //j越界了  或者找到一个合适的 就截止
            while (i < j && br[j] > tmp)//j--有可能越过i，所以要再判断 i<j
                --j;//先不动i值，从后往前找 第一个 比tmp小的或是相等！！！的，将其交换
            if (i < j)
                br[i] = br[j];

            while (i < j && br[i] <= tmp)//注意有个＝ ，
                ++i;
            if (i < j)
                br[j] = br[i];
        }
        br[i] = tmp;
        return i;
    }

   /*
      数组有序如5 4 3 2 1，靠不停调用 QuickPass(br,left,pos-1);，时间复杂度为n*n，
      方法1：使用随机元素作为基准值
      方法2：三位取中  mid=(right-left+1)/2+left, 然后找left mid right的中位数，作为基准值
      */
    public static int RandParition(int[] br, int left, int right) {
        Random random = new Random();
        int index = random.nextInt((right - left + 1)) + left;//nextInt(int n)生成一个介于[0,n)之间的随机值  例：left为3  right为6   随机值范围为[0,6-3+1)

        //将随机的br[index]移到左边作为基准值
        int tmp = br[left];
        br[left] = br[index];
        br[index] = tmp;

        return Parition(br, left, right);
    }
//    QuickPass在left<right时才会调用Partition
    public static int MidPartition(int[] br, int left, int right){
        int  mid=(right-left+1)/2+left;
        //保证left是最小
        if (br[left] > br[right]) {
            Swap_Br(br, left, right);
        }
        if (br[left] > br[mid]) {
            Swap_Br(br, left, mid);
        }
        //因为left最小，保证right比mid大，那么right就是最大
        if (br[right] < br[mid]) {
            Swap_Br(br, right, mid);
        }
        //那么mid应该是中间值，交换到left位置。 left mid right三处值的大小关系为：中 小 大，大的这次Parition就不用再移位置了
        Swap_Br(br, left, mid);
        return Parition(br, left, right);
    }

    ////递归调用Parition
    public static void QuickPass(int []br,int left,int right)
    {
        if(left < right) // if(left <= right)  left=right只有一个元素时，就没有划分的必要了
        {
            int pos = Parition(br,left,right);
//           int pos = RandParition(br,left,right);
            QuickPass(br,left,pos-1);
            QuickPass(br,pos+1,right);
        }
    }

    public static void QuickSort(int[] br) {
//        QuickPass(br, 0, br.length - 1);
        NiceQuickPass(br, 0, br.length - 1);
    }




    ////非递归
    public static void Swap_Br(int[] br, int i, int j) {
        int tmp = br[i];
        br[i] = br[j];
        br[j] = tmp;
    }

    //单逼近的划分（wps图）  单向：OneWay
    public static int OWParition(int[] br, int left, int right) {
        int i = left, j = i + 1;
        int tmp = br[left];
        while (j <= right) {//用j从前往后遍历，将小于left的放在 i+1  指向的
            if (br[j] <= tmp) {//大于时不做处理,把小于等于的都往左边集中
                i += 1;
                Swap_Br(br, i, j);
            }
            ++j;
        }
        Swap_Br(br, left, i);//将基准值移到最终位置i,至此  i左边的都不比他大，边的都比他大
        return i;
    }

    public static void NiceQuickPass(int[] br, int left, int right) {
        Queue<Integer> qu = new LinkedList<Integer>(); //LinkedList可以当链表或队列使用
        //Stack<Integer> stack = new Stack<Integer>(); //用栈记得push  和pop的顺序不一样
        if (left >= right)
            return;
        //存放区间（队列是先进先出）
        qu.offer(left);//offer指添加到尾巴
        qu.offer(right);
        while (!qu.isEmpty()) {
            left = qu.poll();//取出队头
            right = qu.poll();
            int pos = OWParition(br, left, right);
            //添加前要先判断是否有多个数据
            if (left < pos - 1) {
                qu.offer(left);//压进去备用，下轮while弹出来
                qu.offer(pos - 1);
            }
            if (pos + 1 < right) {
                qu.offer(pos + 1);
                qu.offer(right);
            }
        }
    }


    public static void main(String[] args) {
        int[] ar = {56, 78, 12, 34, 90, 67, 100, 45, 23, 89};
        Print_Ar(ar);
        QuickSort(ar);
        Print_Ar(ar);
    }
}


