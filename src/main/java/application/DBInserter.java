package application;

import data_layer.domain.Student;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class DBInserter {

    public static List<Student> getStudsGr231(){
        Student mada = new Student();
        mada.setUsername("aiir2030");
        mada.setEncryptedPassword(BCrypt.hashpw("mada", BCrypt.gensalt()));
        mada.setLastName("Abrudan");
        mada.setFirstName("Ioana-Mădălina");
        mada.setEmail("cnir2083@scs.ubbcluj.ro");
        mada.setRegistrationNr(22030);
        mada.setFathersCapitalLetters("I.");

        Student dan = new Student();
        dan.setUsername("adir2033");
        dan.setEncryptedPassword(BCrypt.hashpw("dan", BCrypt.gensalt()));
        dan.setLastName("Ailenei");
        dan.setFirstName("Dan Gabriel");
        dan.setEmail("cnir2083@scs.ubbcluj.ro");
        dan.setRegistrationNr(12033);
        dan.setFathersCapitalLetters("N.");

        Student alba = new Student();
        alba.setUsername("agir2034");
        alba.setEncryptedPassword(BCrypt.hashpw("alba", BCrypt.gensalt()));
        alba.setLastName("Alba");
        alba.setFirstName("Grigore Cătălin");
        alba.setEmail("cnir2083@scs.ubbcluj.ro");
        alba.setRegistrationNr(12034);
        alba.setFathersCapitalLetters("G.I.");

        Student oana = new Student();
        oana.setUsername("aoir2035");
        oana.setEncryptedPassword(BCrypt.hashpw("oana", BCrypt.gensalt()));
        oana.setLastName("Albu");
        oana.setFirstName("Oana");
        oana.setEmail("aoir2035@scs.ubbcluj.ro");
        oana.setRegistrationNr(22035);
        oana.setFathersCapitalLetters("A.");

        Student stef = new Student();
        stef.setUsername("asir2036");
        stef.setEncryptedPassword(BCrypt.hashpw("stef", BCrypt.gensalt()));
        stef.setLastName("Andraș");
        stef.setFirstName("Ștefan Daniel");
        stef.setEmail("cnir2083@scs.ubbcluj.ro");
        stef.setRegistrationNr(12036);
        stef.setFathersCapitalLetters("D.S.");

        Student antal = new Student();
        antal.setUsername("aair2038");
        antal.setEncryptedPassword(BCrypt.hashpw("antal", BCrypt.gensalt()));
        antal.setLastName("Antal");
        antal.setFirstName("Alexandru");
        antal.setEmail("cnir2083@scs.ubbcluj.ro");
        antal.setRegistrationNr(12038);
        antal.setFathersCapitalLetters("I.");

        Student mortu = new Student();
        mortu.setUsername("aair2039");
        mortu.setEncryptedPassword(BCrypt.hashpw("mortu", BCrypt.gensalt()));
        mortu.setLastName("Ardelean");
        mortu.setFirstName("Alexandru Andrei");
        mortu.setEmail("cnir2083@scs.ubbcluj.ro");
        mortu.setRegistrationNr(12039);
        mortu.setFathersCapitalLetters("D.H.");

        Student baies = new Student();
        baies.setUsername("bair2040");
        baies.setEncryptedPassword(BCrypt.hashpw("baies", BCrypt.gensalt()));
        baies.setLastName("Băieș");
        baies.setFirstName("Alex Laurențiu");
        baies.setEmail("cnir2083.scs.ubbcluj.ro");
        baies.setRegistrationNr(12040);
        baies.setFathersCapitalLetters("T.M.");

        Student balan = new Student();
        balan.setUsername("bpir2041");
        balan.setEncryptedPassword(BCrypt.hashpw("balan", BCrypt.gensalt()));
        balan.setLastName("Bălan");
        balan.setFirstName("Paul Cătălin");
        balan.setEmail("cnir2083@scs.ubccluj.ro");
        balan.setRegistrationNr(12041);
        balan.setFathersCapitalLetters("A.");

        Student iulia = new Student();
        iulia.setUsername("biir2042");
        iulia.setEncryptedPassword(BCrypt.hashpw("iulia", BCrypt.gensalt()));
        iulia.setLastName("Bărăian");
        iulia.setFirstName("Iulia Maria");
        iulia.setEmail("cnir2083@scs.ubbcluj.ro");
        iulia.setRegistrationNr(12042);
        iulia.setFathersCapitalLetters("A.");

        Student denis = new Student();
        denis.setUsername("bdir2043");
        denis.setEncryptedPassword(BCrypt.hashpw("denis", BCrypt.gensalt()));
        denis.setLastName("Bărnuțiu");
        denis.setFirstName("Denis Vasile");
        denis.setEmail("cnir2083@scs.ubbcluj.ro");
        denis.setRegistrationNr(12043);
        denis.setFathersCapitalLetters("M.R.");

        Student dragos = new Student();
        dragos.setUsername("bdir2045");
        dragos.setEncryptedPassword(BCrypt.hashpw("dragos", BCrypt.gensalt()));
        dragos.setLastName("Berlea");
        dragos.setFirstName("Dragoș Teodor");
        dragos.setEmail("bdir2045");
        dragos.setRegistrationNr(12045);
        dragos.setFathersCapitalLetters("N.");

        Student vlad = new Student();
        vlad.setUsername("bvir2046");
        vlad.setEncryptedPassword(BCrypt.hashpw("vlad", BCrypt.gensalt()));
        vlad.setLastName("Bîrsan");
        vlad.setFirstName("Vlad Ioan");
        vlad.setEmail("bvir2046@scs.ubbcluj.ro");
        vlad.setRegistrationNr(12046);
        vlad.setFathersCapitalLetters("A.");

        Student mire = new Student();
        mire.setUsername("bmir2047");
        mire.setEncryptedPassword(BCrypt.hashpw("mire", BCrypt.gensalt()));
        mire.setLastName("Bocșa");
        mire.setFirstName("Mirela Alexandra");
        mire.setEmail("bmir2047@scs.ubbcluj.ro");
        mire.setRegistrationNr(22047);
        mire.setFathersCapitalLetters("G.");

        Student bodiu = new Student();
        bodiu.setUsername("bcir2425");
        bodiu.setEncryptedPassword(BCrypt.hashpw("bodiu", BCrypt.gensalt()));
        bodiu.setLastName("Bodiu");
        bodiu.setFirstName("Cătălin");
        bodiu.setEmail("cnir2083@scs.ubbcluj.ro");
        bodiu.setRegistrationNr(12425);
        bodiu.setFathersCapitalLetters("D");

        Student boicu = new Student();
        boicu.setUsername("bair2049");
        boicu.setEncryptedPassword(BCrypt.hashpw("gabi", BCrypt.gensalt()));
        boicu.setLastName("Boicu");
        boicu.setFirstName("Alexandra");
        bodiu.setEmail("cnir2083@scs.ubbcluj.ro");
        bodiu.setRegistrationNr(12049);
        bodiu.setFathersCapitalLetters("M.");

        Student gabi = new Student();
        gabi.setUsername("biir2052");
        gabi.setEncryptedPassword(BCrypt.hashpw("gabi", BCrypt.gensalt()));
        gabi.setLastName("Borșan");
        gabi.setFirstName("Ioan Gabriel");
        gabi.setEmail("cnir2083@scs.ubbcluj.ro");
        gabi.setRegistrationNr(12052);
        gabi.setFathersCapitalLetters("I.L.");

        Student bosinta = new Student();
        bosinta.setUsername("bbir2053");
        bosinta.setEncryptedPassword(BCrypt.hashpw("bosinta", BCrypt.gensalt()));
        bosinta.setLastName("Boșîntă");
        bosinta.setFirstName("Bogdan Viorel");
        bosinta.setEmail("cnir2083@scs.ubbcluj.ro");
        bosinta.setRegistrationNr(12053);
        bosinta.setFathersCapitalLetters("V.V.");

        Student ana = new Student();
        ana.setUsername("bair2054");
        ana.setEncryptedPassword(BCrypt.hashpw("ana", BCrypt.gensalt()));
        ana.setLastName("Boșutar");
        ana.setFirstName("Ana Maria");
        ana.setEmail("cnir2083@scs.ubbcluj.ro");
        ana.setRegistrationNr(22054);
        ana.setFathersCapitalLetters("T.N.");

        Student delia = new Student();
        delia.setUsername("bcir2055");
        delia.setEncryptedPassword(BCrypt.hashpw("delia", BCrypt.gensalt()));
        delia.setLastName("Brașovean");
        delia.setFirstName("Carmen Delia");
        delia.setEmail("bcir2055");
        delia.setRegistrationNr(22055);
        delia.setFathersCapitalLetters("V.");

        Student florin = new Student();
        florin.setUsername("ctir2077");
        florin.setEncryptedPassword(BCrypt.hashpw("florin", BCrypt.gensalt()));
        florin.setLastName("Condrovici");
        florin.setFirstName("Teodor Florin");
        florin.setEmail("cnir2083@scs.ubbcluj.ro");
        florin.setRegistrationNr(12077);
        florin.setFathersCapitalLetters("V.D.");

        Student norberth = new Student();
        norberth.setUsername("cnir2083");
        norberth.setEncryptedPassword(BCrypt.hashpw("norberth", BCrypt.gensalt()));
        norberth.setLastName("Csorba");
        norberth.setFirstName("Norberth");
        norberth.setEmail("cnir2083@scs.ubbcluj.ro");
        norberth.setRegistrationNr(12083);
        norberth.setFathersCapitalLetters("Ș.");

        Student paul = new Student();
        paul.setUsername("cpir2084");
        paul.setEncryptedPassword(BCrypt.hashpw("paul", BCrypt.gensalt()));
        paul.setLastName("Cuș");
        paul.setFirstName("Paul Gabriel");
        paul.setEmail("cpir2084@scs.ubbcluj.ro");
        paul.setRegistrationNr(12084);
        paul.setFathersCapitalLetters("P.");

        Student mark = new Student();
        mark.setUsername("cmir2085");
        mark.setEncryptedPassword(BCrypt.hashpw("mark", BCrypt.gensalt()));
        mark.setLastName("Czeli");
        mark.setFirstName("Mark Dominik");
        mark.setEmail("cmir2085@scs.ubbcluj.ro");
        mark.setRegistrationNr(12085);
        mark.setFathersCapitalLetters("I.A.");

        Student darius = new Student();
        darius.setUsername("gdir2106");
        darius.setEncryptedPassword(BCrypt.hashpw("darius", BCrypt.gensalt()));
        darius.setLastName("Galanton");
        darius.setFirstName("Darius");
        darius.setEmail("cnir2083@scs.ubbcluj.ro");
        darius.setRegistrationNr(12106);
        darius.setFathersCapitalLetters("C.");

        Student andra = new Student();
        andra.setUsername("rair2184");
        andra.setEncryptedPassword(BCrypt.hashpw("andra", BCrypt.gensalt()));
        andra.setLastName("Runcan");
        andra.setFirstName("Andra");
        andra.setEmail("cnir2083@scs.ubbcluj.ro");
        andra.setRegistrationNr(22184);
        andra.setFathersCapitalLetters("M.M.");

        Student mihnea = new Student();
        mihnea.setUsername("tmir2199");
        mihnea.setEncryptedPassword(BCrypt.hashpw("taranu", BCrypt.gensalt()));
        mihnea.setLastName("Țăranu");
        mihnea.setFirstName("Mihnea Andrei");
        mihnea.setEmail("cnir2083@scs.ubbcluj.ro");
        mihnea.setRegistrationNr(12199);
        mihnea.setFathersCapitalLetters("P.L.");

        List<Student> students = new ArrayList<Student>();
        students.add(mada);
        students.add(dan);
        students.add(alba);
        students.add(oana);
        students.add(stef);
        students.add(antal);
        students.add(mortu);
        students.add(baies);
        students.add(balan);
        students.add(iulia);
        students.add(denis);
        students.add(dragos);
        students.add(vlad);
        students.add(mire);
        students.add(bodiu);
        students.add(boicu);
        students.add(gabi);
        students.add(bosinta);
        students.add(ana);
        students.add(delia);
        students.add(florin);
        students.add(norberth);
        students.add(paul);
        students.add(mark);
        students.add(darius);
        students.add(andra);
        students.add(mihnea);

        return students;
    }



    public static void main(String[] args){

    }
}
