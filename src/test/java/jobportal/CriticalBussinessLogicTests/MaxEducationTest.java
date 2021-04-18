package jobportal.CriticalBussinessLogicTests;

import jobportal.JobPortalApplication;
import jobportal.dao.EduGeneralFieldRepository;
import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.cv_support.EduLog;
import jobportal.models.internal_models.cv_support.MaxEduLvl;
import jobportal.models.internal_models.cv_support.MaxEducation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JobPortalApplication.class)
@WebAppConfiguration
public class MaxEducationTest {
    @Autowired
    EduLevelRepository eduLevelRepository;
    @Autowired
    EduGeneralFieldRepository eduGeneralFieldRepository;

    /**
     * Test of MaxEducation education text content area start index finding accuracy on sample CV text content
     * Tests if found and set start education description area index was set properly as expected
     */
    @Test
    public void testFindEduSectionStartIndexMethodAccuracy() {
        MaxEducation maxEducation = new MaxEducation(eduGeneralFieldRepository);

        MaxEduLvl maxEduLvl = new MaxEduLvl(eduLevelRepository);
        maxEduLvl.setEduLevel("Vysokoskolske_magisterske");

        maxEducation.setMaxEduLvl(maxEduLvl);
        assertNull(maxEducation.getEduGeneralField());

        EduLog eduLog = new EduLog();

        assertTrue(maxEducation.findEduSectionStartIndex(returnSampleTestingExtractedTextFromCV(), eduLog));
        assertEquals(maxEducation.getEduSectionStartIndex(), 185);
    }

    /**
     * Test of MaxEducation object max education general field finding accuracy on sample CV text content
     * Tests if found and set education general field was found as expected
     */
    @Test
    public void testFindFieldForVSLevelMethodAccuracy() {
        MaxEducation maxEducation = new MaxEducation(eduGeneralFieldRepository);

        MaxEduLvl maxEduLvl = new MaxEduLvl(eduLevelRepository);
        maxEduLvl.setEduLevel("Vysokoskolske_magisterske");

        maxEducation.setMaxEduLvl(maxEduLvl);
        assertNull(maxEducation.getEduGeneralField());

        EduLog eduLog = new EduLog();
        assertTrue(eduLog.getLogText().isEmpty());

        maxEducation.findEduSectionStartIndex(returnSampleTestingExtractedTextFromCV(), eduLog);

        assertTrue(maxEducation.findFieldForVSLevel(returnSampleTestingExtractedTextFromCV(),
                false, true, eduLog));
        assertFalse(eduLog.getLogText().isEmpty());
        assertEquals(maxEducation.getEduGeneralField().getName(), "Informatika_a_management");
    }

    /**
     * Method returns new CVProfile instance with testing data included -  for testing
     * @return CVProfile instance
     */
    public String returnSampleTestingExtractedTextFromCV(){
        return "ŽIVOTOPIS\n" +
                "\n" +
                "Osobní údaje\n" +
                "Jméno: Jaroslav JežekDatum narození: 15. 10. 1996Adresa: Lhotská 97, 541 02 Trutnov, Česká republika Tel č.: +420 606 121 121E-mail: jezekjar@seznam.cz\n" +
                "\n" +
                "Vzdělání\n" +
                "09/2019 – nyní probíhá\tUniverzita Hradec Králové, UHKFakulta: Fakulta informatiky a managementu, FIMStudijní obor: Aplikovaná informatikaForma studia: Prezenční, navazující magisterské, 1. ročník\n" +
                "\n" +
                "09/2016 – 05/2019\tUniverzita Hradec Králové, UHKFakulta: Fakulta informatiky a managementu, FIMStudijní obor: Aplikovaná informatikaForma studia: Prezenční, bakalářské 3 leté Zakončené státní závěrečnou zkouškou, získaný titul: Bc.\n" +
                "\n" +
                "09/2012 – 06/2016\tStřední průmyslová škola, Trutnov, Školní 101Obor vzdělání: Elektronické počítačové systémy.Zakončeno maturitní zkouškou.\n" +
                "\n" +
                "Doplňující vzdělání\n" +
                "06/2015\tCisco Networking Academy ® Certifikát získaný v rámci studia na SPŠ TrutnovCertificate of Course Completion: CCNA Routing and Switching: Introduction to Networks\n" +
                "\n" +
                "Pracovní zkušenosti a praxe\n" +
                "11. 6. 2017 – 30. 8. 2017\tSiemens, s.r.o., oz. Trutnov  a 1. 6. 2016 – 30. 8. 2016Brigáda DPP ve firmě SiemensNáplň práce: Pracovník ve výrobě, obsluha strojů\n" +
                "25. 5. 2015 – 5. 6. 2015\tSiemens, s.r.o., oz. Trutnov Výkon odborné stáže v rámci studia SPŠ Trutnov.Náplň práce: Zapojování a pájení vodičů podle schématu, označování vodičů v rozvaděči, pomoc na oddělení elektroúdržby.\n" +
                "4. 7. 2014 – 30. 9. 2014\tSiemens, s.r.o., oz. Trutnov Brigáda DPP ve firmě SiemensNáplň práce: Pracovník ve výrobě, obsluha strojů\n" +
                "12. 5. 2014 – 23. 5. 2014\tSiemens, s.r.o., oz. Trutnov Výkon odborné stáže v rámci studia SPŠ Trutnov.Náplň práce: Překreslování el. schémat v počítačovém programu, pomoc na oddělení elektroúdržby.\n" +
                "\n" +
                "Jazykové znalosti\n" +
                "Anglický jazyk – střední úroveňČeský jazyk – rodilý mluvčí \n" +
                "\n" +
                "Počítačové znalosti\n" +
                "PHP, HTML, CSS, SQL, Bootstrap 4 framework – střední až pokročilýC#, JavaScript, Java – střední úroveň ASP .NET MVC, Java Spring Web MVC – střední úroveňWordpress – základní znalostPočítačové sítě – certifikát Cisco CCNA1, CCNA2Photoshop, Corel, Gimp – základní znalostKancelářské programy (Excell, Word, Access) – střední až pokročilýOS Windows 7/8/8.1/10 – pokročilý uživatelZkušenost se zpracováním: JSON, XML, napojení API, parsing dat.\n" +
                "\n" +
                "Řidičský průkaz\n" +
                "Skupina B – aktivní řidič (20 000 km/rok)\n" +
                "\n" +
                "Zájmy\n" +
                "Rád cestuji a poznávám krásy přírody. Sportuji, jezdím na horském i silničním kole a běhám půlmaratony. Zajímám se o nové technologie v IT, baví mě tvorba webů, databáze a programování. Rád bych se v této oblasti dále zdokonaloval a nakonec si v ní našel i zajímavou práci.\n" +
                "\n" +
                "\n" +
                "\n" +
                "V Trutnově dne 3. 2. 2020\tJaroslav Ježek";
    }
}
