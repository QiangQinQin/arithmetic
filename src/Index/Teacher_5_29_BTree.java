package Index;
/*
B树，即m路搜索树、B-树

根节点至少两个孩子；所有的分支节点至少有 Math.ceil(m/2) 个子女
所有的叶子结点在同一层

插入：（WPS图，面试至少会画图）
  先放入根节点的0号即哨兵位，从该节点的最后一个数据往前比大小，找位置插入，如果比到了哨兵位还没有找到位置，那就去他的子树同理找。
  记得插完修改BNode的n值
分裂：
  节点数据满了后，要进行分裂：把中间元素上升给双亲；有可能导致他的双亲结点也满了，双亲也需要进行分裂；
  分裂时记得处理关键码和子树指针
删除：
都要转化为对叶子的删除
可以先从左后从右兄弟借数据，借不到（因为不满足结点最小数据个数了）就要合并
保证中序遍历有序

问题：叶节点没有子分支，导致sub[]空间闲置浪费
* */
class BNode{
    static int m = 5;//m路(注意需要是奇数，好分裂)
    static int MaxItem=m-1;//4 （因为4个元素会有5个分支；为5个元素时就要考虑分裂，把中间元素上升作为根节点）
    static int MinItem=m/2;//2（为根节点时，可以有一个数据）

    class KeyValue{
        char key;//存放关键码
        Object pval;//是引用，指向结点s[]
    }
    int n;//结点个数,即KeyValue元素的个数
    BNode pa;//代表双亲指针,指向结点的双亲
    KeyValue[] data=new KeyValue[m+1];//K0  K1   K2   K3  K4  K5,其中K0做查询时的哨兵位，可以暂存数据，便于分裂
    BNode[] sub=new BNode[m+1];// 是分支  S0   S1  S2   S3  S4   S5
}
//若有多层，如何保证叶结点在同一层？？？？

class BTree{
    BNode root;
    public BTree(){
        root=null;
    }
    public boolean Insert_kv(BNode.KeyValue kv){
        boolean res=false;
        return  res;
    }

}

public class Teacher_5_29_BTree {
    public static void main(String[] args) {
        System.out.println((int)Math.ceil(4.5));
    }
}
