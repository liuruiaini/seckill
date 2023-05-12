import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javasm.UserApplication;
import com.javasm.bean.User;
import com.javasm.bean.returnData.token;
import com.javasm.mapper.TokenMapper;
import com.javasm.mapper.UserMapperPlus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest(classes = UserApplication.class)
@RunWith(SpringRunner.class)
public class insetUserTest {
    @Resource
    private UserMapperPlus userMapperPlus;
    @Resource
    private TokenMapper tokenMapper;
    @Test
    public void insertUser(){
        for (int i = 0; i < 5000; i++) {
            userMapperPlus.insert(new User().setUserName("test"+i).setPassword("{noop}123456"));
        }
    }
    @Test
    public void delTest(){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(User::getUserName,"test");
        userMapperPlus.delete(wrapper);

    }
    @Test
    public void updateTest() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(User::getUserName, "test");
        List<User> users = userMapperPlus.selectList(wrapper);
        for (User user : users) {
            user.setPermision("[add]");
            userMapperPlus.updateById(user);
        }
    }
        @Test
        public void repeatTest(){
            List<token> list=tokenMapper.selectList(null);
            HashSet<Long> longs = new HashSet<>();
            for (token token : list) {
                longs.add(token.getId());
            }
            System.out.println(longs.size());



        }
}
