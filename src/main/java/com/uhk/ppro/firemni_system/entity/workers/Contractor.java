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
package com.uhk.ppro.firemni_system.entity.workers;

import com.uhk.ppro.firemni_system.entity.Person;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.administraceproject.visit.Visit;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

/**
 * Třída objektu Contractor
 */

@Entity
@Table(name = "contractors")
public class Contractor extends Person {

    @Column(name = "hire_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @Column(name = "address")
    @NotEmpty
    private String address;

    @Column(name = "city")
    @NotEmpty
    private String city;

    @Column(name = "telephone")
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String telephone;

    @ManyToOne
    @JoinColumn(name = "validator_id")
    private Validator mentor;

    @ManyToOne
    @JoinColumn(name = "swimlade_id")
    private SwimlaneType swimlane;

    @Transient

    // constructors

    public Contractor() {
    }

    public Contractor(LocalDate hireDate, @NotEmpty String address, @NotEmpty String city, @NotEmpty @Digits(fraction = 0, integer = 10) String telephone, Validator mentor, SwimlaneType swimlane) {
        this.hireDate = hireDate;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.mentor = mentor;
        this.swimlane = swimlane;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Validator getMentor() {
        return mentor;
    }

    public void setMentor(Validator mentor) {
        this.mentor = mentor;
    }

    public SwimlaneType getSwimlane() {
        return swimlane;
    }

    public void setSwimlane(SwimlaneType swimlane) {
        this.swimlane = swimlane;
    }
}
