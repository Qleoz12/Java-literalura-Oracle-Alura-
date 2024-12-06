package io.qleoz12.com.alura.literalura.principal;

import io.qleoz12.com.alura.literalura.i18n.I18nUtil;
import io.qleoz12.com.alura.literalura.model.Autor;
import io.qleoz12.com.alura.literalura.model.Book;
import io.qleoz12.com.alura.literalura.model.LivroDTO;
import io.qleoz12.com.alura.literalura.repository.AutorRepository;
import io.qleoz12.com.alura.literalura.repository.LivroRepository;
import io.qleoz12.com.alura.literalura.service.ConsumoAPI;
import io.qleoz12.com.alura.literalura.service.ConverteDados;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Principal {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private ConsumoAPI consumoAPI;

    @Autowired
    private ConverteDados converteDados;

    @Autowired
    private I18nUtil i18nUtil;

    private final Scanner leitura = new Scanner(System.in);

    private static final Logger logger = LoggerFactory.getLogger(Principal.class);

    private String lng="en";
    public Principal(LivroRepository livroRepository, ConsumoAPI consumoAPI, ConverteDados converteDados) {
        this.livroRepository = livroRepository;
        this.consumoAPI = consumoAPI;
        this.converteDados = converteDados;
    }

    public void executar() {
        boolean running = true;
        logger.info("select your language  [en,pt]" );
        lng= leitura.nextLine();
        while (running) {

            exibirMenu();
            var opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1 -> buscarLivrosPeloTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivos();
                case 5 -> listarAutoresVivosRefinado();
                case 6 -> listarAutoresPorAnoDeMorte();
                case 7 -> listarLivrosPorIdioma();
                case 0 -> {
                    logger.info(i18nUtil.getMessage("closing.message",lng));
                    running = false;
                }
                default -> logger.warn(i18nUtil.getMessage("invalid.option",lng));
            }
        }
    }

    private void exibirMenu() {
        logger.info(String.format("%s\n %s\n %s\n %s\n %s\n ",
                i18nUtil.getMessage("menu.header",lng),
                i18nUtil.getMessage("menu.title",lng),
                i18nUtil.getMessage("menu.description",lng),
                i18nUtil.getMessage("menu.separator",lng),
                i18nUtil.getMessage("menu.options",lng)));
    }

    @Transactional
    private void salvarLivros(List<Book> livros) {
        for (Book livro : livros) {
            livroRepository.save(livro);
        }
        logger.info(i18nUtil.getMessage("books.saved.db",lng));
    }

    private void buscarLivrosPeloTitulo() {
        String baseURL = "https://gutendex.com/books?search=";

        try {
            logger.info(i18nUtil.getMessage("enter.book.title",lng));
            String titulo = leitura.nextLine();
            String endereco = baseURL + titulo.replace(" ", "%20");
            logger.info("URL da API: {}", endereco);

            String jsonResponse = consumoAPI.obterDados(endereco);

            if (jsonResponse.isEmpty()) {
                logger.warn(i18nUtil.getMessage("api.response.empty",lng));
                return;
            }

            JsonNode rootNode = converteDados.getObjectMapper().readTree(jsonResponse);
            JsonNode resultsNode = rootNode.path("results");

            if (resultsNode.isEmpty()) {
                logger.warn(i18nUtil.getMessage("book.not.found",lng));
                return;
            }

            List<LivroDTO> livrosDTO = converteDados.getObjectMapper()
                    .readerForListOf(LivroDTO.class)
                    .readValue(resultsNode);

            List<Book> livrosExistentes = livroRepository.findByTitulo(titulo);
            livrosExistentes.forEach(livroExistente -> livrosDTO.removeIf(livroDTO -> livroExistente.getTitulo().equals(livroDTO.titulo())));

            List<Book> booksByIds = livroRepository.findByInternalIdIn(livrosDTO.stream().map(LivroDTO::internalId).collect(Collectors.toList()));
            booksByIds.forEach(bookFounded -> livrosDTO.removeIf(livroDTO -> bookFounded.getInternalId().equals(livroDTO.internalId())));

            if (!livrosDTO.isEmpty()) {
                logger.info(i18nUtil.getMessage("books.save",lng));
                List<Book> novosLivros = livrosDTO.stream().map(Book::new).collect(Collectors.toList());
                salvarLivros(novosLivros);
                logger.info(i18nUtil.getMessage("books.saved.success",lng));
            }

            if (!livrosDTO.isEmpty()) {
                logger.info(i18nUtil.getMessage("books.found",lng));
                Set<String> titulosExibidos = new HashSet<>();
                livrosDTO.forEach(livro -> {
                    if (titulosExibidos.add(livro.titulo())) {
                        logger.info("{}", livro);
                    }
                });
            }

            logger.info(i18nUtil.getMessage("books.already.registered",lng));
            booksByIds.forEach(x->{
                logger.info("\n");
                logger.info(x.toString());
            }
            );


        } catch (Exception e) {
            logger.error(i18nUtil.getMessage("error.fetching.books",lng), e.getMessage(), e);
        }
    }

    private void listarLivrosRegistrados() {
        List<Book> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            logger.info(i18nUtil.getMessage("no.books.registered",lng));
        } else {
            livros.forEach(livro -> logger.info("{}", livro));
        }
    }

    private void listarAutoresRegistrados() {
        List<Book> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            logger.info(i18nUtil.getMessage("no.authors.registered",lng));
        } else {
            livros.stream()
                    .map(Book::getAutor)
                    .distinct()
                    .forEach(autor -> logger.info("{}", autor.getAutor()));
        }
    }

    private void listarAutoresVivos() {
        logger.info(i18nUtil.getMessage("enter.year",lng));
        Integer ano = leitura.nextInt();
        leitura.nextLine();

        Year year = Year.of(ano);

        List<Autor> autores = livroRepository.findAutoresVivos(year);
        if (autores.isEmpty()) {
            logger.info(i18nUtil.getMessage("no.authors.found",lng));
        } else {
            logger.info(i18nUtil.getMessage("authors.list.by.year",lng), ano);
            autores.forEach(autor -> logger.info("{}", autor));
        }
    }

    private void listarAutoresVivosRefinado() {
        System.out.println("Digite o ano: ");
        Integer ano = leitura.nextInt();
        leitura.nextLine();

        Year year = Year.of(ano);

        List<Autor> autores = livroRepository.findAutoresVivosRefinado(year);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado.");
        } else {
            System.out.println("Lista de autores nascidos no ano de " + ano + ":\n");

            autores.forEach(autor -> {
                if (Autor.possuiAno(autor.getYearBorn()) && Autor.possuiAno(autor.getYearDeath())) {
                    String nomeAutor = autor.getAutor();
                    String anoNascimento = autor.getYearBorn().toString();
                    String anoFalecimento = autor.getYearDeath().toString();
                    System.out.println(nomeAutor + " (" + anoNascimento + " - " + anoFalecimento + ")");

                }
            });
        }
    }

    private void listarAutoresPorAnoDeMorte() {
        System.out.println("Digite o ano: ");
        Integer ano = leitura.nextInt();
        leitura.nextLine();

        Year year = Year.of(ano);

        List<Autor> autores = livroRepository.findAutoresPorAnoDeMorte(year);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado.");
        } else {

            System.out.println("Lista de autores que morreram no ano de " + ano + ":\n");


            autores.forEach(autor -> {
                if (Autor.possuiAno(autor.getYearBorn()) && Autor.possuiAno(autor.getYearDeath())) {
                    String nomeAutor = autor.getAutor();
                    String anoNascimento = autor.getYearBorn().toString();
                    String anoFalecimento = autor.getYearDeath().toString();
                    System.out.println(nomeAutor + " (" + anoNascimento + " - " + anoFalecimento + ")");
                }
            });
        }
    }


    private void listarLivrosPorIdioma() {
        System.out.println("""
                Digite o idioma pretendido:
                Inglês (en)
                Português (pt)
                Espanhol (es)
                Francês (fr)
                Alemão (de)
                """);
        String idioma = leitura.nextLine();

        List<Book> livros = livroRepository.findByIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma especificado.");
        } else {
            livros.forEach(livro -> {
                String titulo = livro.getTitulo();
                String autor = livro.getAutor().getAutor();
                String idiomaLivro = livro.getIdioma();

                System.out.println("Título: " + titulo);
                System.out.println("Autor: " + autor);
                System.out.println("Idioma: " + idiomaLivro);
                System.out.println("----------------------------------------");
            });
        }
    }


}
