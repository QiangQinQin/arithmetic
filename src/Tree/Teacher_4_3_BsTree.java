package Tree;

import java.util.Stack;

class BSTree {
    class BstNode {
        int key;
        //即三叉链表
        BstNode leftchild;
        BstNode parent;//为了删除结点时方便处理
        BstNode rightchild;

        //构造函数
        public BstNode() {
            key = 0;
            leftchild = parent = leftchild = null;
        }

        public BstNode(int x) {
            key = x;
            leftchild = parent = leftchild = null;
        }

        public BstNode(int x, BstNode left, BstNode pa, BstNode right) {
            key = x;
            leftchild = left;
            parent = pa;
            rightchild = right;
        }
    }

    BstNode root;
    BstNode cur;

    public BSTree() {
        root = null;
        cur = null;
    }


    //   找到了值对应的结点就用cur指向，并返回真s（wps图）
// 二叉搜索树：左 < 根 < 右  （是小的，在左边没有的话，右边更大肯定没有）
    //递归 处理找
    private BstNode Search(BstNode ptr, int kx) {
        if (ptr == null || ptr.key == kx) {
            return ptr;
        } else if (kx < ptr.key) {
            return Search(ptr.leftchild, kx);
        } else {
            return Search(ptr.rightchild, kx);
        }
    }

    //处理结果
    public boolean SearchValue(int kx) {
        boolean res = false;
        cur = Search(root, kx);
        if (cur != null) {
            res = true;
        }
        return res;
    }

    //非递归
    public boolean FindValue(int kx) {
        cur = root;
        boolean res = false;
        //层层往下找
        while (cur != null && cur.key != kx) {
            cur = kx < cur.key ? cur.leftchild : cur.rightchild;
        }
        //处理结果
        if (cur != null && cur.key == kx) {
            res = true;
        }
        return res;
    }


    //找到指定值的结点,偷偷修改
    public boolean FindValue_A(int kx) {
        cur = root;
        boolean res = false;
        while (cur != null && cur.key != kx) {
            cur = kx < cur.key ? cur.leftchild : cur.rightchild;
        }
        if (cur != null && cur.key == kx) {
            cur.key = 100;//故意修改，破坏有序性
            res = true;
        }
        return res;
    }

    //    插入
    public boolean Insert(int kx) {
        boolean res = true;
        //空树时
        if (root == null) {
            root = new BstNode(kx);
            return res;
        }

        cur = root;
        BstNode pa = null;
        //在合适的分支找到底为止
        while (cur != null && cur.key != kx) {
            pa = cur;//找cur的孩子，所以升级当爸了
            cur = kx < cur.key ? cur.leftchild : cur.rightchild;
        }
        //有该值，就不插入（false,因为不允许重复）
        if (cur != null && cur.key == kx) {
            res = false;
        } else {//没有该值
            cur = new BstNode(kx);
            cur.parent = pa;//子指向父
            //将新结点挂在上面查找的最后一个位置的左或右孩子
            if (cur.key < pa.key) {
                pa.leftchild = cur;//父指向子
            } else {
                pa.rightchild = cur;
            }
        }
        return res;
    }


    //非递归的中序遍历
    public void NiceInOrder() {
        //先合法性判断
        if (root == null)
            return;
        Stack<BstNode> st = new Stack<BstNode>();
        cur = root;
        while (cur != null || !st.isEmpty()) {
            while (cur != null) {
                st.push(cur);//栈：先进后出
                cur = cur.leftchild;
            }
            // 即把左到底的最后的孩子先输出
            cur = st.pop();
            System.out.print(cur.key + " ");
            //即左 根 右的遍历顺序
            cur = cur.rightchild;
        }
        System.out.println();
    }



    // 非递归，实现中序遍历（不准使用栈或队列）
    //Map、红黑树的迭代的底层实现就用到了下面的first和next
    private BstNode First(BstNode ptr) {
        //左到底   即以ptr为根的树的min
        while (ptr != null && ptr.leftchild != null) {
            ptr = ptr.leftchild;
        }
        return ptr;
    }


   //找中序序列的直接后继
    private BstNode Next_InOrder(BstNode ptr)
    {
        if(ptr == null)
            return null;

        if(ptr.rightchild != null)
        {
            return  First(ptr.rightchild);//直接后继为 右子树的最左下结点
        }
        else
        {
            BstNode pa = ptr.parent;
            //wps图 45的后继是53   94后继为空
            while(pa != null && pa.leftchild != ptr)
            {
                ptr = pa;
                pa = ptr.parent;
            }
            return pa;
        }
    }
    //非递归 不用栈 实现中序正向遍历
    private void NiceInOrder_For(){
        //如果有head结点，就可以不用!=null作为结束标记
        for(BstNode p=First(root);p!=null;p=Next_InOrder(p)){
            System.out.print(p.key+" ");

        }
        System.out.println();

    }
//=============================中序正向和逆向遍历的代码互为镜像===================================
    //找中序序列直接前驱
    private BstNode Last(BstNode ptr) {
        //右到底   相当于Max
        while (ptr != null && ptr.rightchild != null) {
            ptr = ptr.rightchild;
        }
        return ptr;
    }
    private BstNode Prev_InOrder(BstNode ptr) {
        if (ptr == null)
            return null;
        if (ptr.leftchild != null) {
            //左子树的最右下  53的前驱是45
            return Last(ptr.leftchild);
        } else {
            //即左子树为空时
            BstNode pa = ptr.parent;
            //9没有前驱    65的前驱是53
            while (pa != null && pa.rightchild != ptr) {
                ptr = pa;
                pa = ptr.parent;
            }
            return pa;
        }
    }

    //非递归实现逆向遍历
    private void Reverse_NiceInOrder(){
        //如果有head结点，就可以不用!=null作为结束标记   prev相当于迭代器，上一个上一个
        for(BstNode p=Last(root);p!=null;p=Prev_InOrder(p)){
            System.out.print(p.key+" ");

        }
        System.out.println();

    }


//    非递归   中序遍历基础上将二叉 树 改成二叉 链表！！！
        public void InOrderList() {
        if (root == null)
            return;
        Stack<BstNode> st = new Stack<BstNode>();
        cur = root;
        BstNode pr = null;

        while (cur != null || !st.isEmpty()) {
            //左
            while (cur != null) {
                st.push(cur);
                cur = cur.leftchild;
            }
            //根（即访问的结点）
            cur = st.pop();//一个元素能且只能进栈一次
            //在非递归实现中序遍历时是pop后要将cur打印，这里不需要，只要处理一个当前结点与上一个结点的关系即可


            //即第一次进入while时，此时cur是第一个结点
            if(pr == null)
            {
                root = cur;
                pr = cur;//最近一次访问的结点
            }
            else
            {
                pr.rightchild = cur;//即pr的直接后继 是 这次访问的结点cur
                cur.leftchild = pr;// 这次访问的结点cur的直接前驱 是 pr
                pr = cur;
            }
            //右
            cur = cur.rightchild;
        }
        System.out.println();
    }

    //将上面转为链表的二叉树打印查看
    public void PrintList()
    {
        BstNode p = root;
        //最后一个节点的next没有设置，所以为null
        while(p != null)
        {
            System.out.print(p.key + " ");
            p = p.rightchild; //next
        }
        System.out.println();
    }

    //非递归   判断是不是二叉排序树（即在中序遍历的基础上，将输出值加个判断）（wps图，带个例子就明白了）
    public boolean Is_BSTree() {
        boolean res = true;
        if (root == null)
            return res;
        Stack<BstNode> st = new Stack<BstNode>();
        cur = root;
        BstNode pre = null;//按中序遍历顺序 的 上一个访问的结点，

        //直到找完所有的结点
        while (cur != null || !st.isEmpty()) {
            //左到底(若cur为空，直接执行下面的pop)
            while (cur != null) {
                st.push(cur);//共用一个栈，都往里面压
                cur = cur.leftchild;
            }
            //根  并  判断是否满足  中序遍历依次是由小到大的顺序
            cur = st.pop();//先进后出（第一次弹出叶子节点,肯定 无在他之前弹出来的元素）
            if (pre != null && pre.key >= cur.key) {
                res = false;
                break;//一旦发现，立马退出while,接着退出唯一的一层Is_BSTree
            }
            pre = cur;
            // 右
            cur = cur.rightchild;
        }

        return res;
    }


    //左到底
    private BstNode Next(BstNode ptr) {
        while (ptr != null && ptr.leftchild != null) {
            ptr = ptr.leftchild;
        }
        return ptr;
    }

    //    删除指定值（唯一）的结点
    public boolean Remove(int kx) {
        boolean res = false;
        //空树
        if (root == null)
            return res;

        BstNode pa = null;
        cur = root;
        //按中序找该节点，有就用cur指向,pa指向其父
        while (cur != null && cur.key != kx) {
            pa = cur;
            cur = kx < cur.key ? cur.leftchild : cur.rightchild;
        }
        //没找着
        if (cur == null)
            return res;

        //双分支（即左右孩子都有时）
        if (cur.leftchild != null && cur.rightchild != null) {
            BstNode nt = Next(cur.rightchild);//nt为后继结点（即右子树的最左 且 最下！！！的节点）
            cur.key = nt.key;//用后继结点的值 覆盖 要删除的结点值（然后删除后继节点nt即可）
            pa = nt.parent;//后继节点（nt）可能为叶子或有右子树，所以需再处理下 后继节点的父亲(pa)的child
            cur = nt;//cur指向后继结点

        }


       /*
        若进了上面的if,  cur指向原本的后继节点（值被用了，现在他可以作为被删除结点处理了）只能为 叶   或 只有右子树
        若没进if,cur是要删除的结点 且 没有双孩子，能为 叶子 或 单分支（只有右子树  只有左子树）
        */
        BstNode child = (cur.leftchild != null) ? cur.leftchild : cur.rightchild;
        //待删除结点cur为叶时，上面处理完child为null    cur有右子树时，需要将右子树提上 挂到 后继节点的父亲下
        if (child != null)
            child.parent = pa;            //cur的孩子 指向 cur的父！！！！！！！！！！

        //即待删除结点是根节点，其值提到被删除结点处后，然后结点便要被删除了，此时需要让root指向待删除结点的孩子
        if (pa == null) {
            root = child;
        } else {
        /*
             cur为叶时，child为null,其父指向child即指向空
             cur为单分支时，将cur的子树 重挂在其父的左或右边
             父 指向 子的子（这下cur不指向谁，也没人指向，为空引用，会被自动垃圾回收）！！！！！！！！！！
             别用child.key<pa.key，因为child有可能为null.
             */
            //即待删除结点在左分支时，把待删除结点的子树 往上提，重新挂在 待删除结点父亲的左边
            if (cur.key < pa.key) {
                pa.leftchild = child;
            } else {
                pa.rightchild = child;
            }
        }
        return true;
    }


}

public class Teacher_4_3_BsTree {
    public static void main(String[] args) {
        //二叉树顺序存储
        int[] ar = {53, 17, 78, 9, 45, 65, 87, 23, 81, 94, 88, 92};
        BSTree myt = new BSTree();
        //用插入方式建立二叉排序树
        for (int i = 0; i < ar.length; ++i) {
            myt.Insert(ar[i]);
        }
        //中序遍历 有序：9 17 23 45 53 65 78 81 87 88 92 94
        myt.NiceInOrder();
        boolean res = myt.Is_BSTree();
        System.out.println("是否为二叉排序树：" + res);

//        myt.FindValue_A(87);//找到值为87的结点修改为100，破坏中序遍历有序性
        myt.Remove(87);
        myt.NiceInOrder();
        res = myt.Is_BSTree();
        System.out.println(res);

    }
}