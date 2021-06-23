package Index;

/**
 * @author QiangQin
 * * @date 2021/6/23
 */
//Plus 即 +;    BPNode里面属性为分支结点和叶节点共有的
class BPNode{
    static  int m=5;//5路
    static  int LeafMAX=m;//叶子里的最大元素个数
    static  int LeafMIN=m/2+1;//3  等价于 m/2向上取整
    static  int BrchMAX=m-1;//分支里元素最大个数
    static  int BrchMIN=m/1;

    int n;//结点里实际元素个数
    BPNode pa;
    char[] key=new char[m+1];
}
//继承
class BrchNode extends BPNode{
    BPNode[] sub = new BPNode[m+1];
}
class LeafNode extends BPNode{
    BPNode prev, next;//父的引用可以引用子类型
    Object[] pval = new Object[m+1];//指向记录集（即数据在数据库实际存放的地址）
}
class BPTree{
    BPNode first,root;
}

public class Teacher_5_29_BP {
    public static void main(String[] args) {

    }
}
