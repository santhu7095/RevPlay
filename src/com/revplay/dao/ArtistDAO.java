package com.revplay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revplay.model.Artist;
import com.revplay.util.DBConnection;

public class ArtistDAO {

    /* ===== REGISTER ARTIST ===== */
    public boolean registerArtist(Artist artist) {

        String sql = "INSERT INTO artists (artist_name, email, password, bio, genre) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, artist.getArtistName());
            ps.setString(2, artist.getEmail());
            ps.setString(3, artist.getPassword()); // hashed
            ps.setString(4, artist.getBio());
            ps.setString(5, artist.getGenre());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ===== LOGIN ARTIST ===== */
    public Integer loginArtist(String email, String password) {

        String sql = "SELECT artist_id FROM artists WHERE email=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password); // hashed
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("artist_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /* ===== CHECK EMAIL EXISTS (FORGOT PASSWORD) ===== */
    public boolean emailExists(String email) {

        String sql = "SELECT 1 FROM artists WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ===== RESET PASSWORD (FORGOT PASSWORD) ===== */
    public boolean resetPassword(String email, String newPassword) {

        String sql = "UPDATE artists SET password=? WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword); // hashed
            ps.setString(2, email);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ===== FETCH PROFILE ===== */
    public Artist getArtistById(int artistId) {

        String sql = "SELECT * FROM artists WHERE artist_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, artistId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Artist artist = new Artist();
                artist.setArtistId(rs.getInt("artist_id"));
                artist.setArtistName(rs.getString("artist_name"));
                artist.setEmail(rs.getString("email"));
                artist.setBio(rs.getString("bio"));
                artist.setGenre(rs.getString("genre"));
                artist.setInstagramLink(rs.getString("instagram_link"));
                artist.setYoutubeLink(rs.getString("youtube_link"));
                return artist;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /* ===== UPDATE PROFILE ===== */
    public void updateArtistProfile(int id, String bio, String genre, String insta, String yt) {

        String sql = "UPDATE artists SET bio=?, genre=?, instagram_link=?, youtube_link=? WHERE artist_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, bio);
            ps.setString(2, genre);
            ps.setString(3, insta);
            ps.setString(4, yt);
            ps.setInt(5, id);

            ps.executeUpdate();
            System.out.println("Profile updated!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
