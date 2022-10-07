package tn.sharing.reclamationservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tn.sharing.reclamationservice.dto.ReclamationModel;
import tn.sharing.reclamationservice.entity.Reclamation;
import tn.sharing.reclamationservice.repositories.ReclamationRepo;

@Service
public class ReclamationServiceImpl implements ReclamationService{

    @Autowired
    private WebClient webClient;

    @Autowired
    private ReclamationRepo reclamationRepo;
    @Override
    public Reclamation addReclamation(ReclamationModel reclamationModel, Long userId, Long enterpriseId) {

        Reclamation reclamation = new Reclamation();
        reclamation.setTitle(reclamationModel.getTitle());
        reclamation.setBody(reclamationModel.getBody());
        reclamation.setEmployeeId(userId);
        reclamation.setEnterpriseId(enterpriseId);

        return reclamationRepo.save(reclamation);
    }

    @Override
    public String removeReclamation(Long reclamationId) {
        if(reclamationRepo.findById(reclamationId).isPresent()){
            reclamationRepo.deleteById(reclamationId);
            return "reclamation has been deleted successfully";
        }
        return "try again there has been some error";
    }

    @Override
    public Page<Reclamation> getAllReclamationsForUser(Long employeeId, int pageSize, int pageNumber) {
        boolean exists = Boolean.TRUE.equals(webClient.get().uri("http://domain-header-service/auth/userExists/" + employeeId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
        if (exists) {
            Pageable page = PageRequest.of(pageNumber, pageSize);
            if (reclamationRepo.findFirstByEmployeeId(employeeId).isPresent()){
                return reclamationRepo.findByEmployeeId(employeeId, page);
            }
            return Page.empty() ;
        }
        return Page.empty();

    }

    @Override
    public Page<Reclamation> getAllReclamationsForEnterprise(Long enterpriseId, int pageSize, int pageNumber) {
        boolean exists = Boolean.TRUE.equals(webClient.get().uri("http://domain-header-service/auth/enterpriseExists/" + enterpriseId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
        if(exists) {
            Pageable page = PageRequest.of(pageNumber, pageSize);
            if(reclamationRepo.findFirstByEnterpriseId(enterpriseId).isPresent()){
                return reclamationRepo.findByEnterpriseId(enterpriseId, page);
            }
            return Page.empty();
        }
        return Page.empty();
    }
}
