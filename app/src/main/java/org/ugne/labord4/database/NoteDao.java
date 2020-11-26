package org.ugne.labord4.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Query("SELECT * FROM note WHERE id IN (:userIds)")
    List<Note> loadAllByIds(int[] userIds);

//    @Query("SELECT * FROM note WHERE id = :id AND " +
//            "content LIKE :last LIMIT 1")
//    User findById(Integer id);

    @Insert
    void insertAll(Note... notes);

    @Delete
    void delete(Note note);
}
