package br.com.infnet.guilda_dos_aventureiros.service;

import br.com.infnet.guilda_dos_aventureiros.entities.Companheiro;
import br.com.infnet.guilda_dos_aventureiros.dto.AventureiroAtualizacaoRequest;
import br.com.infnet.guilda_dos_aventureiros.dto.AventureiroResponse;
import br.com.infnet.guilda_dos_aventureiros.dto.AventureiroResumoResponse;
import br.com.infnet.guilda_dos_aventureiros.entities.Aventureiro;
import br.com.infnet.guilda_dos_aventureiros.enums.Classes;
import br.com.infnet.guilda_dos_aventureiros.enums.Especies;
import br.com.infnet.guilda_dos_aventureiros.exceptions.EntityNotFoundException;
import br.com.infnet.guilda_dos_aventureiros.repositories.AventureiroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AventureiroService {
    private final AventureiroRepository aventureiroRepository;
    private final AtomicLong idGenerator = new AtomicLong(101);

    public AventureiroService(AventureiroRepository aventureiroRepository) {
        this.aventureiroRepository = aventureiroRepository;
        carregarAventureiros();
    }

    public void carregarAventureiros() {
        aventureiroRepository.carregarAventureiros();
    }

    public Aventureiro criarAventureiro(Aventureiro aventureiro) {
        aventureiro.setId(idGenerator.getAndIncrement());
        aventureiroRepository.salvarAventureiro(aventureiro);
        return aventureiro;
    }

    public List<AventureiroResponse> listarAventureiros() {
        List<Aventureiro> aventureiros = aventureiroRepository.findAll();
        return aventureiros.stream()
                .map(a -> new AventureiroResponse(
                        a.getId(),
                        a.getNome(),
                        a.getClasse(),
                        a.getNivel(),
                        a.isAtivo(),
                        a.getCompanheiro()
                ))
                .toList();
    }

   public List<AventureiroResumoResponse> listarComFiltros (Classes classe, Boolean ativo, Integer nivelMinimo, int page, int size) {
        List <Aventureiro> todos = aventureiroRepository.findAll();
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

   public long contarAventureirosComFiltros(Classes classe, Boolean ativo, Integer nivelMinimo) {
       List<Aventureiro> todos = aventureiroRepository.findAll();
       return todos.stream()
               .filter(a -> classe == null || a.getClasse().equals(classe))
               .filter(a -> ativo == null || a.isAtivo() == ativo)
               .filter(a -> nivelMinimo == null || a.getNivel() >= nivelMinimo)
               .count();
   }

    public AventureiroResponse buscarPorId (Long id) {
        Aventureiro aventureiro = aventureiroRepository.buscarPorId(id);
        return new AventureiroResponse(
                id,
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo(),
                aventureiro.getCompanheiro()
        );
    }

    public Aventureiro atualizarAventureiro(Long id, AventureiroAtualizacaoRequest request) {
        Aventureiro aventureiroExistente = aventureiroRepository.buscarPorId(id);
        aventureiroExistente.setNome(request.nome());
        aventureiroExistente.setClasse(request.classe());
        aventureiroExistente.setNivel(request.nivel());
        return aventureiroExistente;
    }

    public void encerrarVinculo(Long id) {
        aventureiroRepository.desativarAventureiro(id);
    }

    public void recrutarAventureiro(Long id) {
        aventureiroRepository.reativarAventureiro(id);
    }

    public void invocarCompanheiro(Long idAventureiro, String nomeCompanheiro, Especies especie, int lealdade) {
        Aventureiro aventureiro = aventureiroRepository.buscarPorId(idAventureiro);
        if (!aventureiro.isAtivo() || aventureiro.getId() == null) {
            throw new EntityNotFoundException("O aventureiro não encontrado.");
        }
        Companheiro novoCompanheiro = new Companheiro(nomeCompanheiro, especie, lealdade);
        aventureiro.setCompanheiro(Optional.of(novoCompanheiro));
    }

    public void banirCompanheiro(Long idAventureiro) {
        Aventureiro aventureiro = aventureiroRepository.buscarPorId(idAventureiro);
        if (!aventureiro.isAtivo() || aventureiro.getId() == null) {
            throw new EntityNotFoundException("O aventureiro não encontrado.");
        }
        aventureiro.setCompanheiro(Optional.empty());
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
