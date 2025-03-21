package com.photocollectors.web;

import com.photocollectors.photo.service.PhotoService;
import com.photocollectors.user.model.User;
import com.photocollectors.user.service.UserService;
import com.photocollectors.web.dto.CreatePhotoRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/photos")
public class PhotosController {

    private final UserService userService;
    private final PhotoService photoService;

    @Autowired
    public PhotosController(UserService userService, PhotoService photoService) {
        this.userService = userService;
        this.photoService = photoService;
    }

    @GetMapping("/new")
    public ModelAndView getNewPhotoPage(HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-photo");
        modelAndView.addObject("user",user);
        modelAndView.addObject("createPhotoRequest", new CreatePhotoRequest());

        return modelAndView;
    }

    @PostMapping
    public ModelAndView createNewPhoto(@Valid CreatePhotoRequest createPhotoRequest, BindingResult bindingResult, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);

        if(bindingResult.hasErrors()) {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("new-photo");
            modelAndView.addObject("user",user);
            return modelAndView;
        }

        photoService.createNewPhoto(createPhotoRequest,user);

        return new ModelAndView("redirect:/home");
    }

    @DeleteMapping("/{id}")
    public String deletePhoto(@PathVariable UUID id) {

        photoService.deletePhotoById(id);

        return "redirect:/home";
    }


    @PostMapping("/favourites/{id}")
    public String makeFavouritePhoto(@PathVariable UUID id, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);

        photoService.createFavouriteByPhotoId(id,user);

        return "redirect:/home";
    }

    @DeleteMapping("/favourites/{id}")
    public String deleteFavouritePhoto(@PathVariable UUID id) {

        photoService.deleteFavouritePhotoById(id);

        return "redirect:/home";
    }

    @PutMapping("/{id}/votes")
    public String updateVotes(@PathVariable UUID id, HttpSession session) {

        photoService.incrementVotesByOne(id);

        return "redirect:/home";
    }
}
