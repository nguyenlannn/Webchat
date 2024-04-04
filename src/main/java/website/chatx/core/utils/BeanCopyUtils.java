package website.chatx.core.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class BeanCopyUtils {

    public static void copyProperties(final Object dest, final Object orig) {
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
