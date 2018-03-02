package com.test;

import org.junit.internal.ArrayComparisonFailure;
import org.junit.internal.ExactComparisonCriteria;
import org.junit.internal.InexactComparisonCriteria;

/**
 * This class is copy from junit Assert.class but it doesn't throws error when test fail
 *
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/26, MarkHuang,new
 * </ul>
 * @see org.junit.Assert
 * @since 2018/2/26
 */
public class Assert {

    private static final PostErrorMessage POST_ERROR_MESSAGE = new PostErrorMessage();
    private static int testCount = 0;

    public Assert() {
        testCount++;
    }

    public static int getTestCount() {
        return testCount;
    }

    /**
     * Asserts that a condition is true. If it isn't it throws an error message
     *
     * @param message   the identifying message
     * @param condition condition to be checked
     */
    public void assertTrue(String message, boolean condition) throws AssertErrorException {
        if (!condition) {
            fail(message);
        }
    }

    /**
     * Asserts that a condition is true. If it isn't it throws an message
     *
     * @param condition condition to be checked
     */
    public void assertTrue(boolean condition) throws AssertErrorException {
        assertTrue(null, condition);
    }

    /**
     * Asserts that a condition is false. If it isn't it throws an message
     *
     * @param message   the identifying message for the assert invalid
     * @param condition condition to be checked
     */
    public void assertFalse(String message, boolean condition) throws AssertErrorException {
        assertTrue(message, !condition);
    }

    /**
     * Asserts that a condition is false. If it isn't it throws an message
     *
     * @param condition condition to be checked
     */
    public void assertFalse(boolean condition) throws AssertErrorException {
        assertFalse(null, condition);
    }

    /**
     * Fails a test with the given message.
     *
     * @param message the identifying message for the assert invalid
     * @see PostErrorMessage
     */
    public void fail(String message) throws AssertErrorException {
        if (message == null) {
            throw new AssertErrorException(message);
        }
        throw new AssertErrorException(message);
    }

    /**
     * Fails a test with no message.
     */
    public void fail() throws AssertErrorException {
        fail(null);
    }

    /**
     * Asserts that two objects are equal. If they are not, an
     * {@link PostErrorMessage} is post with the given message. If
     * <code>expected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message  the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                 okay)
     * @param expected expected value
     * @param actual   actual value
     */
    public void assertEquals(String message, Object expected,
                             Object actual) throws AssertErrorException {
        if (equalsRegardingNull(expected, actual)) {
            return;
        } else {
            failNotEquals(message, expected, actual);
        }
    }

    private boolean equalsRegardingNull(Object expected, Object actual) {
        if (expected == null) {
            return actual == null;
        }

        return isEquals(expected, actual);
    }

    private boolean isEquals(Object expected, Object actual) {
        return expected.equals(actual);
    }

    /**
     * Asserts that two objects are equal. If they are not, an
     * {@link PostErrorMessage} without a message is thrown. If
     * <code>expected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param expected expected value
     * @param actual   the value to check against <code>expected</code>
     */
    public void assertEquals(Object expected, Object actual) throws AssertErrorException {
        assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two objects are <b>not</b> equals. If they are, an
     * {@link PostErrorMessage} is thrown with the given message. If
     * <code>unexpected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message    the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                   okay)
     * @param unexpected unexpected value to check
     * @param actual     the value to check against <code>unexpected</code>
     */
    public void assertNotEquals(String message, Object unexpected,
                                Object actual) throws AssertErrorException {
        if (equalsRegardingNull(unexpected, actual)) {
            failEquals(message, actual);
        }
    }

    /**
     * Asserts that two objects are <b>not</b> equals. If they are, an
     * {@link PostErrorMessage} without a message is thrown. If
     * <code>unexpected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param unexpected unexpected value to check
     * @param actual     the value to check against <code>unexpected</code>
     */
    public void assertNotEquals(Object unexpected, Object actual) throws AssertErrorException {
        assertNotEquals(null, unexpected, actual);
    }

    private void failEquals(String message, Object actual) throws AssertErrorException {
        String formatted = "Values should be different. ";
        if (message != null) {
            formatted = message + ". ";
        }
        formatted += "Actual: " + actual;
        fail(formatted);
    }

    /**
     * Asserts that two longs are <b>not</b> equals. If they are, an
     * {@link PostErrorMessage} is thrown with the given message.
     *
     * @param message    the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                   okay)
     * @param unexpected unexpected value to check
     * @param actual     the value to check against <code>unexpected</code>
     */
    public void assertNotEquals(String message, long unexpected, long actual) throws AssertErrorException {
        if (unexpected == actual) {
            failEquals(message, Long.valueOf(actual));
        }
    }

    /**
     * Asserts that two longs are <b>not</b> equals. If they are, an
     * {@link PostErrorMessage} without a message is thrown.
     *
     * @param unexpected unexpected value to check
     * @param actual     the value to check against <code>unexpected</code>
     */
    public void assertNotEquals(long unexpected, long actual) throws AssertErrorException {
        assertNotEquals(null, unexpected, actual);
    }


    private void failNotEquals(String message, Object expected,
                               Object actual) throws AssertErrorException {
        fail(format(message, expected, actual));
    }


    /**
     * Asserts that two doubles are <b>not</b> equal to within a positive delta.
     * If they are, an {@link PostErrorMessage} is thrown with the given
     * message. If the unexpected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertNotEquals(Double.NaN, Double.NaN, *)</code> fails
     *
     * @param message    the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                   okay)
     * @param unexpected unexpected value
     * @param actual     the value to check against <code>unexpected</code>
     * @param delta      the maximum delta between <code>unexpected</code> and
     *                   <code>actual</code> for which both numbers are still
     *                   considered equal.
     */
    public void assertNotEquals(String message, double unexpected,
                                double actual, double delta) throws AssertErrorException {
        if (!doubleIsDifferent(unexpected, actual, delta)) {
            failEquals(message, Double.valueOf(actual));
        }
    }

    /**
     * Asserts that two doubles are <b>not</b> equal to within a positive delta.
     * If they are, an {@link PostErrorMessage} is thrown. If the unexpected
     * value is infinity then the delta value is ignored.NaNs are considered
     * equal: <code>assertNotEquals(Double.NaN, Double.NaN, *)</code> fails
     *
     * @param unexpected unexpected value
     * @param actual     the value to check against <code>unexpected</code>
     * @param delta      the maximum delta between <code>unexpected</code> and
     *                   <code>actual</code> for which both numbers are still
     *                   considered equal.
     */
    public void assertNotEquals(double unexpected, double actual, double delta) throws AssertErrorException {
        assertNotEquals(null, unexpected, actual, delta);
    }

    /**
     * Asserts that two floats are <b>not</b> equal to within a positive delta.
     * If they are, an {@link PostErrorMessage} is thrown. If the unexpected
     * value is infinity then the delta value is ignored.NaNs are considered
     * equal: <code>assertNotEquals(Float.NaN, Float.NaN, *)</code> fails
     *
     * @param unexpected unexpected value
     * @param actual     the value to check against <code>unexpected</code>
     * @param delta      the maximum delta between <code>unexpected</code> and
     *                   <code>actual</code> for which both numbers are still
     *                   considered equal.
     */
    public void assertNotEquals(float unexpected, float actual, float delta) throws AssertErrorException {
        assertNotEquals(null, unexpected, actual, delta);
    }

    /**
     * Asserts that two object arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown with the given message. If
     * <code>expecteds</code> and <code>actuals</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message   the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                  okay)
     * @param expecteds Object array or array of arrays (multi-dimensional array) with
     *                  expected values.
     * @param actuals   Object array or array of arrays (multi-dimensional array) with
     *                  actual values
     */
    public void assertArrayEquals(String message, Object[] expecteds,
                                  Object[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two object arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown. If <code>expected</code> and
     * <code>actual</code> are <code>null</code>, they are considered
     * equal.
     *
     * @param expecteds Object array or array of arrays (multi-dimensional array) with
     *                  expected values
     * @param actuals   Object array or array of arrays (multi-dimensional array) with
     *                  actual values
     */
    public void assertArrayEquals(Object[] expecteds, Object[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two boolean arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown with the given message. If
     * <code>expecteds</code> and <code>actuals</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message   the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                  okay)
     * @param expecteds boolean array with expected values.
     * @param actuals   boolean array with expected values.
     */
    public void assertArrayEquals(String message, boolean[] expecteds,
                                  boolean[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two boolean arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown. If <code>expected</code> and
     * <code>actual</code> are <code>null</code>, they are considered
     * equal.
     *
     * @param expecteds boolean array with expected values.
     * @param actuals   boolean array with expected values.
     */
    public void assertArrayEquals(boolean[] expecteds, boolean[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two byte arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown with the given message.
     *
     * @param message   the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                  okay)
     * @param expecteds byte array with expected values.
     * @param actuals   byte array with actual values
     */
    public void assertArrayEquals(String message, byte[] expecteds,
                                  byte[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two byte arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown.
     *
     * @param expecteds byte array with expected values.
     * @param actuals   byte array with actual values
     */
    public void assertArrayEquals(byte[] expecteds, byte[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two char arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown with the given message.
     *
     * @param message   the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                  okay)
     * @param expecteds char array with expected values.
     * @param actuals   char array with actual values
     */
    public void assertArrayEquals(String message, char[] expecteds,
                                  char[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two char arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown.
     *
     * @param expecteds char array with expected values.
     * @param actuals   char array with actual values
     */
    public void assertArrayEquals(char[] expecteds, char[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two short arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown with the given message.
     *
     * @param message   the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                  okay)
     * @param expecteds short array with expected values.
     * @param actuals   short array with actual values
     */
    public void assertArrayEquals(String message, short[] expecteds,
                                  short[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two short arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown.
     *
     * @param expecteds short array with expected values.
     * @param actuals   short array with actual values
     */
    public void assertArrayEquals(short[] expecteds, short[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two int arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown with the given message.
     *
     * @param message   the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                  okay)
     * @param expecteds int array with expected values.
     * @param actuals   int array with actual values
     */
    public void assertArrayEquals(String message, int[] expecteds,
                                  int[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two int arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown.
     *
     * @param expecteds int array with expected values.
     * @param actuals   int array with actual values
     */
    public void assertArrayEquals(int[] expecteds, int[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two long arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown with the given message.
     *
     * @param message   the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                  okay)
     * @param expecteds long array with expected values.
     * @param actuals   long array with actual values
     */
    public void assertArrayEquals(String message, long[] expecteds,
                                  long[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two long arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown.
     *
     * @param expecteds long array with expected values.
     * @param actuals   long array with actual values
     */
    public void assertArrayEquals(long[] expecteds, long[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two double arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown with the given message.
     *
     * @param message   the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                  okay)
     * @param expecteds double array with expected values.
     * @param actuals   double array with actual values
     * @param delta     the maximum delta between <code>expecteds[i]</code> and
     *                  <code>actuals[i]</code> for which both numbers are still
     *                  considered equal.
     */
    public void assertArrayEquals(String message, double[] expecteds,
                                  double[] actuals, double delta) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(delta).arrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two double arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown.
     *
     * @param expecteds double array with expected values.
     * @param actuals   double array with actual values
     * @param delta     the maximum delta between <code>expecteds[i]</code> and
     *                  <code>actuals[i]</code> for which both numbers are still
     *                  considered equal.
     */
    public void assertArrayEquals(double[] expecteds, double[] actuals, double delta) {
        assertArrayEquals(null, expecteds, actuals, delta);
    }

    /**
     * Asserts that two float arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown with the given message.
     *
     * @param message   the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                  okay)
     * @param expecteds float array with expected values.
     * @param actuals   float array with actual values
     * @param delta     the maximum delta between <code>expecteds[i]</code> and
     *                  <code>actuals[i]</code> for which both numbers are still
     *                  considered equal.
     */
    public void assertArrayEquals(String message, float[] expecteds,
                                  float[] actuals, float delta) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(delta).arrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two float arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown.
     *
     * @param expecteds float array with expected values.
     * @param actuals   float array with actual values
     * @param delta     the maximum delta between <code>expecteds[i]</code> and
     *                  <code>actuals[i]</code> for which both numbers are still
     *                  considered equal.
     */
    public void assertArrayEquals(float[] expecteds, float[] actuals, float delta) {
        assertArrayEquals(null, expecteds, actuals, delta);
    }

    /**
     * Asserts that two object arrays are equal. If they are not, an
     * {@link PostErrorMessage} is thrown with the given message. If
     * <code>expecteds</code> and <code>actuals</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message   the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                  okay)
     * @param expecteds Object array or array of arrays (multi-dimensional array) with
     *                  expected values.
     * @param actuals   Object array or array of arrays (multi-dimensional array) with
     *                  actual values
     */
    private void internalArrayEquals(String message, Object expecteds,
                                     Object actuals) throws ArrayComparisonFailure {
        new ExactComparisonCriteria().arrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two doubles are equal to within a positive delta.
     * If they are not, an {@link PostErrorMessage} is thrown with the given
     * message. If the expected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertEquals(Double.NaN, Double.NaN, *)</code> passes
     *
     * @param message  the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                 okay)
     * @param expected expected value
     * @param actual   the value to check against <code>expected</code>
     * @param delta    the maximum delta between <code>expected</code> and
     *                 <code>actual</code> for which both numbers are still
     *                 considered equal.
     */
    public void assertEquals(String message, double expected,
                             double actual, double delta) throws AssertErrorException {
        if (doubleIsDifferent(expected, actual, delta)) {
            failNotEquals(message, Double.valueOf(expected), Double.valueOf(actual));
        }
    }

    /**
     * Asserts that two floats are equal to within a positive delta.
     * If they are not, an {@link PostErrorMessage} is thrown with the given
     * message. If the expected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertEquals(Float.NaN, Float.NaN, *)</code> passes
     *
     * @param message  the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                 okay)
     * @param expected expected value
     * @param actual   the value to check against <code>expected</code>
     * @param delta    the maximum delta between <code>expected</code> and
     *                 <code>actual</code> for which both numbers are still
     *                 considered equal.
     */
    public void assertEquals(String message, float expected,
                             float actual, float delta) throws AssertErrorException {
        if (floatIsDifferent(expected, actual, delta)) {
            failNotEquals(message, Float.valueOf(expected), Float.valueOf(actual));
        }
    }

    /**
     * Asserts that two floats are <b>not</b> equal to within a positive delta.
     * If they are, an {@link PostErrorMessage} is thrown with the given
     * message. If the unexpected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertNotEquals(Float.NaN, Float.NaN, *)</code> fails
     *
     * @param message    the identifying message for the {@link PostErrorMessage} (<code>null</code>
     *                   okay)
     * @param unexpected unexpected value
     * @param actual     the value to check against <code>unexpected</code>
     * @param delta      the maximum delta between <code>unexpected</code> and
     *                   <code>actual</code> for which both numbers are still
     *                   considered equal.
     */
    public void assertNotEquals(String message, float unexpected,
                                float actual, float delta) throws AssertErrorException {
        if (!floatIsDifferent(unexpected, actual, delta)) {
            failEquals(message, Float.valueOf(actual));
        }
    }


    private boolean doubleIsDifferent(double d1, double d2, double delta) {
        return Double.compare(d1, d2) != 0 && (!(Math.abs(d1 - d2) <= delta));
    }

    private boolean floatIsDifferent(float f1, float f2, float delta) {
        return Float.compare(f1, f2) != 0 && (!(Math.abs(f1 - f2) <= delta));
    }

    /**
     * Asserts that two longs are equal. If they are not, an
     * {@link AssertionError} is thrown.
     *
     * @param expected expected long value.
     * @param actual   actual long value
     */
    public void assertEquals(long expected, long actual) throws AssertErrorException {
        assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two longs are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message  the identifying message for the {@link AssertionError} (<code>null</code>
     *                 okay)
     * @param expected long expected value.
     * @param actual   long actual value
     */
    public void assertEquals(String message, long expected, long actual) throws AssertErrorException {
        if (expected != actual) {
            failNotEquals(message, Long.valueOf(expected), Long.valueOf(actual));
        }
    }

    private String format(String message, Object expected, Object actual) {
        String formatted = "";
        if (message != null && !message.equals("")) {
            formatted = message + " ";
        }
        String expectedString = String.valueOf(expected);
        String actualString = String.valueOf(actual);
        if (expectedString.equals(actualString)) {
            return formatted + "expected: "
                    + formatClassAndValue(expected, expectedString)
                    + " but was: " + formatClassAndValue(actual, actualString);
        } else {
            return formatted + "expected:<" + expectedString + "> but was:<"
                    + actualString + ">";
        }
    }

    private String formatClassAndValue(Object value, String valueString) {
        String className = value == null ? "null" : value.getClass().getName();
        return className + "<" + valueString + ">";
    }

}


