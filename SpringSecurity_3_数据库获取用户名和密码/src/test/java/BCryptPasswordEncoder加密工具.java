import com.wen.SpringSecurityApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest(classes = SpringSecurityApplication.class)
public class BCryptPasswordEncoder加密工具 {
    @Test
    public void test1(){
        // TODO 加密，生成密文
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);// $2a$10$lwjhOlX3CWloDzzZCZoN/e6oYoLCLIsLLNyYoF3lt1YuERX6WIQUK
    }

    @Test
    public void test2(){
        // TODO 校验，密码与密文比对
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches("123456", "$2a$10$lwjhOlX3CWloDzzZCZoN/e6oYoLCLIsLLNyYoF3lt1YuERX6WIQUK");
        System.out.println(matches);// true
    }
}
