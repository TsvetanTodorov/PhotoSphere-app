package com.photocollectors.photo.service;

import com.photocollectors.photo.model.FavouritePhoto;
import com.photocollectors.photo.model.Photo;
import com.photocollectors.photo.repository.FavouriteRepository;
import com.photocollectors.photo.repository.PhotoRepository;
import com.photocollectors.user.model.User;
import com.photocollectors.web.dto.CreatePhotoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final FavouriteRepository favouriteRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, FavouriteRepository favouriteRepository) {
        this.photoRepository = photoRepository;
        this.favouriteRepository = favouriteRepository;
    }


    public void createNewPhoto(CreatePhotoRequest createPhotoRequest, User user) {

        Photo photo = Photo.builder()
                .name(createPhotoRequest.getName())
                .author(createPhotoRequest.getAuthor())
                .style(createPhotoRequest.getStyle())
                .imageUrl(createPhotoRequest.getImageUrl())
                .votes(0)
                .owner(user)
                .build();

        photoRepository.save(photo);

    }

    public void deletePhotoById(UUID id) {
        photoRepository.deleteById(id);
    }

    public List<Photo> getAllPhotos() {

        List<Photo> allPhotos = photoRepository.findAll();

        allPhotos.sort(Comparator.comparing(Photo::getVotes).reversed().thenComparing(Photo::getName));

        return allPhotos;
    }

    public void createFavouriteByPhotoId(UUID paintingId, User user) {

        Photo photo = getById(paintingId);



        FavouritePhoto favouritePhoto = FavouritePhoto.builder()
                .name(photo.getName())
                .author(photo.getAuthor())
                .owner(user)
                .imageUrl(photo.getImageUrl())
                .createdOn(LocalDateTime.now())
                .build();

        favouriteRepository.save(favouritePhoto);

    }

    private Photo getById(UUID photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(()-> new RuntimeException("Painting with id %s does not exist".formatted(photoId)));
    }

    public void incrementVotesByOne(UUID photoId) {

        Photo photo = getById(photoId);

        photo.setVotes(photo.getVotes() + 1);
        photoRepository.save(photo);
    }

    public void deleteFavouritePhotoById(UUID photoId) {
        favouriteRepository.deleteById(photoId);
    }
}
