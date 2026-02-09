package com.revplay.service;
import com.revplay.util.Music;

import java.util.List;
import java.util.Scanner;

import com.revplay.dao.SongDAO;
import com.revplay.dao.HistoryDAO;
import com.revplay.model.Song;

public class PlayerService {

    private SongDAO songDAO = new SongDAO();
    private HistoryDAO historyDAO = new HistoryDAO();
    private Music music;

    /* =====================================================
       ENTRY METHOD â€” USER SEARCHES & SELECTS SONG
       ===================================================== */
    public void startPlayer(int userId, Scanner sc) {

        System.out.print("Enter song name or artist keyword: ");
        String keyword = sc.nextLine();

        List<Song> songs = songDAO.searchSongsForPlayback(keyword);

        if (songs.isEmpty()) {
            System.out.println("â�Œ No songs found.");
            return;
        }

        System.out.println("\nðŸŽ§ Available Songs:");
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


        boolean valid = false;
        Song selectedSong = null;
        for (Song s : songs) {
            if (s.getSongId() == selectedId) {
                selectedSong = s;
                break;
            }
        }

        if (selectedSong == null) {
            System.out.println("â�Œ Invalid selection. Please choose from the list.");
            return;
        }

        playSong(userId, selectedSong, sc);
    }


    /* =====================================================
       PLAYER SIMULATION
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

     while (playing) {
         System.out.println("1. Play");
         System.out.println("2. Pause");
         System.out.println("3. Skip");
         System.out.println("4. Repeat");
         System.out.print("Choose option: ");

         int choice = sc.nextInt();
         sc.nextLine();

         switch (choice) {
         
         case 1:
             if (music == null) {
             	System.out.println("DEBUG PATH: " + song.getFilepath());

             	music = new Music(song.getFilepath());
             	music.play();
             }
             System.out.println("▶ Playing: " + song.getTitle());
             break;

         case 2:
             if (music != null) {
            	 music.pause();
             }
             System.out.println("⏸ Paused");
             break;

         case 3:
             if (music != null) {
            	 music.stop();
            	 music = null;
             }
             System.out.println("⏭ Skipped");
             playing = false;
             break;

         case 4:
             if (music != null) {
            	 music.repeat(true);
             }
             System.out.println("🔁 Repeat ON");
             break;


//             case 1:
//                 System.out.println("▶ Playing...");
//                 System.out.println("\n🎵 Now Playing: "
//                         + song.getTitle()
//                         + " | "
//                         + song.getArtistName());
//                 break;
//
//             case 2:
//                 System.out.println("⏸ Paused");
//                 System.out.println("\n⏸ Paused: "
//                         + song.getTitle()
//                         + " | "
//                         + song.getArtistName());
//                 playing = false;
//                 break;
//
//             case 3:
//                 System.out.println("⏭ Skipped (simulated)");
//                 System.out.println("\n⏭ Skipped "
//                         + song.getTitle()
//                         + " | "
//                         + song.getArtistName());
//                 playing = false;
//                 break;
//
//             case 4:
//                 System.out.println("🔁 Repeating");
//                 System.out.println("\n🔁 Repeating: "
//                         + song.getTitle()
//                         + " | "
//                         + song.getArtistName());
//                 
//                 break;

             default:
                 System.out.println("❌ Invalid choice!");
         }
     }
 }
}
