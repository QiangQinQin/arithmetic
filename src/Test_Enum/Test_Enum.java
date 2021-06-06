package Test_Enum;


import static Test_Enum.ColorType.GREEN;
import static Test_Enum.WeekDay.SUN;
import static Test_Enum.WeekDay.WED;

public class Test_Enum {
    public static void main(String[] args) {
        System.out.println(GREEN.getCode());
        System.out.println(GREEN.getValue());
        System.out.println(GREEN);

        String code = ColorType.getCodeByValue("黄色");
        System.out.println("code=" + code);

        String value = ColorType.getValueByCode("1");
        System.out.println("value=" + value);
//==========================================================
        System.out.println(WED);
        WeekDay weekday = SUN;//枚举类型赋值
        switch (weekday) {
            case SUN:
                System.out.println("sunday");;
                break;
        }
    }
}
