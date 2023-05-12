import com.javasm.GoodsApplication;
import com.javasm.mapper.OrderMapper;
import com.javasm.mapper.SecGoodsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = GoodsApplication.class)
@RunWith(SpringRunner.class)
public class MysqlTest {
    @Resource
    private SecGoodsMapper secGoodsMapper;
    @Resource
    private OrderMapper orderMapper;
    @Test
    public void test(){
        System.out.println(secGoodsMapper.selectById(1).getStockCount());
        System.out.println(orderMapper.selectCount(null));
    }
}
