package com.airbnb.repository;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    @Query("select f from Favourite f where property=:property")
    List<Property> getProperty(@Param("property") Property property);


//    @Query("select f from Favourite f where f.isFavorite=true")
//     List<Favourite> getFavouriteProperties();


/*
    // 1. Find all favourite properties for a given user
    @Query("select f from Favourite f where f.PropertyUser=:user")
    List<Favourite> findFavouritesByUser(@Param("user") PropertyUser user);

    // 2. Find all favourite properties for a given property
    @Query("select f from Favourite f where f.property=:property")
    List<Favourite> findFavouritesByProperty(@Param("property") Property property);

    // 3. Find all favourite properties that are marked as favourite
    @Query("select f from Favourite f where f.isFavourite=true")
    List<Favourite> findFavouriteProperties();

    // 4. Find a favourite property by user and property
    @Query("select f from Favourite f where f.PropertyUser=:user and f.property=:property")
    Favourite findFavouriteByUserAndProperty(@Param("user") PropertyUser user, @Param("property") Property property);

    // 5. Find all users who have favourite a given property
    @Query("select f.PropertyUser from Favourite f where f.property=:property")
    List<PropertyUser> findUsersByProperty(@Param("property") Property property);

    // 6. Find all properties that are favourite by a given user
    @Query("select f.property from Favourite f where f.PropertyUser=:user")
    List<Property> findPropertiesByUser(@Param("user") PropertyUser user);

    // 7. Count the number of favourite properties for a given user
    @Query("select count(f) from Favourite f where f.PropertyUser =:user")
    Long countFavouritesByUser(@Param("user") PropertyUser user);

    // 8. Check if a property is favourite by a given user
    @Query("select case when count(f) > 0 then true else false end from Favourite f where f.user=:user and f.property=:property")
    Boolean isPropertyFavouritedByUser(@Param("user") PropertyUser user, @Param("property") Property property);
*/

}