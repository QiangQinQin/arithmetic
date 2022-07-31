//public class Main {
//
//
//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//    }
//}
 enum ColorType {

    RED("1","红色"), YELLOW("2","黄色"), GREEN("3","绿色");

    private String code;
    private String value;

    ColorType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ColorType{" +
                "code='" + code + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public static String getValueByCode(String code) {
        ColorType[] values = ColorType.values();
        for (ColorType type : values) {
            if (type.code.equalsIgnoreCase(code)) {
                return type.value;
            }
        }
        return null;
    }

    public static String getCodeByValue(String value) {
        ColorType[] values = ColorType.values();
        for (ColorType type : values) {
            if (type.value.equalsIgnoreCase(value)) {
                return type.code;
            }
        }
        return null;
    }



    public static void main(String[] args) {
        System.out.println(GREEN.code);
        System.out.println(ColorType.GREEN.value);
        System.out.println(GREEN);

        String code=ColorType.getCodeByValue("黄色");
        System.out.println("code="+code);

        String value=ColorType.getValueByCode("1");
        System.out.println("value="+value);

    }

}




