package jobportal.dao;


import jobportal.models.offer_data_models.codebooks.PlaceType;
import org.springframework.data.repository.CrudRepository;

public interface PlaceTypeRepository extends CrudRepository <PlaceType, String>
{
    PlaceType findPlaceTypeById(String id);
}