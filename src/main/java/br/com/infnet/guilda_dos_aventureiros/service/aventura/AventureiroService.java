package br.com.infnet.guilda_dos_aventureiros.service.aventura;

import br.com.infnet.guilda_dos_aventureiros.dto.*;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.*;
import br.com.infnet.guilda_dos_aventureiros.entities.audit.Organization;
import br.com.infnet.guilda_dos_aventureiros.entities.audit.User;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Aventureiro;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Companheiro;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.ParticipacaoMissao;
import br.com.infnet.guilda_dos_aventureiros.exceptions.BusinessRuleException;
import br.com.infnet.guilda_dos_aventureiros.exceptions.EntityNotFoundException;
import br.com.infnet.guilda_dos_aventureiros.mapper.AventureiroMapper;
import br.com.infnet.guilda_dos_aventureiros.repositories.audit.OrganizationRepository;
import br.com.infnet.guilda_dos_aventureiros.repositories.audit.UserRepository;
import br.com.infnet.guilda_dos_aventureiros.repositories.aventura.AventureiroRepository;
import br.com.infnet.guilda_dos_aventureiros.repositories.aventura.ParticipacaoMissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AventureiroService {
    private final AventureiroRepository aventureiroRepository;
    private final AventureiroMapper aventureiroMapper;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    @Transactional
    public AventureiroResponse criarAventureiro(AventureiroCriacaoRequest request) {
        User usuario = userRepository.findById(request.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + request.usuarioId() + " não encontrado."));

        Organization organizacao = organizationRepository.findById(request.organizacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Organização com ID " + request.organizacaoId() + " não encontrada."));
        Aventureiro aventureiro = aventureiroMapper.toEntity(request);
        aventureiro.setUsuario(usuario);
        aventureiro.setOrganizacao(organizacao);
        Aventureiro aventureiroSalvo = aventureiroRepository.save(aventureiro);
        return aventureiroMapper.toResponse(aventureiroSalvo);
    }

    @Transactional(readOnly = true)
    public List<AventureiroResponse> findAllSortedById() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return aventureiroRepository.findAll(sort).stream()
                .map(aventureiroMapper::toResponse)
                .toList();
    }

    public PagedResponse<AventureiroResumoResponse> listarComFiltros(AventureiroFiltroRequest filtro, Pageable pageable) {
        Page<AventureiroResumoResponse> responsePage = aventureiroRepository.findByStatusAndClasseAndNivelMinimo(
                filtro.status(),
                filtro.classe(),
                filtro.nivelMinimo(),
                pageable
        );
        return new PagedResponse<>(
                responsePage.getNumber(),
                responsePage.getSize(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                responsePage.getContent()
        );
    }

    @Transactional(readOnly = true)
    public PagedResponse<AventureiroResumoResponse> buscarPorNome(String nome, Pageable pageable) {
        Page<Aventureiro> aventureirosPage = aventureiroRepository.findByNomeContainingIgnoreCase(nome, pageable);

        List<AventureiroResumoResponse> conteudo = aventureirosPage.getContent().stream()
                .map(aventureiroMapper::toResumoResponse)
                .toList();

        return new PagedResponse<>(
                aventureirosPage.getNumber(),
                aventureirosPage.getSize(),
                aventureirosPage.getTotalElements(),
                aventureirosPage.getTotalPages(),
                conteudo
        );
    }

    @Transactional(readOnly = true)
    public AventureiroProfileResponse buscarDetalhesAventureiro(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro com ID " + id + " não encontrado."));

        long totalMissoes = participacaoMissaoRepository.countByAventureiroId(id);

        Optional<ParticipacaoMissao> ultimaParticipacaoOpt = participacaoMissaoRepository.findFirstByAventureiroIdOrderByDataDeRegistroDesc(id);
        ParticipacaoResumoResponse ultimaMissaoDto = ultimaParticipacaoOpt
                .map(aventureiroMapper::toResumoResponse)
                .orElse(null);

        CompanheiroResponse companheiroDto = (aventureiro.getCompanheiro() != null)
                ? new CompanheiroResponse(
                        aventureiro.getCompanheiro().getId(),
                        aventureiro.getCompanheiro().getNome(),
                        aventureiro.getCompanheiro().getEspecie(),
                        aventureiro.getCompanheiro().getLealdade()
                ) : null;

        return new AventureiroProfileResponse(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo(),
                companheiroDto,
                totalMissoes,
                ultimaMissaoDto
        );
    }


    @Transactional(readOnly = true)
    public AventureiroResponse buscarPorId(Long id) {
        return aventureiroRepository.findById(id)
                .map(aventureiroMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro com ID " + id + " não encontrado."));
    }

    @Transactional
    public AventureiroResponse atualizarAventureiro(Long id, AventureiroAtualizacaoRequest request) {
        Aventureiro aventureiroExistente = findAventureiroById(id);

        aventureiroExistente.setNome(request.nome());
        aventureiroExistente.setClasse(request.classe());
        aventureiroExistente.setNivel(request.nivel());

        Aventureiro aventureiroAtualizado = aventureiroRepository.save(aventureiroExistente);
        return aventureiroMapper.toResponse(aventureiroAtualizado);
    }

    @Transactional
    public void encerrarVinculo(Long id) {
        Aventureiro aventureiro = findAventureiroById(id);
        aventureiro.setAtivo(false);
        aventureiroRepository.save(aventureiro);
    }

    @Transactional
    public void recrutarAventureiro(Long id) {
        Aventureiro aventureiro = findAventureiroById(id);
        aventureiro.setAtivo(true);
        aventureiroRepository.save(aventureiro);
    }

    @Transactional
    public AventureiroResponse invocarCompanheiro(Long idAventureiro, CompanheiroCriacaoRequest request) {
        Aventureiro aventureiro = findAventureiroById(idAventureiro);
        if (!aventureiro.isAtivo()) {
            throw new BusinessRuleException("Não é possível invocar um companheiro para um aventureiro inativo.");
        }
        if (aventureiro.getCompanheiro() != null) {
            throw new BusinessRuleException("Aventureiro já possui um companheiro.");
        }

        Companheiro novoCompanheiro = new Companheiro();
        novoCompanheiro.setNome(request.nome());
        novoCompanheiro.setEspecie(request.especie());
        novoCompanheiro.setLealdade(request.lealdade());
        novoCompanheiro.setAventureiro(aventureiro);
        aventureiro.setCompanheiro(novoCompanheiro);
        Aventureiro aventureiroSalvo = aventureiroRepository.save(aventureiro);
        return aventureiroMapper.toResponse(aventureiroSalvo);
    }

    @Transactional
    public void banirCompanheiro(Long idAventureiro) {
        Aventureiro aventureiro = findAventureiroById(idAventureiro);
        aventureiro.setCompanheiro(null);
        aventureiroRepository.save(aventureiro);
    }

    private Aventureiro findAventureiroById(Long id) {
        return aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro com ID " + id + " não encontrado."));
    }
}
