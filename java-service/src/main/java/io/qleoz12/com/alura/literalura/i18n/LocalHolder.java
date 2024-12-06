package io.qleoz12.com.alura.literalura.i18n;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocalHolder {
    private Locale currentLocale;
}
