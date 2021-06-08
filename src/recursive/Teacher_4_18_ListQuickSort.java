package recursive;

import java.util.LinkedList;
import java.util.Queue;
//链表的快排
class LinkList
{
    class ListNode
    {
        int data;
        ListNode next;
        public ListNode()
        {
            data = 0;
            next = null;
        }
        public ListNode(int x)
        {
            data = x;
            next = null;
        }
        public ListNode (int x,ListNode narg)
        {
            data = x;
            next = narg;
        }
    }

    ListNode head;//头指针
    public LinkList()
    {
        head = null;
    }
    //用数组的值初始化链表
    public void Init_List(int []br)
    {
//        先进行合法性判断
        if(br.length < 1)
            return ;
        head = new ListNode(br[0]);//名叫head的引用，指向第一个结点
        ListNode p = head;//指向当前的尾巴
        for(int i = 1;i<br.length; ++i)
        {
            //追加到后面
            ListNode s = new ListNode(br[i]);
            p.next = s;
            p = p.next;
        }
    }
    public void Print_List()
    {
        ListNode p = head;
        while(p != null)
        {
            System.out.print(p.data+" ");
            p = p.next;
        }
        System.out.println();
    }



    // 只换数据，不移结点
    void Swap_Node(ListNode first,ListNode second)
    {
        int tmp = first.data;
        first.data = second.data;
        second.data = tmp;
    }

    /*
          也可以采用这个思路：
            一个链表专门存放比tmp小的、另一个链表专门存放比tmp大的，然后把两个链表链接起来
             需要卸结点，又连进去，复杂度高一点
        */
    //类比数组只能单向逼近时（OWParition）
    ListNode Parition(ListNode left, ListNode right)
    {
        //ip即i  pointer(i指针)
        ListNode ip = left, jp = ip.next;//若一开始jp就==right,就说明只有一个结点，就不进入while
        int tmp = left.data;

        while(jp != right)//注意不能用《=  因为是new出来的结点，其地址并不连续
        {
            if(jp.data <= tmp)
            {
                ip=ip.next; // 类比OWParition中的 i++;
                Swap_Node(ip,jp);
            }
            jp = jp.next; //j++
        }
        //似乎可以加个条件！！！，left和ip不等时，才有必要交换
        Swap_Node(left,ip);//将基准值left 移到最终位置ip
        return ip;
    }


    void QuickPass(ListNode left,ListNode right)//右端是取不到的,因为Parition里的jp.next==right就不会进入到while里
    {
        if(left != right) //即 只要有元素就行
        {
            ListNode p = Parition(left,right);//划分
            QuickPass(left,p);//[left,p）
            QuickPass(p.next,right);
        }
    }
//    非递归（和数组快排时一样）
    public void NiceQuickSort()
    {
        //链表为空  或者 只有一个节点
        if(head == null || head.next == null)
            return ;
        Queue<ListNode > qu = new LinkedList<ListNode>();
         //压入快排区间的左右范围，等待Parition处理r
        qu.offer(head);
        qu.offer(null);
        while(!qu.isEmpty())
        {
            ListNode left = qu.poll();
            ListNode right = qu.poll();
            ListNode p = Parition(left,right);

            if(left != p)
            {
                qu.offer(left);
                qu.offer(p);
            }
            if(p.next != right)
            {
                qu.offer(p.next);
                qu.offer(right);
            }
        }

    }
    public void QuickSort()
    {
        QuickPass(head,null);//因为最后一个结点的next为空，相当于右端是取不到的
    }
}
public class Teacher_4_18_ListQuickSort
{
    public static void main(String[] args)
    {
        int []ar={56,78,12,34,90,67,100,45,23,89};
        LinkList myt = new LinkList();
        myt.Init_List(ar);
        myt.Print_List();
        myt.NiceQuickSort();
        myt.Print_List();
    }
}