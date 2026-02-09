package com.revplay.service;

import java.util.List;
import java.util.Scanner;
import com.revplay.dao.PlaylistDAO;

public class PlaylistService {

    private PlaylistDAO dao = new PlaylistDAO();

    public void createPlaylist(int userId, Scanner sc) {

        System.out.print("Playlist name: ");
        String name = sc.nextLine();

        System.out.print("Description: ");
        String desc = sc.nextLine();

        System.out.print("Public? (yes/no): ");
        boolean isPublic = sc.nextLine().equalsIgnoreCase("yes");

        dao.createPlaylist(userId, name, desc, isPublic);
    }

    public void managePlaylists(int userId, Scanner sc) {

        List<String> playlists = dao.getUserPlaylists(userId);

        if (playlists.isEmpty()) {
            System.out.println("❌ No playlists found.");
            return;
        }

        System.out.println("\n🎶 Your Playlists:");
        playlists.forEach(System.out::println);

        System.out.print("Enter playlist ID: ");
        int pid = sc.nextInt();
        sc.nextLine();

        System.out.println("1. Add Song");
        System.out.println("2. Remove Song");
        int choice = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Song ID: ");
        int songId = sc.nextInt();
        sc.nextLine();

        if (choice == 1)
            dao.addSongToPlaylist(pid, songId);
        else
            dao.removeSongFromPlaylist(pid, songId);
    }
}
