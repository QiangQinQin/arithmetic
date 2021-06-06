package recursive;

public class Main
{
    public static void Print_2Max(int []br)
    {
        int max1 = (br[0]>br[1])? br[0]:br[1];
        int max2 = (br[0]>br[1])? br[1]:br[0];
        for(int i = 2;i<br.length; ++i)
        {
            if(br[i] > max1)
            {
                max2 = max1;
                max1 = br[i];
            }else if(br[i] > max2)
            {
                max2 = br[i];
            }
        }
        System.out.printf("max1 : %d max2 ： %d \n",max1,max2);
    }

    public static int Parition(int []br,int left,int right)
    {
        int i = left, j = right;
        int tmp = br[i];
        while(i<j)
        {
            while(i<j && br[j] >tmp) --j;
            if(i < j) br[i] = br[j];
            while(i<j && br[i] <= tmp) ++i;
            if(i < j) br[j] = br[i];
        }
        br[i] = tmp;
        return i;
    }
    public static int Print_K(int []br,int left,int right, int k)
    {
        if(left == right && k == 1) return br[left];
        int index = Parition(br,left,right);
        int pos = index - left + 1;
        if(k<=pos) return Print_K(br,left,index,k);
        else return Print_K(br,index+1,right,k-pos);
    }

    public static int Print_K_Min(int []br,int k)
    {
        return Print_K(br,0,br.length-1,k);
    }
    public static void main(String[] args) {
        int []ar={56,78,12,34,90,67,100,45,23,89};

        for(int k = 1;k<=ar.length; ++k)
        {
            System.out.printf("%d => %d \n",k,Print_K_Min(ar,k));
        }
    }
}