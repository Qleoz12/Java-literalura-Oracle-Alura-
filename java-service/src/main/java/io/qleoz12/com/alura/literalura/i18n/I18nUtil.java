package io.qleoz12.com.alura.literalura.i18n;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

@Component
public class I18nUtil {

    @Autowired
    MessageSource messageSource;

//    @Resource(name = "localHolder")
//    LocalHolder localHolder;

//    public String getMessage(String code, String... args){
//        return messageSource.getMessage(code, args, localHolder.getCurrentLocale());
//    }
    public String getMessage(String code, String... args){
        Locale locale;
//        try {
//            locale = localHolder.getCurrentLocale(); // This depends on the request scope
//        } catch (IllegalStateException e) {
//            locale = Locale.getDefault(); // Fallback to default locale
//        }

        locale = Locale.ENGLISH; // Fallback to default locale
        locale = new Locale("pt");; // Fallback to default locale
        // Replace with your actual message source logic
        return messageSource.getMessage(code,args, locale);

    }

}
