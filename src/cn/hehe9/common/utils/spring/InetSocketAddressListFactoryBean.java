package cn.hehe9.common.utils.spring;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;


/**
 * <b><code>InetSocketAddressListFactoryBean</code></b>
 * <p>
 * Comment here.
 * </p>
 * <b>Creation Time:</b> Jul 30, 2013 11:18:44 AM
 * @author Kenny Qi
 * @since lottery 1.0
 */
public class InetSocketAddressListFactoryBean implements FactoryBean {
    private String stringValue;

    @Override
    public List<InetSocketAddress> getObject() throws Exception {
        if (stringValue == null)
            return null;
        String[] strs = stringValue.split("[,;]");
        List<InetSocketAddress> result = new ArrayList<InetSocketAddress>(strs.length);

        for (String s : strs) {
        	String[] addr = s.split(":");
            result.add(new InetSocketAddress(addr[0], Integer.valueOf(addr[1])));
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
