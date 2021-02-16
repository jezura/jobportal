package jobportal.models.internal_models.cv_support;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "fields_relevancies")
public class FieldsRelevancy {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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