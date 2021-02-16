package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.PlaceType;
import org.springframework.data.repository.CrudRepository;

public interface PlaceTypeRepository extends CrudRepository <PlaceType, Integer>
{
    PlaceType findPlaceTypeById(String id);
}