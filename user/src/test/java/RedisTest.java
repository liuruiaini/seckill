
import com.javasm.UserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = UserApplication.class)
@RunWith(SpringRunner.class)
public class RedisTest {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test01(){
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set("test01","success");
    }
}
