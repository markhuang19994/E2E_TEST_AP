import com.model.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/3/26, MarkHuang,new
 * </ul>
 * @since 2018/3/26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class SpringTest {
    @Test
    public void test01() {
        System.out.println(213);
    }
}
