package com.revplay.service;

import java.util.Scanner;

import com.revplay.dao.ArtistDAO;
import com.revplay.model.Artist;

public class ArtistService {

    private ArtistDAO artistDAO = new ArtistDAO();

    /* ================= VIEW PROFILE ================= */
    public Artist viewProfile(int artistId) {
        return artistDAO.getArtistById(artistId);
    }

    /* ================= UPDATE PROFILE ================= */
    public void updateProfile(int artistId, Scanner sc) {

        System.out.print("Enter new bio: ");
        String bio = sc.nextLine();

        System.out.print("Enter new genre: ");
        String genre = sc.nextLine();

        System.out.print("Instagram link: ");
        String insta = sc.nextLine();

        System.out.print("YouTube link: ");
        String yt = sc.nextLine();

        artistDAO.updateArtistProfile(artistId, bio, genre, insta, yt);
    }
}
