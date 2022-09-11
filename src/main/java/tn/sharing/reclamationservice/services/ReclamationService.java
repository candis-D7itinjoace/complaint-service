package tn.sharing.reclamationservice.services;

import org.springframework.data.domain.Page;
import tn.sharing.reclamationservice.dto.ReclamationModel;
import tn.sharing.reclamationservice.entity.Reclamation;

public interface ReclamationService {
    Reclamation addReclamation(ReclamationModel reclamationModel, Long userId, Long enterpriseId);

    String removeReclamation(Long reclamationId);

    Page<Reclamation> getAllReclamationsForUser(Long userId, int pageSize, int pageNumber);

    Page<Reclamation> getAllReclamationsForEnterprise(Long enterpriseId, int pageSize, int pageNumber);
}
