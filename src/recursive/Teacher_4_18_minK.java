package recursive;

import java.util.HashSet;

/*
利用快排找第k小的值
也可以建立一个小根堆，输出第几次，就是第几小的（但如果数据多，又要找个第10000小的就很麻烦）
*/
public class Teacher_4_18_minK {
    //找到数组的第一大和第二大并打印，只能使用第一个循环（不准循环嵌套或接力，即一个循环找第一大  另一个循环找第二大。）
    public static void Print_2Max(int[] br) {
         //在前两个数里，找到目前的第一大  第二大
        int max1 = (br[0] > br[1]) ? br[0] : br[1];
        int max2 = (br[0] > br[1]) ? br[1] : br[0];
      //跟数组后面的数逐一比较，并随时更新
        for (int i = 2; i < br.length; ++i) {
            //比最大的大
            if (br[i] > max1) {
                max2 = max1;
                max1 = br[i];
            }
            //介于之间
            else if (br[i] > max2) {
                max2 = br[i];
            }
            //比两个都小，就不处理了
        }
        System.out.printf("max1 : %d max2 ： %d \n", max1, max2);
    }

//  =============================  找第k小（若排好序，对应数组下标是k-1）============================================
  /*
     因为执行完Parition后，基准值到达了最终位置，且左侧的值都不比他大。所以可不停递归划分，且只用处理一边,直到基准值序号等于k。
  * */

    public static int Parition(int[] br, int left, int right) {
        int i = left, j = right;
        int tmp = br[i];
        while (i < j) {
            while (i < j && br[j] > tmp)//从后往前，将小于等于基准值的都往前移
                --j;
            if (i < j)
                br[i] = br[j];

            while (i < j && br[i] <= tmp)
                ++i;
            if (i < j)
                br[j] = br[i];
        }
        br[i] = tmp;
        return i;
    }

    //即在下标为[left,right]中找第K小（没有办法解决数据重复问题！！！）
    public static int Print_K(int[] br, int left, int right, int k) {
        //特殊情况（仅有一个元素，且要找第一小    或者  层层调用Print_K,直到区间长为1，则基准值为要找的元素）
        if (left == right && k == 1)
            return br[left];

        //index即基准值的位置，左边的都比他小
        int index = Parition(br, left, right);//递归划分，只处理一边(分治)就行！！！不用排好序再找，所以比线性时间（即 Ο（n））还小（例如将一列数字加总的所需时间，正比于串行的长度）
        int pos = index - left + 1;//即看基准值是第几小。比如0  1  2  3  4（基准值所在下标）   5   6    7    ，可发现4是第5小

        if(k==pos)
            return br[index];
        else if (k < pos)//基准值 大于 第几小的 几 时，继续在左边找
            return Print_K(br, left, index, k);
        else  //pos<k,则在右边找（因为index及其左边，已经有前pos小，只需在index右边找到 第k-pos小就行）
            return Print_K(br, index + 1, right, k - pos);
    }

    public static int Print_K_Min(int[] br, int k) {
        return Print_K(br, 0, br.length - 1, k);
    }


    public static void main(String[] args) {
        int[] ar = {56, 78, 12, 34, 90, 67, 100, 45, 23, 89};

//        56, 78, 12, 34, 90, 67, 100, 45, 23, 89，89  //可以先用堆排序判断有无重复值
//        System.out.println(cheakRepeat(ar));
//        //第k小
//        for (int k = 1; k <= ar.length; ++k) {
//            System.out.printf("%d => %d \n", k, Print_K_Min(ar, k));
//        }


//        int mindist=Cpair(ar,0,ar.length-1);//传入下标范围
//        System.out.println("mindist = [" + mindist + "]");

    }



//由于hashset实现了set接口，所以它不允许集合中有重复的值，
// 在调用add方法时，如果插入了重复值，不会插入，并且会返回false。
    public static boolean cheakRepeat(int[] array){
        HashSet<Integer> hashSet = new HashSet<Integer>();
        boolean flag=true;
        for (int i = 0; i < array.length; i++) {
            if(hashSet.add(array[i])==false){
                flag=false;
                break;
            }

        }
        return flag;
//        if (hashSet.size() == array.length){
//            return true;
//        }else {
//            return false;
//        }
    }
    public static int Min(int a,int b){
        return a<b?a:b;
    }
    public static int Min3(int a,int b,int c){
        return Min(a,Min(b,c));
    }
    //从左边S1区间(包含了基准值m)找最大值,所以在最右边
    public  static int MaxS1(int[] ar,int left,int right){
        return ar[right];
    }
    public  static int MinS2(int[] ar,int left,int right){
        int minS2=ar[left];
        //遍历
        for(int i=left+1;i<=right;i++){
            if(minS2>ar[i]){
                minS2=ar[i];
            }
        }
        return minS2;
    }
//  最接近点对
    private static int Cpair(int[] ar, int left, int right) {
        if(right-left<=0)//即区间里 <=1个值
            return  Integer.MAX_VALUE;

        int k=(right-left+1)/2;//不加1，第59行Parition会报错（因为right-left=1时，有俩值，但K为第0小）
        Print_K(ar,left,right,k);//用第 k 小，将区间划分为S1  S2() （没有办法解决数据重复问题！！！）
        int d1=Cpair(ar,left,left+k-1);//找S1区间的最近距离（传入物理位置下标）
        int d2=Cpair(ar,left+k,right);//S2的最近距离

        int maxS1=MaxS1(ar,left,left+k-1);
        int minS2=MinS2(ar,left+k,right);

        return Min3(d1,d2,minS2-maxS1);//找到3个值里面的最小值

    }
}