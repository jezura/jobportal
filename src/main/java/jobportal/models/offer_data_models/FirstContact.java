package jobportal.models.offer_data_models;

import javax.persistence.*;

/**
 * Model class for FirstContact entity.
 * An instance represents row in first_contacts table.
 */
@Entity
@Table(name = "first_contacts")
public class FirstContact {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "contact_place")
    private String contactPlace;

    @Column(name = "contact_address")
    private String contactAddress;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_mail")
    private String contactMail;

    @Column(name = "contact_person_name")
    private String contactPersonName;

    @Column(name = "contact_person_surname")
    private String contactPersonSurname;

    @Column(name = "title_before")
    private String titleBefore;

    @Column(name = "title_after")
    private String titleAfter;

    @Column(name = "company_position")
    private String companyPosition;

    public FirstContact() {
    }

    public FirstContact(int id, String contactPlace, String contactAddress, String contactPhone, String contactMail, String contactPersonName, String contactPersonSurname, String titleBefore, String titleAfter, String companyPosition) {
        this.id = id;
        this.contactPlace = contactPlace;
        this.contactAddress = contactAddress;
        this.contactPhone = contactPhone;
        this.contactMail = contactMail;
        this.contactPersonName = contactPersonName;
        this.contactPersonSurname = contactPersonSurname;
        this.titleBefore = titleBefore;
        this.titleAfter = titleAfter;
        this.companyPosition = companyPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactPlace() {
        return contactPlace;
    }

    public void setContactPlace(String contactPlace) {
        this.contactPlace = contactPlace;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonSurname() {
        return contactPersonSurname;
    }

    public void setContactPersonSurname(String contactPersonSurname) {
        this.contactPersonSurname = contactPersonSurname;
    }

    public String getTitleBefore() {
        return titleBefore;
    }

    public void setTitleBefore(String titleBefore) {
        this.titleBefore = titleBefore;
    }

    public String getTitleAfter() {
        return titleAfter;
    }

    public void setTitleAfter(String titleAfter) {
        this.titleAfter = titleAfter;
    }

    public String getCompanyPosition() {
        return companyPosition;
    }

    public void setCompanyPosition(String companyPosition) {
        this.companyPosition = companyPosition;
    }
}