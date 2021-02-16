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
package jobportal.models.offer_data_models.codebooks;

import jobportal.models.base.CodedEntity;
import jobportal.models.offer_data_models.Offer;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "workships")
public class Workship extends CodedEntity {

    @ManyToMany(mappedBy = "workships")
    private Set<Offer> offers = new HashSet<>();

    public Workship() {
    }

    public Workship(Set<Offer> offers) {
        this.offers = offers;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }
}