package tn.sharing.reclamationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "reclamations")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reclamation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long employeeId = null;

    private Long staffId = null;

    private Long enterpriseId;

    private String title;

    private String body;

    private boolean treated = false;

}
