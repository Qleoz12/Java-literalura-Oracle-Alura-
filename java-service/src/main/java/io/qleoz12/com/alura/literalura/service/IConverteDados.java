package io.qleoz12.com.alura.literalura.service;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> classe);

}
