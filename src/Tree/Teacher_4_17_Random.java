package Tree;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

/**
 * @author QiangQin
 * * @date 2021/6/6
 */

class HashTable{
    class  HashNode{
        int key;
        int value;//标志位（默认为0，代表该处没放值）

        public HashNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    HashNode[] table;
    int maxsize;//个数（注意下标是0.。。maxsize-1）

    public HashTable(int n) {
        maxsize=n;//因为要生成指定数量个随机数
        table=new HashNode[maxsize];
    }

    //仅生成hash值（即存放的位置）
    public int Hash(int kx){
        return kx%maxsize;//结果为0.。。maxsize-1
    }

    public  int Inc(int i){//增量
        return  i;//线性探测
//         return  i*i;// 平方探测
    }

//    解决Hash冲突
    public int Hash_Inc(int kx,int i){
        return (Hash(kx)+Inc(i))%maxsize;
    }

    //成功则为true,有重复值或满了则false
    public boolean put(int kx){
        boolean res=false;//res即result

        for(int i=0;i<maxsize;i++){
            /*
                生成kx的hash地址，i用于处理冲突
                若没进入任何一个if（即有哈希冲突 且 没发现有该值）,就啥也不干到下一轮
            */
            int pos=Hash_Inc(kx,i);
            /*
             没有哈希冲突，就 可以 放
             注意:有哈希冲突不代表值相同，只是Hash地址一样，需要找下一个哈希位置
             if和else if 最多只能进一个
             */
            if(table[pos].value==0){
                table[pos].key=kx;
                table[pos].value=1;
                res=true;
                break;
            }
             /*
               当上面不成立，才可能进到这里
               即该位置有值 且 值为kx，就不试了,返回fasle
             */
            else if(table[pos].key==kx){
                break;
            }

        }
        return res;
    }
    //有就返回下标
//    public int get(int kx){
//
//    }
}


public class Teacher_4_17_Random
{
    private static void Init_Ar(int[] ar) {
        Random random = new Random();
        int tmp=0,j=0;
        for(int i=0;i<ar.length;i++){
            //初始化为随机值0...9   1...10
            //ar[i]=(int) (Math.random()*10);//[0.0,1.0）  强转  优先级
            // ar[i]=new Random().nextInt(10);//0...9
            //ar[i]=(int) (Math.random()*10+1);//[0.0,1.0）
            //不准重复
            //方法1：i指向下标，每生成一个再前面生成的里面找，有，就重新生成
            do{
                tmp=random.nextInt(10)+1;
            }while(FindValue(ar,i,tmp)!=-1);
            ar[i]=tmp;
        }

        //时间复杂度不好
        while(j<ar.length){
            int val=random.nextInt(10)+1;
            if(FindValue(ar,j,tmp)!=-1){//若不重复
                ar[j]=val;
                ++j;//不需要赋值，直接加一，就用++i吧
            }
        }

       /*
         查表
         表的长度为数字种类数+1,（不是默认值是0，就说明已经生成过该数）若没生成该数，对应下标的数组值置为1
       */
        int[] table=new int[11];//产生数范围为1。。。10
        while(j<ar.length){
            int val=random.nextInt(10)+1;
            if(table[val]==0){//若不重复
                ar[j]=val;
                table[val]=1;
                ++j;//不需要赋值，直接加一，就用++i吧
            }
        }

        /*
          随机产生值的范围为1...100000
          此时，冲突可能性比较小
          HashNode（key，value）  哈希冲突(线性  链式)  模数
          用的不多，因为劣势是只能进，不能出
        * */
        HashTable ht=new HashTable(13);
        while(j<ar.length) {
            int val = random.nextInt(100000) + 1;

            if (ht.put(val)) {//若不重复
                ar[j] = val;
                ++j;
            }
        }

        /*
          存字符串（转为int）
           链式处理冲突怎么写
           （链表过大怎么办，
           table扩容量，素数，重新插入那些值
           二次哈希，链接起来）

           数组有20亿个值，找出重复频率高前1000位的：
           1.找出所有频度，排序，输出
           2.Hash（
           若不存在，关键码key为1，表示关键码存在；
           若存在，关键码不变，value+1
           然后把hash里的数据全部提出来，按照value值输出到大堆区（优先级队列）（取前1000个即可）

        * */
        /*
            Hash适合海量数据和查找
            nginx  （连接池    内存池 正向代理 反向代理）
            正向代理 反向代理中提到了负载均衡问题（用 一致性哈希）
            面试：
              操作系统 进程、线程、内存管理
              网络： TCP/IP  Http协议
               虚拟机
              设计模式（刘伟）
        * */

    }
    /*
         n是有效数字的个数（即已经生成了几个数）
         value是要查找的值
         若找到，便返回下标位置
     */
    private static  int  FindValue(int[] br,int n,int value){
        int pos=-1;
        for(int i=0;i<n;i++){
            pos=i;
            break;
        }
        return  pos;
    }




    public static void main(String[] args) {
        int[] ar=new int[10];//默认是0
//        Init_Ar(ar);

        HashMap<Integer,Integer> br= new HashMap<Integer,Integer>();
        br.put(1,3);
        br.put(3,3);
        br.put(4,2);
        System.out.println(br);

    }
}
