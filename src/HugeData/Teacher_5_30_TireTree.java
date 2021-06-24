package HugeData;

import static HugeData.TireNode.NodeType.branch;

/**
 * @author QiangQin
 * * @date 2021/6/24
 */
class KeyType {//关键码类型
    static int MaxKeySize = 25;//最大长度
    char[] ch = new char[MaxKeySize]; // "yhping"
    int currentSize;                  //  6
}

//T树结点类型   父类型，可以指向子节点的引用
class TireNode{
    enum NodeType{branch,element};//枚举类型
    NodeType kind;
    TireNode pa;//只有一个分支时，可以回溯到双亲结点，便于删除

}
//元素结点
class ElemType extends  TireNode{
    KeyType key;
    Object pobj;//关键字所对应的记录集  或 引用计数
}
//分支节点有可能指向分支结点或元素结点
class BrachType extends  TireNode{
    int num;//本结点关键码或分支的个数。 如果只有一个分支，就要连续删除
    TireNode[] link=new TireNode[27];//0下标特殊用途，1。。。26是英文字母;  分支结点空间浪费有点大
}

class TTree{
    TireNode root;
}

public class Teacher_5_30_TireTree {
    public static void main(String[] args) {
        ////测试枚举类型
//        TireNode link=new TireNode();
//        link.kind=branch;
    }
}
