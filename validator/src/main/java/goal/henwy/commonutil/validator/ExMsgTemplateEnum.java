package goal.henwy.commonutil.validator;


/**
 * 异常信息模板枚举类
 * <br>用于Validator异常信息的构造
 * <br>通用的模板应当在后续版本迭代中补充到这里来
 *
 * @author HenwyGoal
 */
public enum ExMsgTemplateEnum {

    /**
     *
     */
    MUST_NOT_NULL_OR_EMPTY("%s不能为空"),
    MUST_CONTAIN_TEXT("%s未包含必要文本(%s)"),
    MUST_CONTAIN_ELEMENT("%s未包含必要元素(%s)"),
    MUST_CONTAIN_KEY("%s未包含必要键(%s)"),
    MUST_NOT_CONTAIN_TEXT("%s不能包含文本(%s)"),
    MUST_NOT_CONTAIN_ELEMENT("%s不能包含元素(%s)"),
    MUST_NOT_CONTAIN_KEY("%s不能包含键(%s)"),
    MUST_EQUAL_NUMBER("%s不是正确值(%s)"),
    MUST_NOT_EQUAL_NUMBER("%s不能取值为(%s)"),

    MUST_GT("%s的最小值为%s"),
    MUST_GTE("%s取值不能小于%s"),
    MUST_LT("%s的最小值为%s"),
    MUST_LTE("%s取值不能大于%s"),
    MUST_BETWEEN("%s的取值范围为[%s~%s]"),

    MUST_BEFORE("%s取值不能晚于(%s)"),
    MUST_AFTER("%s取值不能早于于(%s)"),

    MUST_MATCH_PATTERN("%s不符合%s的格式要求"),

    MUST_LEGAL("%s取值不合理"),
    MUST_EXIST("%s不存在"),
    MUST_NOT_EXIST("%s已存在"),



    ;


    private final String template;

    ExMsgTemplateEnum(String template) {
        this.template = template;
    }

    public String errMsg(Object... elements) {
        return String.format(this.template, elements);
    }

    public RuntimeException ex(Object... elements) {
        return new RuntimeException(errMsg(elements));
    }





}
