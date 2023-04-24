package goal.henwy.commonutil.validator.test;

import goal.henwy.commonutil.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 校验器测试类
 *
 * @author HenwyGoal
 */
class ValidatorTest {

    private static final Validator validator = Validator.ValidatorBuilder.build();

    private static final String targetName = "item";

    @Test
    void testNotNull() {
        Assertions.assertThrows(RuntimeException.class, () -> validator.notNull(null, targetName));
        Assertions.assertEquals(validator, validator.notNull(new Object(), targetName));

        Assertions.assertThrows(RuntimeException.class, () -> validator.notEmpty((Collection<?>) null, targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notEmpty(Collections.emptyList(), targetName));
        Assertions.assertEquals(validator, validator.notEmpty(Collections.singleton(new Object()), targetName));

        Assertions.assertThrows(RuntimeException.class, () -> validator.notEmpty((Object[]) null, targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notEmpty(new Object[0], targetName));
        Assertions.assertEquals(validator, validator.notEmpty(new Object[]{new Object()}, targetName));

        Assertions.assertThrows(RuntimeException.class, () -> validator.notEmpty((Map<?, ?>) null, targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notEmpty(Collections.emptyMap(), targetName));
        Assertions.assertEquals(validator, validator.notEmpty(Collections.singletonMap(new Object(), new Object()), targetName));

        Assertions.assertThrows(RuntimeException.class, () -> validator.hasText(null, targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.hasText("", targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.hasText("   ", targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.hasText(new StringBuilder(), targetName));
        Assertions.assertEquals(validator, validator.hasText("1", targetName).hasText(new StringBuilder().append(1), targetName));
    }

    @Test
    void testContains() {
        Assertions.assertEquals(validator, validator.contains((CharSequence) null, targetName, "1", false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.contains((CharSequence) null, targetName, "1"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.contains("", targetName, "1"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.contains("2", targetName, "1"));
        Assertions.assertEquals(validator, validator.contains("21", targetName, "1"));

        Assertions.assertEquals(validator, validator.contains((Collection<Object>) null, targetName, new Object(), false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.contains((Collection<Object>) null, targetName, new Object()));
        Assertions.assertThrows(RuntimeException.class, () -> validator.contains(Collections.emptyList(), targetName, "1"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.contains(Collections.singleton("2"), targetName, "1"));
        Assertions.assertEquals(validator, validator.contains(Arrays.asList("2", "1"), targetName, "1"));

        Assertions.assertEquals(validator, validator.contains((Object[]) null, targetName, new Object(), false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.contains((Object[]) null, targetName, new Object()));
        Assertions.assertThrows(RuntimeException.class, () -> validator.contains(new Object[0], targetName, "1"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.contains(new Object[]{"2"}, targetName, "1"));
        Assertions.assertEquals(validator, validator.contains(new Object[]{"2", "1"}, targetName, "1"));
        Assertions.assertEquals(validator, validator.contains(new Object[]{"2", "1", null}, targetName, null));

        Assertions.assertEquals(validator, validator.containsKey((Map<Object, ?>) null, targetName, new Object(), false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.containsKey((Map<Object, ?>) null, targetName, new Object()));
        Assertions.assertThrows(RuntimeException.class, () -> validator.containsKey(Collections.emptyMap(), targetName, "1"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.containsKey(Collections.singletonMap("2", true), targetName, "1"));
        Assertions.assertEquals(validator, validator.containsKey(Collections.singletonMap("1", true), targetName, "1"));
        Assertions.assertEquals(validator, validator.containsKey(Collections.singletonMap(null, true), targetName, null));
    }

    @Test
    void testNotContains() {
        Assertions.assertEquals(validator, validator.notContains((CharSequence) null, targetName, "1", false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContains((CharSequence) null, targetName, "1"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContains("", targetName, "1"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContains("21", targetName, "1"));
        Assertions.assertEquals(validator, validator.notContains("2", targetName, "1"));

        Assertions.assertEquals(validator, validator.notContains((Collection<Object>) null, targetName, new Object(), false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContains((Collection<Object>) null, targetName, new Object()));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContains(Collections.emptyList(), targetName, "1"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContains(Arrays.asList("2", "1"), targetName, "1"));
        Assertions.assertEquals(validator, validator.notContains(Collections.singleton("2"), targetName, "1"));

        Assertions.assertEquals(validator, validator.notContains((Object[]) null, targetName, new Object(), false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContains((Object[]) null, targetName, new Object()));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContains(new Object[0], targetName, "1"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContains(new Object[]{"2", "1"}, targetName, "1"));
        Assertions.assertEquals(validator, validator.notContains(new Object[]{"2"}, targetName, "1"));

        Assertions.assertEquals(validator, validator.notContainsKey((Map<Object, ?>) null, targetName, new Object(), false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContainsKey((Map<Object, ?>) null, targetName, new Object()));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContainsKey(Collections.emptyMap(), targetName, "1"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notContainsKey(Collections.singletonMap("1", true), targetName, "1"));
        Assertions.assertEquals(validator, validator.notContainsKey(Collections.singletonMap("2", true), targetName, "1"));
    }

    @Test
    void testNumber() {
        Assertions.assertEquals(validator, validator.eq(null, targetName, 1, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.eq(null, targetName, 1));
        Assertions.assertThrows(RuntimeException.class, () -> validator.eq(2.0f, targetName, 1));
        Assertions.assertEquals(validator, validator.eq(1L, targetName, 1.0d));

        Assertions.assertEquals(validator, validator.neq(null, targetName, 1, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.neq(null, targetName, 1));
        Assertions.assertThrows(RuntimeException.class, () -> validator.neq(1L, targetName, 1.0d));
        Assertions.assertEquals(validator, validator.neq(2.0f, targetName, 1.0d));

        Assertions.assertEquals(validator, validator.lte(null, targetName, 1, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.lte(null, targetName, 1));
        Assertions.assertThrows(RuntimeException.class, () -> validator.lte(1.0001F, targetName, 1.0d));
        Assertions.assertEquals(validator, validator.lte(2.0f, targetName, 2.0d));
        Assertions.assertEquals(validator, validator.lte(1.99998f, targetName, 2.0d));

        Assertions.assertEquals(validator, validator.lt(null, targetName, 1, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.lt(null, targetName, 1));
        Assertions.assertThrows(RuntimeException.class, () -> validator.lt(1.0001F, targetName, 1.0d));
        Assertions.assertThrows(RuntimeException.class, () -> validator.lt(2.0f, targetName, 2.0d));
        Assertions.assertEquals(validator, validator.lt(1.99998f, targetName, 2.0d));

        Assertions.assertEquals(validator, validator.gte(null, targetName, 1, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.gte(null, targetName, 1));
        Assertions.assertThrows(RuntimeException.class, () -> validator.gte(0.99999F, targetName, 1.0d));
        Assertions.assertEquals(validator, validator.gte(2.0f, targetName, 2.0d));
        Assertions.assertEquals(validator, validator.gte(2.000001f, targetName, 2.0d));

        Assertions.assertEquals(validator, validator.gt(null, targetName, 1, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.gt(null, targetName, 1));
        Assertions.assertThrows(RuntimeException.class, () -> validator.gt(0.99999F, targetName, 1.0d));
        Assertions.assertThrows(RuntimeException.class, () -> validator.gt(2.0f, targetName, 2.0d));
        Assertions.assertEquals(validator, validator.gt(2.000001f, targetName, 2.0d));

        Assertions.assertEquals(validator, validator.between(null, targetName, 1, 10, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.between(null, targetName, 1, 10));
        Assertions.assertThrows(RuntimeException.class, () -> validator.between(0.99999F, targetName, 1.0d, 10.0d));
        Assertions.assertThrows(RuntimeException.class, () -> validator.between(10.000001f, targetName, 1.0d, 10.0d));
        Assertions.assertEquals(validator, validator.between(1, targetName, 1.0d, 10.0d));
        Assertions.assertEquals(validator, validator.between(10, targetName, 1.0d, 10.0d));
        Assertions.assertEquals(validator, validator.between(5.0f, targetName, 1.0d, 10.0d));
    }

    @Test
    void testDate() {
        long dayMillis = 86400000;
        Date now = new Date();
        Date tomorrow = new Date(now.getTime() + dayMillis);
        Date yesterday = new Date(now.getTime() - dayMillis);
        String dateFormat = "MM-dd";

        Assertions.assertEquals(validator, validator.before(null, targetName, now, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.before(null, targetName, now));
        Assertions.assertThrows(RuntimeException.class, () -> validator.before(now, targetName, yesterday));
        Assertions.assertEquals(validator, validator.before(now, targetName, tomorrow));

        Assertions.assertEquals(validator, validator.before(null, targetName, now, dateFormat, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.before(null, targetName, now, dateFormat));
        Assertions.assertThrows(RuntimeException.class, () -> validator.before(now, targetName, yesterday, dateFormat));
        Assertions.assertEquals(validator, validator.before(now, targetName, tomorrow, dateFormat));

        Assertions.assertEquals(validator, validator.after(null, targetName, now, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.after(null, targetName, now));
        Assertions.assertThrows(RuntimeException.class, () -> validator.after(now, targetName, tomorrow));
        Assertions.assertEquals(validator, validator.after(now, targetName, yesterday));

        Assertions.assertEquals(validator, validator.after(null, targetName, now, dateFormat, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.after(null, targetName, now, dateFormat));
        Assertions.assertThrows(RuntimeException.class, () -> validator.after(now, targetName, tomorrow, dateFormat));
        Assertions.assertEquals(validator, validator.after(now, targetName, yesterday, dateFormat));

        Assertions.assertEquals(validator, validator.between(null, targetName, yesterday, tomorrow, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.between(null, targetName, yesterday, tomorrow));
        Assertions.assertThrows(RuntimeException.class, () -> validator.between(new Date(yesterday.getTime() - dayMillis), targetName, yesterday, tomorrow));
        Assertions.assertThrows(RuntimeException.class, () -> validator.between(new Date(tomorrow.getTime() + dayMillis), targetName, yesterday, tomorrow));
        Assertions.assertThrows(RuntimeException.class, () -> validator.between(now, targetName, tomorrow, yesterday));
        Assertions.assertEquals(validator, validator.between(now, targetName, yesterday, tomorrow));

        Assertions.assertEquals(validator, validator.between(null, targetName, yesterday, tomorrow, dateFormat, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.between(null, targetName, yesterday, tomorrow, dateFormat));
        Assertions.assertThrows(RuntimeException.class, () -> validator.between(new Date(yesterday.getTime() - dayMillis), targetName, yesterday, tomorrow, dateFormat));
        Assertions.assertThrows(RuntimeException.class, () -> validator.between(new Date(tomorrow.getTime() + dayMillis), targetName, yesterday, tomorrow, dateFormat));
        Assertions.assertThrows(RuntimeException.class, () -> validator.between(now, targetName, tomorrow, yesterday, dateFormat));
        Assertions.assertEquals(validator, validator.between(now, targetName, yesterday, tomorrow, dateFormat));
    }

    @Test
    void testPattern() {
        String pureChinese = "^[\\u4e00-\\u9fa5]*$";
        Pattern pureChinesePattern = Pattern.compile(pureChinese);

        Assertions.assertEquals(validator, validator.matchPattern(null, targetName, pureChinese, "纯中文", false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.matchPattern(null, targetName, pureChinesePattern, "纯中文"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.matchPattern(null, targetName, pureChinese, "纯中文"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.matchPattern("", targetName, pureChinese, "纯中文"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.matchPattern("Goal", targetName, pureChinese, "纯中文"));
        Assertions.assertThrows(RuntimeException.class, () -> validator.matchPattern("高1", targetName, pureChinese, "纯中文"));
        Assertions.assertEquals(validator, validator.matchPattern("噶", targetName, pureChinese, "纯中文"));
        Assertions.assertEquals(validator, validator.matchPattern("噶", targetName, pureChinesePattern, "纯中文"));

        Assertions.assertEquals(validator, validator.isEmail(null, targetName, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.isEmail(null, targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.isEmail("", targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.isEmail("null", targetName));
        Assertions.assertEquals(validator, validator.isEmail("HenwyGoal@163.com", targetName));

        Assertions.assertEquals(validator, validator.isTelePhone(null, targetName, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.isTelePhone(null, targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.isTelePhone("", targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.isTelePhone("11125717814", targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.isTelePhone("1112571814", targetName));
        Assertions.assertEquals(validator, validator.isTelePhone("15625717814", targetName));
    }

    @Test
    void testSize() {
        Assertions.assertEquals(validator, validator.size((String) null, targetName, 0, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size((String) null, targetName, 0));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size("", targetName, 0));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size("123", targetName, 0));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size("123", targetName, 2));
        Assertions.assertEquals(validator, validator.size("13", targetName, 2));

        Assertions.assertEquals(validator, validator.size((Collection<Object>) null, targetName, 0, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size((Collection<Object>) null, targetName, 0));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size(Collections.emptyList(), targetName, 0));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size(Collections.singleton(new Object()), targetName, 2));
        Assertions.assertEquals(validator, validator.size(Arrays.asList(1, 2), targetName, 2));

        Assertions.assertEquals(validator, validator.size((Object[]) null, targetName, 0, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size((Object[]) null, targetName, 0));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size(new Object[0], targetName, 0));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size(new Object[]{new Object()}, targetName, 2));
        Assertions.assertEquals(validator, validator.size(new Object[2], targetName, 2));

        Assertions.assertEquals(validator, validator.size((String) null, targetName, 0, 5, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size((String) null, targetName, 0, 5));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size("", targetName, 1, 5));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size("1", targetName, 2, 5));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size("123456", targetName, 2, 5));
        Assertions.assertEquals(validator, validator.size("13", targetName, 1, 5));

        Assertions.assertEquals(validator, validator.size((Collection<Integer>) null, targetName, 0, 5, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size((Collection<Integer>) null, targetName, 0, 5));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size(Collections.emptyList(), targetName, 1, 5));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size(Collections.singleton(1), targetName, 2, 5));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size(Arrays.asList(1, 2, 3, 4, 5, 6), targetName, 2, 5));
        Assertions.assertEquals(validator, validator.size(Arrays.asList(1, 2, 3), targetName, 1, 5));

        Assertions.assertEquals(validator, validator.size((Integer[]) null, targetName, 0, 5, false));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size((Integer[]) null, targetName, 0, 5));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size(new Integer[0], targetName, 1, 5));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size(new Integer[]{1}, targetName, 2, 5));
        Assertions.assertThrows(RuntimeException.class, () -> validator.size(new Integer[]{1, 2, 3, 4, 5, 6}, targetName, 2, 5));
        Assertions.assertEquals(validator, validator.size(new Integer[]{1, 2, 3}, targetName, 1, 5));
    }

    @Test
    void testCondition() {
        Assertions.assertEquals(validator, validator.isLegal(true, targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.isLegal(null, targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.isLegal(false, targetName));

        Assertions.assertEquals(validator, validator.isExist(true, targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.isExist(null, targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.isExist(false, targetName));

        Assertions.assertEquals(validator, validator.notExist(null, targetName));
        Assertions.assertEquals(validator, validator.notExist(false, targetName));
        Assertions.assertThrows(RuntimeException.class, () -> validator.notExist(true, targetName));
    }

}
