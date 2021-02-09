package jobportal.dao;

import jobportal.models.Offer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

public interface OfferRepository extends PagingAndSortingRepository<Offer, Long>
{
    Offer findOfferById(Long id);

    /*@Transactional
    @Modifying
    @Query("Delete from Offer o WHERE expire_date <= :todayDate")
    int deleteAllOffersAfterExpiration(@Param("todayDate") Date todayDate);*/

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
}