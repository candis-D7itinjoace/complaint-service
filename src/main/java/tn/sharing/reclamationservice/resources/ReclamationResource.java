package tn.sharing.reclamationservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tn.sharing.reclamationservice.dto.ReclamationModel;
import tn.sharing.reclamationservice.entity.Reclamation;
import tn.sharing.reclamationservice.services.ReclamationService;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/reclamation")
public class ReclamationResource {
        @Autowired
        private WebClient webClient;


        @Autowired
        private ReclamationService reclamationService;

        private String sayHello(){
                return "Hello Bayrem";
        }

        @PostMapping("/add")
        private String addReclamation(@RequestBody @Valid ReclamationModel reclamationModel){
                Reclamation reclamation = reclamationService.addReclamation(reclamationModel, reclamationModel.getUserId(), reclamationModel.getEnterpriseId());
                return reclamation != null ? "success" : "error";
        }

        @DeleteMapping("/delete/{reclamationId}")
        private String deleteReclamation(@PathVariable("reclamationId") Long reclamationId) throws Exception {
                try{
                        return reclamationService.removeReclamation(reclamationId);
                }catch (Exception e) {
                        throw new Exception("error from request or server", e);
                }


        }

        @GetMapping("/{employeeId}/all")
        private Page<Reclamation> getAllReclamationsForUser(@PathVariable("employeeId") Long employeeId, @RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) throws Exception {


                Page<Reclamation> allReclamationsForUser = reclamationService.getAllReclamationsForUser(employeeId, pageSize, pageNumber);

                if(!Objects.equals(allReclamationsForUser, Page.empty())){
                        return allReclamationsForUser;
                }else {
                        throw new Exception("bad request");
                }

        }

        @GetMapping("/all/{enterpriseId}")
        private Page<Reclamation> getAllReclamationsForEnterprise(@PathVariable("enterpriseId") Long enterpriseId, @RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) throws Exception {
                Page<Reclamation> allReclamationForEnterprise = reclamationService.getAllReclamationsForEnterprise(enterpriseId, pageSize, pageNumber);

                if(!Objects.equals(allReclamationForEnterprise, Page.empty())){
                        return allReclamationForEnterprise;
                }else {
                        throw new Exception("bad request!!");
                }
        }

        @GetMapping("/userExists/{userId}")
        private Boolean userExists(@PathVariable("userId") Long userId) {
                return Boolean.TRUE.equals(webClient.get().uri("http://domain-header-service/auth/userExists/" + userId)
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .block());
        }


}
