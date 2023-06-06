import com.zpp.domain.ResponseResult;
import com.zpp.domain.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestController {
    private TagService tagService;
    @Test
    public ResponseResult list(){
        System.out.println(tagService.list());
        return ResponseResult.okResult(tagService.list());
    }
}
