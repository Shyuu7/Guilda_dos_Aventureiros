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

    public static MissaoResponse toResponse(Missao missao) {
        if (missao == null) {
            return null;
        }

        MissaoResponse dto = new MissaoResponse();
        dto.setId(missao.getId());
        dto.setTitulo(missao.getTitulo());
        dto.setNivelPerigo(missao.getNivelPerigo());
        dto.setStatus(missao.getStatus());
        dto.setDataInicio(missao.getDataInicio());
        dto.setDataTermino(missao.getDataTermino());

        if (missao.getOrganizacao() != null) {
            dto.setOrganizacaoId(missao.getOrganizacao().getId());
        }

        if (missao.getParticipacoes() != null && !missao.getParticipacoes().isEmpty()) {
            dto.setParticipacoes(
                    missao.getParticipacoes().stream()
                            .map(MissaoMapper::toResponse)
                            .collect(Collectors.toList())
            );
        } else {
            dto.setParticipacoes(Collections.emptyList());
        }
        return dto;
    }

    public static ParticipacaoResponse toResponse(ParticipacaoMissao participacaoMissao) {
        if (participacaoMissao == null) {
            return null;
        }

        ParticipacaoResponse dto = new ParticipacaoResponse();
        if (participacaoMissao.getAventureiro() != null) {
            dto.setAventureiroId(participacaoMissao.getAventureiro().getId());
            dto.setNomeAventureiro(participacaoMissao.getAventureiro().getNome());
        }

        dto.setPapelMissao(participacaoMissao.getPapelMissao());
        dto.setRecompensaEmOuro(participacaoMissao.getRecompensaEmOuro());
        dto.setDestaque(participacaoMissao.isDestaque());
        dto.setDataDeRegistro(participacaoMissao.getDataDeRegistro());
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
