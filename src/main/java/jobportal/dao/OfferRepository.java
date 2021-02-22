package jobportal.dao;

import jobportal.models.offer_data_models.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface OfferRepository extends PagingAndSortingRepository<Offer, Long>
{
    Offer findOfferById(Long id);

    Page<Offer> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query(
            value = "SELECT * FROM offers o WHERE o.profession_id IN (SELECT pf.profession_id FROM profession_field pf WHERE pf.field_id=?2) AND o.title LIKE %?1%",
            nativeQuery = true)
    Page<Offer> findByTitleLikeAndFieldIdPageable(String title, String fieldId, Pageable pageable);

    @Query(
            value = "SELECT * FROM offers o WHERE o.profession_id IN (SELECT pf.profession_id FROM profession_field pf WHERE pf.field_id=?1)",
            nativeQuery = true)
    Page<Offer> findByFieldIdPageable(String fieldId, Pageable pageable);

    @Query(
            value = "SELECT o.title FROM offers o where o.title LIKE %:term% LIMIT 20",
            nativeQuery = true)
    List<String> findTitlesLikeSearchTerm(@Param("term") String term);

    @Transactional
    @Modifying
    int deleteByExpireDateBefore(LocalDate todayDate);

    @Transactional
    @Modifying
    int deleteByInsertionDateBefore(LocalDate insertionDate);

    @Transactional
    @Modifying
    int deleteByEditDateBefore(LocalDate editDate);

    @Transactional
    @Modifying
    int deleteByInsertionDateBeforeAndEditDateBefore(LocalDate insertionDate, LocalDate editDate);

    @Query(value = "(SELECT * FROM offers o WHERE o.profession_id IN (SELECT pf.profession_id FROM profession_field pf WHERE pf.field_id=:idField1) LIMIT :limitField1 OFFSET :offsetField1) UNION (SELECT * FROM offers o WHERE o.profession_id IN (SELECT pf.profession_id FROM profession_field pf WHERE pf.field_id=:idField2) LIMIT :limitField2 OFFSET :offsetField2) UNION (SELECT * FROM offers o WHERE o.profession_id IN (SELECT pf.profession_id FROM profession_field pf WHERE pf.field_id=:idField3) LIMIT :limitField3 OFFSET :offsetField3) UNION (SELECT * FROM offers o WHERE o.profession_id IN (SELECT pf.profession_id FROM profession_field pf WHERE pf.field_id=:idField4) LIMIT :limitField4 OFFSET :offsetField4) UNION (SELECT * FROM offers o WHERE o.profession_id IN (SELECT pf.profession_id FROM profession_field pf WHERE pf.field_id=:idField5) LIMIT :limitField5 OFFSET :offsetField5)", nativeQuery = true)
    Collection<Offer> getOffersAccToPredictedRelevances(
            @Param("limitField1") int limitField1,
            @Param("limitField2") int limitField2,
            @Param("limitField3") int limitField3,
            @Param("limitField4") int limitField4,
            @Param("limitField5") int limitField5,
            @Param("offsetField1") int offsetField1,
            @Param("offsetField2") int offsetField2,
            @Param("offsetField3") int offsetField3,
            @Param("offsetField4") int offsetField4,
            @Param("offsetField5") int offsetField5,
            @Param("idField1") String idField1,
            @Param("idField2") String idField2,
            @Param("idField3") String idField3,
            @Param("idField4") String idField4,
            @Param("idField5") String idField5);
}