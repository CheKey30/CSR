import org.mybatis.generator.api.ShellRunner;

/**
 * @author:shuchen
 * @date: 2021/10/15
 * @time: 3:09 下午
 */
public class GeneratorSql {
    public static void main(String[] args) {
        String config = GeneratorSql.class.getClassLoader()
                .getResource("generatorConfig.xml").getFile();
        String[] arg = { "-configfile", config, "-overwrite" };
        ShellRunner.main(arg);
    }
}
