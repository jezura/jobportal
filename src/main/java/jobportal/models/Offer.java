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
package jobportal.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "offers")
public class Offer extends LongIdEntity {

    @NotNull
    @Column(name = "insertion_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate insertionDate;

    @NotNull
    @Column(name = "edit_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate editDate;

    @Column(name = "expire_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @Column(name = "work_start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workStartDate;

    @Column(name = "work_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workEndDate;

    @NotNull
    @Size(min = 1, max = 6000, message = "offerText must be between 1 and 3000 characters long")
    @Column(name = "offer_text")
    private String offerText;

    @NotNull
    @Size(min = 1, max = 300, message = "title must be between 1 and 300 characters long")
    @Column(name = "title")
    private String title;

    @Column(name = "monthly_rate_from")
    private Double monthlyRateFrom;

    @Column(name = "monthly_rate_to")
    private Double monthlyRateTo;

    @Column(name = "salary_type")
    private String salaryType;

    @Column(name = "number_of_jobs")
    private Integer numberOfJobs;

    @ManyToOne
    @NotNull(message = "Choose profession")
    @JoinColumn(name = "profession_id")
    private Profession profession;

    @ManyToOne
    @NotNull(message = "Choose workshift type")
    @JoinColumn(name = "workshift_id")
    private Workshift workshift;

    @ManyToOne
    @NotNull(message = "Choose employer")
    @JoinColumn(name = "employer_ico")
    private Employer employer;

    @ManyToOne
    @JoinColumn(name = "education_id")
    private Education education;

    @ManyToMany
    @JoinTable(name = "offer_workship",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "workship_id"))
    private Set<Workship> workships;

    @ManyToMany
    @JoinTable(name = "offer_suitability",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "suitability_id"))
    private Set<Suitability> suitabilities;

    @ManyToOne
    @NotNull(message = "Choose type of work place")
    @JoinColumn(name = "placetype_id")
    private PlaceType placeType;

    @OneToOne
    @NotNull(message = "Choose work place")
    @JoinColumn(name = "workplace_id")
    private WorkPlace workPlace;

    @OneToOne
    @JoinColumn(name = "firstcontact_id")
    private FirstContact firstContact;

    public Offer() {
    }

    public Offer(@NotNull LocalDate insertionDate,
                 @NotNull LocalDate editDate,
                 LocalDate expireDate,
                 LocalDate workStartDate,
                 LocalDate workEndDate,
                 @NotNull @Size(min = 1, max = 6000, message = "offerText must be between 1 and 3000 characters long") String offerText,
                 @NotNull @Size(min = 1, max = 300, message = "title must be between 1 and 3000 characters long") String title,
                 Double monthlyRateFrom,
                 Double monthlyRateTo,
                 String salaryType,
                 Integer numberOfJobs,
                 @NotNull(message = "Choose profession") Profession profession,
                 @NotNull(message = "Choose workshift type") Workshift workshift,
                 @NotNull(message = "Choose employer") Employer employer,
                 Education education,
                 Set<Workship> workships,
                 Set <Suitability> suitabilities,
                 @NotNull (message = "Choose type of work place") PlaceType placeType,
                 @NotNull (message = "Choose work place") WorkPlace workPlace,
                 FirstContact firstContact) {
        this.insertionDate = insertionDate;
        this.editDate = editDate;
        this.expireDate = expireDate;
        this.workStartDate = workStartDate;
        this.workEndDate = workEndDate;
        this.offerText = offerText;
        this.title = title;
        this.monthlyRateFrom = monthlyRateFrom;
        this.monthlyRateTo = monthlyRateTo;
        this.salaryType = salaryType;
        this.numberOfJobs = numberOfJobs;
        this.profession = profession;
        this.workshift = workshift;
        this.employer = employer;
        this.education = education;
        this.workships = workships;
        this.suitabilities = suitabilities;
        this.placeType = placeType;
        this.workPlace = workPlace;
        this.firstContact = firstContact;
    }

    public LocalDate getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(LocalDate insertionDate) {
        this.insertionDate = insertionDate;
    }

    public LocalDate getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDate editDate) {
        this.editDate = editDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public LocalDate getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(LocalDate workStartDate) {
        this.workStartDate = workStartDate;
    }

    public LocalDate getWorkEndDate() {
        return workEndDate;
    }

    public void setWorkEndDate(LocalDate workEndDate) {
        this.workEndDate = workEndDate;
    }

    public String getOfferText() {
        return offerText;
    }

    public void setOfferText(String offerText) {
        this.offerText = offerText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getMonthlyRateFrom() {
        return monthlyRateFrom;
    }

    public void setMonthlyRateFrom(Double monthlyRateFrom) {
        this.monthlyRateFrom = monthlyRateFrom;
    }

    public Double getMonthlyRateTo() {
        return monthlyRateTo;
    }

    public void setMonthlyRateTo(Double monthlyRateTo) {
        this.monthlyRateTo = monthlyRateTo;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public Integer getNumberOfJobs() {
        return numberOfJobs;
    }

    public void setNumberOfJobs(Integer numberOfJobs) {
        this.numberOfJobs = numberOfJobs;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Workshift getWorkshift() {
        return workshift;
    }

    public void setWorkshift(Workshift workshift) {
        this.workshift = workshift;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Set<Workship> getWorkships() {
        return workships;
    }

    public void setWorkships(Set<Workship> workships) {
        this.workships = workships;
    }

    public Set<Suitability> getSuitabilities() {
        return suitabilities;
    }

    public void setSuitabilities(Set<Suitability> suitabilities) {
        this.suitabilities = suitabilities;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }

    public WorkPlace getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(WorkPlace workPlace) {
        this.workPlace = workPlace;
    }

    public FirstContact getFirstContact() {
        return firstContact;
    }

    public void setFirstContact(FirstContact firstContact) {
        this.firstContact = firstContact;
    }
}