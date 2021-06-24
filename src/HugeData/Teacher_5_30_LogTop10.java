package HugeData;

/**
 * @author QiangQin
 * * @date 2021/6/24
 */
public class Teacher_5_30_LogTop10 {
    public static int Hash(int key){
        return key%97;//注意是素数
    }
    public static int StrSum(char[] str){
        int n=str.length;
        int sum=0;
        for(int i=0;i<n;i++){
            sum=sum*5+str[i];//*5可以避免：pinghy   pingyh这种字母种类相同，但是序列不同的字符串
        }
        return  sum;
    }

    public static void main(String[] args) {
        char[] stra="yhping".toCharArray();//pinghy   pingyh
        char[] strb="yangyuhe".toCharArray();
        char[] strc="zhangyuxuan".toCharArray();
        char[] strd="pingyh".toCharArray();

        int pos=Hash(StrSum(stra));
        pos=Hash(StrSum(strb));
        pos=Hash(StrSum(strc));
        pos=Hash(StrSum(strd));

        stra.hashCode();
// String 源码：s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
//        public int hashCode() {
//            int h = hash;
//            if (h == 0 && value.length > 0) {
//                char val[] = value;
//
//                for (int i = 0; i < value.length; i++) {
//                    h = 31 * h + val[i];
//                }
//                hash = h;
//            }
//            return h;
//        }
    }
}
