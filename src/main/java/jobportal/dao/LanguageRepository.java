package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.Language;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository <Language, Integer>
{
    Language findLanguageById(String id);
}