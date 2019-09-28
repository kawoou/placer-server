package capstone.placer.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages="capstone.placer")
public class MyBatisConfig {
}
