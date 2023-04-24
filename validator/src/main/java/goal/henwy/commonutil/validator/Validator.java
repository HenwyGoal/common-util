package goal.henwy.commonutil.validator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static goal.henwy.commonutil.validator.ExMsgTemplateEnum.*;

/**
 * 校验器
 * <br>提供简单的常用校验
 * <br>&nbsp;之后的常用校验亦补充进来
 * <br>最终类 不可被继承;只能用于成为其他校验类的成员属性/静态属性
 * <br>单例模式;全局唯一对象
 * <br>内部类build单例;为其他校验类的对象生成方式提供参考模板
 * <br><b>建议使用setter注入，规避循环依赖问题</b>
 *
 * @author HenwyGoal
 */
public final class Validator {

    private Validator() {
    }

    public static class ValidatorBuilder {
        private static final Validator INSTANCE = new Validator();

        private ValidatorBuilder() {
        }

        public static Validator build() {
            return INSTANCE;
        }
    }


    public <T> Validator notNull(T target, String targetName) {
        if (null == target) {
            throw MUST_NOT_NULL_OR_EMPTY.ex(targetName);
        }
        return this;
    }

    public <T> Validator notEmpty(Collection<T> target, String targetName) {
        if (null == target || target.isEmpty()) {
            throw MUST_NOT_NULL_OR_EMPTY.ex(targetName);
        }
        return this;
    }

    public <T> Validator notEmpty(T[] target, String targetName) {
        if (null == target || 0 == target.length) {
            throw MUST_NOT_NULL_OR_EMPTY.ex(targetName);
        }
        return this;
    }

    public <K, V> Validator notEmpty(Map<K, V> target, String targetName) {
        if (null == target || target.isEmpty()) {
            throw MUST_NOT_NULL_OR_EMPTY.ex(targetName);
        }
        return this;
    }

    public Validator hasText(CharSequence target, String targetName) {
        if (!InnerMethod.hasText(target)) {
            throw MUST_NOT_NULL_OR_EMPTY.ex(targetName);
        }
        return this;
    }


    public Validator contains(CharSequence target, String targetName, CharSequence item, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        hasText(target, targetName).hasText(item, "必要文本");
        if (!("" + target).contains(item)) {
            throw MUST_CONTAIN_TEXT.ex(targetName, item);
        }
        return this;
    }

    public Validator contains(CharSequence target, String targetName, CharSequence item) {
        return contains(target, targetName, item, true);
    }

    public <T> Validator contains(Collection<T> target, String targetName, T item, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notEmpty(target, targetName);
        if (!target.contains(item)) {
            throw MUST_CONTAIN_ELEMENT.ex(targetName, item);
        }
        return this;
    }

    public <T> Validator contains(Collection<T> target, String targetName, T item) {
        return contains(target, targetName, item, true);
    }

    public <T> Validator contains(T[] target, String targetName, T item, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notEmpty(target, targetName);
        if (!InnerMethod.isArrayContainsItem(target, item)) {
            throw MUST_CONTAIN_ELEMENT.ex(targetName, item);
        }
        return this;
    }

    public <T> Validator contains(T[] target, String targetName, T item) {
        return contains(target, targetName, item, true);
    }

    public <K, V> Validator containsKey(Map<K, V> target, String targetName, K item, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notEmpty(target, targetName);
        if (!target.containsKey(item)) {
            throw MUST_CONTAIN_KEY.ex(targetName, item);
        }
        return this;
    }

    public <K, V> Validator containsKey(Map<K, V> target, String targetName, K item) {
        return containsKey(target, targetName, item, true);
    }

    public Validator notContains(CharSequence target, String targetName, CharSequence item, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        hasText(target, targetName).hasText(item, "必要文本");
        if (("" + target).contains(item)) {
            throw MUST_NOT_CONTAIN_TEXT.ex(targetName, item);
        }
        return this;
    }

    public Validator notContains(CharSequence target, String targetName, CharSequence item) {
        return notContains(target, targetName, item, true);
    }

    public <T> Validator notContains(Collection<T> target, String targetName, T item, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notEmpty(target, targetName);
        if (target.contains(item)) {
            throw MUST_NOT_CONTAIN_ELEMENT.ex(targetName, item);
        }
        return this;
    }

    public <T> Validator notContains(Collection<T> target, String targetName, T item) {
        return notContains(target, targetName, item, true);
    }

    public <T> Validator notContains(T[] target, String targetName, T item, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notEmpty(target, targetName);
        if (InnerMethod.isArrayContainsItem(target, item)) {
            throw MUST_NOT_CONTAIN_ELEMENT.ex(targetName, item);
        }
        return this;
    }

    public <T> Validator notContains(T[] target, String targetName, T item) {
        return notContains(target, targetName, item, true);
    }

    public <K, V> Validator notContainsKey(Map<K, V> target, String targetName, K item, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notEmpty(target, targetName);
        if (target.containsKey(item)) {
            throw MUST_NOT_CONTAIN_KEY.ex(targetName, item);
        }
        return this;
    }

    public <K, V> Validator notContainsKey(Map<K, V> target, String targetName, K item) {
        return notContainsKey(target, targetName, item, true);
    }


    public Validator eq(Number target, String targetName, Number aim, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notNull(target, targetName).notNull(aim, "标准值");
        if (aim.doubleValue() != target.doubleValue()) {
            throw MUST_EQUAL_NUMBER.ex(targetName, aim);
        }
        return this;
    }

    public Validator eq(Number target, String targetName, Number aim) {
        return eq(target, targetName, aim, true);
    }

    public Validator neq(Number target, String targetName, Number aim, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notNull(target, targetName).notNull(aim, "标准值");
        if (aim.doubleValue() == target.doubleValue()) {
            throw MUST_NOT_EQUAL_NUMBER.ex(targetName, aim);
        }
        return this;
    }

    public Validator neq(Number target, String targetName, Number aim) {
        return neq(target, targetName, aim, true);
    }

    public Validator lte(Number target, String targetName, Number max, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notNull(target, targetName).notNull(max, "最大值");
        if (target.doubleValue() > max.doubleValue()) {
            throw MUST_LTE.ex(targetName, max);
        }
        return this;
    }

    public Validator lte(Number target, String targetName, Number max) {
        return lte(target, targetName, max, true);
    }

    public Validator lt(Number target, String targetName, Number max, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notNull(target, targetName).notNull(max, "最大值");
        if (target.doubleValue() >= max.doubleValue()) {
            throw MUST_LT.ex(targetName, max);
        }
        return this;
    }

    public Validator lt(Number target, String targetName, Number max) {
        return lt(target, targetName, max, true);
    }

    public Validator gte(Number target, String targetName, Number min, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notNull(target, targetName).notNull(min, "最小值");
        if (target.doubleValue() < min.doubleValue()) {
            throw MUST_GTE.ex(targetName, min);
        }
        return this;
    }

    public Validator gte(Number target, String targetName, Number min) {
        return gte(target, targetName, min, true);
    }

    public Validator gt(Number target, String targetName, Number min, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notNull(target, targetName).notNull(min, "最小值");
        if (target.doubleValue() <= min.doubleValue()) {
            throw MUST_GT.ex(targetName, min);
        }
        return this;
    }

    public Validator gt(Number target, String targetName, Number min) {
        return gt(target, targetName, min, true);
    }

    public Validator between(Number target, String targetName, Number min, Number max, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notNull(target, targetName).notNull(min, "最小值").notNull(max, "最大值")
                .isLegal(min.doubleValue() < max.doubleValue(), "最小值与最大值");
        if (target.doubleValue() < min.doubleValue() || target.doubleValue() > max.doubleValue()) {
            throw MUST_BETWEEN.ex(targetName, min, max);
        }
        return this;
    }

    public Validator between(Number target, String targetName, Number min, Number max) {
        return between(target, targetName, min, max, true);
    }

    public Validator before(Date target, String targetName, Date end, String dateFormat, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notNull(target, targetName).notNull(end, "最晚时间").hasText(dateFormat, "日期格式");
        if (target.after(end)) {
            throw MUST_BEFORE.ex(targetName, InnerMethod.parseString(end, dateFormat));
        }
        return this;
    }

    public Validator before(Date target, String targetName, Date end, Boolean required) {
        return before(target, targetName, end, InnerConstant.DEFAULT_DATE_FORMAT, required);
    }

    public Validator before(Date target, String targetName, Date end, String dateFormat) {
        return before(target, targetName, end, dateFormat, true);
    }

    public Validator before(Date target, String targetName, Date end) {
        return before(target, targetName, end, true);
    }

    public Validator after(Date target, String targetName, Date start, String dateFormat, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notNull(target, targetName).notNull(start, "最早时间").hasText(dateFormat, "日期格式");
        if (target.before(start)) {
            throw MUST_AFTER.ex(targetName, InnerMethod.parseString(start, dateFormat));
        }
        return this;
    }

    public Validator after(Date target, String targetName, Date start, Boolean required) {
        return after(target, targetName, start, InnerConstant.DEFAULT_DATE_FORMAT, required);
    }

    public Validator after(Date target, String targetName, Date start, String dateFormat) {
        return after(target, targetName, start, dateFormat, true);
    }

    public Validator after(Date target, String targetName, Date start) {
        return after(target, targetName, start, true);
    }

    public Validator between(Date target, String targetName, Date start, Date end, String dateFormat, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        notNull(target, targetName).notNull(start, "最早时间").notNull(end, "最晚时间").hasText(dateFormat, "日期格式")
                .isLegal(start.before(end), "最早时间与最晚时间关系");
        if (target.before(start) || target.after(end)) {
            throw MUST_BETWEEN.ex(targetName, InnerMethod.parseString(start, dateFormat), InnerMethod.parseString(end, dateFormat));
        }
        return this;
    }

    public Validator between(Date target, String targetName, Date start, Date end, Boolean required) {
        return between(target, targetName, start, end, InnerConstant.DEFAULT_DATE_FORMAT, required);
    }

    public Validator between(Date target, String targetName, Date start, Date end, String dateFormat) {
        return between(target, targetName, start, end, dateFormat, true);
    }

    public Validator between(Date target, String targetName, Date start, Date end) {
        return between(target, targetName, start, end, true);
    }


    public Validator matchPattern(String target, String targetName, Pattern pattern, String patternName, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        hasText(target, targetName).notNull(pattern, "格式").hasText(patternName, "格式名称");
        if (!pattern.matcher(target).matches()) {
            throw MUST_MATCH_PATTERN.ex(target, patternName);
        }
        return this;
    }

    public Validator matchPattern(String target, String targetName, Pattern pattern, String patternName) {
        return matchPattern(target, targetName, pattern, patternName, true);
    }

    public Validator matchPattern(String target, String targetName, String pattern, String patternName, Boolean required) {
        return matchPattern(target, targetName, InnerConstant.pattern(pattern), patternName, required);
    }

    public Validator matchPattern(String target, String targetName, String pattern, String patternName) {
        return matchPattern(target, targetName, pattern, patternName, true);
    }

    public Validator isEmail(String target, String targetName, Boolean required) {
        return matchPattern(target, targetName, InnerConstant.REGEX_EMAIL, "邮箱", required);
    }

    public Validator isEmail(String target, String targetName) {
        return isEmail(target, targetName, true);
    }

    public Validator isTelePhone(String target, String targetName, Boolean required) {
        return matchPattern(target, targetName, InnerConstant.REGEX_TELEPHONE, "手机号", required);
    }

    public Validator isTelePhone(String target, String targetName) {
        return isTelePhone(target, targetName, true);
    }


    public Validator size(String target, String targetName, Integer size, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        return hasText(target, targetName).gt(size, targetName + "文本标准长度", 0)
                .eq(target.length(), targetName + "文本标准长度", size);
    }

    public Validator size(String target, String targetName, Integer size) {
        return size(target, targetName, size, true);
    }

    public Validator size(String target, String targetName, Integer minSize, Integer maxSize, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        return hasText(target, targetName)
                .gt(minSize, targetName + "文本最小长度", 0)
                .gt(maxSize, targetName + "文本最大长度", minSize)
                .between(target.length(), targetName + "文本长度", minSize, maxSize, required);
    }

    public Validator size(String target, String targetName, Integer minSize, Integer maxSize) {
        return size(target, targetName, minSize, maxSize, true);
    }

    public <T> Validator size(Collection<T> target, String targetName, Integer size, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        return notEmpty(target, targetName).gt(size, targetName + "列表长度", 0)
                .eq(target.size(), targetName + "列表长度", size);
    }

    public <T> Validator size(Collection<T> target, String targetName, Integer size) {
        return size(target, targetName, size, true);
    }

    public <T> Validator size(Collection<T> target, String targetName, Integer minSize, Integer maxSize, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        return notEmpty(target, targetName)
                .gt(minSize, targetName + "列表最小长度", 0)
                .gt(maxSize, targetName + "列表最大长度", 0)
                .between(target.size(), targetName + "列表长度", minSize, maxSize, required);
    }

    public <T> Validator size(Collection<T> target, String targetName, Integer minSize, Integer maxSize) {
        return size(target, targetName, minSize, maxSize, true);
    }

    public <T> Validator size(T[] target, String targetName, Integer size, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        return notEmpty(target, targetName).gt(size, targetName + "列表长度", 0)
                .eq(target.length, targetName + "列表长度", size);
    }

    public <T> Validator size(T[] target, String targetName, Integer size) {
        return size(target, targetName, size, true);
    }

    public <T> Validator size(T[] target, String targetName, Integer minSize, Integer maxSize, Boolean required) {
        if (!Boolean.TRUE.equals(required) && null == target) {
            return this;
        }
        return notEmpty(target, targetName)
                .gt(minSize, targetName + "列表最小长度", 0)
                .gt(maxSize, targetName + "列表最大长度", 0)
                .between(target.length, targetName + "列表长度", minSize, maxSize, required);
    }

    public <T> Validator size(T[] target, String targetName, Integer minSize, Integer maxSize) {
        return size(target, targetName, minSize, maxSize, true);
    }


    public Validator isLegal(Boolean isLegal, String targetName) {
        if (!Boolean.TRUE.equals(isLegal)) {
            throw MUST_LEGAL.ex(targetName);
        }
        return this;
    }

    public Validator isExist(Boolean isExist, String targetName) {
        if (!Boolean.TRUE.equals(isExist)) {
            throw MUST_EXIST.ex(targetName);
        }
        return this;
    }

    public Validator notExist(Boolean isExist, String targetName) {
        if (Boolean.TRUE.equals(isExist)) {
            throw MUST_NOT_EXIST.ex(targetName);
        }
        return this;
    }


    private static final class InnerConstant {
        private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

        private static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

        private static final String REGEX_TELEPHONE = "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

        private static final Map<String, Pattern> PATTERN_CONTAINER = new HashMap<>(16);

        private static Pattern pattern(String pattern) {
            return PATTERN_CONTAINER.computeIfAbsent(pattern, Pattern::compile);
        }
    }

    private static class InnerMethod {

        private static boolean hasText(CharSequence str) {
            return str != null && str.length() > 0 && containsText(str);
        }

        private static boolean containsText(CharSequence str) {
            int strLen = str.length();
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }
            return false;
        }

        private static <T> boolean isArrayContainsItem(T[] target, T item) {
            if (target.length == 0) {
                return false;
            }
            for (T t : target) {
                if (null == t) {
                    if (null == item) {
                        return true;
                    }
                    continue;
                }
                if (t.equals(item)) {
                    return true;
                }
            }
            return false;
        }

        private static String parseString(Date date, String format) {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(format));
        }

    }
}
