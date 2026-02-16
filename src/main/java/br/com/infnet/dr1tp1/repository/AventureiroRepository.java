package br.com.infnet.dr1tp1.repository;

import br.com.infnet.dr1tp1.enums.Classes;
import br.com.infnet.dr1tp1.enums.Especies;
import br.com.infnet.dr1tp1.exception.AventureiroNotFoundException;
import br.com.infnet.dr1tp1.model.Aventureiro;
import br.com.infnet.dr1tp1.model.Companheiro;
import br.com.infnet.dr1tp1.validation.AventureiroValidator;
import br.com.infnet.dr1tp1.validation.CompanheiroValidator;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Repository
public class AventureiroRepository {

    private static final int QUANTIDADE_AVENTUREIROS = 100;
    private static final double CHANCE_TER_COMPANHEIRO = 0.5;
    private static final int NIVEL_MINIMO = 1;
    private static final int NIVEL_MAXIMO = 20;
    private static final int LEALDADE_MINIMA = 0;
    private static final int LEALDADE_MAXIMA = 100;

    private final List<Aventureiro> aventureiros = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final Faker faker = new Faker();
    private final Random random = new Random();

    public AventureiroRepository() {
        inicializarDados();
    }

    private void inicializarDados() {
        Classes[] classes = Classes.values();
        Especies[] especies = Especies.values();

        IntStream.range(0, QUANTIDADE_AVENTUREIROS)
                .mapToObj(i -> criarAventureiro(classes, especies))
                .forEach(aventureiros::add);
    }

    private Aventureiro criarAventureiro(Classes[] classes, Especies[] especies) {
        String nome = gerarNomeAventureiro();
        Classes classe = classes[random.nextInt(classes.length)];
        int nivel = faker.number().numberBetween(NIVEL_MINIMO, NIVEL_MAXIMO + 1);
        AventureiroValidator.validateForCreationAndThrow(nome, classe, nivel);

        Aventureiro aventureiro = new Aventureiro();
        aventureiro.setId(idGenerator.getAndIncrement());
        aventureiro.setNome(nome);
        aventureiro.setClasse(classe);
        aventureiro.setNivel(nivel);
        aventureiro.setAtivo(true);

        if (random.nextDouble() < CHANCE_TER_COMPANHEIRO) {
            aventureiro.setCompanheiro(criarCompanheiro(especies));
        } else {
            aventureiro.setCompanheiro(null);
        }

        return aventureiro;
    }

    private String gerarNomeAventureiro() {
        return switch (random.nextInt(3)) {
            case 0 -> faker.name().firstName() + " " + faker.ancient().hero();
            case 1 -> faker.ancient().god() + " " + faker.name().lastName();
            default -> faker.superhero().name();
        };
    }

    private Companheiro criarCompanheiro(Especies[] especies) {
        String nomeCompanheiro = switch (random.nextInt(4)) {
            case 0 -> faker.animal().name();
            case 1 -> faker.cat().name();
            case 2 -> faker.dog().name();
            default -> faker.funnyName().name();
        };

        Especies especie = especies[random.nextInt(especies.length)];
        int lealdade = faker.number().numberBetween(LEALDADE_MINIMA, LEALDADE_MAXIMA + 1);
        CompanheiroValidator.validateForCreationAndThrow(nomeCompanheiro, especie, lealdade);

        return new Companheiro(nomeCompanheiro, especie, lealdade);
    }

    public Aventureiro save(Aventureiro aventureiro) {
        AventureiroValidator.validateAndThrow(aventureiro);
        if (aventureiro.getCompanheiro() != null && aventureiro.getCompanheiro().isPresent()) {
            CompanheiroValidator.validateAndThrow(aventureiro.getCompanheiro().get());
        }

        if (aventureiro.getId() == null || aventureiro.getId() == 0) {
            aventureiro.setId(idGenerator.getAndIncrement());
            aventureiros.add(aventureiro);
        } else {
            updateExisting(aventureiro);
        }
        return aventureiro;
    }

    private void updateExisting(Aventureiro aventureiro) {
        for (int i = 0; i < aventureiros.size(); i++) {
            if (Objects.equals(aventureiros.get(i).getId(), aventureiro.getId())) {
                aventureiros.set(i, aventureiro);
                return;
            }
        }
        throw new AventureiroNotFoundException(aventureiro.getId());
    }

    public List<Aventureiro> findAll() {
        return new ArrayList<>(aventureiros);
    }

    public Optional<Aventureiro> findById(Long id) {
        return aventureiros.stream()
                .filter(a -> Objects.equals(a.getId(), id))
                .findFirst();
    }
}
