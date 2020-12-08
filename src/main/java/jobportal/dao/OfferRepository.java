package jobportal.dao;


import jobportal.models.Offer;
import jobportal.models.Village;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OfferRepository extends PagingAndSortingRepository<Offer, Long>
{
    Offer findOfferById(Long id);
}