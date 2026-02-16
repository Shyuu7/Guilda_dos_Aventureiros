package br.com.infnet.dr1tp1.service;

import br.com.infnet.dr1tp1.dto.AventureiroDTO;
import br.com.infnet.dr1tp1.dto.AventureiroResumoDTO;
import br.com.infnet.dr1tp1.records.AventureiroRecord;
import br.com.infnet.dr1tp1.dto.CompanheiroDTO;
import br.com.infnet.dr1tp1.enums.Classes;
import br.com.infnet.dr1tp1.exception.AventureiroNotFoundException;
import br.com.infnet.dr1tp1.model.Aventureiro;
import br.com.infnet.dr1tp1.model.Companheiro;
import br.com.infnet.dr1tp1.repository.AventureiroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AventureiroService {

    private final AventureiroRepository aventureiroRepository;

    public AventureiroService(AventureiroRepository aventureiroRepository) {
        this.aventureiroRepository = aventureiroRepository;
    }

    //=====================
    // REGISTRAR AVENTUREIRO
    //=====================
    public AventureiroDTO registrarAventureiro(AventureiroRecord record) {
        Aventureiro aventureiro = new Aventureiro();
        aventureiro.setNome(record.nome());
        aventureiro.setClasse(record.classe());
        aventureiro.setNivel(record.nivel());
        aventureiro.setAtivo(true);

        Aventureiro salvo = aventureiroRepository.save(aventureiro);
        return convertToDTO(salvo);
    }

    //=====================
    // LISTAR COM FILTROS
    //=====================
    public List<AventureiroResumoDTO> listarComFiltros(Classes classe, Boolean ativo, Integer nivelMinimo, int page, int size) {
        List<Aventureiro> todos = aventureiroRepository.findAll();

        List<Aventureiro> filtrados = todos.stream()
                .filter(a -> classe == null || a.getClasse().equals(classe))
                .filter(a -> ativo == null || a.isAtivo() == ativo)
                .filter(a -> nivelMinimo == null || a.getNivel() >= nivelMinimo)
                .collect(Collectors.toList());

        return paginar(filtrados, page, size)
                .stream()
                .map(this::convertToResumoDTO)
                .collect(Collectors.toList());
    }

    //=====================
    // CONTAR COM FILTROS
    //=====================
    public long contarComFiltros(Classes classe, Boolean ativo, Integer nivelMinimo) {
        List<Aventureiro> todos = aventureiroRepository.findAll();

        return todos.stream()
                .filter(a -> classe == null || a.getClasse().equals(classe))
                .filter(a -> ativo == null || a.isAtivo() == ativo)
                .filter(a -> nivelMinimo == null || a.getNivel() >= nivelMinimo)
                .count();
    }

    //=====================
    // CONSULTAR POR ID
    //=====================
    public AventureiroDTO consultarPorId(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new AventureiroNotFoundException(id));
        return convertToDTO(aventureiro);
    }

    //=====================
    // ATUALIZAR AVENTUREIRO
    //=====================
    public AventureiroDTO atualizarAventureiro(Long id, AventureiroDTO dto) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new AventureiroNotFoundException(id));

        aventureiro.setNome(dto.getNome());
        aventureiro.setClasse(dto.getClasse());
        aventureiro.setNivel(dto.getNivel());

        Aventureiro atualizado = aventureiroRepository.save(aventureiro);
        return convertToDTO(atualizado);
    }

    //=====================
    // ALTERAR STATUS ATIVO
    //=====================
    public AventureiroDTO alterarStatusAtivo(Long id, boolean novoStatus) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new AventureiroNotFoundException(id));

        aventureiro.setAtivo(novoStatus);
        Aventureiro atualizado = aventureiroRepository.save(aventureiro);
        return convertToDTO(atualizado);
    }

    //=====================
    // MÉTODOS AUXILIARES
    //=====================
    private List<Aventureiro> paginar(List<Aventureiro> base, int page, int size) {
        int total = base.size();
        int from = page * size;
        int to = Math.min(from + size, total);

        if (from >= total) {
            return List.of();
        }

        return base.subList(from, to);
    }

    private AventureiroDTO convertToDTO(Aventureiro aventureiro) {
        AventureiroDTO dto = new AventureiroDTO();
        dto.setId(aventureiro.getId());
        dto.setNome(aventureiro.getNome());
        dto.setClasse(aventureiro.getClasse());
        dto.setNivel(aventureiro.getNivel());
        dto.setAtivo(aventureiro.isAtivo());

        if (aventureiro.getCompanheiro() != null && aventureiro.getCompanheiro().isPresent()) {
            dto.setCompanheiro(convertCompanheiroToDTO(aventureiro.getCompanheiro().get()));
        }

        return dto;
    }

    private AventureiroResumoDTO convertToResumoDTO(Aventureiro aventureiro) {
        return new AventureiroResumoDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo()
        );
    }

    private CompanheiroDTO convertCompanheiroToDTO(Companheiro companheiro) {
        CompanheiroDTO dto = new CompanheiroDTO();
        dto.setNome(companheiro.getNome());
        dto.setEspecie(companheiro.getEspecie());
        dto.setLealdade(companheiro.getLealdade());
        return dto;
    }
}
