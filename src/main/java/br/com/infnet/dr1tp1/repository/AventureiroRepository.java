package br.com.infnet.dr1tp1.repository;

import br.com.infnet.dr1tp1.domain.Aventureiro;
import br.com.infnet.dr1tp1.enums.Classes;
import br.com.infnet.dr1tp1.exceptions.EntityNotFoundException;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

@Repository
public class AventureiroRepository {
    private final List<Aventureiro> aventureiros;

    public AventureiroRepository(List<Aventureiro> aventureiros) {
        this.aventureiros = aventureiros;
    }

    public void carregarAventureiros(){
        Faker faker = new Faker();
        aventureiros.addAll(initRepo(faker));
    }

    private List<Aventureiro> initRepo(Faker faker){
        return LongStream.rangeClosed(1,100)
                .mapToObj(i -> new Aventureiro(
                        faker.elderScrolls().firstName() + " " + faker.elderScrolls().lastName(),
                        Classes.values()[new Random().nextInt(Classes.values().length)],
                        faker.number().numberBetween(1, 1000)))
                .toList();
    }

    public List<Aventureiro> findAll() {
        return new ArrayList<>(aventureiros);
    }

    public Aventureiro salvarAventureiro(Aventureiro aventureiro) {
        aventureiros.add(aventureiro);
        return aventureiro;
    }

    public Aventureiro buscarPorId(Long id){
        return aventureiros.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));
    }

    public void reativarAventureiro(Long id) {
        Aventureiro aventureiro = buscarPorId(id);
        aventureiro.recrutar();
    }

    public void desativarAventureiro(Long id) {
        Aventureiro aventureiro = buscarPorId(id);
        aventureiro.encerrarVinculo();
    }
}
