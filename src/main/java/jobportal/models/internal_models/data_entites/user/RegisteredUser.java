/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jobportal.models.internal_models.data_entites.user;

import jobportal.models.internal_models.codebooks.EduGeneralField;
import jobportal.models.internal_models.codebooks.EduLevel;
import jobportal.models.internal_models.data_entites.FieldsRelevancy;
import jobportal.models.offer_data_models.codebooks.Region;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Trida objektu RegisteredUser
 */

@Entity
@Table(name = "registered_users")
public class RegisteredUser extends Person {

    @Column(name = "register_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerDate;

    @NotNull(message = "Pohlaví musí být zadáno")
    @Size(min = 3, message = "Pohlaví musí být zadáno")
    @Column(name = "gender")
    private String gender;

    @NotNull(message = "Rok narození musí být zadán")
    @Min(value=1940, message="Rok narození musí být roven nebo větší než rok 1940")
    @Max(value=2006, message="Rok narození musí být menší nebo roven než rok 2006")
    @Column(name = "birth_year")
    private int birthYear;

    @ManyToOne
    @NotNull(message = "Stupeň nejvyššího dosaženého vzdělání musí být vybrán")
    @JoinColumn(name = "edu_level_id")
    private EduLevel eduLevel;

    @ManyToOne
    @NotNull(message = "Obecný obor nejvyššího dosaženého vzdělání musí být vybrán")
    @JoinColumn(name = "edu_general_field_id")
    private EduGeneralField eduGeneralField;

    @ManyToOne
    @NotNull(message = "Kraj bydliště musí být vybrán")
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "fields_relevancy_id")
    private FieldsRelevancy fieldsRelevancy;


    public RegisteredUser() {}

    public RegisteredUser(LocalDate registerDate, @NotNull(message = "Pohlaví musí být zadáno") String gender, @NotNull(message = "Rok narození musí být zadán") int birthYear, @NotNull(message = "Stupeň nejvyššího dosaženého vzdělání musí být vybrán") EduLevel eduLevel, @NotNull(message = "Obecný obor nejvyššího dosaženého vzdělání musí být vybrán") EduGeneralField eduGeneralField, @NotNull(message = "Kraj bydliště musí být vybrán") Region region, @NotNull(message = "Musí být přiřazen odpovídající objekt uchovávající relevance pracovních oborů") FieldsRelevancy fieldsRelevancy) {
        this.registerDate = registerDate;
        this.gender = gender;
        this.birthYear = birthYear;
        this.eduLevel = eduLevel;
        this.eduGeneralField = eduGeneralField;
        this.region = region;
        this.fieldsRelevancy = fieldsRelevancy;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public EduLevel getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(EduLevel eduLevel) {
        this.eduLevel = eduLevel;
    }

    public EduGeneralField getEduGeneralField() {
        return eduGeneralField;
    }

    public void setEduGeneralField(EduGeneralField eduGeneralField) {
        this.eduGeneralField = eduGeneralField;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public FieldsRelevancy getFieldsRelevancy() {
        return fieldsRelevancy;
    }

    public void setFieldsRelevancy(FieldsRelevancy fieldsRelevancy) {
        this.fieldsRelevancy = fieldsRelevancy;
    }
}