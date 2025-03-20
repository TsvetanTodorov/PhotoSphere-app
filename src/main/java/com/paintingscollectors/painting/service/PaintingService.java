package com.paintingscollectors.painting.service;

import com.paintingscollectors.painting.repository.FavouriteRepository;
import com.paintingscollectors.painting.repository.PaintingRepository;
import com.paintingscollectors.user.model.User;
import com.paintingscollectors.web.dto.CreatePaintingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaintingService {

    private final PaintingRepository paintingRepository;
    private final FavouriteRepository favouriteRepository;

    @Autowired
    public PaintingService(PaintingRepository paintingRepository, FavouriteRepository favouriteRepository) {
        this.paintingRepository = paintingRepository;
        this.favouriteRepository = favouriteRepository;
    }


    public void createNewPainting(CreatePaintingRequest createPaintingRequest, User user) {



    }
}
