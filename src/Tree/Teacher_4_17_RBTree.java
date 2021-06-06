package Tree;


import com.sun.org.apache.xml.internal.security.Init;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static Tree.RBTree.ColorType.BLACK;
import static Tree.RBTree.ColorType.RED;

class RBTree
{
    enum ColorType{RED ,BLACK};
    class rb_node
    {
        rb_node leftchild;
        rb_node parent;
        rb_node rightchild;
        ColorType color; // RED  BLACK   红黑树在插入的时候必须是红节点
        int key;
        public rb_node()
        {
            leftchild = parent = rightchild = null;
            color = RED;//枚举类型赋值
            key = 0;
        }
        public rb_node(int kx)
        {
            leftchild = parent = rightchild = null;
            color = RED;
            key = kx;
        }
    }

    rb_node root;//必须是黑色
    rb_node nil;//sentinel  哨兵结点（必须是黑色）
    rb_node cur;//insert时要用

    public RBTree()
    {
        root = null;
        nil = new rb_node();
        nil.color = BLACK;
    }

    //面试考 画图＋手写
    private void RotateLeft(rb_node ptr)
    {
        rb_node newroot = ptr.rightchild;
        newroot.parent = ptr.parent;//1
        ptr.rightchild = newroot.leftchild;
        if(newroot.leftchild != null)
        {
            newroot.leftchild.parent = ptr;//2
        }
        newroot.leftchild = ptr;
        rb_node pa = ptr.parent;
        if(pa == null)
        {
            root = newroot;
        }
        else {
            if(pa.leftchild == ptr)
            {
                pa.leftchild = newroot;
            }
            else
            {
                pa.rightchild = newroot;
            }
        }
        ptr.parent = newroot; // 3
    }
    private void RotateRight(rb_node ptr)
    {
        rb_node newroot = ptr.leftchild;
        newroot.parent = ptr.parent; //1
        ptr.leftchild = newroot.rightchild;
        if(newroot.rightchild != null){
            newroot.rightchild.parent = ptr;
        }
        newroot.rightchild = ptr;
        rb_node pa = ptr.parent;
        if(pa == null) {
            root = newroot;
        }
        else {
            if (pa.leftchild == ptr) {
                pa.leftchild = newroot;
            } else {
                pa.rightchild = newroot;
            }
        }
        ptr.parent = newroot;
    }

  /*
     子的颜色和双亲的颜色不能都为红色（即不能有两个连续的红色）
     根和叶（哨兵）为黑  结点新插入时为红
     插入前应该使红黑树已经是调节好的状态
     所有路径的黑色节点应该相同
  */
    void Adjust(rb_node p)
    {
        //双亲不为空  且 插入的结点和其父都是红色
        while(p.parent != null && p.parent.color == RED)
        {
            if(p.parent.parent.rightchild == p.parent)// 说明p插在 双亲的双亲的 右边（wps图）
            {
                rb_node left = p.parent.parent.leftchild ;
                if(left.color == RED)
                {
                    p.parent.color = BLACK;
                    left.color = BLACK;
                    p.parent.parent.color = RED;//例如：插入56时

                    p = p.parent.parent;//接着往上判断（例：插入78时,第一遍只需调色，然后继续进入while，执行了下面的else,左旋（将12转下来，34成根）+变色
                } else//即即双亲为红  且 双亲的双亲的左孩子不为红  且 p插在 双亲的双亲的 右边，没法通过变颜色，需要旋转＋变色
                {
                    //例：插入75时
                    if(p.parent.leftchild == p)//注意：插入在双亲的左边
                    {
                        p = p.parent;
                        RotateRight(p);//即总共完成 先右后左双旋
                    }
                    //没进入上面if的话，p就是在双亲的右边

                    p.parent.color = BLACK;
                    p.parent.parent.color = RED;
                    RotateLeft(p.parent.parent);//例：插入67时，将45旋下来，56变为根
                    //注意没写 p = p.parent.parent;  即不再移动双亲
                }
            }else  // p插在双亲的双亲的左边   p自己为红  p的双亲为红（是while的条件）
            {//例：插入5
                rb_node right = p.parent.parent.rightchild;
                if(right.color == RED)
                {
                    //把这俩红都改成黑
                    p.parent.color = BLACK;
                    right.color = BLACK;
                    p.parent.parent.color = RED;

                    p = p.parent.parent;// 此种if没有旋转，但是 移动指针了
                }
                else//即双亲的双亲的右边为黑色，和 p的父亲 颜色不一样，需要 旋转+调色
                {
                    if(p.parent.rightchild == p)//即p在双亲的双亲的左边，但 在双亲的右边 时（类比上面）
                    {
                        p = p.parent;
                        RotateLeft(p);//旋转函数的实现并没有改变指针的指向
                    }
                    p.parent.color = BLACK;
                    p.parent.parent.color = RED;
                    RotateRight(p.parent.parent);
                }
            }
        }

    }


    boolean Insert_Item(int kx)
    {
        // 同BST的插入,多了一个调整

        boolean res = true;
        //空树时
        if (root == null) {
            root = new rb_node(kx);
            return res;
        }

        cur = root;
        rb_node pa = null;
        //在合适的分支找到底为止
        while (cur != null && cur.key != kx) {
            pa = cur;//找cur的孩子，所以升级当爸了
            cur = kx < cur.key ? cur.leftchild : cur.rightchild;
        }
        //有该值，就不插入（false,因为不允许重复）
        if (cur != null && cur.key == kx) {
            res = false;
        } else {//没有该值
            cur = new rb_node(kx);
            cur.parent = pa;//子指向父
            cur.leftchild = cur.rightchild = nil;
            //将新结点挂在上面查找的最后一个位置的左或右孩子
            if (cur.key < pa.key) {
                pa.leftchild = cur;//父指向子
            } else {
                pa.rightchild = cur;
            }
            //挂好后开始调整
            Adjust(cur);
        }
        return res;
    }

}
public class Teacher_4_17_RBTree {
    public static void main(String[] args) {

    }
}