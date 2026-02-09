package com.revplay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.revplay.util.DBConnection;

public class HistoryDAO {

    public void addHistory(int userId, int songId) {

        String sql =
                "INSERT INTO listening_history (user_id, song_id) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, songId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
