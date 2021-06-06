package recursive;

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

    ListNode head;
    public LinkList()
    {
        head = null;
    }
    public void Init_List(int []br)
    {
        if(br.length < 1) return ;
        head = new ListNode(br[0]);
        ListNode p = head;
        for(int i = 1;i<br.length; ++i)
        {
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

    void Swap_Node(ListNode first,ListNode second)
    {
        int tmp = first.data;
        first.data = second.data;
        second.data = tmp;
    }
    ListNode Parition(ListNode left, ListNode right)
    {
        ListNode ip = left, jp = ip.next;
        int tmp = left.data;
        while(jp != right)
        {
            if(jp.data <= tmp)
            {
                ip=ip.next; // i++;
                Swap_Node(ip,jp);
            }
            jp = jp.next; //j++
        }
        Swap_Node(left,ip);
        return ip;
    }
    void QuickPass(ListNode left,ListNode right)
    {
        if(left != right)
        {
            ListNode p = Parition(left,right);
            QuickPass(left,p);
            QuickPass(p.next,right);
        }
    }
    public void NiceQuickSort()
    {
        if(head == null || head.next == null) return ;
        Queue<ListNode > qu = new LinkedList<>();
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
        QuickPass(head,null);
    }
}
public class Main
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