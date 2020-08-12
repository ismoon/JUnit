package com.ismoon.junit.assert_that;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author ismoon on 2018. 1. 13.
 */
public class AssertThatTest {
    @Test
    public void AssertThatIsTest() {
        //is는 첫번째 파라미터와 자기 자신의 파라미터가 동일한지 여부를 체크한다.
        AssertThatSample sample = new AssertThatSample();
        assertThat(sample.getIsTestValue(), is(1));
        assertThat(sample.sum(4, 6), is(10));
        assertThat("Sample string.", is(not(startsWith("Test"))));
    }

    @Test
    public void AssertBasicMatchers() {//wntjrcnrk
        //allOf : 내부에 선언된 모든 매쳐가 정상일 경우 통과한다.
        assertThat("myValue", allOf(startsWith("my"), containsString("Val")));

        //anyOf : 내부에 선언된 매쳐중 하나 이상 통과할 경우 통과한다.
        assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")));

        //both : both A and B 형식으로 matcher를 사용할 수 있게 해 준다.
        //       A, B 매쳐 둘다 통과할 경우 테스트가 성공한다.
        assertThat("fab", both(containsString("a")).and(containsString("b")));

        //either : either A or B 형식으로 matcher를 사용할 수 있게 해 준다.
        //         A, B 매쳐 둘중 하나가 성공할 경우 테스트가 성공한다.
        assertThat("fan", either(containsString("a")).or(containsString("b")));

        //describedAs : 매쳐내부의 메시지를 변경할 수 있다.
        //TODO 별도로 검색 해보기
        BigDecimal mydecimal = new BigDecimal("32123");
        assertThat(new BigDecimal("32123"), describedAs("a big decimal equal to %0"
                , equalTo(mydecimal), mydecimal.toPlainString()));

        //everyItem : 배열이나 리스트를 순회하며 매쳐가 실행된다.
        assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")));

        //isA : 비교되는 값이 특정 클래스일 경우 테스트가 통과된다. is(instanceOf(SomeClass.class))와 동일하다.
        //TODO 별도로 검색 해보기
//        Integer i = new Integer(3);
//        assertThat(Integer.valueOf(String.valueOf(i)), isA(Integer.class));
        assertThat(false, anything());

        //hasItem : 배열에서 매쳐가 통과하는 값이 하나 이상이 있는지 여부를 검사한다.
        assertThat(Arrays.asList("foo", "bar"), hasItem("bar"));

        //hasItems : 배열에서 매쳐리스트에 선언된 값들 모두가 하나 이상 있는지 여부를 검사한다.
        assertThat(Arrays.asList("foo", "bar", "baz"), hasItems("baz", "foo"));
    }

    @Test
    public void AssertNullValueMatchers() {
        String name = null;
        String addr = "120349511";
        assertThat(name, is(nullValue()));
        assertThat(addr, is(notNullValue()));
    }

    @Test
    public void AssertInstanceMatchers() {
        assertThat("", instanceOf(String.class));
    }

    @Test
    public void CustomMatcherTest() {
        // 입력 된 숫자가 5의 배수인지 확인하는 Matcher
        assertThat(30, is(divisorOfFive()));
    }

    private static Matcher<Integer> divisorOfFive() {
        return new TypeSafeMatcher<Integer>() {
            Integer internalNumber;

            @Override
            public boolean matchesSafely(Integer number) {
                internalNumber = number;
                return number % 5 == 0;
            }

            public void describeTo(Description description) {
                description.appendValue(internalNumber + "가 5의 배수여부 확인");
            }
        };
    }
}
