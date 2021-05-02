package jobportal.models.internal_models.data_entites;

import jobportal.models.internal_models.data_entites.user.RegisteredUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

/**
 * Model class for FieldsRelevancy entity.
 * An instance represents row in fields_relevancies table.
 */
@Entity
@Table(name = "fields_relevancies")
public class FieldsRelevancy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "Choose relevancy for job general field1")
    @Column(name = "relevancy_field1")
    private float relevancyField1;

    @NotNull(message = "Choose relevancy for job general field2")
    @Column(name = "relevancy_field2")
    private float relevancyField2;

    @NotNull(message = "Choose relevancy for job general field3")
    @Column(name = "relevancy_field3")
    private float relevancyField3;

    @NotNull(message = "Choose relevancy for job general field4")
    @Column(name = "relevancy_field4")
    private float relevancyField4;

    @NotNull(message = "Choose relevancy for job general field5")
    @Column(name = "relevancy_field5")
    private float relevancyField5;

    @NotNull(message = "Choose relevancy for job general field6")
    @Column(name = "relevancy_field6")
    private float relevancyField6;

    @NotNull(message = "Choose relevancy for job general field7")
    @Column(name = "relevancy_field7")
    private float relevancyField7;

    @NotNull(message = "Choose relevancy for job general field8")
    @Column(name = "relevancy_field8")
    private float relevancyField8;

    @NotNull(message = "Choose relevancy for job general field9")
    @Column(name = "relevancy_field9")
    private float relevancyField9;

    @NotNull(message = "Choose relevancy for job general field10")
    @Column(name = "relevancy_field10")
    private float relevancyField10;

    @NotNull(message = "Choose relevancy for job general field11")
    @Column(name = "relevancy_field11")
    private float relevancyField11;

    @NotNull(message = "Choose relevancy for job general field12")
    @Column(name = "relevancy_field12")
    private float relevancyField12;

    @NotNull(message = "Choose relevancy for job general field13")
    @Column(name = "relevancy_field13")
    private float relevancyField13;

    @NotNull(message = "Choose relevancy for job general field14")
    @Column(name = "relevancy_field14")
    private float relevancyField14;

    @NotNull(message = "Choose relevancy for job general field15")
    @Column(name = "relevancy_field15")
    private float relevancyField15;


    public FieldsRelevancy() {
    }

    public FieldsRelevancy(int id, @NotNull(message = "Choose relevancy for job general field1") float relevancyField1, @NotNull(message = "Choose relevancy for job general field2") float relevancyField2, @NotNull(message = "Choose relevancy for job general field3") float relevancyField3, @NotNull(message = "Choose relevancy for job general field4") float relevancyField4, @NotNull(message = "Choose relevancy for job general field5") float relevancyField5, @NotNull(message = "Choose relevancy for job general field6") float relevancyField6, @NotNull(message = "Choose relevancy for job general field7") float relevancyField7, @NotNull(message = "Choose relevancy for job general field8") float relevancyField8, @NotNull(message = "Choose relevancy for job general field9") float relevancyField9, @NotNull(message = "Choose relevancy for job general field10") float relevancyField10, @NotNull(message = "Choose relevancy for job general field11") float relevancyField11, @NotNull(message = "Choose relevancy for job general field12") float relevancyField12, @NotNull(message = "Choose relevancy for job general field13") float relevancyField13, @NotNull(message = "Choose relevancy for job general field14") float relevancyField14, @NotNull(message = "Choose relevancy for job general field15") float relevancyField15) {
        this.id = id;
        this.relevancyField1 = relevancyField1;
        this.relevancyField2 = relevancyField2;
        this.relevancyField3 = relevancyField3;
        this.relevancyField4 = relevancyField4;
        this.relevancyField5 = relevancyField5;
        this.relevancyField6 = relevancyField6;
        this.relevancyField7 = relevancyField7;
        this.relevancyField8 = relevancyField8;
        this.relevancyField9 = relevancyField9;
        this.relevancyField10 = relevancyField10;
        this.relevancyField11 = relevancyField11;
        this.relevancyField12 = relevancyField12;
        this.relevancyField13 = relevancyField13;
        this.relevancyField14 = relevancyField14;
        this.relevancyField15 = relevancyField15;
    }

    /**
     * Method selects all necessary prediction information from given RegisteredUser instance attributes
     * (gender, maxEduLvl, eduGeneralField), estimates the users age, constructs a URL for the prediction service query,
     * and then stores all predicted relevance scores from response in a local float variables (so that it can be stored
     * in a database).
     *
     * @param registeredUser - RegisteredUser instance
     * @throws IOException - exception when prediction service is not available
     */
    public void setRelevancies(RegisteredUser registeredUser) throws IOException {
        int gender = 0;
        if (registeredUser.getGender().equals("zena")) {
            gender = 1;
        }

        int estimatedAge = (Calendar.getInstance().get(Calendar.YEAR)) - (registeredUser.getBirthYear());
        System.out.println("Calculated age is: " + estimatedAge);

        // Constructing of the query URL to ask neural network to predict relevance scores
        URL url = new URL("https://fieldpredictor.herokuapp.com/get-jobfields-relevance-scores?" +
                "gender=" + gender + "&age=" + estimatedAge + "&edu_lvl="
                + registeredUser.getEduLevel().getAnnCode() +
                "&edu_field=" + registeredUser.getEduGeneralField().getAnnCode());

        // Get the input stream through URL Connection
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = br.readLine();
        String[] strPredictions = line.split("<br>");
        is.close();

        setRelevancyField1(Float.valueOf(strPredictions[0]));
        setRelevancyField2(Float.valueOf(strPredictions[1]));
        setRelevancyField3(Float.valueOf(strPredictions[2]));
        setRelevancyField4(Float.valueOf(strPredictions[3]));
        setRelevancyField5(Float.valueOf(strPredictions[4]));
        setRelevancyField6(Float.valueOf(strPredictions[5]));
        setRelevancyField7(Float.valueOf(strPredictions[6]));
        setRelevancyField8(Float.valueOf(strPredictions[7]));
        setRelevancyField9(Float.valueOf(strPredictions[8]));
        setRelevancyField10(Float.valueOf(strPredictions[9]));
        setRelevancyField11(Float.valueOf(strPredictions[10]));
        setRelevancyField12(Float.valueOf(strPredictions[11]));
        setRelevancyField13(Float.valueOf(strPredictions[12]));
        setRelevancyField14(Float.valueOf(strPredictions[13]));
        setRelevancyField15(Float.valueOf(strPredictions[14]));
    }

    public float[] getRelevanceScoresArray() {
        float[] relevanceScores = new float[15];
        relevanceScores[0] = getRelevancyField1();
        relevanceScores[1] = getRelevancyField2();
        relevanceScores[2] = getRelevancyField3();
        relevanceScores[3] = getRelevancyField4();
        relevanceScores[4] = getRelevancyField5();
        relevanceScores[5] = getRelevancyField6();
        relevanceScores[6] = getRelevancyField7();
        relevanceScores[7] = getRelevancyField8();
        relevanceScores[8] = getRelevancyField9();
        relevanceScores[9] = getRelevancyField10();
        relevanceScores[10] = getRelevancyField11();
        relevanceScores[11] = getRelevancyField12();
        relevanceScores[12] = getRelevancyField13();
        relevanceScores[13] = getRelevancyField14();
        relevanceScores[14] = getRelevancyField15();
        return relevanceScores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRelevancyField1() {
        return relevancyField1;
    }

    public void setRelevancyField1(float relevancyField1) {
        this.relevancyField1 = relevancyField1;
    }

    public float getRelevancyField2() {
        return relevancyField2;
    }

    public void setRelevancyField2(float relevancyField2) {
        this.relevancyField2 = relevancyField2;
    }

    public float getRelevancyField3() {
        return relevancyField3;
    }

    public void setRelevancyField3(float relevancyField3) {
        this.relevancyField3 = relevancyField3;
    }

    public float getRelevancyField4() {
        return relevancyField4;
    }

    public void setRelevancyField4(float relevancyField4) {
        this.relevancyField4 = relevancyField4;
    }

    public float getRelevancyField5() {
        return relevancyField5;
    }

    public void setRelevancyField5(float relevancyField5) {
        this.relevancyField5 = relevancyField5;
    }

    public float getRelevancyField6() {
        return relevancyField6;
    }

    public void setRelevancyField6(float relevancyField6) {
        this.relevancyField6 = relevancyField6;
    }

    public float getRelevancyField7() {
        return relevancyField7;
    }

    public void setRelevancyField7(float relevancyField7) {
        this.relevancyField7 = relevancyField7;
    }

    public float getRelevancyField8() {
        return relevancyField8;
    }

    public void setRelevancyField8(float relevancyField8) {
        this.relevancyField8 = relevancyField8;
    }

    public float getRelevancyField9() {
        return relevancyField9;
    }

    public void setRelevancyField9(float relevancyField9) {
        this.relevancyField9 = relevancyField9;
    }

    public float getRelevancyField10() {
        return relevancyField10;
    }

    public void setRelevancyField10(float relevancyField10) {
        this.relevancyField10 = relevancyField10;
    }

    public float getRelevancyField11() {
        return relevancyField11;
    }

    public void setRelevancyField11(float relevancyField11) {
        this.relevancyField11 = relevancyField11;
    }

    public float getRelevancyField12() {
        return relevancyField12;
    }

    public void setRelevancyField12(float relevancyField12) {
        this.relevancyField12 = relevancyField12;
    }

    public float getRelevancyField13() {
        return relevancyField13;
    }

    public void setRelevancyField13(float relevancyField13) {
        this.relevancyField13 = relevancyField13;
    }

    public float getRelevancyField14() {
        return relevancyField14;
    }

    public void setRelevancyField14(float relevancyField14) {
        this.relevancyField14 = relevancyField14;
    }

    public float getRelevancyField15() {
        return relevancyField15;
    }

    public void setRelevancyField15(float relevancyField15) {
        this.relevancyField15 = relevancyField15;
    }
}