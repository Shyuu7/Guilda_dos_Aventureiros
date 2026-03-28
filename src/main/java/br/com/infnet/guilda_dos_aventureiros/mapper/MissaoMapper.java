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
        missao.setTitulo(dto.getTitulo());
        missao.setNivelPerigo(dto.getNivelPerigo());
        missao.setStatus(dto.getStatus());
        missao.setDataInicio(dto.getDataInicio());
        missao.setDataTermino(dto.getDataTermino());

        if (dto.getOrganizacaoId() != null) {
            Organization org = new Organization();
            org.setId(dto.getOrganizacaoId());
            missao.setOrganizacao(org);
        }

        return missao;
    }

    public static MissaoResponse toResponse(Missao entity) {
        if (entity == null) {
            return null;
        }

        MissaoResponse dto = new MissaoResponse();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setNivelPerigo(entity.getNivelPerigo());
        dto.setStatus(entity.getStatus());
        dto.setDataInicio(entity.getDataInicio());
        dto.setDataTermino(entity.getDataTermino());

        if (entity.getOrganizacao() != null) {
            dto.setOrganizacaoId(entity.getOrganizacao().getId());
        }

        if (entity.getParticipacoes() != null && !entity.getParticipacoes().isEmpty()) {
            dto.setParticipacoes(
                    entity.getParticipacoes().stream()
                            .map(MissaoMapper::toResponse)
                            .collect(Collectors.toList())
            );
        } else {
            dto.setParticipacoes(Collections.emptyList());
        }

        return dto;
    }

    public static ParticipacaoResponse toResponse(ParticipacaoMissao entity) {
        if (entity == null) {
            return null;
        }

        ParticipacaoResponse dto = new ParticipacaoResponse();
        if (entity.getAventureiro() != null) {
            dto.setAventureiroId(entity.getAventureiro().getId());
            dto.setNomeAventureiro(entity.getAventureiro().getNome());
        }
        dto.setPapelMissao(entity.getPapelMissao());
        dto.setRecompensaEmOuro(entity.getRecompensaEmOuro());
        dto.setDestaque(entity.isDestaque());
        dto.setDataDeRegistro(entity.getDataDeRegistro());

        return dto;
    }

    public static ParticipacaoMissao toEntity(ParticipacaoRequest dto) {
        if (dto == null) {
            return null;
        }

        ParticipacaoMissao entity = new ParticipacaoMissao();
        entity.setPapelMissao(dto.getPapelMissao());
        entity.setRecompensaEmOuro(dto.getRecompensaEmOuro());
        entity.setDestaque(dto.isDestaque());

        return entity;
    }
}
