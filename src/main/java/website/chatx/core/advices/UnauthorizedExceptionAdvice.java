package website.chatx.core.advices;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import website.chatx.core.base.BaseAdvice;
import website.chatx.core.common.CommonResponse;
import website.chatx.core.exception.UnauthorizedException;

@ControllerAdvice
public class UnauthorizedExceptionAdvice extends BaseAdvice {

    private static final String MESSAGE_CODE_RESPONSE_MESSAGE = "UnauthorizedExceptionAdvice";

    /**
     * Common logger.
     */
    private final Log LOGGER = LogFactory.getLog(getClass());

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<CommonResponse> exception(UnauthorizedException exception) {

        LOGGER.warn(String.format("%s - %s", HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()));
        LOGGER.warn(exception.getMessage());

        return CommonResponse.error(StringUtils.capitalize(
                        messageSource.getMessage(MESSAGE_CODE_RESPONSE_MESSAGE, null, LocaleContextHolder.getLocale())),
                HttpStatus.UNAUTHORIZED.value(),
                exception.getErrors());
    }
}
