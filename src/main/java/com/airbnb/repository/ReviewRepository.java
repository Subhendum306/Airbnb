package com.airbnb.repository;

import com.airbnb.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    /*@Query("select r from Review r where r.property=:propertyId and r.propertyUser=:userId")
    Review findReviewByUserIdAndPropertyId(@Param("propertyId") long userId,@Param("userId") long propertyId);*/
    @Query("select r from Review r where r.property=:property and r.propertyUser=:user")
    Review findReviewByPropertyAndUser(@Param("property") Property property,@Param("user") PropertyUser user);


    List<Review> findByPropertyUser(PropertyUser user);

    //Find reviews by property
   /* @Query("select r from Review r where r.property=:property")
    List<Review> findReviewsByProperty(@Param("property") Property property);

    //Find reviews by property user
    @Query("selecr r from Review r where r.propertyUser=:propertyUser")
    List<Review> findReviewsByPropertyUser(@Param("propertyUser") PropertyUser propertyUser);

    //Find reviews by property and rating
    @Query("select r from Review r where r.property=:property and r.rating=:rating")
    List<Review> findReviewsByPropertyAndRating(@Param("property") Property property,@Param("rating") int rating);

    //Find average rating for a property
    @Query("select avg(r.rating) from Review r where r.property=:property")
    Double findAverageRatingForProperty(@Param("property") Property property);

    //Find reviews by location
    @Query("select r from Review r where r.property.location=:location")
    List<Review> findReviewsByLocation(@Param("location") Location location);

    //Find review by country
    @Query("select r from Review r where r.property.country=:country")
    List<Review> findReviewsByCountry(@Param("country") Country country);

    //Find top-rated properties
    @Query("select p from Property p join p.reviews r where r.rating >:minRating")
    List<Property> findTopRatedProperties(@Param("minRating") int minRating); */
}