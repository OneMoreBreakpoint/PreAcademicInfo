package application;

import data_layer.domain.*;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class DBInserter {

    private static Group gr231,gr232,gr221,gr933;
    private static Professor guran, mihis, motogna, lazar, forest, grigo, camelia, suciu, ticle;
    private static Course lftc, flct, pdm, mdp, pdav, ss, retele;
    private static Student mihnea, norberth, antal, vlad, ana, beltechi, boros;
    private static EntityManager entityManager;

    static{
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

    private static List<Enrollment> enrollmentList(Student student, Course... courses){
        List<Enrollment> enrollments = new ArrayList<>();
        for(Course course : courses){
            Enrollment e = new Enrollment();
            e.setCourse(course);
            e.setStudent(student);
            enrollments.add(e);
        }
        return enrollments;
    }

    private static void initStudsGr231(){
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
        mihnea.setEnrollments(enrollmentList(mihnea, pdm));
        DBInserter.mihnea = mihnea;

        gr231 = new Group();
        gr231.setCode((short)231);
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

    private static void initStudsGr232(){
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
        gr232.setCode((short)232);
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


    private static void initStudsGr221(){
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
        gr221.setCode((short)221);
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

    private static void initStudsGr933(){
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
        gr933.setCode((short)933);
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

    private static void initProfGuran(){
        guran.setUsername("guran");
        guran.setEncryptedPassword(BCrypt.hashpw("guran", BCrypt.gensalt()));
        guran.setLastName("Guran");
        guran.setFirstName("Adriana");
        guran.setEmail("guran@scs.ubbcluj.ro");
        guran.setWebPage("http://www.cs.ubbcluj.ro/~dana");

        Teaching t_lftc = new Teaching();
        t_lftc.setLaboratoryGroups(new ArrayList<>());
        t_lftc.getLaboratoryGroups().add(gr231);
        t_lftc.setSeminarGroups(new ArrayList<>());
        t_lftc.getSeminarGroups().add(gr232);
        t_lftc.setCourse(lftc);
        t_lftc.setProfessor(guran);

        Teaching t_flct = new Teaching();
        t_flct.setLaboratoryGroups(new ArrayList<>());
        t_flct.getLaboratoryGroups().add(gr933);
        t_flct.setCourse(flct);
        t_flct.setProfessor(guran);

        List<Teaching> teachings = new ArrayList<>();
        teachings.add(t_lftc);
        teachings.add(t_flct);
        guran.setTeachingList(teachings);
    }

    private static void initProfMihis(){
        mihis.setUsername("mihis");
        mihis.setEncryptedPassword(BCrypt.hashpw("mihis", BCrypt.gensalt()));
        mihis.setLastName("Mihis");
        mihis.setFirstName("Andreea");
        mihis.setEmail("mihis@scs.ubbcluj.ro");
        mihis.setWebPage("http://www.cs.ubbcluj.ro/~mihis/");

        Teaching t_lftc = new Teaching();
        t_lftc.setLaboratoryGroups(new ArrayList<>());
        t_lftc.getLaboratoryGroups().add(gr232);
        t_lftc.setSeminarGroups(new ArrayList<>());
        t_lftc.getSeminarGroups().add(gr231);
        t_lftc.setCourse(lftc);
        t_lftc.setProfessor(mihis);

        List<Teaching> teachings = new ArrayList<>();
        teachings.add(t_lftc);
        mihis.setTeachingList(teachings);
    }

    private static void initProfMotogna(){
        motogna.setUsername("motogna");
        motogna.setEncryptedPassword(BCrypt.hashpw("motogna", BCrypt.gensalt()));
        motogna.setLastName("Motogna");
        motogna.setFirstName("Simona");
        motogna.setEmail("motogna@scs.ubbcluj.ro");
        motogna.setWebPage("https://motogna.wordpress.com/");

        Teaching t_flct = new Teaching();
        t_flct.setLaboratoryGroups(new ArrayList<>());
        t_flct.getLaboratoryGroups().add(gr933);
        t_flct.setCourse(flct);
        t_flct.setProfessor(motogna);

        List<Teaching> teachings = new ArrayList<>();
        teachings.add(t_flct);
        motogna.setTeachingList(teachings);
    }

    private static void initProfLazar(){
        lazar.setUsername("lazar");
        lazar.setEncryptedPassword(BCrypt.hashpw("lazar", BCrypt.gensalt()));
        lazar.setLastName("Lazar");
        lazar.setFirstName("Ioan");
        lazar.setEmail("lazar@scs.ubbcluj.ro");
        lazar.setWebPage("http://www.cs.ubbcluj.ro/~ilazar/");

        Teaching t_pdm = new Teaching();
        t_pdm.setLaboratoryGroups(new ArrayList<>());
        t_pdm.getLaboratoryGroups().add(gr231);
        t_pdm.getLaboratoryGroups().add(gr232);
        t_pdm.setCourse(pdm);
        t_pdm.setProfessor(lazar);

        Teaching t_mdp = new Teaching();
        t_mdp.setLaboratoryGroups(new ArrayList<>());
        t_mdp.getLaboratoryGroups().add(gr933);
        t_mdp.setCourse(mdp);
        t_mdp.setProfessor(lazar);

        List<Teaching> teachings = new ArrayList<>();
        teachings.add(t_pdm);
        teachings.add(t_mdp);
        lazar.setTeachingList(teachings);
    }

    private static void initProfForest(){
        forest.setUsername("forest");
        forest.setEncryptedPassword(BCrypt.hashpw("forest", BCrypt.gensalt()));
        forest.setLastName("Sterca");
        forest.setFirstName("Adrian");
        forest.setEmail("forest@scs.ubbcluj.ro");
        forest.setWebPage("http://www.cs.ubbcluj.ro/~forest/");

        Teaching t_pdav = new Teaching();
        t_pdav.setCourse(pdav);
        t_pdav.setProfessor(forest);

        Teaching t_retele = new Teaching();
        t_retele.setLaboratoryGroups(new ArrayList<>());
        t_retele.getLaboratoryGroups().add(gr221);
        t_retele.setCourse(retele);
        t_retele.setProfessor(forest);

        List<Teaching> teachings = new ArrayList<>();
        teachings.add(t_pdav);
        teachings.add(t_retele);
        forest.setTeachingList(teachings);
    }

    private static void initProfCamelia(){
        camelia.setUsername("camelia");
        camelia.setEncryptedPassword(BCrypt.hashpw("camelia", BCrypt.gensalt()));
        camelia.setLastName("Serban");
        camelia.setFirstName("Camelia");
        camelia.setEmail("camelia@scs.ubbcluj.ro");
        camelia.setWebPage("https://www.cs.ubbcluj.ro/~camelia/");

        Teaching t_pdav = new Teaching();
        t_pdav.setLaboratoryGroups(new ArrayList<>());
        t_pdav.getLaboratoryGroups().add(gr231);
        t_pdav.getLaboratoryGroups().add(gr232);
        t_pdav.setSeminarGroups(new ArrayList<>());
        t_pdav.getSeminarGroups().add(gr933);
        t_pdav.setCourse(pdav);
        t_pdav.setProfessor(camelia);

        List<Teaching> teachings = new ArrayList<>();
        teachings.add(t_pdav);
        camelia.setTeachingList(teachings);
    }

    private static void initProfGrigo(){
        grigo.setUsername("grigo");
        grigo.setEncryptedPassword(BCrypt.hashpw("grigo", BCrypt.gensalt()));
        grigo.setLastName("Cojocar");
        grigo.setFirstName("Grigoreta");
        grigo.setEmail("grigo@scs.ubbcluj.ro");
        grigo.setWebPage("https://www.cs.ubbcluj.ro/~grigo/");

        Teaching t_pdav = new Teaching();
        t_pdav.setLaboratoryGroups(new ArrayList<>());
        t_pdav.getLaboratoryGroups().add(gr933);
        t_pdav.setSeminarGroups(new ArrayList<>());
        t_pdav.getSeminarGroups().add(gr231);
        t_pdav.getSeminarGroups().add(gr232);
        t_pdav.setCourse(pdav);
        t_pdav.setProfessor(grigo);

        List<Teaching> teachings = new ArrayList<>();
        teachings.add(t_pdav);
        grigo.setTeachingList(teachings);
    }

    private static void initProfSuciu(){
        suciu.setUsername("suciu");
        suciu.setEncryptedPassword(BCrypt.hashpw("suciu", BCrypt.gensalt()));
        suciu.setLastName("Suciu");
        suciu.setFirstName("Mihai");
        suciu.setEmail("suciu@scs.ubbcluj.ro");
        suciu.setWebPage("https://www.cs.ubbcluj.ro/~mihai-suciu/");

        Teaching t_ss = new Teaching();
        t_ss.setLaboratoryGroups(new ArrayList<>());
        t_ss.getLaboratoryGroups().add(gr232);
        t_ss.setCourse(ss);
        t_ss.setProfessor(suciu);

        List<Teaching> teachings = new ArrayList<>();
        teachings.add(t_ss);
        suciu.setTeachingList(teachings);
    }

    private static void initProfTicle(){
        ticle.setUsername("ticle");
        ticle.setEncryptedPassword(BCrypt.hashpw("ticle", BCrypt.gensalt()));
        ticle.setLastName("Ticle");
        ticle.setFirstName("Daniel");
        ticle.setEmail("ticle@scs.ubbcluj.ro");
        ticle.setWebPage("http://www.cs.ubbcluj.ro/~daniel/");

        Teaching t_ss = new Teaching();
        t_ss.setLaboratoryGroups(new ArrayList<>());
        t_ss.getLaboratoryGroups().add(gr231);
        t_ss.getLaboratoryGroups().add(gr933);
        t_ss.setCourse(ss);
        t_ss.setProfessor(ticle);

        List<Teaching> teachings = new ArrayList<>();
        teachings.add(t_ss);
        ticle.setTeachingList(teachings);
    }

    private static void initCourses(){
        lftc.setCode("MLR5023");
        lftc.setName("Limbaje Formale si Tehnici de Compilare");
        lftc.setNrOfSeminars((byte)14);
        lftc.setNrOfLaboratories((byte)14);
        lftc.setCoordinator(guran);

        flct.setCode("MLE5023");
        flct.setName("Formal Languages and Compilation Techniques");
        flct.setNrOfSeminars((byte)14);
        flct.setNrOfLaboratories((byte)14);
        flct.setCoordinator(motogna);

        pdm.setCode("MLR5078");
        pdm.setName("Programare pentru dispozitive mobile");
        pdm.setNrOfSeminars((byte)0);
        pdm.setNrOfLaboratories((byte)7);
        pdm.setCoordinator(lazar);

        mdp.setCode("MLE5078");
        mdp.setName("Mobile devices programming");
        mdp.setNrOfSeminars((byte)0);
        mdp.setNrOfLaboratories((byte)7);
        mdp.setCoordinator(lazar);

        pdav.setCode("MLE8117");
        pdav.setName("Audio-Video Data Processing");
        pdav.setNrOfSeminars((byte)14);
        pdav.setNrOfLaboratories((byte)7);
        pdav.setCoordinator(forest);

        ss.setCode("MLR8114");
        ss.setName("Securitate Software");
        ss.setNrOfSeminars((byte)0);
        ss.setNrOfLaboratories((byte)14);
        ss.setCoordinator(suciu);

        retele.setCode("MLR5002");
        retele.setName("Retele de calculatoare");
        retele.setNrOfSeminars((byte)0);
        retele.setNrOfLaboratories((byte)7);
        retele.setCoordinator(forest);
    }



    public static void initLessons(){
        List<Group> groups = new ArrayList<>();
        groups.add(gr231);
        groups.add(gr232);
        groups.add(gr221);
        groups.add(gr933);
        groups.forEach(group -> {
            group.getStudents().forEach(student -> {
                student.getEnrollments().forEach(enrollment -> {
                    List<Lesson> lessons = new ArrayList<>();
                    int nrOfSeminars = enrollment.getCourse().getNrOfSeminars();
                    int nrOfLaboratories = enrollment.getCourse().getNrOfLaboratories();
                    int nrOfLessons =  + nrOfLaboratories + nrOfSeminars;
                    if(nrOfSeminars == 0){
                        for(int i=1; i<=nrOfLessons; i++){
                            Lesson l = new Lesson();
                            l.setType(Lesson.LessonType.LABORATORY);
                            l.setNr((byte)i);
                            lessons.add(l);
                        }
                    }else if(nrOfSeminars == nrOfLaboratories){
                        boolean pingPong = true;
                        for(int i=1, j=1; i<=nrOfLessons; i++){
                            Lesson l = new Lesson();
                            if(pingPong){
                                l.setType(Lesson.LessonType.SEMINAR);
                                l.setNr((byte)j);
                            }else{
                                l.setType(Lesson.LessonType.LABORATORY);
                                l.setNr((byte)j++);
                            }
                            pingPong = !pingPong;
                            lessons.add(l);
                        }
                    }else if(nrOfSeminars == 7 && nrOfLaboratories == 14){
                        for(int i=1, j=1; i<=nrOfSeminars; i++, j+=2){
                            Lesson l1 = new Lesson(), l2 = new Lesson(), l3 = new Lesson();
                            l1.setType(Lesson.LessonType.SEMINAR);
                            l1.setNr((byte)i);
                            l2.setType(Lesson.LessonType.LABORATORY);
                            l2.setNr((byte)j);
                            l3.setType(Lesson.LessonType.LABORATORY);
                            int jj = j+1;
                            l3.setNr((byte)jj);
                            lessons.add(l1);
                            lessons.add(l2);
                            lessons.add(l3);
                        }
                    }else if(nrOfSeminars == 14 && nrOfLaboratories == 7){
                        for(int i=1, j=1; i<nrOfLaboratories; i++, j+=2){
                            Lesson l1 = new Lesson(), l2 = new Lesson(), l3 = new Lesson();
                            l1.setType(Lesson.LessonType.SEMINAR);
                            l1.setNr((byte)j);
                            l2.setType(Lesson.LessonType.LABORATORY);
                            l2.setNr((byte)i);
                            l3.setType(Lesson.LessonType.SEMINAR);
                            int jj = j+1;
                            l3.setNr((byte)jj);
                            lessons.add(l1);
                            lessons.add(l2);
                            lessons.add(l3);
                        }
                    }
                    enrollment.setLessons(lessons);
                });
            });
        });
    }

    public static void initPartialExams(){
        List<Group> groups = new ArrayList<>();
        groups.add(gr231);
        groups.add(gr232);
        groups.add(gr221);
        groups.add(gr933);
        groups.forEach(group->{
            group.getStudents().forEach(student -> {
                student.getEnrollments().forEach(enrollment -> {
                    String courseCode = enrollment.getCourse().getCode();
                    if(courseCode.equals("MLR8114")) { // SS
                        List<PartialExam> exams = new ArrayList<>();
                        for(int i=1; i<=4; i++){
                            PartialExam exam = new PartialExam();
                            exam.setType(PartialExam.PartialExamType.LABORATORY);
                            exam.setNr((byte)i);
                            exams.add(exam);
                        }
                        enrollment.setPartialExams(exams);
                    } else if(courseCode.equals("MLR5023") || courseCode.equals("MLE5023")) { // LFTC, FLCT
                        List<PartialExam> exams = new ArrayList<>();
                        for(int i=1; i<=2; i++){
                            PartialExam exam = new PartialExam();
                            exam.setType(PartialExam.PartialExamType.COURSE);
                            exam.setNr((byte)i);
                            exams.add(exam);
                        }
                        PartialExam exam = new PartialExam();
                        exam.setType(PartialExam.PartialExamType.SEMINAR);
                        exam.setNr((byte)1);
                        exams.add(exam);
                        enrollment.setPartialExams(exams);
                    }
                });
            });
        });
    }

    private static void persist(){
        System.out.println("persist started");
        EntityTransaction tran = entityManager.getTransaction();
        try {
            tran.begin();
            List<Group> groups = new ArrayList<>();
            groups.add(gr231);
            groups.add(gr232);
            groups.add(gr221);
            groups.add(gr933);
            groups.forEach(group -> {
                group.getStudents().forEach(student -> {
                    student.getEnrollments().forEach(enrollment -> {
                        entityManager.persist(enrollment.getCourse());
                        if(enrollment.getPartialExams() != null){
                            enrollment.getPartialExams().forEach(partialExam -> {
                                entityManager.persist(partialExam);
                            });
                        }
                        enrollment.getLessons().forEach(lesson -> {
                            entityManager.persist(lesson);
                        });
                        entityManager.persist(enrollment);
                    });
                    entityManager.persist(student);
                });
                entityManager.persist(group);
            });
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
                professor.getTeachingList().forEach(teaching -> {
                    entityManager.persist(teaching);
                });
                entityManager.persist(professor);
            });
            entityManager.flush();
            tran.commit();
            System.out.println("persist finished");
        }catch (RuntimeException ex){
            tran.rollback();
            ex.printStackTrace();
            System.out.println("persist failed");
        }
    }

    public static void main(String[] args){
        System.out.println("init studs");
        initStudsGr231();
        initStudsGr232();
        initStudsGr933();
        initStudsGr221();
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
        System.out.println("init partial exams");
        initPartialExams();
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("db-insert");
        entityManager = emFactory.createEntityManager();
        persist();
    }
}
