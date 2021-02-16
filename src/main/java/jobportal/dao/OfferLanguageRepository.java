package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.OfferLanguage;
import org.springframework.data.repository.CrudRepository;

public interface OfferLanguageRepository extends CrudRepository <OfferLanguage, Integer>
{
    OfferLanguage findOfferLanguageById(String id);
}