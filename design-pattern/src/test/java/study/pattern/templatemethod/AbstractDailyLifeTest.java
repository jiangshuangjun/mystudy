package study.pattern.templatemethod;

import org.junit.Test;

/**
 * @author jiangsj
 * {@link AbstractDailyLife} Test
 */
public class AbstractDailyLifeTest {

    @Test
    public void myDailyLifeTest() {
        AbstractDailyLife myDailyLife = new MyDailyLife();

        myDailyLife.dailyLife();
    }

    @Test
    public void myGirlDailyLife() {
        AbstractDailyLife myGirlDailyLife = new MyGirlDailyLife();

        myGirlDailyLife.dailyLife();
    }
}