package com.revplay.service;

import java.util.List;
import java.util.Scanner;

import com.revplay.dao.SongDAO;
import com.revplay.model.Song;

public class ArtistSongService {

    private SongDAO songDAO = new SongDAO();

    // VIEW ALL SONGS (Album + Non-Album)
    public void viewMySongs(int artistId, Scanner sc) {

        List<Song> songs = songDAO.getSongsByArtistId(artistId);

        if (songs.isEmpty()) {
            System.out.println("❌ No songs uploaded yet.");
            return;
        }

        System.out.println("\n===== MY SONGS =====");
        System.out.println("ID | Title | Album | Plays");
        System.out.println("--------------------------------");

        for (Song s : songs) {
            System.out.println(
                    s.getSongId() + " | " +
                            s.getTitle() + " | " +
                            s.getAlbumName() + " | " +
                            s.getPlayCount()
            );
        }

        System.out.print("\nEnter Song ID to manage (0 to Back): ");
        int songId = sc.nextInt();
        sc.nextLine();

        if (songId != 0) {
            manageSong(songId, artistId, sc);
        }
    }

    // MANAGE SINGLE SONG
    private void manageSong(int songId, int artistId, Scanner sc) {

        System.out.println("\n===== MANAGE SONG =====");
        System.out.println("1. Update Song Info");
        System.out.println("2. Remove Song from Album");
        System.out.println("3. Delete Song");
        System.out.println("4. Back");
        System.out.print("Choose option: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1:
                updateSong(songId, artistId, sc);
                return; // ✅ BACK TO VIEW SONGS

            case 2:
                songDAO.removeSongFromAlbum(songId, artistId);
                System.out.println("✅ Song removed from album.");
                return;

            case 3:
                songDAO.deleteSong(songId, artistId);
                System.out.println("🗑 Song deleted.");
                return;

            case 4:
                return;

            default:
                System.out.println("❌ Invalid choice!");
        }
    }

    // UPDATE SONG (NO INSERT!)
    private void updateSong(int songId, int artistId, Scanner sc) {

        System.out.print("New Title: ");
        String title = sc.nextLine();

        System.out.print("New Genre: ");
        String genre = sc.nextLine();

        System.out.print("New Duration (seconds): ");
        int duration = sc.nextInt();
        sc.nextLine();

        boolean updated =
                songDAO.updateSong(songId, artistId, title, genre, duration);

        if (updated) {
            System.out.println("✅ Song updated successfully!");
        } else {
            System.out.println("❌ Update failed!");
        }
    }
}
