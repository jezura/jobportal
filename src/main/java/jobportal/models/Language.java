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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "languages")
public class Language extends CodedEntity {

    /*@OneToMany(
            mappedBy = "language",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<OfferLanguage> offers;*/

    @OneToMany(mappedBy = "language")
    private Set<OfferLanguage> offerLanguages = new HashSet<OfferLanguage>();

    public Language() {}

    public Language(Set<OfferLanguage> offerLanguages) {
        this.offerLanguages = offerLanguages;
    }

    public Set<OfferLanguage> getOfferLanguages() {
        return offerLanguages;
    }

    public void setOfferLanguages(Set<OfferLanguage> offerLanguages) {
        this.offerLanguages = offerLanguages;
    }

    public void addOfferLanguage(OfferLanguage offerLanguage) {
        this.offerLanguages.add(offerLanguage);
    }
}