package site.elven.planning.boot.base.manager.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageManager {
    @Autowired
    private MessageSource messageSource;

    public String getMessageOrDefault(String code, String defaultValue, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultValue, locale);
    }

    public String getMessage(String code, Object... args) {
        return getMessageOrDefault(code, null, args);
    }

    public String getMessage(MessageSourceResolvable resolvable) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(resolvable, locale);
    }
}
