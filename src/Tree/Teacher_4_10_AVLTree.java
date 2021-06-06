package Tree;

class AVLTree {
    class AVLNode {
        AVLNode leftchild;
        AVLNode parent;
        AVLNode rightchild;
        int balance; // -1 ,0 , 1
        int key;

        public AVLNode() {
        }

        public AVLNode(int x) {
            key = x;
            balance=0;
            leftchild = parent = leftchild = null;
        }
    }

    private AVLNode root;
    private AVLNode cur;

    public AVLTree() {
        this.root = null;
        this.cur = null;
    }

    //成员函数
    //左单旋（wps图）   ptr是发现的第一个不平衡的子树的根   先主干，再完善   一个函数只完成一个单一的功能，所以不管平衡因子的事
    private void RotateLeft(AVLNode ptr) {
        //第1步（newroot代替ptr，指向原来ptr的父亲）
        AVLNode newroot = ptr.rightchild;
        newroot.parent = ptr.parent;
        //第2步
        ptr.rightchild = newroot.leftchild;//父指向子
        if (newroot.leftchild != null) //子若有，就指向父
        {
            newroot.leftchild.parent = ptr;
        }
        //第3步
        newroot.leftchild = ptr;

        //ptr原来的父 指向 新子
        AVLNode pa = ptr.parent;
        if (pa == null)//说明ptr是根节点
        {
            root = newroot;
        } else {
            //原来ptr挂在双亲的左边，现在旋转后的新根就仍然挂在左边
            if (pa.leftchild == ptr) {
                pa.leftchild = newroot;
            } else {
                pa.rightchild = newroot;
            }
        }
        //上面处理完了ptr原来的父，现在ptr就可以指向自己的新父
        ptr.parent = newroot;
    }


    /*
     右单旋  和左旋为镜像
     代码分为三块：
          旋转（1 2 3步）
          维护双亲
          维护新根
     */
    private void RotateRight(AVLNode ptr) {
        AVLNode newroot = ptr.leftchild;//旋转1
        newroot.parent = ptr.parent;
        ptr.leftchild = newroot.rightchild;//旋转2   newroot.rightchild可能为null
        if (newroot.rightchild != null) {
            newroot.rightchild.parent = ptr;
        }
        newroot.rightchild = ptr;//旋转3
        AVLNode pa = ptr.parent;//ptr 和 newroot从头到尾指向各自的同一个结点，没变过！！！！
        if (pa == null) {
            root = newroot;
        } else {
            if (pa.leftchild == ptr) {
                pa.leftchild = newroot;
            } else {
                pa.rightchild = newroot;
            }
        }
        ptr.parent = newroot;//旋转3
    }

    //    先左后右双旋转（保证中序有序  以及  各节点平衡因子尽可能小）   好像不会出现平衡因子大于1或小于-1的，因为插入!!!的时候发现不平衡立马就进行旋转调整了
    private void LeftBalance(AVLNode ptr) {
        // wps图，ptr指向根（即A）！！！    leftsub指向B！！！
        AVLNode leftsub = ptr.leftchild, rightsub = null;
        switch (leftsub.balance) {
            case 0:
                System.out.println("left balance \n");
                break;
            case -1: //即B的右子树-B左子树的深度=-1   即插到D下面（看图知，调整后平衡因子为0）
                ptr.balance = 0;
                leftsub.balance = 0;//这里用不到rightsub，即E的平衡因子没有变化
                //不管是在D的左孩子还是右孩子 插入 ，总之深度＋1了，旋转方式是一样的
                RotateRight(ptr);
                break;
            case 1:// 即B的右子树比左大1，即插入在B的右树       rightsub指向E！！！
                rightsub = leftsub.rightchild;
                //先专门调整  双旋后的banlance值
                switch (rightsub.balance) {
                    case 1://1即G处插入
                        ptr.balance = 0;
                        leftsub.balance = -1;
                        break;
                    case 0://即特殊情况 折线
                        ptr.balance = 0;
                        leftsub.balance = 0;
                        break;
                    case -1://-1即F处插入
                        ptr.balance = 1;
                        leftsub.balance = 0;
                        break;
                }
                rightsub.balance = 0;
                //即F 或 G处插入，旋转方式是一样的
                RotateLeft(leftsub);//即以B结点为根的树
                RotateRight(ptr);// A
                break;
        }
    }

    //   先右后左双旋转(wps图)
    private void RightBalance(AVLNode ptr) {   //ptr即A   rightsub即C ！！！
        AVLNode rightsub = ptr.rightchild, leftsub = null;
        switch (rightsub.balance) {
            case 0:
                System.out.println("right balance \n");
                break;
            case 1://E处插放入
                ptr.balance = 0;
                rightsub.balance = 0;
                RotateLeft(ptr);
                break;
            case -1: //D的子树里插入
                //leftsub即D   ！！！
                leftsub = rightsub.leftchild;
                switch (leftsub.balance) {
                    case 1://G处插入
                        ptr.balance = -1;//可以看图想象  也可以对比LeftBalance
                        rightsub.balance = 0;
                        break;
                    case 0:
                        ptr.balance = 0;
                        rightsub.balance = 0;
                        break;
                    case -1://F处插入
                        ptr.balance = 0;
                        rightsub.balance = 1;
                        break;
                }
                leftsub.balance = 0;
                RotateRight(rightsub);
                RotateLeft(ptr);
                break;
        }

    }

    private void Adjust_AVL(AVLNode ptr) {
        AVLNode pa = ptr.parent;
        boolean high = true;//标志高度是否发生改变

        //若ptr的双亲为空(即为根)  或 插入后层高没变  或 将由底往上第一个不平衡节点的子树调整好了，就不用 再 进行调整了
        while (high && pa != null) {
            //说明ptr插入到了其夫（pa）的左边
            if (pa.leftchild == ptr) {
                switch (pa.balance) {
                    case 0: //即本来平衡因子为0，插入到左边，右-左=-1，pa高度发生了改变！！！（需要下轮while调整其父的平衡因子）
                        pa.balance = -1;
                        break;
                    case 1://本来右-左=1  现在插入左，右-左=0，以pa为根的树的最大高度没有发生改变
                        pa.balance = 0;
                        high = false;
                        break;
                    case -1:
                        LeftBalance(pa);//左边不平衡了，所以调用该算法（里面有调整平衡因子），旋转后的层高（以E为新根） 和 插入前的层高（以A为根）没有改变（都为h+1）(参考双选转的图)（故无需调整其父的平衡因子）
                        high = false;
                        break;
                }
            }
            else {
                switch (pa.balance) {
                    case 0:
                        pa.balance = 1;
                        break;
                    case -1:
                        pa.balance = 0;
                        high = false;
                        break;
                    case 1:
                        RightBalance(pa);
                        high = false;
                        break;
                }
            }
            //调整完 逐层往上找，该变 其父节点的平衡因子（针对 case 0:）
            ptr = pa;
            pa = ptr.parent;
        }
    }



    //    插入方法同BST树的INSERT;
    public boolean Insert(int kx) {

        boolean res = true;
        //空树时
        if (root == null) {
            root = new AVLNode(kx);
            return res;
        }

        cur = root;
        AVLNode pa = null;
        //在合适的分支找到底为止
        while (cur != null && cur.key != kx) {
            pa = cur;//找cur的孩子，所以升级当爸了
            cur = kx < cur.key ? cur.leftchild : cur.rightchild;
        }
        //有该值，就不插入（false,因为不允许重复）
        if (cur != null && cur.key == kx) {
            res = false;
        } else {
            cur = new AVLNode(kx);
            cur.parent = pa;//子指向父
            //没有该值，就将新结点挂在上面查找的最后一个位置的左或右孩子
            if (cur.key < pa.key) {
                pa.leftchild = cur;//父指向子
            } else {
                pa.rightchild = cur;
            }
            //链接好双亲后从插入的数据点处开始调整
            Adjust_AVL(cur);
        }
        return res;
    }




////======================================删除===============================================
////删除？？？？？？？？？？(老师没讲，我猜和BsTree差不多，多了一个调整)  https://blog.csdn.net/ChinaLeeSunnyBoy/article/details/79550680
////    先左后右双旋转（保证中序有序  以及  各节点平衡因子尽可能小）   好像不会出现平衡因子大于1或小于-1的，因为插入!!!的时候发现不平衡立马就进行旋转调整了
////======================================删除和插入对应的旋转的平衡因子变化不一样===============================================
///*
//    由于Remove的实现原理，实际被删除结点即Cur只能为叶结点 或 单分支，cur的孩子挂到cur父下面
//    cur为单分支时，删除cur,挂cur子树会使得高度降低，
//    由于cur侧降低，导致与另一侧差值变大,以ptr为根的树失衡！！！
//    朝左单旋（wps图）   ptr是发现的第一个不平衡的子树的根
//*/
//
//private void LeftBalance_AfterRemove(AVLNode ptr) {
//}
/*
   对于右边失衡的调整
   列出ptr的平衡因子由1到2的所有情况，并处理
 */
//    private void RightBalance_AfterRemove(AVLNode ptr) {   //ptr即A   rightsub即C ！！！

//        // 即Remove被删除的cur是ptr的左子，且为叶
//        if(ptr.leftchild==null){
//            if(ptr.rightchild.leftchild==null){
//                //设置调整后的平衡因子
//                ptr.balance=0;
//                ptr.rightchild.balance=0;
//                RotateLeft(ptr);
//            }else{
//                //双旋前调因子
//                if(ptr.rightchild.rightchild==null){
//                    ptr.balance=0;
//                    ptr.rightchild.balance=0;
//                    ptr.rightchild.leftchild.balance=0;
//                }
//                else{
//                    ptr.balance=0;
//                    ptr.rightchild.balance=1;
//                    ptr.rightchild.leftchild.balance=1;
//                }
//                //正式双旋
//                RotateRight(ptr.rightchild);
//                RotateLeft(ptr);
//            }
//        }
//
//        if(ptr.leftchild!=null &&ptr.leftchild.rightchild==null)
//    }

//
//
//    //====================类比插入结点时的 调整!!! 思路====================
//    private void Adjust_AVL_AfterRemove(AVLNode ptr) {
//    AVLNode pa = ptr.parent;
//    boolean high = true;//标志高度是否发生改变
//
//    //若ptr的双亲为空(即为根)  或 删除后层高没变  或 将由底往上第一个不平衡节点的子树调整好了，就不用 再 进行调整了！！！
//    while (high && pa != null) {
//        //即当Remove的cur是在左子树，cur的子即child（即此方法的参数ptr） 上提  挂在cur父亲的左边时
//        if (pa.leftchild == ptr) {
//            switch (pa.balance) {
//                case 0: //即本来平衡因子为0，删除点在左边，现在右-左=1，以pa为根的的最深子树没有变化，为右树的高度
//                    pa.balance = 1;
//                    high = false;
//                    break;
//                case 1://本来右-左=1，删除点在左边，右-左=2，以pa为根的的最深子树没有变化，为右树的高度
//                    RotateLeft_AfterRemove(pa);//右边太深了，所以调用该算法（里面有调整平衡因子，所以case里就不写了），并且删除前  和  删除并旋转调整后的pa树的层高改变了吧！！！（故无需调整pa的祖宗的平衡因子）
//                    break;
//                case -1: //本来右-左=-1，删除点在左边，右-左=0  以pa为根的树的高度降低1（因为自己实现remove是删除后继），等于较低的右树高！！！（需要下轮while调整其父的平衡因子）
//                    pa.balance = 0;
//                    break;
//            }
//        }
//        else {//即 child挂在cur父亲的右边 的情况
//            switch (pa.balance) {
//                case 0://本来pa的右-左=0   删除点cur在pa的右子树,右子树矮1层，现在右-左=-1，综合左右子树而言，以pa为根的树的最大深度没有变化（以较大的左子树的深度为准）
//                    pa.balance = -1;
//                    high = false;
//                    break;
//                case -1://本来pa的右-左=-1   删除点cur在pa的右子树,右子树矮1层，现在右-左=-2，左边太深，需要旋转！！！  旋转前后树深应该变了
//                    RotateRight_AfterRemove(pa);;
//                    break;
//                case 1://本来pa的右-左=1   删除点cur在pa的右子树,右子树矮1层，现在右-左=0   以pa为根的树高减少了一层！！！（需要下轮while调整pa的父的平衡因子）
//                    pa.balance = 0;
//                    break;
//            }
//        }
//        //调整完 逐层往上找，该变 其父节点的平衡因子（针对 case 0:）
//        ptr = pa;
//        pa = ptr.parent;
//    }
//}
////====================删除====================
//    //左到底
//    private AVLNode Next(AVLNode ptr) {
//        while (ptr != null && ptr.leftchild != null) {
//            ptr = ptr.leftchild;
//        }
//        return ptr;
//    }
//    // 删除指定值（唯一）的结点
//    public boolean Remove(int kx) {
//        boolean res = false;
//        //空树
//        if (root == null)
//            return res;
//
//        AVLNode pa = null;
//        cur = root;
//        //按中序找该节点，有就用cur指向,pa指向其父
//        while (cur != null && cur.key != kx) {
//            pa = cur;
//            cur = kx < cur.key ? cur.leftchild : cur.rightchild;
//        }
//        //没找着
//        if (cur == null)
//            return res;
//
//        //双分支（即左右孩子都有时）
//        if (cur.leftchild != null && cur.rightchild != null) {
//            AVLNode nt = Next(cur.rightchild);//nt为后继结点（即右子树的最左 且 最下的节点）
//            cur.key = nt.key;//用后继结点的值 覆盖 要删除的结点值（然后删除后继节点nt即可）
//            pa = nt.parent;//后继节点（nt）可能为叶子或有右子树，所以需再处理下 后继节点的父亲(pa)的child
//            cur = nt;//cur指向后继结点
//
//        }
//
//
//       /*
//        若进了上面的if,  cur指向原本的后继节点（值被用了，现在他可以作为被删除结点！！！处理了）只能为 叶   或 只有右子树
//        若没进if,cur是要删除的结点！！！ 且 没有双孩子，只能为 叶子 或 单分支（只有右子树  只有左子树）
//        */
//        AVLNode child = (cur.leftchild != null) ? cur.leftchild : cur.rightchild;
//        //待删除结点cur为叶时，上面处理完child为null    cur有右子树时，需要将右子树提上 挂到 后继节点的父亲下
//        if (child != null)
//            child.parent = pa;            //cur的孩子 指向 cur的父！！！！！！！！！！
//
//        //即待删除结点是根节点，其值提到被删除结点处后，然后该结点便要被删除了，此时需要让root指向待删除结点的孩子
//        if (pa == null) {
//            root = child;
//        } else {
//        /*
//             cur为叶时，child为null,其父指向child即指向空
//             cur为单分支时，将cur的子树 重挂在其父的左或右边
//             父 指向 子的子（这下cur！！！不指向谁，也没人指向，为空引用，会被自动垃圾回收）！！！！！！！！！！
//             别用child.key<pa.key，因为child有可能为null.
//             */
//            //即待删除结点在左分支时，把待删除结点的子树 往上提，重新挂在 待删除结点父亲的左边
//            if (cur.key < pa.key) {
//                pa.leftchild = child;
//            } else {
//                pa.rightchild = child;
//            }
//         /*
//           结点删除成功后  从删除结点cur的父亲处开始调整（
//           因为 cur的child的banlance没有变化；而cur的pa由于删除了子节点cur需要调整其balance）
//           类比Insert方法，相当于插入点是child
//         */
//            Adjust_AVL_AfterRemove(child);//可参考  https://blog.csdn.net/ChinaLeeSunnyBoy/article/details/79550680
//        }
//        return true;
//    }

}

public class Teacher_4_10_AVLTree {
    public static void main(String[] args) {
    }
}