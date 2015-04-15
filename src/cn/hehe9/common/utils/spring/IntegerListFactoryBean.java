package cn.hehe9.common.utils.spring;

import org.springframework.beans.factory.FactoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>IntegerListFactoryBean</code></b>
 * <p>
 * Comment here.
 * </p>
 * <b>Creation Time:</b> Jul 30, 2013 11:18:39 AM
 * @author Kenny Qi
 * @since lottery 1.0
 */
public class IntegerListFactoryBean implements FactoryBean {
    private String stringValue;

    @Override
    public List<Integer> getObject() throws Exception {
        if (stringValue == null)
            return null;
        String[] strs = stringValue.split("[,;]");
        List<Integer> result = new ArrayList<Integer>(strs.length);

        for (String s : strs) {
            result.add(Integer.valueOf(s));
        }
        
        return result;
    }

    @Override
    public Class<?> getObjectType() {
        return List.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
