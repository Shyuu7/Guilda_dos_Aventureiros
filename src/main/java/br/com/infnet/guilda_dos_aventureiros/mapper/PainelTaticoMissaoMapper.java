package br.com.infnet.guilda_dos_aventureiros.mapper;

import br.com.infnet.guilda_dos_aventureiros.dto.operacoes.PainelTaticoMissaoDTO;
import br.com.infnet.guilda_dos_aventureiros.entities.operacoes.PainelTaticoMissao;
import org.springframework.stereotype.Component;

@Component
public class PainelTaticoMissaoMapper {
    public PainelTaticoMissaoDTO toDto(PainelTaticoMissao entity) {
        if (entity == null) {
            return null;
        }

        PainelTaticoMissaoDTO dto = new PainelTaticoMissaoDTO();
        dto.setMissaoId(entity.getMissaoId());
        dto.setTitulo(entity.getTitulo());
        dto.setStatus(entity.getStatus());
        dto.setNivelPerigo(entity.getNivelPerigo());
        dto.setOrganizacaoId(entity.getOrganizacaoId());
        dto.setTotalParticipantes(entity.getTotalParticipantes());
        dto.setNivelMedioEquipe(entity.getNivelMedioEquipe());
        dto.setTotalRecompensa(entity.getTotalRecompensa());
        dto.setTotalMvps(entity.getTotalMvps());
        dto.setParticipantesComCompanheiro(entity.getParticipantesComCompanheiro());
        dto.setUltimaAtualizacao(entity.getUltimaAtualizacao());
        dto.setIndiceProntidao(entity.getIndiceProntidao());
        return dto;
    }
}
