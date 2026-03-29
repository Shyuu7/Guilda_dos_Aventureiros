package br.com.infnet.guilda_dos_aventureiros.mapper;

import br.com.infnet.guilda_dos_aventureiros.dto.aventura.MissaoCriacaoRequest;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.MissaoResponse;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.ParticipacaoRequest;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.ParticipacaoResponse;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Missao;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.ParticipacaoMissao;
import br.com.infnet.guilda_dos_aventureiros.entities.audit.Organization;

import java.util.Collections;
import java.util.stream.Collectors;

public class MissaoMapper {

    public static Missao toEntity(MissaoCriacaoRequest dto) {
        if (dto == null) {
            return null;
        }

        Missao missao = new Missao();
        missao.setTitulo(dto.titulo());
        missao.setNivelPerigo(dto.nivelPerigo());
        missao.setStatus(dto.status());
        missao.setDataInicio(dto.dataInicio());
        missao.setDataTermino(dto.dataTermino());

        if (dto.organizacaoId() != null) {
            Organization org = new Organization();
            org.setId(dto.organizacaoId());
            missao.setOrganizacao(org);
        }

        return missao;
    }

    public static ParticipacaoMissao toEntity(ParticipacaoRequest dto) {
        if (dto == null) {
            return null;
        }

        ParticipacaoMissao participacao = new ParticipacaoMissao();
        participacao.setPapelMissao(dto.papelMissao());
        participacao.setRecompensaEmOuro(dto.recompensaEmOuro());
        participacao.setDestaque(dto.destaque());

        return participacao;
    }

    public static MissaoResponse toResponse(Missao entity) {
        if (entity == null) {
            return null;
        }

        var participacoes = entity.getParticipacoes() != null
                ? entity.getParticipacoes().stream().map(MissaoMapper::toResponse).collect(Collectors.toList())
                : Collections.<ParticipacaoResponse>emptyList();

        return new MissaoResponse(
                entity.getId(),
                entity.getTitulo(),
                entity.getNivelPerigo(),
                entity.getStatus(),
                entity.getDataInicio(),
                entity.getDataTermino(),
                entity.getOrganizacao() != null ? entity.getOrganizacao().getId() : null,
                participacoes
        );
    }

    public static ParticipacaoResponse toResponse(ParticipacaoMissao entity) {
        if (entity == null) {
            return null;
        }

        return new ParticipacaoResponse(
                entity.getAventureiro() != null ? entity.getAventureiro().getId() : null,
                entity.getAventureiro() != null ? entity.getAventureiro().getNome() : null,
                entity.getPapelMissao(),
                entity.getRecompensaEmOuro(),
                entity.isDestaque(),
                entity.getDataDeRegistro()
        );
    }
}
