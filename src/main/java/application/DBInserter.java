package application;

import data_layer.domain.*;
import org.mindrot.jbcrypt.BCrypt;
import utils.LessonType;
import utils.RightType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBInserter {

    private static Group gr231, gr232, gr221, gr933, gr0;
    private static Professor guran, mihis, motogna, lazar, forest, grigo, camelia, suciu, ticle;
    private static Course lftc, flct, pdm, mdp, pdav, ss, retele;
    private static Student mihnea, norberth, antal, vlad, ana, beltechi, boros;
    private static EntityManager entityManager;

    static {
        lftc = new Course();
        flct = new Course();
        pdm = new Course();
        mdp = new Course();
        pdav = new Course();
        ss = new Course();
        retele = new Course();
        guran = new Professor();
        mihis = new Professor();
        motogna = new Professor();
        lazar = new Professor();
        forest = new Professor();
        grigo = new Professor();
        camelia = new Professor();
        suciu = new Professor();
        ticle = new Professor();
    }

    private static List<Enrollment> enrollmentList(Student student, Course... courses) {
        List<Enrollment> enrollments = new ArrayList<>();
        for (Course course : courses) {
            Enrollment e = new Enrollment();
            e.setCourse(course);
            e.setStudent(student);
            enrollments.add(e);
        }
        return enrollments;
    }

    private static void initStudsGr231() {
        Student mada = new Student();
        mada.setUsername("aiir2030");
        mada.setEncryptedPassword(BCrypt.hashpw("mada", BCrypt.gensalt()));
        mada.setLastName("Abrudan");
        mada.setFirstName("Ioana-Madalina");
        mada.setEmail("cnir2083@scs.ubbcluj.ro");
        mada.setRegistrationNr(22030);
        mada.setFathersInitials("I.");
        mada.setEnrollments(enrollmentList(mada, lftc, pdm));

        Student dan = new Student();
        dan.setUsername("adir2033");
        dan.setEncryptedPassword(BCrypt.hashpw("dan", BCrypt.gensalt()));
        dan.setLastName("Ailenei");
        dan.setFirstName("Dan Gabriel");
        dan.setEmail("cnir2083@scs.ubbcluj.ro");
        dan.setRegistrationNr(12033);
        dan.setFathersInitials("N.");
        dan.setEnrollments(enrollmentList(dan, lftc, pdm));

        Student alba = new Student();
        alba.setUsername("agir2034");
        alba.setEncryptedPassword(BCrypt.hashpw("alba", BCrypt.gensalt()));
        alba.setLastName("Alba");
        alba.setFirstName("Grigore Catalin");
        alba.setEmail("cnir2083@scs.ubbcluj.ro");
        alba.setRegistrationNr(12034);
        alba.setFathersInitials("G.I.");
        alba.setEnrollments(enrollmentList(alba, lftc, pdm));

        Student oana = new Student();
        oana.setUsername("aoir2035");
        oana.setEncryptedPassword(BCrypt.hashpw("oana", BCrypt.gensalt()));
        oana.setLastName("Albu");
        oana.setFirstName("Oana");
        oana.setEmail("aoir2035@scs.ubbcluj.ro");
        oana.setRegistrationNr(22035);
        oana.setFathersInitials("A.");
        oana.setEnrollments(enrollmentList(oana, lftc, pdm));

        Student stef = new Student();
        stef.setUsername("asir2036");
        stef.setEncryptedPassword(BCrypt.hashpw("stef", BCrypt.gensalt()));
        stef.setLastName("Andras");
        stef.setFirstName("Stefan Daniel");
        stef.setEmail("cnir2083@scs.ubbcluj.ro");
        stef.setRegistrationNr(12036);
        stef.setFathersInitials("D.S.");
        stef.setEnrollments(enrollmentList(stef, lftc, pdm));

        Student antal = new Student();
        antal.setUsername("aair2038");
        antal.setEncryptedPassword(BCrypt.hashpw("antal", BCrypt.gensalt()));
        antal.setLastName("Antal");
        antal.setFirstName("Alexandru");
        antal.setEmail("cnir2083@scs.ubbcluj.ro");
        antal.setRegistrationNr(12038);
        antal.setFathersInitials("I.");
        antal.setEnrollments(enrollmentList(antal, lftc, pdm, ss));
        DBInserter.antal = antal;

        Student mortu = new Student();
        mortu.setUsername("aair2039");
        mortu.setEncryptedPassword(BCrypt.hashpw("mortu", BCrypt.gensalt()));
        mortu.setLastName("Ardelean");
        mortu.setFirstName("Alexandru Andrei");
        mortu.setEmail("cnir2083@scs.ubbcluj.ro");
        mortu.setRegistrationNr(12039);
        mortu.setFathersInitials("D.H.");
        mortu.setEnrollments(enrollmentList(mortu, lftc, pdm));

        Student baies = new Student();
        baies.setUsername("bair2040");
        baies.setEncryptedPassword(BCrypt.hashpw("baies", BCrypt.gensalt()));
        baies.setLastName("Baies");
        baies.setFirstName("Alex Laurentiu");
        baies.setEmail("cnir2083.scs.ubbcluj.ro");
        baies.setRegistrationNr(12040);
        baies.setFathersInitials("T.M.");
        baies.setEnrollments(enrollmentList(baies, lftc, pdm));

        Student balan = new Student();
        balan.setUsername("bpir2041");
        balan.setEncryptedPassword(BCrypt.hashpw("balan", BCrypt.gensalt()));
        balan.setLastName("Balan");
        balan.setFirstName("Paul Catalin");
        balan.setEmail("cnir2083@scs.ubccluj.ro");
        balan.setRegistrationNr(12041);
        balan.setFathersInitials("A.");
        balan.setEnrollments(enrollmentList(balan, lftc, pdm));

        Student iulia = new Student();
        iulia.setUsername("biir2042");
        iulia.setEncryptedPassword(BCrypt.hashpw("iulia", BCrypt.gensalt()));
        iulia.setLastName("Baraian");
        iulia.setFirstName("Iulia Maria");
        iulia.setEmail("cnir2083@scs.ubbcluj.ro");
        iulia.setRegistrationNr(12042);
        iulia.setFathersInitials("A.");
        iulia.setEnrollments(enrollmentList(iulia, lftc, pdm));

        Student denis = new Student();
        denis.setUsername("bdir2043");
        denis.setEncryptedPassword(BCrypt.hashpw("denis", BCrypt.gensalt()));
        denis.setLastName("Barnutiu");
        denis.setFirstName("Denis Vasile");
        denis.setEmail("cnir2083@scs.ubbcluj.ro");
        denis.setRegistrationNr(12043);
        denis.setFathersInitials("M.R.");
        denis.setEnrollments(enrollmentList(denis, lftc, pdm));

        Student dragos = new Student();
        dragos.setUsername("bdir2045");
        dragos.setEncryptedPassword(BCrypt.hashpw("dragos", BCrypt.gensalt()));
        dragos.setLastName("Berlea");
        dragos.setFirstName("Dragos Teodor");
        dragos.setEmail("cnir2083@scs.ubbcluj.ro");
        dragos.setRegistrationNr(12045);
        dragos.setFathersInitials("N.");
        dragos.setEnrollments(enrollmentList(dragos, lftc, pdm));

        Student vlad = new Student();
        vlad.setUsername("bvir2046");
        vlad.setEncryptedPassword(BCrypt.hashpw("vlad", BCrypt.gensalt()));
        vlad.setLastName("Birsan");
        vlad.setFirstName("Vlad Ioan");
        vlad.setEmail("bvir2046@scs.ubbcluj.ro");
        vlad.setRegistrationNr(12046);
        vlad.setFathersInitials("A.");
        vlad.setEnrollments(enrollmentList(vlad, lftc, pdm, ss));
        DBInserter.vlad = vlad;

        Student mire = new Student();
        mire.setUsername("bmir2047");
        mire.setEncryptedPassword(BCrypt.hashpw("mire", BCrypt.gensalt()));
        mire.setLastName("Bocsa");
        mire.setFirstName("Mirela Alexandra");
        mire.setEmail("bmir2047@scs.ubbcluj.ro");
        mire.setRegistrationNr(22047);
        mire.setFathersInitials("G.");
        mire.setEnrollments(enrollmentList(mire, lftc, pdm));

        Student bodiu = new Student();
        bodiu.setUsername("bcir2425");
        bodiu.setEncryptedPassword(BCrypt.hashpw("bodiu", BCrypt.gensalt()));
        bodiu.setLastName("Bodiu");
        bodiu.setFirstName("Catalin");
        bodiu.setEmail("cnir2083@scs.ubbcluj.ro");
        bodiu.setRegistrationNr(12425);
        bodiu.setFathersInitials("D");
        bodiu.setEnrollments(enrollmentList(bodiu, lftc, pdm));

        Student boicu = new Student();
        boicu.setUsername("bair2049");
        boicu.setEncryptedPassword(BCrypt.hashpw("gabi", BCrypt.gensalt()));
        boicu.setLastName("Boicu");
        boicu.setFirstName("Alexandra");
        boicu.setEmail("cnir2083@scs.ubbcluj.ro");
        boicu.setRegistrationNr(12049);
        boicu.setFathersInitials("M.");
        boicu.setEnrollments(enrollmentList(boicu, lftc, pdm));

        Student gabi = new Student();
        gabi.setUsername("biir2052");
        gabi.setEncryptedPassword(BCrypt.hashpw("gabi", BCrypt.gensalt()));
        gabi.setLastName("Borsan");
        gabi.setFirstName("Ioan Gabriel");
        gabi.setEmail("cnir2083@scs.ubbcluj.ro");
        gabi.setRegistrationNr(12052);
        gabi.setFathersInitials("I.L.");
        gabi.setEnrollments(enrollmentList(gabi, lftc, pdm));

        Student bosinta = new Student();
        bosinta.setUsername("bbir2053");
        bosinta.setEncryptedPassword(BCrypt.hashpw("bosinta", BCrypt.gensalt()));
        bosinta.setLastName("Bosinta");
        bosinta.setFirstName("Bogdan Viorel");
        bosinta.setEmail("cnir2083@scs.ubbcluj.ro");
        bosinta.setRegistrationNr(12053);
        bosinta.setFathersInitials("V.V.");
        bosinta.setEnrollments(enrollmentList(bosinta, lftc, pdm));

        Student ana = new Student();
        ana.setUsername("bair2054");
        ana.setEncryptedPassword(BCrypt.hashpw("ana", BCrypt.gensalt()));
        ana.setLastName("Bosutar");
        ana.setFirstName("Ana Maria");
        ana.setEmail("cnir2083@scs.ubbcluj.ro");
        ana.setRegistrationNr(22054);
        ana.setFathersInitials("T.N.");
        ana.setEnrollments(enrollmentList(ana, lftc, pdm, ss));
        DBInserter.ana = ana;

        Student delia = new Student();
        delia.setUsername("bcir2055");
        delia.setEncryptedPassword(BCrypt.hashpw("delia", BCrypt.gensalt()));
        delia.setLastName("Brasovean");
        delia.setFirstName("Carmen Delia");
        delia.setEmail("bcir2055@scs.ubbcluj.ro");
        delia.setRegistrationNr(22055);
        delia.setEnrollments(enrollmentList(delia, lftc, pdm));
        delia.setFathersInitials("V.");

        Student florin = new Student();
        florin.setUsername("ctir2077");
        florin.setEncryptedPassword(BCrypt.hashpw("florin", BCrypt.gensalt()));
        florin.setLastName("Condrovici");
        florin.setFirstName("Teodor Florin");
        florin.setEmail("cnir2083@scs.ubbcluj.ro");
        florin.setRegistrationNr(12077);
        florin.setFathersInitials("V.D.");
        florin.setEnrollments(enrollmentList(florin, lftc, pdm));

        Student norberth = new Student();
        norberth.setUsername("cnir2083");
        norberth.setEncryptedPassword(BCrypt.hashpw("norberth", BCrypt.gensalt()));
        norberth.setLastName("Csorba");
        norberth.setFirstName("Norberth");
        norberth.setEmail("cnir2083@scs.ubbcluj.ro");
        norberth.setRegistrationNr(12083);
        norberth.setFathersInitials("S.");
        norberth.setEnrollments(enrollmentList(norberth, lftc, pdm, pdav, ss));
        DBInserter.norberth = norberth;

        Student paul = new Student();
        paul.setUsername("cpir2084");
        paul.setEncryptedPassword(BCrypt.hashpw("paul", BCrypt.gensalt()));
        paul.setLastName("Cus");
        paul.setFirstName("Paul Gabriel");
        paul.setEmail("cpir2084@scs.ubbcluj.ro");
        paul.setRegistrationNr(12084);
        paul.setFathersInitials("P.");
        paul.setEnrollments(enrollmentList(paul, lftc, pdm));

        Student mark = new Student();
        mark.setUsername("cmir2085");
        mark.setEncryptedPassword(BCrypt.hashpw("mark", BCrypt.gensalt()));
        mark.setLastName("Czeli");
        mark.setFirstName("Mark Dominik");
        mark.setEmail("cmir2085@scs.ubbcluj.ro");
        mark.setRegistrationNr(12085);
        mark.setFathersInitials("I.A.");
        mark.setEnrollments(enrollmentList(mark, lftc, pdm));

        Student darius = new Student();
        darius.setUsername("gdir2106");
        darius.setEncryptedPassword(BCrypt.hashpw("darius", BCrypt.gensalt()));
        darius.setLastName("Galanton");
        darius.setFirstName("Darius");
        darius.setEmail("cnir2083@scs.ubbcluj.ro");
        darius.setRegistrationNr(12106);
        darius.setFathersInitials("C.");
        darius.setEnrollments(enrollmentList(darius, lftc, pdm));

        Student andra = new Student();
        andra.setUsername("rair2184");
        andra.setEncryptedPassword(BCrypt.hashpw("andra", BCrypt.gensalt()));
        andra.setLastName("Runcan");
        andra.setFirstName("Andra");
        andra.setEmail("cnir2083@scs.ubbcluj.ro");
        andra.setRegistrationNr(22184);
        andra.setFathersInitials("M.M.");
        andra.setEnrollments(enrollmentList(andra, lftc, pdm));

        Student mihnea = new Student();
        mihnea.setUsername("tmir2199");
        mihnea.setEncryptedPassword(BCrypt.hashpw("taranu", BCrypt.gensalt()));
        mihnea.setLastName("Taranu");
        mihnea.setFirstName("Mihnea Andrei");
        mihnea.setEmail("cnir2083@scs.ubbcluj.ro");
        mihnea.setRegistrationNr(12199);
        mihnea.setFathersInitials("P.L.");
        mihnea.setEnrollments(enrollmentList(mihnea, pdm, retele));
        DBInserter.mihnea = mihnea;

        gr231 = new Group();
        gr231.setCode("231");
        gr231.setStudents(new ArrayList<>());
        gr231.getStudents().add(mada);
        gr231.getStudents().add(dan);
        gr231.getStudents().add(alba);
        gr231.getStudents().add(oana);
        gr231.getStudents().add(stef);
        gr231.getStudents().add(antal);
        gr231.getStudents().add(mortu);
        gr231.getStudents().add(baies);
        gr231.getStudents().add(balan);
        gr231.getStudents().add(iulia);
        gr231.getStudents().add(denis);
        gr231.getStudents().add(dragos);
        gr231.getStudents().add(vlad);
        gr231.getStudents().add(mire);
        gr231.getStudents().add(bodiu);
        gr231.getStudents().add(boicu);
        gr231.getStudents().add(gabi);
        gr231.getStudents().add(bosinta);
        gr231.getStudents().add(ana);
        gr231.getStudents().add(delia);
        gr231.getStudents().add(florin);
        gr231.getStudents().add(norberth);
        gr231.getStudents().add(paul);
        gr231.getStudents().add(mark);
        gr231.getStudents().add(darius);
        gr231.getStudents().add(andra);
        gr231.getStudents().add(mihnea);

        gr231.getStudents().forEach(student -> {
            student.setGroup(gr231);
            student.setNotifiedByEmail(false);
        });
    }

    private static void initStudsGr232() {
        Student abrudan = new Student();
        abrudan.setUsername("agir2200");
        abrudan.setEncryptedPassword(BCrypt.hashpw("abrudan", BCrypt.gensalt()));
        abrudan.setLastName("Abrudan");
        abrudan.setFirstName("Gabriel-Robert");
        abrudan.setEmail("cnir2083@scs.ubbcluj.ro");
        abrudan.setRegistrationNr(12200);
        abrudan.setFathersInitials("D.D.");
        abrudan.setEnrollments(enrollmentList(abrudan, lftc, pdm, ss));


        Student beltechi = new Student();
        beltechi.setUsername("brir2201");
        beltechi.setEncryptedPassword(BCrypt.hashpw("beltechi", BCrypt.gensalt()));
        beltechi.setLastName("Beltechi");
        beltechi.setFirstName("Rares");
        beltechi.setEmail("cnir2083@scs.ubbcluj.ro");
        beltechi.setRegistrationNr(12201);
        beltechi.setFathersInitials("N.C.");
        beltechi.setEnrollments(enrollmentList(beltechi, lftc, pdm, pdav, ss));
        DBInserter.beltechi = beltechi;


        Student boros = new Student();
        boros.setUsername("bfir2202");
        boros.setEncryptedPassword(BCrypt.hashpw("boros", BCrypt.gensalt()));
        boros.setLastName("Boros");
        boros.setFirstName("Florin");
        boros.setEmail("cnir2083@scs.ubbcluj.ro");
        boros.setRegistrationNr(12202);
        boros.setFathersInitials("V.F.");
        boros.setEnrollments(enrollmentList(boros, lftc, pdm, pdav, ss));
        DBInserter.boros = boros;

        Student bucur = new Student();
        bucur.setUsername("bfir2203");
        bucur.setEncryptedPassword(BCrypt.hashpw("bucur", BCrypt.gensalt()));
        bucur.setLastName("Bucur");
        bucur.setFirstName("Flaviu");
        bucur.setEmail("cnir2083@scs.ubbcluj.ro");
        bucur.setRegistrationNr(12203);
        bucur.setFathersInitials("I.");
        bucur.setEnrollments(enrollmentList(bucur, lftc, pdm, ss));

        Student bulmez = new Student();
        bulmez.setUsername("bair2204");
        bulmez.setEncryptedPassword(BCrypt.hashpw("bulmez", BCrypt.gensalt()));
        bulmez.setLastName("Bulmez");
        bulmez.setFirstName("Alexandru Florin");
        bulmez.setEmail("cnir2083@scs.ubbcluj.ro");
        bulmez.setRegistrationNr(12204);
        bulmez.setFathersInitials("I.");
        bulmez.setEnrollments(enrollmentList(bulmez, lftc, pdm, ss));

        Student burlacu = new Student();
        burlacu.setUsername("biir2205");
        burlacu.setEncryptedPassword(BCrypt.hashpw("burlacu", BCrypt.gensalt()));
        burlacu.setLastName("Burlacu");
        burlacu.setFirstName("Ioana Simona");
        burlacu.setEmail("cnir2083@scs.ubbcluj.ro");
        burlacu.setRegistrationNr(22205);
        burlacu.setFathersInitials("V.");
        burlacu.setEnrollments(enrollmentList(burlacu, lftc, pdm, ss));


        Student buta = new Student();
        buta.setUsername("beir2206");
        buta.setEncryptedPassword(BCrypt.hashpw("buta", BCrypt.gensalt()));
        buta.setLastName("Buta");
        buta.setFirstName("Elisabeta Liana");
        buta.setEmail("cnir2083@scs.ubbcluj.ro");
        buta.setRegistrationNr(22206);
        buta.setFathersInitials("O.Z.");
        buta.setEnrollments(enrollmentList(buta, lftc, pdm, ss));

        gr232 = new Group();
        gr232.setCode("232");
        gr232.setStudents(new ArrayList<>());
        gr232.getStudents().add(abrudan);
        gr232.getStudents().add(beltechi);
        gr232.getStudents().add(boros);
        gr232.getStudents().add(bucur);
        gr232.getStudents().add(bulmez);
        gr232.getStudents().add(burlacu);
        gr232.getStudents().add(buta);

        gr232.getStudents().forEach(student -> {
            student.setGroup(gr232);
            student.setNotifiedByEmail(false);
        });
    }


    private static void initStudsGr221() {
        Student alistar = new Student();
        alistar.setUsername("aair1000");
        alistar.setEncryptedPassword(BCrypt.hashpw("alistar", BCrypt.gensalt()));
        alistar.setLastName("Alistar");
        alistar.setFirstName("Andrei");
        alistar.setEmail("cnir2083@scs.ubbcluj.ro");
        alistar.setRegistrationNr(11000);
        alistar.setFathersInitials("I.");
        alistar.setEnrollments(enrollmentList(alistar, retele));

        Student amarandei = new Student();
        amarandei.setUsername("arir1001");
        amarandei.setEncryptedPassword(BCrypt.hashpw("amarandei", BCrypt.gensalt()));
        amarandei.setLastName("Amarandei");
        amarandei.setFirstName("Robert Andrei");
        amarandei.setEmail("cnir2083@scs.ubbcluj.ro");
        amarandei.setRegistrationNr(11001);
        amarandei.setFathersInitials("M.C.");
        amarandei.setEnrollments(enrollmentList(amarandei, retele));

        Student antinie = new Student();
        antinie.setUsername("arir1002");
        antinie.setEncryptedPassword(BCrypt.hashpw("antinie", BCrypt.gensalt()));
        antinie.setLastName("Antinie");
        antinie.setFirstName("Radu");
        antinie.setEmail("cnir2083@scs.ubbcluj.ro");
        antinie.setFathersInitials("G.R.");
        antinie.setRegistrationNr(11002);
        antinie.setEnrollments(enrollmentList(antinie, retele));

        Student anton = new Student();
        anton.setUsername("amir1003");
        anton.setEncryptedPassword(BCrypt.hashpw("anton", BCrypt.gensalt()));
        anton.setLastName("Anton");
        anton.setFirstName("Mihai");
        anton.setEmail("cnir2083@scs.ubbcluj.ro");
        anton.setRegistrationNr(11003);
        anton.setFathersInitials("A.L.");
        anton.setEnrollments(enrollmentList(anton, retele));

        Student ardelean = new Student();
        ardelean.setUsername("aair1004");
        ardelean.setEncryptedPassword(BCrypt.hashpw("ardelean", BCrypt.gensalt()));
        ardelean.setLastName("Ardelean");
        ardelean.setFirstName("Alexandru Florian");
        ardelean.setEmail("cnir2083@scs.ubbcluj.ro");
        ardelean.setRegistrationNr(11004);
        ardelean.setFathersInitials("F.A.");
        ardelean.setEnrollments(enrollmentList(ardelean, retele));

        Student ardelean2 = new Student();
        ardelean2.setUsername("atir1005");
        ardelean2.setEncryptedPassword(BCrypt.hashpw("ardelean", BCrypt.gensalt()));
        ardelean2.setLastName("Ardelean");
        ardelean2.setFirstName("Tudor Bogdan");
        ardelean2.setEmail("cnir2083@scs.ubbcluj.ro");
        ardelean2.setRegistrationNr(11005);
        ardelean2.setFathersInitials("T.");
        ardelean2.setEnrollments(enrollmentList(ardelean2, retele));

        Student avram = new Student();
        avram.setUsername("avir1006");
        avram.setEncryptedPassword(BCrypt.hashpw("avram", BCrypt.gensalt()));
        avram.setLastName("Avram");
        avram.setFirstName("Vasile Cosmin");
        avram.setEmail("cnir2083@scs.ubbcluj.ro");
        avram.setRegistrationNr(11006);
        avram.setFathersInitials("V.I.");
        avram.setEnrollments(enrollmentList(avram, retele));

        gr221 = new Group();
        gr221.setCode("221");
        gr221.setStudents(new ArrayList<>());
        gr221.getStudents().add(alistar);
        gr221.getStudents().add(amarandei);
        gr221.getStudents().add(antinie);
        gr221.getStudents().add(anton);
        gr221.getStudents().add(ardelean);
        gr221.getStudents().add(ardelean2);
        gr221.getStudents().add(avram);


        gr221.getStudents().forEach(student -> {
            student.setGroup(gr221);
            student.setNotifiedByEmail(false);
        });

    }

    private static void initStudsGr933() {
        Student deszi = new Student();
        deszi.setUsername("diie1007");
        deszi.setEncryptedPassword(BCrypt.hashpw("deszi", BCrypt.gensalt()));
        deszi.setLastName("Deszi");
        deszi.setFirstName("Imola Katalin");
        deszi.setEmail("cnir2083@scs.ubbcluj.ro");
        deszi.setRegistrationNr(21007);
        deszi.setFathersInitials("L.");
        deszi.setEnrollments(enrollmentList(deszi, flct, mdp, pdav, ss));


        Student dolot = new Student();
        dolot.setUsername("ddie1008");
        dolot.setEncryptedPassword(BCrypt.hashpw("dolot", BCrypt.gensalt()));
        dolot.setLastName("Dolot");
        dolot.setFirstName("Diana Nicole");
        dolot.setEmail("cnir2083@scs.ubbcluj.ro");
        dolot.setRegistrationNr(21008);
        dolot.setFathersInitials("C.D.");
        dolot.setEnrollments(enrollmentList(dolot, flct, mdp, pdav, ss));

        Student dragodan = new Student();
        dragodan.setUsername("daie1009");
        dragodan.setEncryptedPassword(BCrypt.hashpw("dragodan", BCrypt.gensalt()));
        dragodan.setLastName("Dragodan");
        dragodan.setFirstName("Alexandra Adriana");
        dragodan.setEmail("cnir2083@scs.ubbcluj.ro");
        dragodan.setRegistrationNr(21009);
        dragodan.setFathersInitials("A.");
        dragodan.setEnrollments(enrollmentList(dragodan, flct, mdp, pdav, ss));

        Student dragomir = new Student();
        dragomir.setUsername("diie1010");
        dragomir.setEncryptedPassword(BCrypt.hashpw("dragomir", BCrypt.gensalt()));
        dragomir.setLastName("Dragomir");
        dragomir.setFirstName("Ioana Bianca");
        dragomir.setEmail("cnir2083@scs.ubbcluj.ro");
        dragomir.setRegistrationNr(21010);
        dragomir.setFathersInitials("G.");
        dragomir.setEnrollments(enrollmentList(dragomir, flct, mdp, pdav, ss));

        Student duma = new Student();
        duma.setUsername("dlie1011");
        duma.setEncryptedPassword(BCrypt.hashpw("duma", BCrypt.gensalt()));
        duma.setLastName("Duma");
        duma.setFirstName("Laurentiu");
        duma.setEmail("cnir2083@scs.ubbcluj.ro");
        duma.setRegistrationNr(11011);
        duma.setFathersInitials("E.L.");
        duma.setEnrollments(enrollmentList(duma, flct, mdp, pdav, ss));

        Student dumitrascu = new Student();
        dumitrascu.setUsername("dmie1012");
        dumitrascu.setEncryptedPassword(BCrypt.hashpw("dumitrascu", BCrypt.gensalt()));
        dumitrascu.setLastName("Dumitrascu");
        dumitrascu.setFirstName("Mihai Razvan");
        dumitrascu.setEmail("cnir2083@scs.ubbcluj.ro");
        dumitrascu.setRegistrationNr(11012);
        dumitrascu.setFathersInitials("C.");
        dumitrascu.setEnrollments(enrollmentList(dumitrascu, flct, mdp, pdav, ss));

        Student farcas = new Student();
        farcas.setUsername("faoe1013");
        farcas.setEncryptedPassword(BCrypt.hashpw("farcas", BCrypt.gensalt()));
        farcas.setLastName("Farcas");
        farcas.setFirstName("Alexandru");
        farcas.setEmail("cnir2083@scs.ubbcluj.ro");
        farcas.setRegistrationNr(11013);
        farcas.setFathersInitials("D.");
        farcas.setEnrollments(enrollmentList(farcas, flct, mdp, pdav, ss));

        gr933 = new Group();
        gr933.setCode("933");
        gr933.setStudents(new ArrayList<>());
        gr933.getStudents().add(deszi);
        gr933.getStudents().add(dolot);
        gr933.getStudents().add(dragodan);
        gr933.getStudents().add(dragomir);
        gr933.getStudents().add(duma);
        gr933.getStudents().add(dumitrascu);
        gr933.getStudents().add(farcas);


        gr933.getStudents().forEach(student -> {
            student.setGroup(gr933);
            student.setNotifiedByEmail(false);
        });

    }

    private static void initStudsGr0() {
        Student andrei = new Student();
        andrei.setUsername("pair0000");
        andrei.setEncryptedPassword(BCrypt.hashpw("andrei", BCrypt.gensalt()));
        andrei.setLastName("Pop");
        andrei.setFirstName("Andrei");
        andrei.setEmail("cnir2083@scs.ubbcluj.ro");
        andrei.setRegistrationNr(10000);
        andrei.setFathersInitials("P.");
        andrei.setEnrollments(enrollmentList(andrei, lftc));

        Student vlad = new Student();
        vlad.setUsername("ivir0001");
        vlad.setEncryptedPassword(BCrypt.hashpw("vlad", BCrypt.gensalt()));
        vlad.setLastName("Istrate");
        vlad.setFirstName("Vlad");
        vlad.setEmail("cnir2083@scs.ubbcluj.ro");
        vlad.setRegistrationNr(10001);
        vlad.setFathersInitials("I.");
        vlad.setEnrollments(enrollmentList(vlad, retele));

        gr0 = new Group();
        gr0.setCode("0");
        gr0.setStudents(new ArrayList<>());
        gr0.getStudents().add(andrei);
        gr0.getStudents().add(vlad);

        gr0.getStudents().forEach(student -> {
            student.setGroup(gr0);
            student.setNotifiedByEmail(false);
        });
    }

    private static void initProfGuran() {
        guran.setUsername("guran");
        guran.setEncryptedPassword(BCrypt.hashpw("guran", BCrypt.gensalt()));
        guran.setLastName("Guran");
        guran.setFirstName("Adriana");
        guran.setEmail("guran@scs.ubbcluj.ro");
        guran.setWebPage("http://www.cs.ubbcluj.ro/~dana");

        List<ProfessorRight> guranRights = new ArrayList<>();
        ProfessorRight right = ProfessorRight.builder().professor(guran).group(gr231).course(lftc)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr231).course(lftc)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr231).course(lftc)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr231).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr231).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr231).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr231).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr231).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        guranRights.add(right);

        right = ProfessorRight.builder().professor(guran).group(gr232).course(lftc)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr232).course(lftc)
                .lessonType(LessonType.SEMINAR).rightType(RightType.WRITE).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr232).course(lftc)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr232).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr232).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.WRITE).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr232).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr232).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr232).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        guranRights.add(right);

        right = ProfessorRight.builder().professor(guran).group(gr0).course(lftc)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr0).course(lftc)
                .lessonType(LessonType.SEMINAR).rightType(RightType.WRITE).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr0).course(lftc)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr0).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr0).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.WRITE).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr0).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr0).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr0).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        guranRights.add(right);


        right = ProfessorRight.builder().professor(guran).group(gr933).course(flct)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr933).course(flct)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr933).course(flct)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        guranRights.add(right);
        right = ProfessorRight.builder().professor(guran).group(gr933).course(flct)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        guranRights.add(right);

        guran.setRights(guranRights);
    }

    private static void initProfMihis() {
        mihis.setUsername("mihis");
        mihis.setEncryptedPassword(BCrypt.hashpw("mihis", BCrypt.gensalt()));
        mihis.setLastName("Mihis");
        mihis.setFirstName("Andreea");
        mihis.setEmail("mihis@scs.ubbcluj.ro");
        mihis.setWebPage("http://www.cs.ubbcluj.ro/~mihis/");

        List<ProfessorRight> mihisRights = new ArrayList<>();
        ProfessorRight right = ProfessorRight.builder().professor(mihis).group(gr231).course(lftc)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        mihisRights.add(right);
        right = ProfessorRight.builder().professor(mihis).group(gr231).course(lftc)
                .lessonType(LessonType.SEMINAR).rightType(RightType.WRITE).build();
        mihisRights.add(right);
        right = ProfessorRight.builder().professor(mihis).group(gr231).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        mihisRights.add(right);
        right = ProfessorRight.builder().professor(mihis).group(gr231).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.WRITE).build();
        mihisRights.add(right);

        right = ProfessorRight.builder().professor(mihis).group(gr232).course(lftc)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        mihisRights.add(right);
        right = ProfessorRight.builder().professor(mihis).group(gr232).course(lftc)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        mihisRights.add(right);
        right = ProfessorRight.builder().professor(mihis).group(gr232).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        mihisRights.add(right);
        right = ProfessorRight.builder().professor(mihis).group(gr232).course(lftc)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        mihisRights.add(right);

        mihis.setRights(mihisRights);
    }

    private static void initProfMotogna() {
        motogna.setUsername("motogna");
        motogna.setEncryptedPassword(BCrypt.hashpw("motogna", BCrypt.gensalt()));
        motogna.setLastName("Motogna");
        motogna.setFirstName("Simona");
        motogna.setEmail("motogna@scs.ubbcluj.ro");
        motogna.setWebPage("https://motogna.wordpress.com/");

        List<ProfessorRight> motognaRights = new ArrayList<>();
        ProfessorRight right = ProfessorRight.builder().professor(motogna).group(gr933).course(flct)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        motognaRights.add(right);
        right = ProfessorRight.builder().professor(motogna).group(gr933).course(flct)
                .lessonType(LessonType.SEMINAR).rightType(RightType.WRITE).build();
        motognaRights.add(right);
        right = ProfessorRight.builder().professor(motogna).group(gr933).course(flct)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        motognaRights.add(right);
        right = ProfessorRight.builder().professor(motogna).group(gr933).course(flct)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        motognaRights.add(right);
        right = ProfessorRight.builder().professor(motogna).group(gr933).course(flct)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.WRITE).build();
        motognaRights.add(right);
        right = ProfessorRight.builder().professor(motogna).group(gr933).course(flct)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        motognaRights.add(right);
        right = ProfessorRight.builder().professor(motogna).group(gr933).course(flct)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        motognaRights.add(right);
        right = ProfessorRight.builder().professor(motogna).group(gr933).course(flct)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        motognaRights.add(right);

        motogna.setRights(motognaRights);
    }

    private static void initProfLazar() {
        lazar.setUsername("lazar");
        lazar.setEncryptedPassword(BCrypt.hashpw("lazar", BCrypt.gensalt()));
        lazar.setLastName("Lazar");
        lazar.setFirstName("Ioan");
        lazar.setEmail("lazar@scs.ubbcluj.ro");
        lazar.setWebPage("http://www.cs.ubbcluj.ro/~ilazar/");


        List<ProfessorRight> lazarRights = new ArrayList<>();
        ProfessorRight right = ProfessorRight.builder().professor(lazar).group(gr231).course(pdm)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr231).course(pdm)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr231).course(pdm)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr231).course(pdm)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr231).course(pdm)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr231).course(pdm)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr231).course(pdm)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr231).course(pdm)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        lazarRights.add(right);

        right = ProfessorRight.builder().professor(lazar).group(gr232).course(pdm)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr232).course(pdm)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr232).course(pdm)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr232).course(pdm)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr232).course(pdm)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr232).course(pdm)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr232).course(pdm)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr232).course(pdm)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        lazarRights.add(right);

        right = ProfessorRight.builder().professor(lazar).group(gr933).course(mdp)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr933).course(mdp)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr933).course(mdp)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr933).course(mdp)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr933).course(mdp)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr933).course(mdp)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr933).course(mdp)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        lazarRights.add(right);
        right = ProfessorRight.builder().professor(lazar).group(gr933).course(mdp)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        lazarRights.add(right);

        lazar.setRights(lazarRights);
    }

    private static void initProfForest() {
        forest.setUsername("forest");
        forest.setEncryptedPassword(BCrypt.hashpw("forest", BCrypt.gensalt()));
        forest.setLastName("Sterca");
        forest.setFirstName("Adrian");
        forest.setEmail("forest@scs.ubbcluj.ro");
        forest.setWebPage("http://www.cs.ubbcluj.ro/~forest/");

        List<ProfessorRight> forestRights = new ArrayList<>();
        ProfessorRight right = ProfessorRight.builder().professor(forest).group(gr231).course(pdav)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(pdav)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        forestRights.add(right);

        right = ProfessorRight.builder().professor(forest).group(gr232).course(pdav)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr232).course(pdav)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr232).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr232).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr232).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr232).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        forestRights.add(right);

        right = ProfessorRight.builder().professor(forest).group(gr933).course(pdav)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr933).course(pdav)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr933).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr933).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr933).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr933).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        forestRights.add(right);

        right = ProfessorRight.builder().professor(forest).group(gr221).course(retele)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr221).course(retele)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr221).course(retele)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr221).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr221).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr221).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr221).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr221).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        forestRights.add(right);

        right = ProfessorRight.builder().professor(forest).group(gr231).course(retele)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(retele)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(retele)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr231).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        forestRights.add(right);

        right = ProfessorRight.builder().professor(forest).group(gr0).course(retele)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr0).course(retele)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr0).course(retele)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr0).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr0).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr0).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr0).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        forestRights.add(right);
        right = ProfessorRight.builder().professor(forest).group(gr0).course(retele)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        forestRights.add(right);

        forest.setRights(forestRights);
    }

    private static void initProfCamelia() {
        camelia.setUsername("camelia");
        camelia.setEncryptedPassword(BCrypt.hashpw("camelia", BCrypt.gensalt()));
        camelia.setLastName("Serban");
        camelia.setFirstName("Camelia");
        camelia.setEmail("camelia@scs.ubbcluj.ro");
        camelia.setWebPage("https://www.cs.ubbcluj.ro/~camelia/");

        List<ProfessorRight> cameliaRights = new ArrayList<>();
        ProfessorRight right = ProfessorRight.builder().professor(camelia).group(gr231).course(pdav)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        cameliaRights.add(right);
        right = ProfessorRight.builder().professor(camelia).group(gr231).course(pdav)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        cameliaRights.add(right);
        right = ProfessorRight.builder().professor(camelia).group(gr231).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        cameliaRights.add(right);
        right = ProfessorRight.builder().professor(camelia).group(gr231).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        cameliaRights.add(right);

        right = ProfessorRight.builder().professor(camelia).group(gr232).course(pdav)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        cameliaRights.add(right);
        right = ProfessorRight.builder().professor(camelia).group(gr232).course(pdav)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        cameliaRights.add(right);
        right = ProfessorRight.builder().professor(camelia).group(gr232).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        cameliaRights.add(right);
        right = ProfessorRight.builder().professor(camelia).group(gr232).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        cameliaRights.add(right);

        right = ProfessorRight.builder().professor(camelia).group(gr933).course(pdav)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        cameliaRights.add(right);
        right = ProfessorRight.builder().professor(camelia).group(gr933).course(pdav)
                .lessonType(LessonType.SEMINAR).rightType(RightType.WRITE).build();
        cameliaRights.add(right);
        right = ProfessorRight.builder().professor(camelia).group(gr933).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        cameliaRights.add(right);
        right = ProfessorRight.builder().professor(camelia).group(gr933).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.WRITE).build();
        cameliaRights.add(right);

        camelia.setRights(cameliaRights);
    }

    private static void initProfGrigo() {
        grigo.setUsername("grigo");
        grigo.setEncryptedPassword(BCrypt.hashpw("grigo", BCrypt.gensalt()));
        grigo.setLastName("Cojocar");
        grigo.setFirstName("Grigoreta");
        grigo.setEmail("grigo@scs.ubbcluj.ro");
        grigo.setWebPage("https://www.cs.ubbcluj.ro/~grigo/");


        List<ProfessorRight> grigoRights = new ArrayList<>();
        ProfessorRight right = ProfessorRight.builder().professor(grigo).group(gr231).course(pdav)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        grigoRights.add(right);
        right = ProfessorRight.builder().professor(grigo).group(gr231).course(pdav)
                .lessonType(LessonType.SEMINAR).rightType(RightType.WRITE).build();
        grigoRights.add(right);
        right = ProfessorRight.builder().professor(grigo).group(gr231).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        grigoRights.add(right);
        right = ProfessorRight.builder().professor(grigo).group(gr231).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.WRITE).build();
        grigoRights.add(right);

        right = ProfessorRight.builder().professor(grigo).group(gr232).course(pdav)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        grigoRights.add(right);
        right = ProfessorRight.builder().professor(grigo).group(gr232).course(pdav)
                .lessonType(LessonType.SEMINAR).rightType(RightType.WRITE).build();
        grigoRights.add(right);
        right = ProfessorRight.builder().professor(grigo).group(gr232).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        grigoRights.add(right);
        right = ProfessorRight.builder().professor(grigo).group(gr232).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.WRITE).build();
        grigoRights.add(right);

        right = ProfessorRight.builder().professor(grigo).group(gr933).course(pdav)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        grigoRights.add(right);
        right = ProfessorRight.builder().professor(grigo).group(gr933).course(pdav)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        grigoRights.add(right);
        right = ProfessorRight.builder().professor(grigo).group(gr933).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        grigoRights.add(right);
        right = ProfessorRight.builder().professor(grigo).group(gr933).course(pdav)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        grigoRights.add(right);

        grigo.setRights(grigoRights);
    }

    private static void initProfSuciu() {
        suciu.setUsername("suciu");
        suciu.setEncryptedPassword(BCrypt.hashpw("suciu", BCrypt.gensalt()));
        suciu.setLastName("Suciu");
        suciu.setFirstName("Mihai");
        suciu.setEmail("suciu@scs.ubbcluj.ro");
        suciu.setWebPage("https://www.cs.ubbcluj.ro/~mihai-suciu/");

        List<ProfessorRight> suciuRights = new ArrayList<>();
        ProfessorRight right = ProfessorRight.builder().professor(suciu).group(gr231).course(ss)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr231).course(ss)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr231).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr231).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr231).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr231).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        suciuRights.add(right);

        right = ProfessorRight.builder().professor(suciu).group(gr232).course(ss)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr232).course(ss)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr232).course(ss)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr232).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr232).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr232).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr232).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr232).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        suciuRights.add(right);

        right = ProfessorRight.builder().professor(suciu).group(gr933).course(ss)
                .lessonType(LessonType.SEMINAR).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr933).course(ss)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr933).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_SEMINAR).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr933).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr933).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.READ).build();
        suciuRights.add(right);
        right = ProfessorRight.builder().professor(suciu).group(gr933).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_COURSE).rightType(RightType.WRITE).build();
        suciuRights.add(right);

        suciu.setRights(suciuRights);
    }

    private static void initProfTicle() {
        ticle.setUsername("ticle");
        ticle.setEncryptedPassword(BCrypt.hashpw("ticle", BCrypt.gensalt()));
        ticle.setLastName("Ticle");
        ticle.setFirstName("Daniel");
        ticle.setEmail("ticle@scs.ubbcluj.ro");
        ticle.setWebPage("http://www.cs.ubbcluj.ro/~daniel/");

        List<ProfessorRight> ticleRights = new ArrayList<>();
        ProfessorRight right = ProfessorRight.builder().professor(ticle).group(gr231).course(ss)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        ticleRights.add(right);
        right = ProfessorRight.builder().professor(ticle).group(gr231).course(ss)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        ticleRights.add(right);
        right = ProfessorRight.builder().professor(ticle).group(gr231).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        ticleRights.add(right);
        right = ProfessorRight.builder().professor(ticle).group(gr231).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        ticleRights.add(right);

        right = ProfessorRight.builder().professor(ticle).group(gr933).course(ss)
                .lessonType(LessonType.LABORATORY).rightType(RightType.READ).build();
        ticleRights.add(right);
        right = ProfessorRight.builder().professor(ticle).group(gr933).course(ss)
                .lessonType(LessonType.LABORATORY).rightType(RightType.WRITE).build();
        ticleRights.add(right);
        right = ProfessorRight.builder().professor(ticle).group(gr933).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.READ).build();
        ticleRights.add(right);
        right = ProfessorRight.builder().professor(ticle).group(gr933).course(ss)
                .lessonType(LessonType.PARTIAL_EXAM_LABORATORY).rightType(RightType.WRITE).build();
        ticleRights.add(right);

        ticle.setRights(ticleRights);
    }

    private static void initCourses() {
        lftc.setCode("MLR5023");
        lftc.setName("Limbaje Formale si Tehnici de Compilare");
        lftc.setLessonTemplates(Arrays.asList(
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)1).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)2).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)3).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)4).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)5).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)6).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)7).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)8).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)9).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)10).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)11).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)12).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)13).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)14).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)1).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)2).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)3).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)4).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)5).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)6).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)7).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)8).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)9).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)10).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)11).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)12).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)13).weight(0.09).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)14).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.PARTIAL_EXAM_SEMINAR).nr((byte)1).weight(0.14).build(),
                LessonTemplate.builder().type(LessonType.PARTIAL_EXAM_COURSE).nr((byte)1).weight(0.14).build(),
                LessonTemplate.builder().type(LessonType.PARTIAL_EXAM_COURSE).nr((byte)2).weight(0.14).build()
        ));
        lftc.setCoordinator(guran);

        flct.setCode("MLE5023");
        flct.setName("Formal Languages and Compilation Techniques");
        flct.setLessonTemplates(Arrays.asList(
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)1).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)2).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)3).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)4).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)5).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)6).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)7).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)8).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)9).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)10).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)11).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)12).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)13).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)14).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)1).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)2).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)3).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)4).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)5).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)6).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)7).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)8).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)9).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)10).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)11).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)12).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)13).weight(0.09).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)14).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.PARTIAL_EXAM_SEMINAR).nr((byte)14).weight(0.14).build(),
                LessonTemplate.builder().type(LessonType.PARTIAL_EXAM_COURSE).nr((byte)14).weight(0.14).build(),
                LessonTemplate.builder().type(LessonType.PARTIAL_EXAM_COURSE).nr((byte)14).weight(0.14).build()
        ));
        flct.setCoordinator(motogna);

        pdm.setCode("MLR5078");
        pdm.setName("Programare pentru dispozitive mobile");
        pdm.setLessonTemplates(Arrays.asList(
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)1).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)2).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)3).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)4).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)5).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)6).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)7).weight(1.0).build()
        ));
        pdm.setCoordinator(lazar);

        mdp.setCode("MLE5078");
        mdp.setName("Mobile devices programming");
        mdp.setLessonTemplates(Arrays.asList(
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)1).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)2).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)3).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)4).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)5).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)6).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)7).weight(1.0).build()
        ));
        mdp.setCoordinator(lazar);

        pdav.setCode("MLE8117");
        pdav.setName("Audio-Video Data Processing");
        pdav.setLessonTemplates(Arrays.asList(
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)1).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)2).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)3).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)4).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)5).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)6).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)7).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)8).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)9).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)10).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)11).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)12).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)13).build(),
                LessonTemplate.builder().type(LessonType.SEMINAR).nr((byte)14).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)1).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)2).weight(0.2).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)3).weight(0.2).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)4).weight(0.2).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)5).weight(0.2).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)6).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)7).weight(0.2).build()
        ));
        pdav.setCoordinator(forest);

        ss.setCode("MLR8114");
        ss.setName("Securitate Software");
        ss.setLessonTemplates(Arrays.asList(
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)1).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)2).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)3).weight(0.2).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)4).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)5).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)6).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)7).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)8).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)9).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)10).weight(0.2).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)11).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)12).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)13).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)14).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.PARTIAL_EXAM_LABORATORY).nr((byte)1).weight(0.15).build(),
                LessonTemplate.builder().type(LessonType.PARTIAL_EXAM_LABORATORY).nr((byte)2).weight(0.15).build(),
                LessonTemplate.builder().type(LessonType.PARTIAL_EXAM_LABORATORY).nr((byte)3).weight(0.15).build(),
                LessonTemplate.builder().type(LessonType.PARTIAL_EXAM_LABORATORY).nr((byte)4).weight(0.15).build()
        ));
        ss.setCoordinator(suciu);

        retele.setCode("MLR5002");
        retele.setName("Retele de calculatoare");
        retele.setLessonTemplates(Arrays.asList(
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)1).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)2).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)3).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)4).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)5).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)6).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)7).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)8).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)9).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)10).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)11).weight(0.07).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)12).weight(0.0).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)13).weight(0.15).build(),
                LessonTemplate.builder().type(LessonType.LABORATORY).nr((byte)14).weight(0.15).build()
        ));
        retele.setCoordinator(forest);
    }


    public static void initLessons() {
        List<Group> groups = new ArrayList<>();
        groups.add(gr231);
        groups.add(gr232);
        groups.add(gr221);
        groups.add(gr933);
        groups.add(gr0);
        groups.forEach(group -> {
            group.getStudents().forEach(student -> {
                student.getEnrollments().forEach(enrollment -> {
                    List<Lesson> lessons = new ArrayList<>();
                    enrollment.getCourse().getLessonTemplates().forEach(lessonTemplate -> {
                        lessons.add(Lesson.builder()
                                .template(lessonTemplate)
                                .enrollment(enrollment)
                                .build());
                    });
                    enrollment.setLessons(lessons);
                });
            });
        });
    }

    private static void persist() {
        System.out.println("persist started");
        EntityTransaction tran = entityManager.getTransaction();
        try {
            tran.begin();
            List<Group> groups = new ArrayList<>();
            groups.add(gr231);
            groups.add(gr232);
            groups.add(gr221);
            groups.add(gr933);
            groups.add(gr0);
            List<Professor> professors = new ArrayList<>();
            professors.add(guran);
            professors.add(mihis);
            professors.add(motogna);
            professors.add(lazar);
            professors.add(forest);
            professors.add(grigo);
            professors.add(camelia);
            professors.add(suciu);
            professors.add(ticle);
            professors.forEach(professor -> {
                entityManager.persist(professor);
            });
            groups.forEach(group -> {
                group.getStudents().forEach(student -> {
                    student.getEnrollments().forEach(enrollment -> {
                        entityManager.persist(enrollment.getCourse());
                        entityManager.persist(enrollment);
                        enrollment.getLessons().forEach(lesson -> {
                            entityManager.persist(lesson.getTemplate());
                            entityManager.persist(lesson);
                        });
                    });
                    entityManager.persist(student);
                });
                entityManager.persist(group);
            });
            professors.forEach(professor -> {
                professor.getRights().forEach(teaching -> {
                    entityManager.persist(teaching);
                });
            });
            entityManager.flush();
            tran.commit();
            System.out.println("persist finished");
        } catch (RuntimeException ex) {
            tran.rollback();
            ex.printStackTrace();
            System.out.println("persist failed");
        }
    }

    public static void main(String[] args) {
        System.out.println("init studs");
        initStudsGr231();
        initStudsGr232();
        initStudsGr933();
        initStudsGr221();
        initStudsGr0();
        System.out.println("init courses");
        initCourses();
        System.out.println("init professors");
        initProfGuran();
        initProfMihis();
        initProfMotogna();
        initProfLazar();
        initProfForest();
        initProfCamelia();
        initProfGrigo();
        initProfSuciu();
        initProfTicle();
        System.out.println("init lessons");
        initLessons();
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("db-insert");
        entityManager = emFactory.createEntityManager();
        persist();
    }
}
