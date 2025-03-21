package com.photocollectors.user.model;

import com.photocollectors.photo.model.FavouritePhoto;
import com.photocollectors.photo.model.Photo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER )
    private List<Photo> photos;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER )
    private List<FavouritePhoto> favouritePhotos;

}
