package br.com.infnet.guilda_dos_aventureiros.service;

import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Companheiro;
import br.com.infnet.guilda_dos_aventureiros.dto.AventureiroAtualizacaoRequest;
import br.com.infnet.guilda_dos_aventureiros.dto.AventureiroResponse;
import br.com.infnet.guilda_dos_aventureiros.dto.AventureiroResumoResponse;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Aventureiro;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.AventureiroClasses;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.CompanheiroEspecies;
import br.com.infnet.guilda_dos_aventureiros.exceptions.EntityNotFoundException;
import br.com.infnet.guilda_dos_aventureiros.repositories.aventura.AventureiroFakeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AventureiroService {
    private final AventureiroFakeRepository aventureiroFakeRepository;
    private final AtomicLong idGenerator = new AtomicLong(101);

    public AventureiroService(AventureiroFakeRepository aventureiroFakeRepository) {
        this.aventureiroFakeRepository = aventureiroFakeRepository;
        carregarAventureiros();
    }

    public void carregarAventureiros() {
        aventureiroFakeRepository.carregarAventureiros();
    }

    public Aventureiro criarAventureiro(Aventureiro aventureiro) {
        aventureiro.setId(idGenerator.getAndIncrement());
        aventureiroFakeRepository.salvarAventureiro(aventureiro);
        return aventureiro;
    }

    public List<AventureiroResponse> listarAventureiros() {
        List<Aventureiro> aventureiros = aventureiroFakeRepository.findAll();
        return aventureiros.stream()
                .map(a -> new AventureiroResponse(
                        a.getId(),
                        a.getNome(),
                        a.getClasse(),
                        a.getNivel(),
                        a.isAtivo(),
                        Optional.ofNullable(a.getCompanheiro())
                ))
                .toList();
    }

   public List<AventureiroResumoResponse> listarComFiltros (AventureiroClasses classe, Boolean ativo, Integer nivelMinimo, int page, int size) {
        List <Aventureiro> todos = aventureiroFakeRepository.findAll();
        List <Aventureiro> filtrados = todos.stream()
                .filter(a -> classe == null || a.getClasse().equals(classe))
                .filter(a -> ativo == null || a.isAtivo() == ativo)
                .filter(a -> nivelMinimo == null || a.getNivel() >= nivelMinimo)
                .toList();

        return paginar(filtrados, page, size)
                .stream()
                .map(this::convertToResumoResponse)
                .toList();
   }

   public long contarAventureirosComFiltros(AventureiroClasses classe, Boolean ativo, Integer nivelMinimo) {
       List<Aventureiro> todos = aventureiroFakeRepository.findAll();
       return todos.stream()
               .filter(a -> classe == null || a.getClasse().equals(classe))
               .filter(a -> ativo == null || a.isAtivo() == ativo)
               .filter(a -> nivelMinimo == null || a.getNivel() >= nivelMinimo)
               .count();
   }

    public AventureiroResponse buscarPorId (Long id) {
        Aventureiro aventureiro = aventureiroFakeRepository.buscarPorId(id);
        return new AventureiroResponse(
                id,
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo(),
                Optional.ofNullable(aventureiro.getCompanheiro())
        );
    }

    public Aventureiro atualizarAventureiro(Long id, AventureiroAtualizacaoRequest request) {
        Aventureiro aventureiroExistente = aventureiroFakeRepository.buscarPorId(id);
        aventureiroExistente.setNome(request.nome());
        aventureiroExistente.setClasse(request.classe());
        aventureiroExistente.setNivel(request.nivel());
        return aventureiroExistente;
    }

    public void encerrarVinculo(Long id) {
        aventureiroFakeRepository.desativarAventureiro(id);
    }

    public void recrutarAventureiro(Long id) {
        aventureiroFakeRepository.reativarAventureiro(id);
    }

    public void invocarCompanheiro(Long idAventureiro, String nomeCompanheiro, CompanheiroEspecies especie, int lealdade) {
        Aventureiro aventureiro = aventureiroFakeRepository.buscarPorId(idAventureiro);
        if (!aventureiro.isAtivo() || aventureiro.getId() == null) {
            throw new EntityNotFoundException("O aventureiro não encontrado.");
        }
        Companheiro novoCompanheiro = new Companheiro(nomeCompanheiro, especie, lealdade);
        aventureiro.setCompanheiro((novoCompanheiro));
    }

    public void banirCompanheiro(Long idAventureiro) {
        Aventureiro aventureiro = aventureiroFakeRepository.buscarPorId(idAventureiro);
        if (!aventureiro.isAtivo() || aventureiro.getId() == null) {
            throw new EntityNotFoundException("O aventureiro não encontrado.");
        }
        aventureiro.setCompanheiro(null);
    }

    private List<Aventureiro> paginar(List<Aventureiro> aventureiros, int page, int size) {
        int total = aventureiros.size();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, total);

        if (fromIndex >= total) {
            return List.of();
        }
        return aventureiros.subList(fromIndex, toIndex);
    }

    private AventureiroResumoResponse convertToResumoResponse(Aventureiro aventureiro) {
        return new AventureiroResumoResponse(
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel());
    }

}
