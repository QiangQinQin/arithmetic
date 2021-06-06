package Test_Enum;


public  enum ColorType {

    RED("1","红色"), YELLOW("2","黄色"), GREEN("3","绿色");

    private String code;
    private String value;

    ColorType(String code, String value) {
        this.code = code;
        this.value = value;
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

    @Override
    public String toString() {
        return "ColorType{" +
                "code='" + code + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
}
