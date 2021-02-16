package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.Region;
import org.springframework.data.repository.CrudRepository;

public interface RegionRepository extends CrudRepository <Region, Integer>
{
    Region findRegionById(String id);
}