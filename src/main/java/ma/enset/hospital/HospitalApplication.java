package ma.enset.hospital;

import ma.enset.hospital.entities.*;
import ma.enset.hospital.repositories.MedecinRepository;
import ma.enset.hospital.repositories.PatientRepository;
import ma.enset.hospital.repositories.RendezVousRepository;
import ma.enset.hospital.service.HospitalServiceImpl;
import ma.enset.hospital.service.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

    @Bean
    CommandLineRunner start(HospitalServiceImpl service,
                            IUserService userService,
                            PatientRepository patientRepository,
                            MedecinRepository medecinRepository,
                            RendezVousRepository rendezVousRepository) {
        return args -> {
            service.savePatient(Patient.builder().nom("Patient 1").dateNaissance(new Date(100, 9, 21)).malade(false).score(9).build());
            service.savePatient(Patient.builder().nom("Patient 2").dateNaissance(new Date(99, 1, 13)).malade(false).score(8).build());
            service.savePatient(Patient.builder().nom("Patient 3").dateNaissance(new Date(102, 3, 20)).malade(true).score(21).build());

            /*
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
            */

            Stream.of("Medecin 1", "Medecin 2", "Medecin 3")
                    .forEach(name -> {
                        Medecin medecin = Medecin.builder()
                                .nom(name)
                                .email(name+"@gmail.com")
                                .specialite(Math.random()>0.5?"Cardio":"Dentiste")
                                .build();
                        service.saveMedecin(medecin);
                    });

            Patient patient1 = patientRepository.findAll().get(0);
            Medecin medecin1 = medecinRepository.findByNom("Medecin 2");

            RendezVous rdv = RendezVous.builder()
                    .date(new Date())
                    .status(StatusRDV.PENDING)
                    .patient(patient1)
                    .medecin(medecin1)
                    .build();
            service.saveRDV(rdv);

            RendezVous rdv1 = rendezVousRepository.findAll().get(0);

            Consultation consultation = Consultation.builder()
                    .dateConsultation(new Date())
                    .rapport("Rapport de la consultation:...")
                    .rendezVous(rdv1)
                    .build();
            service.saveConsultation(consultation);

            User u1 = new User();
            u1.setUsername("user");
            u1.setPassword("123456");
            userService.addNewUser(u1);

            User u2 = new User();
            u2.setUsername("admin");
            u2.setPassword("admin123");
            userService.addNewUser(u2);

            Stream.of("STUDENT","USER","ADMIN").forEach(name -> {
                Role r1 = new Role();
                r1.setRoleName(name);
                userService.addNewRole(r1);
            });

            userService.addRoleToUser("user", "USER");
            userService.addRoleToUser("user", "STUDENT");
            userService.addRoleToUser("admin", "USER");
            userService.addRoleToUser("admin", "ADMIN");

            try {
                User user = userService.authenticate("user", "123456");
                System.out.println(user.getUserID());
                System.out.println(user.getUsername());
                System.out.println("Roles: ");
                for (Role role : user.getRoles()) {
                    System.out.println(role.toString());
                }

                /*User badUser = userService.authenticate("user", "12");
                System.out.println(badUser.getUserID());
                System.out.println(badUser.getUsername());
                System.out.println("Roles: ");
                for (Role role : badUser.getRoles()) {
                    System.out.println(role.toString());
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
    }
}
