package com.revplay.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.revplay.model.UserType;
import com.revplay.service.*;

public class RevPlayApp {

    private static UserType askUserType(Scanner sc) {
        while (true) {
            try {
                System.out.println("Select type:");
                System.out.println("1. User");
                System.out.println("2. Artist");
                System.out.print("Choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) return UserType.USER;
                if (choice == 2) return UserType.ARTIST;

                System.out.println("Invalid choice. Try again.");

            } catch (InputMismatchException e) {
                System.out.println("Please enter a number only.");
                sc.nextLine();
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AuthService authService = new AuthService();
        SearchService searchService = new SearchService();
        PlayerService playerService = new PlayerService();
        PlaylistService playlistService = new PlaylistService();

        MusicService musicService = new MusicService();
        ArtistAlbumService albumService = new ArtistAlbumService();
        ArtistProfileService profileService = new ArtistProfileService();
        ArtistSongService songService = new ArtistSongService();

        boolean isLoggedIn = false;
        UserType loggedInType = null;

        Integer loggedInUserId = null;
        Integer loggedInArtistId = null;

        while (true) {

            /* ================= BEFORE LOGIN ================= */
            if (!isLoggedIn) {

                try {
                    System.out.println("\n===== REVPLAY =====");
                    System.out.println("1. Register");
                    System.out.println("2. Login");
                    System.out.println("3. Forgot Password");
                    System.out.println("4. Exit");
                    System.out.print("Choose option: ");

                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {

                        case 1:
                            UserType regType = askUserType(sc);

                            System.out.print("Name: ");
                            String name = sc.nextLine();

                            System.out.print("Email: ");
                            String email = sc.nextLine();

                            System.out.print("Password: ");
                            String password = sc.nextLine();

                            String bio = null;
                            String genre = null;

                            if (regType == UserType.ARTIST) {
                                System.out.print("Bio: ");
                                bio = sc.nextLine();

                                System.out.print("Genre: ");
                                genre = sc.nextLine();
                            }

                            authService.register(regType, name, email, password, bio, genre);
                            break;

                        /* ================= LOGIN ================= */
                        case 2:
                            UserType loginType = askUserType(sc);
                            Integer id = null;

                            while (id == null) {
                                System.out.print("Email: ");
                                email = sc.nextLine();

                                System.out.print("Password: ");
                                password = sc.nextLine();

                                id = authService.login(loginType, email, password);

                                if (id == null) {
                                    System.out.println("Try again.\n");
                                }
                            }

                            isLoggedIn = true;
                            loggedInType = loginType;

                            if (loginType == UserType.USER) {
                                loggedInUserId = id;
                            } else {
                                loggedInArtistId = id;
                            }
                            break;

                        case 3:
                            UserType fpType = askUserType(sc);
                            authService.forgotPassword(fpType, sc);
                            break;

                        case 4:
                            System.out.println("Thank you for using RevPlay!");
                            sc.close();
                            System.exit(0);

                        default:
                            System.out.println("Invalid option!");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Please enter numbers only.");
                    sc.nextLine();
                }
            }

            /* ================= AFTER LOGIN ================= */
            else {

                try {
                    /* -------- USER MENU -------- */
                    if (loggedInType == UserType.USER) {

                        System.out.println("\n===== REVPLAY (USER) =====");
                        System.out.println("1. Search Song");
                        System.out.println("2. Browse Albums");
                        System.out.println("3. Play Song");
                        System.out.println("4. Create Playlist");
                        System.out.println("5. Manage Playlists");
                        System.out.println("6. Logout");
                        System.out.print("Choice: ");

                        int choice = sc.nextInt();
                        sc.nextLine();

                        switch (choice) {
                            case 1:
                                System.out.print("Keyword: ");
                                searchService.searchSongByKeyword(sc.nextLine());
                                break;
                            case 2:
                                System.out.print("Artist name: ");
                                searchService.browseAlbumsByArtist(sc.nextLine());
                                break;
                            case 3:
                                playerService.startPlayer(loggedInUserId, sc);
                                break;
                            case 4:
                                playlistService.createPlaylist(loggedInUserId, sc);
                                break;
                            case 5:
                                playlistService.managePlaylists(loggedInUserId, sc);
                                break;
                            case 6:
                                isLoggedIn = false;
                                loggedInUserId = null;
                                System.out.println("Logged out!");
                                break;
                            default:
                                System.out.println("Invalid option!");
                        }
                    }

                    /* -------- ARTIST MENU -------- */
                    else {

                        System.out.println("\n===== REVPLAY (ARTIST) =====");
                        System.out.println("1. Create Album");
                        System.out.println("2. Upload Song");
                        System.out.println("3. View Songs");
                        System.out.println("4. View Albums");
                        System.out.println("5. Edit Profile");
                        System.out.println("6. Logout");
                        System.out.print("Choice: ");

                        int choice = sc.nextInt();
                        sc.nextLine();

                        switch (choice) {

                            case 1:
                                try {
                                    System.out.print("Album Name: ");
                                    String albumName = sc.nextLine();

                                    System.out.print("Release Date (yyyy-mm-dd): ");
                                    String dateStr = sc.nextLine();

                                    System.out.print("Genre: ");
                                    String albumGenre = sc.nextLine();

                                    albumService.createAlbum(
                                            loggedInArtistId,
                                            albumName,
                                            java.sql.Date.valueOf(dateStr),
                                            albumGenre
                                    );
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Invalid date format.");
                                }
                                break;

                            case 2:
                                try {
                                    System.out.print("Song Title: ");
                                    String title = sc.nextLine();

                                    System.out.print("Genre: ");
                                    String songGenre = sc.nextLine();

                                    System.out.print("Duration (seconds): ");
                                    int duration = sc.nextInt();
                                    sc.nextLine();

                                    System.out.print("Release Date (yyyy-mm-dd): ");
                                    String songDate = sc.nextLine();

                                    System.out.print("Album ID (0 if none): ");
                                    int albumId = sc.nextInt();
                                    sc.nextLine();

                                    System.out.print("File Path: ");
                                    String filepath = sc.nextLine();

                                    musicService.uploadSong(
                                            loggedInArtistId,
                                            (albumId == 0) ? null : albumId,
                                            title,
                                            songGenre,
                                            duration,
                                            java.sql.Date.valueOf(songDate),
                                            filepath
                                    );
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid numeric input.");
                                    sc.nextLine();
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Invalid date format.");
                                }
                                break;

                            case 3:
                                songService.viewMySongs(loggedInArtistId, sc);
                                break;

                            case 4:
                                albumService.viewMyAlbums(loggedInArtistId);
                                break;

                            case 5:
                                profileService.viewAndEditProfile(loggedInArtistId, sc);
                                break;

                            case 6:
                                isLoggedIn = false;
                                loggedInArtistId = null;
                                System.out.println("Logged out!");
                                break;

                            default:
                                System.out.println("Invalid option!");
                        }
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Please enter valid input.");
                    sc.nextLine();
                }
            }
        }
    }
}
