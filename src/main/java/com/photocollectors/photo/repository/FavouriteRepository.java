package com.photocollectors.photo.repository;

import com.photocollectors.photo.model.FavouritePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FavouriteRepository extends JpaRepository<FavouritePhoto, UUID> {



}
