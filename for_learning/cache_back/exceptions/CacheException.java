package cn.hehe9.common.exceptions;

import java.util.Arrays;
import java.util.List;


/**
 * Cache related exception
 */
public class CacheException extends RuntimeException {

//    String sourceFile;
//    List<String> source;
//    Integer line;
//
//    public CacheException(String message, Throwable cause) {
//        super(message, cause);
//        StackTraceElement element = getInterestingStrackTraceElement(cause);
//        if(element != null) {
//            ApplicationClass applicationClass = Jws.classes.getApplicationClass(element.getClassName());
//            sourceFile = applicationClass.javaFile.relativePath();
//            source = Arrays.asList(applicationClass.javaSource.split("\n"));
//            line = element.getLineNumber();
//        }
//    }
//
//    @Override
//    public String getErrorTitle() {
//        return "Cache error";
//    }
//
//    @Override
//    public String getErrorDescription() {
//        return getMessage();
//    }
//
//    public String getSourceFile() {
//        return sourceFile;
//    }
//
//    public List<String> getSource() {
//        return source;
//    }
//
//    public Integer getLineNumber() {
//        return line;
//    }
//
//    @Override
//    public boolean isSourceAvailable() {
//        return sourceFile != null;
//    }

}
