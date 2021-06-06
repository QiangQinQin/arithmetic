package recursive;

/**
 * @author QiangQin
 * * @date 2021/5/18
 */

public class Teacher_3_20_Factoria {

    /*
          阶乘结果 超出int范围(4字节)怎么办？
          n是有符号数时，限制n不能大于12，或者 令溢出时为负数时为-1
          当n是无符号数时，不会变成负数，一旦出现进位、借位，CF 就为 1。
             */
    //    注意修饰符public static   以及  写在public class Teacher_3_20_Factoria内部
    // O(n)  S(1)
    public static int fun(int n){
        int sum=1;//0的阶乘是1
        for(int i=1;i<=n;i++){//如果发生死循环，耗损cpu时间资源，其他程序会运行慢一点
            sum*=i;
        }
        return sum;
    }
/*
     cpu代表的是时间方向   可以耗损的时间是无穷的，直到宇宙毁灭，所以可以无线循环
     内存代表的是空间方向   内存是有限的，所以不能无限递归
*/

     //   O(n)  S(n)(执行n次递归语句，开辟n次栈帧)
    public  static int fac(int n){
        if(n<=1) //退出这一层调用    如果无限递归，会 栈溢出（因为每当调用发生时，就要分配新的栈帧）
            return 1;
        else
            return fac(n-1)*n;
    }


    /*
                      序号：0  1   2   3   4   5   6
     Fibonacci 斐波那契数列:1  1   2   3   5   8  13
    */
    //    O(2^n)（总结点数）      S(n)（树的最大高度个栈帧，然后就销毁，回退，再往下递推）
    public  static  int Fib(int n){
        if(n<=1){
            return 1;
        }else{
            return Fib(n-1)+Fib(n-2);//双分支递归，调用形式像二叉树，层层往下计算
        }
    }
    //优化上面递归的时间复杂度为O(n)
    public  static  int optimize_Fib(int n,int first,int second){
       if(n<=1)  {
           return second;
       }else{
           return optimize_Fib(n-1,second,first+second);//消除了重复项的计算,利用first+second暂存，类似于下面的for循环实现
       }
    }
    public  static  int optimize_Fib(int n){
        int first=1,second=1;
        return  optimize_Fib(n,first,second);//单分支递归
    }


    /*
     O(n)  S(1)
    O(f(n))：为算法的渐进时间复杂度，简称时间复杂度。
    o（小o）：表示小于,是最坏时间复杂度
    * */
    public  static  int Fibonacci(int n){
        if(n<=1){
            return 1;
        }else{
            int first=1,second=1,sum=1;
            //i等于0 1时，不进入循环，直接输出sum
            for(int i=2;i<=n;i++){
                sum=first+second;
                first=second;
                second=sum;
            }
            return sum;
        }
    }




   /*
     如果数组是从小到大有序的，使用递归和非递归实现二分查询。

     首先，假设表中元素是按升序排列，
     将表中间位置记录的关键字与查找关键字比较，
     如果两者相等，则查找成功；
     否则利用中间位置记录将表分成前、后两个子表，
         如果中间位置记录的关键字大于查找关键字，则进一步查找前一子表，
         否则进一步查找后一子表。
     重复以上过程，
        直到找到满足条件的记录，使查找成功，
        或直到子表不存在为止，此时查找不成功。
   * */
   public static int binarySearch(int[] array,int tar,int  high,int low){
       int pos=-1;
       if(low<=high){//记住有个等于
           int mid=( (high-low)>>1 ) +low;//加法优先级高
           if(array[mid]==tar){
               while(mid<high && array[mid+1]==tar)  ++mid;//找最右边的（注意：<high，才能保证mid+1不越界）
               pos= mid;

           }else if(tar<array[mid]){
               pos= binarySearch(array,tar,low,mid-1);
           }else if(tar>array[mid]){
               pos= binarySearch(array,tar,mid+1,high);
           }
       }
       return pos;
   }

//   常见命名 arr  pos  index  target  left  right
    public static int binSearch(int[] array,int num){
        int low=0;
        int high=array.length-1;//length不带括号，因为是属性
        int pos=-1;
        //记住有个等于号,指向同一个数据也要查
        while (low<=high) {
            int mid=(high-low)/2+low;//比(low+high)/2好在  防止超范围
            if (array[mid] > num){
                high=mid-1;
            }else if (array[mid] < num) {
                low=mid+1;
           }else{
               // 如果有重复值，怎么找到最左边元素的下标？？？？？？？
               while(mid>low && array[mid-1]==num)
                   --mid;//0号元素值也是的时候，减减会越界;最小为low+1时，--一下也不会越界
//                // 如果有重复值，怎么找到最右边元素的下标？？？？？？？
//                while(mid<high && array[mid+1]==num)
//                    ++mid;//0号元素值也是的时候，减减会越界;最小为low+1时，--一下也不会越界
                pos=mid;

               //  如果数组中间没有有序???????????????
                break;
            }
         }
        return pos;
    }
 /*
   一只青蛙一次可以跳上1级台阶，也可以跳上2级。
   求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
   * */

 /*
    二分查询的笔试题目
    给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。
    如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
    * */

    /*
    贪吃的小明.
    小明的父母要出差 N 天，走之前给小明留下了 M 块奶糖,小明决定每天吃的奶糖数量不少于前一天吃的一半，
    但是他又不想在父母回来之前的某一天没有奶糖吃,请问他第一天最多能吃多少块奶糖。
     例如: input: 出差 4 天 , 7 块奶糖 // 1 2 4
    * */

    /*
    寻找旋转排序数组中的最小值
    假设升序排序的数组在预先未知的某个点上进行了旋转。
    例如: 数组 {10,11,12,13,14,15,16,17} 可能变为 [14,15,16,17,10,11,12,13};
    请找出其中最小的元素。
    假设数组中不存在重复元素
    * */

    /*
    整数划分的推导
    * */

    /*

    归并排序
    快速排序
    递归 以及 非递归
    * */



    //算法书13页图
    private static void Perm(int[] arr, int k, int m) {
        //k m是要排序字母的起点和终点   位置重合，即只剩一个元素
        if(k==m){
            for(int i=0;i<=m;i++){
                System.out.print(arr[i]+" ");
            }
            System.out.println();
            //执行到此，此层perm已结束，回退
        }else{
            for(int j=k;j<=m;j++){
                Swap_Arr(arr,j,k);//固定一个
                Perm(arr,k+1,m);//规模减小
                Swap_Arr(arr,j,k);//处理完一个首字母的全排列后，回退，保证原数组仍为1 2 3  不然输出结果发现 1 2 3重复
            }
        }
    }
    //j位置是要固定的元素，应该放到最前面    K是要排列元素的起点位置下标
    private static void Swap_Arr(int[] arr, int j, int k) {
            if(k!=j){
                int tmp=arr[j];
                arr[j]=arr[k];
                arr[k]=tmp;
            }
    }



//找所有子集
    private  static void get_Subset(int[]  arr,int[] brr,int i,int n){
        if(i>=n){
            boolean zeroFlag=true;
            for(int j=0;j<n;j++){//即0.。。length-1  有选择的输出
                if(brr[j]==1){
                    System.out.print(arr[j]+" ");
                    zeroFlag=false;
                }
            }
            if(zeroFlag==true)
                System.out.print("#");
            System.out.println();
        }else{
            //两个get_Subset，像二叉树
            brr[i]=1;//1表示走左边
            get_Subset(arr,brr,i+1,n);//左子树
            brr[i]=0;
            get_Subset(arr,brr,i+1,n);//右子树
        }
    }


    public static void main(String[] args) {
//       System.out.println(optimize_Fib(6));

//       int[] arr={1,2,3};
//       Perm(arr,0,arr.length-1);

        //子集
        int[] arr={1,2,3};//数组
        int[] brr={0,0,0};//打印标识
        get_Subset(arr,brr,0,arr.length);//输出标识 对应 length层的树的叶节点的路径，注意wps图

    }


}


