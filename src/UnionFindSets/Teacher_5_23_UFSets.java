package UnionFindSets;


/*
    考到图，会问深度优先（递归）、广度优先（队列）、怎么判断图联通吗、最小生成树有环怎么判断（比如用并查集）
    只要考到 并查集，肯定会问下面这些
    聚合类型算法：可以搜索速度变快
 */
class UFSets {
    private int[] parent; //下标即结点名字和位置   数组值为正时：即父结点位置  或者为负时：即树的结点个数
    int size;

    public UFSets(int sz) {
        size = sz;
        parent = new int[size];//0...size-1
        for (int i = 0; i < size; ++i) {
            parent[i] = -1;//数组值为-1说明此树只有根一个结点；并且下标 对应的 i结点就是根
        }
    }
    //只要他们两个在一个集合中就等价，即有相同的根节点（WPS图）

    ////   搜索操作（找x所在树的根节点）
//    递归
//    public int FindParent(int x) {
//        int root;
//        if(parent[x]>=0){
//           root= FindParent( parent[x]);
//        }else{
//            root=x;
//        }
//        return root;
//    }
    //非递归（面试没啥问头）
    public int FindParent(int x) {
        while (parent[x] >= 0) {//注意为负数才算到了根节点
            x = parent[x];
        }
        return x;
    }


    //并操作（属于不同集合才有必要合并）
    public boolean Union(int ia, int ib) {
        boolean res = false;
        int pa = FindParent(ia);
        int pb = FindParent(ib);
        if (pa != pb) {
            parent[pa] += parent[pb];//改变以pa为根的树的节点个数
            parent[pb] = pa;//即把pb为根的树挂在pa下
            res = true;
        }
        return res;
    }

/*
      退化成链表。
     优化1：加权规则：将小树挂在大树下面。集合的结点没变，但是树形结构变了
 */
    public boolean NiceUnion(int ia, int ib) {
        boolean res = false;
        int pa = FindParent(ia);
        int pb = FindParent(ib);
        if (pa != pb) {
            if (parent[pa] > parent[pb]){//比如-1  > -3,绝对值大的树的结点个数多
                parent[pb] += parent[pa];
                parent[pa] = pb;//即把pa为根的树挂在pb下
            } else {
                parent[pa] += parent[pb];//改变以pa为根的树的节点个数
                parent[pb] = pa;//即把pb为根的树挂在pa下
            }
            res = true;
        }
        return res;
    }
/*
优化2：折叠规则(自己照PPT写的)
即:如果j是从i到根的路径上的一个结点，且 parent[j]!=root[j]，则把parent[j]置为root[i]。
* */
public int CollapasingFind(int i) {
    int j;
    for(j=i;parent[j]>=0;j=parent[j])//让j沿着双亲指针一直走到根节点
    while (i!=j) {
       int tmp= parent[i];//i的原父亲
       parent[i]=j;//将i的父亲换成根
       i=tmp;//i现在变成之前的父亲，继续while循环，将父亲换成根
    }
    return j;//返回i所在子集的根节点
}

    /*
        打印某个集合的所有元素（不要求顺序）（重要！！！）
        根据功能写不同子函数，条理清晰
    */
    //层层递归，打印x的所有子女
    public void Print(int x) {
        System.out.printf("%3d", x);
        for (int i = 0; i < size; ++i) {//遍历找所有x的子节点
            /*
             x肯定不是自己的子女，所以不用往下找
             i的父亲是x,即x的子是i
             */
            if (i != x && parent[i] == x)
                Print(i);//递归（找子结点的 所有子节点）
        }
    }

    public void PrintSet() {
        int s = 1;//集合编号从1开始
        for (int i = 0; i < size; ++i) {//遍历找到所有集合的根，然后根据根打印集合所有子元素
            if (parent[i] < 0) {//值为负的即为根
                System.out.printf("s%d   \n", s++);
                Print(i);//打印以i为根的集合的所有元素
                System.out.println();
            }
        }
    }

}

public class Teacher_5_23_UFSets {
    public static void main(String[] args) {
        UFSets set = new UFSets(12);//wps图
//        实际上这些等价关系不是人为指定的，而是根据调查一些数据特征得出来的（人：有房 有车  有存款  人：矮挫穷）
//        set.Union(0, 4);//0和4等价，所以聚合在一起
//        set.Union(3, 1);
//        set.Union(6, 10);
//        set.Union(8, 9);
//        set.Union(7, 4);
//        set.Union(6, 8);
//        set.Union(3, 5);
//        set.Union(2, 11);
//        set.Union(11, 0);

        set.NiceUnion(0, 4);
        set.NiceUnion(3, 1);
        set.NiceUnion(6, 10);
        set.NiceUnion(8, 9);
        set.NiceUnion(7, 4);
        set.NiceUnion(6, 8);
        set.NiceUnion(3, 5);
        set.NiceUnion(2, 11);
        set.NiceUnion(11, 0);
        set.PrintSet();
    }
}
