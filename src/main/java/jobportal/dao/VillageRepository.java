package jobportal.dao;

import jobportal.models.offer_data_models.codebooks.Village;
import org.springframework.data.repository.CrudRepository;

public interface VillageRepository extends CrudRepository <Village, Integer>
{
    Village findVillageById(String id);
    Village findVillageByCode(String code);
}