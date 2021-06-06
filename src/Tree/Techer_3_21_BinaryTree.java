package Tree;
//变量名res是result   ptr是pointer的缩写
import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * @author QiangQin
 * * @date 2021/5/18
 */
class BinaryTree {
    static class BtNode //binaryTreeNode的缩写
    {
        int data;
        BtNode leftChild;
        BtNode rightChild;

        //构造函数
        public BtNode() {
            data = 0;
            leftChild = rightChild = null;
        }

        public BtNode(int x) {
            data = x;
            leftChild = rightChild = null;
        }

        public BtNode(int x, BtNode left, BtNode right) {
            data = x;
            leftChild = left;
            rightChild = right;
        }
    }

    private BtNode root;
    private BtNode cur;//当前节点
    private BtNode ans;//最后一个函数，找最近公共祖先用到了

    public BtNode getRoot() {
        return root;
    }

    public BinaryTree() {
        root = null;
        cur = null;
        ans=null;
    }

    public BinaryTree(BtNode tmp) {
        root = tmp;
    }

    //先序遍历
    public void PreOrder(BtNode ptr) {
        if (ptr != null) {//此时才有子节点
            System.out.print(ptr.data + " ");
            PreOrder(ptr.leftChild);
            PreOrder(ptr.rightChild);
        }
    }

    //非递归 实现 前序（根 左 右） 遍历（重要！！！！）
    public void NicePreOrder() {
        if (root == null)
            return;
        Stack<BtNode> st = new Stack<BtNode>();
        st.push(root);//从根节点能带起来一串树
        while (!st.empty()) {
            //打印栈顶并出栈
            BtNode ptr = st.peek();//同样可以获取队首元素，但是与poll不同的是并不会将该元素从队列中删除。
            st.pop();
            System.out.print(ptr.data + " ");

            //注意压入时是先右后左，出栈打印时才能先左后右
            if (ptr.rightChild != null) {
                st.push(ptr.rightChild);
            }
            if (ptr.leftChild != null) {
                st.push(ptr.leftChild);
            }
        }
        System.out.println();
    }

    public void PreOrder() {
        PreOrder(root);
        System.out.println();
    }


    //中序遍历
    private void InOrder(BtNode ptr) {
        if (ptr != null) {
            InOrder(ptr.leftChild);
            System.out.print(ptr.data + " ");
            InOrder(ptr.rightChild);
        }
    }

    public void InOrder() {
        InOrder(root);
        System.out.println();
    }

    //    非递归 实现 中序遍历（重要！！！！）
    void NiceInOrder() {
        if (root == null) return;
        BtNode ptr = root;
        Stack<BtNode> st = new Stack<BtNode>();
        while (ptr != null || !st.empty()) {
            //左孩子入完
            while (ptr != null) {
                st.push(ptr);
                ptr = ptr.leftChild;
            }
            //根
            ptr = st.peek();
            st.pop();
            System.out.print(ptr.data + " ");
            //右（下面又找右孩子的所有左孩子）
            ptr = ptr.rightChild;
        }
        System.out.println();
    }

    //后序遍历
    public void PastOrder(BtNode ptr) {
        if (ptr != null) {
            PastOrder(ptr.leftChild);
            PastOrder(ptr.rightChild);
            System.out.print(ptr.data + " ");
        }
    }

    //    非递归 实现 后序遍历（重要！！！！）
    void NicePastOrder() {
        if (root == null) return;
        BtNode ptr = root;//ptr指向需要打印的节点
        Stack<BtNode> st = new Stack<BtNode>();
        BtNode tag = null;////这个标记位   是记录上一个打印的值，  防止无限循环进入右孩子进行打印??????
        while (ptr != null || !st.empty()) {
            //左孩子入完
            while (ptr != null) {
                st.push(ptr);
                ptr = ptr.leftChild;
            }
            //左到头的根（不着急打印，看右孩子）
            ptr = st.peek();
            st.pop();

            //ptr的右边打印完了时
            if (ptr.rightChild == null || ptr.rightChild == tag) {
                System.out.print(ptr.data + " ");
                tag = ptr;
                ptr = null;//这样ptr的 父节点 指向 ptr的这一分支就算处理完了
            } else {
                //ptr的右孩子不为空  且 没打印时，处理他第一个右孩子的所有左孩子
                st.push(ptr);
                ptr = ptr.rightChild;
            }
        }
        System.out.println();
    }

    public void PastOrder() {
        PastOrder(root);
        System.out.println();
    }

    int index = 0;

    //通过输入顺序存储的前序遍历序列创建一个二叉树（好像能唯一确定）
    public BtNode CreateTreeByPreOrder(int[] str) {
        if (str.length < 0) {
            System.out.println("输入异常");
            return null;
        }

        BtNode s = null;//不管s被不被修改，都返回s
        if (index >= 0 && index < str.length) {
            if (str[index] != -1) {//即  值为-1对应 空节点，不处理，直接返回为null的s
                s = new BtNode(str[index]);//s指向具体的结点
                if (index == 0) {
                    root = s;//让 根节点 指向 先序遍历的首元素所对应的节点
                }
                index++;//要处理其孩子节点前，先移动指针到下一个元素
                s.leftChild = CreateTreeByPreOrder(str);
                index++;
                s.rightChild = CreateTreeByPreOrder(str);
            }

        }
        return s;
    }

    //    在指定范围内 找到 某个根节点  在中序序列中的位置pos   （好像可以 应对 重复值，有多个位置）
    private int FindIs(int[] in, int begin, int end, int val) {
        int pos = -1;
        for (int i = begin; i <= end; ++i) {
            if (in[i] == val) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    // P I P（先中后 单词首字母）
//PI即通过 先 序和 中 序遍历创建二叉树（递归怎么实现？？？？？）
//    ps 是pre的start下标的意思(传入同一棵子树的前序和中序遍历序列)
//    先序遍历可以确定出树的根节点即第一个元素就是根节点，中序遍历可以确定左子树和右子树，接下来依次递归，直到区间的个数<=0就能确定出这棵树
    private BtNode CreatePI(int[] pre, int ps, int pe, int[] in, int is, int ie) {
        BtNode s = null;
        if (pe - ps >= 0) {
            s = new BtNode(pre[ps]);
            int pos = FindIs(in, is, ie, pre[ps]);
            if (pos == -1)
                return null;
            int dist = pos - is;//左子树长度（end一减就是右子树长度）
            //分治  递归调用自己
            //先序序列下标（根左右）：ps(root位置)   ps+1      ...           ps+dist             ps+dist+1     ....   pe
            //中序序列下标（左根右）：is             ...     is+dist-1      is+dist(root位置)    is+dist+1     ....   ie
            s.leftChild = CreatePI(pre, ps + 1, ps + dist, in, is, is + dist - 1);
            s.rightChild = CreatePI(pre, ps + dist + 1, pe, in, is + dist + 1, ie);
        }
        return s;
    }

    public void CreateTreePI(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length < 1 || in.length < 1 || pre.length != in.length) {
            return;
        }
        int n = pre.length;
        root = CreatePI(pre, 0, n - 1, in, 0, n - 1);
    }


    //IP即通过中序和后序遍历创建一棵二叉树  （常考   递归  非递归实现）
//    后续遍历可以确定出树的根节点即最后一个元素就是根节点，中序遍历可以确定左子树和右子树，接下来依次递归，直到区间的个数<=0就能确定出这棵树
    public void CreateTreeIP(int[] in, int[] pa) {
        if (pa == null || in == null ||
                pa.length < 1 || in.length < 1
                || pa.length != in.length) {
            return;
        }
        int n = pa.length;
        root = CreateIP(in, 0, n - 1, pa, 0, n - 1);
    }

    private BtNode CreateIP(int[] in, int is, int ie, int[] pa, int ps, int pe) {
        BtNode s = null;
        if (ie - is >= 0) {
            s = new BtNode(pa[pe]);
            int pos = FindIs(in, is, ie, pa[pe]);
            if (pos == -1)
                return null;
            int dist = pos - is;
            s.leftChild = CreateIP(in, is, is + dist - 1, pa, ps, ps + dist - 1);
            s.rightChild = CreateIP(in, is + dist + 1, ie, pa, ps + dist, pe - 1);
        }
        return s;
    }


//给先序和后序为什么无法创建二叉树？？？
// 都是将父节点与子结点进行分离，但并没有指明左子树和右子树的能力，而不能确定一个二叉树（wps图）。


    //非递归 层次打印（类似网址：https://blog.csdn.net/IT_Quanwudi/article/details/99608727）
    public void NiceLevelOrder() {
        if (root == null)
            return;
        Queue<BtNode> qu = new LinkedList<BtNode>();
        qu.offer(root);//添加进队列里
        while (!qu.isEmpty()) {
            BtNode ptr = qu.poll();//返回 并删除 队头
            System.out.print(ptr.data + " ");
            //先压左孩子后右  因为队列先进先出，所以最后按压入顺序依次弹出由上到下的每一层元素
            if (ptr.leftChild != null) {
                qu.offer(ptr.leftChild);
            }
            if (ptr.rightChild != null) {
                qu.offer(ptr.rightChild);
            }
        }
        System.out.println();
    }


    //查找树里有没有含有某值的节点，找到就返回（考的不多）
    private BtNode FindValue(BtNode ptr, int val) {
        //根 左 右的查找顺序
        if (ptr == null || ptr.data == val)
            return ptr;
        else {
            //分治
            BtNode p = FindValue(ptr.leftChild, val);//层层往下，直到最底层的左子树
            if (p == null) {
                p = FindValue(ptr.rightChild, val);
            }
            return p;
        }
    }

    public boolean FindValue(int val) {
        boolean res = true;
        cur = FindValue(root, val);
        if (cur == null)//没有有效的返回值时
        {
            res = false;
        }
        return res;
    }


    //是否为完全二叉树（对树中的结点按从上至下、从左到右的顺序进行编号，如果编号为i（1≤i≤n）的结点与满二叉树中编号为i的结点在二叉树中的位置相同，则）
    public boolean Is_Comp() {
        boolean res = true;
        if (root == null)
            return res;
        Queue<BtNode> qu = new LinkedList<BtNode>();
        //boolean offer(E e)方法-----向队列末尾追加一个元素
        qu.offer(root);//根 左 右的顺序将元素添加进队列
        while (!qu.isEmpty()) {
            // E poll()方法-----从队首获取元素。注意，获取后该元素就从队列中被移除了!出队操作
            BtNode ptr = qu.poll();//ptr为队头，即根元素，一般不为空，除非他的左或右子女为空了
            if (ptr == null)//发现一个不符合，就可以定性了
                break;
            qu.offer(ptr.leftChild);//ptr即子树的根节点不为null时，哪怕左右子树有一个是null值，都要加进来
            qu.offer(ptr.rightChild);
        }

        //若qu没排空，即二叉树中间有空位置（WPS图】）
        while (!qu.isEmpty()) {
            if (qu.poll() != null) {
                res = false;
                break;
            }
        }
        return res;
    }


    //是否为满二叉树(高度为h，并且由2^h –1个结点的二叉树)
    public boolean Is_Full() {
        int num = 1;
        boolean res = true;//标志是否为满
        if (root == null)//空树
            return res;
        Queue<BtNode> qu = new LinkedList<BtNode>();
        qu.offer(root);
        while (!qu.isEmpty()) {
            int n = num;//该层应该的个数
            int i = 0;//该层实际出队元数的个数

            //一旦发现，队中剩余元素 比 这层期望的少，肯定不满，立马退出（WPS图）
            if (qu.size() < n) {
                res = false;
                break;
            }

           /*
              i<n   而  qu.size>=n,所以qu空不了
              将本层所有节点出队，并将其子女入队
              因为是满二叉树，所以应该出n个
             （出一层   入一层 ，一旦发现层不满，就退出）
             */
            while (i < n) {
                BtNode ptr = qu.poll();//每取出一个根，就把他的左右子节点追加到队列尾
                ++i;//记录出队元素的个数
                if (ptr.leftChild != null)
                    qu.offer(ptr.leftChild);
                if (ptr.rightChild != null)
                    qu.offer(ptr.rightChild);
            }

//            个人感觉用不上，老师却写了的代码
//         while (i < n && !qu.isEmpty())  //         还没记录够  且  队不空
//            //即若队为空了，实际出队元素却没达到改层理论值
//            if (i < n) {
//                res = false;
//                break;
//            }
            num += num;//每层的节点数应该是成倍增长的 1-》2-》4
        }
        return res;
    }


    //获取 以当前节点为根 的 子树节点 的个数
    private int GetSize(BtNode ptr) {
        if (ptr == null)
            return 0;
        else
            //ptr叶时，0+0+1，而后逐层返回，才统计了数量
            return GetSize(ptr.leftChild) + GetSize(ptr.rightChild) + 1;
    }

    public int GetSize() {
        return GetSize(root);
    }

    //   获取当前树的深度（左右子树以深的为准）
    private int GetDepth(BtNode ptr) {
        if (ptr == null)
            return 0;
        else
            return Math.max(GetDepth(ptr.leftChild), GetDepth(ptr.rightChild)) + 1;
    }

    public int GetDepth() {
        return GetDepth(root);
    }

    //获取树的高度（非递归  层次遍历）
    public int GetHeight() {
        int h = 0;
        if (root == null)                                       //入一层
            return h;
        Queue<BtNode> qu = new LinkedList<BtNode>();
        qu.offer(root);

        //为空，表示没有下一层
        while (!qu.isEmpty()) {
            int n = qu.size();//上一轮入的所有元素，即是该层节点的个数（不一定满）
            int i = 0;
            //将queue里上一轮入的全出队，<n是防止越queue界         出一层
            while (i < n) {
                BtNode ptr = qu.poll();
                ++i;//记录出队的个数
                //将上一轮所有节点的左右孩子追加到队列后面           入一层
                if (ptr.leftChild != null)
                    qu.offer(ptr.leftChild);
                if (ptr.rightChild != null)
                    qu.offer(ptr.rightChild);
            }
            h += 1;
            //入一层，   出一层 ,入一层, 出一层 ,入一层
        }
        return h;
    }

//  非递归  按层遍历   https://blog.csdn.net/ldstartnow/article/details/52971422



   //题目：传入树根，在里面找到当前结点的双亲结点（根节点没有父节点）  （是找到值对应结点的变种题目）
   //此函数体真正处理找父亲逻辑（和FindValue几乎一样）   层层函数调用，逻辑更清晰
    private BtNode Parent(BtNode ptr, BtNode child) {
        //找到叶子了  或者找到父了，就返回
       if (ptr == null || ptr.leftChild == child || ptr.rightChild == child) {
           return ptr;
       } else {
           //递归调用（根 左 右的遍历顺序，没有就继续往下找）
           BtNode p = Parent(ptr.leftChild, child);
           if (p == null) {
               p = Parent(ptr.rightChild, child);
           }
           return p;
       }
   }
    private boolean FindParent(BtNode ptr, BtNode child) {
        boolean res = false;
        cur = null;
        //child（即函数调用方传的cur）等于ptr时，即根节点没有父节点     优先级：！> && >||
        if (ptr != null && child != null && ptr != child) {
            cur = Parent(ptr, child);//将指针执行父，并返回真
            if (cur != null) {
                res = true;
            }
        }
        return res;
    }
    public boolean FindParent() {
        return FindParent(root, cur);
    }





    //给定一个二叉树, 找到该树中两个指定节点(不一定哪个是左 哪个是右结点)的最近公共祖先
    // 一个节点也可以是它自己的祖先   两个结点的最近公共祖先可能隔了好几代     p和q节点都是不同且唯一的节点
//    时间复杂度：O(n)    每个结点会并且只会访问一次
//    空间复杂度:O（n）  （递归调用的栈深度最大为二叉树的高度）
//    https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
    private boolean dfs(BtNode root,BtNode p, BtNode q) {
        //根  左 右的遍历方式
        if (root == null)
            return false;
        //层层递归深入，找到 结点的 左右子树分别包含p或q一个的，即该结点是同时包含p q的最深的结点
        boolean lson = dfs(root.leftChild, p, q);//左 子树 是否包含p 或q中的某一个
        boolean rson = dfs(root.rightChild, p, q);
        if (    (lson && rson) || //说明左子树和右子树均包含 p 节点或 q 节点，如果左子树包含的是 p节点，那么右子树只能包含 q节点，反之亦然，
                (  (root.data==p.data||root.data==q.data) && (lson||rson))  ) { //即 root 恰好是 p或 q节点 且 它的左或右子树的一个包含了另一个节点
            ans = root;//一旦发现，立马更新ans
           /*
                此时回退到上一层，其他公共祖先 是无法再被判断为符合条件了，
                因为如果root的左子树已经包含p、q全部了，那么root的右子树肯定不包含p、q，即root的rson为false，注意root这层dfs返回的是true
                层层回退，就从树的根节点那一层dfs就退出了
           */
        }
       /*
          层层深入直到叶子结点后，总之至少有一个p或q，这一层dfs的返回结果就为真，给上一层的lson或rson
         叶节点值他只能等于p或q的一个，但往上的根节点的lson、rson可能同时为真
         */
        return lson || rson || (root.data == p.data || root.data == q.data);
    }
    public BtNode FindNParent(BtNode ptr, BtNode first, BtNode second) {
        dfs(ptr,first,second);
        return this.ans;
    }



    //非递归  按数组顺序存放 转化为  二叉树，返回根节点
    public BtNode arrayToBTree(int[] arrs) {
        if (arrs == null || arrs.length == 0) {
            return null;
        }

        //将值放在结点里，所有结点放在nodes里
        List<BtNode> nodes = new ArrayList<BtNode>(arrs.length);
        for (int value : arrs) {
            BtNode treeNode = new BtNode();
            treeNode.data = value;
            nodes.add(treeNode);

        }

        //处理根结点的子女关系    i指向除最后一个根节点外的前面所有根节点的位置
        // 最后一个结点（arrs.length-1）的父节点下标为：（arrs.length - 1）/2  （值向下取整）
        for (int i = 0; i < arrs.length/2 - 1; i++) {
            BtNode node = nodes.get(i);
            if(nodes.get(i*2 + 1).data!=-1){
                node.leftChild = nodes.get(i*2 + 1);
            }
            if(nodes.get(i*2 + 2).data!=-1){
                node.rightChild = nodes.get(i*2 + 2);
            }
        }
        //最后一个父节点(wps图，下标从0开始，4的孩子下标分别为9 10)
        int lastPNodeIndex =arrs.length/2-1 ;
        BtNode lastPNode = nodes.get(lastPNodeIndex);
        lastPNode.leftChild = nodes.get(lastPNodeIndex*2 + 1);
        // 只有当总节点数是奇数时，最后一个父节点才有右子节点
        if (arrs.length%2 != 0) {
            lastPNode.rightChild = nodes.get(lastPNodeIndex*2 + 2);
        }
        root=nodes.get(0);
        return root;
    }


}


public class Techer_3_21_BinaryTree {

    //给定按数组存放的二叉树，前序遍历
    private  static void PreOrderArray(int[] artree, int i, int length) {
        //i是artree下标范围，属于0.。。。length-1    -1为空值
        if(i<length && artree[i]!=-1){
            System.out.print(artree[i]+" ");
            PreOrderArray(artree,i*2+1,length);//若新i不满足if，递归后就啥都不做
            PreOrderArray(artree,i*2+2,length);
        }
    }
    private  static void InOrderArray(int[] artree, int i, int length) {
        if(i<length && artree[i]!=-1){
            InOrderArray(artree,i*2+1,length);//类比 .leftChild
            System.out.print(artree[i]+" ");
            InOrderArray(artree,i*2+2,length);
        }
    }




    public static void main(String[] args) {
        ////  //Teacher_3_21
//        //前序遍历序列按顺序存储,空节点值为-1（详图见  第1节二叉树文档）
//        int[] str = {1, 2, 3, -1, -1, 4, 5, -1, -1, 6, -1, -1, 7, -1, 8, -1, -1};
//        BinaryTree myt = new BinaryTree();
//        myt.CreateTreeByPreOrder(str);
//        System.out.println(myt.getRoot().rightChild.data);
//        myt.PreOrder(myt.getRoot());

//
////        //Teacher_3_27
        BinaryTree myt = new BinaryTree();
//        int[] pre = {3, 9, 8, 5, 10, 20, 15, 7};
//        int[] in = {8, 9, 10, 5, 20, 3, 15, 7};
////        int[] pa = {8, 10, 20, 5, 9, 7, 15, 3};
//        myt.CreateTreePI(pre, in);
//////        myt.CreateTreeIP(in, pa);
////        myt.NicePreOrder();
////        myt.NiceInOrder();
////        myt.NicePastOrder();
////        myt.NiceLevelOrder();
////        System.out.println(myt.GetDepth());
//        System.out.println(myt.GetHeight());
//        System.out.println(myt.Is_Full());


         //Teacher_4_3
        //给定按顺序存放在数组的二叉树，判断是不是完全二叉树,进行前序遍历。。。   -1代表是空结点
        int[] artree={3, 9, 15, 8, 5, -1, 7, -1, -1, 10, 20};
        //            0  1   2  3  4  5   6   7   8   9  10
//        PreOrderArray(artree,0,artree.length);
//        System.out.println();
//        InOrderArray(artree,0,artree.length);
        myt.arrayToBTree(artree);
//        System.out.println(myt.getRoot().rightChild.rightChild.data);

    }



}
