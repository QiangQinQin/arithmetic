package recursive;

import java.util.Random;

public class Main
{
    public static void Swap_Br(int []br,int i,int j)
    {
        int tmp = br[i];
        br[i] = br[j];
        br[j] = tmp;
    }
    public static void Print_Ar(int []br)
    {
        for(int i = 0;i<br.length;++i)
        {
            System.out.print(br[i] + " ");
        }
        System.out.println();
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
    public static int OWParition(int []br,int left,int right)
    {
        int i = left,j = i+1;
        int tmp = br[left];
        while(j <= right)
        {
            if(br[j] <= tmp)
            {
                i+=1;
                Swap_Br(br,i,j);
            }
            ++j;
        }
        Swap_Br(br,left,i);
        return i;
    }

    public static int RandParition(int []br,int left,int right)
    {
        Random random = new Random();
        int index = random.nextInt((right - left+1))+left;
        int tmp = br[left];
        br[left] = br[index];
        br[index] = tmp;
        return Parition(br,left,right);
    }

    public static void QuickPass(int []br,int left,int right)
    {
        Queue<Integer> qu = new LinkedList<>();
        if(left >= right) return ;
        qu.offer(left);
        qu.offer(right);
        while(!qu.isEmpty())
        {
            left = qu.poll();
            right = qu.poll();
            int pos = OWParition(br,left,right);
            if(left < pos-1){
                qu.offer(left);
                qu.offer(pos-1);
            }
            if(pos + 1 < right) {
                qu.offer(pos + 1);
                qu.offer(right);
            }
        }

    }
    public static void QuickSort(int []br)
    {
        QuickPass(br,0,br.length-1);
    }
    public static void main(String[] args) {
        int []ar={56,78,12,34,90,67,100,45,23,89};
        Print_Ar(ar);
        QuickSort(ar);
        Print_Ar(ar);
    }
}


public static void QuickPass(int []br,int left,int right)
    {
        if(left < right) // if(left <= right)
        {
            int pos = Parition(br,left,right);
            QuickPass(br,left,pos-1);
            QuickPass(br,pos+1,right);
        }// 10:00
    }