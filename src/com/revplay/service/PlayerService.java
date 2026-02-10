package com.revplay.service;

import java.util.List;
import java.util.Scanner;

import com.revplay.dao.SongDAO;
import com.revplay.dao.HistoryDAO;
import com.revplay.model.Song;

public class PlayerService {

    private SongDAO songDAO = new SongDAO();
    private HistoryDAO historyDAO = new HistoryDAO();

    /* =====================================================
       ENTRY METHOD — USER SEARCHES & SELECTS SONG
       ===================================================== */
    public void startPlayer(int userId, Scanner sc) {

        System.out.print("Enter song name or artist keyword: ");
        String keyword = sc.nextLine();

        List<Song> songs = songDAO.searchSongsForPlayback(keyword);

        if (songs.isEmpty()) {
            System.out.println("❌ No songs found.");
            return;
        }

        System.out.println("\n🎧 Available Songs:");
        for (Song s : songs) {
            System.out.println(
                    s.getSongId() + " | " +
                    s.getTitle() + " | " +
                    s.getArtistName()
            );
        }

        System.out.print("\nEnter Song ID to play: ");
        int selectedId = sc.nextInt();
        sc.nextLine();

        Song selectedSong = null;
        for (Song s : songs) {
            if (s.getSongId() == selectedId) {
                selectedSong = s;
                break;
            }
        }

        if (selectedSong == null) {
            System.out.println("❌ Invalid selection. Please choose from the list.");
            return;
        }

        playSong(userId, selectedSong, sc);
    }

    /* =====================================================
       CONSOLE PLAYER (NO REAL AUDIO)
       ===================================================== */
    public void playSong(int userId, Song song, Scanner sc) {

        // Update analytics
        songDAO.incrementPlayCount(song.getSongId());

        try {
            if (userId > 0) {
                historyDAO.addHistory(userId, song.getSongId());
            }
        } catch (Exception e) {
            System.out.println("⚠ Could not record history.");
        }

        boolean playing = true;
        boolean paused = false;

        while (playing) {

            System.out.println("\n🎵 Selected Song: "
                    + song.getTitle()
                    + " | "
                    + song.getArtistName());

            System.out.println("1. Play");
            System.out.println("2. Pause");
            System.out.println("3. Skip");
            System.out.println("4. Repeat");
            System.out.println("5. Back to User Menu");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    if (!paused) {
                        System.out.println("▶ Playing: " + song.getTitle());
                        simulatePlayback();
                        System.out.println("✔ Song finished.");
                    } else {
                        System.out.println("▶ Resumed: " + song.getTitle());
                        paused = false;
                    }
                    break;

                case 2:
                    paused = true;
                    System.out.println("⏸ Paused: " + song.getTitle());
                    break;

                case 3:
                    System.out.println("⏭ Skipped: " + song.getTitle());
                    playing = false;
                    break;

                case 4:
                    System.out.println("🔁 Repeating: " + song.getTitle());
                    simulatePlayback();
                    System.out.println("✔ Song finished.");
                    break;

                case 5:
                    System.out.println("⬅ Returning to User Menu...");
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    /* =====================================================
       PLAYBACK SIMULATION (CONSOLE ONLY)
       ===================================================== */
    private void simulatePlayback() {
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println("⏳ Playing... " + i + " sec");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Playback interrupted.");
        }
    }
}
