package cn.hehe9.common.utils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;

import com.twogotrade.common.utils.PathUtils;

/**
 * .
 * <p/>
 * <br>==========================
 * <br> 公司：优视科技-游戏中心
 * <br> 系统：九游游戏中心客户端后台
 * <br> 开发：陈宏洪（chenhh3@ucweb.com）
 * <br> 创建时间：13-10-12 下午12:11
 * <br>==========================
 */
public class FileResourceLoader {

    private Logger logger = Logger.getLogger(FileResourceLoader.class);

    private static final String JWS_CLASSPATH_PREFIX = "jws:";

    public String getAbsolutePath(String location) {

        //从 jws 的工作目录查找资源
        if (location.startsWith(JWS_CLASSPATH_PREFIX)) {
            location = location.substring(JWS_CLASSPATH_PREFIX.length());
//            return Jws.applicationPath.getAbsolutePath() + location;
            return PathUtils.find(location);
        }

        //使用 spring 的资源加载器进行查找资源
        DefaultResourceLoader springLoader = new DefaultResourceLoader();
        File file;
        try {
            file = springLoader.getResource(location).getFile();
        } catch (IOException e) {
            logger.warn("无法解析文件资源路径，现返回源路径：" + location);
            return location;
        }
        return file.getAbsolutePath();

    }
}
