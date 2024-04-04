package website.chatx.core.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public abstract class BaseAdvice {

    protected static final String MESSAGE_CODE_ERROR_MISMATCH = "website.chatx.constraints.Mismatch.message";

    @Autowired
    protected MessageSource messageSource;
}
