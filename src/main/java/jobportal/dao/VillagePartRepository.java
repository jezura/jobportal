package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.VillagePart;
import org.springframework.data.repository.CrudRepository;

public interface VillagePartRepository extends CrudRepository <VillagePart, String>
{
    VillagePart findVillagePartById(String id);
    VillagePart findVillagePartByCode(String code);
}