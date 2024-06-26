package ma.enset.hospital.repositories;

import ma.enset.hospital.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, String> {
    Medecin findByNom(String nom);
}
