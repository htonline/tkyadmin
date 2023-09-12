package me.zhengjie;

import com.deepoove.poi.XWPFTemplate;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zuohaitao
 * @date 2023-08-31 16:21
 * @describe
 */
public class WordTest {
    @Test
    public void testWord() throws IOException {
        // 获取Word模板文件地址
        String filePath = "D:\\WorkFile\\JavaProject\\tky_huaweiyun\\tkyadmin\\eladmin-system\\src\\main\\resources\\template\\doc\\templateDoc.docx";
        // 读取Word文件
        XWPFTemplate template = XWPFTemplate.compile(filePath);

        // 给文档内的变量设值
        Map<String, Object> map = new HashMap<>();
        map.put("name", "李明");
        map.put("age", "18");
        map.put("date", "2023-07-01");
        // 将值付给文档
        template.render(map);
        // 写出去的文档路径
        template.writeAndClose(Files.newOutputStream(Paths.get("D:\\WorkFile\\JavaProject\\tky_huaweiyun\\tkyadmin\\eladmin-system\\src\\main\\resources\\template\\doc\\templateDoc1.docx")));

    }
}
