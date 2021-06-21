package BackTrace;

/**
 * @author QiangQin
 * * @date 2021/6/20
 */
public class Teacher_5_16_GetSubSet {
    //找所有子集树（https://blog.csdn.net/qq_41571459/article/details/116984535 有整理）
// 递归
    private static void get_Subset(int[] arr, int[] brr, int i, int n) {
        if (i >= n) { //注意有个 = ，此前从0。。。n-1已经n个了
            boolean zeroFlag = true;
            for (int j = 0; j < n; j++) {//即0.。。length-1  根据brr[j]有选择的输出
                if (brr[j] == 1) {
                    System.out.print(arr[j] + " ");
                    zeroFlag = false;
                }
            }
            if (zeroFlag == true)
                System.out.print("#");
            System.out.println();
        } else {
            //两个get_Subset，像二叉树,（记录路径，作为打印标志）
            brr[i] = 1;//1表示走左边
            get_Subset(arr, brr, i + 1, n);//左子树
            brr[i] = 0;
            get_Subset(arr, brr, i + 1, n);//右子树
        }
    }

    /*
     非递归（即回溯法  先画图理解）
    * */
    private static void Print(int[] ar, int[] br) {
        int len = br.length;
        for (int i = 0; i < len; i++) {
//            System.out.printf("%3d", br[i]);
            if (br[i] == 1)
                System.out.printf("%3d", ar[i]);
        }
        System.out.println();
    }

    /*
        进一步时，将前一位先置为-1，然后将该位放的值＋1；
        继续前进；
        前进到越数组长度的界后打印并回退；
        回退到的那一位加1（若加完发现 越了数值的范围的界时，继续回退，回退到的那一新位加又1）再继续前进
        按以上规则前进又回退，
        直到br为1 1 1，不断的加1越界回退，最后数组下标变为-1，br此时为2 2 2


         因为NiceGet_Subset使br列出了 每一位是0或1的全部组合，所以可以用来解决每个物品装还是不装的  01背包问题
         （即把每种排列对应的背包重量算一下，符合条件再算其价值是不是最大）
         这种方法算穷举，回溯会要求一个减枝函数或限定函数（即到某个节点发现不满足条件，就不再往下搜索其子树了）
    * */
    private static void NiceGet_Subset(int[] ar, int[] br) {
        int i = 0;
        br[i] = -1;
        //下标回退到-1时，说明处理完了
        while (i >= 0) {
            br[i] += 1;//统一完成加一

            if (br[i] > 1) {//越数值范围的界时
                i--;//控制前进后退
            } else if (i == ar.length - 1) {//到数组最后一个元素了
                Print(ar, br);
            } else {  //不满足上面if时，都会执行到这里；（不论是回退后再前进还是第一次前进）只要是前进时都先置为负1
                i++;
                br[i] = -1;
            }
        }
    }

    //===========================改造以上算法，解决01背包=======================================
    /*
        br记录了每个物品的01； c是背包容量 ；W 重量 ；V 价值
        返回按当前br[]方式 装物品的总价值
    * */
    private static int  Print( int[] br,int[] W,int[] V,int c) {
        int len = br.length;
        int maxv=0;
        for (int i = 0; i < len; i++) {
            maxv+=br[i]*V[i];
            c-=br[i]*W[i];//背包剩余容量
            if(c<0)
                return 0;
        }
       return maxv;
    }

    private static int fun(int[] W, int[] V, int c) {
        int len = W.length;//即物品的个数
        int[] br = new int[len];
        int maxv = 0;

        int i = 0;
        br[i] = -1;
        //下标回退到-1时，说明处理完了
        while (i >= 0) {
            br[i] += 1;//统一完成加一

            if (br[i] > 1) {//越数值范围的界时
                i--;//控制前进后退
            } else if (i == len - 1) {//到数组最后一个元素了
                int t = Print(br, W, V, c);//返回按当前br[]方式 装物品的总价值
                if (t > maxv) {
                    maxv = t;
                }
            } else {  //不满足上面if时，都会执行到这里；（不论是回退后再前进还是第一次前进）只要是前进时都先置为负1
                i++;
                br[i] = -1;
            }
        }
        return maxv;
    }
//排列树


    public static void main(String[] args) {
//        //子集
        int[] arr = {1, 2, 3};//数组
        int[] brr = {0, 0, 0};//打印标识
//        get_Subset(arr, brr, 0, arr.length);//输出标识 对应 length层的树的叶节点的路径，注意wps图
        NiceGet_Subset(arr,brr);

//        01背包（用上面的算法进行穷举）
        int n = 5;//物品数量
        int c = 10;//背包容量
        int[] W = {2, 2, 6, 5, 4};//物品重量
        int[] V = {6, 3, 5, 4, 6};//物品价值
        int maxv = fun(W, V, c);
        System.out.println(maxv);

    }
}
