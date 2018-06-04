package org.extro5.server.dao;

import com.pullenti.morph.*;
import com.pullenti.ner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JDBCSelectionEntityRepository implements SelectionEntityRepository {

    private JdbcTemplate jdbcTemplate;

    private String phoneNumber;

    private String email;

    private String gender;

    private String firstName;

    private String lastName;

    private String fatherName;

    private static final String PHONE = "PHONE";

    private static final String NUMBER = "NUMBER";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private String selectionEntityAndAddUser(String firstName, String lastName, String farherName, String gender, String email, String phoneNumber) {
        String sql = "SELECT id_user FROM user WHERE first_name = ? AND last_name =? AND father_name = ?";
        List<String> listUser = jdbcTemplate.queryForList(sql, new Object[]{firstName, lastName, farherName}, String.class);

        if (listUser.size() == 0) {
            System.out.println("==null?");
            this.jdbcTemplate.update("INSERT INTO user(first_name, last_name, father_name, email, gender, phone) VALUE (?,?,?,?,?,?)", firstName, lastName, farherName, email, gender, phoneNumber);
            return jdbcTemplate.queryForList(sql, new Object[]{firstName, lastName, farherName}, String.class).get(0);
        }
        return "";

    }

    @Override
    public String getEntity(String text) throws Exception {
        com.pullenti.n2j.Stopwatch sw = com.pullenti.n2j.Utils.startNewStopwatch();
        System.out.print("Initializing ... ");

        com.pullenti.ner.Sdk.initialize(com.pullenti.morph.MorphLang
                .ooBitor(com.pullenti.morph.MorphLang.RU,
                        com.pullenti.morph.MorphLang.EN));

        sw.stop();
        // System.out.println("OK (by " + ((int) sw.getElapsedMilliseconds()) + " ms), version " + com.pullenti.ner.ProcessorService.getVersion());
        // String txt = "Сабанов Владислав 1995 года рождения. Живу в Москве. Моя почта cool.extro5@yandex.ru. Мой телефон 89172550006";
        System.out.println("Text: " + text);

        try (Processor proc = ProcessorService.createProcessor()) {
            AnalysisResult ar = null;
            if (proc != null) {
                ar = proc.process(new SourceOfAnalysis(text)
                        , null, new MorphLang(null));
            }

            System.out.println("\r\n==========================================\r\nEntities: ");
            assert ar != null;


            for (Referent e : ar.getEntities()) {
                for (Slot s : e.getSlots()) {
                    if (e.getTypeName().equals(PHONE)) {
                        if (s.getTypeName().equals(NUMBER)) {
                            phoneNumber = s.getValue().toString();
                        }
                    }


                    if (e.getTypeName().equals("URI")) {
                        if (s.getTypeName().equals("VALUE")) {
                            //  System.out.println("MyPhone" + s.getValue());
                            email = s.getValue().toString();
                            System.out.println("MyEmail" + email);
                        }
                    }

                    if (e.getTypeName().equals("PERSON")) {
                        if (s.getTypeName().equals("SEX")) {
                            //  System.out.println("MyPhone" + s.getValue());
                            gender = s.getValue().toString();
                            System.out.println("MyGender" + gender);
                        }
                    }

                    if (e.getTypeName().equals("PERSON")) {
                        if (s.getTypeName().equals("LASTNAME")) {
                            //  System.out.println("MyPhone" + s.getValue());
                            lastName = s.getValue().toString();
                            System.out.println("MyLastName" + lastName);
                        }
                    }

                    if (e.getTypeName().equals("PERSON")) {
                        if (s.getTypeName().equals("FIRSTNAME")) {
                            //  System.out.println("MyPhone" + s.getValue());
                            firstName = s.getValue().toString();
                            System.out.println("MyFirstName" + firstName);
                        }
                    }

                    if (e.getTypeName().equals("PERSON")) {
                        if (s.getTypeName().equals("MIDDLENAME")) {
                            //  System.out.println("MyPhone" + s.getValue());
                            fatherName = s.getValue().toString();
                            System.out.println("MyFatherName" + fatherName);
                        }
                    }
                }
            }
        }
        System.out.println("Over!");
        return selectionEntityAndAddUser(firstName, lastName, fatherName, gender, email, phoneNumber);
    }
}