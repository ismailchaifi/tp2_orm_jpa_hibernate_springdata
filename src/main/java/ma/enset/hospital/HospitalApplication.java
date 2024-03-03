package ma.enset.hospital;

import ma.enset.hospital.entities.Patient;
import ma.enset.hospital.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class HospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

    CommandLineRunner start(PatientRepository patientRepository) {
        return args -> {

            patientRepository.save(Patient.builder().nom("Patient 1").dateNaissance(new Date(100, 9, 21)).malade(false).score(9).build());
            patientRepository.save(Patient.builder().nom("Patient 2").dateNaissance(new Date(99, 1, 13)).malade(false).score(8).build());
            patientRepository.save(Patient.builder().nom("Patient 3").dateNaissance(new Date(102, 3, 20)).malade(true).score(21).build());
            List<Patient> patients = patientRepository.findAll();
            patients.forEach(System.out::println);
            Patient patient = patientRepository.findById(1L).orElse(null);
            System.out.println("***************");
            if (patient != null){
                System.out.println("Patient trouvé:");
                System.out.println(patient);
            } else {
                System.out.println("Patient n'existe pas.");
            }
            System.out.println("***************");
            patientRepository.save(Patient.builder().id(1L).nom("Ahmed").score(17).dateNaissance(new Date(100, 9, 21)).build());
            patientRepository.deletePatientById(1L);
        };
    }
}
