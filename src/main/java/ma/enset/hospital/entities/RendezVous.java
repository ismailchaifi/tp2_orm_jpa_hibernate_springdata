package ma.enset.hospital.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class RendezVous {

    @Id
    private String id;
    private Date date;
    @Enumerated(EnumType.STRING)
    private StatusRDV status;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Medecin medecin;
    @OneToOne(mappedBy = "rendezVous")
    private Consultation consultation;
}
