package io.qleoz12.com.alura.literalura.i18n;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

@Component
public class I18nUtil {

    @Autowired
    private MessageSource messageSource;

    /**
     * Retrieve a localized message based on the given language code.
     * @param code The key in the properties file.
     * @param language The language code (e.g., "en", "pt").
     * @param args Arguments for message formatting (optional).
     * @return The localized message.
     */
    public String getMessage(String code, String language, String... args) {
        Locale locale;
        // Fallback to system default or English
        try {
            locale = new Locale(language); // Default locale
        } catch (IllegalStateException e) {
            locale = Locale.ENGLISH; // Fallback to English
        }

        // Retrieve message from properties file
        String result= messageSource.getMessage(code, args, locale);
        return result;
    }
}