package com.hosManSys;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.longThat;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hosManSys.admin.dao.AdminDao;
import com.hosManSys.admin.model.Admin;
import com.hosManSys.doctor.dao.DoctorAppointmentDao;
import com.hosManSys.doctor.dao.DoctorDao;
import com.hosManSys.doctor.model.Doctor;
import com.hosManSys.doctor.model.DoctorAppointment;
import com.hosManSys.login.dao.LoginDao;
import com.hosManSys.login.model.Login;
import com.hosManSys.patient.dao.DiagnosysDao;
import com.hosManSys.patient.dao.PatientDao;
import com.hosManSys.patient.dao.PrescriptionDao;
import com.hosManSys.patient.dao.TestDao;
import com.hosManSys.patient.dao.TreatmentDao;
import com.hosManSys.patient.model.Diagnosys;
import com.hosManSys.patient.model.Patient;
import com.hosManSys.patient.model.Prescription;
import com.hosManSys.patient.model.Treatment;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class HospitalManagementSystemApplicationTests {
    
    @Autowired private LoginDao logindao;
    @Autowired private AdminDao admindao;
    @Autowired private DoctorDao doctordao;
    @Autowired private PatientDao patientdao;
    @Autowired private DoctorAppointmentDao doctorappdao;
    @Autowired private TreatmentDao treatmentdao;
    @Autowired private DiagnosysDao diagnosysdao;
    @Autowired private PrescriptionDao prescriptiondao;
    @Autowired private TestDao testdao;
    
    private static String id = "temp001";
    
    @Test
    @Order(1)
    public void testCreateLogin() {
        Login login = new Login();
        login.setUserName("test123");
        login.setPassword("test123");
        login.setAccess("test");
        login.setActivate(true);
        Login loginnew = logindao.save(login);
        id = loginnew.getLogin_id();
        assertNotNull(logindao.findById(id).get());
    }
    
    @Test
    @Order(2)
    public void testGetLogin() {
        List<Login> loginlist = logindao.findAll();
        assertThat(loginlist).size().isGreaterThan(0);
    }
    
    @Test
    @Order(3)
    public void testGetLoginById() {
        Login login = logindao.findById(id).get();
        assertEquals("test123", login.getUserName());
    }
    
    @Test
    @Order(4)
    public void testUpdateLogin() {
        Login login = logindao.findById(id).get();
        login.setAccess("update");;
        logindao.save(login);
        assertEquals("update", logindao.findById(id).get().getAccess());
    }
    
    @Test
    @Order(5)
    public void testDeleteLogin() {
        logindao.deleteById(id);
        assertThat(logindao.existsById(id)).isFalse();
    }
    
    @Test
    @Order(6)
    public void testAdminSetProfile() {
        Admin admin = new Admin();
        admin.setAdmin_id(id);
        admin.setBirthDate("2002-02-01");
        admin.setFname("test");
        admin.setLname("test");
        admin.setAddress("test");
        admin.setGender("test");
        admin.setPhone("12312312321");
        admindao.save(admin);
        assertNotNull(admindao.findById(id).get());
    }
    
    @Test
    @Order(7)
    public void testAdminGetProfile() {
        assertThat(admindao.getById(id)).isNotNull();
    }
    
    @Test
    @Order(8)
    public void testAdminUpdatePrfile() {
        Admin admin = admindao.findById(id).get();
        admin.setFname("tset");
        admindao.save(admin);
        assertEquals("tset", admindao.findById(id).get().getFname());
    }
    
    @Test
    @Order(9)
    public void testAdminDeleteProfile() {
        admindao.deleteById(id);
        assertThat(admindao.existsById(id)).isFalse();
    }
    
    @Test
    @Order(10)
    public void testDoctorSetProfile() {
        Doctor doctor = new Doctor();
        doctor.setDoc_id(id);
        doctor.setAddress("testAddress");
        doctor.setBirthDate("2020-01-01");
        doctor.setFname("test");
        doctor.setGender("test");
        doctor.setLname("test");
        doctor.setPhone("91091092012");
        doctor.setQualification("testQual");
        doctor.setSpecialization("testSpec");
        doctordao.save(doctor);
        assertNotNull(doctordao.findById(id).get());
    }
    
    @Test
    @Order(11)
    public void testDoctorGetProfile() {
        assertThat(doctordao.getById(id)).isNotNull();
    }
    
    @Test
    @Order(12)
    public void testDoctorUpdatePrfile() {
        Doctor doctor = doctordao.findById(id).get();
        doctor.setFname("tset");
        doctordao.save(doctor);
        assertEquals("tset", doctordao.findById(id).get().getFname());
    }
    
    @Test
    @Order(13)
    public void testDoctorDeleteProfile() {
        doctordao.deleteById(id);
        assertThat(doctordao.existsById(id)).isFalse();
    }
    
    @Test
    @Order(14)
    public void testPatientSetProfile() {
        Patient patient = new Patient();
        patient.setAddress("testAddres");
        patient.setDob("2020-01-01");
        patient.setFname("test");
        patient.setGender("testGender");
        patient.setLname("test");
        patient.setPat_id(id);
        patient.setPhone("91091092012");
        patientdao.save(patient);
        assertNotNull(patientdao.findById(id).get());
    }
    
    @Test
    @Order(15)
    public void testPatientGetProfile() {
        assertThat(patientdao.getById(id)).isNotNull();
    }
    
    @Test
    @Order(16)
    public void testPatientUpdatePrfile() {
        Patient patient = patientdao.findById(id).get();
        patient.setFname("tset");
        patientdao.save(patient);
        assertEquals("tset", patientdao.findById(id).get().getFname());
    }
    
    @Test
    @Order(17)
    public void testPatientDeleteProfile() {
        patientdao.deleteById(id);
        assertThat(patientdao.existsById(id)).isFalse();
    }
    
    @Test
    @Order(18)
    public void testDocAppointmentSet() {
        DoctorAppointment docapp = new DoctorAppointment();
        docapp.setStatus("test");
        docapp.setDate(new Date(2020,12,12));
        docapp.setTotal_app(12);
        DoctorAppointment docappnew = doctorappdao.save(docapp);
        id = docappnew.getAppointmentId();
        assertNotNull(doctorappdao.findById(id).get());
    }
    
    @Test
    @Order(19)
    public void testDocAppointmentGet() {
        assertThat(doctorappdao.findById(id)).isNotNull();
    }
    
    @Test
    @Order(20)
    public void testDocAppointmentUpdate() {
        DoctorAppointment docapp = doctorappdao.findById(id).get();
        docapp.setStatus("testedOk");
        doctorappdao.save(docapp);
        assertEquals("testedOk", doctorappdao.findById(id).get().getStatus());
    }
    
    @Test
    @Order(21)
    public void testDocAppointmentDelete() {
        doctorappdao.deleteById(id);
        assertThat(doctorappdao.existsById(id)).isFalse();
    }
    
    @Test
    @Order(22)
    public void testTreatmentInsert() {
        Treatment treatment = new Treatment();
        treatment.setDate(new Date(2020,12,12));
        treatment.setSuggestion("testSuggestion");
        treatment.setTreatment_id(id);
        Treatment newtreatment = treatmentdao.save(treatment);
        id = newtreatment.getTreatment_id();
        assertThat(treatmentdao.findById(id).get()).isNotNull();
    }
    
    @Test
    @Order(23)
    public void testTreatmentGet() {
        assertThat(treatmentdao.findById(id)).isNotNull();
    }
    
    @Test
    @Order(24)
    public void testTreatmentUpdate() {
        Treatment treat = treatmentdao.findById(id).get();
        treat.setSuggestion("testUpdate");
        treatmentdao.save(treat);
        assertEquals("testUpdate", treatmentdao.findById(id).get().getSuggestion());
    }
    
    @Test
    @Order(25)
    public void testTreatmentDelete() {
        treatmentdao.deleteById(id);
        assertThat(treatmentdao.existsById(id)).isFalse();
    }
    
    @Test
    @Order(26)
    public void testDiagosysInsert() {
        Diagnosys diag = new Diagnosys();
        diag.setCause("testCause");
        diag.setDuration("testDuration");
        diag.setNote("testNote");
        diag.setSymptoms("testSymptoms");
        Diagnosys newdiag = diagnosysdao.save(diag);
        id = newdiag.getDiag_id();
        assertThat(diagnosysdao.findById(id).get()).isNotNull();
    }
    
    @Test
    @Order(27)
    public void testDiagnosysGet() {
        assertThat(diagnosysdao.findById(id).get()).isNotNull();
    }
    
    @Test
    @Order(28)
    public void testDiagnosysUpdate() {
        Diagnosys diag = diagnosysdao.findById(id).get();
        diag.setCause("testUpdate");
        diagnosysdao.save(diag);
        assertEquals("testUpdate", diagnosysdao.findById(id).get().getCause());
    }
    
    @Test
    @Order(29)
    public void testDiagnosysDelete() {
        diagnosysdao.deleteById(id);
        assertThat(diagnosysdao.existsById(id)).isFalse();
    }
    
    @Test
    @Order(30)
    public void testPrescriptionInsert() {
        Prescription pres = new Prescription();
        pres.setConsumption("testConsumption");
        pres.setMedName("testMed");
        pres.setSuggestion("testSuggestion");
        Prescription newpres = prescriptiondao.save(pres);
        id = newpres.getPresc_id();
        assertThat(prescriptiondao.findById(id).get()).isNotNull();
    }
    
    @Test
    @Order(31)
    public void testPrescriptionUpdate() {
        Prescription pres = prescriptiondao.findById(id).get();
        pres.setConsumption("testUpdate");
        prescriptiondao.save(pres);
        assertEquals("testUpdate", prescriptiondao.findById(id).get().getConsumption());
    }
    
    @Test
    @Order(32)
    public void testPrescriptionGet() {
        assertThat(prescriptiondao.findById(id)).isNotNull();
    }
    
    @Test
    @Order(33)
    public void testPrescriptionDelete() {
        prescriptiondao.deleteById(id);
        assertThat(prescriptiondao.existsById(id)).isFalse();
    }
    
    @Test
    @Order(34)
    public void testTestInsert() {
        com.hosManSys.patient.model.Test test = new com.hosManSys.patient.model.Test();
        test.setTestName("testTest");
        test.setTestRemark("testRemark");
        test.setTestResult("testResult");
        com.hosManSys.patient.model.Test newtest =  testdao.save(test);
        id = newtest.getTest_id();
        assertThat(testdao.findById(id).get()).isNotNull();
    }
    
    @Test
    @Order(35)
    public void testTestRead() {
        assertThat(testdao.findById(id).get()).isNotNull();
    }
    
    @Test
    @Order(36)
    public void testTestUpdate() {
        com.hosManSys.patient.model.Test test = testdao.findById(id).get();
        test.setTestName("testUpdate");
        testdao.save(test);
        assertEquals("testUpdate", testdao.findById(id).get().getTestName());
    }
    
    @Test
    @Order(37)
    public void testTestDelete() {
        testdao.deleteById(id);
        assertThat(testdao.existsById(id)).isFalse();
    }
    
}
