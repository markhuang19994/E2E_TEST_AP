package core;

import com.Main;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/11, MarkHuang,new
 * </ul>
 * @since 2018/2/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
//@RunWith(SpringRunner.class)
//@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc; //創建MockMvc類的物件

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public void checkStatus(MvcResult result, int correctStatus) throws Exception {
        int status = 0;
        if (result != null) {
            status = result.getResponse().getStatus();
        }
        Assert.assertEquals("錯誤" + status, correctStatus, status);
    }

    @Test
    public void getHello() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = 0;
        if (result != null) {
            status = result.getResponse().getStatus();
        }
    }

    @Test
    public void getData() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/data")
                .requestAttr("projectName", "PCL")
                .accept((MediaType.APPLICATION_JSON))).andReturn();
        checkStatus(result, 200);
    }

    @Test
    public void getProjectName() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/project_name")
                .accept((MediaType.APPLICATION_JSON))).andReturn();
        checkStatus(result, 200);
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void putTestCaseData() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/project_name")
                .accept((MediaType.APPLICATION_JSON))).andReturn();
        checkStatus(result, 200);
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getAllTestCaseData() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/allTestCaseData")
                .param("projectName","PCL")
                .accept((MediaType.APPLICATION_JSON))).andReturn();
        checkStatus(result, 200);
        System.out.println(result.getResponse().getContentAsString());
    }

}