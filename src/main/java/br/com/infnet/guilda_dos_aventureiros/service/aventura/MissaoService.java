package br.com.infnet.guilda_dos_aventureiros.service.aventura;

import br.com.infnet.guilda_dos_aventureiros.dto.PagedResponse;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.*;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Aventureiro;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Missao;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.ParticipacaoMissao;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.ParticipacaoMissaoId;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import br.com.infnet.guilda_dos_aventureiros.exceptions.BusinessRuleException;
import br.com.infnet.guilda_dos_aventureiros.mapper.aventura.MissaoMapper;
import br.com.infnet.guilda_dos_aventureiros.repositories.aventura.AventureiroRepository;
import br.com.infnet.guilda_dos_aventureiros.repositories.aventura.MissaoRepository;
import br.com.infnet.guilda_dos_aventureiros.repositories.aventura.ParticipacaoMissaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MissaoService {

    private final MissaoRepository missaoRepository;
    private final AventureiroRepository aventureiroRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    public MissaoResponse criarMissao(MissaoCriacaoRequest request) {
        Missao missao = MissaoMapper.toEntity(request);
        Missao novaMissao = missaoRepository.save(missao);
        return MissaoMapper.toResponse(novaMissao);
    }

    public PagedResponse<MissaoResumoResponse> listar(MissaoFiltroRequest filtro, Pageable pageable) {
        Page<MissaoResumoResponse> responsePage = missaoRepository.findMissoesByFilters(
                filtro.status(),
                filtro.nivelPerigo(),
                filtro.tipoData() != null ? filtro.tipoData().name() : null,
                filtro.dataMin(),
                filtro.dataMax(),
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

    public MissaoResponse obterMissaoPorId(Long id) {
        Missao missao = missaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada com o ID: " + id));
        return MissaoMapper.toResponse(missao);
    }

    public MissaoResponse atualizarMissao(Long id, MissaoCriacaoRequest request) {
        Missao missaoExistente = missaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada com o ID: " + id));

        missaoExistente.setTitulo(request.titulo());
        missaoExistente.setNivelPerigo(request.nivelPerigo());
        missaoExistente.setStatus(request.status());
        missaoExistente.setDataInicio(request.dataInicio());
        missaoExistente.setDataTermino(request.dataTermino());

        Missao missaoAtualizada = missaoRepository.save(missaoExistente);
        return MissaoMapper.toResponse(missaoAtualizada);
    }

    public void removerMissao(Long id) {
        if (!missaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Missão não encontrada com o ID: " + id);
        }
        missaoRepository.deleteById(id);
    }

    //-- GERENCIAMENTO DE PARTICIPAÇÕES EM MISSÕES --
    public ParticipacaoResponse adicionarAventureiroNaMissao(Long idMissao, Long idAventureiro, ParticipacaoRequest request) {
        Missao missao = missaoRepository.findById(idMissao)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada com o ID: " + idMissao));

        Aventureiro aventureiro = aventureiroRepository.findById(idAventureiro)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado com o ID: " + idAventureiro));

        if (!aventureiro.isAtivo()) {
            throw new BusinessRuleException("Aventureiros inativos não podem participar de missões.");
        }

        if (!missao.getOrganizacao().getId().equals(aventureiro.getOrganizacao().getId())) {
            throw new BusinessRuleException("O aventureiro não pertence à organização desta missão.");
        }

        if (missao.getStatus() != StatusMissao.PLANEJADA && missao.getStatus() != StatusMissao.EM_ANDAMENTO) {
            throw new BusinessRuleException("Esta missão não está aceitando novos participantes no momento.");
        }

        if (participacaoMissaoRepository.existsById(new ParticipacaoMissaoId(idMissao, idAventureiro))) {
            throw new BusinessRuleException("Este aventureiro já participou desta missão.");
        }

        ParticipacaoMissao participacao = MissaoMapper.toEntity(request);
        participacao.setMissao(missao);
        participacao.setAventureiro(aventureiro);

        ParticipacaoMissao novaParticipacao = participacaoMissaoRepository.save(participacao);
        return MissaoMapper.toResponse(novaParticipacao);
    }

    public void removerAventureiroDaMissao(Long idMissao, Long idAventureiro) {
        ParticipacaoMissaoId participacaoId = new ParticipacaoMissaoId(idMissao, idAventureiro);
        if (!participacaoMissaoRepository.existsById(participacaoId)) {
            throw new EntityNotFoundException("Participação não encontrada para a missão " + idMissao + " e aventureiro " + idAventureiro);
        }
        participacaoMissaoRepository.deleteById(participacaoId);
    }

    public ParticipacaoResponse atualizarParticipacaoNaMissao(Long idMissao, Long idAventureiro, ParticipacaoRequest request) {
        ParticipacaoMissaoId participacaoId = new ParticipacaoMissaoId(idMissao, idAventureiro);
        ParticipacaoMissao participacaoExistente = participacaoMissaoRepository.findById(participacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Participação não encontrada para a missão " + idMissao + " e aventureiro " + idAventureiro));

        participacaoExistente.setPapelMissao(request.papelMissao());
        participacaoExistente.setRecompensaEmOuro(request.recompensaEmOuro());
        participacaoExistente.setDestaque(request.destaque());

        ParticipacaoMissao participacaoAtualizada = participacaoMissaoRepository.save(participacaoExistente);
        return MissaoMapper.toResponse(participacaoAtualizada);
    }
}
