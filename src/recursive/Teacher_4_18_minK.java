package recursive;

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

//  =============================  找第k小（对应数组下标是0.。。k-1）============================================
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

    //即在下标为[left,right]中找第K小
    public static int Print_K(int[] br, int left, int right, int k) {
        //特殊情况（仅有一个元素，且要找第一小    或者  层层调用Print_K,直到区间长为1，且基准值为要找元素）
        if (left == right && k == 1)
            return br[left];

        //index即基准值的位置，左边的都比他小
        int index = Parition(br, left, right);//递归划分，只处理一边(分治)就行！！！不用排序再找，所以比线性时间还小
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
        //第k小
        for (int k = 1; k <= ar.length; ++k) {
            System.out.printf("%d => %d \n", k, Print_K_Min(ar, k));
        }
    }
}